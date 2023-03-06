package io.github.rukins.gkeepapi.model.node.nodeentity;

public class LabelId {
    private String labelId;

    public LabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    @Override
    public String toString() {
        return "Label{" +
                "labelId='" + labelId + '\'' +
                '}';
    }
}
