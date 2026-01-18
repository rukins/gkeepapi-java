package io.github.rukins.gkeepapi.client;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.Response;

public interface GKeepMediaClient {
    String URL = "https://keep.google.com";

    // it automatically redirects to https://lh3.googleusercontent.com/keep-bbsk/...
    // (that is located in Location header of the response) and returns image bytes
    @GET("/media/v2/{node-serverId}/{blob-serverId}")
    @Headers({
            "User-Agent: x-gkeepapi (https://github.com/rukins/gkeepapi-java)"
    })
    Call<okhttp3.ResponseBody> media(
            @Path("blob-serverId") String blobServerId,
            @Path("node-serverId") String nodeServerId,
            @Header("Authorization") String authorization
    );
}
