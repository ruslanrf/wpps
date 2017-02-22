/**
 * 
 */
package tuwien.dbai.wpps.core.ie.instadp.interf;

import java.util.Collection;
import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 13, 2012 11:43:39 PM
 */
public interface IResults extends IInstanceAdp, Iterable<IInstanceAdp> {
	
	void addResult(IInstanceAdp res);
	
	void addResults(Collection<? extends IInstanceAdp> res);
	
	List<IInstanceAdp> getResultContent();
	
	IInstanceAdp get(int index);
	
	boolean containsResult(IInstanceAdp res);
	
	@Deprecated
	Class<IInstanceAdp> getResultClass();
	
	<U extends IInstanceAdp> U convertTo(Class<U> clazz);
	
	int size();
	
	/**
	 * Works if adp is rdf adapter. Only 1:1 mapping is possible.
	 * @param adp
	 */
	void mapTo(IInstanceAdp adp);

}
