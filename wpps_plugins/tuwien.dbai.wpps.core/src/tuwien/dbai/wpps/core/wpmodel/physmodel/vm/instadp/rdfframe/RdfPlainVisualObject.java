/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfframe;

import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.EVOQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IAbstractVisualObject;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IPlainVisualObject;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpl.PlainVisualObjectImpl;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 18, 2012 1:10:58 PM
 */
public class RdfPlainVisualObject extends RdfInstanceAdp implements IPlainVisualObject {
	private static final Logger log = Logger.getLogger(RdfPlainVisualObject.class);
	
private final PlainVisualObjectImpl plainVisualObjectImpl;
	
	/**
	 * @param inst
	 * @param strBGRdfModel it is StructBGM, because this adapter do not have corresponding class in structVM ontology.
	 * @param rdfInstAdpFactoryWrap
	 * @param typeCastImpl
	 * @param plainVisualObjectImpl
	 */
	@Inject
	public RdfPlainVisualObject(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model strBGRdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final PlainVisualObjectImpl plainVisualObjectImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.plainVisualObjectImpl = plainVisualObjectImpl;
	}


	@Override
	public TColor getForegroundTColor() {
		return plainVisualObjectImpl.getForegroundTColor(rdfInst);
	}


	@Override
	public double getRelationAsDouble(IAbstractVisualObject refInst,
			EVOQntRelationType relationClass) {
		super.testForRdfResourceInterface(refInst);
		switch (relationClass) {
		case FG_RGB_COLOR_DISTANCE:
			return plainVisualObjectImpl.getForegroundRGBColorDistance(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource());
		case FG_HSV_COLOR_DISTANCE:
			return plainVisualObjectImpl.getForegroundHSVColorDistance(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource());
		case BG_RGB_COLOR_DISTANCE:
			return plainVisualObjectImpl.getBackgroundRGBColorDistance(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource());
		case BG_HSV_COLOR_DISTANCE:
			return plainVisualObjectImpl.getBackgroundHSVColorDistance(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource());
		default:
			throw new UnknownValueFromPredefinedList(log, relationClass);
		}
	}

	@Override
	public TColor getBackgroundTColor() {
		TColor tc = plainVisualObjectImpl.getBackgroundTColor(rdfInst);
if (log.isTraceEnabled() && tc == null) log.trace("Object "+rdfInst+" does not have a background color");
		return tc;
	}
	
	@Override
	public boolean transparentBGColor() {
		final TColor c = plainVisualObjectImpl.getBackgroundTColor(rdfInst);
		return c == null || c.alpha() == 0;
	}
	
}
