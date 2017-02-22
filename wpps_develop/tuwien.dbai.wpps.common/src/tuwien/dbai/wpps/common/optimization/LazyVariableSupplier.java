/**
 * 
 */
package tuwien.dbai.wpps.common.optimization;

import tuwien.dbai.wpps.common.callback.ISupplier;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 15, 2011 12:52:06 PM
 */
public class LazyVariableSupplier <T> implements ISupplier<T> {

	private final ISupplier<T> func;
	
	private T var = null;
	private boolean isComputed = false;
	
	public LazyVariableSupplier(ISupplier<T> func) {
		this.func = func;
	}
	
	@Override
	public T get() {
		if (!isComputed) {
			var = func.get();
			isComputed = true;
		}
		return var;
	}

}
