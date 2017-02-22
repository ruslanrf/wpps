/**
 * 
 */
package tuwien.dbai.wpps.common.geometry;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 16, 2011 7:12:10 AM
 */
public class Point2DUtils {
	
	public static final Point2D sum(Point2D... pArr) {
		final Point2D rez = new Point2D(pArr[0]);
		for (int i=1; i<pArr.length; i++) {
			rez.x += pArr[i].x;
			rez.y += pArr[i].y;
		}
		return rez;
	}
	
	public static final Point2D difference(Point2D p1, Point2D p2) {
		final Point2D rez = new Point2D(p1);
		rez.x -= p2.x;
		rez.y -= p2.y;
		return rez;
	}
	
	public static final Point2D difference(Point2D p1, Point2D... pArr) {
		final Point2D rez = new Point2D(p1);
		for (int i=0; i<pArr.length; i++) {
			rez.x -= pArr[i].x;
			rez.y -= pArr[i].y;
		}
		return rez;
	}

	public static final Point2D[] getPointArrWithValue(int n, Point2D p) {
		final Point2D[] rez = new Point2D[n];
		for (int i=0; i<n; i++) {
			rez[i] = new Point2D(p);
		}
		return rez;
	}

	public static final Point2D[] getZeroPointArr(int n) {
		return getPointArrWithValue(n, Point2D.getZeroPoint());
	}

	/**
	 * Create array of Points with coordinates with NAN values.
	 * @param n
	 * @return
	 */
	public static final Point2D[] getUndefinedPointArr(int n) {
		return getPointArrWithValue(n, Point2D.getUndefinedPoint());
	}

}
