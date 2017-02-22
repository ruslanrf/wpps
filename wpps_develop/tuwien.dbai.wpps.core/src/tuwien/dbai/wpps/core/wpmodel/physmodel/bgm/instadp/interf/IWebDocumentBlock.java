/**
 * File name: IWPBlock.java
 * @created: Mar 17, 2011 4:36:53 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf;


/**
 * (As a structural element).
 * Basis type of the block-based geometric model.
 *
 *This interface corresponds to the all web page, represented by web browser.
 *
 *Web page block structurally contains only boxes.
 *
 * @created: Mar 17, 2011 4:36:53 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public interface IWebDocumentBlock extends ICompositeBlock {
	
	IWebPageBlock getTopWebPage();
	
//	/**
//	 * Update the containment of the block.
//	 * It should contain all blocks on the web page.
//	 * + update borders if needed
//	 * 
//	 * 
//	 * Update the containment of the block.
//	 * It should contain all blocks in the viewport.
//	 * 
//	 */
//	void updateBoxesContainment();
	
}
