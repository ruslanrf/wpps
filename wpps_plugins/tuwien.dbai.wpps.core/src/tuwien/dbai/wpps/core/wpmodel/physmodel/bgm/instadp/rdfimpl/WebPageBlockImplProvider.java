/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl;

import java.util.Collection;

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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.WebPageBlockLib;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 23, 2011 4:20:05 PM
 */
@Singleton // binding in Module. singleton
public class WebPageBlockImplProvider implements Provider<WebPageBlockImpl> {
	private static final Logger log = Logger.getLogger(WebPageBlockImplProvider.class);
	
	private final WPPSConfig config;
	
	private final OntModelAdp sbgModel;
	
	@Inject
	public WebPageBlockImplProvider(final WPPSConfig config
			, @AnnotStructBlockGeomModel final OntModelAdp sbgModel) {
		this.config = config;
		this.sbgModel = sbgModel;
	}

	@Override
	public WebPageBlockImpl get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		WebPageBlockImpl impl = new WebPageBlockImpl();
		
		final EOneToManyRelation oneToMany = config.getStructOneToManyRelationMap()
				.get(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
		
		// --- Bounding block ---
		
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

		// --- Box ---
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.containsBox = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return WebPageBlockLib.containsBoxInRdfContainer((Resource)avars[0]
							, (Resource)avars[1], sbgModel.getTopRdfModel());
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.containsBox = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return WebPageBlockLib.containsBox((Resource)avars[0]
							, (Resource)avars[1], sbgModel.getTopRdfModel());
		} }; }
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.getContainedBoxes = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return WebPageBlockLib.getContainedBoxesFromRdfContainer((Resource)avars[0], sbgModel.getTopRdfModel());
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.getContainedBoxes = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return WebPageBlockLib.getStructContainedBoxes((Resource)avars[0], sbgModel.getTopRdfModel());
		} }; }
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.addContainedBox = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					WebPageBlockLib.addContainedBoxToRdfSeq((Resource)avars[0]
							, (Resource)avars[1], sbgModel.getBottomRdfModel());
					return null;
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.addContainedBox = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					WebPageBlockLib.addContainedBox((Resource)avars[0], (Resource)avars[1], sbgModel.getBottomRdfModel());
					return null;
		} }; }
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.addContainedBoxes = new IArrFunction<Object, Object>() {
				@SuppressWarnings("unchecked")
				@Override public Object apply(Object... avars) {
					WebPageBlockLib.addContainedBoxesToRdfSeq((Resource)avars[0]
							, (Collection<Resource>)avars[1], sbgModel.getBottomRdfModel());
					return null;
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.addContainedBoxes = new IArrFunction<Object, Object>() {
				@SuppressWarnings("unchecked")
				@Override public Object apply(Object... avars) {
					WebPageBlockLib.addContainedBoxes((Resource)avars[0]
							, (Collection<Resource>)avars[1], sbgModel.getBottomRdfModel());
					return null;
		} }; }
		
		// ---
		impl.getParentPageBlock = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return WebPageBlockLib.getParentPage((Resource)avars[0], sbgModel.getTopRdfModel());
		} };
		
		// ---
		impl.containsChildPage = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return WebPageBlockLib.containsChildPage((Resource)avars[0], (Resource)avars[1], sbgModel.getTopRdfModel());
		} };
		
		// ---
		impl.getChildPages = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return WebPageBlockLib.getChildpages((Resource)avars[0], sbgModel.getTopRdfModel());
		} };
		
		// ---
		impl.addChildPage = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				WebPageBlockLib.addChildPage((Resource)avars[0], (Resource)avars[1], sbgModel.getBottomRdfModel());
				return null;
		} };
		
		// ---
		impl.containsViewPort = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return WebPageBlockLib.containsViewPort((Resource)avars[0], (Resource)avars[1], sbgModel.getTopRdfModel());
		} };
		
		// ---
		impl.getViewPort = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return WebPageBlockLib.getViewPort((Resource)avars[0], sbgModel.getTopRdfModel());
		} };
		
		impl.addViewPort = new IArrFunction<Object, Object>() {
			@Override
			public Object apply(Object... avars) {
				WebPageBlockLib.addViewPort((Resource)avars[0], (Resource)avars[1], sbgModel.getBottomRdfModel());
				return null;
		} };
		
//		else {
//			impl.setGetStructContainingBoundingBlocks(new IArrFunction<Object, Object>() {
//				@Override
//				public Object apply(Object... avars) {
//					throw new UnimplementedFunctionException(log);
//				}
//			});
//		}
		
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
		
	}
	
}
