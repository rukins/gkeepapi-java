package io.github.rukins.gkeepapi.model.image;

import io.github.rukins.gkeepapi.model.gkeep.node.blob.MimeType;

public class ImageData {
    private byte[] bytes;
    private Integer byteSize;
    private String fileName;
    private MimeType mimeType;
    private ImageSize imageSize;

    public ImageData(byte[] bytes, String fileName, MimeType mimeType) {
        this.bytes = bytes;
        this.byteSize = bytes.length;
        this.fileName = fileName;
        this.mimeType = mimeType;
    }

    public ImageData(byte[] bytes, String fileName, MimeType mimeType, ImageSize imageSize) {
        this.bytes = bytes;
        this.byteSize = bytes.length;
        this.fileName = fileName;
        this.mimeType = mimeType;
        this.imageSize = imageSize;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Integer getByteSize() {
        return byteSize;
    }

    public void setByteSize(Integer byteSize) {
        this.byteSize = byteSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MimeType getMimeType() {
        return mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public ImageSize getImageSize() {
        return imageSize;
    }

    public void setImageSize(ImageSize imageSize) {
        this.imageSize = imageSize;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "byteSize=" + byteSize +
                ", fileName='" + fileName + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", imageSize=" + imageSize +
                '}';
    }
}
