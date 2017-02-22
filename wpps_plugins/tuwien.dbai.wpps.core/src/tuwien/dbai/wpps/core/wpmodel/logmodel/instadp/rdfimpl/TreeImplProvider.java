/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.annotation.AnnotLogicalModel;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpllib.LogicalObjectLib;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 16, 2012 2:38:25 AM
 */
@Singleton // binding in Module. singleton
public class TreeImplProvider implements Provider<TreeImpl> {
	private static final Logger log = Logger.getLogger(TreeImplProvider.class);

	@SuppressWarnings("unused")
	private final WPPSConfig config;
	private final OntModelAdp rdfLogicModel;

	@Inject
	public TreeImplProvider(
			final WPPSConfig config
			, @AnnotLogicalModel final OntModelAdp rdfLogicModel) {
		this.config = config;
		this.rdfLogicModel = rdfLogicModel;
	}
	
	@Override
	public TreeImpl get() {
		if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		final TreeImpl impl = new TreeImpl();
		
		impl.setGetRoot(new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return LogicalObjectLib.getTreeRoot((Resource)avars[0], rdfLogicModel.getTopRdfModel());
		} } );
		
		
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
	}

}
