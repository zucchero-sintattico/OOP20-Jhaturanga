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
        // final String asciiUpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // final String asciiLowerCase = "abcdefghijklmnopqrstuvwxyz";
        final String digits = "1234567890";
        final String asciiChars = digits; // asciiUpperCase + asciiLowerCase + digits;

        final StringBuilder sb = new StringBuilder();

        int i = 0;
        final Random rand = new Random();
        while (i < length) {
            sb.append(asciiChars.charAt(rand.nextInt(asciiChars.length())));
            i++;
        }
        return sb.toString();
    }
}
