/**
 * File name: IBlock.java
 * 
 * This class corresponds to the block-based geometric objects.
 * 
 * @created: Mar 16, 2011 8:49:14 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf;

import java.util.Collection;

import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

/**
 * Block as a structural element from the ontology regardless of the qualitative and quantitative data.
 * 
 * @created: Mar 16, 2011 8:49:14 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public interface IBlock extends IAbstractBlock {
	
	/**
	 * This function allow us to get bounding blocks which contains this one.
	 * It is not spatial containment but structural containment.
	 * This containment relation
	 * corresponds to the following properties in the ontology:
	 * {@link StructBlockGeomModelOnt#inCompositeBlock}, and also can be gotten via
	 * inverse property {@link StructBlockGeomModelOnt#containsBlock} and 
	 * {@link StructBlockGeomModelOnt#containsBlocks} property.
	 * 
	 * @return bounding block which directly contain instance of this interface.
	 */
	Collection<IBoundingBlock> getContainingBoundingBlocks();
	
}
