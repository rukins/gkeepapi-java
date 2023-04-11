package io.github.rukins.gkeepapi.model.gkeep.responseheader;

import java.util.List;

public class ResponseHeader {
    private UpdateState updateState;

    private String requestId;

    private List<Integer> experimentIds;

    public UpdateState getUpdateState() {
        return updateState;
    }

    public String getRequestId() {
        return requestId;
    }

    public List<Integer> getExperimentIds() {
        return experimentIds;
    }

    @Override
    public String toString() {
        return "ResponseHeader{" +
                "updateState=" + updateState +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
