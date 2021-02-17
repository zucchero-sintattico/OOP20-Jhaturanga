package jhaturanga.commons.validator;

import java.util.function.Function;

/**
 * A Utility interface to create a Service for Validating strings using given rules.
 *
 */
public interface ValidatorBuilder {

    /**
     * Add a rule that requires a not empty String.
     * @return the builder
     */
    ValidatorBuilder notEmpty();

    /**
     * Add a rule that requires a longer String.
     * @param length to check
     * @return the builder
     */
    ValidatorBuilder notShortedThan(int length);

    /**
     * Add a rule that requires a shorter String.
     * @param length to check
     * @return the builder
     */
    ValidatorBuilder notLongerThan(int length);

    /**
     * Add a rule that requires that the String is different between the rule given.
     * @param forbidden string to check if is present
     * @return the builder
     */
    ValidatorBuilder forbid(String forbidden);

    /**
     * 
     * @return the Validator
     */
    Function<String, ValidationResult> build();

    /**
     *
     * The Result of the Validation.
     */
    enum ValidationResult {

        /**
         * The string is correct.
         */
        CORRECT("Correct"),

        /**
         * The string is empty.
         */
        EMPTY("Empty"),

        /**
         * The string is too short.
         */
        TOO_SHORT("Too short"),

        /**
         * The string is too long.
         */
        TOO_LONG("Too long"),

        /**
         * The string contains forbidden strings.
         */
        FORBIDDEN("Forbidden");

        private String message; 

        ValidationResult(final String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }
}
