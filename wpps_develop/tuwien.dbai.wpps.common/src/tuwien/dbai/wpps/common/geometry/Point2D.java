/**
 * File name: Point.java
 * @created: Apr 5, 2011 11:17:06 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common.geometry;

import java.util.Objects;

/**
 * @created: Apr 5, 2011 11:17:06 AM
 * @author Ruslan (ruslanrf@gmail.com)
 * 
 * code taken from Java Spatial Index Library
 *
 */
public class Point2D implements Cloneable {
	/**
	   * The (x, y) coordinates of the point.
	   */
	  public double x, y;
	  
	  /**
	   * Constructor.
	   * 
	   * @param x The x coordinate of the point
	   * @param y The y coordinate of the point
	   */
	  public Point2D(double x, double y) {
	    this.x = x; 
	    this.y = y;
	  }
	  
	  @Deprecated
	  public Point2D(Point2D other) {
		  x = other.x;
		  y = other.y;
	  }
	  
	  /**
	   * Copy from another point into this one
	   */
	  public void set(Point2D other) {
	    x = other.x;
	    y = other.y;
	  }
	  /**
	   * Print as a string in format "(x, y)"
	   */
	  @Override
	  public String toString() {
	    return "(" + x + ", " + y + ")";
	  }
	  
	  public boolean equals(Object o) {
		  if (o instanceof Point2D) {
			  Point2D p = (Point2D)o;
			  return x == p.x && y == p.y;
		  }
		  return super.equals(o);
	  }
	  @Override
	  public int hashCode() {
		  return Objects.hashCode(this);
	  }
	  
	  /**
	   * @return X coordinate rounded to an int
	   */
	  public int xInt() {
	    return (int) Math.round(x);
	  }
	  
	  /**
	   * @return Y coordinate rounded to an int
	   */
	  public int yInt() {
	    return (int) Math.round(y);
	  }
	  
	  public Point2D invert() {
		  x = -x;
		  y = -y;
		  return this;
	  }
	  
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return copy();
	}
	  
	  public Point2D copy() {
		  final Point2D p = new Point2D(this.x, this.y);
		  return p;
	  }
	  
	  public static final Point2D getUndefinedPoint() {
		  return new Point2D(Double.NaN, Double.NaN);
	  }
	  
	  public static final Point2D getZeroPoint() {
		  return new Point2D(0, 0);
	  }
	  
}
