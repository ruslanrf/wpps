/**
 * 
 */
package tuwien.dbai.wpps.core.fuzzy;

import com.google.common.base.Preconditions;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 18, 2011 1:08:48 AM
 */
public class IntervalMuZeroDouble extends IntervalMuDouble implements IMuZeroDouble {

	public IntervalMuZeroDouble(double minVal, double maxVal) {
		super(minVal, maxVal);
		Preconditions.checkArgument(minVal<=0);
		Preconditions.checkArgument(maxVal>=0);
	}

}
