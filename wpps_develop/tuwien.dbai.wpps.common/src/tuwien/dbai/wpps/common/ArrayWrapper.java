/**
 * 
 */
package tuwien.dbai.wpps.common;

import java.util.Arrays;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 23, 2012 10:38:03 AM
 */
public class ArrayWrapper<T> {
	
	private final T[] content;
	
//	public ArrayWrapper(final T[] content) {
//		this.content = content;
//	}
	
	public ArrayWrapper(final T... content) {
		this.content = content;
	}
	
	public T[] getContent() {
		return content;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object o) {
		if (o instanceof ArrayWrapper)
			return Arrays.equals(content, ((ArrayWrapper)o).content);
		return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(content);
	}

}
