package io.github.rukins.gkeepapi.model.gkeep.node.blob;

public enum MimeType {
    AUDIO_3GPP("audio/3gpp"),
    AUDIO_AMR_WB("audio/amr-wb"),
    IMAGE_GIF("image/gif"),
    IMAGE_JPG("image/jpg"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png");

    private final String value;

    MimeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static MimeType getByValue(String value) {
        for (MimeType mimeType : MimeType.values()) {
            if (mimeType.value.equals(value)) {
                return mimeType;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
