package io.github.rukins.gkeepapi;

import io.github.rukins.gkeepapi.model.NodeRequest;
import io.github.rukins.gkeepapi.model.Timestamps;
import io.github.rukins.gkeepapi.model.node.LabelId;
import io.github.rukins.gkeepapi.model.node.nodeobject.ListItemNode;
import io.github.rukins.gkeepapi.model.node.nodeobject.ListNode;
import io.github.rukins.gkeepapi.model.node.nodeobject.Node;
import io.github.rukins.gkeepapi.model.node.nodeobject.NoteNode;
import io.github.rukins.gkeepapi.model.userinfo.Label;
import io.github.rukins.gkeepapi.model.userinfo.UserInfo;
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

    public NodeRequestBuilder createNoteNode(NoteNode noteNode) {
        String noteId = IdUtils.generateId();

        noteNode.setId(noteId);

        if (noteNode.getListItemNode() == null) {
            noteNode.setListItemNode(
                    ListItemNode.builder()
                            .text("")
                            .build()
            );
        }
        noteNode.getListItemNode().setId(IdUtils.generateId());
        noteNode.getListItemNode().setParentId(noteId);

        noteNode.getBlobNodes().forEach(n -> {
            n.setId(IdUtils.generateId());
            n.setParentId(noteId);
            idAndNodeMap.put(n.getId(), n);
        });

        idAndNodeMap.put(noteId, noteNode);
        idAndNodeMap.put(noteNode.getListItemNode().getId(), noteNode.getListItemNode());

        return this;
    }

    public NodeRequestBuilder createOrUpdateNoteNode(NoteNode noteNode) {
        mergeIfExistsOrPut(noteNode);
        mergeIfExistsOrPut(noteNode.getListItemNode());
        noteNode.getBlobNodes().forEach(this::mergeIfExistsOrPut);

        return this;
    }

    public NodeRequestBuilder addLabelToNoteNode(NoteNode noteNode, Label label) {
        noteNode.setLabelIds(List.of(new LabelId(label.getMainId())));
        mergeIfExistsOrPut(noteNode);

        return this;
    }

    public NodeRequestBuilder pinNoteNode(NoteNode noteNode) {
        noteNode.setPinned(true);
        mergeIfExistsOrPut(noteNode);

        return this;
    }

    public NodeRequestBuilder unpinNoteNode(NoteNode noteNode) {
        noteNode.setPinned(false);
        mergeIfExistsOrPut(noteNode);

        return this;
    }

    public NodeRequestBuilder archiveNoteNode(NoteNode noteNode) {
        noteNode.setArchived(true);
        mergeIfExistsOrPut(noteNode);

        return this;
    }

    public NodeRequestBuilder unarchiveNoteNode(NoteNode noteNode) {
        noteNode.setArchived(false);
        mergeIfExistsOrPut(noteNode);

        return this;
    }

    public NodeRequestBuilder createListNode(ListNode listNode) {
        String listId = IdUtils.generateId();

        listNode.setId(listId);
        idAndNodeMap.put(listId, listNode);

        listNode.getListItemNodes().forEach(n -> {
            n.setId(IdUtils.generateId());
            n.setParentId(listId);
            idAndNodeMap.put(n.getId(), n);
        });
        listNode.getBlobNodes().forEach(n -> {
            n.setId(IdUtils.generateId());
            n.setParentId(listId);
            idAndNodeMap.put(n.getId(), n);
        });

        return this;
    }

    public NodeRequestBuilder createOrUpdateListNode(ListNode listNode) {
        mergeIfExistsOrPut(listNode);
        listNode.getListItemNodes().forEach(this::mergeIfExistsOrPut);
        listNode.getBlobNodes().forEach(this::mergeIfExistsOrPut);

        return this;
    }

    public NodeRequestBuilder addLabelToListNode(ListNode listNode, Label label) {
        listNode.setLabelIds(List.of(new LabelId(label.getMainId())));
        mergeIfExistsOrPut(listNode);

        return this;
    }

    public NodeRequestBuilder pinListNode(ListNode listNode) {
        listNode.setPinned(true);
        mergeIfExistsOrPut(listNode);

        return this;
    }

    public NodeRequestBuilder unpinListNode(ListNode listNode) {
        listNode.setPinned(false);
        mergeIfExistsOrPut(listNode);

        return this;
    }

    public NodeRequestBuilder archiveListNode(ListNode listNode) {
        listNode.setArchived(true);
        mergeIfExistsOrPut(listNode);

        return this;
    }

    public NodeRequestBuilder unarchiveListNode(ListNode listNode) {
        listNode.setArchived(false);
        mergeIfExistsOrPut(listNode);

        return this;
    }

    public NodeRequestBuilder trashNode(Node node) {
        node.getTimestamps().setTrashed(LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID));

        mergeIfExistsOrPut(node);

        return this;
    }

    public NodeRequestBuilder restoreNode(Node node) {
        node.getTimestamps().setTrashed(Timestamps.DEFAULT_LOCALDATETIME);

        mergeIfExistsOrPut(node);

        return this;
    }

    public NodeRequestBuilder deleteNode(Node node) {
        node.getTimestamps().setDeleted(LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID));

        mergeIfExistsOrPut(node);

        return this;
    }

    public NodeRequestBuilder createLabel(Label label) {
        label.setMainId(IdUtils.generateId());

        return updateLabel(label);
    }

    public NodeRequestBuilder updateLabel(Label label) {
        LocalDateTime now = LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID);

        // the server returns 500 without adding the created and updated timestamps,
        // though creating and updating nodes works well without it
        label.setTimestamps(
                Timestamps.builder()
                        .created(now)
                        .updated(now)
                        .build()
        );

        mergeIfExistsOrPut(label);

        return this;
    }

    public NodeRequestBuilder deleteLabel(Label label) {
        label.getTimestamps().setDeleted(LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID));

        return updateLabel(label);
    }

    public NodeRequest build() {
        return NodeRequest.withDefaultValues()
                .nodes(idAndNodeMap.values().stream().toList())
                .userInfo(
                        UserInfo.builder()
                                .labels(idAndLabelMap.values().stream().toList())
                                .build()
                )
                .build();
    }

    private void mergeIfExistsOrPut(Node node) {
        if (node == null) {
            return;
        }

        if (idAndNodeMap.containsKey(node.getId())) {
            idAndNodeMap.replace(node.getId(), NodeUtils.mergeNode(idAndNodeMap.get(node.getId()), node));
        } else {
            idAndNodeMap.put(node.getId(), node);
        }
    }

    private void mergeIfExistsOrPut(Label label) {
        if (label == null) {
            return;
        }

        if (idAndLabelMap.containsKey(label.getMainId())) {
            idAndLabelMap.replace(label.getMainId(), NodeUtils.mergeLabel(idAndLabelMap.get(label.getMainId()), label));
        } else {
            idAndLabelMap.put(label.getMainId(), label);
        }
    }
}
