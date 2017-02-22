/**
 * 
 */
package tuwien.dbai.wpps.common.callback;

/**
 * {@linkplain #apply(Object)} should return either {@code true} or {@code false}, not null.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 18, 2012 7:48:34 PM
 */
public interface IPredicate<T> extends IFunction<T, Boolean> {

}
