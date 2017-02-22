/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import tuwien.dbai.wpps.common.exceptions.UnimplementedFunctionException;
import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 23, 2012 8:04:48 PM
 */
public class InstAdpLibSupport {

	/**
	 * Check if {@code inst} is instance of {@linkplain IRdfResourceAdp}, if true then get its underlying rdf instance (object).
	 * @param inst can be null
	 * @return
	 */
	public static final Resource getResourceOrNull(IInstanceAdp inst) {
		return (inst == null)?null:
				( inst instanceof IContainsRDFResource && ((IContainsRDFResource)inst).getRdfResource() != null )
				?((IRdfResourceAdp)inst).getRdfResource():null;
	}
	
	// TODO: move to commons
	public static final <T> Collection<T> createCollection(Collection<?> col, Class<T> transClass) {
		return createSpecCollection(col, transClass, col.size());
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" }) 
	@Deprecated
	public static final <T> Collection<T> createCollection(Collection<?> col, Class<T> transClass, int size) {
		if (ArrayList.class.equals(col.getClass())) {
			return (size>0)?(Collection) new ArrayList<T>():(Collection) new ArrayList<T>(size);
	} else if (LinkedList.class.equals(col.getClass()) || List.class.isAssignableFrom(col.getClass())) {
		return (Collection) new LinkedList<T>();
	} else if (HashSet.class.equals(col.getClass())) {
		return (size>0)?(Collection) new HashSet<T>():(Collection) new HashSet<T>(size);
	} else if (LinkedHashSet.class.equals(col.getClass()) || Set.class.isAssignableFrom(col.getClass())) {
		return (size>0)?(Collection) new LinkedHashSet<T>():(Collection) new LinkedHashSet<T>(size);
	}
	else throw new UnimplementedFunctionException();
	}
	
//	@SuppressWarnings("unchecked")
//	public static final <U, T extends Collection<U>> T createSpecCollection(Collection<?> col, Class<U> transClass, int size) {
//		if (ArrayList.class.equals(col.getClass())) {
//			return (size>0)?(T) new ArrayList<U>():(T) new ArrayList<U>(size);
//		} else if (LinkedList.class.equals(col.getClass()) || List.class.isAssignableFrom(col.getClass())) {
//			return (T) new LinkedList<U>();
//		} else if (HashSet.class.equals(col.getClass())) {
//			return (size>0)?(T) new HashSet<U>():(T) new HashSet<U>(size);
//		} else if (LinkedHashSet.class.equals(col.getClass()) || Set.class.isAssignableFrom(col.getClass())) {
//			return (size>0)?(T) new LinkedHashSet<U>():(T) new LinkedHashSet<U>(size);
//		}
//		else throw new UnimplementedFunctionException();
//	}
	
	public static final <U> Collection<U> createSpecCollection(Collection<?> col, Class<U> transClass, int size) {
		return createSpecCollection2(col.getClass(), transClass, size);
	}
	
	public static final <U> Collection<U> createSpecCollection2(
			@SuppressWarnings("rawtypes") Class<? extends Collection> col
			, Class<U> transClass, int size) {
		if (ArrayList.class.equals(col)) {
			return (size>0)? new ArrayList<U>(): new ArrayList<U>(size);
		} else if (LinkedList.class.equals(col) || List.class.isAssignableFrom(col)) {
			return new LinkedList<U>();
		} else if (HashSet.class.equals(col)) {
			return (size>0)? new HashSet<U>():new HashSet<U>(size);
		} else if (LinkedHashSet.class.equals(col) || Set.class.isAssignableFrom(col)) {
			return (size>0)? new LinkedHashSet<U>(): new LinkedHashSet<U>(size);
		}
		else throw new UnimplementedFunctionException();
	}
	
	public static final <U extends IInstanceAdp>
		Collection<U> convertResourceCollectionToInstAdpCollection(
				Collection<Resource> col
				, RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap
				, Class<U> clazz) {
		Collection<U> rez = createCollection(col, clazz);
		return fillCollectionWithAdaptedResourcesValues(col, rdfInstAdpFactoryWrap, clazz, rez);
	}
	
//	public static final <U extends IInstanceAdp>
//		Collection<U> convertResourceCollectionToInstAdpCollection(
//				Collection<Resource> col
//				, RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap
//				, Class<U> clazz
//				, @SuppressWarnings("rawtypes") Class<? extends Collection> colClazz) {
//		Collection<U> rez = createSpecCollection2(colClazz, clazz, col.size());
//		return fillCollectionWithAdaptedResourcesValues(col, rdfInstAdpFactoryWrap, clazz, rez);
//}
	
	public static final <U extends IInstanceAdp, T extends Collection<U>>
	T convertResourceCollectionToInstAdpCollection(
			Collection<Resource> col
			, RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap
			, Class<U> clazz
			, T colClazz) {
		if (ArrayList.class.isInstance(colClazz))
			((ArrayList<U>)colClazz).ensureCapacity(col.size());
	return fillCollectionWithAdaptedResourcesValues(col, rdfInstAdpFactoryWrap, clazz, colClazz);
}
	
	
	public static final <U extends IInstanceAdp, T extends Collection<U>>
			T fillCollectionWithAdaptedResourcesValues(
					Collection<Resource> col
					, RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap
					, Class<U> clazz
					, T rezult) {
		for (final Resource r: col) {
			rezult.add(rdfInstAdpFactoryWrap.createAdp(r, clazz));
		}	
		return rezult;
	}
	
	// ===========
	// === MIX ===
	// ===========
	
//	public static final <T> T getFirstFromCollectionOfLength1(
//			Collection<T> col) {
//		
//		
//		log.warn("Rdf resource "+inst.getLocalName()+" has more than 1 "+prop.getLocalName()+" attribute.");
//		
//	}
	
	
}