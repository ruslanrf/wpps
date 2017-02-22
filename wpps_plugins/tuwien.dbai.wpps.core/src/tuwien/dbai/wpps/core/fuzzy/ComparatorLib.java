/**
 * 
 */
package tuwien.dbai.wpps.core.fuzzy;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 1, 2012 9:16:03 AM
 */
public class ComparatorLib {

	/**
	 * @param a representative number for the fuzzy number A.
	 * @param muA membership function for the fuzzy number A.
	 * @param b representative number for the fuzzy number B.
	 * @param muB membership function for the fuzzy number B.
	 * @param nu true-function.
	 * @return <code>true</code>, if fuzzy number A less than B.
	 */
	public final static boolean less(double a, final IMuZeroDouble muA, double b, final IMuZeroDouble muB, final Nu nu) {
		return (a<b && nu.apply(muA.apply(b-a))==0 && nu.apply(muB.apply(a-b))==0)?true:false;
//		return ( a<b && !equal(a, muA, b, muB, nu))?true:false;
	}

	public final static boolean lessOrEq(double a, final IMuZeroDouble muA, double b, final IMuZeroDouble muB, final Nu nu) {
		return (a<=b || nu.apply(muA.apply(b-a))>0 || nu.apply(muB.apply(a-b))>0)?true:false;
//		return !more(a, muA, b, muB, nu);
	}

	/**
	 * @param a representative number for the fuzzy number A.
	 * @param muA membership function for the fuzzy number A.
	 * @param b representative number for the fuzzy number B.
	 * @param muB membership function for the fuzzy number B.
	 * @param nu true-function.
	 * @return <code>true</code>, if fuzzy number A equals B.
	 */
	public final static boolean equal(double a, final IMuZeroDouble muA, double b, final IMuZeroDouble muB, final Nu nu) {
		return (nu.apply(muA.apply(b-a))>0 || nu.apply(muB.apply(a-b))>0)?true:false;
	}

	/**
	 * @param a representative number for the fuzzy number A.
	 * @param muA membership function for the fuzzy number A.
	 * @param b representative number for the fuzzy number B.
	 * @param muB membership function for the fuzzy number B.
	 * @param nu true-function.
	 * @return <code>true</code>, if fuzzy number A more than B.
	 */
	public final static boolean more(double a, final IMuZeroDouble muA, double b, final IMuZeroDouble muB, final Nu nu) {
		return (a>b && nu.apply(muA.apply(b-a))==0 && nu.apply(muB.apply(a-b))==0)?true:false;
//		return (a>b && !equal(a, muA, b, muB, nu))?true:false;
	}

	public final static boolean moreOrEq(double a, final IMuZeroDouble muA, double b, final IMuZeroDouble muB, final Nu nu) {
		return (a>=b || nu.apply(muA.apply(b-a))>0 || nu.apply(muB.apply(a-b))>0)?true:false;
//		return !less(a, muA, b, muB, nu);
	}
}
