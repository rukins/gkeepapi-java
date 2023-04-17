package io.github.rukins.gkeepapi.model.gkeep.node.nodeobject;

import io.github.rukins.gkeepapi.model.gkeep.Timestamps;
import io.github.rukins.gkeepapi.model.gkeep.node.annotation.AnnotationsGroup;
import io.github.rukins.gkeepapi.model.gkeep.nodesettings.NodeSettings;

public class Node extends AbstractNode {
    private Node() {
        setType(null);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Node note;

        private Builder() {
            note = new Node();
        }

        public Builder id(String id) {
            note.setId(id);
            return this;
        }

        public Builder serverId(String serverId) {
            note.setServerId(serverId);
            return this;
        }

        public Builder parentId(String parentId) {
            note.setParentId(parentId);
            return this;
        }

        public Builder parentServerId(String parentServerId) {
            note.setParentServerId(parentServerId);
            return this;
        }

        public Builder timestamps(Timestamps timestamps) {
            note.setTimestamps(timestamps);
            return this;
        }

        public Builder sortValue(String sortValue) {
            note.setSortValue(sortValue);
            return this;
        }

        public Builder nodeSettings(NodeSettings nodeSettings) {
            note.setNodeSettings(nodeSettings);
            return this;
        }

        public Builder annotationsGroup(AnnotationsGroup annotationsGroup) {
            note.setAnnotationsGroup(annotationsGroup);
            return this;
        }

        public Node build() {
            return note;
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "id='" + getId() + '\'' +
                ", type='" + getType() + '\'' +
                '}';
    }
}
