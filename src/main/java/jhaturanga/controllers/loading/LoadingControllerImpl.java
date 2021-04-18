package jhaturanga.controllers.loading;

import java.io.IOException;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.controllers.BasicController;

/**
 * Basic implementation for the LoadingController.
 * 
 */
public final class LoadingControllerImpl extends BasicController implements LoadingController {

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() {
        try {
            DirectoryConfigurations.validateUsersDataFile();
            DirectoryConfigurations.validateHistoryDirectory();
            DirectoryConfigurations.validateResourcesDirectory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
