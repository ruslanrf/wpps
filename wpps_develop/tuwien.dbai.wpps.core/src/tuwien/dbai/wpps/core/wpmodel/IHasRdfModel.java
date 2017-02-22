/**
 * File name: IOneModel.java
 * @created: Mar 30, 2011 11:30:44 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * Adapter which implements this interface operates on the one ont. model.
 * Actually it would be worth mentioning that interface {@link com.hp.hpl.jena.rdf.model#Model Model} which represents RDF model
 * wrap OWL model which can be represented by the {@link com.hp.hpl.jena.ontology#OntModel OntModel} interface.
 * 
 * @created: Mar 30, 2011 11:30:44 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public interface IHasRdfModel {
	/**
	 * Get the model where adapter is operates.
	 * 
	 * @return
	 */
	Model getRdfModel();
}
