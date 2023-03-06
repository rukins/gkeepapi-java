package io.github.rukins.gkeepapi.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.github.rukins.gkeepapi.model.node.NodeRequest;
import io.github.rukins.gkeepapi.model.node.NodeResponse;

public interface GKeepClient {
    @RequestLine("POST /notes/v1/changes")
    @Headers({
            "Content-Type: application/json; charset=UTF-8", "Host: notes-pa.googleapis.com",
            "Accept-Encoding: gzip", "Content-Encoding: gzip", "Connection: Keep-Alive",
            "Authorization: OAuth {access-token}"
    })
    NodeResponse changes(NodeRequest body, @Param("access-token") String accessToken);

    @RequestLine("POST /notes/v1/getFamilyInfo")
    @Headers({
            "Content-Type: application/json; charset=UTF-8", "Host: notes-pa.googleapis.com",
            "Accept-Encoding: gzip", "Content-Encoding: gzip", "Connection: Keep-Alive",
            "Authorization: OAuth {access-token}"
    })
    NodeResponse getFamilyInfo(@Param("access-token") String accessToken);
}
