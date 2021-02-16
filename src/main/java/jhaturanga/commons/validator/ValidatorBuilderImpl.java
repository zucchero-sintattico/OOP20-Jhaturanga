package jhaturanga.commons.validator;

import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.CORRECT;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.EMPTY;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.TOO_SHORT;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.TOO_LONG;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.FORBIDDEN;

public final class ValidatorBuilderImpl implements ValidatorBuilder {

    private final List<Function<String, ValidationResult>> rules = new ArrayList<>();
    private boolean build;

    @Override
    public ValidatorBuilder notEmpty() {
        this.rules.add(s -> s.length() > 0 ? CORRECT : EMPTY);
        return this;
    }

    @Override
    public ValidatorBuilder notShortedThan(final int length) {
        this.rules.add(s -> s.length() > length ? CORRECT : TOO_SHORT);
        return this;
    }

    @Override
    public ValidatorBuilder notLongerThan(final int length) {
        this.rules.add(s -> s.length() <= length ? CORRECT : TOO_LONG);
        return this;
    }

    @Override
    public ValidatorBuilder forbid(final String forbidden) {
        this.rules.add(s -> !s.equals(forbidden) ? CORRECT : FORBIDDEN);
        return this;
    }

    @Override
    public Function<String, ValidationResult> build() {
        if (build) {
            return null;
        }
        build = true;

        return s -> this.rules.stream()
                .map(x -> x.apply(s))
                .dropWhile(CORRECT::equals)
                .findFirst().orElse(CORRECT);
    }

}
