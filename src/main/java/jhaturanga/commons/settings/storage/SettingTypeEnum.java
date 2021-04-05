package jhaturanga.commons.settings.storage;

public enum SettingTypeEnum {
    /**
     * application style setting.
     */
    APPLICATION_STYLE("application_style"),
    /**
     * piece style setting.
     */
    PIECES_STYLE("piece_style");

    private final String settingName;

    SettingTypeEnum(final String settingName) {
        this.settingName = settingName;
    }

    public String getSettingName() {
        return this.settingName;
    }

}
