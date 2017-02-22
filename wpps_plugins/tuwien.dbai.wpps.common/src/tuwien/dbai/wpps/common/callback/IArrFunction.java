/**
 * 
 */
package tuwien.dbai.wpps.common.callback;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 22, 2011 12:00:44 AM
 */
public interface IArrFunction<T, U> {

	U apply(T...avars );
	
}
