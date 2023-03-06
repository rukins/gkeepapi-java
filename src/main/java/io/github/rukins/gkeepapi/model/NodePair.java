package io.github.rukins.gkeepapi.model;

import io.github.rukins.gkeepapi.model.node.nodeentity.Node;

import java.util.List;

public class NodePair {
    private Node note;

    private Node listItem;

    public NodePair(List<Node> node) {
        this.note = node.get(0);
        this.listItem = node.get(1);
    }

    public NodePair(Node note, Node listItem) {
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
}
