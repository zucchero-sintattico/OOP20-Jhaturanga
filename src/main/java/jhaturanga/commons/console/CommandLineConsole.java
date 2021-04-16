package jhaturanga.commons.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Utility class for managing printing and reading from console with additional
 * cross-platform tools.
 */
public final class CommandLineConsole {

    private final BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in, Charset.defaultCharset()));

    /**
     * Clear the console - doesn't work on Windows.
     */
    public void clearConsole() {

        final String os = System.getProperty("os.name");

        if (!os.contains("Windows")) {
            System.out.print("\033\143");
        }

    }

    /**
     * Prompt a message and read a line.
     * 
     * @param format - the message to prompt
     * @param args   - the args for the formatted message
     * @return the line
     * @throws IOException
     */
    public String readLine(final String format, final Object... args) {

        if (System.console() != null) {
            return System.console().readLine(format, args);
        }

        this.print(String.format(format, args));

        try {
            return this.reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Read a line from console.
     * 
     * @return the line
     * @throws IOException
     */
    public String readLine() {

        if (System.console() != null) {
            return System.console().readLine();
        }

        try {
            return this.reader.readLine();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Read a password after prompting a test. If possible the password is obscured
     * when entered.
     * 
     * @param format
     * @param args
     * @return the entered password
     */
    public String readPassword(final String format, final Object... args) {
        if (System.console() != null) {
            return String.valueOf(System.console().readPassword(format, args));
        }
        return String.valueOf(readLine(format, args).toCharArray());
    }

    /**
     * Read a password from input, if supported obscure the entered sequence.
     * 
     * @return the entered password.
     */
    public String readPassword() {
        if (System.console() != null) {
            return Arrays.toString(System.console().readPassword());
        }
        final String line = this.readLine();
        return String.valueOf(line.toCharArray());
    }

    /**
     * Print a line.
     * 
     * @param format
     */
    public void println(final String format) {
        System.out.println(format);
    }

    /**
     * Print a string.
     * 
     * @param format
     */
    public void print(final String format) {
        System.out.print(format);
    }
}
