/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;

import tuwien.dbai.wpps.common.callback.IFunction;

/**
 * Gives an advantage!
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 13, 2011 10:00:08 PM
 */
public class ConvertToDOMElement implements IFunction<nsIDOMNode, nsIDOMElement> {

	private final Map<nsIDOMNode, nsIDOMElement> map = new HashMap<nsIDOMNode, nsIDOMElement>(10000);
	
	@Override
	public nsIDOMElement apply(nsIDOMNode avar) {
		nsIDOMElement el = map.get(avar);
		if (el == null) {
			// expensive!  org.mozilla.xpcom.XPCOMException: The function "QueryInterface" returned an error condition  (0x80004002)
			el = ((nsIDOMElement)avar.queryInterface(nsIDOMElement.NS_IDOMELEMENT_IID));
			map.put(avar, el);
		}
		return el;
	}
	
}
