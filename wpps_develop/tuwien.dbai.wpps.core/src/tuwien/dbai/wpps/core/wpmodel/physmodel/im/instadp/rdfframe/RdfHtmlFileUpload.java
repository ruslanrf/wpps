/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EFontStyle;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.ETextDecoration;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlFileUpload;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlWebForm;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 5:45:08 PM
 */
public class RdfHtmlFileUpload extends RdfHtmlElement implements IHtmlFileUpload {

	@Inject
	public RdfHtmlFileUpload(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final IMImpl imImpl) {
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
		Resource rez = imImpl.getWebForm(rdfInst);
		return (rez == null)?null
				:rdfInstAdpFactoryWrap.createAdp(rez, IHtmlWebForm.class);
	}

}
