package io.github.rukins.gkeepapi.model.gkeep.userinfo.settings;

import io.github.rukins.gkeepapi.model.gkeep.PlatformType;
import io.github.rukins.gkeepapi.model.gkeep.nodesettings.CheckedListItemsPolicy;
import io.github.rukins.gkeepapi.model.gkeep.nodesettings.NewListItemPlacement;

import java.util.List;

public class Setting {
    private SettingType type;

    private List<PlatformType> applicablePlatforms;

    /**
    * The field can be used only with {@code SettingType.GLOBAL_NEW_LIST_ITEM_PLACEMENT} type
    */
    private NewListItemPlacement globalNewListItemPlacementValue;

    /**
     * The field can be used only with {@code SettingType.GLOBAL_CHECKED_LIST_ITEMS_POLICY} type
     */
    private CheckedListItemsPolicy globalCheckedListItemsPolicyValue;

    /**
     * The field can be used only with {@code SettingType.LAYOUT_STYLE} type
     */
    private LayoutStyle layoutStyleValue;

    /**
     * The field can be used only with {@code SettingType.SHARING_ENABLED} type
     */
    private Boolean sharingEnabledValue;

    /**
     * The field can be used only with {@code SettingType.WEB_EMBEDS_ENABLED} type
     */
    private Boolean webEmbedsEnabledValue;

    /**
     * The field can be used only with {@code SettingType.WEB_APP_THEME} type
     */
    private WebAppTheme webAppThemeValue;

    public static Builder builder() {
        return new Builder();
    }

    public static Builder withDefaultValuesAndType(SettingType type) {
        return new Builder()
                .type(type)
                .applicablePlatforms(PlatformType.DEFAULT_APPLICABLE_PLATFORMS);
    }

    public static class Builder {
        private final Setting setting;

        private Builder() {
            setting = new Setting();
        }

        public Builder type(SettingType type) {
            setting.setType(type);
            return this;
        }

        public Builder applicablePlatforms(List<PlatformType> applicablePlatforms) {
            setting.setApplicablePlatforms(applicablePlatforms);
            return this;
        }

        public Builder globalNewListItemPlacementValue(NewListItemPlacement globalNewListItemPlacementValue) {
            setting.setGlobalNewListItemPlacementValue(globalNewListItemPlacementValue);
            return this;
        }

        public Builder globalCheckedListItemsPolicyValue(CheckedListItemsPolicy globalCheckedListItemsPolicyValue) {
            setting.setGlobalCheckedListItemsPolicyValue(globalCheckedListItemsPolicyValue);
            return this;
        }

        public Builder layoutStyleValue(LayoutStyle layoutStyleValue) {
            setting.setLayoutStyleValue(layoutStyleValue);
            return this;
        }

        public Builder sharingEnabledValue(Boolean sharingEnabledValue) {
            setting.setSharingEnabledValue(sharingEnabledValue);
            return this;
        }

        public Builder webEmbedsEnabledValue(Boolean webEmbedsEnabledValue) {
            setting.setWebEmbedsEnabledValue(webEmbedsEnabledValue);
            return this;
        }

        public Builder webAppThemeValue(WebAppTheme webAppThemeValue) {
            setting.setWebAppThemeValue(webAppThemeValue);
            return this;
        }

        public Setting build() {
            return setting;
        }
    }

    public SettingType getType() {
        return type;
    }

    public void setType(SettingType type) {
        this.type = type;
    }

    public List<PlatformType> getApplicablePlatforms() {
        return applicablePlatforms;
    }

    public void setApplicablePlatforms(List<PlatformType> applicablePlatforms) {
        this.applicablePlatforms = applicablePlatforms;
    }

    public NewListItemPlacement getGlobalNewListItemPlacementValue() {
        return globalNewListItemPlacementValue;
    }

    public void setGlobalNewListItemPlacementValue(NewListItemPlacement globalNewListItemPlacementValue) {
        this.globalNewListItemPlacementValue = globalNewListItemPlacementValue;
    }

    public CheckedListItemsPolicy getGlobalCheckedListItemsPolicyValue() {
        return globalCheckedListItemsPolicyValue;
    }

    public void setGlobalCheckedListItemsPolicyValue(CheckedListItemsPolicy globalCheckedListItemsPolicyValue) {
        this.globalCheckedListItemsPolicyValue = globalCheckedListItemsPolicyValue;
    }

    public LayoutStyle getLayoutStyleValue() {
        return layoutStyleValue;
    }

    public void setLayoutStyleValue(LayoutStyle layoutStyleValue) {
        this.layoutStyleValue = layoutStyleValue;
    }

    public Boolean getSharingEnabledValue() {
        return sharingEnabledValue;
    }

    public void setSharingEnabledValue(Boolean sharingEnabledValue) {
        this.sharingEnabledValue = sharingEnabledValue;
    }

    public Boolean getWebEmbedsEnabledValue() {
        return webEmbedsEnabledValue;
    }

    public void setWebEmbedsEnabledValue(Boolean webEmbedsEnabledValue) {
        this.webEmbedsEnabledValue = webEmbedsEnabledValue;
    }

    public WebAppTheme getWebAppThemeValue() {
        return webAppThemeValue;
    }

    public void setWebAppThemeValue(WebAppTheme webAppThemeValue) {
        this.webAppThemeValue = webAppThemeValue;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "type=" + type +
                ", applicablePlatforms=" + applicablePlatforms +
                ", globalNewListItemPlacementValue=" + globalNewListItemPlacementValue +
                ", globalCheckedListItemsPolicyValue=" + globalCheckedListItemsPolicyValue +
                ", layoutStyleValue=" + layoutStyleValue +
                ", sharingEnabledValue=" + sharingEnabledValue +
                ", webEmbedsEnabledValue=" + webEmbedsEnabledValue +
                ", webAppThemeValue=" + webAppThemeValue +
                '}';
    }
}
