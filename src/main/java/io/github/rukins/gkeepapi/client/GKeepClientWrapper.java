package io.github.rukins.gkeepapi.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import io.github.rukins.gkeepapi.model.node.NodeRequest;
import io.github.rukins.gkeepapi.model.node.NodeResponse;
import io.github.rukins.gkeepapi.typeadapter.LocalDateTimeTypeAdapter;
import io.github.rukins.gkeepapi.typeadapter.LocaleTypeAdapter;
import io.github.rukins.gpsoauth.Auth;
import io.github.rukins.gpsoauth.exception.AuthError;
import io.github.rukins.gpsoauth.model.AccessTokenRequestParams;

import java.time.LocalDateTime;
import java.util.Locale;

public class GKeepClientWrapper {
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .registerTypeAdapter(Locale.class, new LocaleTypeAdapter())
            .create();

    private final GKeepClient client = Feign.builder()
            .decoder(new GsonDecoder(gson))
            .encoder(new GsonEncoder(gson))
            .target(GKeepClient.class, "https://notes-pa.googleapis.com");

    private final Auth auth = new Auth();

    private final String masterToken;

    private String accessToken;

    public GKeepClientWrapper(String masterToken) {
        this.masterToken = masterToken;
    }

    public NodeResponse changes(NodeRequest body) throws AuthError {
        NodeResponse response;

        try {
            response = client.changes(body, accessToken);
        } catch (FeignException.Unauthorized unauthorized) {
            updateAccessToken();

            response = client.changes(body, accessToken);
        }

        return response;
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
}
