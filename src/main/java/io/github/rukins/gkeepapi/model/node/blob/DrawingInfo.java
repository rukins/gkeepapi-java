package io.github.rukins.gkeepapi.model.node.blob;

import io.github.rukins.gkeepapi.model.node.blob.blobobject.ImageBlob;

import java.time.LocalDateTime;
import java.util.Objects;

public class DrawingInfo {
    private String drawingId;
    private ImageBlob snapshotData;
    private String snapshotFingerprint;
    private LocalDateTime thumbnailGeneratedTime;
    private String inkHash;
    private String snapshotProtoFprint;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final DrawingInfo drawingInfo;

        private Builder() {
            drawingInfo = new DrawingInfo();
        }

        public Builder drawingId(String drawingId) {
            drawingInfo.setDrawingId(drawingId);
            return this;
        }

        public Builder snapshotData(ImageBlob snapshotData) {
            drawingInfo.setSnapshotData(snapshotData);
            return this;
        }

        public Builder snapshotFingerprint(String snapshotFingerprint) {
            drawingInfo.setSnapshotFingerprint(snapshotFingerprint);
            return this;
        }

        public Builder thumbnailGeneratedTime(LocalDateTime thumbnailGeneratedTime) {
            drawingInfo.setThumbnailGeneratedTime(thumbnailGeneratedTime);
            return this;
        }

        public Builder inkHash(String inkHash) {
            drawingInfo.setInkHash(inkHash);
            return this;
        }

        public Builder snapshotProtoFprint(String snapshotProtoFprint) {
            drawingInfo.setSnapshotProtoFprint(snapshotProtoFprint);
            return this;
        }

        public DrawingInfo build() {
            return drawingInfo;
        }
    }

    public String getDrawingId() {
        return drawingId;
    }

    public void setDrawingId(String drawingId) {
        this.drawingId = drawingId;
    }

    public ImageBlob getSnapshotData() {
        return snapshotData;
    }

    public void setSnapshotData(ImageBlob snapshotData) {
        this.snapshotData = snapshotData;
    }

    public String getSnapshotFingerprint() {
        return snapshotFingerprint;
    }

    public void setSnapshotFingerprint(String snapshotFingerprint) {
        this.snapshotFingerprint = snapshotFingerprint;
    }

    public LocalDateTime getThumbnailGeneratedTime() {
        return thumbnailGeneratedTime;
    }

    public void setThumbnailGeneratedTime(LocalDateTime thumbnailGeneratedTime) {
        this.thumbnailGeneratedTime = thumbnailGeneratedTime;
    }

    public String getInkHash() {
        return inkHash;
    }

    public void setInkHash(String inkHash) {
        this.inkHash = inkHash;
    }

    public String getSnapshotProtoFprint() {
        return snapshotProtoFprint;
    }

    public void setSnapshotProtoFprint(String snapshotProtoFprint) {
        this.snapshotProtoFprint = snapshotProtoFprint;
    }

    @Override
    public String toString() {
        return "DrawingInfo{" +
                "drawingId='" + drawingId + '\'' +
                ", snapshotData=" + snapshotData +
                ", snapshotFingerprint='" + snapshotFingerprint + '\'' +
                ", thumbnailGeneratedTime=" + thumbnailGeneratedTime +
                ", inkHash='" + inkHash + '\'' +
                ", snapshotProtoFprint='" + snapshotProtoFprint + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DrawingInfo that)) return false;
        return Objects.equals(drawingId, that.drawingId)
                && Objects.equals(snapshotData, that.snapshotData)
                && Objects.equals(snapshotFingerprint, that.snapshotFingerprint)
                && Objects.equals(thumbnailGeneratedTime, that.thumbnailGeneratedTime)
                && Objects.equals(inkHash, that.inkHash)
                && Objects.equals(snapshotProtoFprint, that.snapshotProtoFprint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drawingId, snapshotData, snapshotFingerprint, thumbnailGeneratedTime, inkHash,
                snapshotProtoFprint);
    }
}
