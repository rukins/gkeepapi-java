package io.github.rukins.gkeepapi;

import io.github.rukins.gkeepapi.client.GKeepClientWrapper;
import io.github.rukins.gkeepapi.model.gkeep.NodeRequest;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.blobobject.ImageBlob;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.Node;
import io.github.rukins.gkeepapi.model.gkeep.userinfo.UserInfo;
import io.github.rukins.gkeepapi.model.image.ImageData;
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

    public NodeResponse getFullData() throws AuthError {
        List<Node> nodes = new ArrayList<>();
        UserInfo userInfo;

        NodeResponse fullData = changes();
        userInfo = fullData.getUserInfo();

        while (fullData.getTruncated()) {
            if (fullData.getNodes() != null) {
                nodes.addAll(fullData.getNodes());
            }
            fullData = changes();
            NodeUtils.mergeUserInfo(userInfo, fullData.getUserInfo());
        }

        if (fullData.getNodes() != null) {
            nodes.addAll(fullData.getNodes());
        }

        fullData.setNodes(nodes);
        fullData.setUserInfo(userInfo);

        return fullData;
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

    public ImageBlob uploadImage(byte[] imageBytes, String blobServerId, String nodeServerId) throws AuthError {
        return client.uploadImage(imageBytes, blobServerId, nodeServerId, client.getUploadId(blobServerId, nodeServerId));
    }

    public ImageData getImageData(String blobServerId, String nodeServerId) throws AuthError {
        return client.getImageData(blobServerId, nodeServerId);
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
}
