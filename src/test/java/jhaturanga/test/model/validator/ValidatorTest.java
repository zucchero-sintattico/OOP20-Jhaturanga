package jhaturanga.test.model.validator;

import static jhaturanga.model.user.validators.StringValidatorImpl.ValidationResult.CORRECT;
import static jhaturanga.model.user.validators.StringValidatorImpl.ValidationResult.EMPTY;
import static jhaturanga.model.user.validators.StringValidatorImpl.ValidationResult.TOO_LONG;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.model.user.validators.FunctionBuilder;
import jhaturanga.model.user.validators.StringValidatorImpl;
import jhaturanga.model.user.validators.StringValidatorImpl.ValidationResult;

class ValidatorTest {

    private FunctionBuilder<String, ValidationResult> v;

    @BeforeEach
    void initialize() {
        v = new StringValidatorImpl();
    }

    @Test
    void emptyBuild() {
        assertSame(CORRECT, v.build().apply("try1"));
    }

    @Test
    void addOne() {
        v.add(s -> s.length() == 0 ? CORRECT : EMPTY);
        assertSame(CORRECT, v.build().apply(""));
        assertSame(EMPTY, v.build().apply("try2"));
    }

    @Test
    void addTwo() {
        v.add(s -> s.length() != 0 ? CORRECT : EMPTY).add(s -> s.length() < 3 ? CORRECT : TOO_LONG);
        assertSame(CORRECT, v.build().apply("tr")); // OK
        assertSame(EMPTY, v.build().apply(""));
        assertSame(TOO_LONG, v.build().apply("tryError"));
    }

}
