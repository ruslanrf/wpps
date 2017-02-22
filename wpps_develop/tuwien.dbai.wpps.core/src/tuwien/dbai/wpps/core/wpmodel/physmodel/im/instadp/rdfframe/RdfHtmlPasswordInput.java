/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlPasswordInput;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlWebForm;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 7:06:35 PM
 */
public class RdfHtmlPasswordInput extends RdfHtmlElement implements IHtmlPasswordInput {

	@Inject
	public RdfHtmlPasswordInput(@Assisted Resource inst,
//			@AnnotStructBlockGeomModel  Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl, IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, imImpl);
	}
	
	@Override
	public IHtmlWebForm getWebForm() {
		Resource rez = imImpl.getWebForm(rdfInst);
		return (rez == null)?null
				:rdfInstAdpFactoryWrap.createAdp(rez, IHtmlWebForm.class);
	}

}
