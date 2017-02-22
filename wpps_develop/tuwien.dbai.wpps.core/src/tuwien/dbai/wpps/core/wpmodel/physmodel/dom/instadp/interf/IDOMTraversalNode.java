/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf;

import java.util.List;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 8:22:25 PM
 */
public interface IDOMTraversalNode extends IDOMNode {

	IDOMTraversalNode getParent();
	
	List<IDOMTraversalNode> getChildren();
	
}
