/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp;

import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateObject;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;

/**
 * 
 * General functions which correspond to instances
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Aug 19, 2012 8:35:59 PM
 */
@Deprecated
public final class RdfInstLib {
	
	/**
	 * Checks if it is possible to create an object {@code o}
	 * according to the configuration provided.
	 * @param config configuration of the WPPS system
	 * @param ontModelAdp ontological model adapter
	 * @param o object to be created (individual or property in the ontology)
	 * @return
	 * @see WhetherCreateObject
	 */
	public static final boolean canCreate(final WPPSConfig config, final OntModelAdp ontModelAdp, Object o) {
		return ontModelAdp != null && config.getCreateInOntology().contains(o);
	}

}
