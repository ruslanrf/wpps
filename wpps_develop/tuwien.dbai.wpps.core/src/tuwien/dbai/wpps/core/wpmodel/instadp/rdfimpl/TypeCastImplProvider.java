/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl;

import java.util.Map;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLib;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * TODO: all process of casting can be opimized. Function with memory, pool...
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 24, 2011 5:11:02 PM
 */
@Singleton // binding in Module. singleton
public class TypeCastImplProvider implements Provider<TypeCastImpl> {
	private static final Logger log = Logger.getLogger(TypeCastImplProvider.class);

//	private final WPMConfig config;
	
//	private final Model bgosModel;
	
	private final Map<EWPOntSubModel, Model> models;
	
	@Inject
	public TypeCastImplProvider(
			final WPOntSubModels wpOntSubModels
//			WPMConfig config,
//			@AnnotStructBlockGeomModel Model bgosModel
			) {
//		this.config = config;
//		this.bgosModel = bgosModel;
		
		models = Maps.transformValues(wpOntSubModels.getOntologyAdapterMap()
				, new Function<OntModelAdp, Model>() {
					@Override
					public Model apply(OntModelAdp arg0) {
						return arg0.getTopRdfModel();
					}
				}
				);
	}
	
	@Override
	public TypeCastImpl get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		TypeCastImpl impl = new TypeCastImpl();
		
		impl.canAs = new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				return TypeCastManagerLib.checkForFirstRdfInstTypeCanBeInstantiated((Resource)avars[0], (IRdfInstType) avars[1]
						, (EWPOntSubModel) avars[2], models);
			}
		};
		
		impl.as = new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				return TypeCastManagerLib.getFirstRdfInstTypeCanBeInstantiated((Resource)avars[0], (IRdfInstType) avars[1]
						, (EWPOntSubModel) avars[2], models);
			}
		};
		
		
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
	}

}
