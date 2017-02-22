/**
 * 
 */
package tuwien.dbai.wpps.common.geometry;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 3, 2012 8:16:53 PM
 */
public class Interval1D {
	
	  public double min, max;
		  
	  public Interval1D() {
		  min = Float.MAX_VALUE;
		  max = -Float.MAX_VALUE;
		  }
		  
	  /**
	   * Constructor.
	   * 
	   * @param x1 coordinate of any corner of the rectangle
	   * @param y1 (see x1)
	   * @param x2 coordinate of the opposite corner
	   * @param y2 (see x2)
	   */
	  public Interval1D(float min, float max) {
	    set(min, max);
	  }
	  
	  public Interval1D(double min, double max) {
		  set(min, max);
	  }
		  
	  public void set(double min, double max) {
		  this.min = Math.min(min, max);
		  this.max = Math.max(min, max);
	  }
	  
	  public void set(Interval1D r) {
		  min = r.min;
		  max = r.max;
	  }
	  
		@Override
		public Object clone() throws CloneNotSupportedException {
			return copy();
		}
	  
	  public Interval1D copy() {
	    return new Interval1D(min, max); 
	  }
		  
	  public boolean sameObject(Object o) {
	    return super.equals(o); 
	  }
	  
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Interval1D) {
				Interval1D o = (Interval1D)obj;
				return min == o.min && max == o.max;
			}
			return super.equals(obj);
		}
		
		@Override
		public int hashCode() {
			return (int)(min+max)/2*17;
		}
	  
	  
	  public String toString() {
	    return "(" + min + ", " + max + ")";
	  }
	  
	  public double lenght() {
	    return max - min;
	  }
	  
	  public double centre() {
	    return (min + max) / 2;
	  }

	  public boolean isValid() {
		  return min<=max;
	  }
	  
	  public boolean isUndefined() {
		  return Double.isNaN(min) || Double.isNaN(max);
	  }
	  
	  public void extend(Interval1D r) {
		  min = Math.min(min, r.min);
		  max = Math.max(max, r.max);
	  }
	  
	  public static final Interval1D getUndefinedInterval() {
		  return new Interval1D(Double.NaN, Double.NaN);
	  }
	  
	  public static final Interval1D getZeroInterval() {
		  return new Interval1D(0, 0);
	  }

}
