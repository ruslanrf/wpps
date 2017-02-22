/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf;

import java.util.List;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 22, 2012 9:32:19 PM
 */
public interface ILogicalDataStructure extends ILogicalObject {
	
//	List<ILogicalObject> getBasicElements();
	
	/**
	 * @return all elements corresponding to this one.
	 */
	List<ILogicalObject> getStructElements();
	
//	/**
//	 * TODO: Check if we really need this function.
//	 * @return list of structural elements including {@code this}.
//	 */
//	@Deprecated
//	List<ILogicalObject> getStructElementsAndThis();

}
