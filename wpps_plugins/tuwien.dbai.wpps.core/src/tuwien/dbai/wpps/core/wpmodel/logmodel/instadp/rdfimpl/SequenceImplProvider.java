/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl;

import java.util.Collection;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnimplementedFunctionException;
import tuwien.dbai.wpps.core.annotation.AnnotLogicalModel;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.config.WPPSConfig.EOneToManyRelation;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpllib.LogicalObjectLib;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 9, 2012 10:08:49 PM
 */
@Singleton // binding in Module. singleton
public class SequenceImplProvider implements Provider<SequenceImpl> {
	private static final Logger log = Logger.getLogger(SequenceImplProvider.class);

	private final WPPSConfig config;
	private final OntModelAdp rdfLogicModel;
	
	@Inject
	public SequenceImplProvider(
			final WPPSConfig config
			, @AnnotLogicalModel final OntModelAdp rdfLogicModel) {
		this.config = config;
		this.rdfLogicModel = rdfLogicModel;
	}
	
	@Override
	public SequenceImpl get() {
		if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		SequenceImpl impl = new SequenceImpl();
		
		final EOneToManyRelation oneToMany =  config.getStructOneToManyRelationMap().get(EWPOntSubModel.LOGICAL_MODEL);
		
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.setGetItems(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return LogicalObjectLib.getItemsFromSequenceFromSeq((Resource)avars[0], rdfLogicModel.getTopRdfModel());
			} } );
		} else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.setGetItems(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return LogicalObjectLib.getItemsFromSequence((Resource)avars[0], rdfLogicModel.getTopRdfModel());
			} } );
		} else {
			impl.setGetItems(new IArrFunction<Object, Object>() {
				@Override
				public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
				}
			});
		}
		
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.setAppendItem(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					LogicalObjectLib.appendItemToSequenceToSeq(
							(Resource)avars[0], (Resource)avars[1], rdfLogicModel.getBottomRdfModel());
					return null;
				} } );
		} else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.setAppendItem(new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					LogicalObjectLib.appendItemToSequence(
							(Resource)avars[0], (Resource)avars[1], rdfLogicModel.getBottomRdfModel());
					return null;
				} } );
		} else {
			impl.setAppendItem(new IArrFunction<Object, Object>() {
				@Override
				public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
				}
			});
		}
		
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.setAppendItems(new IArrFunction<Object, Object>() {
				@SuppressWarnings("unchecked") @Override public Object apply(Object... avars) {
					LogicalObjectLib.appendItemsToSequenceToSeq(
							(Resource)avars[0], (Collection<Resource>)avars[1], rdfLogicModel.getBottomRdfModel());
					return null;
			} } );
		} else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.setAppendItems(new IArrFunction<Object, Object>() {
				@SuppressWarnings("unchecked") @Override public Object apply(Object... avars) {
					LogicalObjectLib.appendItemsToSequence(
							(Resource)avars[0], (Collection<Resource>)avars[1], rdfLogicModel.getBottomRdfModel());
					return null;
			} } );
		} else {
			impl.setAppendItems(new IArrFunction<Object, Object>() {
				@Override
				public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
				}
			});
		}

		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
		
	}

}
