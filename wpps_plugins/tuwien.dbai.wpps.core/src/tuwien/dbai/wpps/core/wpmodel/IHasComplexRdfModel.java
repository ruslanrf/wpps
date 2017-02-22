/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel;

import com.hp.hpl.jena.rdf.model.Model;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Aug 4, 2013 1:23:37 PM
 */
public interface IHasComplexRdfModel {

	Model getBottomRdfModel();
	
	Model getTopRdfModel();
	
}
