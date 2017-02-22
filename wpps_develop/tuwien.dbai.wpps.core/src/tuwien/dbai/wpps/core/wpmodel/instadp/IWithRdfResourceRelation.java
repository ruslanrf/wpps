/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 31, 2012 6:42:27 PM
 */
public interface IWithRdfResourceRelation extends IRdfResourceAdp {

	void setRdfResource(Resource res);
	
}
