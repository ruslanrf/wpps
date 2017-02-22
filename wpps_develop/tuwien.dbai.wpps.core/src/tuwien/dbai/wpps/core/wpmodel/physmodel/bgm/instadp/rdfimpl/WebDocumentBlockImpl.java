/**
 * File name: WebPageBlockStructContainmentAF.java
 * @created: Jul 23, 2011 7:24:06 PM
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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfWebDocumentBlock;

import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * <P>
 * Directly, functionality for this class is used in following classes:
 * {@link RdfWebDocumentBlock}, {@link RdfBox}.</P>
 * 
 * <P>This additional functionality has similar interface with
 * {@link BoundingBlockImpl},
 * {@link ViewPortBlockImpl},
 * {@link OuterInnerBlocksAF}.</P>
 * 
 * @created: Jul 23, 2011 7:24:06 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
@Singleton
public final class WebDocumentBlockImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(WebDocumentBlockImpl.class);
	
	public WebDocumentBlockImpl() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
	}
	
	IArrFunction<Object, Object> getContainingBoundingBlocks = null;
	IArrFunction<Object, Object> containsTopPage = null;
	IArrFunction<Object, Object> getTopPage = null;
	IArrFunction<Object, Object> addTopPage = null;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<Resource> getContainingBoundingBlocks(final Resource viewport) {
		return (Set)getContainingBoundingBlocks.apply(viewport);
	}
	
	/**
	 * Check if block in the composite block.
	 */
	public boolean containsPage(final Resource thisWebPage, final Resource block) {
		return (Boolean)containsTopPage.apply(thisWebPage, block);
	}
	
	public Resource getTopPage(final Resource thisWebpage) {
		return (Resource)getTopPage.apply(thisWebpage);
	}
	
	public void addTopPage(final Resource thisBlock, final Resource block) {
		addTopPage.apply(thisBlock, block);
	}

	@Override
	public boolean allFunctionsAreImplemented() {
		if ( containsTopPage != null
				&& getTopPage != null
				&& addTopPage != null )
			return true;
		else
			return false;
		
	}

}
