package jhaturanga.commons.settings.storage;

/**
 * The Enum SettingTypeEnum.
 */
public enum SettingTypeEnum {
    /**
     * application style setting.
     */
    APPLICATION_STYLE("application_style"),
    /**
     * piece style setting.
     */
    PIECES_STYLE("piece_style"),
    /**
     * volume of sound effect.
     */
    SOUND_VOLUME("sound_volume");

    /** The setting name. */
    private final String settingName;

    /**
     * Instantiates a new setting type enum.
     *
     * @param settingName the setting name
     */
    SettingTypeEnum(final String settingName) {
        this.settingName = settingName;
    }

    /**
     * Gets the setting name.
     *
     * @return the setting name
     */
    public String getSettingName() {
        return this.settingName;
    }

}
