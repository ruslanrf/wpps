/**
 * File name: IInnerBlock.java
 * @created: Mar 17, 2011 4:34:58 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf;


/**
 * Basis type of the block-based geometric model.
 * @created: Mar 17, 2011 4:34:58 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public interface IInnerBlock extends IBasicBlock {
	IBox getBox();
}
