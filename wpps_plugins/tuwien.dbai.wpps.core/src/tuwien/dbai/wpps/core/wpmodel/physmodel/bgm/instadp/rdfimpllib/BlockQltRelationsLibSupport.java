/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib;

import static tuwien.dbai.wpps.core.fuzzy.ComparatorLib.equal;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.core.fuzzy.IMuZeroDouble;
import tuwien.dbai.wpps.core.fuzzy.Nu;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QltBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl;

import com.google.common.base.Preconditions;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

//TODO: implement automatic computations according to the chierarchy of relations
/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 17, 2011 11:58:45 PM
 */
public final class BlockQltRelationsLibSupport {
	private static final Logger log = Logger.getLogger(BlockQltRelationsLibSupport.class);

	/**
	 * Compute basic interval relations based on the quantitative information.
	 * @param qltPrimBlock primary block
	 * @param qltRefBlock relative block
	 * @param rel interval relation
	 * @param qntBlockImpl implementations of "quantitative block"
	 * @param muPtMin weight function for comparing first points of intervals
	 * @param muPtMax weight function for comparing second points of intervals
	 * @param nu membership function for intervals
	 * @return
	 */
	public static final Boolean compHasBasicIntervalRelation2DBasedOnQntFeatures(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation rel, final QntBlockImpl qntBlockImpl
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(rel.getRelationType() == EBlockQltRelationType.IR2D);
Preconditions.checkArgument(rel.isBasicRelation()); }

		if (rel.getParents().contains(EBlockQltRelation.INTERVAL_RELATION_X)) {
			final Double primPtMin = qntBlockImpl.getXMin(qltPrimBlock);
			final Double primPtMax = qntBlockImpl.getXMax(qltPrimBlock);
			final Double refPtMin = qntBlockImpl.getXMin(qltRefBlock);
			final Double refPtMax = qntBlockImpl.getXMax(qltRefBlock);
			
			if (primPtMin == null || primPtMax == null || refPtMin == null || refPtMax == null) {
log.warn("Cannot compute value!");
				return null; }
			
			switch(rel) {
			case AFTER_X:
				return BlockQltRelationsLib.hasIRAfterComp(primPtMin, refPtMax, muPtMin, muPtMax, nu);
			case BEFORE_X:
				return BlockQltRelationsLib.hasIRBeforeComp(primPtMax, refPtMin, muPtMin, muPtMax, nu);
			case INSIDE_X:
				return BlockQltRelationsLib.hasIRInsideComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
			case CONTAINS_X:
				return BlockQltRelationsLib.hasIRContainsComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
			case EQUALS_X:
				return BlockQltRelationsLib.hasIREqualsComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
			case FINISHED_BY_X:
				return BlockQltRelationsLib.hasIRFinishedByComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
			case FINISHES_X:
				return BlockQltRelationsLib.hasIRFinishesComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
			case TOUCHES_X:
				return BlockQltRelationsLib.hasIRTouchesComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
			case TOUCHED_BY_X:
				return BlockQltRelationsLib.hasIRTouchedByComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
			case OVERLAPED_BY_X:
				return BlockQltRelationsLib.hasIROverlappedByComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
			case OVERLAPS_X:
				return BlockQltRelationsLib.hasIROverlapsComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
			case STARTED_BY_X:
				return BlockQltRelationsLib.hasIRStartedByComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
			case STARTS_X:
				return BlockQltRelationsLib.hasIRStartsComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
			} 
		} else if (rel.getParents().contains(EBlockQltRelation.INTERVAL_RELATION_Y)) {
				final Double primPtMin = qntBlockImpl.getYMin(qltPrimBlock);
				final Double primPtMax = qntBlockImpl.getYMax(qltPrimBlock);
				final Double refPtMin = qntBlockImpl.getYMin(qltRefBlock);
				final Double refPtMax = qntBlockImpl.getYMax(qltRefBlock);
				
				if (primPtMin == null || primPtMax == null || refPtMin == null || refPtMax == null) {
					log.warn("Cannot compute value!");
									return null; }
				switch(rel) {
				case AFTER_Y:
					return BlockQltRelationsLib.hasIRAfterComp(primPtMin, refPtMax, muPtMin, muPtMax, nu);
				case BEFORE_Y:
					return BlockQltRelationsLib.hasIRBeforeComp(primPtMax, refPtMin, muPtMin, muPtMax, nu);
				case INSIDE_Y:
					return BlockQltRelationsLib.hasIRInsideComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
				case CONTAINS_Y:
					return BlockQltRelationsLib.hasIRContainsComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
				case EQUALS_Y:
					return BlockQltRelationsLib.hasIREqualsComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
				case FINISHED_BY_Y:
					return BlockQltRelationsLib.hasIRFinishedByComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
				case FINISHES_Y:
					return BlockQltRelationsLib.hasIRFinishesComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
				case TOUCHES_Y:
					return BlockQltRelationsLib.hasIRTouchesComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
				case TOUCHED_BY_Y:
					return BlockQltRelationsLib.hasIRTouchedByComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
				case OVERLAPED_BY_Y:
					return BlockQltRelationsLib.hasIROverlappedByComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
				case OVERLAPS_Y:
					return BlockQltRelationsLib.hasIROverlapsComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
				case STARTED_BY_Y:
					return BlockQltRelationsLib.hasIRStartedByComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
				case STARTS_Y:
					return BlockQltRelationsLib.hasIRStartsComp(primPtMin, primPtMax, refPtMin, refPtMax, muPtMin, muPtMax, nu);
				}
		}
		throw new UnknownValueFromPredefinedList(log, rel);
	}
	
	/**
	 * It always returns true for composite interval relations such as {@linkplain EBlockQltRelation#INTERVAL_RELATION},
	 * {@linkplain EBlockQltRelation#INTERVAL_RELATION_X}, {@linkplain EBlockQltRelation#INTERVAL_RELATION_Y}.
	 * @param rel
	 * @return
	 */
	public static final Boolean compHasCompositeIntervalRelation2DTautology(final EBlockQltRelation rel) {
//	public static final Boolean compHasCompositeIntervalRelation2D2(final Resource qltPrimBlock, final Resource qltRefBlock
//			, final EBlockQltRelation relType, final QntBlockImpl qltBlockImpl) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(rel.getRelationType() == EBlockQltRelationType.IR2D);
Preconditions.checkArgument(!rel.isBasicRelation()); }
		switch (rel) {
		case INTERVAL_RELATION_X:
		case INTERVAL_RELATION_Y:
		case INTERVAL_RELATION:
			return true;
		}
		throw new UnknownValueFromPredefinedList(log, rel);
	}
	
	
	/**
	 * Compute basic "positive" alignment relations based on the quantitative information.
	 * Basic "negative" relations such as {@linkplain EBlockQltRelation#NO_HORIZONTALLY_ALIGNED_WITH}
	 * and {@linkplain EBlockQltRelation#NO_VERTICALLY_ALIGNED_WITH} are not implemented here.
	 * @param qltPrimBlock
	 * @param qltRefBlock
	 * @param relType
	 * @param model
	 * @param qntBlockImpl
	 * @param muPtMin
	 * @param muPtMax
	 * @param muPtCenter
	 * @param nu
	 * @return
	 */
	public static final Boolean compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation relType, final QntBlockImpl qntBlockImpl
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final IMuZeroDouble muPtCenter
			, final Nu nu) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(relType.getRelationType() == EBlockQltRelationType.Alignment);
Preconditions.checkArgument(relType.isBasicRelation()); }
		
		Double primPt = null;
		Double refPt = null;
		IMuZeroDouble mu = null;
		
		if (relType.getParents().contains(EBlockQltRelation.VERTICALLY_ALIGNED_WITH)) {
			switch(relType) {
			case LEFT_ALIGNED_WITH: {
				primPt = qntBlockImpl.getXMin(qltPrimBlock);
				refPt = qntBlockImpl.getXMin(qltRefBlock);
				mu = muPtMin;
				break;
			}
			case RIGHT_ALIGNED_WITH: {
				primPt = qntBlockImpl.getXMax(qltPrimBlock);
				refPt = qntBlockImpl.getXMax(qltRefBlock);
				mu = muPtMax;
				break;
			}
			case CENTERED_HORIZONTALLY_WITH: {
				primPt =   qntBlockImpl.getXCenter(qltPrimBlock);
				refPt = qntBlockImpl.getXCenter(qltRefBlock);
				mu = muPtCenter;
				break;
			}
			default:
				throw new UnknownValueFromPredefinedList(log, relType);
			}
		} else if (relType.getParents().contains(EBlockQltRelation.HORIZONTALLY_ALIGNED_WITH)) {
			switch(relType) {
			case TOP_ALIGNED_WITH: {
				primPt = qntBlockImpl.getYMin(qltPrimBlock);
				refPt = qntBlockImpl.getYMin(qltRefBlock);
				mu = muPtMin;
				break;
			}
			case BOTTOM_ALIGNED_WITH: {
				primPt = qntBlockImpl.getYMax(qltPrimBlock);
				refPt = qntBlockImpl.getYMax(qltRefBlock);
				mu = muPtMax;
				break;
			}
			case CENTERED_VERTICALLY_WITH: {
				primPt =  qntBlockImpl.getYCenter(qltPrimBlock);
				refPt = qntBlockImpl.getYCenter(qltRefBlock);
				mu = muPtCenter;
				break;
			}
			default:
				throw new UnknownValueFromPredefinedList(log, relType);
			}
		}
		
		if (primPt == null || refPt == null) {
			log.warn("Cannot compute value!");
			return null; }
		
		return equal(primPt, mu, refPt, mu, nu);
	}
	
	/**
	 * Compute composite and basic "negative" alignment relations based on the quantitative information.
	 * @param qltPrimBlock
	 * @param qltRefBlock
	 * @param relType
	 * @param qntBlockImpl
	 * @param muPtMin
	 * @param muPtMax
	 * @param muPtCenter
	 * @param nu
	 * @return
	 */
	public static final Boolean compHasCompositeAndBasicNegativeAlignmentRelationBasedOnQntFeatures(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation relType, final QntBlockImpl qntBlockImpl
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final IMuZeroDouble muPtCenter
			, final Nu nu) {
if (log.isDebugEnabled()) { Preconditions.checkArgument(relType.getRelationType() == EBlockQltRelationType.Alignment); }
		switch (relType) {
		case VERTICALLY_ALIGNED_WITH:
			return compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.LEFT_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					|| compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.RIGHT_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					|| compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_HORIZONTALLY_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu);
		case HORIZONTALLY_ALIGNED_WITH:
			return compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.TOP_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					|| compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.BOTTOM_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					|| compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_VERTICALLY_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu);
		case ALIGNED_WITH:
			return compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.LEFT_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					|| compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.RIGHT_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					|| compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_HORIZONTALLY_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					|| compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.TOP_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					|| compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.BOTTOM_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					|| compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_VERTICALLY_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu);
		case NO_VERTICALLY_ALIGNED_WITH:
			return !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.LEFT_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					&& !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.RIGHT_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					&& !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_HORIZONTALLY_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu);
		case NO_HORIZONTALLY_ALIGNED_WITH:
			return !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.TOP_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					&& !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.BOTTOM_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					&& !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_VERTICALLY_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu);
		case NOT_ALIGNED_WITH:
			return !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.LEFT_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					&& !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.RIGHT_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					&& !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_VERTICALLY_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					&& !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.TOP_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					&& !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.BOTTOM_ALIGNED_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
					&& !compHasBasicPositiveAlignmentRelationBasedOnQntFeatures(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_HORIZONTALLY_WITH, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu);
		case HAS_ALIGNMENT_RELATION:
			return true;
		}
		throw new UnknownValueFromPredefinedList(log, relType);
	}
	
	/**
	 * Compute composite and basic "negative" alignment relations based on computations of basic relations.
	 * @param qltPrimBlock
	 * @param qltRefBlock
	 * @param relType
	 * @param qltBlockImpl
	 * @return
	 */
	public static final Boolean compHasCompositeAndBasicNegativeAlignmentRelationCompBasicDepend(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation relType, final QltBlockImpl qltBlockImpl) {
if (log.isDebugEnabled()) { Preconditions.checkArgument(relType.getRelationType() == EBlockQltRelationType.Alignment); }
		switch (relType) {
		case VERTICALLY_ALIGNED_WITH:
			return qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.LEFT_ALIGNED_WITH)
					||  qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.RIGHT_ALIGNED_WITH)
					||  qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_HORIZONTALLY_WITH);
		case HORIZONTALLY_ALIGNED_WITH:
			return qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.TOP_ALIGNED_WITH)
					||  qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.BOTTOM_ALIGNED_WITH)
					||  qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_VERTICALLY_WITH);
		case ALIGNED_WITH:
			return qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.LEFT_ALIGNED_WITH)
					||  qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.RIGHT_ALIGNED_WITH)
					||  qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_HORIZONTALLY_WITH)
					||  qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.TOP_ALIGNED_WITH)
					||  qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.BOTTOM_ALIGNED_WITH)
					||  qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_VERTICALLY_WITH);
		case NO_VERTICALLY_ALIGNED_WITH:
			return !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.LEFT_ALIGNED_WITH)
					&& !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.RIGHT_ALIGNED_WITH)
					&& !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_HORIZONTALLY_WITH);
		case NO_HORIZONTALLY_ALIGNED_WITH:
			return !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.TOP_ALIGNED_WITH)
					&& !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.BOTTOM_ALIGNED_WITH)
					&& !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_VERTICALLY_WITH);
		case NOT_ALIGNED_WITH:
			return !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.LEFT_ALIGNED_WITH)
					&& !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.RIGHT_ALIGNED_WITH)
					&& !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_VERTICALLY_WITH)
					&& !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.TOP_ALIGNED_WITH)
					&& !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.BOTTOM_ALIGNED_WITH)
					&& !qltBlockImpl.hasBasicPositiveAlignmentRelation(qltPrimBlock, qltRefBlock, EBlockQltRelation.CENTERED_HORIZONTALLY_WITH);
		case HAS_ALIGNMENT_RELATION:
			return true;
		}
		throw new UnknownValueFromPredefinedList(log, relType);
	}
	
	/**
	 * Compute basic O-direction relations based on the quantitative information.
	 * @param qltPrimBlock
	 * @param qltRefBlock
	 * @param relType
	 * @param qntBlockImpl
	 * @param muPtMin
	 * @param muPtMax
	 * @param muPtCenter
	 * @param nu
	 * @return
	 */
	public static final Boolean compHasBasicODirectionRelationBasedOnQntFeatures(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation relType, final QntBlockImpl qntBlockImpl
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax
			, final Nu nu) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(relType.getRelationType() == EBlockQltRelationType.ODirection);
Preconditions.checkArgument(relType.isBasicRelation()); }
		
		final Double primPtXMin = qntBlockImpl.getXMin(qltPrimBlock);
		final Double primPtXMax = qntBlockImpl.getXMax(qltPrimBlock);
		final Double refPtXMin = qntBlockImpl.getXMin(qltRefBlock);
		final Double refPtXMax = qntBlockImpl.getXMax(qltRefBlock);
		
		final Double primPtYMin = qntBlockImpl.getYMin(qltPrimBlock);
		final Double primPtYMax = qntBlockImpl.getYMax(qltPrimBlock);
		final Double refPtYMin = qntBlockImpl.getYMin(qltRefBlock);
		final Double refPtYMax = qntBlockImpl.getYMax(qltRefBlock);
		
		if (primPtXMin == null || primPtXMax == null || refPtXMin == null || refPtXMax == null
				|| primPtYMin == null || primPtYMax == null || refPtYMin == null || refPtYMax == null) {
			log.warn("Cannot compute value!");
			return null; }
		
		switch (relType) {
		case NORTH_OF_O:
			return BlockQltRelationsLib.hasODirectionNorthOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, muPtMin, muPtMax, nu);
		case NORTH_EAST_OF_O:
			return BlockQltRelationsLib.hasODirectionNorthEastOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMax, refPtYMin, muPtMin, muPtMax, nu);
		case EAST_OF_O:
			return BlockQltRelationsLib.hasODirectionEastOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case SOUTH_EAST_OF_O:
			return BlockQltRelationsLib.hasODirectionSouthEastOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMax, refPtYMax, muPtMin, muPtMax, nu);
		case SOUTH_OF_O:
			return BlockQltRelationsLib.hasODirectionSouthOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMax, muPtMin, muPtMax, nu);
		case SOUTH_WEST_OF_O:
			return BlockQltRelationsLib.hasODirectionSouthWestOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtYMax, muPtMin, muPtMax, nu);
		case WEST_OF_O:
			return BlockQltRelationsLib.hasODirectionWestOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case NORTH_WEST_OF_O:
			return BlockQltRelationsLib.hasODirectionNorthWestOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtYMin, muPtMin, muPtMax, nu);
		}
		throw new UnknownValueFromPredefinedList(log, relType);
	}
	
	/**
	 * The computation is simplified to checking an existence of relation RCC8.DR.
	 * Quantitative information is used.
	 * @param qltPrimBlock
	 * @param qltRefBlock
	 * @param relType
	 * @param qntBlockImpl
	 * @param muPtMin
	 * @param muPtMax
	 * @param muPtCenter
	 * @param nu
	 * @return
	 */
	public static final Boolean compHasCompositeODirectionRelationBasedOnQntFeatures(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation relType, final QntBlockImpl qntBlockImpl
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final IMuZeroDouble muPtCenter
			, final Nu nu) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(relType.getRelationType() == EBlockQltRelationType.ODirection);
Preconditions.checkArgument(!relType.isBasicRelation()); }
		
		final Double primPtXMin = qntBlockImpl.getXMin(qltPrimBlock);
		final Double primPtXMax = qntBlockImpl.getXMax(qltPrimBlock);
		final Double refPtXMin = qntBlockImpl.getXMin(qltRefBlock);
		final Double refPtXMax = qntBlockImpl.getXMax(qltRefBlock);
		
		final Double primPtYMin = qntBlockImpl.getYMin(qltPrimBlock);
		final Double primPtYMax = qntBlockImpl.getYMax(qltPrimBlock);
		final Double refPtYMin = qntBlockImpl.getYMin(qltRefBlock);
		final Double refPtYMax = qntBlockImpl.getYMax(qltRefBlock);
		
		if (primPtXMin == null || primPtXMax == null || refPtXMin == null || refPtXMax == null
				|| primPtYMin == null || primPtYMax == null || refPtYMin == null || refPtYMax == null) {
			log.warn("Cannot compute the value!");
			return null; }
		
		switch (relType) {
		case O_DIRECTION_RELATION:
			// if there are DR relation between blocks than we always can get o-direction relation
			return BlockQltRelationsLib.hasRCC8_DR(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
//			return compHasBasicODirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.NORTH_OF_O, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicODirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.NORTH_EAST_OF_O, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicODirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.EAST_OF_O, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicODirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.SOUTH_EAST_OF_O, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicODirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.SOUTH_OF_O, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicODirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.SOUTH_WEST_OF_O, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicODirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.WEST_OF_O, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicODirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.NORTH_WEST_OF_O, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu);
		}
		throw new UnknownValueFromPredefinedList(log, relType);
	}
	
	/**
	 * The computation is simplified to checking an existence of relation RCC8.DR.
	 * Existence of a relation is computed based on the implementation.
	 * @param qltPrimBlock
	 * @param qltRefBlock
	 * @param relType
	 * @param qltBlockImpl
	 * @return
	 */
	public static final Boolean compHasCompositeODirectionRelationCompBasicDepend(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation relType, final QltBlockImpl qltBlockImpl) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(relType.getRelationType() == EBlockQltRelationType.ODirection);
Preconditions.checkArgument(!relType.isBasicRelation()); }

		switch (relType) {
		case O_DIRECTION_RELATION:
			// if there are DR relation between blocks than we always can get o-direction relation
			return qltBlockImpl.hasCompositeRCC8Relation(qltPrimBlock, qltRefBlock, EBlockQltRelation.DR);
		}
		throw new UnknownValueFromPredefinedList(log, relType);
	}
	
	/**
	 * Compute basic P-direction relations based on the quantitative information.
	 * @param qltPrimBlock
	 * @param qltRefBlock
	 * @param relType
	 * @param qntBlockImpl
	 * @param muPtMin
	 * @param muPtMax
	 * @param muPtCenter
	 * @param nu
	 * @return
	 */
	public static final Boolean compHasBasicPDirectionRelationBasedOnQntFeatures(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation relType, final QntBlockImpl qntBlockImpl
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax
			, final Nu nu) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(relType.getRelationType() == EBlockQltRelationType.PDirection);
Preconditions.checkArgument(relType.isBasicRelation()); }
		
		final Double primPtXMin = qntBlockImpl.getXMin(qltPrimBlock);
		final Double primPtXMax = qntBlockImpl.getXMax(qltPrimBlock);
		final Double refPtXMin = qntBlockImpl.getXMin(qltRefBlock);
		final Double refPtXMax = qntBlockImpl.getXMax(qltRefBlock);
		
		final Double primPtYMin = qntBlockImpl.getYMin(qltPrimBlock);
		final Double primPtYMax = qntBlockImpl.getYMax(qltPrimBlock);
		final Double refPtYMin = qntBlockImpl.getYMin(qltRefBlock);
		final Double refPtYMax = qntBlockImpl.getYMax(qltRefBlock);
		
		if (primPtXMin == null || primPtXMax == null || refPtXMin == null || refPtXMax == null
				|| primPtYMin == null || primPtYMax == null || refPtYMin == null || refPtYMax == null) {
			log.warn("Cannot compute the value!");
			return null; }
		
		switch (relType) {
		case NORTH_OF_P:
			return BlockQltRelationsLib.hasPDirectionNorthOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, muPtMin, muPtMax, nu);
		case NORTH_EAST_OF_P:
			return BlockQltRelationsLib.hasPDirectionNorthEastOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMax, refPtYMin, muPtMin, muPtMax, nu);
		case EAST_OF_P:
			return BlockQltRelationsLib.hasPDirectionEastOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case SOUTH_EAST_OF_P:
			return BlockQltRelationsLib.hasPDirectionSouthEastOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMax, refPtYMax, muPtMin, muPtMax, nu);
		case SOUTH_OF_P:
			return BlockQltRelationsLib.hasPDirectionSouthOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMax, muPtMin, muPtMax, nu);
		case SOUTH_WEST_OF_P:
			return BlockQltRelationsLib.hasPDirectionSouthWestOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtYMax, muPtMin, muPtMax, nu);
		case WEST_OF_P:
			return BlockQltRelationsLib.hasPDirectionWestOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case NORTH_WEST_OF_P:
			return BlockQltRelationsLib.hasPDirectionNorthWestOfComp(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtYMin, muPtMin, muPtMax, nu);
		}
		throw new UnknownValueFromPredefinedList(log, relType);
	}
	
	/**
	 * Check an existence of composite relations based on computation of basic relations.
	 * Quantitative information is used.
	 * @param qltPrimBlock
	 * @param qltRefBlock
	 * @param relType
	 * @param qntBlockImpl
	 * @param muPtMin
	 * @param muPtMax
	 * @param muPtCenter
	 * @param nu
	 * @return
	 */
	public static final Boolean compHasCompositePDirectionRelationBasedOnQntFeatures(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation relType, final QntBlockImpl qntBlockImpl
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final IMuZeroDouble muPtCenter
			, final Nu nu) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(relType.getRelationType() == EBlockQltRelationType.PDirection);
Preconditions.checkArgument(!relType.isBasicRelation()); }
		
		final Double primPtXMin = qntBlockImpl.getXMin(qltPrimBlock);
		final Double primPtXMax = qntBlockImpl.getXMax(qltPrimBlock);
		final Double refPtXMin = qntBlockImpl.getXMin(qltRefBlock);
		final Double refPtXMax = qntBlockImpl.getXMax(qltRefBlock);
		
		final Double primPtYMin = qntBlockImpl.getYMin(qltPrimBlock);
		final Double primPtYMax = qntBlockImpl.getYMax(qltPrimBlock);
		final Double refPtYMin = qntBlockImpl.getYMin(qltRefBlock);
		final Double refPtYMax = qntBlockImpl.getYMax(qltRefBlock);
		
		if (primPtXMin == null || primPtXMax == null || refPtXMin == null || refPtXMax == null
				|| primPtYMin == null || primPtYMax == null || refPtYMin == null || refPtYMax == null) {
			log.warn("Cannot compute the value!");
			return null; }
		
		switch (relType) {
		case P_DIRECTION_RELATION:
			// if there are DR relation between blocks than we always can get p-direction relation
			return BlockQltRelationsLib.hasRCC8_DR(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
//			return compHasBasicPDirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.NORTH_OF_P, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicPDirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.NORTH_EAST_OF_P, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicPDirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.EAST_OF_P, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicPDirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.SOUTH_EAST_OF_P, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicPDirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.SOUTH_OF_P, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicPDirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.SOUTH_WEST_OF_P, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicPDirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.WEST_OF_P, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)
//					|| compHasBasicPDirectionRelation(qltPrimBlock, qltRefBlock, EQltBlockRelationType.NORTH_WEST_OF_P, model, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu);
		}
		throw new UnknownValueFromPredefinedList(log, relType);
	}
	
	/**
	 * The computation is simplified to checking an existence of relation RCC8.DR.
	 * Existence of a relation is computed based on the implementation.
	 * @param qltPrimBlock
	 * @param qltRefBlock
	 * @param relType
	 * @param qltBlockImpl
	 * @return
	 */
	public static final Boolean compHasCompositePDirectionRelationCompBasicDepend(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation relType, final QltBlockImpl qltBlockImpl) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(relType.getRelationType() == EBlockQltRelationType.PDirection);
Preconditions.checkArgument(!relType.isBasicRelation()); }

		switch (relType) {
		case P_DIRECTION_RELATION:
			// if there are DR relation between blocks than we always can get o-direction relation
			return qltBlockImpl.hasCompositeRCC8Relation(qltPrimBlock, qltRefBlock, EBlockQltRelation.DR);
		}
		throw new UnknownValueFromPredefinedList(log, relType);
	}
	
	/**
	 * Compute existence of basic RCC8 relations based on the quantitative information.
	 * Existence of a relation is computed based on the implementation.
	 * @param qltPrimBlock
	 * @param qltRefBlock
	 * @param relType
	 * @param qntBlockImpl
	 * @param muPtMin
	 * @param muPtMax
	 * @param muPtCenter
	 * @param nu
	 * @return
	 */
	public static final Boolean compHasBasicRCC8RelationBasedOnQntFeatures(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation relType, final QntBlockImpl qntBlockImpl
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax
			, final Nu nu) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(relType.getRelationType() == EBlockQltRelationType.RCC8);
Preconditions.checkArgument(relType.isBasicRelation()); }
		
		final Double primPtXMin = qntBlockImpl.getXMin(qltPrimBlock);
		final Double primPtXMax = qntBlockImpl.getXMax(qltPrimBlock);
		final Double refPtXMin = qntBlockImpl.getXMin(qltRefBlock);
		final Double refPtXMax = qntBlockImpl.getXMax(qltRefBlock);
		
		final Double primPtYMin = qntBlockImpl.getYMin(qltPrimBlock);
		final Double primPtYMax = qntBlockImpl.getYMax(qltPrimBlock);
		final Double refPtYMin = qntBlockImpl.getYMin(qltRefBlock);
		final Double refPtYMax = qntBlockImpl.getYMax(qltRefBlock);
		
		if (primPtXMin == null || primPtXMax == null || refPtXMin == null || refPtXMax == null
				|| primPtYMin == null || primPtYMax == null || refPtYMin == null || refPtYMax == null) {
			log.warn("Cannot compute the value!");
			return null; }
		
		switch (relType) {
		case EC:
			return BlockQltRelationsLib.hasRCC8_EC(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case DC:
			return BlockQltRelationsLib.hasRCC8_DC(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case PO:
			return BlockQltRelationsLib.hasRCC8_PO(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case TPP:
			return BlockQltRelationsLib.hasRCC8_TPP(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case NTPP:
			return BlockQltRelationsLib.hasRCC8_NTPP(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case TPPi:
			return BlockQltRelationsLib.hasRCC8_TPPi(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case NTPPi:
			return BlockQltRelationsLib.hasRCC8_NTPPi(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case EQUAL:
			return BlockQltRelationsLib.hasRCC8_EQUAL(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		}
		throw new UnknownValueFromPredefinedList(log, relType);
	}
	
	/**
	 * Check an existence of composite relations based on computation of basic relations.
	 * Quantitative information is used.
	 * @param qltPrimBlock
	 * @param qltRefBlock
	 * @param relType
	 * @param qntBlockImpl
	 * @param muPtMin
	 * @param muPtMax
	 * @param muPtCenter
	 * @param nu
	 * @return
	 */
	public static final Boolean compHasCompositeRCC8RelationBasedOnQntFeatures(final Resource qltPrimBlock, final Resource qltRefBlock
			, final EBlockQltRelation relType, final QntBlockImpl qntBlockImpl
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final IMuZeroDouble muPtCenter
			, final Nu nu) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(relType.getRelationType() == EBlockQltRelationType.RCC8);
Preconditions.checkArgument(!relType.isBasicRelation()); }
		
		final Double primPtXMin = qntBlockImpl.getXMin(qltPrimBlock);
		final Double primPtXMax = qntBlockImpl.getXMax(qltPrimBlock);
		final Double refPtXMin = qntBlockImpl.getXMin(qltRefBlock);
		final Double refPtXMax = qntBlockImpl.getXMax(qltRefBlock);
		
		final Double primPtYMin = qntBlockImpl.getYMin(qltPrimBlock);
		final Double primPtYMax = qntBlockImpl.getYMax(qltPrimBlock);
		final Double refPtYMin = qntBlockImpl.getYMin(qltRefBlock);
		final Double refPtYMax = qntBlockImpl.getYMax(qltRefBlock);
		
		if (primPtXMin == null || primPtXMax == null || refPtXMin == null || refPtXMax == null
				|| primPtYMin == null || primPtYMax == null || refPtYMin == null || refPtYMax == null) {
			log.warn("Cannot compute the value!");
			return null; }
		
		switch (relType) {
		case DR:
			return BlockQltRelationsLib.hasRCC8_DR(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case PP:
			return BlockQltRelationsLib.hasRCC8_PP(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case P:
			return BlockQltRelationsLib.hasRCC8_P(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case PPi:
			return BlockQltRelationsLib.hasRCC8_PPi(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case Pi:
			return BlockQltRelationsLib.hasRCC8_Pi(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case O:
			return BlockQltRelationsLib.hasRCC8_O(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case C:
			return BlockQltRelationsLib.hasRCC8_C(primPtXMin, primPtXMax, primPtYMin, primPtYMax, refPtXMin, refPtXMax, refPtYMin, refPtYMax, muPtMin, muPtMax, nu);
		case RCC8_RELATION:
			return true;
		}
		throw new UnknownValueFromPredefinedList(log, relType);
	}
	
	/**
	 * Check an existence of composite relations based on computation of basic relations.
	 * Corresponding basic relations can be computed via their "implementation" either based on quantitative data or taken from ontology.
	 * @param prim
	 * @param ref
	 * @param rel
	 * @param impl
	 * @return
	 */
	public static final Boolean compHasCompositeRCC8RelationCompBasicDepend(final Resource prim, final Resource ref
			, final EBlockQltRelation rel, final QltBlockImpl impl) {
		if (log.isDebugEnabled()) {
			Preconditions.checkArgument(rel.getRelationType() == EBlockQltRelationType.RCC8);
			Preconditions.checkArgument(!rel.isBasicRelation()); }
					
					switch (rel) {
					case DR:
						return impl.hasBasicRCC8Relation(prim, ref, EBlockQltRelation.DC)
								|| impl.hasBasicRCC8Relation(prim, ref, EBlockQltRelation.EC);
					case PP:
						return impl.hasBasicRCC8Relation(prim, ref, EBlockQltRelation.TPP)
								|| impl.hasBasicRCC8Relation(prim, ref, EBlockQltRelation.NTPP);
					case P:
						return impl.hasBasicRCC8Relation(prim, ref, EBlockQltRelation.EQUAL)
							|| impl.hasCompositeRCC8Relation(prim, ref, EBlockQltRelation.PP);
					case PPi:
						return impl.hasBasicRCC8Relation(prim, ref, EBlockQltRelation.TPPi)
								|| impl.hasBasicRCC8Relation(prim, ref, EBlockQltRelation.NTPPi);
					case Pi:
						return impl.hasBasicRCC8Relation(prim, ref, EBlockQltRelation.EQUAL)
								|| impl.hasCompositeRCC8Relation(prim, ref, EBlockQltRelation.PPi);
					case O:
						return impl.hasBasicRCC8Relation(prim, ref, EBlockQltRelation.PO)
							|| impl.hasCompositeRCC8Relation(prim, ref, EBlockQltRelation.P)
							|| impl.hasCompositeRCC8Relation(prim, ref, EBlockQltRelation.Pi);
					case C:
						return impl.hasBasicRCC8Relation(prim, ref, EBlockQltRelation.EC)
								|| impl.hasCompositeRCC8Relation(prim, ref, EBlockQltRelation.O);
					case RCC8_RELATION:
						// we always can define RCC8 relations between blocks.
						return true;
					}
					throw new UnknownValueFromPredefinedList(log, rel);
	}
	
	// =============================================
	// ============== FROM ONTOLOGY ================
	// =============================================
	
	public static final boolean hasBasicOrthogonalVisibilityRelationFromOnt(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation rel, final Model model) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(rel.getRelationType() == EBlockQltRelationType.OrthogonallyVisibleBlock); }
		switch(rel) {
//		// --- basic relations ---
//		case NEAREST_SOUTH_NEIGHBORING_BLOCK_OF:
//		case NEAREST_WEST_NEIGHBORING_BLOCK_OF:
//		case NEAREST_NORTH_NEIGHBORING_BLOCK_OF:
//		case NEAREST_EAST_NEIGHBORING_BLOCK_OF:
		// --- basic relations ---
		case SOUTH_ORTHOGONAL_VISIBLE_BLOCK_OF:
		case WEST_ORTHOGONAL_VISIBLE_BLOCK_OF:
		case NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF:
		case EAST_ORTHOGONAL_VISIBLE_BLOCK_OF:
			return model.contains(primQltBlock, rel.getProperty(), refQltBlock);
		}
		throw new UnknownValueFromPredefinedList(log, rel);
	}

	// ==================
	
	public static final boolean hasCompleteCompositeOrthogonalVisibilityRelationCompBasicDepend(
			final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation rel, final QltBlockImpl qltBlockImpl) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(rel.getRelationType() == EBlockQltRelationType.OrthogonallyVisibleBlock);
Preconditions.checkArgument(!rel.isBasicRelation()); }
		if (rel == EBlockQltRelation.ORTHOGONAL_VISIBILITY_RELATION)
			return qltBlockImpl.hasBasicOrthogonalVisibilityRelation(primQltBlock, refQltBlock
							, EBlockQltRelation.SOUTH_ORTHOGONAL_VISIBLE_BLOCK_OF)
					|| qltBlockImpl.hasBasicOrthogonalVisibilityRelation(primQltBlock, refQltBlock
							, EBlockQltRelation.WEST_ORTHOGONAL_VISIBLE_BLOCK_OF)
					|| qltBlockImpl.hasBasicOrthogonalVisibilityRelation(primQltBlock, refQltBlock
							, EBlockQltRelation.NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF)
					|| qltBlockImpl.hasBasicOrthogonalVisibilityRelation(primQltBlock, refQltBlock
							, EBlockQltRelation.EAST_ORTHOGONAL_VISIBLE_BLOCK_OF);
		else
			throw new UnknownValueFromPredefinedList(log, rel);
	}
	
	/**
	 * Checks an existence basic neighboring relations between blocks in QltBGM.
	 * It consider relations such as {@linkplain EBlockQltRelation#NEAREST_SOUTH_NEIGHBORING_BLOCK_OF},
	 * {@linkplain EBlockQltRelation#NEAREST_WEST_NEIGHBORING_BLOCK_OF}, {@linkplain EBlockQltRelation#NEAREST_NORTH_NEIGHBORING_BLOCK_OF},
	 * {@linkplain EBlockQltRelation#NEAREST_EAST_NEIGHBORING_BLOCK_OF}, {@linkplain EBlockQltRelation#SOUTH_NEIGHBORING_BLOCK_OF},
	 * {@linkplain EBlockQltRelation#WEST_NEIGHBORING_BLOCK_OF}, {@linkplain EBlockQltRelation#NORTH_NEIGHBORING_BLOCK_OF},
	 * {@linkplain EBlockQltRelation#EAST_NEIGHBORING_BLOCK_OF}.
	 * Corresponding relation should be in the ontology.
	 * @param primQltBlock
	 * @param refQltBlock
	 * @param rel
	 * @param model
	 * @return
	 */
	public static final boolean hasBasicOrIncompleteCompositeNeighboringRelationFromOnt(final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation rel, final Model model) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(rel.getRelationType() == EBlockQltRelationType.NeighboringBlock); }
		switch(rel) {
		// --- basic relations ---
		case NEAREST_SOUTH_NEIGHBORING_BLOCK_OF:
		case NEAREST_WEST_NEIGHBORING_BLOCK_OF:
		case NEAREST_NORTH_NEIGHBORING_BLOCK_OF:
		case NEAREST_EAST_NEIGHBORING_BLOCK_OF:
		// --- "composite" relations ---
		case SOUTH_NEIGHBORING_BLOCK_OF:
		case WEST_NEIGHBORING_BLOCK_OF:
		case NORTH_NEIGHBORING_BLOCK_OF:
		case EAST_NEIGHBORING_BLOCK_OF:
			return model.contains(primQltBlock, rel.getProperty(), refQltBlock);
		}
		throw new UnknownValueFromPredefinedList(log, rel);
	}

	// ==================
	
//	compHasCompositeRCC8Relation  compHasCompositeNeighboringRelation
	
	/**
	 * Relation {@linkplain EBlockQltRelation#NEIGHBORING_BLOCK_RELATION} is computed based on its child relations
	 * @param primQltBlock
	 * @param refQltBlock
	 * @param rel
	 * @param qltBlockImpl
	 * @return
	 */
	public static final boolean hasCompleteCompositeNeighboringRelationCompBasicDepend(
			final Resource primQltBlock, final Resource refQltBlock
			, final EBlockQltRelation rel, final QltBlockImpl qltBlockImpl) {
if (log.isDebugEnabled()) {
Preconditions.checkArgument(rel.getRelationType() == EBlockQltRelationType.NeighboringBlock);
Preconditions.checkArgument(!rel.isBasicRelation()); }
		if (rel == EBlockQltRelation.NEIGHBORING_BLOCK_RELATION)
			return qltBlockImpl.hasBasicOrIncompleteCompositeNeighboringRelation(primQltBlock, refQltBlock
							, EBlockQltRelation.SOUTH_NEIGHBORING_BLOCK_OF)
					|| qltBlockImpl.hasBasicOrIncompleteCompositeNeighboringRelation(primQltBlock, refQltBlock
							, EBlockQltRelation.WEST_NEIGHBORING_BLOCK_OF)
					|| qltBlockImpl.hasBasicOrIncompleteCompositeNeighboringRelation(primQltBlock, refQltBlock
							, EBlockQltRelation.NORTH_NEIGHBORING_BLOCK_OF)
					|| qltBlockImpl.hasBasicOrIncompleteCompositeNeighboringRelation(primQltBlock, refQltBlock
							, EBlockQltRelation.EAST_NEIGHBORING_BLOCK_OF);
		else
			throw new UnknownValueFromPredefinedList(log, rel);
	}
	
	
}
