package jhaturanga.commons;

import jhaturanga.commons.datastorage.UsersDataStorageJsonStrategy;
import jhaturanga.commons.datastorage.UsersDataStorageStrategy;

/**
 * Class that contains the Application configurations.
 */
public final class AppConfigurations {

    private AppConfigurations() {
    }

    /**
     * 
     * @return the default Application Strategy to operate with users.
     * @throws IOException
     */
    public static UsersDataStorageStrategy defaultUsersDataStorageStrategy() {
        return new UsersDataStorageJsonStrategy();
    }
}
