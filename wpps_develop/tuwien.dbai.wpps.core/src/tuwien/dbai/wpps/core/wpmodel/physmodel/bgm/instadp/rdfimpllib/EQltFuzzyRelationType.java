/**
 * File name: EQltFuzzyRelationType.java
 * @created: Mar 31, 2011 5:18:33 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.bgm.instadps.lib;

import tuwien.dbai.wpps.core.models.IContainsRDFResource;

import com.hp.hpl.jena.ontology.OntClass;

/**
 * @type: EQltFuzzyRelationType
 *
 * @created: Mar 31, 2011 5:18:33 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public enum EQltFuzzyRelationType implements IContainsRDFResource {
	DISTANCE(null,
			new EQltFuzzyRelation[] {}); // TODO: create class in the ontology and instances to store info. about relations.
	
	EQltFuzzyRelationType(OntClass cls, EQltFuzzyRelation[] relArr) {
		this.cls = cls;
		this.relArr = relArr;
	}
	
	private OntClass cls = null;
	/**
	 * Get correspondent property in the Ontology
	 * @return
	 */
	@Override
	public OntClass getRdfResource() {
		return cls;
	}
	
	private EQltFuzzyRelation[] relArr = null;
	/**
	 * Get all relations of the relation type
	 * @return
	 */
	public EQltFuzzyRelation[] getRelations() {
		return relArr;
	}
	
}
