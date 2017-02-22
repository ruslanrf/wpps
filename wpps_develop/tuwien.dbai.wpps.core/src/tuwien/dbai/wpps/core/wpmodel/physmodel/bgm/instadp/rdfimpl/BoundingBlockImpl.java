/**
 * File name: BlockContainmentFuncs.java
 * @created: May 6, 2011 9:44:27 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl;

import java.util.Collection;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.ICheckImplInitialization;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfInnerBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfOuterBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.CompositeBlockLib;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * <p>To implement its methods static class {@link CompositeBlockLib} should be used.</p>
 *
  * <p>Functions related to the Composite and Basic blocks and their structural relations
  * such as containment.</p>
 * 
 * <P>Directly, functionality for this class is used in following classes:
 * {@link RdfBoundingBlock}, {@link RdfBox}, {@link RdfInnerBlock}, {@link RdfOuterBlock},
 * {@link RdfOutline}, {@link RdfViewPortBlock}, {@link RdfWebDocumentBlock}.</P>
 * 
 * <P>This additional functionality has similar interface with
 * {@link WebDocumentBlockImpl},
 * {@link ViewPortBlockImpl},
 * {@link OuterInnerBlocksAF}.</P>
 * 
 * @created: May 6, 2011 9:44:27 PM
 * @author Ruslan (ruslanrf@gmail.com)
 *
 */
@Singleton
public final class BoundingBlockImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(BoundingBlockImpl.class);
	
	public BoundingBlockImpl() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
	}
	
	IArrFunction<Object, Object> getContainingBoundingBlocks = null;
	IArrFunction<Object, Object> containsBlock = null;
	IArrFunction<Object, Object> getContainedBlocks = null;
	IArrFunction<Object, Object> addContainedBlock = null;
	IArrFunction<Object, Object> addContainedBlocks = null;
	
	/**
	 * Check if block in the composite block.
	 */
	public boolean structContainsBlock(final Resource thisBlock, final Resource block) {
		return (Boolean)containsBlock.apply(thisBlock, block);
	}

	/**
	 * @return collection of contained blocks. It never returns null.
	 */
	@SuppressWarnings("unchecked")
	public Collection<Resource> getContainedBlocks(final Resource thisBlock) {
		return (Set<Resource>)getContainedBlocks.apply(thisBlock);
	}
	
	public void addContainedBlock(final Resource thisBlock, final Resource block) {
		addContainedBlock.apply(thisBlock, block);
	}
	
	public void addContainedBlocks(final Resource thisBlock, final Resource block) {
		addContainedBlocks.apply(thisBlock, block);
	}

	/**
	 * Getting composite block which contains specified block <code>thisBlock</code>.
	 * This function is used in bounding blocks ({@link IBoundingBlock}, {@link IBoundingBlock}),
	 * web page block (({@link IWebDocumentBlock}, {@link RdfWebDocumentBlock}))
	 * and viewport block(({@link IViewPortBlock}, {@link RdfViewPortBlock})).
	 * 
	 * Such object property as {@link StructBlockGeomModelOnt#inCompositeBlock} or
	 * its inverse ({@link StructBlockGeomModelOnt#containsBlock}),
	 * or {@link StructBlockGeomModelOnt#containsBlocks}. 
	 * 
	 * @param thisBoundungBlock
	 * @return never null.
	 */
	@SuppressWarnings("unchecked")
	public Set<Resource> getContainingBoundingBlocks(final Resource thisBlock) {
		return (Set<Resource>)getContainingBoundingBlocks.apply(thisBlock);
	}

	@Override
	public boolean allFunctionsAreImplemented() {
		 return  getContainingBoundingBlocks != null
				 && containsBlock != null
				 && getContainedBlocks != null
				 && addContainedBlock != null
				 && addContainedBlocks != null;
	}

}
