package jhaturanga.views.resume;

import jhaturanga.controllers.resume.ResumeController;
import jhaturanga.views.JavaFXView;

public interface ResumeView extends JavaFXView {

    default ResumeController getResumeController() {
        return (ResumeController) this.getController();
    }
}
