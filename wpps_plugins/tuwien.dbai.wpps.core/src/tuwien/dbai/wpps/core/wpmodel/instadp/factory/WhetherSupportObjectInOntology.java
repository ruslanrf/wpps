/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 1, 2012 7:42:38 PM
 */
@Singleton
public class WhetherSupportObjectInOntology implements IFunction<IRdfInstType, Boolean> {
	private static final Logger log = Logger.getLogger(WhetherSupportObjectInOntology.class);
	
	private final WPPSConfig config;
	private final WPOntSubModels models;
	
	@Inject
	public WhetherSupportObjectInOntology(final WPPSConfig config, final WPOntSubModels models) {
		this.config = config; 
		this.models = models;
	}
	
	@Override
	public Boolean apply(IRdfInstType avar) {
		if (models.getOntAdapter(avar.getWPSubModelType()) == null) {
			log.warn("Model "+avar.getWPSubModelType()+" does not exist, but creation of "+avar+" is requested.");
			return false;
		}
		else {
			return config.getSupportInOntology().contains(avar);
		}
	}

}
