/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp;

import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalDataStructure;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 22, 2012 9:24:06 PM
 */
public interface IConvertableLogicalObject {

	<T extends ILogicalDataStructure> T makeAs(Class<T> view);
	
}
