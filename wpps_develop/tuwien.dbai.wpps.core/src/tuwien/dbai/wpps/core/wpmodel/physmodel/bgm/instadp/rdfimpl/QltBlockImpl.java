/**
 * File name: QltBlockAF.java
 * @created: Jul 23, 2011 10:59:05 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.ICheckImplInitialization;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfQltBlock;

import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Directly, functionality for this class is used in following classes:
 * {@link RdfQltBlock}.
 * 
 * @created: Jul 23, 2011 10:59:05 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
@Singleton
public final class QltBlockImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(QltBlockImpl.class);

	public QltBlockImpl() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
	}
	
	IArrFunction<Object, Object> hasBasicIntervalRelation2D = null;
	IArrFunction<Object, Object> hasCompositeIntervalRelation2D = null;
	
	IArrFunction<Object, Object> hasBasicRCC8Relation = null;
	IArrFunction<Object, Object> hasCompositeRCC8Relation = null;
	
	IArrFunction<Object, Object> hasBasicPDirectionRelation = null;
	IArrFunction<Object, Object> hasCompositePDirectionRelation = null;
	
	IArrFunction<Object, Object> hasBasicODirectionRelation = null;
	IArrFunction<Object, Object> hasCompositeODirectionRelation = null;
	
	IArrFunction<Object, Object> hasBasicPositiveAlignmentRelation = null;
	IArrFunction<Object, Object> hasCompositeAndBasicNegativeAlignmentRelation = null;
	
	IArrFunction<Object, Object> hasBasicOrthogonalVisibilityRelation = null;
	IArrFunction<Object, Object> addBasicOrthogonalVisibilityRelation = null;
	IArrFunction<Object, Object> hasCompleteOrthogonalVisibilityRelation = null;
	
	IArrFunction<Object, Object> hasBasicOrIncompleteCompositeNeighboringRelation = null;
	IArrFunction<Object, Object> addBasicOrIncompleteCompositeNeighboringRelation = null;
	IArrFunction<Object, Object> hasCompleteCompositeNeighboringRelation = null;
	
	public boolean hasBasicIntervalRelation2D(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasBasicIntervalRelation2D.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasCompositeIntervalRelation2D(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasCompositeIntervalRelation2D.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasBasicRCC8Relation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasBasicRCC8Relation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasCompositeRCC8Relation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		if (hasCompositeRCC8Relation == null) System.err.println("hasCompositeRCC8Relation == null");
		return (Boolean)hasCompositeRCC8Relation.apply(
				primQltBlock,
				refQltBlock,
				relType);
	}
	
	public boolean hasBasicPDirectionRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasBasicPDirectionRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasCompositePDirectionRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasCompositePDirectionRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasBasicODirectionRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasBasicODirectionRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasCompositeODirectionRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasCompositeODirectionRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasBasicPositiveAlignmentRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasBasicPositiveAlignmentRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasCompositeAndBasicNegativeAlignmentRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasCompositeAndBasicNegativeAlignmentRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasBasicOrthogonalVisibilityRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasBasicOrthogonalVisibilityRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public void addBasicOrthogonalVisibilityRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		addBasicOrthogonalVisibilityRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasCompleteOrthogonalVisibilityRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasCompleteOrthogonalVisibilityRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasBasicOrIncompleteCompositeNeighboringRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasBasicOrIncompleteCompositeNeighboringRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public void addBasicOrIncompleteCompositeNeighboringRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		addBasicOrIncompleteCompositeNeighboringRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	public boolean hasCompleteCompositeNeighboringRelation(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation relType) {
		return (Boolean)hasCompleteCompositeNeighboringRelation.apply(primQltBlock, refQltBlock, relType);
	}
	
	// TODO add to the parent this check. Do it via reflection.
	@Override
	public boolean allFunctionsAreImplemented() {
		if (hasBasicIntervalRelation2D == null || hasCompositeIntervalRelation2D == null
				|| hasBasicRCC8Relation == null || hasCompositeRCC8Relation == null
				|| hasBasicPDirectionRelation == null || hasCompositePDirectionRelation == null
				|| hasBasicODirectionRelation == null || hasCompositeODirectionRelation == null
				|| hasBasicPositiveAlignmentRelation == null || hasCompositeAndBasicNegativeAlignmentRelation == null
				|| hasBasicOrthogonalVisibilityRelation == null
				|| addBasicOrthogonalVisibilityRelation == null
				|| hasCompleteOrthogonalVisibilityRelation == null
				|| hasBasicOrIncompleteCompositeNeighboringRelation == null
				|| addBasicOrIncompleteCompositeNeighboringRelation == null
				|| hasCompleteCompositeNeighboringRelation == null
			)
			return false;
		else
			return true;
	}

}
