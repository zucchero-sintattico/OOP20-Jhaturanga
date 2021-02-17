package jhaturanga.test.validator;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jhaturanga.commons.validator.ValidatorBuilder;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.CORRECT;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.EMPTY;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.TOO_SHORT;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.TOO_LONG;
import static jhaturanga.commons.validator.ValidatorBuilder.ValidationResult.FORBIDDEN;
import jhaturanga.commons.validator.ValidatorBuilderImpl;

class ValidatorTest {

    private ValidatorBuilder vb1;
    private ValidatorBuilder vb2;

    @BeforeEach
    void setUpBeforeClass() {
        vb1 = new ValidatorBuilderImpl();
        vb2 = new ValidatorBuilderImpl();
    }

    @Test
    void emptyBuild() {
        assertSame(CORRECT, vb1.build().apply("try1"));
    }

    @Test
    void testEmpty() {
        assertSame(CORRECT, vb1.notEmpty().build().apply("try2"));
        assertSame(EMPTY, vb2.notEmpty().build().apply(""));
    }

    @Test
    void testShort() {
        assertSame(CORRECT, vb1.notShortedThan(2).build().apply("try3"));
        assertSame(TOO_SHORT, vb2.notShortedThan(2).build().apply("t"));
    }

    @Test
    void testLong() {
        assertSame(CORRECT, vb1.notLongerThan(3).build().apply("tt"));
        assertSame(TOO_LONG, vb2.notLongerThan(3).build().apply("tryy4"));
    }

    @Test
    void testForbidden() {
        assertSame(CORRECT, vb1.forbid("tryy5").build().apply("try"));
        assertSame(FORBIDDEN, vb2.forbid("tryy5").build().apply("tryy5"));
    }

    @Test
    void generalTest() {
        final int length = 5;
        final var validator = vb1.notEmpty()
                .notShortedThan(1)
                .notLongerThan(length) //5 to avoid warning
                .forbid("tryy6")
                .build();
        assertSame(CORRECT, validator.apply("try"));
        assertSame(CORRECT, validator.apply("try6"));
        assertSame(CORRECT, validator.apply("try7"));
        assertNotSame(CORRECT, validator.apply("tryy6"));
        assertNotSame(CORRECT, validator.apply("tryyyy6"));
    }

    @Test
    void testMoreBuild() {
        assertSame(CORRECT, vb1.build().apply("try8"));
        assertNull(vb1.build());
    }

}
