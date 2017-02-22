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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.WebPageBlockLib;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * <p>To implement its methods static class {@link WebPageBlockLib} should be used.</p>
 * <p>An implementation adecuate to the is provided </p>
 *
 * @created: May 6, 2011 9:44:27 PM
 * @author Ruslan (ruslanrf@gmail.com)
 *
 */
@Singleton
public final class WebPageBlockImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(WebPageBlockImpl.class);
	
	
	public WebPageBlockImpl() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
	}

	IArrFunction<Object, Object> getContainingBoundingBlocks = null;
	IArrFunction<Object, Object> containsBox = null;
	IArrFunction<Object, Object> getContainedBoxes = null;
	IArrFunction<Object, Object> addContainedBox = null;
	IArrFunction<Object, Object> addContainedBoxes = null;
	IArrFunction<Object, Object> getParentPageBlock = null;
	IArrFunction<Object, Object> containsChildPage = null;
	IArrFunction<Object, Object> getChildPages = null;
	IArrFunction<Object, Object> addChildPage = null;
	IArrFunction<Object, Object> containsViewPort = null;
	IArrFunction<Object, Object> getViewPort = null;
	IArrFunction<Object, Object> addViewPort = null;
	
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
	public Collection<Resource> getContainingBoundingBlocks(final Resource page) {
		return (Set<Resource>)getContainingBoundingBlocks.apply(page);
	}
	
	/**
	 * Check if block in the composite block.
	 */
	public boolean containsBox(final Resource thisPage, final Resource box) {
		return (Boolean)containsBox.apply(thisPage, box);
	}

	@SuppressWarnings("unchecked")
	public Collection<Resource> getContainedBoxes(final Resource page) {
		return (Set<Resource>)getContainedBoxes.apply(page);
	}
	
	public void addContainedBox(Resource page, Resource box) {
		addContainedBox.apply(page, box);
	}
	
	public void addContainedBoxes(Resource page, Collection<Resource> boxes) {
		addContainedBoxes.apply(page, boxes);
	}
	
	public Resource getParentPage(final Resource thisWindow) {
		return (Resource)getParentPageBlock.apply(thisWindow);
	}
	
	public boolean containsChildPage(final Resource thisPage, final Resource page) {
		return (Boolean)containsChildPage.apply(thisPage, page);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Resource> getChildPages(final Resource thisWindow) {
		return (Collection<Resource>)getChildPages.apply(thisWindow);
	}
	
	public void addChildPage(Resource thisBlock, Resource block) {
		addChildPage.apply(thisBlock, block);
	}
	
	public boolean containsViewPort(final Resource thisPage, final Resource viewPort) {
		return (Boolean)containsViewPort.apply(thisPage, viewPort);
	}
	
	/**
	 * @param thisWindow
	 * @return can be null
	 */
	public Resource getViewPort(final Resource thisWindow) {
		return (Resource)getViewPort.apply(thisWindow);
	}
	
	public void addViewPort(Resource thisBlock, Resource block) {
		addViewPort.apply(thisBlock, block);
	}
	
	@Override
	public boolean allFunctionsAreImplemented() {
		if ( getContainingBoundingBlocks != null
				&& containsBox != null
				&& getContainedBoxes != null
				&& addContainedBox != null
				&& addContainedBoxes != null
				&& getParentPageBlock != null
				&& containsChildPage != null
				&& getChildPages != null
				&& addChildPage != null
				&& containsViewPort != null
				&& getViewPort != null  
				&& addViewPort != null)
			return true;
		else
			return false;
	}

}
