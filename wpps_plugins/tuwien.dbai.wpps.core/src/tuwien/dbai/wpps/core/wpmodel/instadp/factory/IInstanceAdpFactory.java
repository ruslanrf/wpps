/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.factory;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 14, 2012 11:07:27 PM
 */
public interface IInstanceAdpFactory {

	IInstanceAdp createInstanceAdp(Resource inst);
	
}
