package jhaturanga.model.idgenerator;

import java.util.UUID;

public final class MatchIdGenerator {

    private MatchIdGenerator() {
    }

    public static String getNewMatchId() {
        return UUID.randomUUID().toString();
    }
}
