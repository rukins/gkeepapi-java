package io.github.rukins.gkeepapi;

import io.github.rukins.gkeepapi.exception.BadNodeTypeException;
import io.github.rukins.gkeepapi.exception.NoIdException;
import io.github.rukins.gkeepapi.model.NodePair;
import io.github.rukins.gkeepapi.model.node.NodeRequest;
import io.github.rukins.gkeepapi.model.node.Timestamps;
import io.github.rukins.gkeepapi.model.node.nodeentity.LabelId;
import io.github.rukins.gkeepapi.model.node.nodeentity.Node;
import io.github.rukins.gkeepapi.model.node.nodeentity.NodeType;
import io.github.rukins.gkeepapi.model.node.userinfo.Label;
import io.github.rukins.gkeepapi.model.node.userinfo.UserInfo;
import io.github.rukins.gkeepapi.utils.IdUtils;
import io.github.rukins.gkeepapi.utils.NodeUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeRequestBuilder {
    private NodeRequestBuilder() {
    }
    private final Map<String, Node> idAndNodeMap = new HashMap<>();
    private final Map<String, Label> idAndLabelMap = new HashMap<>();

    public static NodeRequestBuilder builder() {
        return new NodeRequestBuilder();
    }

    public NodeRequestBuilder createNodePair(String title, String text) throws NoIdException, BadNodeTypeException {
        String noteId = IdUtils.generateId();

        Node note = Node.withDefaultValuesAndType(NodeType.NOTE)
                .id(noteId)
                .title(title)
                .timestamps(Timestamps.builder().trashed(Timestamps.DEFAULT_LOCALDATETIME).build())
                .build();

        Node listItem = Node.withDefaultValuesAndType(NodeType.LIST_ITEM)
                .id(IdUtils.generateId())
                .parentId(noteId)
                .text(text)
                .timestamps(Timestamps.builder().trashed(Timestamps.DEFAULT_LOCALDATETIME).build())
                .build();

        return createOrUpdateNodePair(new NodePair(note, listItem));
    }

    public NodeRequestBuilder createOrUpdateNodePair(NodePair nodePair) throws NoIdException {
        if (nodePair.getNote().getId() == null || nodePair.getNote().getId().isEmpty()) {
            throw new NoIdException("Note should contain id field");
        }
        if (nodePair.getListItem().getId() == null || nodePair.getListItem().getId().isEmpty()
                || nodePair.getListItem().getParentId() == null || nodePair.getListItem().getParentId().isEmpty()) {
            throw new NoIdException("ListItem should contain id and parentId fields");
        }

        for (Node node : List.of(nodePair.getNote(), nodePair.getListItem())) {
            if (idAndNodeMap.containsKey(node.getId())) {
                idAndNodeMap.replace(node.getId(), NodeUtils.mergeNode(idAndNodeMap.get(node.getId()), node));
            } else {
                idAndNodeMap.put(node.getId(), node);
            }
        }

        return this;
    }

    public NodeRequestBuilder updateNodePair(String text,
                                             String noteId, String listItemId) throws NoIdException, BadNodeTypeException {
        NodePair nodePair = new NodePair(
                Node.builder()
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build(),
                Node.builder()
                        .type(NodeType.LIST_ITEM)
                        .text(text)
                        .id(listItemId)
                        .parentId(noteId)
                        .build()
        );

        return createOrUpdateNodePair(nodePair);
    }

    public NodeRequestBuilder updateNodePair(String title, String text,
                                             String noteId, String listItemId) throws NoIdException, BadNodeTypeException {
        NodePair nodePair = new NodePair(
                Node.builder()
                        .type(NodeType.NOTE)
                        .title(title)
                        .id(noteId)
                        .build(),
                Node.builder()
                        .type(NodeType.LIST_ITEM)
                        .text(text)
                        .id(listItemId)
                        .parentId(noteId)
                        .build()
        );

        return createOrUpdateNodePair(nodePair);
    }

    public NodeRequestBuilder updateNode(Node note) throws NoIdException, BadNodeTypeException {
        NodePair.checkIfNoteType(note);

        if (note.getId() == null || note.getId().isEmpty()) {
            throw new NoIdException("Note should contain id field");
        }

        if (idAndNodeMap.containsKey(note.getId())) {
            idAndNodeMap.replace(note.getId(), NodeUtils.mergeNode(idAndNodeMap.get(note.getId()), note));
        } else {
            idAndNodeMap.put(note.getId(), note);
        }

        return this;
    }

    public NodeRequestBuilder updateNode(String title, String noteId) throws NoIdException, BadNodeTypeException {
        return updateNode(
                Node.builder().type(NodeType.NOTE).id(noteId).title(title).build()
        );
    }

    public NodeRequestBuilder trashNode(String noteId) throws NoIdException, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .timestamps(Timestamps.builder().trashed(LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID)).build())
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    public NodeRequestBuilder deleteNode(String noteId) throws NoIdException, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .timestamps(Timestamps.builder().deleted(LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID)).build())
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    public NodeRequestBuilder restoreNode(String noteId) throws NoIdException, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .timestamps(Timestamps.builder().trashed(Timestamps.DEFAULT_LOCALDATETIME).build())
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    public NodeRequestBuilder pinNode(String noteId) throws NoIdException, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .pinned(true)
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    public NodeRequestBuilder unpinNode(String noteId) throws NoIdException, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .pinned(false)
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    public NodeRequestBuilder archiveNode(String noteId) throws NoIdException, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .archived(true)
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    public NodeRequestBuilder unarchiveNode(String noteId) throws NoIdException, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .archived(false)
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    public NodeRequestBuilder createLabel(String labelName) throws NoIdException {
        Label label = Label.withDefaultValues()
                .mainId(IdUtils.generateId())
                .name(labelName)
                .build();

        return createLabel(label);
    }

    public NodeRequestBuilder createLabel(Label label) throws NoIdException {
        return updateLabel(label);
    }

    public NodeRequestBuilder updateLabel(String name, String labelId) throws NoIdException {
        return updateLabel(
                Label.withDefaultValues()
                        .name(name)
                        .mainId(labelId)
                        .build()
        );
    }

    public NodeRequestBuilder updateLabel(Label label) throws NoIdException {
        if (label.getMainId() == null || label.getMainId().isEmpty()) {
            throw new NoIdException("The Label object should contain mainId field");
        }

        LocalDateTime now = LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID);

        // the server returns 500 without adding the created and updated timestamps,
        // though updating nodes works good without it
        label.setTimestamps(
                Timestamps.builder()
                        .created(now)
                        .updated(now)
                        .build()
        );

        if (idAndLabelMap.containsKey(label.getMainId())) {
            idAndLabelMap.replace(label.getMainId(), NodeUtils.mergeLabel(idAndLabelMap.get(label.getMainId()), label));
        } else {
            idAndLabelMap.put(label.getMainId(), label);
        }

        return this;
    }

    public NodeRequestBuilder deleteLabel(String labelId) throws NoIdException {
        return updateLabel(
                Label.builder()
                        .timestamps(Timestamps.builder().deleted(LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID)).build())
                        .mainId(labelId)
                        .build()
        );
    }

    public NodeRequestBuilder addLabelToNode(String noteId, String labelId) throws NoIdException, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .labelIds(List.of(new LabelId(labelId)))
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    public NodeRequest build() {
        return NodeRequest.withDefaultValues()
                .nodes(idAndNodeMap.values().stream().sorted().toList())
                .userInfo(
                        UserInfo.builder()
                                .labels(idAndLabelMap.values().stream().toList())
                                .build()
                )
                .build();
    }
}
