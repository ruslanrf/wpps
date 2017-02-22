/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
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
 * @created Oct 5, 2012 6:50:42 PM
 */
public class RdfHtmlSelect extends RdfHtmlElement implements IHtmlSelect {

	/**
	 * @param inst
	 * @param rdfModel
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 * @param imImpl
	 */
	@Inject
	public RdfHtmlSelect(@Assisted Resource inst,
//			@AnnotStructBlockGeomModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl, IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, imImpl);
	}
	
	@Override
	public List<IHtmlOption> getOptions() {
		return InstAdpLibSupport.convertResourceCollectionToInstAdpCollection(
				imImpl.getOptions(rdfInst)
				, rdfInstAdpFactoryWrap
				, IHtmlOption.class
				, new ArrayList<IHtmlOption>());
	}

	@Override
	public List<IHtmlOption> getSelected() {
		Iterator<IHtmlOption> iter = this.getOptions().iterator();
		List<IHtmlOption> rez = new LinkedList<IHtmlOption>();
		while (iter.hasNext()) {
			IHtmlOption op = iter.next();
			if (op.isSelected())
				rez.add(op);
		}
		return rez;
	}
	
	@Override
	public String getText() {
		Iterator<IHtmlOption> iter = getSelected().iterator();
		StringBuffer sb = new StringBuffer();
		while (iter.hasNext()) {
			sb.append(iter.next().getText());
			if (iter.hasNext())
				sb.append("; ");
		}
		return sb.toString();
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
