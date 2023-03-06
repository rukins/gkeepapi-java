package io.github.rukins.gkeepapi.model.node;

import io.github.rukins.gkeepapi.model.node.responseHeader.ResponseHeader;
import io.github.rukins.gkeepapi.model.node.userinfo.UserInfo;
import io.github.rukins.gkeepapi.model.node.nodeentity.Node;

import java.util.List;

public class NodeResponse {
    private NodeResponse() {
    }

    private String kind;

    private String fromVersion;

    private String toVersion;

    private List<Node> nodes;

    private Boolean truncated;

    private Boolean forceFullResync;

    private ResponseHeader responseHeader;

    private UserInfo userInfo;

    public String getKind() {
        return kind;
    }

    public String getFromVersion() {
        return fromVersion;
    }

    public String getToVersion() {
        return toVersion;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Boolean getTruncated() {
        return truncated;
    }

    public Boolean getForceFullResync() {
        return forceFullResync;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    public String toString() {
        return "NodeResponse{" +
                "fromVersion='" + fromVersion + '\'' +
                ", toVersion='" + toVersion + '\'' +
                ", nodes=" + nodes +
                ", truncated=" + truncated +
                ", forceFullResync=" + forceFullResync +
                ", responseHeader=" + responseHeader +
                ", userInfo=" + userInfo +
                '}';
    }
}
