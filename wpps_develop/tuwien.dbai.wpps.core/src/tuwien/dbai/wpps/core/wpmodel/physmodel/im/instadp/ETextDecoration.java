/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp;

import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;
import tuwien.dbai.wpps.ontschema.InterfaceModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 14, 2012 1:29:23 PM
 */
public enum ETextDecoration implements IContainsRDFResource {
	/**
	 * none. Defines a normal text. This is default.
	 */
	NONE(InterfaceModelOnt.NoTextDecoration),
	/**
	 * underline. Defines a line below the text.
	 */
	UNDERLINE(InterfaceModelOnt.Underline),
	/**
	 * overline. Defines a line above the text.
	 */
	OVERLINE(InterfaceModelOnt.Overline),
	/**
	 * line-through. Defines a line through the text
	 */
	LINE_THROUGH(InterfaceModelOnt.LineThrough),
	/**
	 * blink. Defines a blinking text.
	 */
	BLINK(InterfaceModelOnt.Blink)
	;
	
	ETextDecoration(Resource fontStyle) {
		this.fontStyle = fontStyle;
	}
	private final Resource fontStyle;
	
	@Override
	public Resource getRdfResource() {
		return fontStyle;
	}
}
