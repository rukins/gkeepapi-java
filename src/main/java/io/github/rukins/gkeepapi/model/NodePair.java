package io.github.rukins.gkeepapi.model;

import io.github.rukins.gkeepapi.exception.BadNodeTypeException;
import io.github.rukins.gkeepapi.model.node.nodeentity.Node;
import io.github.rukins.gkeepapi.model.node.nodeentity.NodeType;

import java.util.List;

public class NodePair {
    private Node note;

    private Node listItem;

    public NodePair(List<Node> node) throws BadNodeTypeException {
        checkIfNoteType(node.get(0));
        checkIfListItemType(node.get(1));

        this.note = node.get(0);
        this.listItem = node.get(1);
    }

    public NodePair(Node note, Node listItem) throws BadNodeTypeException {
        checkIfNoteType(note);
        checkIfListItemType(listItem);

        this.note = note;
        this.listItem = listItem;
    }

    public Node getNote() {
        return note;
    }

    public void setNote(Node note) {
        this.note = note;
    }

    public Node getListItem() {
        return listItem;
    }

    public void setListItem(Node listItem) {
        this.listItem = listItem;
    }

    @Override
    public String toString() {
        return "NodePair{" +
                "note=" + note +
                ", listItem=" + listItem +
                '}';
    }

    public static void checkIfNoteType(Node node) throws BadNodeTypeException {
        if (node.getType() != NodeType.NOTE) {
            throw new BadNodeTypeException("The node type should be NOTE");
        }
    }

    public static void checkIfListItemType(Node node) throws BadNodeTypeException {
        if (node.getType() != NodeType.LIST_ITEM) {
            throw new BadNodeTypeException("The node type should be LIST_ITEM");
        }
    }
}
