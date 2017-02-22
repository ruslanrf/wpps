/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.config.WPPSConfig.EOneToManyRelation;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.BoundinBlockLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.WebDocumentLib;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 23, 2011 3:28:01 PM
 */
@Singleton // binding in Module. singleton
public class WebDocumentBlockImplProvider implements Provider<WebDocumentBlockImpl> {

private static final Logger log = Logger.getLogger(WebDocumentBlockImplProvider.class);
	
	private final WPPSConfig config;
	
	private final OntModelAdp sbgModel;
	
	@Inject
	public WebDocumentBlockImplProvider(WPPSConfig config
			, @AnnotStructBlockGeomModel final OntModelAdp sbgModel) {
		this.config = config;
		this.sbgModel = sbgModel;
	}
	
	@Override
	public WebDocumentBlockImpl get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		WebDocumentBlockImpl impl = new WebDocumentBlockImpl();
		
		final EOneToManyRelation oneToMany = config.getStructOneToManyRelationMap()
				.get(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
		
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.getContainingBoundingBlocks = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoundinBlockLib.getContainingBoundingBlocksFromRdfContainer(
							(Resource)avars[0], sbgModel.getTopRdfModel());
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.getContainingBoundingBlocks = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoundinBlockLib.getContainingBoundingBlocks((Resource)avars[0],sbgModel.getTopRdfModel());
		} }; }
		
		// ---
		impl.containsTopPage = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return WebDocumentLib.containsTopPage((Resource)avars[0], (Resource)avars[1], sbgModel.getTopRdfModel());
		} };
		
		
		impl.getTopPage = new IArrFunction<Object, Object>() {
		@Override
		public Object apply(Object... avars) {
			return WebDocumentLib.getTopPage((Resource)avars[0], sbgModel.getTopRdfModel());
		} };
	
		impl.addTopPage = new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				WebDocumentLib.addTopPage((Resource)avars[0], (Resource)avars[1], sbgModel.getBottomRdfModel());
				return null;
		} };
		
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
	}

}
