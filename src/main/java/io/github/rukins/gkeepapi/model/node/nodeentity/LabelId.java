package io.github.rukins.gkeepapi.model.node.nodeentity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabelId labelId1)) return false;
        return Objects.equals(labelId, labelId1.labelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labelId);
    }
}
