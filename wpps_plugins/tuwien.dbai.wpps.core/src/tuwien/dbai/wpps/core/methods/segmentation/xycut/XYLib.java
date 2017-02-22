/**
 * 
 */
package tuwien.dbai.wpps.core.methods.segmentation.xycut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.google.common.base.Preconditions;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 30, 2012 1:27:35 PM
 */
public class XYLib {
	private static final Logger log = Logger.getLogger(XYLib.class);
	
	/**
	 * @param col
	 * @param height
	 * @param offset
	 * @param mode 0 - horizontal, 1 - vertical
	 * @return
	 */
	private ArrayList<Integer> buildProjectionProfile(Collection<IInstanceAdp> col, int height, int offset
			, int mode) {
//if (log.isTraceEnabled()) log.trace("height="+height+", offset="+offset);

		ArrayList<Integer> pr = new ArrayList<Integer>(height+1);
		for (int i=0; i<height;i++)
			pr.add(0);
		Iterator<IInstanceAdp> iter = col.iterator();
		while (iter.hasNext()) {
			IInstanceAdp tmpAdp = iter.next();
			Rectangle2D r = tmpAdp.as(IQntBlock.class).getArea();
			int min = -1;
			int max = -1;
			if (mode == 0) {
				min = (int)Math.round(r.yMin)-offset;
				max = (int)Math.round(r.yMax)-offset;
			} else if (mode == 1) {
				min = (int)Math.round(r.xMin)-offset;
				max = (int)Math.round(r.xMax)-offset;
			}
			Preconditions.checkState(min<=max);
			if (min>=0 && max<=height) { // TODO: some elements can be out of the window for some reason.
				for (int i=min; i<=max; i++) {
					if (i>=0 && i<pr.size())
						pr.set(i, pr.get(i)+1);
			} } else
				if (log.isDebugEnabled()) log.debug("Rdf individ. "+tmpAdp+" is out of the window ["+offset+", "+(height+offset)+"]");
			
		}
		return pr;
	}
	
	public ArrayList<Integer> buildHorizontalProjectionProfile(Collection<IInstanceAdp> col, int height, int offset) {
		return buildProjectionProfile(col, height, offset, 0);
	}
	
	public ArrayList<Integer> buildVerticalProjectionProfile(Collection<IInstanceAdp> col, int width, int offset) {
		return buildProjectionProfile(col, width, offset, 1);
	}
	
	public boolean oneOf(IInstanceAdp target, Collection<Class<? extends IInstanceAdp>> objectsToBeConsidered) {
		Iterator<Class<? extends IInstanceAdp>> iter = objectsToBeConsidered.iterator();
		while (iter.hasNext()) {
			if (target.canAs(iter.next()))
				return true;
		}
		return false;
	}
	
	public IInstanceAdp toOneOf(IInstanceAdp target, Collection<Class<? extends IInstanceAdp>> objectsToBeConsidered) {
		Iterator<Class<? extends IInstanceAdp>> iter = objectsToBeConsidered.iterator();
		while (iter.hasNext()) {
			Class<? extends IInstanceAdp> cl =iter.next(); 
			if (target.canAs(cl))
				return target.as(cl);
		}
		return null;
	}

}
