package io.github.rukins.gkeepapi;

import io.github.rukins.gkeepapi.model.gkeep.NodeRequest;
import io.github.rukins.gkeepapi.model.gkeep.Timestamps;
import io.github.rukins.gkeepapi.model.gkeep.node.LabelId;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.*;
import io.github.rukins.gkeepapi.model.gkeep.userinfo.Label;
import io.github.rukins.gkeepapi.model.gkeep.userinfo.UserInfo;
import io.github.rukins.gkeepapi.utils.IdUtils;
import io.github.rukins.gkeepapi.utils.NodeUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeRequestBuilder {
    private NodeRequestBuilder() {
    }
    private final Map<String, AbstractNode> idAndNodeMap = new HashMap<>();
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

        noteNode.setListItemNode((ListItemNode) mergeIfExistsOrPut(noteNode.getListItemNode()));

        noteNode.setBlobNodes(
                noteNode.getBlobNodes().stream().map(n -> {
                    n.setId(IdUtils.generateId());
                    n.setParentId(noteNode.getId());
                    return (BlobNode) mergeIfExistsOrPut(n);
                }).toList()
        );

        return (NoteNode) mergeIfExistsOrPut(noteNode);
    }

    public NoteNode createOrUpdateNoteNode(NoteNode noteNode) {
        mergeIfExistsOrPut(noteNode);

        noteNode.getListItemNode().setParentId(noteNode.getId());
        noteNode.setListItemNode((ListItemNode) mergeIfExistsOrPut(noteNode.getListItemNode()));

        noteNode.setBlobNodes(
                noteNode.getBlobNodes().stream().map(n -> {
                    n.setParentId(noteNode.getId());
                    return (BlobNode) mergeIfExistsOrPut(n);
                }).toList()
        );

        return (NoteNode) mergeIfExistsOrPut(noteNode);
    }

    public NoteNode addLabelToNoteNode(NoteNode noteNode, Label label) {
        noteNode.getLabelIds().add(new LabelId(label.getMainId()));

        return (NoteNode) mergeIfExistsOrPut(noteNode);
    }

    public NoteNode pinNoteNode(NoteNode noteNode) {
        noteNode.setPinned(true);

        return (NoteNode) mergeIfExistsOrPut(noteNode);
    }

    public NoteNode unpinNoteNode(NoteNode noteNode) {
        noteNode.setPinned(false);

        return (NoteNode) mergeIfExistsOrPut(noteNode);
    }

    public NoteNode archiveNoteNode(NoteNode noteNode) {
        noteNode.setArchived(true);

        return (NoteNode) mergeIfExistsOrPut(noteNode);
    }

    public NoteNode unarchiveNoteNode(NoteNode noteNode) {
        noteNode.setArchived(false);

        return (NoteNode) mergeIfExistsOrPut(noteNode);
    }

    public ListNode createListNode(ListNode listNode) {
        String listId = IdUtils.generateId();

        listNode.setId(listId);

        listNode.setListItemNodes(
                listNode.getListItemNodes().stream().map(n -> {
                    n.setId(IdUtils.generateId());
                    n.setParentId(listNode.getId());
                    return (ListItemNode) mergeIfExistsOrPut(n);
                }).toList()
        );
        listNode.setBlobNodes(
                listNode.getBlobNodes().stream().map(n -> {
                    n.setId(IdUtils.generateId());
                    n.setParentId(listNode.getId());
                    return (BlobNode) mergeIfExistsOrPut(n);
                }).toList()
        );

        return (ListNode) mergeIfExistsOrPut(listNode);
    }

    public ListNode createOrUpdateListNode(ListNode listNode) {
        listNode.setListItemNodes(
                listNode.getListItemNodes().stream().map(n -> {
                    n.setParentId(listNode.getId());
                    return (ListItemNode) mergeIfExistsOrPut(n);
                }).toList()
        );
        listNode.setBlobNodes(
                listNode.getBlobNodes().stream().map(n -> {
                    n.setParentId(listNode.getId());
                    return (BlobNode) mergeIfExistsOrPut(n);
                }).toList()
        );

        return (ListNode) mergeIfExistsOrPut(listNode);
    }

    public ListNode addLabelToListNode(ListNode listNode, Label label) {
        listNode.getLabelIds().add(new LabelId(label.getMainId()));

        return (ListNode) mergeIfExistsOrPut(listNode);
    }

    public ListNode pinListNode(ListNode listNode) {
        listNode.setPinned(true);

        return (ListNode) mergeIfExistsOrPut(listNode);
    }

    public ListNode unpinListNode(ListNode listNode) {
        listNode.setPinned(false);

        return (ListNode) mergeIfExistsOrPut(listNode);
    }

    public ListNode archiveListNode(ListNode listNode) {
        listNode.setArchived(true);

        return (ListNode) mergeIfExistsOrPut(listNode);
    }

    public ListNode unarchiveListNode(ListNode listNode) {
        listNode.setArchived(false);

        return (ListNode) mergeIfExistsOrPut(listNode);
    }

    public AbstractNode trashNode(AbstractNode node) {
        node.getTimestamps().setTrashed(LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID));

        return mergeIfExistsOrPut(node);
    }

    public AbstractNode restoreNode(AbstractNode node) {
        node.getTimestamps().setTrashed(Timestamps.DEFAULT_LOCALDATETIME);

        return mergeIfExistsOrPut(node);
    }

    public AbstractNode deleteNode(AbstractNode node) {
        node.getTimestamps().setDeleted(LocalDateTime.now(Timestamps.DEFAULT_ZONE_ID));

        return mergeIfExistsOrPut(node);
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

        return mergeIfExistsOrPut(label);
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

    public List<AbstractNode> getCurrentNodeList() {
        return idAndNodeMap.values().stream().toList();
    }

    public List<Label> getCurrentLabelList() {
        return idAndLabelMap.values().stream().toList();
    }

    public void empty() {
        idAndNodeMap.clear();
        idAndLabelMap.clear();
    }

    private AbstractNode mergeIfExistsOrPut(AbstractNode node) {
        if (node == null) {
            return null;
        }

        if (idAndNodeMap.containsKey(node.getId())) {
            idAndNodeMap.replace(node.getId(), NodeUtils.mergeNode(idAndNodeMap.get(node.getId()), node));
        } else {
            idAndNodeMap.put(node.getId(), node);
        }

        return idAndNodeMap.get(node.getId());
    }

    private Label mergeIfExistsOrPut(Label label) {
        if (label == null) {
            return null;
        }

        if (idAndLabelMap.containsKey(label.getMainId())) {
            idAndLabelMap.replace(label.getMainId(), NodeUtils.mergeLabel(idAndLabelMap.get(label.getMainId()), label));
        } else {
            idAndLabelMap.put(label.getMainId(), label);
        }

        return idAndLabelMap.get(label.getMainId());
    }
}
