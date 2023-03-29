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

    public NoteNode createNoteNode(NoteNode noteNode) {
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

        return noteNode;
    }

    public NoteNode createOrUpdateNoteNode(NoteNode noteNode) {
        mergeIfExistsOrPut(noteNode);

        noteNode.getListItemNode().setParentId(noteNode.getId());
        mergeIfExistsOrPut(noteNode.getListItemNode());

        noteNode.getBlobNodes().forEach(n -> {
            n.setParentId(noteNode.getId());
            mergeIfExistsOrPut(n);
        });

        return noteNode;
    }

    public NoteNode addLabelToNoteNode(NoteNode noteNode, Label label) {
        noteNode.setLabelIds(List.of(new LabelId(label.getMainId())));
        mergeIfExistsOrPut(noteNode);

        return noteNode;
    }

    public NoteNode pinNoteNode(NoteNode noteNode) {
        noteNode.setPinned(true);
        mergeIfExistsOrPut(noteNode);

        return noteNode;
    }

    public NoteNode unpinNoteNode(NoteNode noteNode) {
        noteNode.setPinned(false);
        mergeIfExistsOrPut(noteNode);

        return noteNode;
    }

    public NoteNode archiveNoteNode(NoteNode noteNode) {
        noteNode.setArchived(true);
        mergeIfExistsOrPut(noteNode);

        return noteNode;
    }

    public NoteNode unarchiveNoteNode(NoteNode noteNode) {
        noteNode.setArchived(false);
        mergeIfExistsOrPut(noteNode);

        return noteNode;
    }

    public ListNode createListNode(ListNode listNode) {
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

        return listNode;
    }

    public ListNode createOrUpdateListNode(ListNode listNode) {
        mergeIfExistsOrPut(listNode);

        listNode.getListItemNodes().forEach(n -> {
            n.setParentId(listNode.getId());
            mergeIfExistsOrPut(n);
        });

        listNode.getBlobNodes().forEach(n -> {
            n.setParentId(listNode.getId());
            mergeIfExistsOrPut(n);
        });

        return listNode;
    }

    public ListNode addLabelToListNode(ListNode listNode, Label label) {
        listNode.setLabelIds(List.of(new LabelId(label.getMainId())));
        mergeIfExistsOrPut(listNode);

        return listNode;
    }

    public ListNode pinListNode(ListNode listNode) {
        listNode.setPinned(true);
        mergeIfExistsOrPut(listNode);

        return listNode;
    }

    public ListNode unpinListNode(ListNode listNode) {
        listNode.setPinned(false);
        mergeIfExistsOrPut(listNode);

        return listNode;
    }

    public ListNode archiveListNode(ListNode listNode) {
        listNode.setArchived(true);
        mergeIfExistsOrPut(listNode);

        return listNode;
    }

    public ListNode unarchiveListNode(ListNode listNode) {
        listNode.setArchived(false);
        mergeIfExistsOrPut(listNode);

        return listNode;
    }

    public Node trashNode(Node node) {
        node.getTimestamps().setTrashed(LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID));

        mergeIfExistsOrPut(node);

        return node;
    }

    public Node restoreNode(Node node) {
        node.getTimestamps().setTrashed(Timestamps.DEFAULT_LOCALDATETIME);

        mergeIfExistsOrPut(node);

        return node;
    }

    public Node deleteNode(Node node) {
        node.getTimestamps().setDeleted(LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID));

        mergeIfExistsOrPut(node);

        return node;
    }

    public Label createLabel(Label label) {
        label.setMainId(IdUtils.generateId());

        return createOrUpdateLabel(label);
    }

    public Label createOrUpdateLabel(Label label) {
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

        return label;
    }

    public Label deleteLabel(Label label) {
        label.getTimestamps().setDeleted(LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID));

        return createOrUpdateLabel(label);
    }

    public NodeRequest build() {
        return NodeRequest.withDefaultValues()
                .nodes(getCurrentNodeList())
                .userInfo(
                        UserInfo.builder()
                                .labels(getCurrentLabelList())
                                .build()
                )
                .build();
    }

    public List<Node> getCurrentNodeList() {
        return idAndNodeMap.values().stream().toList();
    }

    public List<Label> getCurrentLabelList() {
        return idAndLabelMap.values().stream().toList();
    }

    public void empty() {
        idAndNodeMap.clear();
        idAndLabelMap.clear();
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
