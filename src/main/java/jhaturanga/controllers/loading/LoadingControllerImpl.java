package jhaturanga.controllers.loading;

import java.io.IOException;

import jhaturanga.commons.DirectoryConfigurations;
import jhaturanga.controllers.AbstractController;

public final class LoadingControllerImpl extends AbstractController implements LoadingController {

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
