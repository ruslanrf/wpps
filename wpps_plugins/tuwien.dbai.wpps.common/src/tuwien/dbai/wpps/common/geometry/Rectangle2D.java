/**
 * 
 */
package tuwien.dbai.wpps.common.geometry;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @author  aled@sourceforge.net
 * @created Jan 1, 2012 8:33:49 AM
 */
public class Rectangle2D implements Cloneable {

  /**
   * use primitives instead of arrays for the coordinates of the rectangle,
   * to reduce memory requirements.
   */
  public double xMin, yMin, xMax, yMax;
	  
  public Rectangle2D() {
	    xMin = Float.MAX_VALUE;
	    yMin = Float.MAX_VALUE;
	    xMax = -Float.MAX_VALUE;
	    yMax = -Float.MAX_VALUE;
	  }
	  
  /**
   * Constructor.
   * 
   * @param x1 coordinate of any corner of the rectangle
   * @param y1 (see x1)
   * @param x2 coordinate of the opposite corner
   * @param y2 (see x2)
   */
  public Rectangle2D(float x1, float y1, float x2, float y2) {
    set(x1, y1, x2, y2);
  }
  
  public Rectangle2D(double x1, double y1, double x2, double y2) {
	    set(x1, y1, x2, y2);
	  }
  
  public Rectangle2D(final Point2D p1, final Point2D p2) {
	  set(p1.x, p1.y, p2.x, p2.y);
  }
	  
  /**
   * Sets the size of the rectangle.
   * 
   * @param x1 coordinate of any corner of the rectangle
   * @param y1 (see x1)
   * @param x2 coordinate of the opposite corner
   * @param y2 (see x2)
   */
  public void set(double x1, double y1, double x2, double y2) {
    xMin = Math.min(x1, x2);
    xMax = Math.max(x1, x2);
    yMin = Math.min(y1, y2);
    yMax = Math.max(y1, y2);
//	  xMin = x1;
//	  xMax = x2;
//	  yMin = y1;
//	  yMax = y2;
  }
  
  /**
   * Sets the size of this rectangle to equal the passed rectangle.
   */
  public void set(Rectangle2D r) {
    xMin = r.xMin;
    yMin = r.yMin;
    xMax = r.xMax;
    yMax = r.yMax;  
  }
  
  public Point2D getPoint1() {
	  return new Point2D(xMin, yMin);
  }
  
  public Point2D getPoint2() {
	  return new Point2D(xMax, yMax);
  }
  
	@Override
	public Object clone() throws CloneNotSupportedException {
		return copy();
	}
  
  /**
   * Make a copy of this rectangle
   * 
   * @return copy of this rectangle
   */
  public Rectangle2D copy() {
    return new Rectangle2D(xMin, yMin, xMax, yMax); 
  }
	  
  /** 
   * Determine whether this rectangle is the same as another object
   * 
   * Note that two rectangles can be equal but not the same object, 
   * if they both have the same bounds.
   * 
   * @param o The object to compare with this rectangle.
   */  
  public boolean sameObject(Object o) {
    return super.equals(o); 
  }
  
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Rectangle2D) {
			Rectangle2D o = (Rectangle2D)obj;
			return xMin == o.xMin && xMax == o.xMax && yMin == o.yMin && yMax == o.yMax;
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return (int)(xMin+yMin+xMax+yMax)/4*17;
	}
  
  
  /**
   * Return a string representation of this rectangle, in the form: 
   * (1.2, 3.4), (5.6, 7.8)
   * 
   * @return String String representation of this rectangle.
   */
  public String toString() {
    return "(" + xMin + ", " + yMin + "), (" + xMax + ", " + yMax + ")";
  }
  
  /**
   * Utility methods (not used by JSI); added to 
   * enable this to be used as a generic rectangle class 
   */
  public double width() {
    return xMax - xMin;
  }
  
  public double height() {
    return yMax - yMin;
  }
  
  public double aspectRatio() {
    return width() / height();
  }
  
  public Point2D centre() {
    return new Point2D((xMin + xMax) / 2, (yMin + yMax) / 2);
  }

  public boolean isValid() {
	  return xMin<=xMax && yMin<=yMax;
  }
  
  public boolean isUndefined() {
	  return Double.isNaN(xMin) || Double.isNaN(yMin) || Double.isNaN(xMax) || Double.isNaN(yMax);
  }
  
  public void extend(Rectangle2D r) {
	  xMin = Math.min(xMin, r.xMin);
	  yMin = Math.min(yMin, r.yMin);
	  xMax = Math.max(xMax, r.xMax);
	  yMax = Math.max(yMax, r.yMax);
  }
  
  public static final Rectangle2D getUndefinedRectangle() {
	  return new Rectangle2D(Double.NaN, Double.NaN, Double.NaN, Double.NaN);
  }
  
  public static final Rectangle2D getZeroRectangle() {
	  return new Rectangle2D(0, 0, 0, 0);
  }
  
}
