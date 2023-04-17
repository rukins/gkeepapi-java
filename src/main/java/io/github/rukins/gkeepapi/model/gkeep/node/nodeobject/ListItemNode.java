package io.github.rukins.gkeepapi.model.gkeep.node.nodeobject;

import io.github.rukins.gkeepapi.model.gkeep.Timestamps;
import io.github.rukins.gkeepapi.model.gkeep.node.NodeType;
import io.github.rukins.gkeepapi.model.gkeep.node.annotation.AnnotationsGroup;
import io.github.rukins.gkeepapi.model.gkeep.nodesettings.NodeSettings;

import java.util.Objects;

public class ListItemNode extends AbstractNode {
    private String text;
    private Boolean checked;
    private String baseVersion;
    private String superListItemId;
    private String superListItemServerId;

    private ListItemNode() {
        setType(NodeType.LIST_ITEM);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ListItemNode listItem;

        private Builder() {
            listItem = new ListItemNode();
        }

        public Builder id(String id) {
            listItem.setId(id);
            return this;
        }

        public Builder serverId(String serverId) {
            listItem.setServerId(serverId);
            return this;
        }

        public Builder parentId(String parentId) {
            listItem.setParentId(parentId);
            return this;
        }

        public Builder parentServerId(String parentServerId) {
            listItem.setParentServerId(parentServerId);
            return this;
        }

        public Builder timestamps(Timestamps timestamps) {
            listItem.setTimestamps(timestamps);
            return this;
        }

        public Builder sortValue(String sortValue) {
            listItem.setSortValue(sortValue);
            return this;
        }

        public Builder nodeSettings(NodeSettings nodeSettings) {
            listItem.setNodeSettings(nodeSettings);
            return this;
        }

        public Builder annotationsGroup(AnnotationsGroup annotationsGroup) {
            listItem.setAnnotationsGroup(annotationsGroup);
            return this;
        }

        public Builder text(String text) {
            listItem.setText(text);
            return this;
        }

        public Builder checked(Boolean checked) {
            listItem.setChecked(checked);
            return this;
        }

        public Builder baseVersion(String baseVersion) {
            listItem.setBaseVersion(baseVersion);
            return this;
        }

        public Builder superListItemId(String superListItemId) {
            listItem.setSuperListItemId(superListItemId);
            return this;
        }

        public Builder superListItemServerId(String superListItemServerId) {
            listItem.setSuperListItemServerId(superListItemServerId);
            return this;
        }

        public ListItemNode build() {
            return listItem;
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getBaseVersion() {
        return baseVersion;
    }

    public void setBaseVersion(String baseVersion) {
        this.baseVersion = baseVersion;
    }

    public String getSuperListItemId() {
        return superListItemId;
    }

    public void setSuperListItemId(String superListItemId) {
        this.superListItemId = superListItemId;
    }

    public String getSuperListItemServerId() {
        return superListItemServerId;
    }

    public void setSuperListItemServerId(String superListItemServerId) {
        this.superListItemServerId = superListItemServerId;
    }

    @Override
    public String toString() {
        return "ListItemNode{" +
                "id='" + getId() + '\'' +
                ", parentId='" + getParentId() + '\'' +
                ", type='" + getType() + '\'' +
                ", text='" + text + '\'' +
                ", checked=" + checked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListItemNode that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(text, that.text)
                && Objects.equals(checked, that.checked)
                && Objects.equals(baseVersion, that.baseVersion)
                && Objects.equals(superListItemId, that.superListItemId)
                && Objects.equals(superListItemServerId, that.superListItemServerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), text, checked, baseVersion, superListItemId, superListItemServerId);
    }
}
