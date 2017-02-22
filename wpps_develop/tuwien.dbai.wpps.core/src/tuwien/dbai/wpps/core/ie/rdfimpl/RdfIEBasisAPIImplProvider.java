/**
 * 
 */
package tuwien.dbai.wpps.core.ie.rdfimpl;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.ie.impllib.BasisRdfIEUtilLib;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 16, 2012 11:43:21 PM
 */
@Singleton // binding in Module. singleton
public class RdfIEBasisAPIImplProvider  implements Provider<IEBasisAPIImpl> {
private static final Logger log = Logger.getLogger(RdfIEBasisAPIImplProvider.class);
	
	private final WPPSConfig config;
	private final WPOntSubModels wpModels;
	
	@Inject
	public RdfIEBasisAPIImplProvider(WPPSConfig config
			, final WPOntSubModels wpModels) {
		this.config = config;
		this.wpModels = wpModels;
	}
	
	@Override
	public IEBasisAPIImpl get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		IEBasisAPIImpl impl = new IEBasisAPIImpl();
		
		// TODO: implement in case if we use classifiers (hierarchical reasoners)
		impl.setGetObjectsByType(new IArrFunction<Object, Object>() {
			@SuppressWarnings("unchecked")
			@Override public Object apply(Object... avars) {
				//return BasisRdfIEUtilLib.getInstancesByTypeWithHierarchicalClassifier((Class<IInstanceAdp>[])avars[0], wpModels);
				return BasisRdfIEUtilLib.getInstancesByType((Class<IInstanceAdp>[])avars[0], wpModels);
		} } );
		
//		// TODO: implement in case if we use classifiers (chirarchical reasoners)
//		impl.setGetObjectsFromRdfModel(new IArrFunction<Object, Object>() {
//			@Override public Object apply(Object... avars) {
//				return BasisRdfIEUtilLib.getBasisInstancesFromOntology( (EWPOntSubModel)avars[0]
//						, wpModels.getOntAdapter((EWPOntSubModel)avars[0]) );
//			} } );
		
		// TODO: implement in case if we use classifiers (chirarchical reasoners)
		impl.setGetObjects(new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BasisRdfIEUtilLib.getBasisInstancesFromOntologies(wpModels);
			} } );
		
		
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
	}

}
