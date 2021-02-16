package jhaturanga.commons.validator;

import java.util.function.Function;

public interface ValidatorBuilder {

    /**
     * 
     * @return the builder
     */
    ValidatorBuilder notEmpty();

    /**
     * 
     * @param length to check
     * @return the builder
     */
    ValidatorBuilder notShortedThan(int length);

    /**
     * 
     * @param length to check
     * @return the builder
     */
    ValidatorBuilder notLongerThan(int length);

    /**
     * 
     * @param forbidden string to check if is
     * @return the builder
     */
    ValidatorBuilder forbid(String forbidden);

    /**
     * 
     * @return the Validator
     */
    Function<String, ValidatioResult> build();

    enum ValidatioResult {

        /**
         * The string is correct.
         */
        CORRECT,

        /**
         * The string is empty.
         */
        EMPTY,

        /**
         * The string is too short.
         */
        TOO_SHORT,

        /**
         * The string is too long.
         */
        TOO_LONG,

        /**
         * The string contains forbidden strings.
         */
        FORBIDDEN;

    }
}
