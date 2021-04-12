package jhaturanga.controllers.loading;

import java.io.IOException;

import jhaturanga.commons.configurations.DirectoryConfigurations;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
