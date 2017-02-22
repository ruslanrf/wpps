/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import java.util.List;

import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EFontStyle;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.ETextDecoration;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlOption;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlSelect;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlWebForm;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 6:48:52 PM
 */
public class RdfHtmlOption extends RdfHtmlElement implements IHtmlOption {

	/**
	 * @param inst
	 * @param rdfModel
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 * @param imImpl
	 */
	@Inject
	public RdfHtmlOption(@Assisted Resource inst,
//			@AnnotStructBlockGeomModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl, IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, imImpl);
	}

	@Override
	public String getText() {
		return imImpl.getTextValue(rdfInst);
	}
	
	@Override
	public String getString() {
		return getText();
	}

	@Override
	public List<String> getFontFamilyList() {
		return imImpl.getFontFamilyList(rdfInst);
	}

	@Override
	public Float getFontWeight() {
		return imImpl.getFontWeight(rdfInst);
	}

	@Override
	public Float getFontSize() {
		return imImpl.getFontSize(rdfInst);
	}

	@Override
	public EFontStyle getFontStyle() {
		return imImpl.getFontStyle(rdfInst);
	}
	
	@Override
	public ETextDecoration getTextDecoration() {
		return imImpl.getTextDecoration(rdfInst);
	}
	
	@Override
	public IHtmlWebForm getWebForm() {
		IHtmlSelect s = this.getSelect();
		return (s==null)?null:s.getWebForm();
	}

	@Override
	public IHtmlSelect getSelect() {
		final Resource rez = imImpl.getHtmlSelectForOption(rdfInst);
		return (rez==null)?null:rdfInstAdpFactoryWrap.createAdp(rez, IHtmlSelect.class);
	}

	@Override
	public Boolean isSelected() {
		return imImpl.isSelected(rdfInst);
	}

}
