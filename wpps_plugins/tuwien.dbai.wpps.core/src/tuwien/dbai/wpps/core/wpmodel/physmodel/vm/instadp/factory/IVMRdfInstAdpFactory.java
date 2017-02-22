/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.factory;

import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IPlainVisualObject;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 18, 2012 1:07:00 PM
 */
public interface IVMRdfInstAdpFactory {

	IPlainVisualObject createPlainVisualObjectAdp(Resource inst);
	
}
