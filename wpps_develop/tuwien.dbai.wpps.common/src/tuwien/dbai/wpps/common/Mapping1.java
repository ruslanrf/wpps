/**
 * 
 */
package tuwien.dbai.wpps.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 3, 2012 1:12:08 PM
 */
public class Mapping1 {

	private final Map<Object, Object> propObjMap = new LinkedHashMap<Object, Object>();
	
	public Map<Object, Object> getUnderlyingMap() {
		return propObjMap;
	}
	
	public void addMapping(Object objId, Object obj) {
		propObjMap.put(objId, obj);
	}
	
	public Object getMappedObject(Object objId) {
		return propObjMap.get(objId);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getMappedObjectAs(Object objId, Class<T> view) {
		return (T)propObjMap.get(objId);
	}
	
	public boolean hasMappedObject(Object objId) {
		return propObjMap.containsKey(objId);
	}
	
	public void rmMappedObject(Object objId) {
		propObjMap.remove(objId);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(500);
		for (Entry<Object, Object> e : propObjMap.entrySet()) {
			sb.append("["+e.getKey()+": ");
			sb.append(e.getValue());
			sb.append("]\n");
		}
		return sb.toString();
	}
}
