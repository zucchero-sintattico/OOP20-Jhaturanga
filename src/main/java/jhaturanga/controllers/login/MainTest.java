package jhaturanga.controllers.login;

public class MainTest {

    public static void main(String[] args) {
        LoginController testologin = new LoginControllerImpl();

        testologin.register("ste", "p1", "p1");
        testologin.register("fano", "p2", "p2");

    }

}
