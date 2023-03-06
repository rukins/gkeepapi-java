package io.github.rukins.gkeepapi;

import io.github.rukins.gkeepapi.exception.BadNodeTypeException;
import io.github.rukins.gkeepapi.model.NodePair;
import io.github.rukins.gkeepapi.model.node.NodeRequest;
import io.github.rukins.gkeepapi.model.node.NodeResponse;
import io.github.rukins.gkeepapi.model.node.userinfo.Label;
import io.github.rukins.gkeepapi.model.node.nodeentity.Node;
import io.github.rukins.gpsoauth.exception.AuthError;

import java.util.List;

public interface GKeepAPI {
    NodeResponse getFullInfo() throws AuthError;

    List<Node> getAllNodes() throws AuthError;
    NodePair getNodeById(String noteId) throws AuthError, BadNodeTypeException;
    NodePair getNodeById(String noteId, List<Node> allNodes) throws BadNodeTypeException;
    NodePair createNode(String title, String text) throws AuthError, BadNodeTypeException;
    NodePair createNode(String title, String text, String sortValue) throws AuthError, BadNodeTypeException;
    NodePair createNode(Node note, Node listItem) throws AuthError, BadNodeTypeException;
    NodePair updateNode(String title, String text, String noteId) throws AuthError, BadNodeTypeException;
    NodePair updateNode(NodePair nodePair) throws AuthError, BadNodeTypeException;
    Node deleteNode(String noteId) throws AuthError, BadNodeTypeException;
    Node deleteNode(Node note) throws AuthError, BadNodeTypeException;

    List<Label> getAllLabels() throws AuthError;
    Label getLabelById(String labelId) throws AuthError;
    Label getLabelById(String labelId, List<Label> allLabels);
    List<Label> createLabel(String labelName) throws AuthError;
    List<Label> createLabel(Label label) throws AuthError;
    List<Label> updateLabel(String labelId) throws AuthError;
    List<Label> updateLabel(Label label) throws AuthError;
    List<Label> deleteLabel(String labelId) throws AuthError;
    List<Label> deleteLabel(Label label) throws AuthError;

    Node addLabelToNote(String noteId, String labelId) throws AuthError, BadNodeTypeException;
    Node addLabelToNote(Node note, String labelId) throws AuthError, BadNodeTypeException;

    NodeResponse changes(NodeRequest nodeRequest) throws AuthError;
}
