/**
 * 
 */
package tuwien.dbai.wpps.core.spatialindex;

import gnu.trove.TIntProcedure;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter.EFilterResult;
import tuwien.dbai.wpps.core.spatialindex.jsi.RectangleDynamicUtils;
import tuwien.dbai.wpps.core.spatialindex.jsi.SpatialIndex;
import tuwien.dbai.wpps.core.spatialindex.jsi.rtree.RTree;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 3, 2012 5:58:07 PM
 */
@Singleton
public class RTreeManager implements ISpatialIndexManager {
	private static final Logger log = Logger.getLogger(RTreeManager.class);

	private final SpatialIndex spatialIndex;
	
	@Inject
	public RTreeManager(final RectangleDynamicUtils rectangleUtils) {
		// create and init a new spatial index
		spatialIndex = new RTree(rectangleUtils);
		Properties p = new Properties();
		p.setProperty("MaxNodeEntries", "10");  // default //10
		p.setProperty("MinNodeEntries", "5");	// half of max //1
		spatialIndex.init(p);
	}
	
	@Override
	public SpatialIndex getSpatialIndex() {
		return spatialIndex;
	}
	
	/**
	 * Array of the blocks (instances) where their index is a id of the respective rectangle in the spatial index 
	 */
	private final List<IQntBlock> blockAdapterArr = new ArrayList<IQntBlock>(10000);
	private int currFreeRectangleId = 0;

	// TODO: Try to add Resources and return QntBlocks by request. Will it be faster then current solution?
	@Override
	public void add(final IQntBlock block, double xMin, double yMin, double xMax, double yMax) {
if (log.isTraceEnabled())
	log.trace(TSForLog.getTS(log)+"START. Add block into the spatial index");
		
if (log.isTraceEnabled()) {
	MatcherAssert.assertThat(block.getXMin(), Matchers.equalTo(xMin));
	MatcherAssert.assertThat(block.getYMin(), Matchers.equalTo(yMin));
	MatcherAssert.assertThat(block.getXMax(), Matchers.equalTo(xMax));
	MatcherAssert.assertThat(block.getYMax(), Matchers.equalTo(yMax));
//			log.trace("add: "+xMin+" "+yMin+" "+xMax+" "+yMax);
}
		
		blockAdapterArr.add(block);
		spatialIndex.add(new tuwien.dbai.wpps.core.spatialindex.jsi.Rectangle(xMin, yMin, xMax, yMax)
				, currFreeRectangleId);
		currFreeRectangleId++;
		if (log.isTraceEnabled())
			log.trace(TSForLog.getTS(log)+"FINISH. Add block into the spatial index");
	}
	
	@Override
	public List<IQntBlock> getIntersections(
			final Rectangle2D block, final IGenericIEFilter<IQntBlock> filter) {
		return getIntersections(block, filter, IQntBlock.class);
	}
	
	@Override
	public <T extends IInstanceAdp> List<T> getIntersections(
			final Rectangle2D block, final IGenericIEFilter<IQntBlock> filter, final Class<T> view) {
		if (log.isTraceEnabled())
			log.trace(TSForLog.getTS(log)+"START. Get intersections");
		// create array which has capacity equal to the amount of all qntBlocks.
		final List<T> baSet = new ArrayList<T>(blockAdapterArr.size());
		final tuwien.dbai.wpps.core.spatialindex.jsi.Rectangle rect
			= new tuwien.dbai.wpps.core.spatialindex.jsi.Rectangle(block.xMin, block.yMin, block.xMax, block.yMax);
		spatialIndex.intersects(rect,
				new TIntProcedure() {
					@Override
					public boolean execute(int id) { // id of rectangle
						final IQntBlock tmpBlock = blockAdapterArr.get(id);
						if (filter == null) {
							baSet.add(tmpBlock.as(view));
							return true;
						}
						else {
							final EFilterResult res = filter.apply(tmpBlock);
							switch (res) {
							case ACCEPT:
								baSet.add(tmpBlock.as(view));
							case REJECT:
								return true;
							case ACCEPT_STOP:
								baSet.add(tmpBlock.as(view));
							case REJECT_STOP:
								return false;
							default:
								throw new UnknownValueFromPredefinedList(log, res);
							}
						}
					}
				}
		);
		if (log.isTraceEnabled())
			log.trace(TSForLog.getTS(log)+"FINISH. Get intersections");
		return baSet;
	}

	@Override
	public List<IQntBlock> getContainments(
			final Rectangle2D block, final IGenericIEFilter<IQntBlock> filter) {
		return getContainments(block, filter, IQntBlock.class);
	}
	
	@Override
	public <T extends IInstanceAdp> List<T> getContainments(
			final Rectangle2D block, final IGenericIEFilter<IQntBlock> filter, final Class<T> reprView) {
		if (log.isTraceEnabled())
			log.trace(TSForLog.getTS(log)+"START. Get containments");
		// create array which has capacity equal to the amount of all qntBlocks.
		final List<T> baSet = new ArrayList<T>(blockAdapterArr.size());
		final tuwien.dbai.wpps.core.spatialindex.jsi.Rectangle rect
				= new tuwien.dbai.wpps.core.spatialindex.jsi.Rectangle(block.xMin, block.yMin, block.xMax, block.yMax);
		spatialIndex.contains(rect,
				new TIntProcedure() {
					@Override
					public boolean execute(int id) { // id of rectangle
						final IQntBlock tmpBlock = blockAdapterArr.get(id);
						if (filter == null) {
							baSet.add(tmpBlock.as(reprView));
							return true;
						}
						else {
							final EFilterResult res = filter.apply(tmpBlock);
							switch (res) {
							case ACCEPT:
								baSet.add(tmpBlock.as(reprView));
							case REJECT:
								return true;
							case ACCEPT_STOP:
								baSet.add(tmpBlock.as(reprView));
							case REJECT_STOP:
								return false;
							default:
								throw new UnknownValueFromPredefinedList(log, res);
							}
						}
					}
				}
		);
		if (log.isTraceEnabled())
			log.trace(TSForLog.getTS(log)+"FINISH. Get containments");
		return baSet;
	}

}
