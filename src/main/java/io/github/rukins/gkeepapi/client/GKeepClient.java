package io.github.rukins.gkeepapi.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.github.rukins.gkeepapi.model.gkeep.NodeRequest;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;

public interface GKeepClient {
    String URL = "https://notes-pa.googleapis.com";

    @RequestLine("POST /notes/v1/changes")
    @Headers({
            "Content-Type: application/json; charset=UTF-8",
            "Connection: Keep-Alive",
            "Authorization: OAuth {access-token}",
            "User-Agent: x-gkeepapi (https://github.com/rukins/gkeepapi-java)"
    })
    NodeResponse changes(NodeRequest body, @Param("access-token") String accessToken);

    @RequestLine("POST /notes/v1/getFamilyInfo")
    @Headers({
            "Connection: Keep-Alive",
            "Authorization: OAuth {access-token}",
            "User-Agent: x-gkeepapi (https://github.com/rukins/gkeepapi-java)"
    })
    NodeResponse getFamilyInfo(@Param("access-token") String accessToken);
}
