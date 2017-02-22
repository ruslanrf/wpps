/**
 * 
 */
package tuwien.dbai.wpps.ui.model;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 1, 2012 8:38:42 AM
 */
@Deprecated
public class Mapping1Typed {

	private final Map<EProperty, Object> propObjMap = new LinkedHashMap<EProperty, Object>();
	
	public void addMapping(EProperty objId, Object obj) {
Preconditions.checkArgument(objId.getDataClass().isInstance(obj), obj+" must be of type "+objId.getDataClass().getName());
		propObjMap.put(objId, obj);
	}
	
	public Object getMappedObject(EProperty objId) {
		return propObjMap.get(objId);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getMappedObjectAs(EProperty objId, Class<T> view) {
		return (T)propObjMap.get(objId);
	}
	
	public boolean hasMappedObject(EProperty objId) {
		return propObjMap.containsKey(objId);
	}
	
	public void rmMappedObject(EProperty objId) {
		propObjMap.remove(objId);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(500);
		for (Entry<EProperty, Object> e : propObjMap.entrySet()) {
			sb.append("["+e.getKey()+": ");
			sb.append(e.getValue());
			sb.append("]\n");
		}
		return sb.toString();
	}
	
}
