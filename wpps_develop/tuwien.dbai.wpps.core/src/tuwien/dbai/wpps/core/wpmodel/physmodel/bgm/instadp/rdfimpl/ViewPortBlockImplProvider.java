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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 23, 2011 3:18:56 PM
 */
@Singleton // binding in Module. singleton
public class ViewPortBlockImplProvider implements Provider<ViewPortBlockImpl> {
	private static final Logger log = Logger.getLogger(ViewPortBlockImplProvider.class);
	
	private final WPPSConfig config;
	
	private final OntModelAdp sbgModel;
	
	@Inject
	public ViewPortBlockImplProvider(WPPSConfig config
			, @AnnotStructBlockGeomModel final OntModelAdp sbgModel) {
		this.config = config;
		this.sbgModel = sbgModel;
	}
	
	@Override
	public ViewPortBlockImpl get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		ViewPortBlockImpl impl = new ViewPortBlockImpl();
		
		final EOneToManyRelation oneToMany = config.getStructOneToManyRelationMap().get(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
		
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
		
//		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
//			impl.setStructContainsBlock(new IArrFunction<Object, Object>() {
//				@Override
//				public Object apply(Object... avars) {
//					return CompositeBlockLib.checkBlockContainmentFromSeq((Resource)avars[0], (Resource)avars[1], (Model)avars[2]);
//				}
//			});
//		}
//		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
//			impl.setStructContainsBlock(new IArrFunction<Object, Object>() {
//				@Override
//				public Object apply(Object... avars) {
//					return CompositeBlockLib.checkBlockContainment((Resource)avars[0], (Resource)avars[1], (Model)avars[2]);
//				}
//			});
//		}
//		else {
//			impl.setStructContainsBlock(new IArrFunction<Object, Object>() {
//				@Override
//				public Object apply(Object... avars) {
//					throw new UnimplementedFunctionException(log);
//				}
//			});
//		}
//		
//		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
//			impl.setGetStructContainedBlocks(new IArrFunction<Object, Object>() {
//				@Override
//				public Object apply(Object... avars) {
//					return CompositeBlockLib.getStructContainedBlocksFromSeq((Resource)avars[0], (Model)avars[1]);
//				}
//			});
//		}
//		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
//			impl.setGetStructContainedBlocks(new IArrFunction<Object, Object>() {
//				@Override
//				public Object apply(Object... avars) {
//					return CompositeBlockLib.getcontainedTopPage((Resource)avars[0], (Model)avars[1]);
//				}
//			});
//		}
//		else {
//			impl.setGetStructContainedBlocks(new IArrFunction<Object, Object>() {
//				@Override
//				public Object apply(Object... avars) {
//					throw new UnimplementedFunctionException(log);
//				}
//			});
//		}
//		
//		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
//			impl.setAddStructContainedBlock(new IArrFunction<Object, Object>() {
//				@Override
//				public Object apply(Object... avars) {
//					CompositeBlockLib.addContainedBlockToSeq((Resource)avars[0], (Resource)avars[1], (Model)avars[2]);
//					return null;
//				}
//			});
//		}
//		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
//			impl.setAddStructContainedBlock(new IArrFunction<Object, Object>() {
//				@Override
//				public Object apply(Object... avars) {
//					CompositeBlockLib.addContainedBlock((Resource)avars[0], (Resource)avars[1], (Model)avars[2]);
//					return null;
//				}
//			});
//		}
//		else {
//			impl.setAddStructContainedBlock(new IArrFunction<Object, Object>() {
//				@Override
//				public Object apply(Object... avars) {
//					throw new UnimplementedFunctionException(log);
//				}
//			});
//		}
//		
//		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
//			impl.setAddStructContainedBlocks(new IArrFunction<Object, Object>() {
//				@SuppressWarnings("unchecked")
//				@Override
//				public Object apply(Object... avars) {
//					CompositeBlockLib.appendContainedBlocksToSeq((Resource)avars[0], (List<Resource>)avars[1], (Model)avars[2]);
//					return null;
//				}
//			});
//		}
//		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
//			impl.setAddStructContainedBlocks(new IArrFunction<Object, Object>() {
//				@SuppressWarnings("unchecked")
//				@Override
//				public Object apply(Object... avars) {
//					CompositeBlockLib.addContainedBlocks((Resource)avars[0], (List<Resource>)avars[1], (Model)avars[2]);
//					return null;
//				}
//			});
//		}
//		else {
//			impl.setAddStructContainedBlocks(new IArrFunction<Object, Object>() {
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
