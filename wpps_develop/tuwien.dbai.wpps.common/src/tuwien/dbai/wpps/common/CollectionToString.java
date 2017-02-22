/**
 * 
 */
package tuwien.dbai.wpps.common;

import java.util.Collection;
import java.util.Iterator;

import tuwien.dbai.wpps.common.callback.IFunction;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 23, 2012 11:26:50 PM
 */
public class CollectionToString<T> {

	private final IFunction<T, String> toStrF;
	private final String separator;
	
	public CollectionToString(final String separator) {
		this.toStrF = new IFunction<T, String>() {
			@Override public String apply(T avar) {
				return (avar instanceof String)?(String)avar:avar.toString();
		} } ;
		this.separator = separator;
	}
	
	public CollectionToString(final IFunction<T, String> toStrF, final String separator) {
		this.toStrF = toStrF;
		this.separator = separator;
	}
	
	@Override
	public String toString() {
		return "";
	}
	
	public String toString(Collection<? extends T> col) {
		StringBuffer sb = new StringBuffer();
		Iterator<? extends T> iter = col.iterator();
		if (iter.hasNext()) {
			sb.append(toStrF.apply(iter.next()));
			while (iter.hasNext()) {
				sb.append(separator);
				sb.append(toStrF.apply(iter.next()));
			}
		}
		return sb.toString();
	}
	
}
