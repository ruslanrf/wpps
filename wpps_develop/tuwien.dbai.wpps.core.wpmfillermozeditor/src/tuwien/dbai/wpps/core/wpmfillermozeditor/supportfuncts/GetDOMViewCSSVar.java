/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMViewCSS;

import tuwien.dbai.wpps.common.callback.ISupplier;
import tuwien.dbai.wpps.mozcommon.MozDomUtils;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 13, 2011 9:35:40 PM
 */
@Deprecated
public class GetDOMViewCSSVar implements ISupplier<nsIDOMViewCSS> {

	private nsIDOMViewCSS viewCss = null;
	
	public GetDOMViewCSSVar(nsIDOMDocument document) {
		
	}
	
	@Override
	public nsIDOMViewCSS get() {
//		if (viewCss == null) {
//			viewCss = MozDomUtils.getDOMViewCSS(avar);
//		}
		return viewCss;
	}

}
