/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp;

import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 24, 2011 3:09:00 PM
 */
public interface IRdfInstType extends IContainsRDFResource {
	
	Class<? extends IInstanceAdp> getJavaInterface();
	
	boolean isCanBeInstantiated();
	
	boolean hasChildren();
	
	List<IRdfInstType> getChildren();
	
	boolean isMainType();
	
	EWPOntSubModel getWPSubModelType();
	
//	IRdfInstType getMainRoot();
	
}
