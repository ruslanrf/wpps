/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 7, 2011 3:34:12 PM
 */
public final class BlockQntLibSupport {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BlockQntLibSupport.class);

	public static final Double compWidth(final Resource qntBlock, final QntBlockImpl qntBlockImpl) {
		final Double min = qntBlockImpl.getXMin(qntBlock);
		final Double max = qntBlockImpl.getXMax(qntBlock);
		return (min == null || max == null)?null:max-min;
	}
	
	public static final double compHeight(final Resource qntBlock, final QntBlockImpl qntBlockImpl) {
		final Double min = qntBlockImpl.getYMin(qntBlock);
		final Double max = qntBlockImpl.getYMax(qntBlock);
		return (min == null || max == null)?null:max-min;
	}
	
//	@Deprecated
//	public static final Point2D compCentre(final Resource inst, final QntBlockImpl qntBlockImpl) {
//		return BlockQntLib.calcCentre(qntBlockImpl.getXMin(inst), qntBlockImpl.getYMin(inst)
//    		, qntBlockImpl.getXMax(inst), qntBlockImpl.getYMax(inst));
//	}
	
	public static final Double compXCenter(final Resource inst, final QntBlockImpl qntBlockImpl) {
		final Double min = qntBlockImpl.getXMin(inst);
		final Double max = qntBlockImpl.getXMax(inst);
		return (min == null || max == null)?null:BlockQntLib.calcCenter(min, max);
	}
	
	public static final Double compYCenter(final Resource inst, final QntBlockImpl qntBlockImpl) {
		final Double min = qntBlockImpl.getYMin(inst);
		final Double max = qntBlockImpl.getYMax(inst);
		return (min == null || max == null)?null:BlockQntLib.calcCenter(min, max);
	}
	
	
	/**
	 * Constant.
	 * Minimal possible distance between geometrical centers of blocks to calculate
	 * angle between them.
	 */
	private static final double ANGLE_MIN_DISTANCE = Float.MIN_NORMAL;
	
	/**
	 * <p> Calculate angle between objects as a angle between axis x and the line connecting
	 * blocks' geometric centers. </p>
	 * 
	 * <p>This relation can be stored in the ontology.</p>
	 * 
	 * @param primInst
	 * @param refInst
	 * @param model
	 * @return NAN if centers of objects are very close to each other.
	 */
	public static final Double compAngleAsDouble(Resource primInst, Resource refInst
			, final QntBlockImpl qntBlockImpl) {
		final Double primPointX = qntBlockImpl.getXCenter(primInst);
		final Double primPointY = qntBlockImpl.getYCenter(primInst);
		final Double refPointX = qntBlockImpl.getXCenter(refInst);
		final Double refPointY = qntBlockImpl.getYCenter(refInst);
		
		if (primPointX == null || primPointY == null
				|| refPointX == null || refPointY == null)
			return null;
		
		final double opposite =  primPointY - refPointY;
		final double oppositeAbs = Math.abs(opposite);
		final double adjacent =  primPointX - refPointX;
		final double adjacentAbs = Math.abs(adjacent);
		if ( oppositeAbs <= ANGLE_MIN_DISTANCE) {
			if (adjacentAbs <= ANGLE_MIN_DISTANCE)
				return Double.NaN;
			else if (adjacent>0)
				return 0d;
			else if (adjacent<0)
				return 180d;
		}
		else if ( adjacentAbs <= ANGLE_MIN_DISTANCE) {
			if (opposite>0)
				return 90d;
			else if (opposite<0)
				return 270d;
		}
		final double hypotenuse = Math.sqrt(opposite*opposite + adjacent*adjacent);
		final double angle = Math.toDegrees(Math.asin(opposite/hypotenuse * Math.signum(adjacent)));
		if (angle>0) {
			if (adjacent>0)
				return (double)angle;
			else
				return (double)angle+180;
		}
		else if (angle<0) {
			if (opposite>0)
				return (double)angle+180;
			else
				return (double)angle+360;
		}
		return Double.NaN;
	}
	
	
	/**
	 * <p>This relation can be stored in the ontology.</p>
	 * @param primInst
	 * @param refInst
	 * @param model
	 * @return
	 */
	public static final Double compDistanceAsDouble(Resource primInst, Resource refInst
			, final QntBlockImpl qntBlockImpl) {
		final Double primXMin = qntBlockImpl.getXMin(primInst);
		final Double primXMax = qntBlockImpl.getXMax(primInst);
		final Double primYMin = qntBlockImpl.getYMin(primInst);
		final Double primYMax = qntBlockImpl.getYMax(primInst);
		final Double refXMin = qntBlockImpl.getXMin(refInst);
		final Double refXMax = qntBlockImpl.getXMax(refInst);
		final Double refYMin = qntBlockImpl.getYMin(refInst);
		final Double refYMax = qntBlockImpl.getYMax(refInst);
		
		if (primXMin == null || primXMax == null || primYMin == null || primYMax == null
				|| refXMin == null || refXMax == null || refYMin == null || refYMax == null)
			return null;

	    double greatestMinX = Math.max(primXMin, refXMin);
	    double leastMaxX    = Math.min(primXMax, refXMax);
	    double distanceSquared = 0;
	    if (greatestMinX > leastMaxX) {
	      distanceSquared += ((greatestMinX - leastMaxX) * (greatestMinX - leastMaxX)); 
	    }
	    double  greatestMinY = Math.max(primYMin, refYMin);
	    double leastMaxY    = Math.min(primYMax, refYMax);
	    if (greatestMinY > leastMaxY) {
	      distanceSquared += ((greatestMinY - leastMaxY) * (greatestMinY - leastMaxY));
	    }
	    return (double) Math.sqrt(distanceSquared);
	  }
	
	
//	/**
//	 * Distance between borders.
//	 * 
//	 * @param primInst
//	 * @param refInst
//	 * @param relationClass
//	 * @param model
//	 * @return
//	 * @see QntBlockModelOnt#BorderDistance
//	 */
//	@Deprecated
//	public static final double compBorderDistanceAsDouble(final Resource primInst, final Resource refInst
//			, final EQntBlockRelationType relationClass, final QntBlockImpl qntBlockImpl) {
//		switch (relationClass) {
//		case BORDER_DISTANCE_BB:
//			return qntBlockImpl.getYMax(primInst)-qntBlockImpl.getYMax(refInst);
////		case BORDER_DISTANCE_BT:
////			return getYMax(primInst, model)-getYMin(refInst, model);
//		case BORDER_DISTANCE_LL:
//			return qntBlockImpl.getXMin(primInst)-qntBlockImpl.getXMin(refInst);
//		case BORDER_DISTANCE_LR:
//			return qntBlockImpl.getXMin(primInst)-qntBlockImpl.getXMax(refInst);
////		case BORDER_DISTANCE_RL:
////			return getXMax(primInst, model)-getXMin(refInst, model);
//		case BORDER_DISTANCE_RR:
//			return qntBlockImpl.getXMax(primInst)-qntBlockImpl.getXMax(refInst);
//		case BORDER_DISTANCE_TB:
//			return qntBlockImpl.getYMin(primInst)-qntBlockImpl.getYMax(refInst);
//		case BORDER_DISTANCE_TT:
//			return qntBlockImpl.getYMax(primInst)-qntBlockImpl.getYMax(refInst);
//		default:
//			throw new UnknownValueFromPredefinedList(log, relationClass);
//		}
//	}
	
	public static final Double compBorderDistanceBBAsDouble(final Resource primInst, final Resource refInst
			, final QntBlockImpl qntBlockImpl) {
		final Double a = qntBlockImpl.getYMax(primInst);
		final Double b = qntBlockImpl.getYMax(refInst);
		return (a == null || b == null)?null:a - b;
	}
	
	public static final double compBorderDistanceLLAsDouble(final Resource primInst, final Resource refInst
			, final QntBlockImpl qntBlockImpl) {
		final Double a = qntBlockImpl.getXMin(primInst);
		final Double b = qntBlockImpl.getXMin(refInst);
		return (a == null || b == null)?null:a - b;
	}
	
	public static final double compBorderDistanceLRAsDouble(final Resource primInst, final Resource refInst
			, final QntBlockImpl qntBlockImpl) {
		final Double a = qntBlockImpl.getXMin(primInst);
		final Double b = qntBlockImpl.getXMax(refInst);
		return (a == null || b == null)?null:a - b;
	}
	
	public static final double compBorderDistanceRRAsDouble(final Resource primInst, final Resource refInst
			, final QntBlockImpl qntBlockImpl) {
		final Double a = qntBlockImpl.getXMax(primInst);
		final Double b = qntBlockImpl.getXMax(refInst);
		return (a == null || b == null)?null:a - b;
	}
	
	public static final double compBorderDistanceTBAsDouble(final Resource primInst, final Resource refInst
			, final QntBlockImpl qntBlockImpl) {
		final Double a = qntBlockImpl.getYMin(primInst);
		final Double b = qntBlockImpl.getYMax(refInst);
		return (a == null || b == null)?null:a - b;
	}
	
	public static final double compBorderDistanceTTAsDouble(final Resource primInst, final Resource refInst
			, final QntBlockImpl qntBlockImpl) {
		final Double a = qntBlockImpl.getYMax(primInst);
		final Double b = qntBlockImpl.getYMax(refInst);
		return (a == null || b == null)?null:a - b;
	}
	
}
