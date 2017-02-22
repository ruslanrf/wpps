/**
 * File name: ViewPortBlockStructContainmentAF.java
 * @created: Jul 23, 2011 6:45:13 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl;

import java.util.Collection;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.ICheckImplInitialization;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfViewPortBlock;

import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Directly, functionality for this class is used in following classes:
 * {@link RdfViewPortBlock}, {@link RdfBox}.
 * 
 * <P>This additional functionality has similar interface with
 * {@link WebDocumentBlockImpl},
 * {@link BoundingBlockImpl},
 * {@link OuterInnerBlocksAF}.</P>
 * 
 * @created: Jul 23, 2011 6:45:13 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
@Singleton
public final class ViewPortBlockImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(ViewPortBlockImpl.class);
	
	public ViewPortBlockImpl() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
	}
	
	IArrFunction<Object, Object> getContainingBoundingBlocks = null;
//	private IArrFunction<Object, Object> structContainsBlock = null;
//	private IArrFunction<Object, Object> getStructContainedBlocks = null;
//	private IArrFunction<Object, Object> addStructContainedBlock = null;
//	private IArrFunction<Object, Object> addStructContainedBlocks = null;
	
	
	/**
	 * Getting composite block which structurally contains specified block <code>thisBlock</code>.
	 * This function is used in ({@link RdfBox}.
	 * @param box
	 * @return viewport ontological instance. 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<Resource> getContainingBoundingBlocks(final Resource viewport) {
		return (Set)getContainingBoundingBlocks.apply(viewport);
	}

//	/**
//	 * Check if box structurally in the viewport.
//	 * (Structurally, viewport block can contains only boxes).
//	 * 
//	 * @param viewPortBlock
//	 * @param box
//	 * @return <code>true</code>, if the box is contained in the viewport.
//	 */
//	public boolean structContainsBlock(final Resource viewPortBlock, final Resource box, final Model model) {
//		return (Boolean)structContainsBlock.apply(viewPortBlock, box, model);
//	}
//
//	/**
//	 * Get structurally contained boxes.
//	 * @param viewPortBlock
//	 * @return collection of contained boxes. (never null).
//	 */
//	@SuppressWarnings("unchecked")
//	public Set<Resource> getStructContainedBlocks(final Resource viewPortBlock, final Model model) {
//		return (Set<Resource>)getStructContainedBlocks.apply(viewPortBlock, model);
//	}
//
//	/**
//	 * This function can be used in case if we would like to store containment relation in the ontology.
//	 * This function can be invoked during getting all relative blocks from an R-Tree.  
//	 * @param viewPortBlock
//	 * @param block
//	 */
//	public void addStructContainedBlock(final Resource viewPortBlock, final Resource block, final Model model) {
//		addStructContainedBlock.apply(viewPortBlock, block, model);
//	}
//	
//	public void addStructContainedBlocks(final Resource viewPortBlock, final List<Resource> blocks, final Model model) {
//		addStructContainedBlocks.apply(viewPortBlock, blocks, model);
//	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.adp.instadp.rdfimpl.ICheckImplInitialization#allFunctionsAreImplemented()
	 */
	@Override
	public boolean allFunctionsAreImplemented() {
		
		return getContainingBoundingBlocks != null;
//				&& (structContainsBlock != null)
//				&& (getStructContainedBlocks != null) && (addStructContainedBlock != null)
//				&& addStructContainedBlocks!=null 
	}

}
