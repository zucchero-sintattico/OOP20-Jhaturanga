package jhaturanga.test.validator;

import static jhaturanga.commons.validator.StringValidatorImpl.ValidationResult.CORRECT;
import static jhaturanga.commons.validator.StringValidatorImpl.ValidationResult.EMPTY;
import static jhaturanga.commons.validator.StringValidatorImpl.ValidationResult.TOO_LONG;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jhaturanga.commons.validator.FunctionConcatenator;
import jhaturanga.commons.validator.StringValidatorImpl;
import jhaturanga.commons.validator.StringValidatorImpl.ValidationResult;

class ValidatorTest {

    private FunctionConcatenator<String, ValidationResult> v;

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
