package io.github.rukins.gkeepapi.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.blobobject.ImageBlob;

public interface GKeepUploadMediaClient {
    String URL = "https://notes-pa.googleapis.com";

    @RequestLine("POST /upload/notes/v1/media/{blob-serverId}?noteId={node-serverId}&uploadType=resumable")
    @Headers({
            "Connection: Keep-Alive",
            "Authorization: OAuth {access-token}",
            "User-Agent: x-gkeepapi (https://github.com/rukins/gkeepapi-java)"
    })
    Response uploadMedia(
            @Param("blob-serverId") String blobServerId,
            @Param("node-serverId") String nodeServerId,
            @Param("access-token") String accessToken
    );

    @RequestLine("PUT /upload/notes/v1/media/{blob-serverId}?noteId={node-serverId}&uploadType=resumable&upload_id={upload_id}")
    @Headers({
            "Connection: Keep-Alive",
            "User-Agent: x-gkeepapi (https://github.com/rukins/gkeepapi-java)"
    })
    ImageBlob uploadMedia(
            @Param("file") byte[] imageBytes,
            @Param("blob-serverId") String blobServerId,
            @Param("node-serverId") String nodeServerId,
            @Param("upload_id") String uploadId
    );
}
