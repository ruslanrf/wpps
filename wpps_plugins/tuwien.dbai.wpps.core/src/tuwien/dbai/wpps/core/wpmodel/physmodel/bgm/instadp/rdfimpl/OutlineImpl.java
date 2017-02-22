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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfInnerBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfOuterBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.CompositeBlockLib;

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
public final class OutlineImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(OutlineImpl.class);
	
	public OutlineImpl() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
	}
	
	public void setGetStructContainingBoundingBlocks(final IArrFunction<Object, Object> getStructContainingBoundingBlocks) {
		this.getContainingBoundingBlocks = getStructContainingBoundingBlocks;
	}
	
	private IArrFunction<Object, Object> getContainingBoundingBlocks = null;
	
	@SuppressWarnings("unchecked")
	public Collection<Resource> getContainingBoundingBlocks(final Resource thisBox) {
		return (Set<Resource>)getContainingBoundingBlocks.apply(thisBox);
	}
	
	@Override
	public boolean allFunctionsAreImplemented() {
		 if (getContainingBoundingBlocks != null)
			 return true;
		 else
			 return false;
	}

}
