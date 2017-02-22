/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import java.util.Collection;

import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EWebFormMethod;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlWebForm;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlWebFormElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 4, 2012 2:20:00 PM
 */
public class RdfHtmlWebForm extends RdfHtmlElement implements IHtmlWebForm {

	@Inject
	public RdfHtmlWebForm(@Assisted Resource inst,
//			@AnnotStructBlockGeomModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl,
			final IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, imImpl);
	}

	@Override
	public Collection<IHtmlWebFormElement> getElements() {
		final Collection<Resource> resCol =  imImpl.getWebFormElements(rdfInst);
		Collection<IHtmlWebFormElement> rez = InstAdpLibSupport
				.createCollection(resCol, IHtmlWebFormElement.class);
		for (final Resource r: resCol) {
			rez.add(rdfInstAdpFactoryWrap.createAdp(r, IHtmlWebFormElement.class));
		}
		return rez;
	}

	@Override
	public EWebFormMethod getMethod() {
		return imImpl.getWebFormMethod(rdfInst);
	}

	@Override
	public String getAction() {
		return imImpl.getWebFormAction(rdfInst);
	}

}
