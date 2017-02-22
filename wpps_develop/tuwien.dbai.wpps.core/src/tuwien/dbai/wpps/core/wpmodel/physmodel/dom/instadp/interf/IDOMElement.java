/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf;

import java.util.Map;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 8:18:55 PM
 */
public interface IDOMElement extends IDOMTraversalNode {

	String getName();
	
	Map<String, String> getAttributesMap();
	
}
