/**
 * File name: ICompositeBlock.java
 * @created: Mar 16, 2011 8:52:07 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf;

import java.util.Collection;


/**
 * (As a structural element).
 * This interface should not have any direct implementations because, for instance,
 * {@link #getContainedBlocks()} function is different for box, bounding block,
 * viewport and web page blocks.
 *
 * @created: Mar 16, 2011 8:52:07 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public interface ICompositeBlock extends IBlock {
	
	/**
	 * Check if block is directly in the composite block, as structurally
	 * (not spatially/topologically) contained one.
	 * For instance, Page contains boxes, box contains outer, inner and outline blocks.
	 * @param block
	 * @return
	 */
	boolean containsBlock(IBlock block);
	
	// TODO: change to set ?
	/**
	 * Get array of direct structurally contained blocks.
	 * @return contained blocks which can be any implementation
	 * of the interface {@link IBlock}.
	 */
	Collection<IBlock> getContainedBlocks();
	
}
