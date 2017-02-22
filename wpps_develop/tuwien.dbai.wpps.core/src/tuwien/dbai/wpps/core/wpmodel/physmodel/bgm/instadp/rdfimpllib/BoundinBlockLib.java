/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLib;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Aug 31, 2012 5:29:43 PM
 */
public final class BoundinBlockLib {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BoundinBlockLib.class);

	final static public Set<Resource> getContainingBoundingBlocks(final Resource block
			, final Model model) {
		return InstAdpLib.fillCollectionOfSubjectsAsResourcesByType(block, StructBlockGeomModelOnt.BoundingBlock
				, StructBlockGeomModelOnt.containsBlock, model, new LinkedHashSet<Resource>());
	}
	
	final static public List<Resource> getContainingBoundingBlocksFromRdfContainer(final Resource block, final Model model) {
		return CompositeBlockLib.getStructContainingBlocksFromContainer(StructBlockGeomModelOnt.BoundingBlock
				, StructBlockGeomModelOnt.containsBlocks, block, model);
	}
	
	public static final boolean containsBlockInRdfContainer(final Resource compositeBlock
			, final Resource containedBlock, final Model model) {
		return InstAdpLib.containmentInRdfSeq(
				compositeBlock, StructBlockGeomModelOnt.containsBlocks, containedBlock, model);
	}
	
	
	public static final boolean containsBlock(final Resource compositeBlock
			, final Resource containedBlock, final Model model) {
		return model.contains(compositeBlock, StructBlockGeomModelOnt.containsBlock, containedBlock);
	}
	
	private final static int MAX_ESTIMATED_QNT_BLOCKS_IN_BOUNDINGBLOCK = 10000;
	public static final List<Resource> getContainedBlocksFromRdfContainer(final Resource compositeBlock
			, final Model model) {
		return InstAdpLib.fillCollectionOfResourcesFromRdfSeqSoft(compositeBlock
				, StructBlockGeomModelOnt.containsBlocks, model
				, new ArrayList<Resource>(MAX_ESTIMATED_QNT_BLOCKS_IN_BOUNDINGBLOCK));
	}
	
	/**
	 * Get all structurally contained blocks (property {@link StructBlockGeomModelOnt#containsBlock}).
	 */
	public static final Set<Resource> getContainedBlocks(final Resource compositeBlock, final Model model) {
		return InstAdpLib.fillCollectionOfValuesAsResources(compositeBlock
				, StructBlockGeomModelOnt.containsBlock, model
				, new LinkedHashSet<Resource>(MAX_ESTIMATED_QNT_BLOCKS_IN_BOUNDINGBLOCK));
	}
	
	final static public void appendContainedBlockToRdfSeq(final Resource compositeBlock
			, final Resource containedBlock, final Model model) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(compositeBlock
				, StructBlockGeomModelOnt.containsBlocks, containedBlock, model);
	}
	
	/**
	 * Sequences are very expensive.
	 * @param compositeBlock
	 * @param containedBlocks
	 * @param model
	 */
	final static public void appendContainedBlocksToRdfSeq(final Resource compositeBlock
			, final Collection<Resource> containedBlocks, final Model model) {
		InstAdpLib.appendResourcesToRdfSeqOrCreate(compositeBlock, StructBlockGeomModelOnt.containsBlocks
				, containedBlocks, model);
	}
	
	/**
	 * Add block <code>containedBlock</code> as a contained block into the <code>compositeBlock</code>.
	 * The block is added due to the property {@link StructBlockGeomModelOnt#containsBlock containsBlock}
	 * of the block-based geometric model.
	 * 
	 * @param compositeBlock
	 * @param containedBlock
	 * @param model
	 */
	final static public void addContainedBlock(final Resource compositeBlock, final Resource containedBlock, final Model model) {
		model.add(compositeBlock, StructBlockGeomModelOnt.containsBlock, containedBlock);
	}
	
	final static public void addContainedBlocks(final Resource compositeBlock
			, final Collection<Resource> containedBlocks, final Model model) {
		Iterator<Resource> iter = containedBlocks.iterator();
		while (iter.hasNext()) {
			addContainedBlock(compositeBlock, iter.next(), model);
		}
	}
	
}
