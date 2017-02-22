/**
 * File name: IWPBlock.java
 * @created: Mar 17, 2011 4:36:53 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf;

import java.util.Collection;


/**
 * TODO: implement getBoxes function
 * (As a structural element).
 * Basis type of the block-based geometric model.
 *
 * @created: Mar 17, 2011 4:36:53 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public interface IWebPageBlock extends ICompositeBlock {
	
	IWebPageBlock getParentWebPageBlock();
	
	Collection<IWebPageBlock> getChildWebPageBlocks();
	
	IViewPortBlock getViewPort();
	
	Collection<IBox> getBoxes();
	
}
