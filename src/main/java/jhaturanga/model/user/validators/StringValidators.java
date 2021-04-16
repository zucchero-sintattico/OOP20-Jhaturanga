package jhaturanga.model.user.validators;

import java.util.function.Function;

import jhaturanga.model.user.validators.StringValidatorImpl.ValidationResult;

public final class StringValidators {

    /**
     * Check on empty strings.
     */
    public static final Function<String, ValidationResult> NOT_EMPTY = (s) -> s.length() != 0 ? ValidationResult.CORRECT
            : ValidationResult.EMPTY;

    /**
     * Check on max length.
     */
    public static final Function<Integer, Function<String, ValidationResult>> SHORTER_THAN = (
            max) -> (String s) -> s.length() < max ? ValidationResult.CORRECT : ValidationResult.TOO_LONG;

    /**
     * Check on min length.
     */
    public static final Function<Integer, Function<String, ValidationResult>> LONGER_THAN = (
            min) -> (String s) -> s.length() > min ? ValidationResult.CORRECT : ValidationResult.TOO_SHORT;
    /**
     * Check that not match with another string.
     */
    public static final Function<String, Function<String, ValidationResult>> DIFFERENT_FROM = (
            different) -> (String s) -> !s.equals(different) ? ValidationResult.CORRECT : ValidationResult.FORBIDDEN;

    private StringValidators() {

    }
}
