package jhaturanga.model.user.validators;

import java.util.function.Function;

/**
 * This Interface represent a concatenator of Functions that allow to chain more
 * functions together. When all functions are added, it produce a new function
 * that evaluate all the functions added.
 * 
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 */
public interface FunctionConcatenator<T, R> {

    /**
     * 
     * @param function to add in the chain
     * @return a reference to this FunctionConcatenator
     */
    FunctionConcatenator<T, R> add(Function<T, R> function);

    /**
     * 
     * @return the chained function.
     */
    Function<T, R> create();
}
