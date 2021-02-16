package jhaturanga.commons.validator;

import java.util.function.Function;

import jhaturanga.commons.validator.ValidatorBuilder.ValidationResult;

public interface Validator extends Function<String, ValidationResult> {

}
