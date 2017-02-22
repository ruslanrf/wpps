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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImplProvider;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Library of static functions for web page.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Aug 29, 2012 10:01:42 PM
 * @see WebPageBlockImplProvider
 * @see RdfWebPageBlock
 * @see IWebPageBlock
 */
public final class WebPageBlockLib {
	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(WebPageBlockLib.class);

	public static final boolean containsBox(final Resource webPage
			, final Resource box, final Model sbgModel) {
		return sbgModel.contains(webPage, StructBlockGeomModelOnt.containsBox, box);
	}
	
	public static final boolean containsBoxInRdfContainer(final Resource webPage
			, final Resource box, final Model sbgModel) {
		return InstAdpLib.containmentInRdfSeq(
				webPage, StructBlockGeomModelOnt.containsBoxes, box, sbgModel);
	}
	
	private final static int MAX_ESTIMATED_QNT_BOXES_IN_PAGE = 10000;
	public static final List<Resource> getContainedBoxesFromRdfContainer(final Resource compositeBlock, final Model model) {
		return InstAdpLib.fillCollectionOfResourcesFromRdfSeqSoft(compositeBlock
				, StructBlockGeomModelOnt.containsBoxes, model
				, new ArrayList<Resource>(MAX_ESTIMATED_QNT_BOXES_IN_PAGE));
	}
	
	public static final Set<Resource> getStructContainedBoxes(final Resource compositeBlock, final Model model) {
		return InstAdpLib.fillCollectionOfValuesAsResources(compositeBlock
				, StructBlockGeomModelOnt.containsBox, model
				, new LinkedHashSet<Resource>(MAX_ESTIMATED_QNT_BOXES_IN_PAGE));
	}
	
	final static public void addContainedBox(final Resource containing
			, final Resource contained, final Model model) {
		model.add(containing, StructBlockGeomModelOnt.containsBox, contained);
	}
	
	final static public void addContainedBoxes(final Resource containing
			, final Collection<Resource> contained, final Model model) {
		Iterator<Resource> iter = contained.iterator();
		while (iter.hasNext()) {
			addContainedBox(containing, iter.next(), model);
		}
	}
	
	final static public void addContainedBoxToRdfSeq(final Resource containing
			, final Resource contained, final Model model) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(containing
				, StructBlockGeomModelOnt.containsBoxes, contained, model);
	}
	
	final static public void addContainedBoxesToRdfSeq(final Resource containing
			, final Collection<Resource> contained, final Model model) {
		InstAdpLib.appendResourcesToRdfSeqOrCreate(containing
				, StructBlockGeomModelOnt.containsBoxes, contained, model);
	}
	
	public static final Resource getParentPage(final Resource window, final Model model) {
		return InstAdpLib.getSubjectAsResourceSoft(window, StructBlockGeomModelOnt.hasChildPage, model);
	}
	
	public static final boolean containsChildPage(final Resource containing
			, final Resource contained, final Model model) {
		return model.contains(containing, StructBlockGeomModelOnt.hasChildPage, contained);
	}
	
	public static final Set<Resource> getChildpages(final Resource page, final Model model) {
		return InstAdpLib.fillCollectionOfValuesAsResources(page, StructBlockGeomModelOnt.hasChildPage
				, model, new LinkedHashSet<Resource>());
	}
	
	final static public void addChildPage(final Resource containing, final Resource contained, final Model model) {
		model.add(containing, StructBlockGeomModelOnt.hasChildPage, contained);
	}
	
	public static final boolean containsViewPort(final Resource containing
			, final Resource contained, final Model model) {
		return model.contains(containing, StructBlockGeomModelOnt.hasViewPort, contained);
	}
	
	public static final Resource getViewPort(final Resource window, final Model model) {
		return InstAdpLib.jenaTypeCastSoft(
				InstAdpLib.getValueAsRDFNodeSoft(window, StructBlockGeomModelOnt.hasViewPort, model)
				, Resource.class);
	}
	
	final static public void addViewPort(final Resource window, final Resource containedWindow
			, final Model model) {
		model.add(window, StructBlockGeomModelOnt.hasViewPort, containedWindow);
	}

}
