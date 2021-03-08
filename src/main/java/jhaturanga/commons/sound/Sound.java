package jhaturanga.commons.sound;

import java.net.URISyntaxException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public final class Sound {

    private static final String PATH_START = "sounds/";
    private static final String PATH_END = ".wav";

    private Sound() {
    }

    /**
     * 
     * @param sound witch play.
     */
    public static void play(final SoundsEnum sound) {

        final Media mediaSound;
        try {
            mediaSound = new Media(
                    ClassLoader.getSystemResource(PATH_START + sound.getFileName() + PATH_END).toURI().toString());
            final MediaPlayer mediaPlayer = new MediaPlayer(mediaSound);
            mediaPlayer.play();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

}
