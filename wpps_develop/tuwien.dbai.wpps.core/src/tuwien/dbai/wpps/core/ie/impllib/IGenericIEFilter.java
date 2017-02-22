/**
 * 
 */
package tuwien.dbai.wpps.core.ie.impllib;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter.EFilterResult;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 4, 2012 3:36:19 PM
 */
public interface IGenericIEFilter<T extends IInstanceAdp> extends IFunction<T, EFilterResult> {
	
	public static enum EFilterResult{ACCEPT, REJECT, ACCEPT_STOP, REJECT_STOP};
	
}