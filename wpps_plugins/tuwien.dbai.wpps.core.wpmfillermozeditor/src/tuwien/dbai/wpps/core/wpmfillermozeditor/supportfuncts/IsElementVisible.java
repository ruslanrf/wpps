/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts;
import java.util.HashMap;
import java.util.Map;

import org.mozilla.interfaces.nsIDOMElement;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.mozcommon.MozDomUtils;

/**
 * Gives a very little advantage
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 13, 2011 9:28:38 PM
 */
public class IsElementVisible implements IFunction<nsIDOMElement, Boolean> {

	private final Map<nsIDOMElement, Boolean> map = new HashMap<nsIDOMElement, Boolean>(10000);
	
	private final GetDOMCSS2Properties props;
	
	public IsElementVisible(GetDOMCSS2Properties props) {
		this.props = props;
	}
	
	@Override
	public Boolean apply(nsIDOMElement avar) {
		Boolean vis = map.get(avar);
		if (vis == null) {
			vis = MozDomUtils.checkElementCSSVisibility(avar, props.apply(avar));
			map.put(avar, vis);
		}
		return vis;
	}
	
//	public Boolean apply(nsIDOMElement avar) {
//		return MozDomUtils.isElementVisible(avar, props.apply(avar));
//	}

}
