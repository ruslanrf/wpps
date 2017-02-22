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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBoxType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.BoundinBlockLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.BoxLib;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 23, 2011 1:54:24 PM
 */
@Singleton // binding in Module. singleton
public class BoxImplProvider implements Provider<BoxImpl> {
	private static final Logger log = Logger.getLogger(BoxImplProvider.class);

	private final WPPSConfig config;
	
	private final OntModelAdp sbgModel;
	
	@Inject
	public BoxImplProvider(final WPPSConfig config
			, @AnnotStructBlockGeomModel final OntModelAdp sbgModel) {
		this.config = config;
		this.sbgModel = sbgModel;
	}
	
	@Override
	public BoxImpl get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		BoxImpl impl = new BoxImpl();
		
		final EOneToManyRelation oneToMany = config.getStructOneToManyRelationMap().get(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
		
		// --- Bounding block ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.getContainingBoundingBlocks = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoundinBlockLib.getContainingBoundingBlocksFromRdfContainer
							((Resource)avars[0], sbgModel.getTopRdfModel());
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.getContainingBoundingBlocks = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoundinBlockLib.getContainingBoundingBlocks((Resource)avars[0],sbgModel.getTopRdfModel());
		} }; }
		
		// ---
		impl.getInnerBlock = new IArrFunction<Object, Object>() {
		@Override public Object apply(Object... avars) {
			return BoxLib.getInnerBlock((Resource)avars[0], sbgModel.getTopRdfModel());
		} };
		
		// ---
		impl.addInnerBlock = new IArrFunction<Object, Object>() {
		@Override public Object apply(Object... avars) {
			BoxLib.addInnerBlock((Resource)avars[0], (Resource)avars[1], sbgModel.getBottomRdfModel());
			return null;
		} };
		
		// ---
		impl.getOuterBlock = new IArrFunction<Object, Object>() {
		@Override
		public Object apply(Object... avars) {
			return BoxLib.getOuterBlock((Resource)avars[0], sbgModel.getTopRdfModel());
		} };
		
		// ---
		impl.addOuterBlock = new IArrFunction<Object, Object>() {
		@Override public Object apply(Object... avars) {
			BoxLib.addOuterBlock((Resource)avars[0], (Resource)avars[1], sbgModel.getBottomRdfModel());
			return null;
		} };
		
		// ---
		impl.getBoxForInnerBlock = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BoxLib.getBoxForInnerBlock((Resource)avars[0], sbgModel.getTopRdfModel());
		} };
		
		// ---
		impl.getBoxForOuterBlock = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return BoxLib.getBoxForOuterBlock((Resource)avars[0], sbgModel.getTopRdfModel());
		} };
		
		// ---
		impl.getBoxType = new IArrFunction<Object, Object>() {
		@Override public Object apply(Object... avars) {
			return BoxLib.getBoxType((Resource)avars[0], sbgModel.getTopRdfModel());
		} };
		
		// ---
		impl.addBoxType = new IArrFunction<Object, Object>() {
		@Override public Object apply(Object... avars) {
			BoxLib.addBoxType((Resource)avars[0], (EBoxType)avars[1], sbgModel.getBottomRdfModel());
			return null;
		} };
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.getContainingPage = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoxLib.getStructContainingPageFromContainer((Resource)avars[0], sbgModel.getTopRdfModel());
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.getContainingPage = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoxLib.getStructContainingPage((Resource)avars[0], sbgModel.getTopRdfModel());
		} }; }
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.getClientRects = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoxLib.getClientRectsFromContainer((Resource)avars[0], sbgModel.getTopRdfModel());
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.getClientRects = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoxLib.getClientRects((Resource)avars[0], sbgModel.getTopRdfModel());
		} }; }
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.addClientRect = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				BoxLib.addClientRectToSeq((Resource)avars[0], (Resource)avars[1], sbgModel.getBottomRdfModel());
				return null;
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.addClientRect = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				BoxLib.addClientRect((Resource)avars[0], (Resource)avars[1], sbgModel.getBottomRdfModel());
				return null;
		} }; }
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.addClientRects = new IArrFunction<Object, Object>() {
				@SuppressWarnings("unchecked")
				@Override public Object apply(Object... avars) {
					BoxLib.addClientRectsToRdfSeq((Resource)avars[0]
							, (Collection<Resource>)avars[1], sbgModel.getBottomRdfModel());
					return null;
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.addClientRects = new IArrFunction<Object, Object>() {
				@SuppressWarnings("unchecked")
				@Override public Object apply(Object... avars) {
					BoxLib.addClientRects((Resource)avars[0]
							, (Collection<Resource>)avars[1], sbgModel.getBottomRdfModel());
					return null;
		} }; }
		
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
	}
}
