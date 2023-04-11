package io.github.rukins.gkeepapi.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface GKeepMediaClient {
    String URL = "https://keep.google.com";

    // it automatically redirects to https://lh3.googleusercontent.com/keep-bbsk/...
    // (that is located in Location header of the response) and returns image bytes
    @RequestLine("GET /media/v2/{node-serverId}/{blob-serverId}") // ?accept=audio/3gpp,audio/amr-wb,image/gif,image/jpg,image/jpeg,image/png&sz=2148
    @Headers({
            "Authorization: OAuth {access-token}",
            "User-Agent: x-gkeepapi (https://github.com/rukins/gkeepapi-java)"
    })
    Response media(
            @Param("blob-serverId") String blobServerId,
            @Param("node-serverId") String nodeServerId,
            @Param("access-token") String accessToken
    );
}
