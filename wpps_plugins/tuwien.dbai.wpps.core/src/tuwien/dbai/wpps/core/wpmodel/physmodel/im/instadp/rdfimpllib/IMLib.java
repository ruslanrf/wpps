/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpllib;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EFontStyle;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.ETextDecoration;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EWebFormMethod;
import tuwien.dbai.wpps.ontschema.InterfaceModelOnt;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 16, 2012 12:46:35 PM
 */
public final class IMLib {
	private static final Logger log = Logger.getLogger(IMLib.class);

//	// TODO: Correct!
//	public static final EIMInstType TagNameToEIMInstType(final String htmlEl
//			, final Map<String, String> htmlAttrMap, final Map<String, String> cssAttrMap) {
////		Preconditions.checkNotNull(htmlEl);
//		
////		return EHtmlElementConstants.valueOfStrRepr(htmlEl);
//		
//		return null;
//		
//	}
	
	// =====================
	// ===== IHtmlText =====
	// =====================
	
	/**
	 * @param box
	 * @return type of the box.
	 */
	@Deprecated
	public static final EFontStyle getFontStyle(final Resource res, final Model model) {
		return InstAdpLib.getObjectAsEnumSoft(EFontStyle.class, res, InterfaceModelOnt.hasFontStyle, model);
//		if (model.contains(res, InterfaceModelOnt.hasFontStyle, InterfaceModelOnt.NormalFontStyle))
//			return EFontStyle.NORMAL_FONT_STYLE;
//		else if (model.contains(res, InterfaceModelOnt.hasFontStyle, InterfaceModelOnt.Italic))
//			return EFontStyle.ITALIC;
//		else if (model.contains(res, InterfaceModelOnt.hasFontStyle, InterfaceModelOnt.Oblique))
//			return EFontStyle.OBLIQUE;
//		else {
//			throw new UnknownValueFromPredefinedList(log, "?");
//		}
	}
	
	@Deprecated
	public static final void addFontStyle(final Resource res, final EFontStyle fontStyle, final Model model) {
			model.add(res, InterfaceModelOnt.hasFontStyle, fontStyle.getRdfResource());
	}
	
	@Deprecated
	public static final ETextDecoration getTextDecoration(final Resource res, final Model model) {
		return InstAdpLib.getObjectAsEnumSoft(ETextDecoration.class, res, InterfaceModelOnt.hasTextDecoration, model);
//		if (model.contains(res, InterfaceModelOnt.hasTextDecoration, InterfaceModelOnt.NoTextDecoration))
//			return ETextDecoration.NONE;
//		else if (model.contains(res, InterfaceModelOnt.hasTextDecoration, InterfaceModelOnt.Underline))
//			return ETextDecoration.UNDERLINE;
//		else if (model.contains(res, InterfaceModelOnt.hasTextDecoration, InterfaceModelOnt.Overline))
//			return ETextDecoration.OVERLINE;
//		else if (model.contains(res, InterfaceModelOnt.hasTextDecoration, InterfaceModelOnt.LineThrough))
//			return ETextDecoration.LINE_THROUGH;
//		else if (model.contains(res, InterfaceModelOnt.hasTextDecoration, InterfaceModelOnt.Blink))
//			return ETextDecoration.BLINK;
//		else {
//			throw new UnknownValueFromPredefinedList(log, "?");
//		}
	}
	
	@Deprecated
	public static final void addTextDecoration(final Resource res, final ETextDecoration fontDecoration, final Model model) {
		model.add(res, InterfaceModelOnt.hasTextDecoration, fontDecoration.getRdfResource());
	}
	
	// =====================
	// ===== IHtmlWebForm =====
	// =====================
	
	@Deprecated
	public static final EWebFormMethod getWebFormMethod(final Resource res, final Model model) {
		return InstAdpLib.getObjectAsEnumSoft(EWebFormMethod.class, res, InterfaceModelOnt.hasWebFormMethod, model);
//		
//		if (model.contains(res, InterfaceModelOnt.hasWebFormMethod, InterfaceModelOnt.Post))
//			return EWebFormMethod.POST;
//		else if (model.contains(res, InterfaceModelOnt.hasFontStyle, InterfaceModelOnt.Get))
//			return EWebFormMethod.GET;
//		else {
//			throw new UnknownValueFromPredefinedList(log, "?");
//		}
	}
	
}
