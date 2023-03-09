package io.github.rukins.gkeepapi;

import io.github.rukins.gkeepapi.client.GKeepClientWrapper;
import io.github.rukins.gkeepapi.model.node.NodeRequest;
import io.github.rukins.gkeepapi.model.node.NodeResponse;
import io.github.rukins.gkeepapi.model.node.nodeentity.Node;
import io.github.rukins.gkeepapi.model.node.userinfo.UserInfo;
import io.github.rukins.gkeepapi.utils.NodeUtils;
import io.github.rukins.gpsoauth.exception.AuthError;

import java.util.ArrayList;
import java.util.List;

public class GKeepAPI {
    private final GKeepClientWrapper client;

    private String currentVersion;

    public GKeepAPI(String masterToken) {
        this.client = new GKeepClientWrapper(masterToken);
    }

    public GKeepAPI(String masterToken, String version) {
        this.client = new GKeepClientWrapper(masterToken);
        this.currentVersion = version;
    }

    public NodeResponse getFullInfo() throws AuthError {
        List<Node> nodes = new ArrayList<>();
        UserInfo userInfo;

        NodeResponse fullInfo = changes();
        userInfo = fullInfo.getUserInfo();

        while (fullInfo.getTruncated()) {
            if (fullInfo.getNodes() != null) {
                nodes.addAll(fullInfo.getNodes());
            }
            fullInfo = changes();
            NodeUtils.mergeUserInfo(userInfo, fullInfo.getUserInfo());
        }

        if (fullInfo.getNodes() != null) {
            nodes.addAll(fullInfo.getNodes());
        }

        fullInfo.setNodes(nodes.stream().sorted().toList());
        fullInfo.setUserInfo(userInfo);

        return fullInfo;
    }

    public NodeResponse changes(NodeRequest nodeRequest) throws AuthError {
        nodeRequest.setTargetVersion(currentVersion);

        NodeResponse nodeResponse = client.changes(nodeRequest);
        currentVersion = nodeResponse.getToVersion();

        return nodeResponse;
    }

    public NodeResponse changes() throws AuthError {
        return changes(NodeRequest.withDefaultValues().build());
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
}
