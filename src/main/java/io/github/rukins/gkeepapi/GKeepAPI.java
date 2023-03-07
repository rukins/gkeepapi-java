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

    NodePair getNodePairById(String noteId) throws AuthError, BadNodeTypeException;
    NodePair getNodePairById(String noteId, List<Node> allNodes) throws BadNodeTypeException;

    NodePair createNodePair(String title, String text) throws AuthError, BadNodeTypeException;
    NodePair createNodePair(NodePair nodePair) throws AuthError, BadNodeTypeException;

    NodePair updateNodePair(String text, String noteId, String listItemId) throws AuthError, BadNodeTypeException;
    NodePair updateNodePair(String title, String text, String noteId, String listItemId) throws AuthError, BadNodeTypeException;
    NodePair updateNodePair(NodePair nodePair) throws AuthError, BadNodeTypeException;

    Node updateNode(String title, String noteId) throws AuthError, BadNodeTypeException;
    Node updateNode(Node note) throws AuthError, BadNodeTypeException;

    Node trashNode(String noteId) throws AuthError, BadNodeTypeException;
    Node deleteNode(String noteId) throws AuthError, BadNodeTypeException;
    Node restoreNode(String noteId) throws AuthError, BadNodeTypeException;



    List<Label> getAllLabels() throws AuthError;

    Label getLabelById(String labelId) throws AuthError;
    Label getLabelById(String labelId, List<Label> allLabels);

    List<Label> createLabel(String labelName) throws AuthError;
    List<Label> createLabel(Label label) throws AuthError;

    List<Label> updateLabel(String name, String labelId) throws AuthError;
    List<Label> updateLabel(Label label) throws AuthError;

    List<Label> deleteLabel(String labelId) throws AuthError;
    List<Label> deleteLabel(Label label) throws AuthError;



    Node addLabelToNode(String noteId, String labelId) throws AuthError, BadNodeTypeException;
    Node addLabelToNode(Node note, String labelId) throws AuthError, BadNodeTypeException;



    NodeResponse changes(NodeRequest nodeRequest) throws AuthError;
}
