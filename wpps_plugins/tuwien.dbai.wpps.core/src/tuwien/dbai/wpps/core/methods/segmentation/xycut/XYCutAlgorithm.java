/**
 * 
 */
package tuwien.dbai.wpps.core.methods.segmentation.xycut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import jdsl.core.api.Position;
import jdsl.core.api.Tree;
import jdsl.core.ref.NodeTree;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.Mapping1;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.segmentation.xycut.XYBlock.EDirection;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHTMLElementWithVisibleText;

import com.google.common.base.Preconditions;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 30, 2012 1:42:25 PM
 */
public class XYCutAlgorithm {
	private static final Logger log = Logger.getLogger(XYCutAlgorithm.class);
	
	private class Interval {
		public int a;
		public int b;
		public final int getWidth() {
			return b-a;
		}
		@Override public String toString() {
			return "["+a+", "+b+"]";
		}
	}
	
	private final IIEBasisAPI api;
	private final Collection<Class<? extends IInstanceAdp>> consideredObjectsJavaTypes;
	private final IInstanceAdp webPageBlock;
	
	private Tree xyTree;
	
	private XYLib xyLib = new XYLib();
	
	private int separationThreshold = -1;
	
	public XYCutAlgorithm(IInstanceAdp webPageBlock, IIEBasisAPI api
			, Collection<Class<? extends IInstanceAdp>> consideredObjectsJavaTypes) {
		this.webPageBlock = webPageBlock;
		this.api = api;
		this.consideredObjectsJavaTypes = consideredObjectsJavaTypes;
	}
	
	public Tree buildXYTree() {
		xyTree = new NodeTree();
if (log.isTraceEnabled()) log.trace("webPageBlock: "+webPageBlock);
		Collection<IInstanceAdp> consideredObjects = getConsideredObjects(webPageBlock, api, consideredObjectsJavaTypes);
		if (separationThreshold<0)
			separationThreshold = compDominantFontSize(consideredObjects);
		XYBlock root = createRoot(consideredObjects);
		buildXYTreeRec(root, null);
		return xyTree;
	}
	
	private int compDominantFontSize (Collection<IInstanceAdp> consideredObjects) {
		Mapping1 cntMap = new Mapping1();
		Iterator<IInstanceAdp> iter = consideredObjects.iterator();
		while (iter.hasNext()) {
			IInstanceAdp a = iter.next();
			a = xyLib.toOneOf(a, consideredObjectsJavaTypes);
			if (a!=null && a instanceof IHTMLElementWithVisibleText) {
				Float fs = ((IHTMLElementWithVisibleText)a).getFontSize();
				String s = ((IHTMLElementWithVisibleText)a).getText();
if (log.isTraceEnabled()) log.trace("compDominantFontSize:: "+a+", fs: "+fs+", s: "+s );
				int l = (s==null)?0:s.trim().length();
				if (fs != null) {
					Integer fsI = Integer.valueOf((int)(float)fs);
					Integer tmp = cntMap.getMappedObjectAs(fsI, Integer.class);
					cntMap.addMapping(fsI, (tmp==null)?l:tmp+l);
				}
			}
		}
		Iterator<Entry<Object, Object>> iter2 = cntMap.getUnderlyingMap().entrySet().iterator();
		Entry<Object, Object> max = null;
		while (iter2.hasNext()) {
			Entry<Object, Object> tmp = iter2.next();
			if (max == null || (((Integer)max.getValue())<((Integer)tmp.getValue())))
				max = tmp;
		}
		if (max == null) {
			log.warn("Cannot compute dominant font size: There is no elements with textual data.");
			return Integer.MAX_VALUE;
		}
if (log.isTraceEnabled()) log.trace("compDominantFontSize.Result:: fs: "+max.getKey()+", qnt: "+max.getValue() );
		return (Integer)max.getKey();
	}
	
	private List<IInstanceAdp> getConsideredObjects(IInstanceAdp webPageBlock, IIEBasisAPI api
			, Collection<Class<? extends IInstanceAdp>> consideredObjectsJavaTypes) {
		final IInstanceAdp wpb = webPageBlock;
		IResults res = api.getObjectsByType2(IQntBlock.class, 
				new IPredicate<IQntBlock>() {
					@Override public Boolean apply(IQntBlock avar) {
						return wpb.equals(avar.as(IBox.class).getWebPage());
		} }
		, consideredObjectsJavaTypes);
		return res.getResultContent();
	}
	
	private List<IInstanceAdp> getConsideredObjects(IInstanceAdp webPageBlock, IIEBasisAPI api, Rectangle2D area
			, Collection<Class<? extends IInstanceAdp>> consideredObjectsJavaTypes) {
		final IInstanceAdp wpb = webPageBlock;
		IResults res = api.getObjectsContainedInArea2(IQntBlock.class, area
				, new IGenericIEFilter<IQntBlock>() {
					@Override public EFilterResult apply(IQntBlock avar) {
						return (wpb.equals(avar.as(IBox.class).getWebPage()))?EFilterResult.ACCEPT
								:EFilterResult.REJECT;
		} } , consideredObjectsJavaTypes);
		return res.getResultContent();
	}
	
	
	
	private void buildXYTreeRec(XYBlock block1, Position parent) {
		if (parent == null) {
			xyTree.replaceElement(xyTree.root(), block1);
			parent = xyTree.root();
		}
		else {
			parent = xyTree.insertLastChild(parent, block1);
		}
		// 1. Calculate the horizontal and vertical projection profiles within the region of interest
		List<Interval> gaps = comProjectionProfile(block1);
		// 2. Do dovosions at large gaps
		ArrayList<XYBlock> blocks = getSegments(block1, gaps);
		for (int i=0; i<blocks.size(); i++) {
			buildXYTreeRec(blocks.get(i), parent);
		}
		
	}

	private XYBlock createRoot(Collection<IInstanceAdp> elements) {
		XYBlock b = new XYBlock(elements, webPageBlock.as(IQntBlock.class).getArea()
				, EDirection.NONE);
		return b;
	}
	
	private List<Interval> comProjectionProfile(XYBlock block1) {
		Preconditions.checkState(separationThreshold>=0);
		List<Interval> intervals = null;
		// --- Compute projection profiles ---
		ArrayList<Integer> projp = null;
		switch (block1.getDirection()) {
		case HORIZONTAL:
			projp = xyLib.buildHorizontalProjectionProfile(block1.getElements()
							, (int)(block1.getArea().height()), (int)block1.getArea().yMin);
			break;
		case VERTICAL:
			projp = xyLib.buildVerticalProjectionProfile(block1.getElements()
							, (int)(block1.getArea().width()), (int)block1.getArea().xMin);
			break;
		default: {
			ArrayList<Integer> projpH = xyLib.buildHorizontalProjectionProfile(block1.getElements()
					, (int)(block1.getArea().height()), (int)block1.getArea().yMin);
			ArrayList<Integer> projpV = xyLib.buildVerticalProjectionProfile(block1.getElements()
					, (int)(block1.getArea().width()), (int)block1.getArea().xMin);
			List<Interval> intervalsH = filterGaps(getGaps(projpH));
			List<Interval> intervalsV = filterGaps(getGaps(projpV));
			int gapsWidthH = compGapsWidth(intervalsH);
			int gapsWidthV = compGapsWidth(intervalsV);
			if (gapsWidthH>=gapsWidthV) {
				projp = projpH;
				intervals = intervalsH;
				block1.setDirection(EDirection.HORIZONTAL);
			}
			else {
				projp = projpV;
				intervals = intervalsV;
				block1.setDirection(EDirection.VERTICAL);
			}
			break;
		}
		}
		
		if (intervals == null)
			intervals = filterGaps(getGaps(projp));
		block1.setProjectionProfile(projp);
		return intervals;
	}
	
	private List<Interval> filterGaps(List<Interval> gaps) {
		List<Interval> rez = new ArrayList<Interval>(gaps.size());
		for (Interval ir : gaps) {
			if (ir.getWidth()>=separationThreshold)
				rez.add(ir);
		}
		return rez;
	}
		
	private ArrayList<XYBlock> getSegments(final XYBlock block2, final List<Interval> filteredGaps) {
		if (filteredGaps.size() == 0)
			return new ArrayList<XYBlock>(0);
		// --- Create blocks-segments ---
		// -- get cutting points
		ArrayList<Integer> cuttingPoints = new ArrayList<Integer>(filteredGaps.size());
		Iterator<Interval> iter = filteredGaps.iterator();
		while (iter.hasNext()) {
			Interval ir = iter.next();
//			if (ir.getWidth()>separationThreshold)
			cuttingPoints.add(ir.a+ir.getWidth()/2);
		}
		if (cuttingPoints.size() == 0)
			return new ArrayList<XYBlock>(0);
		// -- create blocks
		ArrayList<XYBlock> blocks = new ArrayList<XYBlock>(cuttingPoints.size()+1);
		for (int i=0; i<=cuttingPoints.size(); i++) {
			Rectangle2D realArea = new Rectangle2D();
			int min = -1;
			int max = -1;
			if (i==0) {
				if (block2.getDirection() == EDirection.HORIZONTAL)
					min = 0;
				else
					min = 0;
				max = cuttingPoints.get(i);
			}
			else {
				min = cuttingPoints.get(i-1);
				if (i==cuttingPoints.size()) {
					if (block2.getDirection() == EDirection.HORIZONTAL)
						max = (int)block2.getArea().height();
					else
						max = (int)block2.getArea().width();
				}
				else
					max = cuttingPoints.get(i);
			}
			
			if (block2.getDirection() == EDirection.HORIZONTAL) {
				realArea.yMin = min+(int)block2.getArea().yMin;
				realArea.yMax = max+(int)block2.getArea().yMin;
				realArea.xMin = (int)block2.getArea().xMin;
				realArea.xMax = (int)block2.getArea().xMax;
			}
			else {
				realArea.xMin = min+(int)block2.getArea().xMin;
				realArea.xMax = max+(int)block2.getArea().xMin;
				realArea.yMin = (int)block2.getArea().yMin;
				realArea.yMax = (int)block2.getArea().yMax;
			}
			Collection<IInstanceAdp> elements = getConsideredObjects(webPageBlock, api, realArea, consideredObjectsJavaTypes);
			XYBlock subBlock = new XYBlock(elements, realArea, block2.getDirection().opposite());
			blocks.add(subBlock);
		}
		return blocks;
	}
	
	private int compGapsWidth(List<Interval> intervalList) {
		int rez = 0;
		Iterator<Interval> iter = intervalList.iterator();
		while (iter.hasNext()) {
			Interval ir = iter.next();
			rez += ir.b - ir.a;
		}
		return rez;
	}
	
	private List<Interval> getGaps(ArrayList<Integer> projp) {
		List<Interval> rez = new LinkedList<Interval>();
		int state = 0;
		int prevVal = -1;
		int a = -1;
		for (int i=0; i<projp.size(); i++) {
			int val = projp.get(i);
			if (prevVal <= 0 && val > 0) {
				if (state == 2) {
					Interval ir = new Interval();
					Preconditions.checkState(a>=0);
					ir.a = a;
					ir.b = i;
					Preconditions.checkState(ir.b>=a);
					rez.add(ir);
				}
				state = 1;
			} else if (prevVal > 0 && val == 0) {
				if (state == 1) {
					a = i;
				}
				state = 2;
			}
			prevVal = val;
		}
		
		return rez;
	}
	
//	private int compGapsWidth(ArrayList<Integer> projp) {
//		Iterator<Integer> iter = projp.iterator();
//		// pass throught first 0
//		int tmpVal = -1;
//		while (iter.hasNext() && (tmpVal=iter.next()) == 0);
//		if (tmpVal<1) return 0;
//		int cnt = 0;
//		while (iter.hasNext()) {
//			while (iter.hasNext() && (tmpVal=iter.next()) > 0);
//			int cntTmp = (tmpVal==0)?1:0;
//			while (iter.hasNext() && (tmpVal=iter.next()) == 0)
//				cntTmp++;
//			if (tmpVal>0)
//				cnt += cntTmp;
//		}
//		return cnt;
//	}
	
	
}
