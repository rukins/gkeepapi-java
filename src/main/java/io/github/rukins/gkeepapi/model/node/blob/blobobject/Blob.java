package io.github.rukins.gkeepapi.model.node.blob.blobobject;

import com.google.gson.annotations.SerializedName;
import io.github.rukins.gkeepapi.model.node.blob.BlobType;

import java.util.Objects;

public abstract class Blob {
    private String kind;
    @SerializedName("extracted_text")
    private String extractedText;
    @SerializedName("extraction_status")
    private String extractionStatus;
    private BlobType type;

    public String getKind() {
        return kind;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }

    public String getExtractionStatus() {
        return extractionStatus;
    }

    public void setExtractionStatus(String extractionStatus) {
        this.extractionStatus = extractionStatus;
    }

    public BlobType getType() {
        return type;
    }

    protected void setType(BlobType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blob blob)) return false;
        return Objects.equals(extractedText, blob.extractedText)
                && Objects.equals(extractionStatus, blob.extractionStatus)
                && Objects.equals(type, blob.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extractedText, extractionStatus, type);
    }
}
