package jhaturanga.model.user.validators;

import java.util.function.Function;

/**
 * This Interface represent a concatenator of Functions that allow to chain more
 * functions together. When all functions are added, it produce a new function
 * that evaluate all the functions added.
 * 
 * @param <T> the input that will be tested
 * @param <R> the result of the test
 */
public interface FunctionBuilder<T, R> {

    /**
     * 
     * @param function to add in the chain
     * @return a reference to this FunctionConcatenator object to fulfill the
     *         "Builder" pattern
     */
    FunctionBuilder<T, R> add(Function<T, R> function);

    /**
     * 
     * @return the chained function.
     */
    Function<T, R> build();
}
