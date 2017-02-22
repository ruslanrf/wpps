/**
 * 
 */
package tuwien.dbai.wpps.mozcommon;

import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMTreeWalker;
import org.mozilla.interfaces.nsIDOMWindow;

/**
 * Class is under development.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 6, 2013 9:22:54 PM
 */
public class DOMTreesWalker {
	
	private nsIDOMWindow domWindow;
	
	private Object params;
	
	public DOMTreesWalker(nsIDOMWindow domWindow, Object params) {
		
	}
	
	public void exe() {
		
	}
	
	/**
	 * Main function which walk through all DOM-trees in recursion.
	 * @param aWalker Walked for current DOM tree.
	 */
	private void depthFirstSearch(final nsIDOMTreeWalker aWalker) {
		final nsIDOMNode currNode = aWalker.getCurrentNode();
		
		switch (currNode.getNodeType()) {
		case nsIDOMNode.ELEMENT_NODE: {
			break;
		}
		case nsIDOMNode.TEXT_NODE: {
			break;
		}
		case nsIDOMNode.CDATA_SECTION_NODE: {
			break;
		}
		case nsIDOMNode.ENTITY_REFERENCE_NODE: {
			break;
		}
		case nsIDOMNode.ENTITY_NODE: {
			break;
		}
		case nsIDOMNode.PROCESSING_INSTRUCTION_NODE: {
			break;
		}
		case nsIDOMNode.COMMENT_NODE: {
			break;
		}
		case nsIDOMNode.DOCUMENT_NODE: {
			break;
		}
		case nsIDOMNode.DOCUMENT_TYPE_NODE: {
			break;
		}
		case nsIDOMNode.DOCUMENT_FRAGMENT_NODE: {
			break;
		}
		case nsIDOMNode.NOTATION_NODE: {
			break;
		}
		}
	}
	
	public void processElementNode(nsIDOMNode currNode) {
		
	}
	
}
