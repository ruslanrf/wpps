/**
 * 
 */
package tuwien.dbai.wpps.core.spatialindex.jsi;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.callback.IArrFunction;

import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 1, 2012 8:35:54 AM
 */
@Singleton
public final class RectangleDynamicUtils {
	private static final Logger log = Logger.getLogger(RectangleDynamicUtils.class);

	private final IArrFunction<Float, Boolean> intersects;
	private final IArrFunction<Float, Boolean> contains;
	
	public RectangleDynamicUtils(final IArrFunction<Float, Boolean> intersects
			, final IArrFunction<Float, Boolean> contains) {
		this.intersects = intersects;
		this.contains = contains;
	}
	
	
	/**
	   * Determine whether or not two rectangles intersect
	   * 
	   * @param r1MinX minimum X coordinate of rectangle 1
	   * @param r1MinY minimum Y coordinate of rectangle 1
	   * @param r1MaxX maximum X coordinate of rectangle 1
	   * @param r1MaxY maximum Y coordinate of rectangle 1
	   * @param r2MinX minimum X coordinate of rectangle 2
	   * @param r2MinY minimum Y coordinate of rectangle 2
	   * @param r2MaxX maximum X coordinate of rectangle 2
	   * @param r2MaxY maximum Y coordinate of rectangle 2
	   * 
	   * @return true if r1 intersects r2, false otherwise.
	   */
	  public final boolean intersects(float r1MinX, float r1MinY, float r1MaxX, float r1MaxY,
			  float r2MinX, float r2MinY, float r2MaxX, float r2MaxY) {
		  final boolean rez = intersects.apply(r1MinX, r1MaxX, r1MinY, r1MaxY, r2MinX, r2MaxX, r2MinY, r2MaxY);
//		  log.trace("intersects ("+rez+") for ["+r1MinX+", "+r1MinY+", "+r1MaxX+", "+r1MaxY+"] ["+
//                   r2MinX+", "+r2MinY+", "+r2MaxX+", "+r2MaxY+"]");
		  
		  return rez;
	  }
	
	  /**
	   * Determine whether or not one rectangle contains another.
	   * 
	   * @param r1MinX minimum X coordinate of rectangle 1
	   * @param r1MinY minimum Y coordinate of rectangle 1
	   * @param r1MaxX maximum X coordinate of rectangle 1
	   * @param r1MaxY maximum Y coordinate of rectangle 1
	   * @param r2MinX minimum X coordinate of rectangle 2
	   * @param r2MinY minimum Y coordinate of rectangle 2
	   * @param r2MaxX maximum X coordinate of rectangle 2
	   * @param r2MaxY maximum Y coordinate of rectangle 2
	   * 
	   * @return true if r1 contains r2, false otherwise.
	   */
	  public final boolean contains(float r1MinX, float r1MinY, float r1MaxX, float r1MaxY,
	                                 float r2MinX, float r2MinY, float r2MaxX, float r2MaxY) {
		  final boolean rez = contains.apply(r1MinX, r1MaxX, r1MinY, r1MaxY, r2MinX, r2MaxX, r2MinY, r2MaxY);
//		  log.trace("contains ("+rez+") for ["+r1MinX+", "+r1MinY+", "+r1MaxX+", "+r1MaxY+"] ["+
//                   r2MinX+", "+r2MinY+", "+r2MaxX+", "+r2MaxY+"]");
		  
		  return rez;
	  }
	
	  public final float distanceSq(float minX, float minY, float maxX, float maxY, float pX, float pY) {
	    float distanceSqX = 0;
	    float distanceSqY = 0;
	    
	    if (minX > pX) {
	      distanceSqX = minX - pX;
	      distanceSqX *= distanceSqX;
	    } else if (pX > maxX) {
	      distanceSqX = pX - maxX;
	      distanceSqX *= distanceSqX;
	    }
	   
	    if (minY > pY) {
	      distanceSqY = minY - pY;
	      distanceSqY *= distanceSqY;
	    } else if (pY > maxY) {
	      distanceSqY = pY - maxY;
	      distanceSqY *= distanceSqY;
	    }
	   
	    return distanceSqX + distanceSqY;
	  }
	  
	  /**
	    * Calculate the area by which a rectangle would be enlarged if
	    * added to the passed rectangle..
	    * 
	    * @param r1MinX minimum X coordinate of rectangle 1
	    * @param r1MinY minimum Y coordinate of rectangle 1
	    * @param r1MaxX maximum X coordinate of rectangle 1
	    * @param r1MaxY maximum Y coordinate of rectangle 1
	    * @param r2MinX minimum X coordinate of rectangle 2
	    * @param r2MinY minimum Y coordinate of rectangle 2
	    * @param r2MaxX maximum X coordinate of rectangle 2
	    * @param r2MaxY maximum Y coordinate of rectangle 2
	    * 
	    * @return enlargement
	    */
	  public final float enlargement(float r1MinX, float r1MinY, float r1MaxX, float r1MaxY,
	                                  float r2MinX, float r2MinY, float r2MaxX, float r2MaxY) { 
	    float r1Area = (r1MaxX - r1MinX) * (r1MaxY - r1MinY);                    
	    
	    if (r1Area == Float.POSITIVE_INFINITY) {
	      return 0; // cannot enlarge an infinite rectangle...
	    }
	    
	    if (r2MinX < r1MinX) r1MinX = r2MinX;   
	    if (r2MinY < r1MinY) r1MinY = r2MinY;   
	    if (r2MaxX > r1MaxX) r1MaxX = r2MaxX;
	    if (r2MaxY > r1MaxY) r1MaxY = r2MaxY;
	    
	    float r1r2UnionArea = (r1MaxX - r1MinX) * (r1MaxY - r1MinY);
	          
	    if (r1r2UnionArea == Float.POSITIVE_INFINITY) {
	      // if a finite rectangle is enlarged and becomes infinite,
	      // then the enlargement must be infinite.
	      return Float.POSITIVE_INFINITY;
	    }
	    return r1r2UnionArea - r1Area;                              
	  }
	  
	  /**
	   * Compute the area of a rectangle.
	   * 
	   * @param minX the minimum X coordinate of the rectangle
	   * @param minY the minimum Y coordinate of the rectangle
	   * @param maxX the maximum X coordinate of the rectangle
	   * @param maxY the maximum Y coordinate of the rectangle
	   * 
	   * @return The area of the rectangle
	   */
	  public final float area(float minX, float minY, float maxX, float maxY) {
	    return (maxX - minX) * (maxY - minY);
	  }
	  
	  
	
}
