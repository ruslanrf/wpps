/**
 * File name: Nu.java
 * @created: Jul 24, 2011 12:08:45 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.fuzzy;

import tuwien.dbai.wpps.common.callback.IFunction;


/**
 * Function "nu" which detects level of belonging in terms of 3 valued logic. 
 * 
 * @created: Jul 24, 2011 12:08:45 AM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public final class Nu implements IFunction<Double, Integer> {

	private final double delta;
	
	private final double centre;
	
	public Nu(double delta) {
		this.delta = delta;
		this.centre = 0.5;
	}
	
	public Nu(double centre, double delta) {
		this.delta = delta;
		this.centre = centre;
	}
	
	/**
	 * @param val value from a membership function Mu. value in [0; 1].
	 * @return 0 ("0"), 1 ("1"), 2 ("?")
	 */
	@Override
	public Integer apply(Double avar) {
		if (avar>centre+delta)
			return 1;
		else if (avar <centre-delta)
			return 0;
		else return 2;
	}

	public double getDelta() {
		return delta;
	}

	public double getCentre() {
		return centre;
	}
	
}
