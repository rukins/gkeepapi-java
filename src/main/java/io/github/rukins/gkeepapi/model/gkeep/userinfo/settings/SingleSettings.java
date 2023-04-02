package io.github.rukins.gkeepapi.model.gkeep.userinfo.settings;

import java.util.List;

public class SingleSettings {
    private List<Setting> singleSettings;

    public SingleSettings(List<Setting> singleSettings) {
        this.singleSettings = singleSettings;
    }

    public List<Setting> getSingleSettings() {
        return singleSettings;
    }

    public void setSingleSettings(List<Setting> singleSettings) {
        this.singleSettings = singleSettings;
    }

    @Override
    public String toString() {
        return "SingleSettings{" +
                "singleSettings=" + singleSettings +
                '}';
    }
}
