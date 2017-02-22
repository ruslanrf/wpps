/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp;

import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;
import tuwien.dbai.wpps.ontschema.InterfaceModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 4, 2012 3:52:05 PM
 */
public enum EWebFormMethod implements IContainsRDFResource {

	POST(InterfaceModelOnt.Post), GET(InterfaceModelOnt.Get);
	
	EWebFormMethod(Resource method) {
		this.method = method;
	}
	private final Resource method;
	
	@Override
	public Resource getRdfResource() {
		return method;
	}

}
