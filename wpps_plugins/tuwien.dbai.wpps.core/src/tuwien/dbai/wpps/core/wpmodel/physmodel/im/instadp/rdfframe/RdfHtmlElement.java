/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe;

import toxi.color.TColor;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlButton;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlLink;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 5:49:27 PM
 */
public class RdfHtmlElement extends RdfInstanceAdp implements IHtmlElement {

	protected final IMImpl imImpl;
	
	/**
	 * @param inst
	 * @param rdfModel
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 * @param imImpl
	 */
	@Inject
	public RdfHtmlElement(
			@Assisted Resource inst, 
//			@AnnotStructBlockGeomModel Model rdfModel,
			RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			TypeCastImpl typeCastImpl
			, IMImpl imImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.imImpl = imImpl;
	}

	@Override
	public IHtmlLink getHtmlLink() {
		final Resource rez = imImpl.getHtmlLink(rdfInst);
		return (rez==null)?null:rdfInstAdpFactoryWrap.createAdp(rez, IHtmlLink.class);
	}
	
	@Override
	public boolean hasHtmlLink() {
		return imImpl.hasHtmlLink(rdfInst);
	}

	@Override
	public IHtmlButton getHtmlButton() {
		final Resource rez = imImpl.getHtmlButtonReference(rdfInst);
		return (rez==null)?null:rdfInstAdpFactoryWrap.createAdp(rez, IHtmlButton.class);
	}

	@Override
	public boolean hasHtmlButton() {
		return imImpl.hasHtmlButton(rdfInst);
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement#getForegroundTColor()
	 */
	@Override
	public TColor getForegroundTColor() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement#transparentBGColor()
	 */
	@Override
	public boolean transparentBGColor() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement#getBackgroundTColor()
	 */
	@Override
	public TColor getBackgroundTColor() {
		// TODO Auto-generated method stub
		return null;
	}

}
