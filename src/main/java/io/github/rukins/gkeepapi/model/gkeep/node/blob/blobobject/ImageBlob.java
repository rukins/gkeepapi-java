package io.github.rukins.gkeepapi.model.gkeep.node.blob.blobobject;

import com.google.gson.annotations.SerializedName;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.BlobType;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.ExtractionStatus;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.MimeType;

import java.util.Objects;

public class ImageBlob extends Blob {
    private MimeType mimetype;
    @SerializedName("byte_size")
    private Integer byteSize;
    private Integer width;
    private Integer height;
    @SerializedName("is_uploaded")
    private Boolean isUploaded;

    private ImageBlob() {
        setType(BlobType.IMAGE);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ImageBlob imageBlob;

        private Builder() {
            imageBlob = new ImageBlob();
        }

        public Builder extractedText(String extractedText) {
            imageBlob.setExtractedText(extractedText);
            return this;
        }

        public Builder extractionStatus(ExtractionStatus extractionStatus) {
            imageBlob.setExtractionStatus(extractionStatus);
            return this;
        }

        public Builder mimetype(MimeType mimetype) {
            imageBlob.setMimetype(mimetype);
            return this;
        }

        public Builder byteSize(Integer byteSize) {
            imageBlob.setByteSize(byteSize);
            return this;
        }

        public Builder width(Integer width) {
            imageBlob.setWidth(width);
            return this;
        }

        public Builder height(Integer height) {
            imageBlob.setHeight(height);
            return this;
        }

        public Builder uploaded(Boolean uploaded) {
            imageBlob.setUploaded(uploaded);
            return this;
        }

        public ImageBlob build() {
            return imageBlob;
        }
    }

    public MimeType getMimetype() {
        return mimetype;
    }

    public void setMimetype(MimeType mimetype) {
        this.mimetype = mimetype;
    }

    public Integer getByteSize() {
        return byteSize;
    }

    public void setByteSize(Integer byteSize) {
        this.byteSize = byteSize;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getUploaded() {
        return isUploaded;
    }

    public void setUploaded(Boolean uploaded) {
        isUploaded = uploaded;
    }

    @Override
    public String toString() {
        return "ImageBlob{" +
                "type='" + getType() + '\'' +
                ", extractedText='" + getExtractedText() + '\'' +
                ", extractionStatus='" + getExtractionStatus() + '\'' +
                ", mimetype='" + mimetype + '\'' +
                ", byteSize=" + byteSize +
                ", width=" + width +
                ", height=" + height +
                ", isUploaded=" + isUploaded +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImageBlob imageBlob)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(mimetype, imageBlob.mimetype)
                && Objects.equals(byteSize, imageBlob.byteSize)
                && Objects.equals(width, imageBlob.width)
                && Objects.equals(height, imageBlob.height)
                && Objects.equals(isUploaded, imageBlob.isUploaded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mimetype, byteSize, width, height, isUploaded);
    }
}
