/**
 * File name: IContainsResource.java
 * @created: Apr 14, 2011 11:26:47 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Interface which is needed to indicate that java object which implements this interface
 * contains a resource in the ontology.
 *
 * @created: Apr 14, 2011 11:26:47 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public interface IContainsRDFResource {

	/**
	 * @return RDF resource
	 */
	Resource getRdfResource();
}
