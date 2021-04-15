package jhaturanga.model.user.management;
import jhaturanga.model.user.datastorage.UsersDataStorageJsonStrategy;

/**
 * This class represent the Singleton for UsersManager.
 */
public final class UsersManagerSingleton {

    private UsersManagerSingleton() {
    }

    private static class LazyHolder {
        private static final UsersManager SINGLETON = 
                new UsersManagerImpl(new UsersDataStorageJsonStrategy());
    }

    public static UsersManager getInstance() {
        return LazyHolder.SINGLETON;
    }
}
