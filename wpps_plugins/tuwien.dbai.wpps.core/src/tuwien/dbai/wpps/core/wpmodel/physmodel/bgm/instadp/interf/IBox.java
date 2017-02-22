/**
 * File name: IBox.java
 * @created: Mar 17, 2011 4:37:43 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf;

import java.util.Collection;

import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBoxType;

/**
 * 
 * (As a structural element).
 * Basis type of the block-based geometric model.
 *
 * @created: Mar 17, 2011 4:37:43 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public interface IBox extends ICompositeBlock {
	
	/**
	 * Get type of the box which is not a structural one.
	 * @return type of the box.
	 */
	EBoxType getBoxType();
	
//	/**
//	 * Web page block structurally can contain only boxes(?) which are CSS boxes in the CSS model.
//	 * @return web page block which contains this box.
//	 */
//	IWebPageBlock getStructContainingWebPageBlock();
	
	IWebPageBlock getWebPage();
	
//	/**
//	 * View port block structurally can contain only boxes which are CSS boxes in the CSS model.
//	 * @return view port block which contains this box, or <CODE>null</CODE> if there is no such a view port.
//	 */
//	IViewPortBlock getStructContainingViewPortBlock();
	
//	/**
//	 * @type: EMultiCSSBuilding
//	 *
//	 * Configuration for creating CSS Attribute
//	 *
//	 * @created: Mar 21, 2011 5:15:39 PM
//	 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
//	 *
//	 */
//	public static enum EMultiCSSBuilding {
//		CREATE_NONE (0),
//		CREATE_CSS_CODE(1),
//		CREATE_CSS_ATTRIBUTES(2);
//		private int _id = 0;
//		public int id() {return _id;}
//		EMultiCSSBuilding(int _id) {
//			this._id = _id;
//		}
//	}
	
	// ======== Box as a composite block ========
	
	/**
	 * @return inner block, if it does not exists, return null.
	 */
	IInnerBlock getInnerBlock();
	
	/**
	 * @return outer block, if it does not exists, return null.
	 */
	IOuterBlock getOuterBlock();
	
	// ========  ========
	
	// ======== Box as a conterpart of a CSS box ========
	
//	/**
//	 * @return CSS Properties related to the Box. Can return <CODE>null</CODE>
//	 * if there is no css property attached for optimization reasons.
//	 */
//	CSSProperties getCSSProperties();
	
	// ========  ========
	
	Collection<IOutline> getClientRects();

}
