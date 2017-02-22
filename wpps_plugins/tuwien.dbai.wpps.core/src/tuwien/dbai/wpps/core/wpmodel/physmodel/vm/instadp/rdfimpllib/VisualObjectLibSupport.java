/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpllib;

import toxi.color.TColor;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpl.PlainVisualObjectImpl;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 18, 2012 3:42:21 PM
 */
public class VisualObjectLibSupport {
//	private static final Logger log = Logger.getLogger(VisualObjectLibSupport.class);

	/**
	 * @param primInst
	 * @param refInst
	 * @param plainVisualObjectImpl
	 * @return
	 */
	public static final double compForegroundRGBTColorDistanceAsDouble(Resource primInst, Resource refInst
			, final PlainVisualObjectImpl plainVisualObjectImpl) {
		final TColor c1 = plainVisualObjectImpl.getForegroundTColor(primInst);
		final TColor c2 = plainVisualObjectImpl.getForegroundTColor(refInst);
		return VisualObjectLib.compRGBDistance(c1, c2);
	}
	
	/**
	 * @param primInst
	 * @param refInst
	 * @param plainVisualObjectImpl
	 * @return
	 */
	public static final double compForegroundHSVTColorDistanceAsDouble(Resource primInst, Resource refInst
			, final PlainVisualObjectImpl plainVisualObjectImpl) {
		final TColor c1 = plainVisualObjectImpl.getForegroundTColor(primInst);
		final TColor c2 = plainVisualObjectImpl.getForegroundTColor(refInst);
		return VisualObjectLib.compHSVDistance(c1, c2);
	}
	
	/**
	 * @param primInst
	 * @param refInst
	 * @param plainVisualObjectImpl
	 * @return
	 */
	public static final double compBackgroundRGBTColorDistanceAsDouble(Resource primInst, Resource refInst
			, final PlainVisualObjectImpl plainVisualObjectImpl) {
		final TColor c1 = plainVisualObjectImpl.getBackgroundTColor(primInst);
		final TColor c2 = plainVisualObjectImpl.getBackgroundTColor(refInst);
		return VisualObjectLib.compRGBDistance(c1, c2);
	}
	
	/**
	 * @param primInst
	 * @param refInst
	 * @param plainVisualObjectImpl
	 * @return
	 */
	public static final double compBackgroundHSVTColorDistanceAsDouble(Resource primInst, Resource refInst
			, final PlainVisualObjectImpl plainVisualObjectImpl) {
		final TColor c1 = plainVisualObjectImpl.getBackgroundTColor(primInst);
		final TColor c2 = plainVisualObjectImpl.getBackgroundTColor(refInst);
		return VisualObjectLib.compHSVDistance(c1, c2);
	}
	
}
