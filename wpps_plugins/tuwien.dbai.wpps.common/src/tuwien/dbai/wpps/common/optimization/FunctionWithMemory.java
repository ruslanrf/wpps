/**
 * 
 */
package tuwien.dbai.wpps.common.optimization;

import java.util.HashMap;
import java.util.Map;

import tuwien.dbai.wpps.common.callback.IFunction;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 17, 2011 6:28:47 AM
 */
public class FunctionWithMemory <T, U> implements IFunction<T, U> {

	private final IFunction<T, U> func;
	protected final Map<T, U> map;
	
	public FunctionWithMemory(final IFunction<T, U> func) {
		this.func = func;
		map = new HashMap<T, U>();
	}
	
	public FunctionWithMemory(final IFunction<T, U> func, int N) {
		this.func = func;
		map = new HashMap<T, U>(N);
	}
	
	@Override
	public U apply(T avar) {
		U rez = map.get(avar);
		if (rez == null) {
			rez = func.apply(avar);
			map.put(avar, rez);
		}
		return rez;
	}

}
