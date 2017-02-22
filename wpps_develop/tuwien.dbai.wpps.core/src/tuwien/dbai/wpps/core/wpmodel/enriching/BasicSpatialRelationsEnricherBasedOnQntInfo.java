/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.enriching;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMBorderNu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMCenterMu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMLeftBorderMu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMRightBorderMu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBlockModel;
import tuwien.dbai.wpps.core.fuzzy.IMuZeroDouble;
import tuwien.dbai.wpps.core.fuzzy.Nu;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.BlockQltRelationsLibSupport;

import com.google.inject.Inject;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 24, 2013 8:29:47 PM
 */
public class BasicSpatialRelationsEnricherBasedOnQntInfo extends AWPModelSystemEnricher {
	private static final Logger log = Logger.getLogger(BasicSpatialRelationsEnricherBasedOnQntInfo.class);

	private boolean symmetry;
	private EBlockQltRelationType[] relTypeArr;
	
//	private final OntModelAdp rdfModelQnt;
	private final OntModelAdp rdfModelQlt;
	private final IIEBasisAPI api;
	private final QntBlockImpl qntBlockImpl;
	
	private final IMuZeroDouble muPtMin;
	private final IMuZeroDouble muPtMax;
	private final IMuZeroDouble muPtCenter;
	private final Nu nu;
	
	@Inject
	public BasicSpatialRelationsEnricherBasedOnQntInfo(
//			@AnnotQntBlockModel final OntModelAdp rdfModelQnt
			@AnnotQltBlockModel final OntModelAdp rdfModelQlt
//			, @AnnotStructBlockGeomModel final OntModelAdp str
			, final IIEBasisAPI api
			, final QntBlockImpl qntBlockImpl
			, @AnnotQltBMLeftBorderMu final IMuZeroDouble muPtMin
			, @AnnotQltBMRightBorderMu final IMuZeroDouble muPtMax
			, @AnnotQltBMCenterMu final IMuZeroDouble muPtCenter
			, @AnnotQltBMBorderNu final Nu nu
			) {
//System.err.println("2: "+rdfModelQnt.equals(qlt)+" "+rdfModelQnt.equals(str));
//		this.rdfModelQnt = rdfModelQnt;
		this.rdfModelQlt = rdfModelQlt;
		this.api = api;
		this.qntBlockImpl = qntBlockImpl;
		
		this.muPtMin = muPtMin;
		this.muPtMax = muPtMax;
		this.muPtCenter = muPtCenter;
		this.nu = nu;
		
	}
	
	public void init(boolean symmetry, EBlockQltRelationType... relTypeArr) {
		super.init();
		this.symmetry = symmetry;
		this.relTypeArr = relTypeArr;
	}
	
	@Override
	protected void _enrich() {
		goThroughPairs(api.getObjects().getResultContent());
		if (rdfModelQlt.getTopRdfModel() instanceof InfModel) {
if (log.isDebugEnabled()) log.debug("START. Rebind "+rdfModelQlt);
			((InfModel)rdfModelQlt.getTopRdfModel()).rebind();
if (log.isDebugEnabled()) log.debug("FINISH. Rebind "+rdfModelQlt);
		}
	}
	
	private void goThroughPairs(List<IInstanceAdp> instAdpCol) {
		ListIterator<IInstanceAdp> iter1 = instAdpCol.listIterator();
		ListIterator<IInstanceAdp> iter2 = instAdpCol.listIterator();
		
		boolean straight = true;

//if (log.isTraceEnabled()) log.trace("_L1");
		
		while (iter1.hasNext()) {
			IInstanceAdp inst1 = iter1.next();
			
			while (_hasNext(iter2, straight)) {
				IInstanceAdp inst2 = _next(iter2, straight);
				if (!inst1.equals(inst2)) {
					if (inst1 instanceof IRdfResourceAdp && inst2 instanceof IRdfResourceAdp){
					setRelations(relTypeArr
							, symmetry
							, ((IRdfResourceAdp)inst1).getRdfResource()
							, ((IRdfResourceAdp)inst2).getRdfResource()
							, qntBlockImpl
							, muPtMin, muPtMax, muPtCenter, nu, rdfModelQlt.getBottomRdfModel());
					}
					
				}
				else if (!straight){
					_next(iter2, true);
					break;
				}
			}
			straight = !straight;
		}
		
//if (log.isTraceEnabled()) log.trace("_L2");
		
	}
	
	private boolean _hasNext(ListIterator<IInstanceAdp> iter, boolean straight) {
		return straight?iter.hasNext():iter.hasPrevious();
	}
	
	private IInstanceAdp _next(ListIterator<IInstanceAdp> iter, boolean straight) {
		return straight?iter.next():iter.previous();
	}
	
	private void setRelations(EBlockQltRelationType[] relTypeArr, boolean symmetry
			, Resource inst1, Resource inst2
			, QntBlockImpl qntBlockImpl, IMuZeroDouble muPtMin, IMuZeroDouble muPtMax, IMuZeroDouble muPtCenter, Nu nu
			, Model rdfModelQnt) {
		for (EBlockQltRelationType relType : relTypeArr) {
			Iterator<EBlockQltRelation> relIter = relType.getQltBlockRelationTypes().iterator();
			EBlockQltRelation rel = null;
			boolean found = false;
			while (!found && relIter.hasNext()) {
				rel = relIter.next();
				if (hasBasicRelation(inst1, inst2, rel, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)) {
					found = true;
					setRelation(inst1, inst2, rel, rdfModelQnt);
				}
			}
			if (symmetry && found) {
				if (hasBasicRelation(inst2, inst1, rel, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)) {
					setRelation(inst2, inst1, rel, rdfModelQnt);
				}
				else {
					relIter = relType.getQltBlockRelationTypes().iterator();
					while (relIter.hasNext()) {
						EBlockQltRelation rel2 = relIter.next();
						if (!rel2.equals(rel) && hasBasicRelation(inst2, inst1, rel2, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu)) {
							setRelation(inst2, inst1, rel2, rdfModelQnt);
							break;
						}
					}
				}
			}
			
		}
	}
	
	private boolean hasBasicRelation(Resource inst1, Resource inst2, EBlockQltRelation rel
			, QntBlockImpl qntBlockImpl, IMuZeroDouble muPtMin, IMuZeroDouble muPtMax, IMuZeroDouble muPtCenter, Nu nu) {
		if (!rel.isBasicRelation())
			return false;
		
		switch (rel) {
		
		case PO:
		case TPP:
		case NTPP:
		case EQUAL:
		case EC:
		case DC:
			
		case TPPi:
		case NTPPi:
			return hasBasicRCC8Relation(inst1, inst2, rel, qntBlockImpl, muPtMin, muPtMax, nu);
		
		case BEFORE_X:
		case TOUCHES_X:
		case OVERLAPS_X:
		case STARTS_X:
		case INSIDE_X:
		case FINISHES_X:
		case EQUALS_X:
			
		case AFTER_X:
		case TOUCHED_BY_X:
		case OVERLAPED_BY_X:
		case STARTED_BY_X:
		case CONTAINS_X:
		case FINISHED_BY_X:

		case BEFORE_Y:
		case TOUCHES_Y:
		case OVERLAPS_Y:
		case STARTS_Y:
		case INSIDE_Y:
		case FINISHES_Y:
		case EQUALS_Y:
			
		case AFTER_Y:
		case TOUCHED_BY_Y:
		case OVERLAPED_BY_Y:
		case STARTED_BY_Y:
		case CONTAINS_Y:
		case FINISHED_BY_Y:
			return hasBasicIntervalRelation2D(inst1, inst2, rel, qntBlockImpl, muPtMin, muPtMax, nu);
			
		case LEFT_ALIGNED_WITH:
		case RIGHT_ALIGNED_WITH:
		case CENTERED_HORIZONTALLY_WITH:
			
			
		case TOP_ALIGNED_WITH:
		case BOTTOM_ALIGNED_WITH:
		case CENTERED_VERTICALLY_WITH:
			
		case NO_HORIZONTALLY_ALIGNED_WITH:
		case NO_VERTICALLY_ALIGNED_WITH:
			return hasBasicPositiveAlignmentRelation(inst1, inst2, rel, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu);
			
		case NORTH_OF_P:
		case NORTH_EAST_OF_P:
		case EAST_OF_P:
		case SOUTH_EAST_OF_P:
		case SOUTH_OF_P:
		case SOUTH_WEST_OF_P:
		case WEST_OF_P:
		case NORTH_WEST_OF_P:
			return hasBasicPDirectionRelation(inst1, inst2, rel, qntBlockImpl, muPtMin, muPtMax, nu);
			
		case NORTH_OF_O:
		case NORTH_EAST_OF_O:
		case EAST_OF_O:
		case SOUTH_EAST_OF_O:
		case SOUTH_OF_O:
		case SOUTH_WEST_OF_O:
		case WEST_OF_O:
		case NORTH_WEST_OF_O:
			return hasBasicODirectionRelation(inst1, inst2, rel, qntBlockImpl, muPtMin, muPtMax, nu);
		}
		
throw new GeneralUncheckedException(log, "Unknown relation "+rel);
	}
	
	private boolean hasBasicIntervalRelation2D(Resource inst1, Resource inst2, EBlockQltRelation rel
			, QntBlockImpl qntBlockImpl, IMuZeroDouble muPtMin, IMuZeroDouble muPtMax, Nu nu) {
		return BlockQltRelationsLibSupport.compHasBasicIntervalRelation2DBasedOnQntFeatures
			(inst1, inst2, rel, qntBlockImpl, muPtMin, muPtMax, nu);
	}
	
	private boolean hasBasicPositiveAlignmentRelation(Resource inst1, Resource inst2, EBlockQltRelation rel
			, QntBlockImpl qntBlockImpl, IMuZeroDouble muPtMin, IMuZeroDouble muPtMax, IMuZeroDouble muPtCenter, Nu nu) {
		return BlockQltRelationsLibSupport.compHasBasicPositiveAlignmentRelationBasedOnQntFeatures
				(inst1, inst2, rel, qntBlockImpl, muPtMin, muPtMax, muPtCenter, nu);
	}
	
	private boolean hasBasicRCC8Relation(Resource inst1, Resource inst2, EBlockQltRelation rel
			, QntBlockImpl qntBlockImpl, IMuZeroDouble muPtMin, IMuZeroDouble muPtMax, Nu nu) {
		return BlockQltRelationsLibSupport.compHasBasicRCC8RelationBasedOnQntFeatures
				(inst1, inst2, rel, qntBlockImpl, muPtMin, muPtMax, nu);
	}
	
	private boolean hasBasicPDirectionRelation(Resource inst1, Resource inst2, EBlockQltRelation rel
			, QntBlockImpl qntBlockImpl, IMuZeroDouble muPtMin, IMuZeroDouble muPtMax, Nu nu) {
		return BlockQltRelationsLibSupport.compHasBasicPDirectionRelationBasedOnQntFeatures
				(inst1, inst2, rel, qntBlockImpl, muPtMin, muPtMax, nu);
	}
	
	private boolean hasBasicODirectionRelation(Resource inst1, Resource inst2, EBlockQltRelation rel
			, QntBlockImpl qntBlockImpl, IMuZeroDouble muPtMin, IMuZeroDouble muPtMax, Nu nu) {
		return BlockQltRelationsLibSupport.compHasBasicODirectionRelationBasedOnQntFeatures
				(inst1, inst2, rel, qntBlockImpl, muPtMin, muPtMax, nu);
	}
	
	private void setRelation(Resource inst1, Resource inst2, EBlockQltRelation rel, Model rdfModelQnt) {
		rdfModelQnt.add(inst1, rel.getProperty(), inst2);
	}
	
	
}
