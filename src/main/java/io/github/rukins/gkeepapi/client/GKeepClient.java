package io.github.rukins.gkeepapi.client;

import io.github.rukins.gkeepapi.model.gkeep.NodeRequest;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface GKeepClient {
    String URL = "https://notes-pa.googleapis.com";

    @POST("/notes/v1/changes")
    @Headers({
            "Content-Type: application/json; charset=UTF-8",
            "Connection: Keep-Alive",
            "User-Agent: x-gkeepapi (https://github.com/rukins/gkeepapi-java)"
    })
    Call<NodeResponse> changes(
            @Body NodeRequest body,
            @Header("Authorization") String authorization
    );

    @POST("/notes/v1/getFamilyInfo")
    @Headers({
            "Connection: Keep-Alive",
            "User-Agent: x-gkeepapi (https://github.com/rukins/gkeepapi-java)"
    })
    Call<NodeResponse> getFamilyInfo(
            @Header("Authorization") String authorization
    );
}
