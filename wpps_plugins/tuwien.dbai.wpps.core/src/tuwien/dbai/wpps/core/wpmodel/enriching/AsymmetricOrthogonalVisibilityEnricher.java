/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.enriching;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.geometry.Interval1D;
import tuwien.dbai.wpps.common.geometry.Interval1DUtils;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QltBlockImpl;

import com.google.inject.Inject;
import com.hp.hpl.jena.rdf.model.Resource;

// TODO: Rename relations from passive to active
/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 3, 2012 4:06:40 PM
 */
public class AsymmetricOrthogonalVisibilityEnricher extends AWPModelSystemEnricher {
	private static final Logger log = Logger.getLogger(AsymmetricOrthogonalVisibilityEnricher.class);

	private IPredicate<IInstanceAdp> p = null;
	private Set<EBlockQltRelation> typSet = new LinkedHashSet<EBlockQltRelation>(4);
	
	private IIEBasisAPI api = null;
	private QltBlockImpl qltBlockImpl = null;
	
	@Inject
	public AsymmetricOrthogonalVisibilityEnricher(final IIEBasisAPI api
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
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"AsymmetricOrthogonalVisibilityEnricher. Initialization.");
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
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"AsymmetricOrthogonalVisibilityEnricher. Initialization.");
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
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"AsymmetricOrthogonalVisibilityEnricher. Initialization.");
		initWithClasses(clazzArr);
		postInit(typArr);
	}
	
	@SuppressWarnings("unchecked")
	public void init(final Collection<Class<? extends IInstanceAdp>> clazzCol
			, final EBlockQltRelation... typArr) {
		super.init();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"AsymmetricOrthogonalVisibilityEnricher. Initialization.");
		initWithClasses(clazzCol.toArray(new Class[clazzCol.size()]));
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
			if (typArr[i].getRelationType() != EBlockQltRelationType.OrthogonallyVisibleBlock)
				throw new GeneralUncheckedException(log, "Relation "+typArr+" should belongs to the category "
						+ EBlockQltRelationType.OrthogonallyVisibleBlock);
		}
		Collections.addAll(this.typSet, typArr);
	}
	
	@Override
	protected void _enrich() {
		@SuppressWarnings("unchecked")
		Iterator<? extends IInstanceAdp> qntBlksIter = api.getObjectsByType
				(IQntBlock.class).getResultContent().iterator();
		while (qntBlksIter.hasNext()) {
			final IInstanceAdp instAdp = qntBlksIter.next();
			final IQntBlock qntBlock = instAdp.as(IQntBlock.class);
			if (p == null || p.apply(qntBlock))
				enrichForQntBlock(qntBlock, p, typSet);
		}
		
	}
	
	private IFunction<IInstanceAdp, Double> getXMin = new IFunction<IInstanceAdp, Double>() {
		@Override public Double apply(IInstanceAdp avar) {
			return avar.as(IQntBlock.class).getXMin();
	} };
		
	private IFunction<IInstanceAdp, Double> getXMax = new IFunction<IInstanceAdp, Double>() {
		@Override public Double apply(IInstanceAdp avar) {
			return avar.as(IQntBlock.class).getXMax();
	} };
		
	private IFunction<IInstanceAdp, Double> getYMin = new IFunction<IInstanceAdp, Double>() {
		@Override public Double apply(IInstanceAdp avar) {
			return avar.as(IQntBlock.class).getYMin();
	} };
			
	private IFunction<IInstanceAdp, Double> getYMax = new IFunction<IInstanceAdp, Double>() {
		@Override public Double apply(IInstanceAdp avar) {
			return avar.as(IQntBlock.class).getYMax();
	} };
	
	private void enrichForQntBlock(final IQntBlock qntBlock, final IPredicate<IInstanceAdp> p
			, final Set<EBlockQltRelation> typSet) {
		if (typSet.contains(EBlockQltRelation.NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF))
			process(qntBlock, EBlockQltRelation.NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF, qntBlock.getSouthArea()
					, getXMin, getXMax, getYMin 
			, 1, p);
		if (typSet.contains(EBlockQltRelation.EAST_ORTHOGONAL_VISIBLE_BLOCK_OF))
			process(qntBlock, EBlockQltRelation.EAST_ORTHOGONAL_VISIBLE_BLOCK_OF, qntBlock.getWestArea()
					, getYMin, getYMax, getXMax 
			, -1, p);
		if (typSet.contains(EBlockQltRelation.SOUTH_ORTHOGONAL_VISIBLE_BLOCK_OF))
			process(qntBlock, EBlockQltRelation.SOUTH_ORTHOGONAL_VISIBLE_BLOCK_OF, qntBlock.getNorthArea()
					, getXMin, getXMax, getYMax
			, -1, p);
		if (typSet.contains(EBlockQltRelation.WEST_ORTHOGONAL_VISIBLE_BLOCK_OF))
			process(qntBlock, EBlockQltRelation.WEST_ORTHOGONAL_VISIBLE_BLOCK_OF, qntBlock.getEastArea()
					, getYMin, getYMax, getXMin 
			, 1, p);
	}
	
	/**
	 * @param qntBlock
	 * @param rel
	 * @param area
	 * @param minF
	 * @param maxF
	 * @param borderF
	 * @param minmax >=0 - the bigger borderF the closer object to qntBlock; <0 - vice versa.
	 * @param p
	 */
	@SuppressWarnings("unchecked")
	void process(final IQntBlock qntBlock, EBlockQltRelation rel, Rectangle2D area
			, IFunction<IInstanceAdp, Double> minF, IFunction<IInstanceAdp, Double> maxF
			, IFunction<IInstanceAdp, Double> borderF
			, final int minmax
			, final IPredicate<IInstanceAdp> p ) {
		IResults res = api.getObjectsIntersectingArea(area
				, new IGenericIEFilter<IQntBlock>() {
					@Override public EFilterResult apply(IQntBlock avar) {
						return ( (p == null || p.apply(avar))
						&&  qntBlock.as(IQltBlock.class).hasRelation(avar, EBlockQltRelation.DR) )?
								EFilterResult.ACCEPT:EFilterResult.REJECT;
		} }
		, IQntBlock.class);
		res = api.orderBy(res, borderF, minmax);
		List<Interval1D> interList = new ArrayList<Interval1D>(1);
		Interval1D inter = new Interval1D(minF.apply(qntBlock), maxF.apply(qntBlock));
		interList.add(inter);
		Interval1D veryLeft = inter;
		Interval1D veryRight = inter;
		for (IInstanceAdp avar: res.getResultContent()) {
			Interval1D inter2 = new Interval1D(minF.apply(
					avar.as(IQntBlock.class)), maxF.apply(avar.as(IQntBlock.class)));
			final List<Interval1D> interSetTmp = new ArrayList<Interval1D>(interList.size()+1);
			for (Interval1D inter1 : interList) {
				if (Interval1DUtils.overlap(inter2, inter1)) {
					List<Interval1D> interCut = Interval1DUtils.cut(inter2, inter1);
					interSetTmp.addAll(interCut);
					
					if (
							(veryLeft == inter1 && veryRight == inter1)
							// left point
							|| (veryLeft == inter1 && inter2.max<=inter1.max)
							|| Interval1DUtils.contains(inter1, inter2)
							// right point
							|| (veryRight == inter1 && inter2.min>=inter1.min)
							)
						if (qntBlock instanceof IRdfResourceAdp && avar instanceof IRdfResourceAdp)
							addRelation(((IRdfResourceAdp)qntBlock).getRdfResource()
									, ((IRdfResourceAdp)avar).getRdfResource(), rel);
					if (veryLeft == inter1) {
						if (interCut.size()==0 || veryLeft.min != interCut.get(0).min)
							veryLeft = null;
						else veryLeft = interCut.get(0);
					}
					if (veryRight == inter1) {
						if (interCut.size()==0 || veryRight.max != interCut.get(interCut.size()-1).max)
							veryRight = null;
						else veryRight = interCut.get(interCut.size()-1);
					}
				}
				else
					interSetTmp.add(inter1);
			}
			interList = interSetTmp;
		}
	}
	
	private final void addRelation(Resource primQltBlock, Resource refQltBlock, EBlockQltRelation relType) {
		qltBlockImpl.addBasicOrthogonalVisibilityRelation(primQltBlock, refQltBlock, relType);
	}
	

}
