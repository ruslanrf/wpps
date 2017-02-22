/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.interfaces.nsIDOMNode;

import tuwien.dbai.wpps.common.callback.IFunction;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 7, 2012 10:24:27 PM
 */
public class GetObjectForNode implements IFunction<nsIDOMNode, Object> {
	
	public static final String NONE = "NONE-913baa36-10bd-11e2-9f79-00247e160239";
	
	public static final int N = 10000;
	
	private final Map<nsIDOMNode, Object> map = new HashMap<nsIDOMNode, Object>(N);
	
	public void add(nsIDOMNode node, Object o) {
		map.put(node, o);
	}

	@Override
	public Object apply(final nsIDOMNode node) {
		if (node == null)
			return null;
		Object o = rec(node);
		if (o instanceof String && ((String)o).equals(NONE))
			return null;
		return o;
	}
	
	private final Object rec(nsIDOMNode node) {
		Object o = map.get(node);
		if (o!=null) return o;
		nsIDOMNode parent = node.getParentNode();
		if (parent == null) {
			map.put(node, NONE);
			return NONE;
		}
		o = rec(parent);
		map.put(node, o);
		return o;
	}
	
}
