/**
 * 
 */
package tuwien.dbai.wpps.common.callback;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 7, 2012 9:31:00 AM
 */
public interface IFunction2<T, U> {
	
	U apply(T avar1, T avar2);

}
