package jhaturanga.commons.sound;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public final class Sound {

    private static final String PATH_START = "sounds/";
    private static final String PATH_END = ".wav";
    private static final Map<SoundsEnum, Media> SOUNDS_CACHE;

    // TODO: a volte non va?
    static {
        SOUNDS_CACHE = new EnumMap<>(SoundsEnum.class);
        Arrays.stream(SoundsEnum.values()).forEach(e -> {
            try {
                final Media mediaSound = new Media(
                        ClassLoader.getSystemResource(PATH_START + e.getFileName() + PATH_END).toURI().toString());

                SOUNDS_CACHE.put(e, mediaSound);
            } catch (URISyntaxException exception) {
                exception.printStackTrace();
            }
        });
    }

    private Sound() {
    }

    /**
     * 
     * @param sound witch play.
     */
    public static synchronized void play(final SoundsEnum sound) {
        final MediaPlayer mediaPlayer = new MediaPlayer(SOUNDS_CACHE.get(sound));

        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
    }

}
