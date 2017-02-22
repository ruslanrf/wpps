/**
 * 
 */
package tuwien.dbai.wpps.common;

/**
 * TODO describe!
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 28, 2012 9:02:04 PM
 */
public class SFuzzyComparatorDouble {
	
	public static boolean less(double var1, double var2, double d) {
		return var2 - var1 > d;
	}
	
	public static boolean lessProc(double var1, double var2, double proc) {
		return var1/var2*100 < 100-proc;
	}
	
	public static boolean lessProc(double var1, double var2, double proc, double mind) {
		if (var2 == 0)
			return less(var1, var2, mind);
		return var1/var2*100 < 100-proc && less(var1, var2, mind);
	}
	
	public static boolean lessOrEq(double var1, double var2, double d) {
		return var1 - var2 <= d;
	}
	
	public static boolean lessOrEqProc(double var1, double var2, double proc) {
		return var1/var2*100 <= 100+proc;
	}
	
	public static boolean lessOrEqProc(double var1, double var2, double proc, double mind) {
		if (var2 == 0)
			return lessOrEq(var1, var2, mind);
		return var1/var2*100 <= 100+proc || lessOrEq(var1, var2, mind);
	}
	
	public static boolean equal(double var1, double var2, double d) {
		return var1 - var2 >=-d && var1 - var2 <= d;
	}
	
	public static boolean equalProc(double var1, double var2, double proc) {
		return var1/var2*100 >=100-proc && var1/var2*100 <= 100+proc;
	}
	
	public static boolean equalProc(double var1, double var2, double proc, double mind) {
		if (var2 == 0)
			return equal(var1, var2, mind);
		return var1/var2*100 >=100-proc && var1/var2*100 <= 100+proc || equal(var1, var2, mind);
	}
	
	public static boolean more(double var1, double var2, double d) {
		return var1 - var2 > d;
	}
	
	public static boolean moreProc(double var1, double var2, double proc) {
		return var1/var2*100 > 100+proc;
	}
	
	public static boolean moreProc(double var1, double var2, double proc, double mind) {
		if (var2 == 0)
			return more(var1, var2, mind);
		return var1/var2*100 > 100+proc && more(var1, var2, mind);
	}
	
	public static boolean moreOrEq(double var1, double var2, double d) {
		return var1 - var2 >= -d;
	}
	
	public static boolean moreOrEqProc(double var1, double var2, double proc) {
		return var1/var2*100 >= 100-proc;
	}
	
	public static boolean moreOrEqProc(double var1, double var2, double proc, double mind) {
		if (var2 == 0)
			return moreOrEq(var1, var2, mind);
		return var1/var2*100 >= 100-proc || moreOrEq(var1, var2, mind);
	}
	
}
