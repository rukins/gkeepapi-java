package io.github.rukins.gkeepapi.model.node.nodeentity;

import io.github.rukins.gkeepapi.model.node.Timestamps;
import io.github.rukins.gkeepapi.model.node.nodesettings.NodeSettings;

import java.util.List;

public class Node implements Comparable<Node> {
    private String kind;

    private String id;

    private String serverId;

    private String parentId;

    private String parentServerId;

    private NodeType type;

    private Timestamps timestamps;

    private String title;

    private String text;

    private List<LabelId> labelIds;

    private NodeSettings nodeSettings;

    private Boolean isArchived;

    private Boolean isPinned;

    private Color color;

    private Background background;

    private String sortValue;

    private String shareState;

    private AnnotationsGroup annotationsGroup;

    private String lastModifierEmail;

    private String moved;

    private Boolean xplatModel;

    private Boolean checked;

    private String baseVersion;

    private String superListItemId;

    private String superListItemServerId;

    public static Builder builder() {
        return new Builder();
    }

    public static Builder withDefaultValuesAndType(NodeType type) {
        return switch (type) {
            case NOTE -> new Builder()
                            .type(NodeType.NOTE)
                            .nodeSettings(NodeSettings.DEFAULT_NODE_SETTINGS)
                            .parentId("root")
                            .color(Color.DEFAULT)
                            .archived(false)
                            .pinned(false);
            case LIST_ITEM -> new Builder()
                                .type(NodeType.LIST_ITEM)
                                .checked(false)
                                .sortValue("0")
                                .superListItemId("")
                                .superListItemServerId("");
        };
    }

    public static class Builder {
        private final Node node;

        private Builder() {
            node = new Node();
        }

        public static Builder aNode() {
            return new Builder();
        }

        public Builder id(String id) {
            node.setId(id);
            return this;
        }

        public Builder serverId(String serverId) {
            node.setServerId(serverId);
            return this;
        }

        public Builder parentId(String parentId) {
            node.setParentId(parentId);
            return this;
        }

        public Builder parentServerId(String parentServerId) {
            node.setParentServerId(parentServerId);
            return this;
        }

        public Builder type(NodeType type) {
            node.setType(type);
            return this;
        }

        public Builder timestamps(Timestamps timestamps) {
            node.setTimestamps(timestamps);
            return this;
        }

        public Builder title(String title) {
            node.setTitle(title);
            return this;
        }

        public Builder text(String text) {
            node.setText(text);
            return this;
        }

        public Builder labelIds(List<LabelId> labelIds) {
            node.setLabelIds(labelIds);
            return this;
        }

        public Builder nodeSettings(NodeSettings nodeSettings) {
            node.setNodeSettings(nodeSettings);
            return this;
        }

        public Builder archived(Boolean archived) {
            node.setArchived(archived);
            return this;
        }

        public Builder pinned(Boolean pinned) {
            node.setPinned(pinned);
            return this;
        }

        public Builder color(Color color) {
            node.setColor(color);
            return this;
        }

        public Builder background(Background background) {
            node.setBackground(background);
            return this;
        }

        public Builder sortValue(String sortValue) {
            node.setSortValue(sortValue);
            return this;
        }

        public Builder shareState(String shareState) {
            node.setShareState(shareState);
            return this;
        }

        public Builder annotationsGroup(AnnotationsGroup annotationsGroup) {
            node.setAnnotationsGroup(annotationsGroup);
            return this;
        }

        public Builder lastModifierEmail(String lastModifierEmail) {
            node.setLastModifierEmail(lastModifierEmail);
            return this;
        }

        public Builder moved(String moved) {
            node.setMoved(moved);
            return this;
        }

        public Builder xplatModel(Boolean xplatModel) {
            node.setXplatModel(xplatModel);
            return this;
        }

        public Builder checked(Boolean checked) {
            node.setChecked(checked);
            return this;
        }

        public Builder baseVersion(String baseVersion) {
            node.setBaseVersion(baseVersion);
            return this;
        }

        public Builder superListItemId(String superListItemId) {
            node.setSuperListItemId(superListItemId);
            return this;
        }

        public Builder superListItemServerId(String superListItemServerId) {
            node.setSuperListItemServerId(superListItemServerId);
            return this;
        }

        public Node build() {
            return node;
        }
    }



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

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public Timestamps getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Timestamps timestamps) {
        this.timestamps = timestamps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<LabelId> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<LabelId> labelIds) {
        this.labelIds = labelIds;
    }

    public NodeSettings getNodeSettings() {
        return nodeSettings;
    }

    public void setNodeSettings(NodeSettings nodeSettings) {
        this.nodeSettings = nodeSettings;
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

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }

    public String getSortValue() {
        return sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }

    public String getShareState() {
        return shareState;
    }

    public void setShareState(String shareState) {
        this.shareState = shareState;
    }

    public AnnotationsGroup getAnnotationsGroup() {
        return annotationsGroup;
    }

    public void setAnnotationsGroup(AnnotationsGroup annotationsGroup) {
        this.annotationsGroup = annotationsGroup;
    }

    public String getLastModifierEmail() {
        return lastModifierEmail;
    }

    public void setLastModifierEmail(String lastModifierEmail) {
        this.lastModifierEmail = lastModifierEmail;
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
        return switch (this.type) {
            case NOTE -> "Note{" +
                    "id='" + id + '\'' +
                    ", type=" + type +
                    ", timestamps=" + timestamps +
                    ", title='" + title + '\'' +
                    ", labelIds=" + labelIds +
                    ", isArchived=" + isArchived +
                    ", isPinned=" + isPinned +
                    '}';
            case LIST_ITEM -> "ListItem{" +
                    "id='" + id + '\'' +
                    ", parentId='" + parentId + '\'' +
                    ", type=" + type +
                    ", timestamps=" + timestamps +
                    ", text='" + text + '\'' +
                    '}';
        };
    }

    @Override
    public int compareTo(Node node) {
        return this.getType().compareTo(node.getType());
    }
}
