package jhaturanga.model.match;

import java.util.Random;

public final class MatchIdGenerator {

    private static final int MATCH_ID_LENGHT = 5;

    private MatchIdGenerator() {
    }

    public static String getNewMatchId() {
        return generateRandomString(MATCH_ID_LENGHT);
    }

    private static String generateRandomString(final int length) {
        final String digits = "1234567890";

        final StringBuilder sb = new StringBuilder();

        int i = 0;
        final Random rand = new Random();
        while (i < length) {
            sb.append(digits.charAt(rand.nextInt(digits.length())));
            i++;
        }
        return sb.toString();
    }
}
