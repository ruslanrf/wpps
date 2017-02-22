/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 4, 2012 12:41:22 AM
 */
@Singleton @Deprecated
public class LMDynamicClassesManager {

	private final Map<String, Resource> map = new HashMap<String, Resource>();
	private final OntModelAdp modelAdp;
	
	@Inject
	public LMDynamicClassesManager(final OntModelAdp modelAdp) {
		this.modelAdp = modelAdp;
	}
	
	public Resource getClass(final String label) {
		return map.get(label);
	}
	
	public Resource initClass(final String label, final Resource superClass) {
		Resource res = null;
		if (modelAdp.isOntology()) {
			res = JenaModelsUtilLib.createNewOntClass((OntClass)superClass
					, modelAdp.getRdfModelAsOntModel(), modelAdp.getNameSpace());
		}
		else {
			res = JenaModelsUtilLib.createNewClass(superClass
					, modelAdp.getRdfModel(), modelAdp.getNameSpace());
		}
		modelAdp.getRdfModel().add(res, RDFS.label, label);
		map.put(label, res);
		return res;
	}
	
	public Resource initClass(final String label, final Resource superClass, final String uri) {
		Resource res = null;
		if (modelAdp.isOntology()) {
			res = JenaModelsUtilLib.createNewOntClass(uri, (OntClass)superClass
					, modelAdp.getRdfModelAsOntModel());
		}
		else {
			res = JenaModelsUtilLib.createNewClass(uri, superClass
					, modelAdp.getRdfModel());
		}
		modelAdp.getRdfModel().add(res, RDFS.label, label);
		map.put(label, res);
		return res;
	}
	
	public Resource initClass(final Resource superClass, final String uri) {
		Resource res = null;
		if (modelAdp.isOntology()) {
			res = JenaModelsUtilLib.createNewOntClass(uri, (OntClass)superClass
					, modelAdp.getRdfModelAsOntModel());
		}
		else {
			res = JenaModelsUtilLib.createNewClass(uri, superClass
					, modelAdp.getRdfModel());
		}
		return res;
	}
	
	private static final class WrappedKey {
		String label = null;
		Property mainProp = null;
		
		public WrappedKey(final String label, final Property mainProp) {
			this.label = label;
			this.mainProp = mainProp;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o instanceof WrappedKey) {
				final WrappedKey r = (WrappedKey)o;
				return label.equals(r.label) && mainProp.equals(r.mainProp);
			}
			else
				return super.equals(o);
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(label, mainProp);
		}
	}
	
}
