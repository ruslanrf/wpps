/**
 * File name: QntBlock.java
 * @created: Apr 5, 2011 11:34:52 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IAbstractBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IRdfQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl;
import tuwien.dbai.wpps.ontschema.QntBlockModelOnt;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Direct functionality for this class is provided via following interfaces:
 * {@link QntBlockImpl}.
 *
 * @created: Apr 5, 2011 11:34:52 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 * @see QntBlockModelOnt
 * @see IBlock
 */
public final class RdfQntBlock extends RdfInstanceAdp implements IRdfQntBlock {
	private static final Logger log = Logger.getLogger(RdfQntBlock.class);

	private final QntBlockImpl qntBlockImpl;
	
	@Inject
	public RdfQntBlock(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final QntBlockImpl qntBlockImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.qntBlockImpl = qntBlockImpl;
	}
	
	@Override
	public final Double getXMin() {
		return qntBlockImpl.getXMin(rdfInst);
	}

	@Override
	public final Double getYMin() {
		return qntBlockImpl.getYMin(rdfInst);
	}

	@Override
	public final Double getXMax() {
		return qntBlockImpl.getXMax(rdfInst);
	}

	@Override
	public final Double getYMax() {
		return qntBlockImpl.getYMax(rdfInst);
	}

	@Override
	public final Double getWidth() {
		return qntBlockImpl.getWidth(rdfInst);
	}

	@Override
	public final Double getHeight() {
		return qntBlockImpl.getHeight(rdfInst);
	}

	@Override
	public Double getXCenter() {
		return qntBlockImpl.getXCenter(rdfInst);
	}

	@Override
	public Double getYCenter() {
		return qntBlockImpl.getYCenter(rdfInst);
	}
	
	@Override
	public Rectangle2D getArea() {
		return new Rectangle2D(getXMin(), getYMin(), getXMax(), getYMax());
	}

	// TODO: change 0 and FLOAT:Max to real values!
	@Override
	public Rectangle2D getNorthArea() {
		return new Rectangle2D(getXMin(), 0, getXMax(), getYMin());
	}

	@Override
	public Rectangle2D getNorthEastArea() {
		return new Rectangle2D(getXMax(), 0, Float.MAX_VALUE, getYMin());
	}

	@Override
	public Rectangle2D getEastArea() {
		return new Rectangle2D(getXMax(), getYMin(), Float.MAX_VALUE, getYMax());
	}

	@Override
	public Rectangle2D getSouthEastArea() {
		return new Rectangle2D(getXMax(), getYMax(),Float.MAX_VALUE, Float.MAX_VALUE);
	}

	@Override
	public Rectangle2D getSouthArea() {
		return new Rectangle2D(getXMin(), getYMax(), getXMax(), Float.MAX_VALUE);
	}

	@Override
	public Rectangle2D getSouthWestArea() {
		return new Rectangle2D(0, getYMax(), getXMin(), Float.MAX_VALUE);
	}

	@Override
	public Rectangle2D getWestArea() {
		return new Rectangle2D(0, getYMin(), getXMin(), getYMax());
	}

	@Override
	public Rectangle2D getNorthWestArea() {
		return new Rectangle2D(0, 0, getXMin(), getYMin());
	}
	
	@Override
	public Double getRelationAsDouble(IInstanceAdp refInst,
			EBlockQntRelationType relationClass) {
		return getRelationAsDouble(refInst.as(IAbstractBlock.class), relationClass);
	}
	
	private final Double getRelationAsDouble(IAbstractBlock refInst,
			EBlockQntRelationType relationClass) {
		super.testForRdfResourceInterface(refInst);
		switch (relationClass) {
		case QNT_DISTANCE:
			return qntBlockImpl.getDistance(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource());
		case QNT_DIRECTION:
			return qntBlockImpl.getDirection(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource());
		case QNT_BORDER_DISTANCE_BB:
			return qntBlockImpl.getBorderDistanceBB(rdfInst
					, ((IRdfResourceAdp)refInst).getRdfResource());
		case QNT_BORDER_DISTANCE_LL:
			return qntBlockImpl.getBorderDistanceLL(rdfInst
					, ((IRdfResourceAdp)refInst).getRdfResource());
		case QNT_BORDER_DISTANCE_LR:
			return qntBlockImpl.getBorderDistanceLR(rdfInst
					, ((IRdfResourceAdp)refInst).getRdfResource());
		case QNT_BORDER_DISTANCE_RR:
			return qntBlockImpl.getBorderDistanceRR(rdfInst
					, ((IRdfResourceAdp)refInst).getRdfResource());
		case QNT_BORDER_DISTANCE_TB:
			return qntBlockImpl.getBorderDistanceTB(rdfInst
					, ((IRdfResourceAdp)refInst).getRdfResource());
		case QNT_BORDER_DISTANCE_TT:
			return qntBlockImpl.getBorderDistanceTT(rdfInst
					, ((IRdfResourceAdp)refInst).getRdfResource());
		default:
			throw new UnknownValueFromPredefinedList(log, relationClass);
		}
	}

	@Override
	public Integer getDrawId() {
		return qntBlockImpl.getDrawId(rdfInst);
	}

	@Override
	public Integer getLayer() {
		return qntBlockImpl.getLayerId(rdfInst);
	}

}
