package io.github.rukins.gkeepapi.model.node.nodeobject;

import io.github.rukins.gkeepapi.model.Timestamps;
import io.github.rukins.gkeepapi.model.node.blob.blobobject.Blob;
import io.github.rukins.gkeepapi.model.node.NodeType;
import io.github.rukins.gkeepapi.model.node.annotation.AnnotationsGroup;
import io.github.rukins.gkeepapi.model.nodesettings.NodeSettings;

import java.util.Objects;

public class BlobNode extends Node {
    private Blob blob;

    private BlobNode() {
        setType(NodeType.BLOB);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final BlobNode blobNode;

        private Builder() {
            blobNode = new BlobNode();
        }

        public Builder id(String id) {
            blobNode.setId(id);
            return this;
        }

        public Builder serverId(String serverId) {
            blobNode.setServerId(serverId);
            return this;
        }

        public Builder parentId(String parentId) {
            blobNode.setParentId(parentId);
            return this;
        }

        public Builder parentServerId(String parentServerId) {
            blobNode.setParentServerId(parentServerId);
            return this;
        }

        public Builder timestamps(Timestamps timestamps) {
            blobNode.setTimestamps(timestamps);
            return this;
        }

        public Builder sortValue(String sortValue) {
            blobNode.setSortValue(sortValue);
            return this;
        }

        public Builder nodeSettings(NodeSettings nodeSettings) {
            blobNode.setNodeSettings(nodeSettings);
            return this;
        }

        public Builder annotationsGroup(AnnotationsGroup annotationsGroup) {
            blobNode.setAnnotationsGroup(annotationsGroup);
            return this;
        }
        public Builder blob(Blob blob) {
            blobNode.setBlob(blob);
            return this;
        }

        public BlobNode build() {
            return blobNode;
        }
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    @Override
    public String toString() {
        return "BlobNode{" +
                "id='" + getId() + '\'' +
                ", parentId='" + getParentId() + '\'' +
                ", type='" + getType() + '\'' +
                ", blob='" + blob + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlobNode blobNode)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(blob, blobNode.blob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), blob);
    }
}
