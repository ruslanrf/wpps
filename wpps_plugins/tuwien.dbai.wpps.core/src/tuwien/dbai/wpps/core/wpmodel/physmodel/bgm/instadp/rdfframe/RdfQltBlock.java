/**
 * File name: QltBlock.java
 * @created: Apr 5, 2011 10:50:46 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IAbstractBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QltBlockImpl;
import tuwien.dbai.wpps.ontschema.QltBlockModelOnt;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Direct functionality for this class is provided via following interfaces:
 * {@link QltBlockImpl}.
 *
 * @created: Apr 5, 2011 10:50:46 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * 
 * @see QltBlockModelOnt
 * @see IBlock
 * 
 */
public final class RdfQltBlock extends RdfInstanceAdp implements IQltBlock {
	private static final Logger log = Logger.getLogger(RdfQltBlock.class);
	
	private final QltBlockImpl qltBlockImpl;
	
	@Inject
	public RdfQltBlock(
			@Assisted final Resource inst,
//			@AnnotStructBlockGeomModel final Model rdfModel,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl,
			final QltBlockImpl qltBlockImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl);
		this.qltBlockImpl = qltBlockImpl;
	}
	
	@Override
	public boolean hasRelation(IInstanceAdp refInst, EBlockQltRelation rel) {
		return hasRelation(refInst.as(IAbstractBlock.class), rel);
	}
	
	private boolean hasRelation(IAbstractBlock refInst, EBlockQltRelation relationType) {
		super.testForRdfResourceInterface(refInst);
		switch (relationType.getRelationType()) {
		case IR2D: {
			if (relationType.isBasicRelation())
				return qltBlockImpl.hasBasicIntervalRelation2D(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource()
						,relationType);
			else
				return qltBlockImpl.hasCompositeIntervalRelation2D(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource()
						,relationType);
		}
		case Alignment: {
			if (relationType.isBasicRelation())
				return qltBlockImpl.hasBasicPositiveAlignmentRelation(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource()
						,relationType);
			else
				return qltBlockImpl.hasCompositeAndBasicNegativeAlignmentRelation(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource()
						,relationType);
		}
		case ODirection: {
			if (relationType.isBasicRelation())
				return qltBlockImpl.hasBasicODirectionRelation(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource()
						,relationType);
			else
				return qltBlockImpl.hasCompositeODirectionRelation(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource()
						,relationType);
		}
		case PDirection: {
			if (relationType.isBasicRelation())
				return qltBlockImpl.hasBasicPDirectionRelation(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource()
						,relationType);
			else
				return qltBlockImpl.hasCompositePDirectionRelation(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource()
						,relationType);
		}
		case RCC8: {
			if (relationType.isBasicRelation())
				return qltBlockImpl.hasBasicRCC8Relation(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource()
						,relationType);
			else
				return qltBlockImpl.hasCompositeRCC8Relation(rdfInst, ((IRdfResourceAdp)refInst).getRdfResource()
						,relationType);
		}
		case NeighboringBlock:
			if (relationType.isBasicRelation())
				return qltBlockImpl.hasBasicOrIncompleteCompositeNeighboringRelation(rdfInst
						, ((IRdfResourceAdp)refInst).getRdfResource(), relationType);
			else
				return qltBlockImpl.hasCompleteCompositeNeighboringRelation(rdfInst
						, ((IRdfResourceAdp)refInst).getRdfResource(), relationType);
		case OrthogonallyVisibleBlock:
			if (relationType.isBasicRelation())
				return qltBlockImpl.hasBasicOrthogonalVisibilityRelation(rdfInst
						, ((IRdfResourceAdp)refInst).getRdfResource(), relationType);
			else
				return qltBlockImpl.hasCompleteOrthogonalVisibilityRelation(rdfInst
						, ((IRdfResourceAdp)refInst).getRdfResource(), relationType);
		case Distance:
		default:
			throw new UnknownValueFromPredefinedList(log, relationType.getRelationType());
		}
	}

}
