package io.github.rukins.gkeepapi.model.requestheader;

import io.github.rukins.gkeepapi.model.PlatformType;
import io.github.rukins.gkeepapi.model.requestheader.capability.Capability;
import io.github.rukins.gkeepapi.utils.SessionIdUtils;

import java.util.List;
import java.util.Locale;

public class RequestHeader {
    public static final Locale DEFAULT_LOCALE = Locale.US;
    public static final String DEFAULT_NOTE_SUPPORTED_MODEL_FEATURES = "";

    private List<Capability> capabilities;

    private Locale clientLocale;

    private PlatformType clientPlatform;

    private String clientSessionId;

    private ClientVersion clientVersion;

    private String noteSupportedModelFeatures;

    public static Builder builder() {
        return new Builder();
    }

    public static Builder withDefaultValues() {
        return new Builder()
                .capabilities(Capability.DEFAULT_CAPABILITIES)
                .clientLocale(DEFAULT_LOCALE)
                .clientPlatform(PlatformType.DEFAULT_PLATFORM_TYPE)
                .clientSessionId(SessionIdUtils.generateIdFromMacAddress())
                .clientVersion(ClientVersion.DEFAULT_CLIENT_VERSION)
                .noteSupportedModelFeatures(DEFAULT_NOTE_SUPPORTED_MODEL_FEATURES);
    }

    public static class Builder {
        private final RequestHeader requestHeader;

        private Builder() {
            requestHeader = new RequestHeader();
        }

        public Builder capabilities(List<Capability> capabilities) {
            requestHeader.setCapabilities(capabilities);
            return this;
        }

        public Builder clientLocale(Locale clientLocale) {
            requestHeader.setClientLocale(clientLocale);
            return this;
        }

        public Builder clientPlatform(PlatformType clientPlatform) {
            requestHeader.setClientPlatform(clientPlatform);
            return this;
        }

        public Builder clientSessionId(String clientSessionId) {
            requestHeader.setClientSessionId(clientSessionId);
            return this;
        }

        public Builder clientVersion(ClientVersion clientVersion) {
            requestHeader.setClientVersion(clientVersion);
            return this;
        }

        public Builder noteSupportedModelFeatures(String noteSupportedModelFeatures) {
            requestHeader.setNoteSupportedModelFeatures(noteSupportedModelFeatures);
            return this;
        }

        public RequestHeader build() {
            return requestHeader;
        }
    }

    public List<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities = capabilities;
    }

    public Locale getClientLocale() {
        return clientLocale;
    }

    public void setClientLocale(Locale clientLocale) {
        this.clientLocale = clientLocale;
    }

    public PlatformType getClientPlatform() {
        return clientPlatform;
    }

    public void setClientPlatform(PlatformType clientPlatform) {
        this.clientPlatform = clientPlatform;
    }

    public String getClientSessionId() {
        return clientSessionId;
    }

    public void setClientSessionId(String clientSessionId) {
        this.clientSessionId = clientSessionId;
    }

    public ClientVersion getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(ClientVersion clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getNoteSupportedModelFeatures() {
        return noteSupportedModelFeatures;
    }

    public void setNoteSupportedModelFeatures(String noteSupportedModelFeatures) {
        this.noteSupportedModelFeatures = noteSupportedModelFeatures;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "capabilities=" + capabilities +
                ", clientLocale=" + clientLocale +
                ", clientPlatform=" + clientPlatform +
                ", clientSessionId='" + clientSessionId + '\'' +
                ", clientVersion=" + clientVersion +
                ", noteSupportedModelFeatures='" + noteSupportedModelFeatures + '\'' +
                '}';
    }
}
