/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp;

import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;
import tuwien.dbai.wpps.ontschema.InterfaceModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 3, 2012 5:58:16 PM
 */
public enum EFontStyle implements IContainsRDFResource {
		NORMAL_FONT_STYLE(InterfaceModelOnt.NormalFontStyle),
		ITALIC(InterfaceModelOnt.Italic),
		OBLIQUE(InterfaceModelOnt.Oblique)
	;
	
	EFontStyle(Resource fontStyle) {
		this.fontStyle = fontStyle;
	}
	private final Resource fontStyle;
	
	@Override
	public Resource getRdfResource() {
		return fontStyle;
	}

}
