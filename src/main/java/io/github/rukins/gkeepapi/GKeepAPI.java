package io.github.rukins.gkeepapi;

import io.github.rukins.gkeepapi.client.GKeepClientWrapper;
import io.github.rukins.gkeepapi.exception.WrongBlobDataException;
import io.github.rukins.gkeepapi.model.gkeep.NodeRequest;
import io.github.rukins.gkeepapi.model.gkeep.NodeResponse;
import io.github.rukins.gkeepapi.model.gkeep.node.blob.blobobject.ImageBlob;
import io.github.rukins.gkeepapi.model.gkeep.node.nodeobject.AbstractNode;
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

    public NodeResponse changes(NodeRequest nodeRequest) throws AuthError {
        List<AbstractNode> nodes = new ArrayList<>();

        nodeRequest.setTargetVersion(currentVersion);
        NodeResponse nodeResponse = client.changes(nodeRequest);
        currentVersion = nodeResponse.getToVersion();

        UserInfo userInfo = nodeResponse.getUserInfo();

        nodeRequest = NodeRequest.withDefaultValues().build();

        while (nodeResponse.getTruncated()) {
            if (nodeResponse.getNodes() != null) {
                nodes.addAll(nodeResponse.getNodes());
            }

            nodeRequest.setTargetVersion(currentVersion);
            nodeResponse = client.changes(nodeRequest);
            currentVersion = nodeResponse.getToVersion();

            NodeUtils.mergeUserInfo(userInfo, nodeResponse.getUserInfo());
        }

        if (nodeResponse.getNodes() != null) {
            nodes.addAll(nodeResponse.getNodes());
        }

        nodeResponse.setNodes(nodes);
        nodeResponse.setUserInfo(userInfo);

        return nodeResponse;
    }

    public NodeResponse changes() throws AuthError {
        return changes(NodeRequest.withDefaultValues().build());
    }

    public ImageBlob uploadImage(byte[] imageBytes, String blobServerId, String nodeServerId) throws AuthError {
        return client.uploadImage(imageBytes, blobServerId, nodeServerId, client.getUploadId(blobServerId, nodeServerId));
    }

    public ImageData getImageData(String blobServerId, String nodeServerId) throws AuthError, WrongBlobDataException {
        return client.getImageData(blobServerId, nodeServerId);
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }
}
