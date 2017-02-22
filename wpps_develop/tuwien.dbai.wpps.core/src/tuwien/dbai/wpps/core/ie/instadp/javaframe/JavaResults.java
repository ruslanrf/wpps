/**
 * 
 */
package tuwien.dbai.wpps.core.ie.instadp.javaframe;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import tuwien.dbai.wpps.core.ie.instadp.factory.ResultsRdfConverter;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.IWithRdfResourceRelation;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 13, 2012 11:44:46 PM
 */
public class JavaResults implements IResults, IWithRdfResourceRelation {

	private final List<IInstanceAdp> structContainedBlockList = new LinkedList<IInstanceAdp>();
	private final Set<IInstanceAdp> structContainedBlockSet = new LinkedHashSet<IInstanceAdp>();
	
	private final ResultsRdfConverter resultsRdfConverter;
	
	private Resource rdfInst = null;
	@Override
	public Resource getRdfResource() {
		return rdfInst;
	}
	@Override
	public void setRdfResource(Resource res) {
		rdfInst = res;
	}
	
	private final TypeCastImpl typeCastImpl;
	
	private final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap;
	
//	private final Map<Class<? extends IInstanceAdp>, Object> classObjectMap
//		= new HashMap<Class<? extends IInstanceAdp>, Object>();
	
	private String marker = null;
	private String grMarker = null;
	
	public JavaResults(final ResultsRdfConverter resultsRdfConverter
			, TypeCastImpl typeCastImpl
			, RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap) {
		this.resultsRdfConverter = resultsRdfConverter;
		this.typeCastImpl = typeCastImpl;
		this.rdfInstAdpFactoryWrap = rdfInstAdpFactoryWrap;
	}
	
	@Override
	public void addResult(IInstanceAdp res) {
		structContainedBlockSet.add(res);
		structContainedBlockList.add(res);
	}

	@Override
	public void addResults(Collection<? extends IInstanceAdp> res) {
		final Iterator<? extends IInstanceAdp> iter = res.iterator();
		while (iter.hasNext()) {
			addResult(iter.next());
		}
	}

	@Override
	public List<IInstanceAdp> getResultContent() {
		return structContainedBlockList;
	}

	@Override
	public IInstanceAdp get(int index) {
		return structContainedBlockList.get(index);
	}

	@Override
	public boolean containsResult(IInstanceAdp res) {
		return structContainedBlockSet.contains(res);
	}

	@Override
	public Class<IInstanceAdp> getResultClass() {
		return IInstanceAdp.class;
	}

	@Override
	public <U extends IInstanceAdp> U convertTo(Class<U> clazz) {
//		if (canAs(clazz))
//			return as(clazz);
//		else {
			final U u = resultsRdfConverter.convert(this, clazz);
			return u;
//		}
	}
	private <U extends IInstanceAdp> boolean canCreateAdp(Resource inst, Class<U> typ) {
		if (inst == null) return false;
		final Object[] res = TypeCastManagerLibSupport.getMainInstTypeAndModel(typ);
		return typeCastImpl.canAs(inst, (IRdfInstType)res[0], (EWPOntSubModel)res[1]);
	}
	
	@Override
	public <T extends IInstanceAdp> boolean canAs(Class<T> view) {
		if (view.isInstance(this))
			return true;
		return canCreateAdp(rdfInst, view);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends IInstanceAdp> T as(Class<T> view) {
		if (view.isInstance(this))
			return (T)this;
		return TypeCastManagerLibSupport.as(rdfInst, view, typeCastImpl, rdfInstAdpFactoryWrap);
	}
	@Override
	public void mapTo(IInstanceAdp adp) {
		if (adp instanceof IRdfResourceAdp
				&& ((IRdfResourceAdp)adp).getRdfResource() != null) {
			rdfInst = ((IRdfResourceAdp)adp).getRdfResource();
		}
	}
	
	@Override
	public int size() {
		return structContainedBlockList.size();
	}

//	@Override
//	public void addMarker(String mark) {
//		marker = mark;
//	}
//
//	@Override
//	public String getMarker() {
//		return marker;
//	}
//
//	@Override
//	public void addGroupMarker(String mark) {
//		grMarker = mark;
//		
//	}
//
//	@Override
//	public String getGroupMarker() {
//		return grMarker;
//	}

	@Override
	public Iterator<IInstanceAdp> iterator() {
		return structContainedBlockList.iterator();
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(1000);
		sb.append("\nResults::\n");
		if (size() == 0)
			sb.append("NULL");
		else {
			sb.append("[");
			for (IInstanceAdp adp : getResultContent()) {
				sb.append(adp);
				sb.append(", ");
			}
			sb.delete(sb.length()-2, sb.length());
			sb.append("]");
		}
		return sb.toString();
	}
	
	@Override
	public String getString() {
		return "";
	}

}
