/**
 * 
 */
package tuwien.dbai.wpps.core.ie.instadp.javaframe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tuwien.dbai.wpps.core.ie.instadp.factory.GenericResultsRdfConverter;
import tuwien.dbai.wpps.core.ie.instadp.factory.ResultsObjectsFactory;
import tuwien.dbai.wpps.core.ie.instadp.interf.IGenericResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.IJavaObjAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.IWithRdfResourceRelation;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * TODO: implement addMarker, groupMarker properly?
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 14, 2012 6:05:53 PM
 * @see ResultsObjectsFactory
 */
@Deprecated
public class JavaGenericResults<T extends IInstanceAdp> implements IGenericResults<T>, IJavaObjAdp, IWithRdfResourceRelation {
//	private static final Logger log = Logger.getLogger(JavaResults.class);

	private final List<T> structContainedBlockList = new LinkedList<T>();
	private final Set<T> structContainedBlockSet = new LinkedHashSet<T>();
	
	private final Class<T> resultClazz;
	
	private final GenericResultsRdfConverter resultsRdfConverter;
	
//	private final RdfObjectsFactoryWrap objectsFactoryWrap;
	
	@Override
	public Class<T> getResultClass() {
		return resultClazz;
	}
	
	public JavaGenericResults(final Class<T> resultClazz
			, final GenericResultsRdfConverter resultsRdfConverter 
//			, final RdfObjectsFactoryWrap objectsFactoryWrap
			) {
		this.resultClazz = resultClazz;
		this.resultsRdfConverter = resultsRdfConverter;
//		this.objectsFactoryWrap = objectsFactoryWrap;
	}
	
	@Override
	public <U extends IInstanceAdp> boolean canAs(final Class<U> view) {
		if (view.isInstance(this))
			return true;
		return classObjectMap.containsKey(view);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <U extends IInstanceAdp> U as(final Class<U> view) {
		if (view.isInstance(this))
			return (U)this;
		return (U)classObjectMap.get(view);
	}

	@Override
	public void addResult(final T res) {
		structContainedBlockSet.add(res);
		structContainedBlockList.add(res);
	}

	@Override
	public void addResults(final Collection<? extends T> res) {
		final Iterator<? extends T> iter = res.iterator();
		while (iter.hasNext()) {
			addResult(iter.next());
		}
	}

	@Override
	public List<T> getResultContent() {
		return structContainedBlockList;
	}

	@Override
	public boolean containsResult(final T res) {
		return structContainedBlockSet.contains(res);
	}

	private final Map<Class<? extends IInstanceAdp>, Object> classObjectMap
			= new HashMap<Class<? extends IInstanceAdp>, Object>();
	private Resource rdfInst = null;
	
	@Override
	public Resource getRdfResource() {
		return rdfInst;
	}
	
	@Override
	public void setRdfResource(Resource res) {
		rdfInst = res;
	}
	
	@Override
	public <U extends IInstanceAdp> U convertTo(final Class<U> clazz) {
		if (canAs(clazz))
			return as(clazz);
		else {
			final U u = resultsRdfConverter.convert(this, clazz);
			if (rdfInst == null && u instanceof IRdfResourceAdp)
				rdfInst = ((IRdfResourceAdp)u).getRdfResource();
			classObjectMap.put(clazz, u);
			return u;
		}
	}
	
	@Override
	public T get(int index) {
		return structContainedBlockList.get(index);
	}
	
	@Override
	public int size() {
		return structContainedBlockList.size();
	}

	@Override
	public Iterator<T> iterator() {
		return structContainedBlockList.iterator();
	}

	@Override
	public String getString() {
		return null;
	}

	private String marker = null;
	@Override
	public void addMarker(String mark) {
		marker = mark;
	}

	@Override
	public String getMarker() {
		return marker;
	}

	private String grMarker = null;
	@Override
	public void addGroupMarker(String mark) {
		grMarker = mark;
	}

	@Override
	public String getGroupMarker() {
		return grMarker;
	}

}
