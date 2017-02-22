/**
 * 
 */
package tuwien.dbai.wpps.core.spatialindex.jsi;

import com.google.inject.Inject;

/**
 * This class is used in spatial index only!
 * 
 * Currently hardcoded to 2 dimensions, but could be extended.
 * TODO: Use double!
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @author  aled@sourceforge.net
 * @created Jan 1, 2012 8:33:49 AM
 */
public class Rectangle {

  /**
   * use primitives instead of arrays for the coordinates of the rectangle,
   * to reduce memory requirements.
   */
  public float minX, minY, maxX, maxY;
	  
  @Inject
  public Rectangle() {
	    minX = Float.MAX_VALUE;
	    minY = Float.MAX_VALUE;
	    maxX = -Float.MAX_VALUE;
	    maxY = -Float.MAX_VALUE;
	  }
	  
  /**
   * Constructor.
   * 
   * @param x1 coordinate of any corner of the rectangle
   * @param y1 (see x1)
   * @param x2 coordinate of the opposite corner
   * @param y2 (see x2)
   */
  public Rectangle(float x1, float y1, float x2, float y2) {
    set(x1, y1, x2, y2);
  }
  
  public Rectangle(double x1, double y1, double x2, double y2) {
	    set((float)x1, (float)y1, (float)x2, (float)y2);
	  }
	  
  /**
   * Sets the size of the rectangle.
   * 
   * @param x1 coordinate of any corner of the rectangle
   * @param y1 (see x1)
   * @param x2 coordinate of the opposite corner
   * @param y2 (see x2)
   */
  public void set(float x1, float y1, float x2, float y2) {
    minX = Math.min(x1, x2);
    maxX = Math.max(x1, x2);
    minY = Math.min(y1, y2);
    maxY = Math.max(y1, y2);
//	  minX = x1;
//	  maxX = x2;
//	  minY = y1;
//	  maxY = y2;
  }
  
  /**
   * Sets the size of this rectangle to equal the passed rectangle.
   */
  public void set(Rectangle r) {
    minX = r.minX;
    minY = r.minY;
    maxX = r.maxX;
    maxY = r.maxY;  
  }
  
  /**
   * Make a copy of this rectangle
   * 
   * @return copy of this rectangle
   */
  public Rectangle copy() {
    return new Rectangle(minX, minY, maxX, maxY); 
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
  
  
  /**
   * Return a string representation of this rectangle, in the form: 
   * (1.2, 3.4), (5.6, 7.8)
   * 
   * @return String String representation of this rectangle.
   */
  public String toString() {
    return "(" + minX + ", " + minY + "), (" + maxX + ", " + maxY + ")";
  }
  
  /**
   * Utility methods (not used by JSI); added to 
   * enable this to be used as a generic rectangle class 
   */
  public float width() {
    return maxX - minX;
  }
  
  public float height() {
    return maxY - minY;
  }
  
  public float aspectRatio() {
    return width() / height();
  }
  
  public Point centre() {
    return new Point((minX + maxX) / 2, (minY + maxY) / 2);
  }
  
}
