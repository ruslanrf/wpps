/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp;

import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;
import tuwien.dbai.wpps.ontschema.InterfaceModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 1:54:00 PM
 */
public enum EHtmlButtonType implements IContainsRDFResource {
	
	NORMAL(InterfaceModelOnt.HtmlNormalButton),
	RESET(InterfaceModelOnt.HtmlResetButton),
	SUBMIT(InterfaceModelOnt.HtmlSubmitButton),
	
	;

	EHtmlButtonType(Resource btnType) {
		this.btnType = btnType;
	}
	private final Resource btnType;
	
	@Override
	public Resource getRdfResource() {
		return btnType;
	}

}
