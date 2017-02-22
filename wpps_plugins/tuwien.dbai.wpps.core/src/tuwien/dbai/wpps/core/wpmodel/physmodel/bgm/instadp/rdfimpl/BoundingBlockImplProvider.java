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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 22, 2011 8:31:57 PM
 */
@Singleton // binding in Module. singleton
public final class BoundingBlockImplProvider implements Provider<BoundingBlockImpl> {
	private static final Logger log = Logger.getLogger(BoundingBlockImplProvider.class);
	
	private final WPPSConfig config;
	
	private final OntModelAdp sbgModel;
	
	@Inject
	public BoundingBlockImplProvider(
			final WPPSConfig config
			, @AnnotStructBlockGeomModel final OntModelAdp sbgModel) {
		this.config = config;
		this.sbgModel = sbgModel;
	}
	
	@Override
	public BoundingBlockImpl get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		BoundingBlockImpl impl = new BoundingBlockImpl();
		
		final EOneToManyRelation oneToMany = config.getStructOneToManyRelationMap()
				.get(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.getContainingBoundingBlocks = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoundinBlockLib
							.getContainingBoundingBlocksFromRdfContainer(
									(Resource)avars[0], sbgModel.getTopRdfModel());
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.getContainingBoundingBlocks = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoundinBlockLib.getContainingBoundingBlocks(
							(Resource)avars[0],sbgModel.getTopRdfModel());
		} }; }
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.containsBlock = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoundinBlockLib.containsBlockInRdfContainer(
							(Resource)avars[0], (Resource)avars[1], sbgModel.getTopRdfModel());
			} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.containsBlock = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return BoundinBlockLib.containsBlock(
							(Resource)avars[0], (Resource)avars[1], sbgModel.getTopRdfModel());
		} }; }
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.getContainedBlocks = new IArrFunction<Object, Object>() {
				@Override
				public Object apply(Object... avars) {
					return BoundinBlockLib.getContainedBlocksFromRdfContainer(
							(Resource)avars[0], sbgModel.getTopRdfModel());
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.getContainedBlocks = new IArrFunction<Object, Object>() {
				@Override
				public Object apply(Object... avars) {
					return BoundinBlockLib.getContainedBlocks(
							(Resource)avars[0], sbgModel.getTopRdfModel());
		} }; }
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.addContainedBlock = new IArrFunction<Object, Object>() {
				@Override
				public Object apply(Object... avars) {
					BoundinBlockLib.appendContainedBlockToRdfSeq(
							(Resource)avars[0], (Resource)avars[1], sbgModel.getBottomRdfModel());
					return null;
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.addContainedBlock = new IArrFunction<Object, Object>() {
				@Override
				public Object apply(Object... avars) {
					BoundinBlockLib.addContainedBlock(
							(Resource)avars[0], (Resource)avars[1], sbgModel.getBottomRdfModel());
					return null;
		} }; }
		
		// ---
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.addContainedBlocks = new IArrFunction<Object, Object>() {
				@SuppressWarnings("unchecked")
				@Override public Object apply(Object... avars) {
					BoundinBlockLib.appendContainedBlocksToRdfSeq(
							(Resource)avars[0], (Collection<Resource>)avars[1], sbgModel.getBottomRdfModel());
					return null;
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.addContainedBlocks = new IArrFunction<Object, Object>() {
				@SuppressWarnings("unchecked")
				@Override public Object apply(Object... avars) {
					BoundinBlockLib.addContainedBlocks(
							(Resource)avars[0], (Collection<Resource>)avars[1], sbgModel.getBottomRdfModel());
					return null;
		} }; }
		
		
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
	}

}
