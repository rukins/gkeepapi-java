package io.github.rukins.gkeepapi.model.node;

import io.github.rukins.gkeepapi.model.node.responseheader.ResponseHeader;
import io.github.rukins.gkeepapi.model.node.userinfo.UserInfo;
import io.github.rukins.gkeepapi.model.node.nodeentity.Node;

import java.util.List;

public class NodeResponse {
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

    public void setFromVersion(String fromVersion) {
        this.fromVersion = fromVersion;
    }

    public String getToVersion() {
        return toVersion;
    }

    public void setToVersion(String toVersion) {
        this.toVersion = toVersion;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public Boolean getTruncated() {
        return truncated;
    }

    public void setTruncated(Boolean truncated) {
        this.truncated = truncated;
    }

    public Boolean getForceFullResync() {
        return forceFullResync;
    }

    public void setForceFullResync(Boolean forceFullResync) {
        this.forceFullResync = forceFullResync;
    }

    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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
