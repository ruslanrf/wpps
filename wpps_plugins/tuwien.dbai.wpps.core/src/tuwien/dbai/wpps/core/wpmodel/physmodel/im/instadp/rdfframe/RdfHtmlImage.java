/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlImage;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 23, 2012 1:13:41 PM
 */
public class RdfHtmlImage extends RdfHtmlElement implements IHtmlImage {

	@Inject
	public RdfHtmlImage(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, imImpl);
	}

	@Override
	public String getUrl() {
		return imImpl.getUrl(rdfInst);
	}
	
	@Override
	public String getAltText() {
		return imImpl.getAltText(rdfInst);
	}

	@Override
	public String getString() {
		return getAltText();
	}

}
