/**
 * 
 */
package tuwien.dbai.wpps.colors;

import org.eclipse.swt.graphics.RGB;

import toxi.color.TColor;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 17, 2012 8:18:02 AM
 */
public final class ColorsUtil {
	
	public static final int floatToInt(float f) {
		int i = Math.round(f*255);
		return (i>255)?255:i;
	}
	
	public static RGB convertTColorToSWTRGB(TColor color) {
		int red = Math.round(color.red()*255);
		red = (red>255)?255:red;
		int green = Math.round(color.green()*255);
		green = (green>255)?255:green;
		int blue = Math.round(color.blue()*255);
		blue = (blue>255)?255:blue;
		return new RGB(red, green, blue);
	}
	
	public static TColor convertSWTRGBToTColor(RGB color) {
		return convertRGBToTColor(color.red, color.green, color.blue);
	}
	
	/**
	 * @param red from 0 to 255
	 * @param green from 0 to 255
	 * @param blue from 0 to 255
	 * @return
	 */
	public static TColor convertRGBToTColor(int red, int green, int blue) {
		return TColor.newRGB(red/255f,
				green/255f,
				blue/255f );
	}
	
	/**
	 * @param red from 0 to 255
	 * @param green from 0 to 255
	 * @param blue from 0 to 255
	 * @param a from 0 to 255
	 * @return
	 */
	public static TColor convertRGBAToTColor(int red, int green, int blue, int a) {
		return TColor.newRGBA(red/255f, green/255f, blue/255f, a/255f );
	}

}
