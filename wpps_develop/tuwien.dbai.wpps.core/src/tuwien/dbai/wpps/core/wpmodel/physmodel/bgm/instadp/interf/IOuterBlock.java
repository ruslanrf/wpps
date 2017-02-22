/**
 * File name: IOuterBlock.java
 * @created: Mar 17, 2011 4:35:54 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf;


/**
 * Basis type of the block-based geometric model.
 * @created: Mar 17, 2011 4:35:54 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public interface IOuterBlock extends IBasicBlock {
	IBox getBox();
}
