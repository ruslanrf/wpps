/**
 * 
 */
package tuwien.dbai.wpps.common;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 4, 2012 1:11:09 AM
 */
public class Mapping1_1N_generic <T, U> {
	
	private final Map<T, Collection<U>> propObjMap = new LinkedHashMap<T, Collection<U>>();
	
	public Map<T, Collection<U>> getMap() {
		return propObjMap;
	}
	
	public static enum ECollectionType {SET, LIST}
	
	private final ECollectionType colType;
	
	public Mapping1_1N_generic(ECollectionType colType) {
		this.colType = colType;
	}
	
	public void addMapping(T objId, U obj) {
		Collection<U> c = propObjMap.get(objId);
		if (c == null) {
			c = _createCollection(colType);
			propObjMap.put(objId, c);
		}
		c.add(obj);
	}
	
	private Collection<U> _createCollection(final ECollectionType colType) {
		switch(colType) {
		case LIST:
			return new LinkedList<U>();
		case SET:
			return new LinkedHashSet<U>();
		}
		return new LinkedList<U>();
	}
	
	private Collection<U> _createEmptyCollection(final ECollectionType colType) {
		switch(colType) {
		case LIST:
			return Collections.emptyList();
		case SET:
			return Collections.emptySet();
		}
		return Collections.emptyList();
	}
	
	/**
	 * @param objId
	 * @return never null
	 */
	public Collection<U> getMappedObjects(T objId) {
		Collection<U> c = propObjMap.get(objId);
		if (c == null)
			return _createEmptyCollection(colType);
		else
			return c;
	}
	
	public boolean contains(T objId, U objVal) {
		Collection<U> c = propObjMap.get(objId);
		if (c == null)
			return false;
		else {
			return c.contains(objVal);
		}
	}
	
//	public Object getMappedObject(Object objId) {
//		return propObjMap.get(objId);
//	}
	
//	@SuppressWarnings("unchecked")
//	public <T> T getMappedObjectAs(Object objId, Class<T> view) {
//		return (T)propObjMap.get(objId);
//	}
	
//	public boolean hasMappedObject(Object objId) {
//		return propObjMap.containsKey(objId);
//	}
	
//	public void rmMappedObject(Object objId) {
//		propObjMap.remove(objId);
//	}
	
//	@Override
//	public String toString() {
//		StringBuffer sb = new StringBuffer(500);
//		for (Entry<Object, Object> e : propObjMap.entrySet()) {
//			sb.append("["+e.getKey()+": ");
//			sb.append(e.getValue());
//			sb.append("]\n");
//		}
//		return sb.toString();
//	}
}
