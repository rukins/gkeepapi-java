package io.github.rukins.gkeepapi.model.gkeep.userinfo;

import io.github.rukins.gkeepapi.model.gkeep.Timestamps;
import io.github.rukins.gkeepapi.model.gkeep.userinfo.settings.SingleSettings;

import java.util.List;

public class UserInfo {
    private Timestamps timestamps;

    private SingleSettings settings;

    private List<String> contextualCoachmarksAcked;

    private List<Label> labels;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final UserInfo userInfo;

        private Builder() {
            userInfo = new UserInfo();
        }

        public static Builder anUserInfo() {
            return new Builder();
        }

        public Builder timestamps(Timestamps timestamps) {
            userInfo.setTimestamps(timestamps);
            return this;
        }

        public Builder settings(SingleSettings settings) {
            userInfo.setSettings(settings);
            return this;
        }

        public Builder contextualCoachmarksAcked(List<String> contextualCoachmarksAcked) {
            userInfo.setContextualCoachmarksAcked(contextualCoachmarksAcked);
            return this;
        }

        public Builder labels(List<Label> labels) {
            userInfo.setLabels(labels);
            return this;
        }

        public UserInfo build() {
            return userInfo;
        }
    }

    public Timestamps getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Timestamps timestamps) {
        this.timestamps = timestamps;
    }

    public SingleSettings getSettings() {
        return settings;
    }

    public void setSettings(SingleSettings settings) {
        this.settings = settings;
    }

    public List<String> getContextualCoachmarksAcked() {
        return contextualCoachmarksAcked;
    }

    public void setContextualCoachmarksAcked(List<String> contextualCoachmarksAcked) {
        this.contextualCoachmarksAcked = contextualCoachmarksAcked;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "timestamps=" + timestamps +
                ", settings=" + settings +
                ", contextualCoachmarksAcked=" + contextualCoachmarksAcked +
                ", labels=" + labels +
                '}';
    }
}
