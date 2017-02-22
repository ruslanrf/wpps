/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp;

import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;

import com.hp.hpl.jena.rdf.model.Property;

/**
 * Root of all group of quantitative relations
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 17, 2011 6:28:56 PM
 */
public interface IQltRelationType extends IContainsRDFResource {

	Property getProperty();
	
//	IQltRelationType[] getQltRelationTypes();
	
}
