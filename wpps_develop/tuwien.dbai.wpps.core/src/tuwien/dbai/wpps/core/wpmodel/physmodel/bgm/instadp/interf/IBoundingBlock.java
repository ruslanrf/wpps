/**
 * File name: IBoundingBlock.java
 * @created: Mar 17, 2011 4:38:08 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf;

import java.util.Collection;

import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;


/**
 * Basis type of the block-based geometric model.
 * @created: Mar 17, 2011 4:38:08 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public interface IBoundingBlock extends ICompositeBlock {
	
	/**
	 * Add block into the bounding block. This containment relation
	 * corresponds to the following properties in the ontology:
	 * {@link StructBlockGeomModelOnt#containsBlock}, {@link StructBlockGeomModelOnt#containsBlocks}.
	 * 
	 * @param block
	 */
	void addContainedBlock(IBlock block);
	
	void addContainedBlocks(Collection<IBlock> blockCol);
	
//	/**
//	 * We need this operation when add new blocks contained in the bounding block.
//	 * Because model can be configured in such a way that the bounding block will not
//	 * change its quantitative spatial extend during adding new blocks.
//	 * 
//	 * @return true if we should update info about spatial extend.
//	 */
//	boolean needUpdateQntSpatialExtend();
	
//	/**
//	 * Update quantitative spatial extend.
//	 * Function which allow as to update quantitative spatial extend of the bounding block.
//	 */
//	void updateQntSpatialExtend();
	
}
