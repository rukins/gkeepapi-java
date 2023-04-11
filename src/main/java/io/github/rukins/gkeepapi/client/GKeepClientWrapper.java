package io.github.rukins.gkeepapi.client;

import com.google.gson.Gson;
import feign.Feign;
import feign.FeignException;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
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

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GKeepClientWrapper {
    private final Gson gson = GsonConfig.gson();

    private final GKeepClient client = Feign.builder()
            .decoder(new GsonDecoder(gson))
            .encoder(new GsonEncoder(gson))
            .target(GKeepClient.class, GKeepClient.URL);

    private final GKeepUploadMediaClient uploadMediaClient = Feign.builder()
            .decoder(new GsonDecoder(gson))
            .encoder(new FileEncoder())
            .target(GKeepUploadMediaClient.class, GKeepUploadMediaClient.URL);

    private final GKeepMediaClient mediaClient = Feign.builder()
            .target(GKeepMediaClient.class, GKeepMediaClient.URL);

    private final Auth auth = new Auth();

    private final String masterToken;

    private String accessToken;

    public GKeepClientWrapper(String masterToken) {
        this.masterToken = masterToken;
    }

    public NodeResponse changes(NodeRequest body) throws AuthError {
        NodeResponse nodeResponse;

        try {
            nodeResponse = client.changes(body, accessToken);
        } catch (FeignException.Unauthorized unauthorized) {
            updateAccessToken();

            nodeResponse = client.changes(body, accessToken);
        }

        return nodeResponse;
    }

    public String getUploadId(String blobServerId, String nodeServerId) throws AuthError {
        final String UPLOAD_ID_HEADER = "X-GUploader-UploadID";

        Map<String, Collection<String>> headers;

        try (Response response = uploadMediaClient.uploadMedia(blobServerId, nodeServerId, accessToken)) {
            if (response.status() == 401) {
                throw new FeignException.Unauthorized(
                        response.reason(), response.request(),
                        response.body().asInputStream().readAllBytes(), response.headers()
                );
            }

            headers = response.headers();
        } catch (FeignException.Unauthorized unauthorized) {
            updateAccessToken();

            Response response = uploadMediaClient.uploadMedia(blobServerId, nodeServerId, accessToken);

            headers = response.headers();

            response.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return headers.containsKey(UPLOAD_ID_HEADER)
                ? (String) headers.get(UPLOAD_ID_HEADER).toArray()[0]
                : null;
    }

    public ImageBlob uploadImage(byte[] imageBytes, String blobServerId, String nodeServerId, String uploadId) {
        return uploadMediaClient.uploadMedia(imageBytes, blobServerId, nodeServerId, uploadId);
    }

    public ImageData getImageData(String blobServerId, String nodeServerId) throws AuthError, WrongBlobDataException {
        final String CONTENT_TYPE_HEADER = "Content-Type";
        final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";

        byte[] imageBytes;
        Map<String, Collection<String>> headers;

        try(Response response = mediaClient.media(blobServerId, nodeServerId, accessToken)) {
            if (response.status() == 401) {
                throw new FeignException.Unauthorized(
                        response.reason(), response.request(),
                        response.body().asInputStream().readAllBytes(), response.headers()
                );
            }
            checkIfWrongBlobData(response);

            imageBytes = response.body().asInputStream().readAllBytes();
            headers = response.headers();
        } catch (FeignException.Unauthorized unauthorized) {
            updateAccessToken();

            Response response = mediaClient.media(blobServerId, nodeServerId, accessToken);
            checkIfWrongBlobData(response);

            try {
                imageBytes = response.body().asInputStream().readAllBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            headers = response.headers();

            response.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return headers.containsKey(CONTENT_TYPE_HEADER) && headers.containsKey(CONTENT_DISPOSITION_HEADER)
                ? new ImageData(
                        imageBytes,
                        getFileNameFromContentDispositionHeader(
                                ((String) headers.get(CONTENT_DISPOSITION_HEADER).toArray()[0])
                        ),
                        MimeType.getByValue((String) headers.get(CONTENT_TYPE_HEADER).toArray()[0])
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

    private void checkIfWrongBlobData(Response response) throws WrongBlobDataException {
        if (response.status() == 400) {
            throw new WrongBlobDataException("Wrong blob server id");
        }
        else if (response.status() == 403) {
            throw new WrongBlobDataException("Wrong node server id");
        }
    }

    private static class FileEncoder implements Encoder {
        public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
            if (object instanceof Map && ((Map<?, ?>) object).containsKey("file")) {
                template.body((byte[]) ((Map<?, ?>) object).get("file"), StandardCharsets.UTF_8);
            }
        }
    }
}
