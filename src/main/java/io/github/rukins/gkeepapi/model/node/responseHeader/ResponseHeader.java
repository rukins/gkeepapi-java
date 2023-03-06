package io.github.rukins.gkeepapi.model.node.responseHeader;

public class ResponseHeader {
    private ResponseHeader() {
    }

    private UpdateState updateState;

    private String requestId;

    public UpdateState getUpdateState() {
        return updateState;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return "ResponseHeader{" +
                "updateState=" + updateState +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
