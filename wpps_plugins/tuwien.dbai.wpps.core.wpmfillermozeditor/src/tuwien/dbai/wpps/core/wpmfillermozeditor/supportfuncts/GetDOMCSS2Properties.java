/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.interfaces.nsIDOMCSS2Properties;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMViewCSS;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.mozcommon.MozDomUtils;

/**
 * Gives a very little advantage
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 13, 2011 9:32:13 PM
 */
public class GetDOMCSS2Properties implements IFunction<nsIDOMElement, nsIDOMCSS2Properties> {

	public final Map<nsIDOMElement, nsIDOMCSS2Properties> map = new HashMap<nsIDOMElement, nsIDOMCSS2Properties>(10000);
	
	private final nsIDOMViewCSS viewCss;
	
	public GetDOMCSS2Properties(final nsIDOMViewCSS viewCss) {
		this.viewCss = viewCss;
	}
	
	@Override
	public nsIDOMCSS2Properties apply(nsIDOMElement avar) {
		nsIDOMCSS2Properties props = map.get(avar);
		if (props == null) {
			props = MozDomUtils.getComputedCSSProperties(avar, viewCss);
			map.put(avar, props);
		}
		return props;
	}
	
//	public nsIDOMCSS2Properties apply(nsIDOMElement avar) {
//		return MozDomUtils.getComputedCSSProperties(avar, viewCss);
//	}
	
}
