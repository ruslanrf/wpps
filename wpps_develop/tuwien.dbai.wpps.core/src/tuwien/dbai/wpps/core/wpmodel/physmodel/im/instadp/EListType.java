/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp;

import tuwien.dbai.wpps.core.wpmodel.IContainsRDFResource;
import tuwien.dbai.wpps.ontschema.InterfaceModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 6, 2012 12:21:49 AM
 */
public enum EListType implements IContainsRDFResource {
	
	ORDERED(InterfaceModelOnt.OrderedList),
	UNORDERED(InterfaceModelOnt.UnorderedList),
	
	;

	EListType(Resource btnType) {
		this.btnType = btnType;
	}
	private final Resource btnType;
	
	@Override
	public Resource getRdfResource() {
		return btnType;
	}

}
