/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib;

import static tuwien.dbai.wpps.core.fuzzy.ComparatorLib.equal;
import static tuwien.dbai.wpps.core.fuzzy.ComparatorLib.less;
import tuwien.dbai.wpps.core.fuzzy.FuzzyComparator;
import tuwien.dbai.wpps.core.fuzzy.IMuZeroDouble;
import tuwien.dbai.wpps.core.fuzzy.Nu;

// TODO correct for points according to the RCC8
/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 18, 2011 12:51:44 AM
 */
public final class BlockQltRelationsLib {
	
	// ==============================
	// ===== Interval Relations =====
	// ==============================
	
	/**
	 * Check existence of the interval relation "before" between two intervals.<br/>
	 * <code>primPtMax &lt; refPtMin.</code>
	 * @param primPtMax right limit point of the primary interval.
	 * @param refPtMin left limit point of the reference interval.
	 * @param muPtMin membership function for the left point.
	 * @param muPtMax membership function for the right point.
	 * @param nu true-function.
	 * @return <code>true</code>, if the specified relation exists, <code>false</code> otherwise.
	 */
	public static final boolean hasIRBeforeComp(double primPtMax
			, double refPtMin, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return less(primPtMax, muPtMax, refPtMin, muPtMin, nu);
	}
	
	/**
	 * Check existence of the interval relation "after" between two intervals.
	 * It is a symmetric relation for the "before"
	 * @param primPtMin left limit point of the primary interval.
	 * @param refPtMax right limit point of the reference interval.
	 * @param muPtMin membership function for the left point.
	 * @param muPtMax membership function for the right point.
	 * @param nu true-function.
	 * @return <code>true</code>, if the specified relation exists, <code>false</code> otherwise.
	 */
	public static final boolean hasIRAfterComp(double primPtMin
			, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return less(refPtMax, muPtMax, primPtMin, muPtMin, nu);
	}
	
	/**
	 * Check existence of the interval relation "touches" between two intervals.<br/>
	 * <code>primPtMin &lt; refPtMin & primPtMax = refPtMin & primPtMax &lt; refPtMax.</code>
	 * 
	 * @param primPtMin left limit point of the primary interval.
	 * @param primPtMax right limit point of the primary interval.
	 * @param refPtMin left limit point of the reference interval.
	 * @param refPtMax right limit point of the reference interval.
	 * @param muPtMin membership function for the left point.
	 * @param muPtMax membership function for the right point.
	 * @param nu true-function.
	 * @return <code>true</code>, if the specified relation exists, <code>false</code> otherwise.
	 */
	public static final boolean hasIRTouchesComp(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return less(primPtMin, muPtMin, refPtMin, muPtMin, nu)
				&& equal(primPtMax, muPtMax, refPtMin, muPtMin, nu)
				&& less(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}
	
	public static final boolean hasIRTouchedByComp(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
//		return less(refPtMin, muPtMin, primPtMin, muPtMin, nu)
//				&& equal(refPtMax, muPtMax, primPtMin, muPtMin, nu)
//				&& less(refPtMax, muPtMax, primPtMax, muPtMax, nu);
		return hasIRTouchesComp(refPtMin, refPtMax, primPtMin, primPtMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasIROverlapsComp(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return less(primPtMin, muPtMin, refPtMin, muPtMin, nu)
				&& less(refPtMin, muPtMin, primPtMax, muPtMax, nu)
				&& less(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}
	
	public static final boolean hasIROverlappedByComp(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return hasIROverlapsComp(refPtMin, refPtMax, primPtMin, primPtMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasIRStartsComp(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return equal(primPtMin, muPtMin, refPtMin, muPtMin, nu)
				&& less(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}
	
	public static final boolean hasIRStartedByComp(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return hasIRStartsComp(refPtMin, refPtMax, primPtMin, primPtMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasIRInsideComp(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return less(refPtMin, muPtMin, primPtMin, muPtMin, nu)
				&& less(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}
	
	public static final boolean hasIRContainsComp(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return hasIRInsideComp(refPtMin, refPtMax, primPtMin, primPtMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasIRFinishesComp(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return less(refPtMin, muPtMin, primPtMin, muPtMin, nu)
				&& equal(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}
	
	public static final boolean hasIRFinishedByComp(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return hasIRFinishesComp(refPtMin, refPtMax, primPtMin, primPtMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasIREqualsComp(double primPtMin, double primPtMax
			, double refPtMin, double refPtMax, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return equal(primPtMin, muPtMin, refPtMin, muPtMin, nu)
				&& equal(primPtMax, muPtMax, refPtMax, muPtMax, nu);
	}

	// ==============================
	// =====                    =====
	// ==============================
	
	
	// ==============================
	// ========  O-Direction ========
	// ==============================
	
	public static final boolean hasODirectionNorthOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return ( less(refPtXMin, muPtMin, primPtXMax, muPtMax, nu) && less(primPtXMin, muPtMin, refPtXMax, muPtMax, nu)
				|| equal(primPtXMin, muPtMin, refPtXMin, muPtMin, nu) || equal(primPtXMax, muPtMax, refPtXMax, muPtMax, nu) )
				&& !less(refPtYMin, muPtMin, primPtYMax, muPtMax, nu) && less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu);
	}
	
	public static final boolean hasODirectionNorthEastOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMax, double refPtYMin
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		final boolean a = less(primPtXMin, muPtMin, refPtXMax, muPtMax, nu);
		return less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu)
				&& less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu)
				&&
				( !a
					||
					a
					&& !less(refPtYMin, muPtMin, primPtYMax, muPtMax, nu) );
	}
	
	public static final boolean hasODirectionEastOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return ( less(refPtYMin, muPtMin, primPtYMax, muPtMax, nu) && less(primPtYMin, muPtMin, refPtYMax, muPtMax, nu)
				|| equal(primPtYMin, muPtMin, refPtYMin, muPtMin, nu) || equal(primPtYMax, muPtMax, refPtYMax, muPtMax, nu) )
				&& !less(primPtXMin, muPtMin, refPtXMax, muPtMax, nu) && less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu);
	}
	
	public static final boolean hasODirectionSouthEastOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMax, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		final boolean a = less(primPtXMin, muPtMin, refPtXMax, muPtMax, nu);
		return less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu)
				&& less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu)
				&&
				( !a
					||
					a
					&& !less(primPtYMin, muPtMin, refPtYMax, muPtMax, nu) );
	}
	
	public static final boolean hasODirectionSouthOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return ( less(refPtXMin, muPtMin, primPtXMax, muPtMax, nu) && less(primPtXMin, muPtMin, refPtXMax, muPtMax, nu)
				|| equal(primPtXMin, muPtMin, refPtXMin, muPtMin, nu) || equal(primPtXMax, muPtMax, refPtXMax, muPtMax, nu) )
				&& !less(primPtYMin, muPtMin, refPtYMax, muPtMax, nu) && less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu);
	}
	
	public static final boolean hasODirectionSouthWestOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		final boolean a = less(refPtXMin, muPtMin, primPtXMax, muPtMax, nu);
		return less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu)
				&& less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu)
				&&
				( !a
					||
					a
					&& !less(primPtYMin, muPtMin, refPtYMax, muPtMax, nu) );
	}
	
	public static final boolean hasODirectionWestOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return ( less(refPtYMin, muPtMin, primPtYMax, muPtMax, nu) && less(primPtYMin, muPtMin, refPtYMax, muPtMax, nu)
				|| equal(primPtYMin, muPtMin, refPtYMin, muPtMin, nu) || equal(primPtYMax, muPtMax, refPtYMax, muPtMax, nu) )
				&& !less(refPtXMin, muPtMin, primPtXMax, muPtMax, nu) && less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu);
	}
	
	public static final boolean hasODirectionNorthWestOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtYMin
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		final boolean a = less(refPtXMin, muPtMin, primPtXMax, muPtMax, nu);
		return less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu)
				&& less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu)
				&&
				( !a
					||
					a
					&& !less(refPtYMin, muPtMin, primPtYMax, muPtMax, nu) );
	}
	
	// ==============================
	// =====                    =====
	// ==============================
	
	
	// ==============================
	// ========  P-Direction ========
	// ==============================
	
	public static final boolean hasPDirectionNorthOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return !less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu) && !less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu)
				&& !less(refPtYMin, muPtMin, primPtYMax, muPtMax, nu) && less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu);
	}
	
	public static final boolean hasPDirectionNorthEastOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMax, double refPtYMin
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return !less(refPtYMin, muPtMin, primPtYMax, muPtMax, nu) && less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu)
				&& !less(primPtXMin, muPtMin, refPtXMax, muPtMax, nu) && less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu);
	}
	
	public static final boolean hasPDirectionEastOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return !less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu) && !less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu)
				&& !less(primPtXMin, muPtMin, refPtXMax, muPtMax, nu) && less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu);
	}
	
	public static final boolean hasPDirectionSouthEastOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMax, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return !less(primPtYMin, muPtMin, refPtYMax, muPtMax, nu) && less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu)
				&& !less(primPtXMin, muPtMin, refPtXMax, muPtMax, nu) && less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu);
	}
	
	public static final boolean hasPDirectionSouthOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return !less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu) && !less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu)
				&& !less(primPtYMin, muPtMin, refPtYMax, muPtMax, nu) && less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu);
	}
	
	public static final boolean hasPDirectionSouthWestOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return !less(primPtYMin, muPtMin, refPtYMax, muPtMax, nu) && less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu)
				&& !less(refPtXMin, muPtMin, primPtXMax, muPtMax, nu) && less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu);
	}
	
	public static final boolean hasPDirectionWestOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return !less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu) && !less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu)
				&& !less(refPtXMin, muPtMin, primPtXMax, muPtMax, nu) && less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu);
	}
	
	public static final boolean hasPDirectionNorthWestOfComp(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtYMin
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return !less(refPtYMin, muPtMin, primPtYMax, muPtMax, nu) && less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu)
				&& !less(refPtXMin, muPtMin, primPtXMax, muPtMax, nu) && less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu);
	}
	
	// ==============================
	// =====                    =====
	// ==============================
	
	
	
	// ==============================
	// ======       RCC8      =======
	// TODO: Here we always suppose that reference object is not of volume 0
	// ==============================
	
	// --- JEPD ---
	
	public static final boolean hasRCC8_DC(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return less(primPtXMax, muPtMax, refPtXMin, muPtMin, nu) || less(refPtXMax, muPtMax, primPtXMin, muPtMin, nu)
				|| less(primPtYMax, muPtMax, refPtYMin, muPtMin, nu) ||  less(refPtYMax, muPtMax, primPtYMin, muPtMin, nu);
	}
	
	public static final boolean hasRCC8_EC(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu) && equal(primPtXMax, muPtMax, refPtXMin, muPtMin, nu)
				&& !less(primPtYMax, muPtMax, refPtYMin, muPtMin, nu) && !less(refPtYMax, muPtMax, primPtYMin, muPtMin, nu)
				||
				less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu) && equal(refPtXMax, muPtMax, primPtXMin, muPtMin, nu)
				&& !less(primPtYMax, muPtMax, refPtYMin, muPtMin, nu) && !less(refPtYMax, muPtMax, primPtYMin, muPtMin, nu)
				||
				less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu) && equal(primPtYMax, muPtMax, refPtYMin, muPtMin, nu)
				&& !less(primPtXMax, muPtMax, refPtXMin, muPtMin, nu) && !less(refPtXMax, muPtMax, primPtXMin, muPtMin, nu)
				||
				less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu) && equal(primPtYMin, muPtMin, refPtYMax, muPtMax, nu)
				&& !less(primPtXMax, muPtMax, refPtXMin, muPtMin, nu) && !less(refPtXMax, muPtMax, primPtXMin, muPtMin, nu);
	}
	
	public static final boolean hasRCC8_PO(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return
				// primary and reference blocks should have common points
				less(primPtXMin, muPtMin, refPtXMax, muPtMax, nu) && less(refPtXMin, muPtMin, primPtXMax, muPtMax, nu)
				&& less(primPtYMin, muPtMin, refPtYMax, muPtMax, nu) && less(refPtYMin, muPtMin, primPtYMax, muPtMax, nu)
				// primary block should have points outside the reference block
				&& ( less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu) || less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu)
						|| less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu) || less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu) )
				// reference block should have points outside the primary block
				&& ( less(refPtXMin, muPtMin, primPtXMin, muPtMin, nu) || less(refPtYMin, muPtMin, primPtYMin, muPtMin, nu)
						|| less(primPtXMax, muPtMax, refPtXMax, muPtMax, nu) || less(primPtYMax, muPtMax, refPtYMax, muPtMax, nu) );
	}
	
	public static final boolean hasRCC8_TPP(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return
				// primary block inside reference block or equal
				!less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu) && !less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu)
				&& !less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu) && !less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu)
				&& // one of the border should coincide
				( equal(primPtXMin, muPtMin, refPtXMin, muPtMin, nu) || equal(primPtYMin, muPtMin, refPtYMin, muPtMin, nu)
						|| equal(primPtXMax, muPtMax, refPtXMax, muPtMax, nu) || equal(primPtYMax, muPtMax, refPtYMax, muPtMax, nu) )
				&& // reference block should have points outside the primary block
				( less(refPtXMin, muPtMin, primPtXMin, muPtMin, nu) || less(refPtYMin, muPtMin, primPtYMin, muPtMin, nu)
						|| less(primPtXMax, muPtMax, refPtXMax, muPtMax, nu) || less(primPtYMax, muPtMax, refPtYMax, muPtMax, nu) );
	}
	
	public static final boolean hasRCC8_NTPP(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return less(refPtXMin, muPtMin, primPtXMin, muPtMin, nu) && less(refPtYMin, muPtMin, primPtYMin, muPtMin, nu)
				&& less(primPtXMax, muPtMax, refPtXMax, muPtMax, nu) && less(primPtYMax, muPtMax, refPtYMax, muPtMax, nu);
	}
	
	public static final boolean hasRCC8_EQUAL(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return equal(refPtXMin, muPtMin, primPtXMin, muPtMin, nu) && equal(refPtYMin, muPtMin, primPtYMin, muPtMin, nu)
				&& equal(primPtXMax, muPtMax, refPtXMax, muPtMax, nu) && equal(primPtYMax, muPtMax, refPtYMax, muPtMax, nu);
	}
	
	public static final boolean hasRCC8_NTPPi(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return hasRCC8_NTPP(refPtXMin, refPtXMax, refPtYMin, refPtYMax, primPtXMin, primPtXMax, primPtYMin, primPtYMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasRCC8_TPPi(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return hasRCC8_TPP(refPtXMin, refPtXMax, refPtYMin, refPtYMax, primPtXMin, primPtXMax, primPtYMin, primPtYMax, muPtMin, muPtMax, nu);
	}
	
	// ---      ---
	
	// --- Composite relations ---
	
	public static final boolean hasRCC8_DR(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return !less(refPtXMin, muPtMin, primPtXMax, muPtMax, nu) && less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu)
				|| !less(refPtYMin, muPtMin, primPtYMax, muPtMax, nu) && less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu)
				|| !less(primPtXMin, muPtMin, refPtXMax, muPtMax, nu) && less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu)
				|| !less(primPtYMin, muPtMin, refPtYMax, muPtMax, nu) && less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu);
	}
	
	public static final boolean hasRCC8_PP(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return
				// primary block inside reference block or equal
				!less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu) && !less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu)
				&& !less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu) && !less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu)
				&& // reference block should have points outside the primary block
				( less(refPtXMin, muPtMin, primPtXMin, muPtMin, nu) || less(refPtYMin, muPtMin, primPtYMin, muPtMin, nu)
						|| less(primPtXMax, muPtMax, refPtXMax, muPtMax, nu) || less(primPtYMax, muPtMax, refPtYMax, muPtMax, nu) );
	}
	
	public static final boolean hasRCC8_PPi(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return hasRCC8_PP(refPtXMin, refPtXMax, refPtYMin, refPtYMax, primPtXMin, primPtXMax, primPtYMin, primPtYMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasRCC8_P(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return
				// primary block inside reference block or equal
				!less(primPtXMin, muPtMin, refPtXMin, muPtMin, nu) && !less(primPtYMin, muPtMin, refPtYMin, muPtMin, nu)
				&& !less(refPtXMax, muPtMax, primPtXMax, muPtMax, nu) && !less(refPtYMax, muPtMax, primPtYMax, muPtMax, nu);
	}
	
	public static final boolean hasRCC8_P(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final FuzzyComparator c) {
		return
				// primary block inside reference block or equal
				!c.less(primPtXMin, refPtXMin, 0, 0, 0) && !c.less(primPtYMin, refPtYMin, 0, 0, 1)
				&& !c.less(refPtXMax, primPtXMax, 1, 1, 0) && !c.less(refPtYMax, primPtYMax, 1, 1, 1);
	}
	
	public static final boolean hasRCC8_Pi(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return hasRCC8_P(refPtXMin, refPtXMax, refPtYMin, refPtYMax, primPtXMin, primPtXMax, primPtYMin, primPtYMax, muPtMin, muPtMax, nu);
	}
	
	public static final boolean hasRCC8_O(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return
				// primary and reference blocks should have common points
				less(primPtXMin, muPtMin, refPtXMax, muPtMax, nu) && less(refPtXMin, muPtMin, primPtXMax, muPtMax, nu)
				&& less(primPtYMin, muPtMin, refPtYMax, muPtMax, nu) && less(refPtYMin, muPtMin, primPtYMax, muPtMax, nu);
	}
	
	public static final boolean hasRCC8_O(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final FuzzyComparator c) {
		return
				// primary and reference blocks should have common points
				c.less(primPtXMin, refPtXMax, 0, 1, 0) && c.less(refPtXMin, primPtXMax, 0, 1, 0)
				&& c.less(primPtYMin, refPtYMax, 0, 1, 1) && c.less(refPtYMin, primPtYMax, 0, 1, 1);
	}
	
	public static final boolean hasRCC8_C(double primPtXMin, double primPtXMax, double primPtYMin, double primPtYMax
			, double refPtXMin, double refPtXMax, double refPtYMin, double refPtYMax
			, final IMuZeroDouble muPtMin, final IMuZeroDouble muPtMax, final Nu nu) {
		return
				// primary and reference blocks should have common points
				!less(refPtXMax, muPtMax, primPtXMin, muPtMin, nu) && !less(primPtXMax, muPtMax, refPtXMin, muPtMin, nu)
				&& !less(refPtYMax, muPtMax, primPtYMin, muPtMin, nu) && !less(primPtYMax, muPtMax, refPtYMin, muPtMin, nu);
	}
	
	// ---                     ---
	
	
	// ==============================
	// =====                    =====
	// ==============================
	
	
}
