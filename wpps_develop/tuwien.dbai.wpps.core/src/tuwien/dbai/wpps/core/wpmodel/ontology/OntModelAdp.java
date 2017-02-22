/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.ontology;

import java.util.HashSet;
import java.util.Set;

import tuwien.dbai.wpps.core.wpmodel.IHasComplexRdfModel;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 26, 2011 6:11:18 PM
 */
public class OntModelAdp implements IHasComplexRdfModel {

	private final String nameSpace;
	/**
	 * @return the nameSpace
	 */
	public String getNameSpace() {
		return nameSpace;
	}
	
	private final Model bottomOntology;
	private final Model topOntology;
	
	@Override
	public Model getBottomRdfModel() {
		return bottomOntology;
	}

	@Override
	public Model getTopRdfModel() {
		return topOntology;
	}
	
	private Set<EWPOntSubModel> wpOntSubModelSet = new HashSet<EWPOntSubModel>();
	
	public Set<EWPOntSubModel> getWpOntSubModel() {
		return wpOntSubModelSet;
	}
	
	public void addWPOntSubModel(EWPOntSubModel wpOntSubModel) {
		wpOntSubModelSet.add(wpOntSubModel);
	}

	public OntModel getBottomRdfModelAsOntModel() {
		return (OntModel)bottomOntology;
	}
	
	public OntModel getTopRdfModelAsOntModel() {
		return (OntModel)topOntology;
	}
	
	private final boolean isOntology;
	public final boolean isOntology() {
		return isOntology;
	}
	
	public OntModelAdp(final String nameSpace
			, final Model bottomOntology
			, final Model topOntology
			, final boolean isOntology) {
		this.nameSpace = nameSpace;
		this.bottomOntology = bottomOntology;
		this.topOntology = topOntology;
		this.isOntology = isOntology;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof OntModelAdp) {
			final OntModelAdp tmp = (OntModelAdp)o;
			return nameSpace.equals(tmp.nameSpace) && bottomOntology == tmp.bottomOntology && topOntology == tmp.topOntology;
		}
		return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return nameSpace.hashCode();
	}

}
