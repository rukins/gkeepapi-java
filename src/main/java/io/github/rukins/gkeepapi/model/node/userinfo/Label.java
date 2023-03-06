package io.github.rukins.gkeepapi.model.node.userinfo;

import io.github.rukins.gkeepapi.model.node.Timestamps;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Label {
    private String name;

    private String mainId;

    private Timestamps timestamps;

    private LocalDateTime lastMerged;

    private List<Object> mergedIds;

    private String revision;

    public static Builder builder() {
        return new Builder();
    }

    public static Builder withDefaultValues() {
        return new Builder()
                .lastMerged(Timestamps.DEFAULT_LOCALDATETIME)
                .mergedIds(new ArrayList<>())
                .revision("0");
    }

    public static class Builder {
        private final Label label;

        private Builder() {
            label = new Label();
        }

        public static Builder aLabel() {
            return new Builder();
        }

        public Builder name(String name) {
            label.setName(name);
            return this;
        }

        public Builder mainId(String mainId) {
            label.setMainId(mainId);
            return this;
        }

        public Builder timestamps(Timestamps timestamps) {
            label.setTimestamps(timestamps);
            return this;
        }

        public Builder lastMerged(LocalDateTime lastMerged) {
            label.setLastMerged(lastMerged);
            return this;
        }

        public Builder mergedIds(List<Object> mergedIds) {
            label.setMergedIds(mergedIds);
            return this;
        }

        public Builder revision(String revision) {
            label.setRevision(revision);
            return this;
        }

        public Label build() {
            return label;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public Timestamps getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Timestamps timestamps) {
        this.timestamps = timestamps;
    }

    public LocalDateTime getLastMerged() {
        return lastMerged;
    }

    public void setLastMerged(LocalDateTime lastMerged) {
        this.lastMerged = lastMerged;
    }

    public List<Object> getMergedIds() {
        return mergedIds;
    }

    public void setMergedIds(List<Object> mergedIds) {
        this.mergedIds = mergedIds;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    @Override
    public String toString() {
        return "Label{" +
                "name='" + name + '\'' +
                ", mainId='" + mainId + '\'' +
                ", timestamps=" + timestamps +
                '}';
    }
}
