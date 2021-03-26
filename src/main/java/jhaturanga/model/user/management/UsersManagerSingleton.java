package jhaturanga.model.user.management;
import jhaturanga.commons.AppConfigurations;

/**
 * This class represent the Singleton for UsersManager.
 */
public final class UsersManagerSingleton {

    private UsersManagerSingleton() {
    }

    private static class LazyHolder {
        private static final UsersManager SINGLETON = 
                new UsersManagerImpl(AppConfigurations.defaultUsersDataStorageStrategy());
    }

    public static UsersManager getInstance() {
        return LazyHolder.SINGLETON;
    }
}
