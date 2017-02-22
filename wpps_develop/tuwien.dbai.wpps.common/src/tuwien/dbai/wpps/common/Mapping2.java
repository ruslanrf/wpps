/**
 * 
 */
package tuwien.dbai.wpps.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 22, 2012 1:34:15 AM
 */
public class Mapping2 {

private Map<Object, Map<Object, Object>> objPropObjMap = new LinkedHashMap<Object, Map<Object, Object>>();
	
	public void addMapping(final Object obj, final Object prop, final Object val) {
		Map<Object, Object> props = objPropObjMap.get(obj);
		if (props == null) {
			props = new LinkedHashMap<Object, Object>();
			objPropObjMap.put(obj, props);
		}
		props.put(prop, val);
	}
	
	public Object getMappedObject(Object obj, Object prop) {
		Map<Object, Object> props = objPropObjMap.get(obj);
		if (props == null)
			return null;
		return props.get(prop);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getMappedObjectAs(Object obj, Object prop, Class<T> view) {
		return (T)getMappedObject(obj, prop);
	}
	
	public boolean hasMappedObject(Object obj, Object prop) {
		Map<Object, Object> props = objPropObjMap.get(obj);
		if (props == null)
			return false;
		return props.containsKey(prop);
	}
	
	public boolean hasPrimaryObject(Object obj) {
		return objPropObjMap.containsKey(obj);
	}
	
	public void rmMappedObject(Object obj, Object prop) {
		Map<Object, Object> props = objPropObjMap.get(obj);
		if (props != null)
			props.remove(prop);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(500);
		for (Entry<Object, Map<Object, Object>> e : objPropObjMap.entrySet()) {
			sb.append("["+e.getKey()+": ");
			for (Entry<Object, Object> e2 : e.getValue().entrySet()) {
				sb.append("("+e2.getKey()+"; "+e2.getValue()+") ");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("]\n");
		}
		return sb.toString();
	}
	
}
