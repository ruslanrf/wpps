/**
 * 
 */
package tuwien.dbai.wpps.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import tuwien.dbai.wpps.common.callback.IFunction;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 12, 2013 10:23:11 AM
 */
public class CollectionStringParser<T> {

	private final IFunction<T, String> toStrF;
	private final IFunction<String, T> toColF;
	private final String separator;
	
	public CollectionStringParser(final String separator) {
		this.toStrF = new IFunction<T, String>() {
			@Override public String apply(T avar) {
				return (avar instanceof String)?(String)avar:avar.toString();
		} };
		this.toColF = new IFunction<String, T>() {
			@Override public T apply(String avar) {
				return null;
			}
		};
		this.separator = separator;
	}
	
	public CollectionStringParser(
			final IFunction<T, String> toStrF
			, final IFunction<String, T> toColF
			, final String separator) {
		this.toStrF = toStrF;
		this.toColF = toColF;
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
	
	public Collection<T> toCollection(String str) {
		Collection<T> rez = new ArrayList<T>();
		String parsedStr = str;
		while (parsedStr.length()>0) {
			int ind = parsedStr.indexOf(separator);
			String strTmp = parsedStr;
			if (ind>=0)
				strTmp = parsedStr.substring(0, ind);
			rez.add(toColF.apply(strTmp));
			
			if (ind<0)
				parsedStr = "";
			else
				parsedStr = parsedStr.substring(ind+separator.length());
		}
		return rez;
	}
	
	
	
}
