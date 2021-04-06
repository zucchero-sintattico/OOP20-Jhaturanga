package jhaturanga.controllers.loading;

import java.io.IOException;

import jhaturanga.commons.configurations.DirectoryConfigurations;
import jhaturanga.controllers.BasicController;

public final class LoadingControllerImpl extends BasicController implements LoadingController {

    @Override
    public void load() {
        try {
            DirectoryConfigurations.validateUsersDataFile();
            DirectoryConfigurations.validateHistoryDirectory();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
