/**
 * 
 */
package tuwien.dbai.wpps.core.methods.segmentation.vips;

import java.util.Iterator;
import java.util.List;

import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.common.html.EHtmlElementConstants;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMText;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMTraversalNode;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 27, 2012 4:27:19 PM
 */
public class VIPSLib {
	
	/**
	 * Inline node: the DOM node with inline text HTML tags, which affect the appearance
	 * of text and can be applied to a string of characters without introducing line break.
	 * Such tags include <B>, <BIG>, <EM>, <FONT>, <I>, <STRONG>, <U>, etc.
	 * 
	 * @param el
	 * @return
	 */
	public final boolean inlineNode(IDOMTraversalNode el) {
		return el.canAs(IDOMElement.class)
				&& inlineTag(el.as(IDOMElement.class).getName());
	}
	
	public final boolean inlineTag(String tag) {
		return
//				EHtmlElementConstants.A.equals(tag)
				EHtmlElementConstants.B.equals(tag)
				|| EHtmlElementConstants.BIG.equals(tag)
				|| EHtmlElementConstants.EM.equals(tag)
				|| EHtmlElementConstants.FONT.equals(tag)
				|| EHtmlElementConstants.I.equals(tag)
				|| EHtmlElementConstants.STRONG.equals(tag)
				|| EHtmlElementConstants.U.equals(tag)
				|| EHtmlElementConstants.SUB.equals(tag)
				|| EHtmlElementConstants.SUP.equals(tag)
//				|| EHtmlElementConstants.SPAN.equals(tag)
//				|| EHtmlElementConstants.P.equals(tag)
//				|| EHtmlElementConstants.PRE.equals(tag)
//				|| EHtmlElementConstants.Q.equals(tag)
//				|| EHtmlElementConstants.DT.equals(tag)
//				|| EHtmlElementConstants.LABEL.equals(tag)
//				|| EHtmlElementConstants.LEGEND.equals(tag)
//				|| EHtmlElementConstants.CAPTION.equals(tag)
				;
	}
	
	/**
	 * Line-break Node: the node with tag other than inline text tags.
	 * @param el
	 * @return
	 */
	public final boolean lineBreakNode(IDOMTraversalNode el) {
		return el.canAs(IDOMElement.class) && !inlineNode(el);
	}
	
	/**
	 * Valid node: a node that can be seen through the browser.
	 * The node’s width and height are not equal to zero. (For
	 * @param el
	 * @return
	 */
	public final boolean validNode(IDOMTraversalNode el) {
		IQntBlock qntBlock = el.as(IQntBlock.class);
		return Rectangle2DUtils.area(qntBlock.getArea())>0;
	}
	
	/**
	 * Text node: the DOM node corresponding to free text, which does not have an html tag.
	 * @param node
	 * @return
	 */
	public final boolean textNode(IDOMTraversalNode node) {
		return node.canAs(IDOMText.class);
	}
	
	
	/**
	 * Virtual text node (recursive definition):
	 * Inline node with only text node children is a virtual text node.
	 * Inline node with only text node and virtual text node children is a virtual text node.
	 * @param node
	 * @return
	 */
	public final boolean virtualTextNode(IDOMTraversalNode node) {
		if (inlineNode(node)) {
			List<IDOMTraversalNode> chList = node.getChildren();
			if (chList.size() == 0) return false;
				Iterator<IDOMTraversalNode> iter = chList.iterator();
				while (iter.hasNext()) {
					IDOMTraversalNode a = iter.next();
					if (!textNode(a) && !virtualTextNode(a))
						return false;
				}
				return true;
		}
		else
			return false;
	}
	
	
	// =============
	// === RULES ===
	// =============
	
	
	
//	/**
//	 * If the DOM node is the root node of the sub-DOM tree (corresponding to the block),
//	 * and there is only one sub DOM tree corresponding to this block, divide this node.
//	 */
//	public static final boolean rule3(IDOMTraversalNode node, Block subWP, Block parentSubWP) {
//		return (node.equals(subWP.getRoot()) && parentSubWP.getChildBlocksList().size() == 1);
//	}
	
}
