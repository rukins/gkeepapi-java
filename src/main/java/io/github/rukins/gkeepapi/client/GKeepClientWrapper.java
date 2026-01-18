package io.github.rukins.gkeepapi.client;

import com.google.gson.Gson;
import io.github.rukins.gkeepapi.config.GsonConfig;
import io.github.rukins.gkeepapi.exception.WrongBlobDataException;
import io.github.rukins.gkeepapi.model.gkeep.NodeRequest;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.MimeType;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.blobobject.ImageBlob;
import io.github.rukins.gkeepapi.model.image.ImageData;
import io.github.rukins.gpsoauth.Auth;
import io.github.rukins.gpsoauth.exception.AuthError;
import io.github.rukins.gpsoauth.model.AccessTokenRequestParams;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GKeepClientWrapper {
    private final Gson gson = GsonConfig.gson();

    private final GKeepClient client;
    private final GKeepUploadMediaClient uploadMediaClient;
    private final GKeepMediaClient mediaClient;

    private final Auth auth = new Auth();

    private final String masterToken;

    private String accessToken;

    public GKeepClientWrapper(String masterToken) {
        this.masterToken = masterToken;

        Retrofit retrofitGKeep = new Retrofit.Builder()
                .baseUrl(GKeepClient.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.client = retrofitGKeep.create(GKeepClient.class);

        Retrofit retrofitUpload = new Retrofit.Builder()
                .baseUrl(GKeepUploadMediaClient.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.uploadMediaClient = retrofitUpload.create(GKeepUploadMediaClient.class);

        Retrofit retrofitMedia = new Retrofit.Builder()
                .baseUrl(GKeepMediaClient.URL)
                .build();
        this.mediaClient = retrofitMedia.create(GKeepMediaClient.class);
    }

    public NodeResponse changes(NodeRequest body) throws AuthError {
        NodeResponse nodeResponse;

        try {
            String authHeader = "OAuth " + accessToken;
            Call<NodeResponse> call = client.changes(
                    body,
                    authHeader
            );
            Response<NodeResponse> response = call.execute();

            if (response.code() == 401) {
                throw new HttpUnauthorizedException("Unauthorized");
            }

            if (!response.isSuccessful()) {
                throw new RuntimeException("Request failed: " + response.code());
            }

            nodeResponse = response.body();
        } catch (HttpUnauthorizedException unauthorized) {
            updateAccessToken();

            String authHeader = "OAuth " + accessToken;
            Call<NodeResponse> call = client.changes(
                    body,
                    authHeader
            );
            try {
                nodeResponse = call.execute().body();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return nodeResponse;
    }

    public String getUploadId(String blobServerId, String nodeServerId) throws AuthError {
        final String UPLOAD_ID_HEADER = "X-GUploader-UploadID";

        Map<String, List<String>> headers;

        try {
            String authHeader = "OAuth " + accessToken;
            Call<ResponseBody> call = uploadMediaClient.uploadMedia(
                    blobServerId,
                    nodeServerId,
                    authHeader
            );
            Response<ResponseBody> response = call.execute();

            if (response.code() == 401) {
                throw new HttpUnauthorizedException("Unauthorized");
            }

            headers = response.headers().toMultimap();
        } catch (HttpUnauthorizedException unauthorized) {
            updateAccessToken();

            String authHeader = "OAuth " + accessToken;
            Call<ResponseBody> call = uploadMediaClient.uploadMedia(
                    blobServerId,
                    nodeServerId,
                    authHeader
            );
            try {
                Response<ResponseBody> response = call.execute();
                headers = response.headers().toMultimap();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return headers.containsKey(UPLOAD_ID_HEADER)
                ? headers.get(UPLOAD_ID_HEADER).get(0)
                : null;
    }

    public ImageBlob uploadImage(byte[] imageBytes, String blobServerId, String nodeServerId, String uploadId) {
        try {
            Call<ImageBlob> call = uploadMediaClient.uploadMedia(
                    imageBytes,
                    blobServerId,
                    nodeServerId,
                    uploadId
            );
            Response<ImageBlob> response = call.execute();
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ImageData getImageData(String blobServerId, String nodeServerId) throws AuthError, WrongBlobDataException {
        final String CONTENT_TYPE_HEADER = "Content-Type";
        final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";

        byte[] imageBytes;
        Map<String, List<String>> headers;

        try {
            String authHeader = "OAuth " + accessToken;
            Call<ResponseBody> call = mediaClient.media(
                    blobServerId,
                    nodeServerId,
                    authHeader
            );
            Response<ResponseBody> response = call.execute();

            if (response.code() == 401) {
                throw new HttpUnauthorizedException("Unauthorized");
            }

            if (response.isSuccessful()) {
                checkIfWrongBlobData(response.code());

                try {
                    imageBytes = response.body().bytes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                headers = response.headers().toMultimap();
            } else {
                throw new RuntimeException("Request failed: " + response.code());
            }
        } catch (HttpUnauthorizedException unauthorized) {
            updateAccessToken();

            String authHeader = "OAuth " + accessToken;
            Call<ResponseBody> call = mediaClient.media(
                    blobServerId,
                    nodeServerId,
                    authHeader
            );
            try {
                Response<ResponseBody> response = call.execute();
                checkIfWrongBlobData(response.code());

                try {
                    imageBytes = response.body().bytes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                headers = response.headers().toMultimap();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return headers.containsKey(CONTENT_TYPE_HEADER) && headers.containsKey(CONTENT_DISPOSITION_HEADER)
                ? new ImageData(
                        imageBytes,
                        getFileNameFromContentDispositionHeader(
                                headers.get(CONTENT_DISPOSITION_HEADER).get(0)
                        ),
                        MimeType.getByValue(headers.get(CONTENT_TYPE_HEADER).get(0))
                )
                : null;
    }

    private void updateAccessToken() throws AuthError {
        AccessTokenRequestParams accessTokenRequestParams = AccessTokenRequestParams
                .withDefaultValues()
                .masterToken(masterToken)
                .app("com.google.android.keep")
                .scopes("oauth2:https://www.googleapis.com/auth/memento https://www.googleapis.com/auth/reminders")
                .build();

        accessToken = auth.getAccessToken(accessTokenRequestParams).getAccessToken();
    }

    private String getFileNameFromContentDispositionHeader(String contentDispositionHeaderData) {
        Pattern pattern = Pattern.compile("filename=\"(?<name>.+)\"");

        Matcher matcher = pattern.matcher(contentDispositionHeaderData);

        if (matcher.find()) {
            return matcher.group("name");
        }

        return null;
    }

    private void checkIfWrongBlobData(int statusCode) throws WrongBlobDataException {
        if (statusCode == 400) {
            throw new WrongBlobDataException("Wrong blob server id");
        }
        else if (statusCode == 403) {
            throw new WrongBlobDataException("Wrong node server id");
        }
    }

    private static class HttpUnauthorizedException extends Exception {
        public HttpUnauthorizedException(String message) {
            super(message);
        }
    }
}
