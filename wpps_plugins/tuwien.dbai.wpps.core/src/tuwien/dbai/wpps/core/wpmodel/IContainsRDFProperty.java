/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel;

import com.hp.hpl.jena.rdf.model.Property;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 10:58:56 PM
 */
public interface IContainsRDFProperty {

	/**
	 * @return RDF resource
	 */
	Property getRdfProperty();
	
}
