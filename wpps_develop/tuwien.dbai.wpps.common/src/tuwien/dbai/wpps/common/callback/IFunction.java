/**
 * 
 */
package tuwien.dbai.wpps.common.callback;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 28, 2011 5:36:07 PM
 */
public interface IFunction<T, U> {

	U apply(T avar);

}
