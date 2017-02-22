/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp;

import java.util.Objects;

import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 9:33:56 PM
 */
@Deprecated
public final class LMRelationsMap {
	
	private final BiMap<WrappedKey,Property> biMap = HashBiMap.create();
	private final OntModelAdp modelAdp;
	
	public LMRelationsMap(final OntModelAdp modelAdp) {
		this.modelAdp = modelAdp;
	}
	
	public Property getPropertyOrCreate(final String label, final Property mainProp) {
		final WrappedKey wk = new WrappedKey(label, mainProp);
		Property prop = biMap.get(wk);
		if (prop == null) {
			if (modelAdp.isOntology()) {
				prop = JenaModelsUtilLib.createNewObjectProperty((ObjectProperty)mainProp
						, modelAdp.getRdfModelAsOntModel(), modelAdp.getNameSpace());
			}
			else {
				prop = JenaModelsUtilLib.createNewProperty(mainProp
						, modelAdp.getRdfModel(), modelAdp.getNameSpace());
			}
			modelAdp.getRdfModel().add(prop, RDFS.label, label);
			biMap.put(wk, prop);
		}
		return prop;
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
