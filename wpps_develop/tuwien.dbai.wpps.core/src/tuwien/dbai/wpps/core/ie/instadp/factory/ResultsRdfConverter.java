/**
 * 
 */
package tuwien.dbai.wpps.core.ie.instadp.factory;

import java.util.Iterator;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.UnknownType;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.ObjectsPublicFactory;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequenceItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITree;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.google.inject.Inject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 14, 2012 1:08:11 AM
 */
public class ResultsRdfConverter {
private static final Logger log = Logger.getLogger(ResultsRdfConverter.class);
	
	private final ObjectsPublicFactory objectsPublicFactory;
	
	@Inject
	public ResultsRdfConverter(
			ObjectsPublicFactory objectsPublicFactory
			) {
		this.objectsPublicFactory = objectsPublicFactory;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IInstanceAdp> T convert(final IResults res
			, final Class<T> view) {
		// Composite logical objects
		if (ISequence.class.equals(view)) {
			ISequence seq = objectsPublicFactory.createEmptySequence(res);
			final Iterator<IInstanceAdp> iter = res.iterator();
			while (iter.hasNext()) {
				final IInstanceAdp t = iter.next();
				ISequenceItem seqI = objectsPublicFactory.createSequenceItem(t);
				seq.appendItem(seqI);
				final String str = t.getString();
				if (str != null) seqI.addStringContent(str);
			}
			return (T)seq;
		}
		else if (ITree.class.equals(view)) {
			//TODO: Implement.
		}
		
		// Block based Geometric Objects
		else if (IBoundingBlock.class.equals(view)) {
			IBoundingBlock bb = objectsPublicFactory.createEmptyBoundingBlock(res);
			final Iterator<IInstanceAdp> iter = res.iterator();
			while (iter.hasNext()) {
				final IInstanceAdp t = iter.next();
				if (t.canAs(IBlock.class))
					bb.addContainedBlock(t.as(IBlock.class));
			}
			return (T)bb;
		}
		// QntBlock
		else if (IQntBlock.class.equals(view)) {
			Rectangle2D area = calcArea(res.iterator());
			return (T)objectsPublicFactory.createQntBlock(res, area);
		}
		throw new UnknownType(log, view.getCanonicalName());
	}
	
	private Rectangle2D calcArea(Iterator<IInstanceAdp> iter) {
		Rectangle2D area = Rectangle2D.getUndefinedRectangle();
		while (iter.hasNext()) {
			IInstanceAdp t = iter.next();
			if (t.canAs(IQntBlock.class)) {
				final IQntBlock qntBlock = t.as(IQntBlock.class);
				final double xMin2 = qntBlock.getXMin();
				final double yMin2 = qntBlock.getYMin();
				final double xMax2 = qntBlock.getXMax();
				final double yMax2 = qntBlock.getYMax();
				if (Double.isNaN(area.xMin) || area.xMin>xMin2) area.xMin =xMin2;
				if (Double.isNaN(area.yMin) || area.yMin>yMin2) area.yMin =yMin2;
				if (Double.isNaN(area.xMax) || area.xMax<xMax2) area.xMax =xMax2;
				if (Double.isNaN(area.yMax) || area.yMax<yMax2) area.yMax =yMax2;
			}
		}
		return area;
	}
}
