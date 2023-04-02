package io.github.rukins.gkeepapi.model.gkeep.node.nodeobject;

import io.github.rukins.gkeepapi.annotation.Exclude;
import io.github.rukins.gkeepapi.model.gkeep.Timestamps;
import io.github.rukins.gkeepapi.model.gkeep.node.Background;
import io.github.rukins.gkeepapi.model.gkeep.node.Color;
import io.github.rukins.gkeepapi.model.gkeep.node.LabelId;
import io.github.rukins.gkeepapi.model.gkeep.node.NodeType;
import io.github.rukins.gkeepapi.model.gkeep.node.annotation.AnnotationsGroup;
import io.github.rukins.gkeepapi.model.gkeep.nodesettings.NodeSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListNode extends Node {
    private String title;
    private Boolean isArchived;
    private Boolean isPinned;
    private Color color;
    private String lastModifierEmail;
    private String shareState;
    private String moved;
    private Boolean xplatModel;
    private List<LabelId> labelIds = new ArrayList<>();
    private Background background;
    @Exclude
    private List<ListItemNode> listItemNodes = new ArrayList<>();
    @Exclude
    private List<BlobNode> blobNodes = new ArrayList<>();

    private ListNode() {
        setType(NodeType.LIST);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ListNode list;

        private Builder() {
            list = new ListNode();
        }

        public Builder id(String id) {
            list.setId(id);
            return this;
        }

        public Builder serverId(String serverId) {
            list.setServerId(serverId);
            return this;
        }

        public Builder parentId(String parentId) {
            list.setParentId(parentId);
            return this;
        }

        public Builder parentServerId(String parentServerId) {
            list.setParentServerId(parentServerId);
            return this;
        }

        public Builder timestamps(Timestamps timestamps) {
            list.setTimestamps(timestamps);
            return this;
        }

        public Builder sortValue(String sortValue) {
            list.setSortValue(sortValue);
            return this;
        }

        public Builder nodeSettings(NodeSettings nodeSettings) {
            list.setNodeSettings(nodeSettings);
            return this;
        }

        public Builder annotationsGroup(AnnotationsGroup annotationsGroup) {
            list.setAnnotationsGroup(annotationsGroup);
            return this;
        }

        public Builder title(String title) {
            list.setTitle(title);
            return this;
        }

        public Builder archived(Boolean archived) {
            list.setArchived(archived);
            return this;
        }

        public Builder pinned(Boolean pinned) {
            list.setPinned(pinned);
            return this;
        }

        public Builder color(Color color) {
            list.setColor(color);
            return this;
        }

        public Builder lastModifierEmail(String lastModifierEmail) {
            list.setLastModifierEmail(lastModifierEmail);
            return this;
        }

        public Builder shareState(String shareState) {
            list.setShareState(shareState);
            return this;
        }

        public Builder moved(String moved) {
            list.setMoved(moved);
            return this;
        }

        public Builder xplatModel(Boolean xplatModel) {
            list.setXplatModel(xplatModel);
            return this;
        }

        public Builder labelIds(List<LabelId> labelIds) {
            list.setLabelIds(labelIds);
            return this;
        }

        public Builder background(Background background) {
            list.setBackground(background);
            return this;
        }

        public Builder listItemNodes(List<ListItemNode> listItemNodes) {
            list.setListItemNodes(listItemNodes);
            return this;
        }

        public Builder blobNodes(List<BlobNode> blobNodes) {
            list.setBlobNodes(blobNodes);
            return this;
        }

        public ListNode build() {
            return list;
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public void setPinned(Boolean pinned) {
        isPinned = pinned;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getLastModifierEmail() {
        return lastModifierEmail;
    }

    public void setLastModifierEmail(String lastModifierEmail) {
        this.lastModifierEmail = lastModifierEmail;
    }

    public String getShareState() {
        return shareState;
    }

    public void setShareState(String shareState) {
        this.shareState = shareState;
    }

    public String getMoved() {
        return moved;
    }

    public void setMoved(String moved) {
        this.moved = moved;
    }

    public Boolean getXplatModel() {
        return xplatModel;
    }

    public void setXplatModel(Boolean xplatModel) {
        this.xplatModel = xplatModel;
    }

    public List<LabelId> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<LabelId> labelIds) {
        this.labelIds = labelIds;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public List<ListItemNode> getListItemNodes() {
        return listItemNodes;
    }

    public void setListItemNodes(List<ListItemNode> listItemNodes) {
        this.listItemNodes = listItemNodes;
    }

    public List<BlobNode> getBlobNodes() {
        return blobNodes;
    }

    public void setBlobNodes(List<BlobNode> blobNodes) {
        this.blobNodes = blobNodes;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "id='" + getId() + '\'' +
                ", type='" + getType() + '\'' +
                ", title='" + title + '\'' +
                ", isArchived=" + isArchived +
                ", isPinned=" + isPinned +
                ", color=" + color +
                ", labelIds=" + labelIds +
                ", listItemNodes=" + listItemNodes +
                ", blobNodes=" + blobNodes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListNode listNode)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(title, listNode.title)
                && Objects.equals(isArchived, listNode.isArchived)
                && Objects.equals(isPinned, listNode.isPinned)
                && color == listNode.color
                && Objects.equals(lastModifierEmail, listNode.lastModifierEmail)
                && Objects.equals(shareState, listNode.shareState)
                && Objects.equals(moved, listNode.moved)
                && Objects.equals(xplatModel, listNode.xplatModel)
                && Objects.equals(labelIds, listNode.labelIds)
                && Objects.equals(background, listNode.background)
                && Objects.equals(listItemNodes, listNode.listItemNodes)
                && Objects.equals(blobNodes, listNode.blobNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, isArchived, isPinned, color, lastModifierEmail,
                shareState, moved, xplatModel, labelIds, background, listItemNodes, blobNodes);
    }
}
