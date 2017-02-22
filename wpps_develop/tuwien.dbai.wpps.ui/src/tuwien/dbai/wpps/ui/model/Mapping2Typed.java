/**
 * 
 */
package tuwien.dbai.wpps.ui.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;

/**
 * Only properties of simple types should be contained here.
 *  
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 16, 2012 5:55:15 PM
 */
@Deprecated
public class Mapping2Typed {

	private final Map<Object, Map<EProperty, Object>> objPropObjMap = new LinkedHashMap<Object, Map<EProperty, Object>>();
	
	public void addMapping(final Object obj, final EProperty prop, final Object val) {
		Map<EProperty, Object> props = objPropObjMap.get(obj);
		if (props == null) {
			props = new LinkedHashMap<EProperty, Object>();
			objPropObjMap.put(obj, props);
		}
Preconditions.checkArgument(prop.getDataClass().isInstance(val), val+" must be of type "+prop.getDataClass().getName());
		props.put(prop, val);
	}
	
	public Object getMappedObject(Object obj, EProperty prop) {
		Map<EProperty, Object> props = objPropObjMap.get(obj);
		if (props == null)
			return null;
		return props.get(prop);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getMappedObjectAs(Object obj, EProperty prop, Class<T> view) {
		return (T)getMappedObject(obj, prop);
	}
	
	public boolean hasMappedObject(Object obj, EProperty prop) {
		Map<EProperty, Object> props = objPropObjMap.get(obj);
		if (props == null)
			return false;
		return props.containsKey(prop);
	}
	
	public void rmMappedObject(Object obj, EProperty prop) {
		Map<EProperty, Object> props = objPropObjMap.get(obj);
		if (props != null)
			props.remove(prop);
	}
	
	public void rmMappedObjects(Object obj) {
		objPropObjMap.remove(obj);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(500);
		for (Entry<Object, Map<EProperty, Object>> e : objPropObjMap.entrySet()) {
			sb.append("["+e.getKey()+": ");
			for (Entry<EProperty, Object> e2 : e.getValue().entrySet()) {
				sb.append("("+e2.getKey()+"; "+e2.getValue()+") ");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]\n");
		}
		return sb.toString();
	}
	
}
