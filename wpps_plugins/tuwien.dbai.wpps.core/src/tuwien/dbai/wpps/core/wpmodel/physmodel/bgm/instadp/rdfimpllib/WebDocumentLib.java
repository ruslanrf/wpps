/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib;

import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLib;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Aug 31, 2012 11:37:20 AM
 */
public class WebDocumentLib {

	public static final boolean containsTopPage(final Resource containing
			, final Resource contained, final Model model) {
		return model.contains(containing, StructBlockGeomModelOnt.hasTopPage, contained);
	}
	
	public static final Resource getTopPage(final Resource webpageBlock, final Model model) {
		return InstAdpLib.jenaTypeCastSoft(
				InstAdpLib.getValueAsRDFNodeSoft(webpageBlock, StructBlockGeomModelOnt.hasTopPage, model)
				, Resource.class);
	}
	
	public static final void addTopPage(final Resource webpageBlock, final Resource topWindowBlock, final Model model) {
		model.add(webpageBlock, StructBlockGeomModelOnt.hasTopPage, topWindowBlock);
	}
	
}
