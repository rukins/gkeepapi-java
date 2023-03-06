package io.github.rukins.gkeepapi.model.node;

import io.github.rukins.gkeepapi.model.node.requestheader.RequestHeader;
import io.github.rukins.gkeepapi.model.node.userinfo.UserInfo;
import io.github.rukins.gkeepapi.model.node.nodeentity.Node;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NodeRequest {
    private LocalDateTime clientTimestamp;

    private List<Node> nodes;

    private RequestHeader requestHeader;

    private String targetVersion;

    private UserInfo userInfo;

    public static Builder builder() {
        return new Builder();
    }

    public static Builder withDefaultValues() {
        return new Builder()
                .clientTimestamp(LocalDateTime.now())
                .nodes(new ArrayList<>())
                .requestHeader(RequestHeader.withDefaultValues().build());
    }

    public static class Builder {
        private final NodeRequest nodeRequest;

        private Builder() {
            nodeRequest = new NodeRequest();
        }

        public Builder clientTimestamp(LocalDateTime clientTimestamp) {
            nodeRequest.setClientTimestamp(clientTimestamp);
            return this;
        }

        public Builder nodes(List<Node> nodes) {
            nodeRequest.setNodes(nodes);
            return this;
        }

        public Builder requestHeader(RequestHeader requestHeader) {
            nodeRequest.setRequestHeader(requestHeader);
            return this;
        }

        public Builder targetVersion(String targetVersion) {
            nodeRequest.setTargetVersion(targetVersion);
            return this;
        }

        public Builder userInfo(UserInfo userInfo) {
            nodeRequest.setUserInfo(userInfo);
            return this;
        }

        public NodeRequest build() {
            return nodeRequest;
        }
    }

    public LocalDateTime getClientTimestamp() {
        return clientTimestamp;
    }

    public void setClientTimestamp(LocalDateTime clientTimestamp) {
        this.clientTimestamp = clientTimestamp;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(RequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getTargetVersion() {
        return targetVersion;
    }

    public void setTargetVersion(String targetVersion) {
        this.targetVersion = targetVersion;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "NodeRequest{" +
                "clientTimestamp=" + clientTimestamp +
                ", nodes=" + nodes +
                ", requestHeader=" + requestHeader +
                ", targetVersion='" + targetVersion + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
