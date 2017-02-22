/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.factory;

import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMDocument;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMText;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 *  Instance of this factory is generated automatically thanks to Guice.
 *  
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 11:31:45 PM
 */
public interface IDOMRdfInstAdpFactory {
	
	IDOMDocument createDocumentAdp(Resource inst);
	
	IDOMElement createElementAdp(Resource inst);
	
	IDOMText createTextAdp(Resource inst);

}
