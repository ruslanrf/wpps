/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpllib;

import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLib;
import tuwien.dbai.wpps.ontschema.QntVisualModelOnt;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 16, 2012 9:56:56 PM
 */
public class VisualObjectLib {
	private static final Logger log = Logger.getLogger(VisualObjectLib.class);
	
//	private static final double _getDoubleValue(final Resource inst, final Property prop
//			, final Model mdl) {
//		StmtIterator iter = mdl.listStatements(inst, prop, (RDFNode)null);
//		if (iter.hasNext()) {
//			final double rez = iter.next().getDouble();
//			if (iter.hasNext())
//				log.warn("Instance "+inst.getLocalName()+" has more than 1 "+prop.getLocalName()+" attribute.");
//			return rez;
//		}
//		throw new GeneralUncheckedException(log, "Instance does not have property \""+prop.getLocalName()+"\".");
//	}
	
	private static final void _setIntegerValue(final Resource inst, final Property prop, int val
			, final Model mdl) {
		mdl.addLiteral(inst, prop, val);
	}
	
//	private static final String _getStringValue(final Resource inst, final Property prop
//			, final Model mdl) {
//		StmtIterator iter = mdl.listStatements(inst, prop, (RDFNode)null);
//		if (iter.hasNext()) {
//			final String rez = iter.next().getString();
//			if (iter.hasNext())
//				log.warn("Instance "+inst.getLocalName()+" has more than 1 "+prop.getLocalName()+" attribute.");
//			return rez;
//		}
//		throw new GeneralUncheckedException(log, "Instance does not have property \""+prop.getLocalName()+"\".");
//	}
	
	public static final TColor getForegroundTColor(final Resource instRdf, final Model model) {
		final int sRGB = InstAdpLib.getValueAsInteger(instRdf, QntVisualModelOnt.hasFGSRGBColor, model);
		return TColor.newARGB(sRGB);
	}
	
	public static final void addForegroundTColor(final Resource instRdf, TColor color, final Model model) {
		_setIntegerValue(instRdf, QntVisualModelOnt.hasFGSRGBColor, color.toARGB(), model);
	}
	
	/**
	 * @param instRdf
	 * @param model
	 * @return null, of there is no such a statement.
	 */
	public static final TColor getBackgroundTColorSoft(final Resource instRdf, final Model model) {
		final Integer sRGB = InstAdpLib.getValueAsIntegerSoft(instRdf, QntVisualModelOnt.hasBGSRGBColor, model);
		return (sRGB==null)?null:TColor.newARGB(sRGB);
	}
	
	public static final void addBackgroundTColor(final Resource instRdf, TColor color, final Model model) {
		_setIntegerValue(instRdf, QntVisualModelOnt.hasBGSRGBColor, color.toARGB(), model);
	}
	
	public static final double compRGBDistance(final int argb1, final int argb2) {
		return compRGBDistance(TColor.newARGB(argb1), TColor.newARGB(argb2));
	}
	
	public static final double compRGBDistance(final TColor c1, final TColor c2) {
		return c1.distanceToRGB(c2);
	}
	
	public static final double compHSVDistance(final int argb1, final int argb2) {
		return compHSVDistance(TColor.newARGB(argb1), TColor.newARGB(argb2));
	}
	
	public static final double compHSVDistance(final TColor c1, final TColor c2) {
		return c1.distanceToHSV(c2);
	}
	
}
