/**
 * File name: EQltFuzzyRelation.java
 * @created: Mar 31, 2011 5:20:43 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.bgm.instadps.lib;

import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @type: EQltFuzzyRelation
 * Relation such as Distance fo instance
 * @created: Mar 31, 2011 5:20:43 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public class EQltFuzzyRelation {
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private float start;
	public float getStart() {
		return start;
	}
	public void setStart(float start) {
		this.start = start;
	}
	private float end;
	public float getEnd() {
		return end;
	}
	public void setEnd(float end) {
		this.end = end;
	}
	
	private ObjectProperty prop = null;
	/**
	 * Get property in the ontology
	 * @return
	 */
	public ObjectProperty getOntProperty() {
		return prop;
	}
	
	/**
	 * Instance in the ontology which represent quantitative interval for this relation.
	 * It is a instance of class RelationScale
	 */
	private Resource instance = null;
	
	public Resource getInstance(){
		return instance;
	}
	
	private EQltFuzzyRelationType relType;
	void setRelationType(EQltFuzzyRelationType relType) {
		this.relType = relType;
	}
	public EQltFuzzyRelationType getRelationType() {
		return relType;
	}
	
}
