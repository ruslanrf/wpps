/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlCheckBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlWebForm;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMCheckBoxGroup;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 5:40:16 PM
 */
public class RdfHtmlCheckBox extends RdfHtmlElement implements IHtmlCheckBox {

	/**
	 * @param inst
	 * @param rdfModel
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 * @param imImpl
	 */
	@Inject
	public RdfHtmlCheckBox(
			@Assisted Resource inst,
//			, @AnnotStructBlockGeomModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl, IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, imImpl);
	}

	@Override
	public boolean isChecked() {
		return imImpl.isSelected(rdfInst);
	}

	@Override
	public IIMCheckBoxGroup getCheckBoxGroup() {
		Resource rez = imImpl.getCheckBoxGroupForCheckBox(rdfInst);
		return (rez == null)?null
				:rdfInstAdpFactoryWrap.createAdp(rez, IIMCheckBoxGroup.class);
	}

	@Override
	public IHtmlWebForm getWebForm() {
		Resource rez = imImpl.getWebForm(rdfInst);
		return (rez == null)?null
				:rdfInstAdpFactoryWrap.createAdp(rez, IHtmlWebForm.class);
	}


}
