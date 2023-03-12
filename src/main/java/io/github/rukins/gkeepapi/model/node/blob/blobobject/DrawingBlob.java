package io.github.rukins.gkeepapi.model.node.blob.blobobject;

import io.github.rukins.gkeepapi.model.node.blob.BlobType;
import io.github.rukins.gkeepapi.model.node.blob.DrawingInfo;

import java.util.Objects;

public class DrawingBlob extends Blob {
    private DrawingInfo drawingInfo;

    private DrawingBlob() {
        setType(BlobType.DRAWING);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final DrawingBlob drawingBlob;

        private Builder() {
            drawingBlob = new DrawingBlob();
        }

        public Builder extractedText(String extractedText) {
            drawingBlob.setExtractedText(extractedText);
            return this;
        }

        public Builder extractionStatus(String extractionStatus) {
            drawingBlob.setExtractionStatus(extractionStatus);
            return this;
        }

        public Builder drawingInfo(DrawingInfo drawingInfo) {
            drawingBlob.setDrawingInfo(drawingInfo);
            return this;
        }

        public DrawingBlob build() {
            return drawingBlob;
        }
    }

    public DrawingInfo getDrawingInfo() {
        return drawingInfo;
    }

    public void setDrawingInfo(DrawingInfo drawingInfo) {
        this.drawingInfo = drawingInfo;
    }

    @Override
    public String toString() {
        return "DrawingBlob{" +
                "type='" + getType() + '\'' +
                ", extractedText='" + getExtractedText() + '\'' +
                ", extractionStatus='" + getExtractionStatus() + '\'' +
                ", drawingInfo=" + drawingInfo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DrawingBlob that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(drawingInfo, that.drawingInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), drawingInfo);
    }
}
