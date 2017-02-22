/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.interfaces.nsIDOMNode;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.mozcommon.MozDomUtils;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 27, 2011 5:41:01 PM
 */
public class IsATFInternalElement implements IFunction<nsIDOMNode, Boolean> {

	private final Map<nsIDOMNode, Boolean> map = new HashMap<nsIDOMNode, Boolean>(10000);
	
	@Override
	public Boolean apply(final nsIDOMNode avar) {
		Boolean vis = map.get(avar);
		if (vis == null) {
			vis = MozDomUtils.isATFInternalElement(avar);
			map.put(avar, vis);
		}
		return vis;
	}

}
