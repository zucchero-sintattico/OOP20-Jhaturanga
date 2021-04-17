package jhaturanga.commons.settings.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import jhaturanga.commons.DirectoryConfigurations;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public abstract class SettingDataStorageJson<T> implements SettingsDataStorageJsonStrategy<T> {

    /** The settings map. */
    private Map<SettingTypeEnum, String> settingsMap = new HashMap<>();

    /**
     * Date loader from json file.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void dateLoader() throws IOException {
        DirectoryConfigurations.validateSettingsDirectory();
        DirectoryConfigurations.validateSettingsDataFile();
        if (this.settingsMap != null) {
            final Gson gson = new GsonBuilder().create();
            final String json = Files.readString(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH),
                    StandardCharsets.UTF_8);
            if (!json.isEmpty()) {
                final Type typeOfHashMap = new TypeToken<Map<SettingTypeEnum, String>>() {
                }.getType();
                this.settingsMap = gson.fromJson(json, typeOfHashMap);
            }

        }

    }

    /**
     * write setting in json file.
     *
     * @param parameter you want to assign a value
     * @param value     you want to assign to the parameter
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void put(final SettingTypeEnum parameter, final String value) throws IOException {
        this.dateLoader();
        this.settingsMap.put(parameter, value);
        final Gson gson = new GsonBuilder().create();
        final String json = gson.toJson(this.settingsMap);
        Files.deleteIfExists(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH));
        Files.writeString(Path.of(DirectoryConfigurations.SETTINGS_DATA_FILE_PATH), json, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE);

    }

    /**
     * read setting by json file.
     *
     * @param parameter you want to assign a value
     * @return the value of the selected parameter
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected String getSettingValue(final SettingTypeEnum parameter) throws IOException {
        this.dateLoader();
        return this.settingsMap.get(parameter);

    }
}
