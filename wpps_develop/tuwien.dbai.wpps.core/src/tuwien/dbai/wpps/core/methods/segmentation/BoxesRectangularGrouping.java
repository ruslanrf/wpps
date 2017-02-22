/**
 * 
 */
package tuwien.dbai.wpps.core.methods.segmentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.Mapping1_1N_generic;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.common.optimization.FunctionWithMemory;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 26, 2012 12:59:08 AM
 */
public class BoxesRectangularGrouping extends AWPUWrapper {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BoxesRectangularGrouping.class);

	/**
	 * @param description
	 */
	public BoxesRectangularGrouping(AWPUMethodDescription description) {
		super(description);
	}

	@Override
	protected void _dumpIntoLM(List<IResults> results) {
//		for (IInstanceAdp instAdp : results.getResultContent()) {
//			IResults r = (IResults)instAdp;
//			ISequence seq = r.makeAs(ISequence.class);
//			addLogicalStructure(seq);
//			r.makeAs(IBoundingBlock.class);
//			r.makeAs(IQntBlock.class);
//		}
//		System.out.println(results.getResultContent());
	}
	
	@SuppressWarnings("unchecked")
	protected List<IResults> _extractResults() {
		IIEBasisAPI api = getIEAPI().getIEBasisAPI();
		IResults boxObjs = api.getObjectsByType(IBox.class);
		int i = 0;
		for (IInstanceAdp obj : boxObjs.getResultContent()) {
			_groupInRecursion(obj.as(IBox.class), i, api);
			i++;
		}
		
		List<IResults> l = new ArrayList<IResults>(intBoxesMap.getMap().size());
		for (Collection<IBox> bCol : intBoxesMap.getMap().values()) {
			IResults tmpRes = api.toResults(bCol);
			l.add(tmpRes);
		}
		List<IResults> results = new ArrayList<IResults>();
		results.add(api.toResults(l));
		return results;
	}
	
	final private Mapping1_1N_generic<Integer, IBox> intBoxesMap
		= new Mapping1_1N_generic<Integer, IBox>(Mapping1_1N_generic.ECollectionType.SET);
	final private Set<IInstanceAdp> groupedInstsSet = new HashSet<IInstanceAdp>(5000);
	@SuppressWarnings("unchecked")
	private void _groupInRecursion(final IBox currObg, final int groupNum, final IIEBasisAPI api) {
		if (groupedInstsSet.contains(currObg))
			return;
		intBoxesMap.addMapping(groupNum, currObg);
		groupedInstsSet.add(currObg);
		
		Collection<IOutline> clRectCol = currObg.getClientRects();
		if (clRectCol.size() == 0)
			return;
		final IQntBlock firstLine = firstClientRect.apply(currObg);
		final IQntBlock lastLine = lastClientRect.apply(currObg);
		final Rectangle2D firstLineArea = firstLine.getArea();
		final Rectangle2D lastLineArea = lastLine.getArea();
		final IQntBlock currQntObg = currObg.as(IQntBlock.class);
		Rectangle2D boxArea = currQntObg.getArea();
		// go down
		Rectangle2D bottomRightArea = new Rectangle2D(lastLineArea.xMax, lastLineArea.yMin
				, boxArea.xMax, boxArea.yMax);
		IInstanceAdp downItem = getNextInlineLikeBlock(bottomRightArea, currObg, 1);
		if (downItem != null)
			_groupInRecursion(downItem.as(IBox.class), groupNum, api);
		// go up
		Rectangle2D upRightArea = new Rectangle2D(boxArea.xMin, boxArea.yMin
				, firstLineArea.xMin, firstLineArea.yMax);
		IInstanceAdp upItem = getNextInlineLikeBlock(upRightArea, currObg, 0);
		if (upItem != null)
			_groupInRecursion(upItem.as(IBox.class), groupNum, api);
		
	}
	
	private final int MAX_DRAW_ID_DISTANCE = 3;
	private final double X_DEVIATION = 3;
	
	private final FunctionWithMemory<IBox, List<IQntBlock>> orderedClientRects
	 	= new FunctionWithMemory<IBox, List<IQntBlock>>(
			 new IFunction<IBox, List<IQntBlock>>() {
				@SuppressWarnings({ "unchecked", "rawtypes" })
				@Override public List<IQntBlock> apply(IBox avar) {
					final IIEBasisAPI api = getIEAPI().getIEBasisAPI();
					Collection<IOutline> c = avar.getClientRects();
					if (c.size() == 0) {
						List<IQntBlock> t = new ArrayList<IQntBlock>(1);
						t.add(avar.as(IQntBlock.class));
						return t;
					}
					Collection<IQntBlock> t = Collections2.transform(c, new Function<IOutline, IQntBlock>() {
						@Override public IQntBlock apply(IOutline arg0) {
							return arg0.as(IQntBlock.class);
						} } );
					
					
					IResults rTmp = api.orderBy(api.toResults(t),
							new IFunction<IInstanceAdp, Double>() {
								@Override public Double apply(IInstanceAdp avar) {
									return avar.as(IQntBlock.class).getYMin();
					} }, 1);
					return (List)rTmp.getResultContent();
		} } );
	
	
	private final FunctionWithMemory<IBox, IQntBlock> firstClientRect
		 = new FunctionWithMemory<IBox, IQntBlock>(
				 new IFunction<IBox, IQntBlock>() {
					@Override public IQntBlock apply(IBox avar) {
						List<IQntBlock> c = orderedClientRects.apply(avar);
						return c.get(0);
		} } );
	
	private final FunctionWithMemory<IBox, IQntBlock> lastClientRect
	 = new FunctionWithMemory<IBox, IQntBlock>(
			 new IFunction<IBox, IQntBlock>() {
				@Override public IQntBlock apply(IBox avar) {
					List<IQntBlock> c = orderedClientRects.apply(avar);
					return c.get(c.size()-1);
	} } );
	
	private boolean _isPotentialLineContinuation(final IBox primBox, final IBox refBox, int direction) {
		IQntBlock primQntClientRect = null;
		IQntBlock refQntClientRect = null;
		if (direction == 0) {
			primQntClientRect = firstClientRect.apply(primBox);
			refQntClientRect = lastClientRect.apply(refBox);
		} else if (direction == 1) {
			primQntClientRect = lastClientRect.apply(primBox);
			refQntClientRect = firstClientRect.apply(refBox);
		}
		if (Math.abs(primBox.as(IQntBlock.class).getDrawId() - refBox.as(IQntBlock.class).getDrawId())
				> MAX_DRAW_ID_DISTANCE)
				return false;
		Rectangle2D primClientRectArea = primQntClientRect.getArea();
		Rectangle2D refClientRectArea = refQntClientRect.getArea();
		if ( Math.abs(primClientRectArea.xMin - refClientRectArea.xMin)
				+ Math.abs(primClientRectArea.xMax - refClientRectArea.xMax)
				> X_DEVIATION
				)
			return false;
		return true;
		
	}
	
	private final class GET_X_F implements IFunction<IInstanceAdp, Double> {
		private int i = 0; 
		/**
		 * @param i 0 -- getxMin for top client rectangle, 1 -- getxMax for bottom client rectangle.
		 */
		public GET_X_F(int i) {
			this.i = i;
		}
		
		@Override public Double apply(IInstanceAdp avar) {
			switch (i) {
			case 0:
				return firstClientRect.apply(avar.as(IBox.class)).getXMin();
			case 1:
				return lastClientRect.apply(avar.as(IBox.class)).getXMax();
			default:
				break;
			}
			return null;
		}
	}
	
	/**
	 * @param mainQntBlock
	 * @param xSort
	 * @param direction 0 -- up, 1 -- down.
	 * @param api
	 * @return
	 */
	private IInstanceAdp getNextInlineLikeBlock(final Rectangle2D area, final IBox mainQntBlock
			, final int direction
			) {
		final IIEBasisAPI api = getIEAPI().getIEBasisAPI();
		IQntBlock mainQntBlock2 = mainQntBlock.as(IQntBlock.class);
		@SuppressWarnings("unchecked")
		IResults res = api.getObjectsIntersectingArea(mainQntBlock2.getArea()
				, null
//				, new IGenericIEFilter<IQntBlock>() {
//					@Override public tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter.EFilterResult apply(
//							IQntBlock avar) {
//						IQntBlock mainRect = null;
//						IQntBlock clientRect = null;
//						if (direction == 0) {
//							mainRect = firstClientRect.apply(mainQntBlock.as(IBox.class));
//							clientRect = lastClientRect.apply(avar.as(IBox.class));
//						} else if (direction == 1) {
//							mainRect = lastClientRect.apply(mainQntBlock.as(IBox.class));
//							clientRect = firstClientRect.apply(avar.as(IBox.class));
//						}
//						return (lineContinuation.apply(mainRect, clientRect)?IGenericIEFilter.EFilterResult.ACCEPT
//								: IGenericIEFilter.EFilterResult.REJECT);
//			} }
		, IBox.class);
		
		res = api.filter(res, new IPredicate<IInstanceAdp>() {
			@Override public Boolean apply(IInstanceAdp avar) {
				return _isPotentialLineContinuation(mainQntBlock, avar.as(IBox.class), direction);
			}
		});
		
		if (res.size() == 0)
			return null;
		
		switch (direction) {
		case 0:
			return api.orderBy(res, new GET_X_F(direction), -1).getResultContent().get(0);
		case 1:
			return api.orderBy(res, new GET_X_F(direction), 1).getResultContent().get(0);
		default:
			break;
		}
		return null;
	}
	
	

}
