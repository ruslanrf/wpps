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
 * @created Feb 24, 2012 8:07:31 PM
 */
@Singleton
public class WhetherCreateObject implements IFunction<IRdfInstType, Boolean> {
	private static final Logger log = Logger.getLogger(WhetherCreateObject.class);

	private final WPPSConfig config;
	private final WPOntSubModels models;
	
	@Inject
	public WhetherCreateObject(final WPPSConfig config, final WPOntSubModels models) {
		this.config = config; 
		this.models = models;
	}
	
	@Override
	public Boolean apply(IRdfInstType avar) {
		if (models.getOntAdapter(avar.getWPSubModelType()) == null) {
log.debug("Model "+avar.getWPSubModelType()+" does not exist, but creation of "+avar+" is requested.");
			return false;
		}
		else {
			return config.getCreateInOntology().contains(avar);
		}
	}
	
}

//@Singleton
//public class WhetherCreateObject extends FunctionWithMemory<IRdfInstType, Boolean> {
//	private static final Logger log = Logger.getLogger(WhetherCreateObject.class);
//	
//	@Inject
//	public WhetherCreateObject(final WPPSConfig config, final WPOntSubModels models) {
//		super(new IFunction<IRdfInstType, Boolean>() {
//			@Override public Boolean apply(final IRdfInstType avar) {
//				if (models.getOntAdapter(avar.getWPSubModelType()) == null) {
//log.warn("Model "+avar.getWPSubModelType()+" does not exist, but creation of "+avar+" is defined.");
//					return false;
//				}
//				else {
//					return config.getCreateInOntology().contains(avar);
//				}
//			} }, 50);
//	}
//}
