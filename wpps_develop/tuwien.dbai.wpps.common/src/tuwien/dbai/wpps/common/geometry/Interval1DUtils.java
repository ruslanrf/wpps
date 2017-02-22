/**
 * 
 */
package tuwien.dbai.wpps.common.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 3, 2012 8:28:54 PM
 */
public class Interval1DUtils {

	/**
	 * Check if there is an overlapping between primRect (main object) and refRect.
	 * @param overlappingObj
	 * @param overlappedObj
	 * @return
	 */
	public static final boolean overlap(final Interval1D overlappingObj, final Interval1D overlappedObj) {
		return overlappingObj.max>overlappedObj.min && overlappingObj.min<overlappedObj.max;
	}
	
	public static final boolean contains(final Interval1D outer, final Interval1D inner) {
		return inner.min >=outer.min && inner.max<=outer.max;
	}
	
	public static final List<Interval1D> cut(Interval1D cuttingObj, Interval1D cutObj) {
		List<Interval1D> rez = new ArrayList<Interval1D>(2);
		if (cutObj.min < cuttingObj.min)
			rez.add(new Interval1D(cutObj.min, Math.min(cuttingObj.min, cutObj.max)));
		if (cutObj.max > cuttingObj.max)
			rez.add(new Interval1D(Math.max(cutObj.min, cuttingObj.max), cutObj.max));
		return rez;
	}
}
