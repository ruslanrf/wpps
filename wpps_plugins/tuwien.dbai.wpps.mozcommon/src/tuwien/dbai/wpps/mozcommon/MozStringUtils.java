/**
 * 
 */
package tuwien.dbai.wpps.mozcommon;

import java.awt.Color;

import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.colors.ColorsUtil;
import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.html.ECSSPropertyConstants;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 9, 2011 2:50:04 PM
 */
public final class MozStringUtils {
	private static final Logger log = Logger.getLogger(MozStringUtils.class);

//	public static String getNumericalPart
	
	public static final String getNumericalPartWOPx(final String str) {
		return str.replaceFirst("px", "");
	}
	
	public static final String[] parseFontFamily(final String str) {
		return str.split(",");
	}
	
	public static final float parseFontWeight(final String str) {
		try{
			return Float.parseFloat(str);
		}
		catch (java.lang.NumberFormatException e) {
			if (ECSSPropertyConstants.FONT_WEIGHT_BOLD_VALUE.string().equals(str))
				return 700;
			else {
log.warn(TSForLog.getTS(log)+"Wrong value: "+str);
				return 100;
			}
		}
	}
	
	public static final Color getColorFromCSSAttribute(final String str) {
		if (str.contains("rgba")) {
			final String[] strArr = str.substring(4, str.length()-1).split(", ");
			return new Color(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1])
					, Integer.parseInt(strArr[2]), Integer.parseInt(strArr[3]));
		}
		else if (str.contains("rgb")) {
			final String[] strArr = str.substring(4, str.length()-1).split(", ");
			return new Color(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1]), Integer.parseInt(strArr[2]));
		}
		else if (str.contains(ECSSPropertyConstants.COLOR_TRANSPARENT_VALUE.string()))
			return null;
		else
			throw new GeneralUncheckedException(log, "Unknown color's string format: "+str);
	}
	
	public static final TColor getTColorFromCSSAttributeColor(final String str) {
		if (str.contains("rgba")) {
			final String[] strArr = str.substring(5, str.length()-1).split(", ");
			return ColorsUtil.convertRGBAToTColor(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1])
					, Integer.parseInt(strArr[2]), Integer.parseInt(strArr[3]));
		}
		else if (str.contains("rgb")) {
			final String[] strArr = str.substring(4, str.length()-1).split(", ");
			return ColorsUtil.convertRGBToTColor(Integer.parseInt(strArr[0]), Integer.parseInt(strArr[1])
					, Integer.parseInt(strArr[2]));
		}
		else if (str.contains(ECSSPropertyConstants.COLOR_TRANSPARENT_VALUE.string()))
			return null;
		else
			throw new GeneralUncheckedException(log, "Unknown color's string format: "+str);
	}
	
	public static final TColor getTColorFromCSSAttributeBGColor(final String str) {
		if (str.contains("rgba")) {
			final String[] strArr = str.substring(5, str.length()-1).split(", ");
			return ColorsUtil.convertRGBAToTColor((int)Math.round(Double.parseDouble(strArr[0]))
					, (int)Math.round(Double.parseDouble(strArr[1]))
					, (int)Math.round(Double.parseDouble(strArr[2]))
					, (int)Math.round(Double.parseDouble(strArr[3])));
		}
		else if (str.contains("rgb")) {
			final String[] strArr = str.substring(4, str.length()-1).split(", ");
			return ColorsUtil.convertRGBToTColor((int)Math.round(Double.parseDouble(strArr[0]))
					, (int)Math.round(Double.parseDouble(strArr[1]))
					, (int)Math.round(Double.parseDouble(strArr[2])));
		}
		else
			return null;
//		else
//			throw new GeneralUncheckedException(log, "Unknown color's string format: "+str);
	}
	
}
