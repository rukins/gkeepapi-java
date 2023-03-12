package io.github.rukins.gkeepapi.model.node.nodeobject;

import io.github.rukins.gkeepapi.annotation.Exclude;
import io.github.rukins.gkeepapi.model.Timestamps;
import io.github.rukins.gkeepapi.model.node.Background;
import io.github.rukins.gkeepapi.model.node.Color;
import io.github.rukins.gkeepapi.model.node.LabelId;
import io.github.rukins.gkeepapi.model.node.NodeType;
import io.github.rukins.gkeepapi.model.node.annotation.AnnotationsGroup;
import io.github.rukins.gkeepapi.model.nodesettings.NodeSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NoteNode extends Node {
    private String title;
    private Boolean isArchived;
    private Boolean isPinned;
    private Color color;
    private String lastModifierEmail;
    private String shareState;
    private String moved;
    private Boolean xplatModel;
    private List<LabelId> labelIds;
    private Background background;
    @Exclude
    private ListItemNode listItemNode;
    @Exclude
    private List<BlobNode> blobNodes = new ArrayList<>();
    private NoteNode() {
        setType(NodeType.NOTE);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final NoteNode note;

        private Builder() {
            note = new NoteNode();
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

        public Builder title(String title) {
            note.setTitle(title);
            return this;
        }

        public Builder archived(Boolean archived) {
            note.setArchived(archived);
            return this;
        }

        public Builder pinned(Boolean pinned) {
            note.setPinned(pinned);
            return this;
        }

        public Builder color(Color color) {
            note.setColor(color);
            return this;
        }

        public Builder lastModifierEmail(String lastModifierEmail) {
            note.setLastModifierEmail(lastModifierEmail);
            return this;
        }

        public Builder shareState(String shareState) {
            note.setShareState(shareState);
            return this;
        }

        public Builder moved(String moved) {
            note.setMoved(moved);
            return this;
        }

        public Builder xplatModel(Boolean xplatModel) {
            note.setXplatModel(xplatModel);
            return this;
        }

        public Builder labelIds(List<LabelId> labelIds) {
            note.setLabelIds(labelIds);
            return this;
        }

        public Builder background(Background background) {
            note.setBackground(background);
            return this;
        }

        public Builder listItemNode(ListItemNode listItemNode) {
            note.setListItemNode(listItemNode);
            return this;
        }

        public Builder blobNodes(List<BlobNode> blobNodes) {
            note.setBlobNodes(blobNodes);
            return this;
        }

        public NoteNode build() {
            return note;
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

    public ListItemNode getListItemNode() {
        return listItemNode;
    }

    public void setListItemNode(ListItemNode listItemNode) {
        this.listItemNode = listItemNode;
    }

    public List<BlobNode> getBlobNodes() {
        return blobNodes;
    }

    public void setBlobNodes(List<BlobNode> blobNodes) {
        this.blobNodes = blobNodes;
    }

    @Override
    public String toString() {
        return "NoteNode{" +
                "id='" + getId() + '\'' +
                ", type='" + getType() + '\'' +
                ", title='" + title + '\'' +
                ", isArchived=" + isArchived +
                ", isPinned=" + isPinned +
                ", color=" + color +
                ", labelIds=" + labelIds +
                ", listItemNode=" + listItemNode +
                ", blobNodes=" + blobNodes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NoteNode noteNode)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(title, noteNode.title)
                && Objects.equals(isArchived, noteNode.isArchived)
                && Objects.equals(isPinned, noteNode.isPinned)
                && color == noteNode.color
                && Objects.equals(lastModifierEmail, noteNode.lastModifierEmail)
                && Objects.equals(shareState, noteNode.shareState)
                && Objects.equals(moved, noteNode.moved)
                && Objects.equals(xplatModel, noteNode.xplatModel)
                && Objects.equals(labelIds, noteNode.labelIds)
                && Objects.equals(background, noteNode.background)
                && Objects.equals(listItemNode, noteNode.listItemNode)
                && Objects.equals(blobNodes, noteNode.blobNodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, isArchived, isPinned, color, lastModifierEmail,
                shareState, moved, xplatModel, labelIds, background, listItemNode, blobNodes);
    }
}
