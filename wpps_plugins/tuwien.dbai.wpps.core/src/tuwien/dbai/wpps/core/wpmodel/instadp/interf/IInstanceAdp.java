/**
 * File name: ICoreObject.java
 * @created: Mar 17, 2011 8:06:16 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.instadp.interf;



/**
 * Root interface which corresponds to wrapped instance, which can be located
 * in the ontology or represented as a java object without relations to any ontological models.
 *
 * @created: Mar 17, 2011 8:06:16 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public interface IInstanceAdp extends ITypeCastManager {
	
	String getString();
	
//	void addMarker(String mark);
//	
//	String getMarker();
//	
//	void addGroupMarker(String mark);
//	
//	String getGroupMarker();
	
	
	
}
