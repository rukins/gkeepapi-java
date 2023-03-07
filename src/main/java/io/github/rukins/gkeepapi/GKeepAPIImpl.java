package io.github.rukins.gkeepapi;

import io.github.rukins.gkeepapi.client.GKeepClientWrapper;
import io.github.rukins.gkeepapi.exception.BadNodeTypeException;
import io.github.rukins.gkeepapi.model.NodePair;
import io.github.rukins.gkeepapi.model.node.NodeRequest;
import io.github.rukins.gkeepapi.model.node.NodeResponse;
import io.github.rukins.gkeepapi.model.node.Timestamps;
import io.github.rukins.gkeepapi.model.node.nodeentity.LabelId;
import io.github.rukins.gkeepapi.model.node.nodeentity.Node;
import io.github.rukins.gkeepapi.model.node.nodeentity.NodeType;
import io.github.rukins.gkeepapi.model.node.userinfo.Label;
import io.github.rukins.gkeepapi.model.node.userinfo.UserInfo;
import io.github.rukins.gkeepapi.utils.IdUtils;
import io.github.rukins.gpsoauth.Auth;
import io.github.rukins.gpsoauth.exception.AuthError;
import io.github.rukins.gpsoauth.model.MasterTokenRequestParams;

import java.time.LocalDateTime;
import java.util.List;

public class GKeepAPIImpl implements GKeepAPI {
    private final GKeepClientWrapper client;

    private String currentVersion;

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    private GKeepAPIImpl(String masterToken) {
        this.client = new GKeepClientWrapper(masterToken);
    }

    public static GKeepAPI withAuthenticationToken(String authenticationToken) throws AuthError {
        Auth auth = new Auth();

        MasterTokenRequestParams masterTokenRequestParams = MasterTokenRequestParams
                .withDefaultValues()
                .token(authenticationToken)
                .build();

        return new GKeepAPIImpl(auth.getMasterToken(masterTokenRequestParams).getMasterToken());
    }

    public static GKeepAPI withMasterToken(String masterToken) {
        return new GKeepAPIImpl(masterToken);
    }

    @Override
    public NodeResponse getFullInfo() throws AuthError {
        NodeResponse nodeResponse = client.changes(NodeRequest.withDefaultValues().build());
        currentVersion = nodeResponse.getToVersion();

        return nodeResponse;
    }

    @Override
    public List<Node> getAllNodes() throws AuthError {
        return getFullInfo().getNodes();
    }

    @Override
    public NodePair getNodePairById(String noteId) throws AuthError, BadNodeTypeException {
        return getNodePairById(noteId, getAllNodes());
    }

    @Override
    public NodePair getNodePairById(String noteId, List<Node> allNodes) throws BadNodeTypeException {
        return new NodePair(
                allNodes.stream()
                        .filter(n -> noteId.equals(n.getId()) || noteId.equals(n.getParentId()))
                        .sorted()
                        .toList()
        );
    }

    @Override
    public NodePair createNodePair(String title, String text) throws AuthError, BadNodeTypeException {
        String noteId = IdUtils.generateId();
        LocalDateTime now = LocalDateTime.now();

        Node note = Node.withDefaultValuesAndType(NodeType.NOTE)
                .id(noteId)
                .title(title)
                .timestamps(
                        Timestamps.builder()
                                .created(now)
                                .trashed(Timestamps.DEFAULT_LOCALDATETIME)
                                .build()
                )
                .build();

        Node listItem = Node.withDefaultValuesAndType(NodeType.LIST_ITEM)
                .id(IdUtils.generateId())
                .parentId(noteId)
                .text(text)
                .timestamps(
                        Timestamps.builder()
                                .created(now)
                                .trashed(Timestamps.DEFAULT_LOCALDATETIME)
                                .build()
                )
                .build();

        return createNodePair(new NodePair(note, listItem));
    }

    @Override
    public NodePair createNodePair(NodePair nodePair) throws AuthError, BadNodeTypeException {
        NodeRequest nodeRequest = NodeRequest.withDefaultValues()
                .nodes(List.of(nodePair.getNote(), nodePair.getListItem()))
                .build();

        String noteId = nodePair.getNote().getId();

        // the server should return only created nodes, but sometimes it returns others for some reason,
        // so the filtering is necessary to get correct result
        return new NodePair(
                changes(nodeRequest).getNodes().stream()
                        .filter(n -> noteId.equals(n.getId()) || noteId.equals(n.getParentId()))
                        .sorted()
                        .toList()
        );
    }

    @Override
    public NodePair updateNodePair(String text, String noteId, String listItemId) throws AuthError, BadNodeTypeException {
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

        return updateNodePair(nodePair);
    }

    @Override
    public NodePair updateNodePair(String title, String text, String noteId, String listItemId) throws AuthError, BadNodeTypeException {
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

        return updateNodePair(nodePair);
    }

    @Override
    public NodePair updateNodePair(NodePair nodePair) throws AuthError, BadNodeTypeException {
        NodeRequest nodeRequest = NodeRequest.withDefaultValues()
                .nodes(List.of(nodePair.getNote(), nodePair.getListItem()))
                .build();

        String noteId = nodePair.getNote().getId();

        // the server should return only created nodes, but sometimes it returns others for some reason,
        // so the filtering is necessary to get correct result
        return new NodePair(
                changes(nodeRequest).getNodes().stream()
                        .filter(n -> noteId.equals(n.getId()) || noteId.equals(n.getParentId()))
                        .sorted()
                        .toList()
        );
    }

    @Override
    public Node updateNode(String title, String noteId) throws AuthError, BadNodeTypeException {
        return updateNode(
                Node.builder().type(NodeType.NOTE).id(noteId).title(title).build()
        );
    }

    @Override
    public Node updateNode(Node note) throws AuthError, BadNodeTypeException {
        NodePair.checkIfNoteType(note);

        NodeRequest nodeRequest = NodeRequest.withDefaultValues()
                .nodes(List.of(note))
                .build();

        List<Node> nodes = changes(nodeRequest).getNodes();

        return nodes != null && nodes.size() == 1 ? nodes.get(0) : null;
    }

    @Override
    public Node trashNode(String noteId) throws AuthError, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .timestamps(Timestamps.builder().trashed(LocalDateTime.now()).build())
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    @Override
    public Node deleteNode(String noteId) throws AuthError, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .timestamps(Timestamps.builder().deleted(LocalDateTime.now()).build())
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    @Override
    public Node restoreNode(String noteId) throws AuthError, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .timestamps(Timestamps.builder().trashed(Timestamps.DEFAULT_LOCALDATETIME).build())
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    @Override
    public Node pinNode(String noteId) throws AuthError, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .pinned(true)
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    @Override
    public Node unpinNode(String noteId) throws AuthError, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .pinned(false)
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    @Override
    public Node archiveNode(String noteId) throws AuthError, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .archived(true)
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    @Override
    public Node unarchiveNode(String noteId) throws AuthError, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .archived(false)
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    @Override
    public List<Label> getAllLabels() throws AuthError {
        NodeRequest nodeRequest = NodeRequest.withDefaultValues()
                .userInfo(new UserInfo())
                .build();

        return changes(nodeRequest).getUserInfo().getLabels();
    }

    @Override
    public Label getLabelById(String labelId) throws AuthError {
        return getLabelById(labelId, getAllLabels());
    }

    @Override
    public Label getLabelById(String labelId, List<Label> allLabels) {
        return allLabels.stream()
                .filter(l -> labelId.equals(l.getMainId()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Label> createLabel(String labelName) throws AuthError {
        Label label = Label.withDefaultValues()
                .mainId(IdUtils.generateId())
                .name(labelName)
                .build();

        return createLabel(label);
    }

    @Override
    public List<Label> createLabel(Label label) throws AuthError {
        LocalDateTime now = LocalDateTime.now();

        label.setTimestamps(
                Timestamps.builder()
                        .created(now)
                        .updated(now)
                        .build()
        );

        NodeRequest nodeRequest = NodeRequest.withDefaultValues()
                .userInfo(
                        UserInfo.builder()
                                .labels(List.of(label))
                                .build()
                )
                .build();

        return changes(nodeRequest).getUserInfo().getLabels();
    }

    @Override
    public List<Label> updateLabel(String name, String labelId) throws AuthError {
        LocalDateTime now = LocalDateTime.now();

        // the server returns 500 without adding the created and updated timestamps,
        // though updating nodes works good without it
        return updateLabel(
                Label.withDefaultValues()
                        .name(name)
                        .mainId(labelId)
                        .timestamps(Timestamps.builder().created(now).updated(now).build())
                        .build()
        );
    }

    @Override
    public List<Label> updateLabel(Label label) throws AuthError {
        NodeRequest nodeRequest = NodeRequest.withDefaultValues()
                .userInfo(
                        UserInfo.builder()
                                .labels(List.of(label))
                                .build()
                )
                .build();

        return changes(nodeRequest).getUserInfo().getLabels();
    }

    @Override
    public List<Label> deleteLabel(String labelId) throws AuthError {
        return updateLabel(
                Label.builder()
                        .timestamps(Timestamps.builder().deleted(LocalDateTime.now()).build())
                        .mainId(labelId)
                        .build()
        );
    }

    @Override
    public Node addLabelToNode(String noteId, String labelId) throws AuthError, BadNodeTypeException {
        return updateNode(
                Node.builder()
                        .labelIds(List.of(new LabelId(labelId)))
                        .type(NodeType.NOTE)
                        .id(noteId)
                        .build()
        );
    }

    @Override
    public NodeResponse changes(NodeRequest nodeRequest) throws AuthError {
        if (currentVersion == null) {
            currentVersion = client.changes(NodeRequest.withDefaultValues().build()).getToVersion();
        }
        nodeRequest.setTargetVersion(currentVersion);

        NodeResponse nodeResponse = client.changes(nodeRequest);
        currentVersion = nodeResponse.getToVersion();

        return nodeResponse;
    }
}
