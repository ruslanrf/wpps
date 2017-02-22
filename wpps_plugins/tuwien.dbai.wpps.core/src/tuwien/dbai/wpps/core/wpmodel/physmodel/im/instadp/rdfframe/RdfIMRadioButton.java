/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMRadioButton;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMRadioButtonGroup;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 7:15:54 PM
 */
public class RdfIMRadioButton extends RdfInstanceAdp implements IIMRadioButton {

private final IMImpl imImpl;
	
	/**
	 * @param inst
	 * @param rdfModel
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 */
	@Inject
	public RdfIMRadioButton(@Assisted Resource inst,
//			@AnnotStructBlockGeomModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl,
			final IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.imImpl = imImpl;
	}

	@Override
	public boolean isChecked() {
		return imImpl.isSelected(rdfInst);
	}

	@Override
	public IIMRadioButtonGroup getRadioButtonGroup() {
		Resource rez = imImpl.getRadioButtonGroupForRadioButton(rdfInst);
		return (rez == null)?null
				:rdfInstAdpFactoryWrap.createAdp(rez, IIMRadioButtonGroup.class);
	}
}
