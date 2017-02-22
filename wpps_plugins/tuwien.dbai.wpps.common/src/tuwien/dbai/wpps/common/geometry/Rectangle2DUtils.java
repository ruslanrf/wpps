/**
 * 
 */
package tuwien.dbai.wpps.common.geometry;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 13, 2011 10:58:37 PM
 */
// TODO rename to Rectangle2DUtils
public final class Rectangle2DUtils {

	public static final double area(Rectangle2D rec) {
		return (rec.xMax - rec.xMin) * (rec.yMax-rec.yMin);
	}
	
	public static final boolean zeroVolumeRectangle(final Rectangle2D rec) {
		return ( (rec.xMax-rec.xMin == 0) || (rec.yMax-rec.yMin == 0) );
	}
	
	@Deprecated
	public static final boolean zeroVolumeRectangle(final Point2D[] pts) {
		return ( (pts[1].x-pts[0].x == 0) || (pts[1].y-pts[0].y == 0) );
	}
	
	public static final boolean zeroVolumeRectangle(double x1, double y1, double x2, double y2) {
		return ( (x2-x1 == 0) || (y2-y1 == 0) );
	}
	
	//TODO rewrite for Rectangle2D, change name to containsPoint2D
	@Deprecated
	public static final boolean insideRectangle(final Point2D pt, final Point2D[] pts) {
		return pt.x <= pts[0].x || pt.x >= pts[1].x || pt.y <= pts[0].y || pt.y >= pts[1].y;
	}
	
//	public static final boolean overlapsQuadrant(final Point[] rect, final Point centre, int quadrantNum) {
//	}
	
	public static final boolean overlapRectangle(final Rectangle2D primRect, final Rectangle2D refRect) {
		return primRect.xMax>refRect.xMin && primRect.xMin<refRect.xMax
				&& primRect.yMax>refRect.yMin && primRect.yMin<refRect.yMax;
	}
	
	@Deprecated
	public static final boolean overlapRectangle(final Point2D[] innerRect, final Point2D[] outerRect) {
		return innerRect[1].x>outerRect[0].x && innerRect[0].x<outerRect[1].x
				&& innerRect[1].y>outerRect[0].y && innerRect[0].y<outerRect[1].y;
	}
	
	public static final boolean containsRectangle(final Rectangle2D innerRect, final Rectangle2D outerRect) {
		return innerRect.xMin >=outerRect.xMin && innerRect.xMax<=outerRect.xMax
				&& innerRect.yMin>=outerRect.yMin && innerRect.yMax<=outerRect.yMax;
	}
	
	@Deprecated
	public static final boolean containsRectangle(final Point2D[] innerRect, final Point2D[] outerRect) {
		return innerRect[0].x>=outerRect[0].x && innerRect[1].x<=outerRect[1].x
				&& innerRect[0].y>=outerRect[0].y && innerRect[1].y<=outerRect[1].y;
	}
	
	public static final Rectangle2D joinRectangles(final Rectangle2D rect1, final Rectangle2D rect2) {
		return new Rectangle2D(
				Math.min(rect1.xMin, rect2.xMin)
				, Math.min(rect1.yMin, rect2.yMin)
				, Math.max(rect1.xMax, rect2.xMax)
				, Math.max(rect1.yMax, rect2.yMax)
				);
	}
	
	@Deprecated
	public static final Point2D[] joinRectangles(final Point2D[] rect1, final Point2D[] rect2) {
		final Point2D[] rez = Point2DUtils.getUndefinedPointArr(2);
		rez[0].x = Math.min(rect1[0].x, rect2[0].x);
		rez[0].y = Math.min(rect1[0].y, rect2[0].y);
		rez[1].x = Math.max(rect1[1].x, rect2[1].x);
		rez[1].y = Math.max(rect1[1].y, rect2[1].y);
		return rez;
	}
	
	public static final Rectangle2D getIntersection(final Rectangle2D rect1, final Rectangle2D rect2) {
		final Rectangle2D rez = new Rectangle2D();
		rez.xMin = Math.max(rect1.xMin, rect2.xMin);
		rez.yMin = Math.max(rect1.yMin, rect2.yMin);
		rez.xMax = Math.min(rect1.xMin, rect2.xMax);
		rez.yMax = Math.min(rect1.yMin, rect2.yMax);
		
		if (rez.isValid())
			return rez;
		else
			return null;
		
	}
	
	@Deprecated
	public static final Point2D[] getIntersection(final Point2D[] rect1, final Point2D[] rect2) {
		final Point2D[] rez = Point2DUtils.getUndefinedPointArr(2);
		rez[0].x = Math.max(rect1[0].x, rect2[0].x);
		rez[0].y = Math.max(rect1[0].y, rect2[0].y);
		rez[1].x = Math.min(rect1[1].x, rect2[1].x);
		rez[1].y = Math.min(rect1[1].y, rect2[1].y);
		
		if (rez[0].x<=rez[1].x && rez[0].y<=rez[1].y)
			return rez;
		else
			return null;
	}
	
	/**
	 * rectangle provided is affected and returned.
	 * @param r rectangle to be moved.
	 * @param p distance for x and y to move.
	 * @return
	 */
	public static final Rectangle2D move(Rectangle2D r, Point2D p) {
		r.xMin += p.x;
		r.yMin += p.y;
		r.xMax += p.x;
		r.yMax += p.y;
		return r;
	}
	
}
