/**
 * 
 */
package tuwien.dbai.wpps.core.ie.instadp.factory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.UnknownType;
import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.instadp.interf.IGenericResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstanceFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequenceItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.LogicalObjectImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.SequenceImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 23, 2012 6:09:46 PM
 */
@Singleton @Deprecated
public class GenericResultsRdfConverter {
	private static final Logger log = Logger.getLogger(GenericResultsRdfConverter.class);
	
	private final RdfInstanceFactoryWrap rdfInstanceFactoryWrap;
	private final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap;
	
	final LogicalObjectImpl logicalObjectImpl;
	
	private final SequenceImpl sequenceImpl;
	
//	private final BoundingBlockImpl boundingBlockImpl;
	
	@Inject
	public GenericResultsRdfConverter(
			final RdfInstanceFactoryWrap rdfInstanceFactoryWrap
			, final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap
			, final LogicalObjectImpl logicalObjectImpl
			, final SequenceImpl sequenceImpl
//			, final BoundingBlockImpl boundingBlockImpl
			) {
		this.rdfInstanceFactoryWrap = rdfInstanceFactoryWrap;
		this.rdfInstAdpFactoryWrap = rdfInstAdpFactoryWrap;
		
		this.logicalObjectImpl = logicalObjectImpl;
		this.sequenceImpl = sequenceImpl;
		
//		this.boundingBlockImpl = boundingBlockImpl;
	}
	
	public <T extends IInstanceAdp, U extends IInstanceAdp> U convert(final IGenericResults<T> res
			, final Class<U> view) {
		Resource rez = null;
		// Composite logical objects
		if (ISequence.class.equals(view)) {
			rez = rdfInstanceFactoryWrap.createObject(InstAdpLibSupport.getResourceOrNull(res), ISequence.class);
			final Iterator<T> iter = res.iterator();
			while (iter.hasNext()) {
				final T t = iter.next();
				final Resource itmRes = rdfInstanceFactoryWrap.createObject(
						InstAdpLibSupport.getResourceOrNull(t), ISequenceItem.class);
				final String str = t.getString();
				if (str != null)
					logicalObjectImpl.addStringContent(itmRes, str);
				sequenceImpl.appendItem(rez, itmRes);
			}
		}
		
		// Block based Geometric Objects
		else if (IBoundingBlock.class.equals(view)) {
			final List<Resource> blocksRdfList = new ArrayList<Resource>(res.size());
			final Iterator<T> iter = res.iterator();
			while (iter.hasNext()) {
				final Resource blockRdf = InstAdpLibSupport.getResourceOrNull(iter.next());
				if (blockRdf == null)
					log.warn(TSForLog.getTS(log)+" is not an RDF resource");
				else
					blocksRdfList.add(blockRdf);
			}
			rez = rdfInstanceFactoryWrap.createObject(InstAdpLibSupport.getResourceOrNull(res), IBoundingBlock.class
					, (Object[])blocksRdfList.toArray(new Resource[blocksRdfList.size()]));
		}
		else if (IQntBlock.class.equals(view)) {
			final Rectangle2D area = new Rectangle2D(Point2D.getUndefinedPoint(), Point2D.getUndefinedPoint());
			for (final T t : res.getResultContent()) {
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
			rez = rdfInstanceFactoryWrap.createObject(InstAdpLibSupport.getResourceOrNull(res), IQntBlock.class, area);
		}
		else
			throw new UnknownType(log, view.getCanonicalName());
		return rdfInstAdpFactoryWrap.createAdp(rez, view);
	}
	
}
