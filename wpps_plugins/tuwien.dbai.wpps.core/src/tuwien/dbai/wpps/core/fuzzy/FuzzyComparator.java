/**
 * 
 */
package tuwien.dbai.wpps.core.fuzzy;


/**
 * Fuzzy Comparator.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 1, 2012 8:34:03 AM
 */
public final class FuzzyComparator {

	private final IMuZeroDouble[][] mu;
	private final Nu nu;
	
	public FuzzyComparator(final IMuZeroDouble[][] mu, final Nu nu) {
		this.mu = mu;
		this.nu = nu;
	}
	
	public boolean less(double a, double b, int aType, int bType, int dim) {
		return ComparatorLib.less(a, mu[dim][aType], b, mu[dim][bType], nu);
	}
	
	public boolean lessOrEq(double a, double b, int aType, int bType, int dim) {
		return ComparatorLib.lessOrEq(a, mu[dim][aType], b, mu[dim][bType], nu);
	}
	
	public boolean equal(double a, double b, int aType, int bType, int dim) {
		return ComparatorLib.equal(a, mu[dim][aType], b, mu[dim][bType], nu);
	}
	
	public boolean more(double a, double b, int aType, int bType, int dim) {
		return ComparatorLib.more(a, mu[dim][aType], b, mu[dim][bType], nu);
	}
	
	public boolean moreOrEq(double a, double b, int aType, int bType, int dim) {
		return ComparatorLib.moreOrEq(a, mu[dim][aType], b, mu[dim][bType], nu);
	}
	
}
