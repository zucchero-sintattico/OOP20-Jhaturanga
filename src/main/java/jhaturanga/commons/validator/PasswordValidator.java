package jhaturanga.commons.validator;

import static jhaturanga.commons.validator.PasswordValidator.PasswordValidationResult.CORRECT;
import static jhaturanga.commons.validator.PasswordValidator.PasswordValidationResult.EMPTY_PASSWORD;
import static jhaturanga.commons.validator.PasswordValidator.PasswordValidationResult.TOO_LONG_PASSWORD;
import static jhaturanga.commons.validator.PasswordValidator.PasswordValidationResult.TOO_SHORT_PASSWORD;

import java.util.function.Function;

import jhaturanga.commons.validator.PasswordValidator.PasswordValidationResult;

public interface PasswordValidator extends Function<String, PasswordValidationResult> {

    static PasswordValidator notEmpty() {
        return s -> s.length() > 0 ? CORRECT : EMPTY_PASSWORD;
    }

    static PasswordValidator notShorterThan(final int length) {
        return s -> s.length() > length ? CORRECT : TOO_SHORT_PASSWORD;
    }

    static PasswordValidator notLongerThan(final int length) {
        return s -> s.length() < length ? CORRECT : TOO_LONG_PASSWORD;
    }

    default PasswordValidator check(PasswordValidator next) {
        return s -> {
            PasswordValidationResult result = this.apply(s);
            return result == CORRECT ? next.apply(s) : result;
        };
    }

    enum PasswordValidationResult {

        /**
         * The password is correct.
         */
        CORRECT("Correct!"),

        /**
         * The password too short.
         */
        EMPTY_PASSWORD("Please insert a non empty password"),

        /**
         * The password too short.
         */
        TOO_SHORT_PASSWORD("Please insert a longer password"),

        /**
         * The password too long.
         */
        TOO_LONG_PASSWORD("Please insert a shorter password");

        private final String message;

        PasswordValidationResult(final String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }
}
