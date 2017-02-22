/**
 * 
 */
package tuwien.dbai.wpps.core.methods.segmentation.vips;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.common.html.EHtmlElementConstants;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMTraversalNode;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IPlainVisualObject;

import com.google.common.base.Preconditions;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 27, 2012 6:54:19 PM
 */
public class Rules {
	
	private final int sizeThreshold;
	private final int maxSize;
	
	public Rules(int sizeThreshold, int maxSize) {
		this.sizeThreshold = sizeThreshold;
		this.maxSize = maxSize;
	}
	
	private VIPSLib vipsLib = new VIPSLib();
	
	public class IsDividable {
		public IsDividable() {
		}
		private IRule firstAcceptedRule;
		public IRule getFirstAcceptedRule() {
			return firstAcceptedRule;
		}
		
		public boolean check() {
			boolean checkRez = false;
			IRule currRule = null;
			for (int i=1; i<12 && !checkRez; i++) {
				currRule = getRule(i);
				checkRez = currRule.check();
			}
			if (checkRez)
				firstAcceptedRule = currRule;
			return checkRez;
		}
	}
	
	private IRule getRule(int id) {
//		switch (id) {
//		case 1:
//			return new Rule1();
//		case 2:
//			return new Rule2();
//		case 3:
//			return new Rule3();
//		case 4:
//			return new Rule4();
//		case 5:
//			return new Rule5();
//		case 6:
//			return new Rule6();
//		case 7:
//			return new Rule7();
//		case 8:
//			return new Rule8();
//		case 9:
//			return new Rule9();
//		case 10:
//			return new Rule10();
//		case 11:
//			return new Rule11();
//		case 12:
//			return new Rule12();
//		}
		throw new UnknownValueFromPredefinedList(id);
	}
	
	public interface IRule {
		
		int getID();
		
		boolean check();

	}
	
//	public static final boolean rule1(IDOMTraversalNode node) {
//		if (!textNode(node)) {
//				Iterator<IDOMTraversalNode> iter = node.getChildren().iterator();
//				while (iter.hasNext()) {
//					if (validNode(iter.next()))
//						return false;
//				}
//				return true;
//		}
//		else return false;
//	}
	/**
	 * If the DOM node is not a text node and it has no valid children,
	 * then this node cannot be divided and will be cut.
	 */
	public class Rule1 implements IRule {
		@Override public int getID() {return 1;}
		private IDOMTraversalNode node;
		public Rule1(IDOMTraversalNode node) {
			this.node = node;
		}
		@Override public boolean check() {
			if (!vipsLib.textNode(node)) {
				Iterator<IDOMTraversalNode> iter = node.getChildren().iterator();
				while (iter.hasNext()) {
					if (vipsLib.validNode(iter.next()))
						return false;
				}
				return true;
			}
			else return false;
		}
	}
	
//	public static final boolean rule2(IDOMTraversalNode node) {
//		Iterator<IDOMTraversalNode> iter = node.getChildren().iterator();
//		IDOMTraversalNode validCh = null;
//		int validChNum = 0;
//		while (validChNum<2 && iter.hasNext()) {
//			IDOMTraversalNode tmpCh = iter.next();
//			if (validNode(tmpCh)) {
//				validCh = tmpCh;
//				validChNum++;
//			}
//		}
//		if (validChNum == 1 && !textNode(validCh))
//				return true;
//		else return false;
//	}
	/**
	 * If the DOM node has only one valid child and the child is not a text node,
	 * then divide this node.
	 */
	public class Rule2 implements IRule {
		@Override public int getID() {return 2;}
		private IDOMTraversalNode node;
		public Rule2(IDOMTraversalNode node) {
			this.node = node;
		}
		@Override public boolean check() {
			Iterator<IDOMTraversalNode> iter = node.getChildren().iterator();
			IDOMTraversalNode validCh = null;
			int validChNum = 0;
			while (validChNum<2 && iter.hasNext()) {
				IDOMTraversalNode tmpCh = iter.next();
				if (vipsLib.validNode(tmpCh)) {
					validCh = tmpCh;
					validChNum++;
				}
			}
			if (validChNum == 1 && !vipsLib.textNode(validCh))
					return true;
			else return false;
		}
	}
	
	/**
	 * If the DOM node is the root node of the sub-DOM tree (corresponding to the block),
	 * and there is only one sub DOM tree corresponding to this block, divide this node.
	 */
	public class Rule3 implements IRule {
		@Override public int getID() {return 3;}
		private IDOMTraversalNode node;
		private Block block;
		public Rule3(IDOMTraversalNode node, Block block) {
			this.node = node;
			this.block = block;
		}
		@Override public boolean check() {
			Preconditions.checkState(block.getRootsList().size()>0);
			return block.getRootsList().size() == 1 && block.getRootsList().contains(node);
		}
	}
	
//	public static final boolean rule4(IDOMTraversalNode node) {
//		Iterator<IDOMTraversalNode> iter = node.getChildren().iterator();
//		while (iter.hasNext()) {
//			IDOMTraversalNode ch = iter.next();
//			if (!textNode(ch) && !virtualTextNode(ch))
//				return false;
//		}
//		return true;
//	}
	/**
	 * If all of the child nodes of the DOM node are text nodes or virtual text nodes,
	 * do not divide the node. If the font size and font weight of all these child nodes are same,
	 * set the DoC of the extracted block to 10. Otherwise, set the DoC of this extracted block to 9.
	 */
	public class Rule4 implements IRule {
		@Override public int getID() {return 4;}
		private IDOMTraversalNode node;
		public Rule4(IDOMTraversalNode node) {
			this.node = node;
		}
		@Override public boolean check() {
			Iterator<IDOMTraversalNode> iter = node.getChildren().iterator();
			while (iter.hasNext()) {
				IDOMTraversalNode ch = iter.next();
				if (!vipsLib.textNode(ch) && !vipsLib.virtualTextNode(ch))
					return false;
			}
			return true;
		}
	}
	
	/**
	 * If one of the child nodes of the DOM node is line-break node, then divide this DOM node.
	 */
	public class Rule5 implements IRule {
		@Override public int getID() {return 5;}
		private IDOMTraversalNode node;
		public Rule5(IDOMTraversalNode node) {
			this.node = node;
		}
		@Override public boolean check() {
			Iterator<IDOMTraversalNode> iter = node.getChildren().iterator();
			while (iter.hasNext()) {
				IDOMTraversalNode ch = iter.next();
				if (vipsLib.lineBreakNode(ch))
					return true;
			}
			return false;
		}
	}
	
	/**
	 * If one of the child nodes of the DOM node has HTML tag <HR>, then divide this DOM node.
	 */
	public class Rule6 implements IRule {
		@Override public int getID() {return 6;}
		private IDOMTraversalNode node;
		public Rule6(IDOMTraversalNode node) {
			this.node = node;
		}
		@Override public boolean check() {
			Iterator<IDOMTraversalNode> iter = node.getChildren().iterator();
			while (iter.hasNext()) {
				IDOMTraversalNode ch = iter.next();
				if (ch.canAs(IDOMElement.class)
					&& EHtmlElementConstants.HR.equalTo(
							ch.as(IDOMElement.class).getName()))
					return true;
			}
			return false;
		}
	}
	
	/**
	 * If the background color of this node is different from one of its children’s,
	 * divide this node and at the same time, the child node with different background color
	 * will not be divided in this round. Set the DoC value (6-8) for the child node based on
	 * the html tag of the child node and the size of the child node.
	 */
	public class Rule7 implements IRule {
		@Override public int getID() {return 7;}
		private IDOMTraversalNode node;
		private List<IDOMTraversalNode> nodesBGList = new LinkedList<IDOMTraversalNode>();
		public List<IDOMTraversalNode> getNodesBGList() {return nodesBGList;}
		public Rule7(IDOMTraversalNode node) {
			this.node = node;
		}
		@Override public boolean check() {
			TColor bg = node.as(IPlainVisualObject.class).getBackgroundTColor();
			if (bg != null) {
				Iterator<IDOMTraversalNode> iter = node.getChildren().iterator();
				while (iter.hasNext()) {
					IDOMTraversalNode ch = iter.next();
					TColor bgTmp = ch.as(IPlainVisualObject.class).getBackgroundTColor();
					if (bgTmp != null && !bgTmp.equals(bg))
						nodesBGList.add(ch);
				}
			}
			return !nodesBGList.isEmpty();
		}
	}
	
	/**
	 * If the node has at least one text node child or at least one virtual text node child,
	 * and the node's relative size is smaller than a threshold, then the node cannot be divided
	 * Set the DoC value (from 5-8) based on the html tag of the node
	 */
	public class Rule8 implements IRule {
		@Override public int getID() {return 8;}
		private IDOMTraversalNode node;
		public Rule8(IDOMTraversalNode node) {
			this.node = node;
		}
		@Override public boolean check() {
			Rectangle2D area = node.as(IQntBlock.class).getArea();
			double width = area.xMax - area.xMin;
			double height = area.yMax - area.yMin;
			if (width/sizeThreshold<maxSize
					|| height/sizeThreshold<maxSize) {
				Iterator<IDOMTraversalNode> iter = node.getChildren().iterator();
				while (iter.hasNext()) {
					IDOMTraversalNode ch = iter.next();
					if (vipsLib.textNode(ch) || vipsLib.virtualTextNode(ch))
						return true;
				}
			}
			return false;
		}
	}
	
	/**
	 * If the child of the node with maximum size are small than a threshold (relative size),
	 * do not divide this node. Set the DoC based on the html tag and size of this node.
	 */
	public class Rule9 implements IRule {
		@Override public int getID() {return 9;}
		private IDOMTraversalNode node;
		public Rule9(IDOMTraversalNode node) {
			this.node = node;
		}
		@Override public boolean check() {
			Iterator<IDOMTraversalNode> iter = node.getChildren().iterator();
			while (iter.hasNext()) {
				IDOMTraversalNode ch = iter.next();
				Rectangle2D area = ch.as(IQntBlock.class).getArea();
				double width = area.xMax - area.xMin;
				double height = area.yMax - area.yMin;
				if (width/sizeThreshold>=maxSize
						&& height/sizeThreshold>=maxSize) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * If previous sibling node has not been divided, do not divide this node
	 */
	public class Rule10 implements IRule {
		@Override public int getID() {return 10;}
		private boolean prevSiblingDivided;
		public Rule10(boolean prevSiblingDivided) {
			this.prevSiblingDivided = prevSiblingDivided;
		}
		@Override public boolean check() {
			return !prevSiblingDivided;
		}
	}
	
	public class Rule11 implements IRule {
		@Override public int getID() {
			return 11;
		}
		@Override public boolean check() {
			return false;
		}
	}
	
	public class Rule12 implements IRule {
		@Override public int getID() {
			return 12;
		}
		@Override public boolean check() {
			return false;
		}
	}

}
