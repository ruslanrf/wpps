/**
 * 
 */
package tuwien.dbai.wpps.core.spatialindex;

import java.util.List;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.spatialindex.jsi.SpatialIndex;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 3, 2012 5:57:35 PM
 */
public interface ISpatialIndexManager {
	
//	/**
//	 * ACCEPT --- accept value during querying.
//	 * REJECT --- reject value during querying.
//	 * STOP --- stop querying.
//	 * 
//	 * Actually filter is applied during the walk trough the returned values.
//	 * 
//	 * @author Ruslan (ruslanrf@gmail.com)
//	 * @created Jan 3, 2012 6:25:30 PM
//	 */
//	public static enum EFilterResult{ACCEPT, REJECT, ACCEPT_STOP, REJECT_STOP};
//	
//	/**
//	 * Interface for filtering results gotten from {@linkplain ISpatialIndexManager#getIntersections(IQntBlock)}
//	 * or {@linkplain ISpatialIndexManager#getContainments(IQntBlock)} for instance.
//	 * 
//	 * @author Ruslan (ruslanrf@gmail.com)
//	 * @created Jan 3, 2012 6:03:24 PM
//	 */
//	public interface IFilter extends IFunction<Resource, EFilterResult> {}
	
	// TODO: Remove this class use linked list! We also do not need info about coordinates!
//	public static class SpatialIndexQueryResult<T extends IInstanceAdp> extends LinkedHashSet<T> {
//		private static final long serialVersionUID = 4185942350664033622L;
//		
//		private Rectangle boundingRectangle = null;
//		public final Rectangle getBoundingRectangle() {
//			return boundingRectangle;
//		}
//		
//		public boolean add(final T block, final Rectangle rect) {
//			return add(block, rect.xMin, rect.xMax, rect.yMin, rect.yMax);
//		}
//		
//		public boolean add(final T block, double xMin, double xMax, double yMin, double yMax) {
//			if (boundingRectangle == null)
//				boundingRectangle = new Rectangle(xMin, yMin, xMax, yMax);
//			else {
//				if (boundingRectangle.xMin>xMin)
//					boundingRectangle.xMin = (float)xMin;
//				if (boundingRectangle.yMin>yMin)
//					boundingRectangle.yMin = (float)yMin;
//				if (boundingRectangle.xMax<xMax)
//					boundingRectangle.xMax = (float)xMax;
//				if (boundingRectangle.yMax<yMax)
//					boundingRectangle.yMax = (float)yMax;
//			}
//			return super.add(block);
//		}
//		
//	}
	
	/**
	 * Get wrapped spatial index.
	 */
	SpatialIndex getSpatialIndex();
	
	/**
	 * Add quantitative block into the spatial indexing.
	 * @param block
	 */
	public void add(IQntBlock t, double xMin, double yMin, double xMax, double yMax);
	
	/**
	 * Get all quantitative boxes intersecting current one.
	 * @param block
	 * @param filter can be {@code null}.
	 * @return
	 */
	public List<IQntBlock> getIntersections(
			Rectangle2D block, IGenericIEFilter<IQntBlock> filter);
	
	/**
	 * @param block
	 * @param filter can be {@code null}.
	 * @param view all objects will be converted to this type.
	 * @return
	 */
	public <T extends IInstanceAdp> List<T> getIntersections(
			final Rectangle2D block, final IGenericIEFilter<IQntBlock> filter, final Class<T> view);
	
	/**
	 * Get all quantitative blocks contained in the current one.
	 * @param block
	 * @param filter can be {@code null}.
	 * @return
	 */
	public List<IQntBlock> getContainments(
			final Rectangle2D block, final IGenericIEFilter<IQntBlock> filter);
	
	/**
	 * @param block
	 * @param filter can be {@code null}.
	 * @param view used to represent all onjects gotten.
	 * @return
	 */
	public <T extends IInstanceAdp> List<T> getContainments(
			final Rectangle2D block, final IGenericIEFilter<IQntBlock> filter, final Class<T> view);
	

}
