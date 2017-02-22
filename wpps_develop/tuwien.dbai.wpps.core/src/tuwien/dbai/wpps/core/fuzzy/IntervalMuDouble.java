/**
 * File name: IntervalMu.java
 * @created: Jul 24, 2011 1:46:51 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.fuzzy;

import tuwien.dbai.wpps.common.geometry.Point2D;

/**
 * Membership function "mu" which detects a strength of a belonging to fuzzy set.
 * If element in the particular interval then the strench is 1, 0 otherwise. 
 * 
 * @created: Jul 24, 2011 1:46:51 AM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class IntervalMuDouble implements IMuDouble {

	private final double minVal;
	private final double maxVal;
	
	public IntervalMuDouble(double minVal, double maxVal) {
		this.minVal = minVal;
		this.maxVal = maxVal;
	}
	
	@Override
	public Double apply(Double avar) {
		return (avar>=minVal && avar<=maxVal)?1d:0d;
	}
	
	public Point2D getInterval() {
		return new Point2D(minVal, maxVal);
	}

}
