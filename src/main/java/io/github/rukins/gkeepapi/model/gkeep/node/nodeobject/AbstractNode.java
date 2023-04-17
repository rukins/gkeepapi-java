package io.github.rukins.gkeepapi.model.gkeep.node.nodeobject;

import io.github.rukins.gkeepapi.model.gkeep.Timestamps;
import io.github.rukins.gkeepapi.model.gkeep.node.NodeType;
import io.github.rukins.gkeepapi.model.gkeep.node.annotation.AnnotationsGroup;
import io.github.rukins.gkeepapi.model.gkeep.nodesettings.NodeSettings;

import java.util.Objects;

public abstract class AbstractNode {
    private String kind;
    private String id;
    private String serverId;
    private String parentId;
    private String parentServerId;
    private Timestamps timestamps = new Timestamps();
    private String sortValue;
    private NodeSettings nodeSettings;
    private AnnotationsGroup annotationsGroup;
    private NodeType type;

    public String getKind() {
        return kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentServerId() {
        return parentServerId;
    }

    public void setParentServerId(String parentServerId) {
        this.parentServerId = parentServerId;
    }

    public Timestamps getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Timestamps timestamps) {
        this.timestamps = timestamps;
    }

    public String getSortValue() {
        return sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }

    public NodeSettings getNodeSettings() {
        return nodeSettings;
    }

    public void setNodeSettings(NodeSettings nodeSettings) {
        this.nodeSettings = nodeSettings;
    }

    public AnnotationsGroup getAnnotationsGroup() {
        return annotationsGroup;
    }

    public void setAnnotationsGroup(AnnotationsGroup annotationsGroup) {
        this.annotationsGroup = annotationsGroup;
    }

    public NodeType getType() {
        return type;
    }

    protected void setType(NodeType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractNode node)) return false;
        return Objects.equals(id, node.id)
                && Objects.equals(serverId, node.serverId)
                && Objects.equals(parentId, node.parentId)
                && Objects.equals(parentServerId, node.parentServerId)
                && Objects.equals(timestamps, node.timestamps)
                && Objects.equals(sortValue, node.sortValue)
                && Objects.equals(nodeSettings, node.nodeSettings)
                && Objects.equals(annotationsGroup, node.annotationsGroup)
                && type == node.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serverId, parentId, parentServerId, timestamps, sortValue, nodeSettings,
                annotationsGroup, type);
    }
}
