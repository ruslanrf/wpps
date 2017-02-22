/**
 * 
 */
package tuwien.dbai.wpps.core.ie.instadp.interf;

import java.util.Collection;
import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 4, 2012 2:33:04 PM
 */
public interface IGenericResults<T extends IInstanceAdp> extends IInstanceAdp, Iterable<T> {

	void addResult(T res);
	
	void addResults(Collection<? extends T> res);
	
	List<T> getResultContent();
	
	T get(int index);
	
	boolean containsResult(T res);
	
	@Deprecated
	Class<T> getResultClass();
	
	<U extends IInstanceAdp> U convertTo(Class<U> clazz);
	
	int size();

}
