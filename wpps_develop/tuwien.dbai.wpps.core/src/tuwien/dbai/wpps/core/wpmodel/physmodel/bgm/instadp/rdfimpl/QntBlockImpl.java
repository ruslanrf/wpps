/**
 * File name: QntBlockAF.java
 * @created: Jul 23, 2011 10:13:30 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.ICheckImplInitialization;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfQntBlock;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * <P>
 * Directly, functionality for this class is used in following classes:
 * {@link RdfQntBlock}.</P>
 * <P>
 * Functions which provide implementation of the interface IQntBlock with necessary functional.
 * Implementation of this interface should be provided with Jena Model.</P>
 * <P>
 * Functions which set spatial extends of the block are used in {@link RdfBoundingBlock}.</P>
 * <P> Width and height must be computed using other data.</P>
 * 
 * @created: Jul 23, 2011 10:13:30 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public final class QntBlockImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(QntBlockImpl.class);
	
	public QntBlockImpl() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
	}
	
	public final void setGetXMin(final IArrFunction<Object, Object> getXMin) {
		this.getXMin = getXMin;
	}
	
	public final void setAddXMin(final IArrFunction<Object, Object> addXMin) {
		this.addXMin = addXMin;
	}
	
	public final void setGetYMin(final IArrFunction<Object, Object> getYMin) {
		this.getYMin = getYMin;
	}
	
	public final void setAddYMin(final IArrFunction<Object, Object> addYMin) {
		this.addYMin = addYMin;
	}
	
	public final void setGetXMax(final IArrFunction<Object, Object> getXMax) {
		this.getXMax = getXMax;
	}
	
	public final void setAddXMax(final IArrFunction<Object, Object> addXMax) {
		this.addXMax = addXMax;
	}
	
	public final void setGetYMax(final IArrFunction<Object, Object> getYMax) {
		this.getYMax = getYMax;
	}
	
	public final void setAddYMax(final IArrFunction<Object, Object> addYMax) {
		this.addYMax = addYMax;
	}
	
	public final void setGetDrawId(final IArrFunction<Object, Object> getDrawId) {
		this.getDrawId = getDrawId;
	}
	
//	public final void setHasDrawId(final IArrFunction<Object, Object> hasDrawId) {
//		this.hasDrawId = hasDrawId;
//	}
	
	public final void setAddDrawId(final IArrFunction<Object, Object> addDrawId) {
		this.addDrawId = addDrawId;
	}
	
	public final void setGetLayerId(final IArrFunction<Object, Object> getLayerId) {
		this.getLayerId = getLayerId;
	}
	
	public final void setAddLayerId(final IArrFunction<Object, Object> addLayerId) {
		this.addLayerId = addLayerId;
	}
	
	public final void setGetWidth(final IArrFunction<Object, Object> getWidth) {
		this.getWidth = getWidth;
	}
	
	public final void setGetHeight(final IArrFunction<Object, Object> getHeight) {
		this.getHeight = getHeight;
	}
	
//	@Deprecated
//	public final void setGetCentre(final IArrFunction<Object, Object> getCentre) {
//		this.getCentre = getCentre;
//	}
	
	
	
//	public final void setGetRelationAsDouble(final IArrFunction<Object, Object> getRelationAsDouble) {
//		this.getRelationAsDouble = getRelationAsDouble;
//	}
	
	public final void setGetDirection(final IArrFunction<Object, Object> getDirection) {
		this.getDirection = getDirection;
	}
	
	public final void setGetDistance(final IArrFunction<Object, Object> getDistance) {
		this.getDistance = getDistance;
	}
	
//	@Deprecated
//	public final void setGetBorderDistance(final IArrFunction<Object, Object> getBorderDistance) {
//		this.getBorderDistance = getBorderDistance;
//	}
	
	IArrFunction<Object, Object> getXMin = null;
	IArrFunction<Object, Object> addXMin = null;
	IArrFunction<Object, Object> getYMin = null;
	IArrFunction<Object, Object> addYMin = null;
	IArrFunction<Object, Object> getXMax = null;
	IArrFunction<Object, Object> addXMax = null;
	IArrFunction<Object, Object> getYMax = null;
	IArrFunction<Object, Object> addYMax = null;
	IArrFunction<Object, Object> getDrawId = null;
	IArrFunction<Object, Object> addDrawId = null;
	IArrFunction<Object, Object> getLayerId = null;
	IArrFunction<Object, Object> addLayerId = null;
//	IArrFunction<Object, Object> hasDrawId = null;
	
	IArrFunction<Object, Object> getWidth = null;
//	private IArrFunction<Object, Object> addWidth = null;
	IArrFunction<Object, Object> getHeight = null;
//	private IArrFunction<Object, Object> addHeight = null;
//	IArrFunction<Object, Object> getCentre = null;
	IArrFunction<Object, Object> getXCenter = null;
	IArrFunction<Object, Object> getYCenter = null;
//	private IArrFunction<Object, Object> addCentre = null;
	IArrFunction<Object, Object> getDirection = null;
//	private IArrFunction<Object, Object> addDirection = null;
	IArrFunction<Object, Object> getDistance = null;
//	private IArrFunction<Object, Object> addDistance = null;
//	@Deprecated
//	IArrFunction<Object, Object> getBorderDistance = null;
	IArrFunction<Object, Object> getBorderDistanceBB = null;
	IArrFunction<Object, Object> getBorderDistanceLL = null;
	IArrFunction<Object, Object> getBorderDistanceLR = null;
	IArrFunction<Object, Object> getBorderDistanceRR = null;
	IArrFunction<Object, Object> getBorderDistanceTB = null;
	IArrFunction<Object, Object> getBorderDistanceTT = null;
//	private IArrFunction<Object, Object> addBorderDistance = null;
	
	public Double getXMin(final Resource thisQntBlock) {
		Double rez = (Double)getXMin.apply(thisQntBlock);
if (rez == null && log.isTraceEnabled()) log.trace("Resource: "+thisQntBlock+" has XMin=null"); 
		return rez;
	}
	
	public void addXMin(final Resource thisQntBlock, final double val) {
		addXMin.apply(thisQntBlock, val);
	}
	
	public Double getYMin(final Resource thisQntBlock) {
		Double rez = (Double)getYMin.apply(thisQntBlock);
if (rez == null && log.isTraceEnabled()) log.trace("Resource: "+thisQntBlock+" has YMin=null"); 
		return rez;
	}
	
	public void addYMin(final Resource thisQntBlock, final double val) {
		addYMin.apply(thisQntBlock, val);
	}
	
	public Double getXMax(final Resource thisQntBlock) {
		Double rez = (Double)getXMax.apply(thisQntBlock);
if (rez == null && log.isTraceEnabled()) log.trace("Resource: "+thisQntBlock+" has XMax=null"); 
		return rez;
	}
	
	public void addXMax(final Resource thisQntBlock, final double val) {
		addXMax.apply(thisQntBlock, val);
	}
	
	public Double getYMax(final Resource thisQntBlock) {
		Double rez = (Double)getYMax.apply(thisQntBlock);
if (rez == null && log.isTraceEnabled()) log.trace("Resource: "+thisQntBlock+" has YMax=null"); 
		return rez;
	}
	
	public void addYMax(final Resource thisQntBlock, final double val) {
		addYMax.apply(thisQntBlock, val);
	}
	
	public Integer getDrawId(final Resource thisQntBlock) {
		return (Integer)getDrawId.apply(thisQntBlock);
	}
	
//	public boolean hasDrawId(final Resource thisQntBlock) {
//		return (Boolean)hasDrawId.apply(thisQntBlock);
//	}
	
	
	public void addDrawId(final Resource thisQntBlock, final int val) {
		addDrawId.apply(thisQntBlock, val);
	}
	
	public Integer getLayerId(final Resource thisQntBlock) {
		return (Integer)getLayerId.apply(thisQntBlock);
	}
	
	public void addLayerId(final Resource thisQntBlock, final int val) {
		addLayerId.apply(thisQntBlock, val);
	}
	
	public Double getWidth(final Resource thisQntBlock) {
		return (Double)getWidth.apply(thisQntBlock);
	}
	
	public Double getHeight(final Resource thisQntBlock) {
		return (Double)getHeight.apply(thisQntBlock);
	}
	
//	@Deprecated
//	public Point2D getCentre(final Resource thisQntBlock) {
//		return (Point2D)getCentre.apply(thisQntBlock);
//	}
	
	public Double getXCenter(final Resource thisQntBlock) {
		return (Double)getXCenter.apply(thisQntBlock);
	}
	
	public Double getYCenter(final Resource thisQntBlock) {
		return (Double)getYCenter.apply(thisQntBlock);
	}
	
	public Double getDirection(final Resource thisQntBlock, final Resource refQntBlock) {
		return (Double)getDirection.apply(thisQntBlock, refQntBlock);
	}
	
	public Double getDistance(final Resource thisQntBlock, final Resource refQntBlock) {
		return (Double)getDistance.apply(thisQntBlock, refQntBlock);
	}
	
	public Double getBorderDistanceBB(final Resource thisQntBlock, final Resource refQntBlock) {
		return (Double)getBorderDistanceBB.apply(thisQntBlock, refQntBlock);
	}
	
	public Double getBorderDistanceLL(final Resource thisQntBlock, final Resource refQntBlock) {
		return (Double)getBorderDistanceLL.apply(thisQntBlock, refQntBlock);
	}
	
	public Double getBorderDistanceLR(final Resource thisQntBlock, final Resource refQntBlock) {
		return (Double)getBorderDistanceLR.apply(thisQntBlock, refQntBlock);
	}
	
	public Double getBorderDistanceRR(final Resource thisQntBlock, final Resource refQntBlock) {
		return (Double)getBorderDistanceRR.apply(thisQntBlock, refQntBlock);
	}
	
	public Double getBorderDistanceTB(final Resource thisQntBlock, final Resource refQntBlock) {
		return (Double)getBorderDistanceTB.apply(thisQntBlock, refQntBlock);
	}
	
	public Double getBorderDistanceTT(final Resource thisQntBlock, final Resource refQntBlock) {
		return (Double)getBorderDistanceTT.apply(thisQntBlock, refQntBlock);
	}
	
	@Override
	public boolean allFunctionsAreImplemented() {
		if (getXMin == null
				|| addXMin ==  null
				|| getYMin == null
				|| addYMin == null
				|| getXMax == null
				|| addXMax == null
				|| getYMax == null
				|| addYMax == null
				|| getDrawId == null
				|| addDrawId == null
				|| getLayerId == null
				|| addLayerId == null
				|| getWidth == null
				|| getHeight == null
//				|| getCentre == null
				|| getXCenter == null
				|| getYCenter == null
				|| getDirection == null
				|| getDistance == null
				|| getBorderDistanceBB == null
				|| getBorderDistanceLL == null
				|| getBorderDistanceLR == null
				|| getBorderDistanceRR == null
				|| getBorderDistanceTB == null
				|| getBorderDistanceTT == null
			)
			return false;
		else
			return true;
	}
	
	
	
}
