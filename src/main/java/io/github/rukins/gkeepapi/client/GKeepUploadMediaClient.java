package io.github.rukins.gkeepapi.client;

import io.github.rukins.gkeepapi.model.gkeep.node.blob.blobobject.ImageBlob;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.Response;

public interface GKeepUploadMediaClient {
    String URL = "https://notes-pa.googleapis.com";

    @POST("/upload/notes/v1/media/{blob-serverId}?uploadType=resumable")
    @Headers({
            "Connection: Keep-Alive",
            "User-Agent: x-gkeepapi (https://github.com/rukins/gkeepapi-java)"
    })
    Call<okhttp3.ResponseBody> uploadMedia(
            @Path("blob-serverId") String blobServerId,
            @Query("noteId") String nodeServerId,
            @Header("Authorization") String authorization
    );

    @PUT("/upload/notes/v1/media/{blob-serverId}?uploadType=resumable")
    @Headers({
            "Connection: Keep-Alive",
            "User-Agent: x-gkeepapi (https://github.com/rukins/gkeepapi-java)"
    })
    Call<ImageBlob> uploadMedia(
            @Body byte[] imageBytes,
            @Path("blob-serverId") String blobServerId,
            @Query("noteId") String nodeServerId,
            @Query("upload_id") String uploadId
    );
}
