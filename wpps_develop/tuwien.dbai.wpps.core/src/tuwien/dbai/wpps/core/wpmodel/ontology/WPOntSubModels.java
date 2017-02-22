/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.ontology;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.Mapping1_1N_generic;
import tuwien.dbai.wpps.common.Mapping1_1N_generic.ECollectionType;
import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.IInstanceAdpFactory;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 26, 2011 1:37:26 AM
 */
@Singleton
public final class WPOntSubModels {
	private static final Logger log = Logger.getLogger(WPOntSubModels.class);
	
	@Inject
	public WPOntSubModels(final IInstanceAdpFactory instanceAdpFactory) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing "+this.getClass().getSimpleName());
		this.instanceAdpFactory = instanceAdpFactory;
	}
	
	private final Map<EWPOntSubModel, OntModelAdp> ontAdpMap = new HashMap<EWPOntSubModel, OntModelAdp>();
	
	private Object source = null;
	
	private final IInstanceAdpFactory instanceAdpFactory;
	
	private final Map<Resource, Object> resourceDOMNodeMap1 = new LinkedHashMap<Resource, Object>(10000);
	private final Mapping1_1N_generic<Object, Resource> domNodeResourceMap1N
			= new Mapping1_1N_generic<Object, Resource>(ECollectionType.SET);
	
	/**
	 * For Mozilla Based Editor, it is instance of top {@code nsIDOMWindow} of a web browser.
	 * @return
	 */
	public Object getSource() {
		return source;
	}
	
	public void setSource(Object source) {
		this.source = source;
	}
	
	/**
	 * For Mozilla Based Editor, it is instance of {@code nsIDOMNode}.
	 * @param instAdp
	 * @return
	 */
	public Object getSourceObjectForAdp(IInstanceAdp instAdp) {
		if (instAdp instanceof IRdfResourceAdp)
			return resourceDOMNodeMap1.get(((IRdfResourceAdp)instAdp).getRdfResource());
		return null;
	}
	
	public Object getSourceObjectForRdfResource(Resource res) {
			return resourceDOMNodeMap1.get(res);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Set<Resource> getRdfResourcesForSourceObject(Object o) {
		return (Set)domNodeResourceMap1N.getMappedObjects(o);
	}
	
	/**
	 * Function returns mapping  {@linkplain EWPOntSubModel} -&gt; Resource.
	 * It checks the real containment of the resource in a model.
	 * @param o
	 * @return
	 */
	public Map<EWPOntSubModel, Resource> getRdfResourcesForSourceObjectAsMap_expensive(Object o) {
		final Set<Entry<EWPOntSubModel, OntModelAdp>> moSet = ontAdpMap.entrySet();
		final Map<EWPOntSubModel, Resource> rez = new LinkedHashMap<EWPOntSubModel, Resource>(15);
		final Iterator<Resource> iter = domNodeResourceMap1N.getMappedObjects(o).iterator();
		while (iter.hasNext()) {
			final Resource res = iter.next();
			final Iterator<Entry<EWPOntSubModel, OntModelAdp>> iter2 = moSet.iterator();
			while (iter2.hasNext()) {
				final Entry<EWPOntSubModel, OntModelAdp> e = iter2.next();
				if ( res.getModel().equals(e.getValue().getTopRdfModel())
					|| e.getValue().getTopRdfModel().containsResource(res)
						) {
if (log.isDebugEnabled() && (rez.get(e.getKey()) != null)) {
	log.error("This must never happen: 2 resources (individuals), which correspond to the same source object" +
			" are under the same sub-model! Submodel:"+e.getKey()+", source object:"+o+", res:"+res);
}
					rez.put(e.getKey(), res);
				}
			}
		}
		return rez;
	}
	
	public Map<EWPOntSubModel, Resource> getRdfResourcesForSourceObjectAsMap_expensive(
			Object o
			, EWPOntSubModel... subModel) {
		final Map<EWPOntSubModel, Resource> rez = new LinkedHashMap<EWPOntSubModel, Resource>(15);
		final Iterator<Resource> iter = domNodeResourceMap1N.getMappedObjects(o).iterator();
		while (iter.hasNext()) {
			final Resource res = iter.next();
			for (int i=0; i<subModel.length; i++) {
				final OntModelAdp oma = ontAdpMap.get(subModel[i]);
				if ( oma != null
						&& ( res.getModel().equals(oma.getTopRdfModel())
								|| oma.getTopRdfModel().containsResource(res) )
						) {
if (log.isDebugEnabled() && (rez.get(subModel[i]) != null)) {
	log.error("This must never happen: 2 resources (individuals), which correspond to the same source object" +
			" are under the same sub-model! Submodel:"+subModel[i]+", source object:"+o+", res:"+res);
}
					rez.put(subModel[i], res);
				}
			}
		}
		return rez;
	}
	
	public void setSourceObjectResourceMapping(final Object sourceObj, Resource res) {
		resourceDOMNodeMap1.put(res, sourceObj);
		domNodeResourceMap1N.addMapping(sourceObj, res);
	}
	
	public IInstanceAdp adaptResource(Resource res) {
		return instanceAdpFactory.createInstanceAdp(res);
	}
	
	/**
	 * @return the ontology
	 */
	public Map<EWPOntSubModel, OntModelAdp> getOntologyAdapterMap() {
		return ontAdpMap;
	}
	
	public void addOntAdapter(EWPOntSubModel ewpOntModel, OntModelAdp ontAdp) {
		ontAdpMap.put(ewpOntModel, ontAdp);
		ontAdp.addWPOntSubModel(ewpOntModel);
	}

	public OntModelAdp getOntAdapter(EWPOntSubModel ewpOntModel) {
		return ontAdpMap.get(ewpOntModel);
	}
	
	public Set<OntModelAdp> getOntModelAdpList() {
		if (ontAdpMap.size() == 0)
			return Collections.emptySet();
		final Set<OntModelAdp> rez = new HashSet<OntModelAdp>(ontAdpMap.size());
		for (final Entry<EWPOntSubModel, OntModelAdp> e : ontAdpMap.entrySet()) {
			rez.add(e.getValue());
		}
		return rez;
	}
	
	public Set<Model> getOntModelSet() {
		if (ontAdpMap.size() == 0)
			return Collections.emptySet();
		final Set<Model> rez = new HashSet<Model>(ontAdpMap.size());
		for (final Entry<EWPOntSubModel, OntModelAdp> e : ontAdpMap.entrySet()) {
			rez.add(e.getValue().getTopRdfModel());
		}
		return rez;
	}
	
	/**
	 * TODO: When SBGM will be transfered into IM remove this. webpage there will have this property.
	 */
	public Map<Resource, String> webpageUrlMap = new HashMap<Resource, String>();
	
}
