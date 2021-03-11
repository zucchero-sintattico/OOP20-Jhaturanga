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
    private static final Map<SoundsEnum, MediaPlayer> SOUNDSCACHE;

    //TODO: a volte non va?
    static {
        SOUNDSCACHE = new EnumMap<>(SoundsEnum.class);
        Arrays.stream(SoundsEnum.values()).forEach(e -> {
            try {
                final Media mediaSound = new Media(
                        ClassLoader.getSystemResource(PATH_START + e.getFileName() + PATH_END).toURI().toString());
                final MediaPlayer mediaPlayer = new MediaPlayer(mediaSound);
                SOUNDSCACHE.put(e, mediaPlayer);
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
    public static void play(final SoundsEnum sound) {
        SOUNDSCACHE.get(sound).play();
    }

}
