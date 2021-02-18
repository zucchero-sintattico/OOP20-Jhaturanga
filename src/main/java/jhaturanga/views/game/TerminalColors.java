package jhaturanga.views.game;

public enum TerminalColors {

    /**
     * Color Reset.
     */
    RESET("\033[0m"),

    // Regular Colors. Normal color, no bold, background color etc.

    /**
     * Black Color.
     */
    BLACK("\033[0;30m"),

    /**
     * Red Color.
     */
    RED("\033[0;31m"),

    /**
     * Green Color.
     */
    GREEN("\033[0;32m"),

    /**
     * Yellow Color.
     */
    YELLOW("\033[0;33m"),

    /**
     * Blue Color.
     */
    BLUE("\033[0;34m"),

    /**
     * Magenta Color.
     */
    MAGENTA("\033[0;35m"),

    /**
     * Cyan Color.
     */
    CYAN("\033[0;36m"),

    /**
     * White Color.
     */
    WHITE("\033[0;37m"),

    // Bold

    /**
     * Bold Black Color.
     */
    BLACK_BOLD("\033[1;30m"),

    /**
     * Bold Red Color.
     */
    RED_BOLD("\033[1;31m"),

    /**
     * Bold Green Color.
     */
    GREEN_BOLD("\033[1;32m"),

    /**
     * Bold Yellow Color.
     */
    YELLOW_BOLD("\033[1;33m"),

    /**
     * Bold Blue Color.
     */
    BLUE_BOLD("\033[1;34m"),

    /**
     * Bold Magenta Color.
     */
    MAGENTA_BOLD("\033[1;35m"),

    /**
     * Bold Cyan Color.
     */
    CYAN_BOLD("\033[1;36m"),

    /**
     * Bold White Color.
     */
    WHITE_BOLD("\033[1;37m"),

    // Underline

    /**
     * Underline Black Color.
     */
    BLACK_UNDERLINED("\033[4;30m"),

    /**
     * Underline Red Color.
     */
    RED_UNDERLINED("\033[4;31m"),

    /**
     * Underline Green Color.
     */
    GREEN_UNDERLINED("\033[4;32m"),

    /**
     * Underline Yellow Color.
     */
    YELLOW_UNDERLINED("\033[4;33m"),

    /**
     * Underline Blue Color.
     */
    BLUE_UNDERLINED("\033[4;34m"),

    /**
     * Underline Magenta Color.
     */
    MAGENTA_UNDERLINED("\033[4;35m"),

    /**
     * Underline Cyan Color.
     */
    CYAN_UNDERLINED("\033[4;36m"),

    /**
     * Underline White Color.
     */
    WHITE_UNDERLINED("\033[4;37m"), // WHITE

    // Background

    /**
     * Background Black Color.
     */
    BLACK_BACKGROUND("\033[40m"),

    /**
     * Background Red Color.
     */
    RED_BACKGROUND("\033[41m"),

    /**
     * Background Green Color.
     */
    GREEN_BACKGROUND("\033[42m"),

    /**
     * Background Yellow Color.
     */
    YELLOW_BACKGROUND("\033[43m"),

    /**
     * Background Blue Color.
     */
    BLUE_BACKGROUND("\033[44m"),

    /**
     * Background Magenta Color.
     */
    MAGENTA_BACKGROUND("\033[45m"),

    /**
     * Background Cyan Color.
     */
    CYAN_BACKGROUND("\033[46m"),

    /**
     * Background White Color.
     */
    WHITE_BACKGROUND("\033[47m"),

    // High Intensity

    /**
     * High Intensity Black Color.
     */
    BLACK_BRIGHT("\033[0;90m"),

    /**
     * High Intensity Red Color.
     */
    RED_BRIGHT("\033[0;91m"),

    /**
     * High Intensity Green Color.
     */
    GREEN_BRIGHT("\033[0;92m"),

    /**
     * High Intensity Yellow Color.
     */
    YELLOW_BRIGHT("\033[0;93m"),

    /**
     * High Intensity Blue Color.
     */
    BLUE_BRIGHT("\033[0;94m"),

    /**
     * High Intensity Magenta Color.
     */
    MAGENTA_BRIGHT("\033[0;95m"),

    /**
     * High Intensity Cyan Color.
     */
    CYAN_BRIGHT("\033[0;96m"),

    /**
     * High Intensity White Color.
     */
    WHITE_BRIGHT("\033[0;97m"),

    // Bold High Intensity

    /**
     * Bold High Intensity Black Color.
     */
    BLACK_BOLD_BRIGHT("\033[1;90m"),

    /**
     * Bold High Intensity Red Color.
     */
    RED_BOLD_BRIGHT("\033[1;91m"),

    /**
     * Bold High Intensity Green Color.
     */
    GREEN_BOLD_BRIGHT("\033[1;92m"),

    /**
     * Bold High Intensity Yellow Color.
     */
    YELLOW_BOLD_BRIGHT("\033[1;93m"),

    /**
     * Bold High Intensity Blue Color.
     */
    BLUE_BOLD_BRIGHT("\033[1;94m"),

    /**
     * Bold High Intensity Magenta Color.
     */
    MAGENTA_BOLD_BRIGHT("\033[1;95m"),

    /**
     * Bold High Intensity Cyan Color.
     */
    CYAN_BOLD_BRIGHT("\033[1;96m"),

    /**
     * Bold High Intensity White Color.
     */
    WHITE_BOLD_BRIGHT("\033[1;97m"),

    // High Intensity backgrounds

    /**
     * High Intensity Background Black Color.
     */
    BLACK_BACKGROUND_BRIGHT("\033[0;100m"),

    /**
     * High Intensity Background Red Color.
     */
    RED_BACKGROUND_BRIGHT("\033[0;101m"),

    /**
     * High Intensity Background Green Color.
     */
    GREEN_BACKGROUND_BRIGHT("\033[0;102m"),

    /**
     * High Intensity Background Yellow Color.
     */
    YELLOW_BACKGROUND_BRIGHT("\033[0;103m"),

    /**
     * High Intensity Background Blue Color.
     */
    BLUE_BACKGROUND_BRIGHT("\033[0;104m"),

    /**
     * High Intensity Background Magenta Color.
     */
    MAGENTA_BACKGROUND_BRIGHT("\033[0;105m"),

    /**
     * High Intensity Background Cyan Color.
     */
    CYAN_BACKGROUND_BRIGHT("\033[0;106m"),

    /**
     * High Intensity Background White Color.
     */
    WHITE_BACKGROUND_BRIGHT("\033[0;107m");

    private final String code;

    TerminalColors(final String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
