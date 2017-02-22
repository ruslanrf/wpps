/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.enriching;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QltBlockImpl;

import com.google.inject.Inject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 23, 2012 2:14:15 PM
 */
//TODO: make it depricated
public final class AsymmetricNeighborhoodEnricher extends AWPModelSystemEnricher {
	private static final Logger log = Logger.getLogger(AsymmetricNeighborhoodEnricher.class);

	private IPredicate<IInstanceAdp> p = null;
	private Set<EBlockQltRelation> typSet = new LinkedHashSet<EBlockQltRelation>(4);
	
	private IIEBasisAPI api = null;
	private QltBlockImpl qltBlockImpl = null;
	
	@Inject
	public AsymmetricNeighborhoodEnricher(final IIEBasisAPI api
			, final QltBlockImpl qltBlockImpl) {
		super();
		this.api = api;
		this.qltBlockImpl = qltBlockImpl;
	}
	
	/**
	 * @param p predicate for the elements to be accepted for setting relations specified. Can be {@code null}.
	 * @param typArr relations to be set.
	 */
	public void init(final IPredicate<IInstanceAdp> p
			, final EBlockQltRelation... typArr) {
		super.init();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"AsymmetricNeighborhoodEnricher. Initialization.");
		this.p = p;
		postInit(typArr);
	}
	
	/**
	 * @param clazz class to be considered.
	 * @param typArr relations to be set.
	 */
	@SuppressWarnings("unchecked")
	public void init(final Class<? extends IInstanceAdp> clazz
			, final EBlockQltRelation... typArr) {
		super.init();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"AsymmetricNeighborhoodEnricher. Initialization.");
		initWithClasses(new Class[]{clazz});
		postInit(typArr);
	}
	
	/**
	 * @param clazzArr classes to be considered.
	 * @param typArr relations to be set.
	 */
	public void init(final Class<? extends IInstanceAdp>[] clazzArr
			, final EBlockQltRelation... typArr) {
		super.init();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"AsymmetricNeighborhoodEnricher. Initialization.");
		initWithClasses(clazzArr);
		postInit(typArr);
	}
	
	private final void initWithClasses(final Class<? extends IInstanceAdp>[] clazzArr) {
		this.p = new IPredicate<IInstanceAdp>() {
			@Override
			public Boolean apply(IInstanceAdp avar) {
				for (int i=0; i<clazzArr.length; i++) {
					if (avar.canAs(clazzArr[i]))
						return true;
				}
				return false;
			}
		};
	}
	
	private final void postInit(final EBlockQltRelation... typArr) {
		for (int i=0; i<typArr.length; i++) {
			if (typArr[i].getRelationType() != EBlockQltRelationType.NeighboringBlock)
				throw new GeneralUncheckedException(log, "Relation "+typArr+" should belongs to the category "
						+ EBlockQltRelationType.NeighboringBlock);
		}
		Collections.addAll(this.typSet, typArr);
	}
	
//	public void init(final Collection<? extends IInstanceAdp> coll
//			, final EQltBlockRelationType... typArr) {
//		super.init();
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"AsymmetricNeighborhoodEnricher. Initialization.");
//		this.coll = coll;
//		for (int i=0; i<typArr.length; i++) {
//			if (typArr[i].getRelationCathegory() != EQltBlockRelationCathegory.NeighboringBlock)
//				throw new GeneralUncheckedException(log, "Relation "+typArr+" should belongs to the category "
//						+ EQltBlockRelationCathegory.NeighboringBlock);
//		}
//		Collections.addAll(this.typSet, typArr);
//	}
	
	@Override
	protected void _enrich() {
		@SuppressWarnings("unchecked")
		Iterator<? extends IInstanceAdp> qntBlksIter = api.getObjectsByType
				(IQntBlock.class).getResultContent().iterator();
		while (qntBlksIter.hasNext()) {
			final IInstanceAdp instAdp = qntBlksIter.next();
			final IQntBlock qntBlock = instAdp.as(IQntBlock.class);
			if (p == null || p.apply(qntBlock))
				enrichForQntBlock(qntBlock, p, typSet, qltBlockImpl);
		}
		
	}
	
	private IFunction<IQntBlock, Double> getXMin = new IFunction<IQntBlock, Double>() {
		@Override
		public Double apply(IQntBlock avar) {
			return avar.getXMin();
			}
		};
		
	private IFunction<IQntBlock, Double> getXMax = new IFunction<IQntBlock, Double>() {
		@Override
		public Double apply(IQntBlock avar) {
			return avar.getXMax();
			}
		};
		
	private IFunction<IQntBlock, Double> getYMin = new IFunction<IQntBlock, Double>() {
		@Override
		public Double apply(IQntBlock avar) {
			return avar.getYMin();
			}
		};
			
	private IFunction<IQntBlock, Double> getYMax = new IFunction<IQntBlock, Double>() {
		@Override
		public Double apply(IQntBlock avar) {
			return avar.getYMax();
			}
		};
	
	private void enrichForQntBlock(final IQntBlock qntBlock, final IPredicate<IInstanceAdp> p
			, final Set<EBlockQltRelation> typSet, final QltBlockImpl qltBlockImpl) {
		IQntBlock btm = null;
		IQntBlock left = null;
		IQntBlock top = null;
		IQntBlock right = null;
		
		if (typSet.contains(EBlockQltRelation.NEAREST_NORTH_NEIGHBORING_BLOCK_OF))
			btm = getNeighboringBlock(qntBlock, qntBlock.getSouthArea()
					, getYMin, getXMin
			, 0, p, qltBlockImpl);
		
		if (typSet.contains(EBlockQltRelation.NEAREST_EAST_NEIGHBORING_BLOCK_OF))
			left = getNeighboringBlock(qntBlock, qntBlock.getWestArea()
					, getXMax, getYMin
			, 1, p, qltBlockImpl);
		
		if (typSet.contains(EBlockQltRelation.NEAREST_SOUTH_NEIGHBORING_BLOCK_OF))
			top = getNeighboringBlock(qntBlock, qntBlock.getNorthArea()
					, getYMax, getXMin
			, 1, p, qltBlockImpl);
		
		if (typSet.contains(EBlockQltRelation.NEAREST_WEST_NEIGHBORING_BLOCK_OF))
			right = getNeighboringBlock(qntBlock, qntBlock.getEastArea()
					, getXMin, getYMin
			, 0, p, qltBlockImpl);
		
		if (btm != null)
			qltBlockImpl.addBasicOrIncompleteCompositeNeighboringRelation(((IRdfResourceAdp)qntBlock).getRdfResource()
					, ((IRdfResourceAdp)btm).getRdfResource()
					, EBlockQltRelation.NEAREST_NORTH_NEIGHBORING_BLOCK_OF);
		if (left != null)
			qltBlockImpl.addBasicOrIncompleteCompositeNeighboringRelation(((IRdfResourceAdp)qntBlock).getRdfResource()
					, ((IRdfResourceAdp)left).getRdfResource()
					, EBlockQltRelation.NEAREST_EAST_NEIGHBORING_BLOCK_OF);
		if (top != null)
			qltBlockImpl.addBasicOrIncompleteCompositeNeighboringRelation(((IRdfResourceAdp)qntBlock).getRdfResource()
					, ((IRdfResourceAdp)top).getRdfResource()
					, EBlockQltRelation.NEAREST_SOUTH_NEIGHBORING_BLOCK_OF);
		if (right != null)
			qltBlockImpl.addBasicOrIncompleteCompositeNeighboringRelation(((IRdfResourceAdp)qntBlock).getRdfResource()
					, ((IRdfResourceAdp)right).getRdfResource()
					, EBlockQltRelation.NEAREST_WEST_NEIGHBORING_BLOCK_OF);
		
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)
		+ "Enrichment for rdf instance: "+((IRdfResourceAdp)qntBlock).getRdfResource()
		+ " Top: "+ ((btm==null)?"":((IRdfResourceAdp)btm).getRdfResource())
		+ " Right: "+ ((left==null)?"":((IRdfResourceAdp)left).getRdfResource())
		+ " Btm: "+ ((top==null)?"":((IRdfResourceAdp)top).getRdfResource())
		+ " Left: "+ ((right==null)?"":((IRdfResourceAdp)right).getRdfResource()));
		
	}
	
	/**
	 * @param PrimQntBlock primary block
	 * @param area Area of elements where we should find a proper block.
	 * @param f1 attribure from the first dimention
	 * @param f2 attribure from the second dimention
	 * @param minmax1
	 * @param p predicate for acceptance
	 * @param qltBlockImpl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private IQntBlock getNeighboringBlock(final IQntBlock PrimQntBlock
			, final Rectangle2D area
			, final IFunction<IQntBlock, Double> f1
			, final IFunction<IQntBlock, Double> f2
			, final int minmax1
			, final IPredicate<IInstanceAdp> p
			 , final QltBlockImpl qltBlockImpl) {
		
		final Double[] minmaxArr1 = new Double[]{null};
		final Double[] minmaxArr2 = new Double[]{null};
		final IQntBlock[] instAdpArr = new IQntBlock[]{null};
		
		api.getObjectsIntersectingArea ( area
				, new IGenericIEFilter<IQntBlock>() {
					@Override
					public tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter.EFilterResult apply(
							IQntBlock avar) {
						if ( (p == null || p.apply(avar))
								&& qltBlockImpl.hasCompositeRCC8Relation(
										((IRdfResourceAdp)PrimQntBlock).getRdfResource()
										, ((IRdfResourceAdp)avar).getRdfResource()
										, EBlockQltRelation.DR)
							) {
							final double minmaxTmp1 = f1.apply(avar);
							final double minmaxTmp2 = f2.apply(avar);
							if ( minmaxArr1[0] == null
									||
										minmax1 == 0
											&& (minmaxArr1[0]>minmaxTmp1
													|| minmaxArr1[0]==minmaxTmp1
														&& minmaxArr2[0]>minmaxTmp2
													)
									|| minmax1 == 1
											&& (minmaxArr1[0]<minmaxTmp1
													|| minmaxArr1[0]==minmaxTmp1
														&& minmaxArr2[0]>minmaxTmp2
													)
								) {
								minmaxArr1[0] = minmaxTmp1;
								minmaxArr2[0] = minmaxTmp2;
								instAdpArr[0] = avar;
							}
						}
						return EFilterResult.REJECT;
					}
		}
				, IQntBlock.class );
		return instAdpArr[0];
	}
	

}
