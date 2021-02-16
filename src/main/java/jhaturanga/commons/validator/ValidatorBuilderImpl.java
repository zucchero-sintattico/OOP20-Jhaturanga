package jhaturanga.commons.validator;

import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;

import static jhaturanga.commons.validator.ValidatorBuilder.ValidatioResult.CORRECT;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidatioResult.EMPTY;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidatioResult.TOO_SHORT;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidatioResult.TOO_LONG;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidatioResult.FORBIDDEN;

public final class ValidatorBuilderImpl implements ValidatorBuilder {

    private final List<Function<String, ValidatioResult>> checks = new ArrayList<>();
    private boolean build;

    @Override
    public ValidatorBuilder notEmpty() {
        this.checks.add(s -> s.length() > 0 ? CORRECT : EMPTY);
        return this;
    }

    @Override
    public ValidatorBuilder notShortedThan(final int length) {
        this.checks.add(s -> s.length() > length ? CORRECT : TOO_SHORT);
        return this;
    }

    @Override
    public ValidatorBuilder notLongerThan(final int length) {
        this.checks.add(s -> s.length() <= length ? CORRECT : TOO_LONG);
        return this;
    }

    @Override
    public ValidatorBuilder forbid(final String forbidden) {
        this.checks.add(s -> s.equals(forbidden) ? CORRECT : FORBIDDEN);
        return this;
    }

    @Override
    public Function<String, ValidatioResult> build() {
        if (build) {
            return null;
        }
        build = true;
        return new Function<>() {

            @Override
            public ValidatioResult apply(final String t) {
                for (final var f : checks) {
                    final var res = f.apply(t);
                    if (res != CORRECT) {
                        return res;
                    }
                }
                return CORRECT;
            }
        };
    }

}
