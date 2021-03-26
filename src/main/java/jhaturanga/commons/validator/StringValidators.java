package jhaturanga.commons.validator;

import java.util.function.Function;

import jhaturanga.commons.validator.StringValidatorImpl.ValidationResult;

public final class StringValidators {

    /**
     * Validator for empty strings.
     */
    public static final Function<String, ValidationResult> NOT_EMPTY = (s) -> s.length() != 0 ? ValidationResult.CORRECT
            : ValidationResult.EMPTY;

    /**
     * 
     */
    public static final Function<Integer, Function<String, ValidationResult>> SHORTER_THAN = (
            max) -> (String s) -> s.length() < max ? ValidationResult.CORRECT : ValidationResult.TOO_LONG;

    /**
     * 
     */
    public static final Function<Integer, Function<String, ValidationResult>> LONGER_THAN = (
            min) -> (String s) -> s.length() > min ? ValidationResult.CORRECT : ValidationResult.TOO_SHORT;
    /**
     * 
     */
    public static final Function<String, Function<String, ValidationResult>> DIFFERENT_FROM = (
            different) -> (String s) -> !s.equals(different) ? ValidationResult.CORRECT : ValidationResult.FORBIDDEN;

    private StringValidators() {

    }
}
