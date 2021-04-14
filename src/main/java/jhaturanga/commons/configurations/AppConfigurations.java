package jhaturanga.commons.configurations;

import jhaturanga.model.user.datastorage.UsersDataStorageJsonStrategy;
import jhaturanga.model.user.datastorage.UsersDataStorageStrategy;

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
