package jhaturanga.views.resume;

import jhaturanga.controllers.resume.ResumeController;
import jhaturanga.views.View;

public interface ResumeView extends View {

    default ResumeController getResumeController() {
        return (ResumeController) this.getController();
    }
}
