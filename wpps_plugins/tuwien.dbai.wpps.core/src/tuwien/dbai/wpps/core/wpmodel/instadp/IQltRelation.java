/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp;

import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;

import com.hp.hpl.jena.rdf.model.Property;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 17, 2011 6:46:33 PM
 */
public interface IQltRelation extends IContainsRDFResource {

	Property getProperty();
	
	boolean hasChildren();
	
//	List<IQltRelationType> getChildren();
	
	boolean hasParent();
	
//	List<IQltRelationType> getParents();
	
	boolean isBasicRelation();
	
}
