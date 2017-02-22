/**
 * 
 */
package tuwien.dbai.wpps.core.ie.api.basis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.SFuzzyComparatorDouble;
import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IFunction2;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.callback.IPredicate2;
import tuwien.dbai.wpps.common.callback.IProcedure;
import tuwien.dbai.wpps.common.callback.IProcedure2;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.impllib.BasisIEUtilLib;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.ie.instadp.factory.ResultsObjectsFactory;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.ie.rdfimpl.IEBasisAPIImpl;
import tuwien.dbai.wpps.core.spatialindex.ISpatialIndexManager;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 14, 2012 2:15:03 AM
 */
@Singleton
public class RdfIEBasisAPI implements IIEBasisAPI {
	private static final Logger log = Logger.getLogger(RdfIEBasisAPI.class);

	private final ResultsObjectsFactory resultsObjectsFactory;
	private final RdfInstAdpFactoryWrap rdfInstAdpFactory;
	private final ISpatialIndexManager spatialIndex;
	private final IEBasisAPIImpl ieBasisAPIImpl;
	
	private final WPOntSubModels wpOntSubModels;
	
	@Inject
	public RdfIEBasisAPI(
			final ResultsObjectsFactory resultBlocksFactory
			, final RdfInstAdpFactoryWrap rdfInstAdpFactory
			, final ISpatialIndexManager spatialIndex
			, final IEBasisAPIImpl ieBasisAPIImpl
			, final WPOntSubModels wpOntSubModels) {
		this.resultsObjectsFactory = resultBlocksFactory;
		this.rdfInstAdpFactory = rdfInstAdpFactory;
		this.spatialIndex = spatialIndex;
		this.ieBasisAPIImpl = ieBasisAPIImpl;
		
		this.wpOntSubModels = wpOntSubModels;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IInstanceAdp> T getObjectByType(Class<T> clazz) {
		IResults res = this.getObjectsByType(clazz);
		
//if (log.isTraceEnabled()) log.trace("z1 "+clazz.getCanonicalName());
		
		 T rez = res.size()==0?null:res.iterator().next().as(clazz);

// if (log.isTraceEnabled()) log.trace("z2");
		 
		 return rez;
	}
	
	@Override
	public IResults getObjectsForQltBGMRelation(IInstanceAdp refObj,
			EBlockQltRelation rel) {
		return getObjectsForQltBGMRelation(null, refObj, rel);
	}
	
	@Override
	public IResults getForQltBGMRelation(EBlockQltRelation rel) {
		return getForQltBGMRelation(null, rel);
	}
	
	@Override
	public IResults getObjectsForQltBGMRelation(IPredicate<IInstanceAdp> p,
			IInstanceAdp refObj, EBlockQltRelation rel) {
		StmtIterator iter = wpOntSubModels.getOntAdapter(EWPOntSubModel.QLT_BLOCK_MODEL)
				.getTopRdfModel()
				.listStatements(((IRdfResourceAdp)refObj).getRdfResource(), rel.getProperty(), (RDFNode)null);
		final IResults rez = resultsObjectsFactory.createResultBlock();
		while (iter.hasNext()) {
			final IInstanceAdp inst = rdfInstAdpFactory.createAdp(
					iter.next().getObject().as(Resource.class)
					, IInstanceAdp.class);
			if (p == null || p.apply(inst)) {
				rez.addResult(inst);
			}
		}
		return rez;
	}
	
	@Override
	public IResults getSubjectsForQltBGMRelation(IInstanceAdp refObj,
			EBlockQltRelation rel) {
		return getSubjectsForQltBGMRelation(null, refObj, rel);
	}
	
	@Override
	public IResults getSubjectsForQltBGMRelation(IPredicate<IInstanceAdp> p,
			IInstanceAdp refObj, EBlockQltRelation rel) {

//if (log.isTraceEnabled()) {
//	final Model topM = wpOntSubModels.getOntAdapter(EWPOntSubModel.QLT_BLOCK_MODEL)
//			.getTopRdfModel();
//	final Model botM = wpOntSubModels.getOntAdapter(EWPOntSubModel.QLT_BLOCK_MODEL)
//			.getBottomRdfModel();
//	
//	log.trace(" BOTTOM P: "+botM
//			.listStatements(null, QltBlockModelOnt.P, (RDFNode)null).toList().size()
//	+ " TOP P: "+topM
//	.listStatements(null, QltBlockModelOnt.P, (RDFNode)null).toList().size()
//	+ " BOTTOM Pi: "+botM
//	.listStatements(null, QltBlockModelOnt.Pi, (RDFNode)null).toList().size()
//+ " TOP Pi: "+topM
//.listStatements(null, QltBlockModelOnt.Pi, (RDFNode)null).toList().size()
//	+ " equal1: "+ botM.equals(topM)
//	+ " equal2: "+ (botM == topM)
//	+" "+
//	topM.contains(null, OWL.inverseOf, (RDFNode)null)
//			);
////	final String rulFile = "file:///home/ruslan/workspaces/eclipseIndigo/testOntology/rulesTest/inverse.rul";
////	GenericRuleReasoner reasoner1 = new GenericRuleReasoner(Rule.rulesFromURL(rulFile));
////	reasoner1.setMode(GenericRuleReasoner.FORWARD_RETE);
////	final Model testM = ModelFactory.createInfModel(reasoner1, botM);
//	
////	((InfModel)topM).rebind();
//	final Model testM = topM;
//	
//	botM.add(botM.getResource("a"), QltBlockModelOnt.P, botM.getResource("b"));
//	
//	int i1 = testM.listStatements(new SimpleSelector(null, OWL.inverseOf, (RDFNode)null) {
//		@Override public boolean selects(Statement s) {
//			StmtIterator iter = testM.listStatements(null, s.getSubject().as(Property.class), (RDFNode)null);
//			while (iter.hasNext()) {
//				Statement st = iter.next();
//				if (!testM.contains(st.getObject().asResource(), s.getObject().as(Property.class), st.getSubject())) {
//					log.trace("Inversion 1 "+st.getObject().asResource().getLocalName()+" - "+s.getObject().as(Property.class).getLocalName()+" - "+st.getSubject().getLocalName()+" does no exist");
//					return true;
//				}
//			}
//			return false;
//		}
//	}).toList().size();
//	
//	log.trace("Inversion 1 size = "+i1);
//	
//	int i2 = testM.listStatements(new SimpleSelector(null, OWL.inverseOf, (RDFNode)null) {
//		@Override public boolean selects(Statement s) {
//			StmtIterator iter = testM.listStatements(null, s.getObject().as(Property.class), (RDFNode)null);
//			while (iter.hasNext()) {
//				Statement st = iter.next();
//				if (!testM.contains(st.getObject().asResource(), s.getSubject().as(Property.class), st.getSubject())) {
//					log.trace("Inversion 2 "+st.getObject().asResource().getLocalName()+" - "+s.getSubject().as(Property.class).getLocalName()+" - "+st.getSubject().getLocalName()+" does no exist");
//					return true;
//				}
//			}
//			return false;
//		}
//	}).toList().size();
//	
//	log.trace("Inversion 2 size = "+i2);
//	
//}


		StmtIterator iter = wpOntSubModels.getOntAdapter(EWPOntSubModel.QLT_BLOCK_MODEL)
				.getTopRdfModel()
				.listStatements(null, rel.getProperty(), ((IRdfResourceAdp)refObj).getRdfResource());
		final IResults rez = resultsObjectsFactory.createResultBlock();
		while (iter.hasNext()) {
			final IInstanceAdp inst = rdfInstAdpFactory.createAdp(
					iter.next().getSubject()
					, IInstanceAdp.class);
			if (p == null || p.apply(inst)) {
				rez.addResult(inst);
			}
		}
		return rez;
	}
	
	@Override
	public IResults getForQltBGMRelation(IPredicate2<IInstanceAdp> p,
			EBlockQltRelation rel) {
		StmtIterator iter = wpOntSubModels.getOntAdapter(EWPOntSubModel.QLT_BLOCK_MODEL)
				.getTopRdfModel()
				.listStatements(null, rel.getProperty(), (RDFNode)null);
		final IResults rez = resultsObjectsFactory.createResultBlock();
		while (iter.hasNext()) {
			Statement s = iter.next();
			final IInstanceAdp instSbj = rdfInstAdpFactory.createAdp(
					s.getSubject().as(Resource.class)
					, IInstanceAdp.class);
			final IInstanceAdp instObj = rdfInstAdpFactory.createAdp(
					s.getObject().as(Resource.class)
					, IInstanceAdp.class);
			if (p == null || p.apply(instSbj, instObj)) {
					IResults rez2 = resultsObjectsFactory.createResultBlock();
					rez2.addResult(instSbj);
					rez2.addResult(instObj);
					rez.addResult(rez2);
			}
		}
		return rez;
	}
	
	/**
	 * All object from the collection satisfying the predicate provided add into results.
	 *TODO: it will be good to check if it is possible to provided specified provider.
	 * @param reprView
	 * @param resCol
	 * @param p
	 * @return
	 */
	private <T extends IInstanceAdp> IResults _getObjectsByPredicateFromResourceColl(Class<T> reprView
			, final Collection<Resource> resCol, final IPredicate<? super T> p) {
		
//if (log.isTraceEnabled()) log.trace("y1");
		
		final IResults rez = resultsObjectsFactory.createResultBlock();
		for (final Resource res : resCol) {
			
//if (log.isTraceEnabled()) log.trace("y2");
			
			final T t = rdfInstAdpFactory.createAdp(res, reprView);
			
//if (log.isTraceEnabled()) log.trace("y3");
			
if (t == null) { System.err.println(t); log.warn("rdfInstAdpFactory.createAdp(res, reprView) returns NULL.");}
			if (p == null || p.apply(t)) {
//				System.err.println("::: "+t);
				rez.addResult(t);
			}
		}
		
//if (log.isTraceEnabled()) log.trace("y4");
		
		return rez;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IResults getObjects() {
		return getObjects((IPredicate)null);
	}

	//TODO: can be optimized
	@Override
	public IResults getObjects(IPredicate<IInstanceAdp> p) {
		final Set<Resource> resSet = ieBasisAPIImpl.getObjects();
		return _getObjectsByPredicateFromResourceColl(IInstanceAdp.class, resSet, p);
	}

	@Override
	public IResults getObjectsByType(Class<? extends IInstanceAdp>... viewArr) {
		return getObjectsByType2(IInstanceAdp.class, null, viewArr);
	}

	@Override
	public <T extends IInstanceAdp> IResults getObjectsByType2(
			Class<T> reprView, Class<? extends IInstanceAdp>... viewArr) {
		return getObjectsByType2(reprView, null, viewArr);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IInstanceAdp> IResults getObjectsByType2(
			Class<T> reprView, Collection<Class<? extends IInstanceAdp>> viewCol) {
		return getObjectsByType2(reprView, null, viewCol.toArray(new Class[viewCol.size()]));
	}

	@Override
	public IResults getObjectsByType(IPredicate<IInstanceAdp> p, Class<? extends IInstanceAdp>... viewArr) {
		return getObjectsByType2(IInstanceAdp.class, p, viewArr);
	}

	@Override
	public <T extends IInstanceAdp> IResults getObjectsByType2(
			Class<T> reprView, IPredicate<? super T> p, Class<? extends IInstanceAdp>... viewArr) {
		final Set<Resource> resList = ieBasisAPIImpl.getObjectsByType(viewArr);
		return _getObjectsByPredicateFromResourceColl(reprView, resList, p);
	}
	
	@Override
	public <T extends IInstanceAdp> IResults getObjectsByType2(
			Class<T> reprView, IPredicate<? super T> p, Collection<Class<? extends IInstanceAdp>> viewCol) {
		@SuppressWarnings("unchecked")
		final Set<Resource> resList = ieBasisAPIImpl.getObjectsByType(viewCol.toArray(new Class[viewCol.size()]));
		return _getObjectsByPredicateFromResourceColl(reprView, resList, p);
	}

	@Override
	public IResults toResults(Collection<? extends IInstanceAdp> col) {
		final IResults rez = resultsObjectsFactory.createResultBlock();
		rez.addResults(col);
		return rez;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IResults getObjectsContainedInArea(Rectangle2D area) {
		return BasisIEUtilLib.getBlocksContainedInArea(area, null, spatialIndex, resultsObjectsFactory, null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public IResults getObjectsContainedInArea(Rectangle2D area, IGenericIEFilter<IQntBlock> filter) {
		return BasisIEUtilLib.getBlocksContainedInArea(area, null, spatialIndex, resultsObjectsFactory, filter);
	}
	
	@Override
	public <T extends IInstanceAdp> IResults getObjectsContainedInArea(Rectangle2D area, Class<T>... viewArr) {
		return BasisIEUtilLib.getBlocksContainedInArea(area, null, spatialIndex, resultsObjectsFactory, null, viewArr);
	}
	
	@Override
	public <T extends IInstanceAdp, U extends IInstanceAdp> IResults getObjectsContainedInArea2(
			Class<T> reprView, Rectangle2D area, Class<U>... viewArr) {
		return BasisIEUtilLib.getBlocksContainedInArea(area, reprView, spatialIndex, resultsObjectsFactory, null, viewArr);
	}
	
	@Override
	public <T extends IInstanceAdp> IResults getObjectsContainedInArea(
			Rectangle2D area, IGenericIEFilter<IQntBlock> filter, Class<T>... viewArr) {
		return BasisIEUtilLib.getBlocksContainedInArea(area, null, spatialIndex, resultsObjectsFactory, filter, viewArr);
	}
	
	@Override
	public <T extends IInstanceAdp> IResults getObjectsContainedInArea2(
			Class<T> reprView, Rectangle2D area, IGenericIEFilter<IQntBlock> filter
			, Class<? extends IInstanceAdp>... viewArr) {
		return BasisIEUtilLib.getBlocksContainedInArea(area, reprView, spatialIndex, resultsObjectsFactory, filter, viewArr);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends IInstanceAdp> IResults getObjectsContainedInArea2(
			Class<T> reprView, Rectangle2D area, IGenericIEFilter<IQntBlock> filter
			, Collection<Class<? extends IInstanceAdp>> viewCol) {
		return BasisIEUtilLib.getBlocksContainedInArea(area, reprView, spatialIndex, resultsObjectsFactory, filter
				, viewCol.toArray(new Class[viewCol.size()]));
	}

	@Override
	public IResults getObjectsIntersectingArea(Rectangle2D area
			, IGenericIEFilter<IQntBlock> filter, Class<? extends IInstanceAdp>... viewArr) {
		return BasisIEUtilLib.getBlocksIntersectingArea(area, null, spatialIndex, resultsObjectsFactory, filter, viewArr);
	}

	@Override
	public IResults orderBy(IResults res, Comparator<IInstanceAdp>... comps) {
		return BasisIEUtilLib.orderBy(res, comps, resultsObjectsFactory);
	}

	@Override
	public IResults orderBy(IResults res,
			IFunction<IInstanceAdp, Comparable<?>>[] attrs, int[] orders) {
		Preconditions.checkArgument(attrs.length == orders.length);
		return BasisIEUtilLib.orderBy(res, attrs, orders, resultsObjectsFactory);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <U extends Comparable<?>> IResults orderBy(IResults res,
			IFunction<IInstanceAdp, U> attr, int order) {
		return orderBy(res, new IFunction[]{attr}, new int[]{order});
	}
	
	@Override
	public IResults orderBy(IResults res,
			List<IFunction<IInstanceAdp, Comparable<?>>> attrs,
			int... orders) {
		Preconditions.checkArgument(attrs.size() == orders.length);
		return BasisIEUtilLib.orderBy(res, attrs, orders, resultsObjectsFactory);
	}

	@Override
	public IResults intersection(IResults... resArr) {
		return BasisIEUtilLib.intersection(resArr, resultsObjectsFactory);
	}

	@Override
	public IResults union(IResults... resArr) {
		return BasisIEUtilLib.union(resArr, resultsObjectsFactory);
	}

	@Override
	public IResults union(Collection<IResults> resCol) {
		return BasisIEUtilLib.union(resCol.toArray(new IResults[resCol.size()]), resultsObjectsFactory);
	}

	@Override
	public IResults groupInSetsSimple(IResults res, IPredicate2<IInstanceAdp> p) {
		return BasisIEUtilLib.groupInSetsSimple(res, p, resultsObjectsFactory);
	}

	@Override
	public IResults groupInSeq(IResults res, IPredicate2<IInstanceAdp> p) {
		return BasisIEUtilLib.groupInSeqSimple(res, p, resultsObjectsFactory);
	}

	@Override
	public <U> IResults splitSeqSimpleSeqPairs(IResults res,
			IFunction2<IInstanceAdp, U> feature, IFunction2<U, Double> similar) {
		return BasisIEUtilLib.splitSeqSimpleSeqPairs2(res, feature, similar, resultsObjectsFactory);
	}

	@Override
	public IResults splitSeqSimpleSeqPairs(final IResults res,
			final IFunction2<IInstanceAdp, Double> feature
			, final double devProc, final double minD) {
		return splitSeqSimpleSeqPairs(
				res, feature, new IFunction2<Double, Double>() {
					@Override public Double apply(Double avar1, Double avar2) {
						if (SFuzzyComparatorDouble.equalProc(avar1, avar2, devProc, minD))
							return 0d;
						if (SFuzzyComparatorDouble.lessProc(avar1, avar2, devProc, minD))
							return -1d;
						return 1d;
			} } );
	}

	@Override
	public <U extends Comparable<?>> IResults groupInTreeSimple(IResults res,
			IPredicate2<IInstanceAdp> totalOrderPred,
			IFunction<IInstanceAdp, U>[] nodeTypeAttrs, int[] nodeTypesOrders) {
		return BasisIEUtilLib.groupInTreeSimple(res, totalOrderPred, nodeTypeAttrs, nodeTypesOrders, resultsObjectsFactory, true);
	}
	
	@Override
	public IResults groupInTreeSimple(IResults res,
			IFunction<IResults, IResults> totalSeqGrouping,
			Comparator<IInstanceAdp> nodeTypesComparator) {
		return BasisIEUtilLib.groupInTreeSimple(res, totalSeqGrouping, nodeTypesComparator, resultsObjectsFactory, true);
	}
	
	@Override
	public IResults unpack(IResults res) {
		return BasisIEUtilLib.unpack(res, resultsObjectsFactory);
	}

	@Override
	public IResults filter(IResults res, IPredicate<IInstanceAdp> p) {
		final IResults res2 = resultsObjectsFactory.createResultBlock();
		for (final IInstanceAdp t : res.getResultContent()) {
			if (p.apply(t))
				res2.addResult(t);
		}
		return res2;
	}

	@Override
	public void forEach(IResults res, IProcedure<IInstanceAdp> proc) {
		for (final IInstanceAdp t : res.getResultContent()) {
			proc.apply(t);
		}
	}

	@Override
	public <U, V> V aggr(IResults res, IFunction<IInstanceAdp, U> feature,
			IFunction<List<U>, V> aggrCalc) {
		final List<U> valsList = new ArrayList<U>(res.size());
		for (final IInstanceAdp t : res.getResultContent()) {
			valsList.add(feature.apply(t));
		}
		return aggrCalc.apply(valsList);
	}

	private static final IFunction<List<Double>, Double> calcAvg = new IFunction<List<Double>, Double>(){
		@Override public Double apply(List<Double> avar) {
			if (avar.size() == 0) return null;
			double rez = 0;
			for (final double d : avar) {
				rez += d;
			}
			return rez/avar.size();
	}};
	
	private static final IFunction<List<Double>, Double> calcMin = new IFunction<List<Double>, Double>(){
		@Override public Double apply(List<Double> avar) {
			Double rez = null;
			for (final Double d : avar) {
				if (rez == null)
					rez = d;
				else if (rez>d)
					rez = d;
			}
			return rez;
	}};
	
	private static final IFunction<List<Double>, Double> calcMax = new IFunction<List<Double>, Double>(){
		@Override public Double apply(List<Double> avar) {
			Double rez = null;
			for (final Double d : avar) {
				if (rez == null)
					rez = d;
				else if (rez<d)
					rez = d;
			}
			return rez;
	}};
	
	@Override
	public Double avg(IResults res, IFunction<IInstanceAdp, Double> feature) {
		return aggr(res, feature, calcAvg);
	}

	@Override
	public Double min(IResults res, IFunction<IInstanceAdp, Double> feature) {
		return aggr(res, feature, calcMin);
	}

	@Override
	public Double max(IResults res, IFunction<IInstanceAdp, Double> feature) {
		return aggr(res, feature, calcMax);
	}

	@Override
	public Double midrange(IResults res, IFunction<IInstanceAdp, Double> feature) {
		if (res.size()==0)
			return null;
		Double min = null;
		Double max = null;
		for (final IInstanceAdp t : res.getResultContent()) {
			final Double val = feature.apply(t);
			if (min == null || min>val) min = val;
			if (max == null || max<val) max = val;
		}
		return (min+max)/2;
	}

	@Override
	public Double variance(IResults res, IFunction<IInstanceAdp, Double> feature) {
		if (res.size() == 0)
			return null;
		if (res.size() == 1)
			return 0d;
		double xsum = 0;
		for (final IInstanceAdp t : res.getResultContent()) {
			xsum += Math.pow(feature.apply(t), 2);
		}
		final Double avg = avg(res, feature);
		return xsum/res.size() - Math.pow(avg, 2);
	}

	@Override
	public Double stdDev(IResults res, IFunction<IInstanceAdp, Double> feature) {
		final Double d = variance(res, feature);
		return (d == null)?null:Math.sqrt(d);
	}

	@Override
	public void forEachSeqPairs(IResults res, IProcedure2<IInstanceAdp> proc) {
		BasisIEUtilLib.forEachSeqPairs(res, proc);
	}

	@Override
	public <U, V> V aggrSeqPairs(final IResults res,
			final IFunction2<IInstanceAdp, U> feature, final IFunction<List<U>, V> val) {
		final List<U> valsList = new ArrayList<U>(res.size());
		BasisIEUtilLib.forEachSeqPairs(res, 
			new IProcedure2<IInstanceAdp>() {
				@Override public void apply(IInstanceAdp avar1, IInstanceAdp avar2) {
					valsList.add(feature.apply(avar1, avar2));
		} } );
		return val.apply(valsList);
	}

	@Override
	public Double avgSeqPairs(IResults res,
			IFunction2<IInstanceAdp, Double> feature) {
		return aggrSeqPairs(res, feature, calcAvg);
	}

	@Override
	public Double minSeqPairs(IResults res,
			IFunction2<IInstanceAdp, Double> feature) {
		return aggrSeqPairs(res, feature, calcMin);
	}

	@Override
	public Double maxSeqPairs(IResults res,
			IFunction2<IInstanceAdp, Double> feature) {
		return aggrSeqPairs(res, feature, calcMax);
	}

	@Override
	public Double midrangeSeqPairs(final IResults res,
			final IFunction2<IInstanceAdp, Double> feature) {
		final Double[] min = new Double[]{null};
		final Double[] max = new Double[]{null};
		BasisIEUtilLib.forEachSeqPairs(res, new IProcedure2<IInstanceAdp>() {
					@Override public void apply(IInstanceAdp avar1, IInstanceAdp avar2) {
						final Double val = feature.apply(avar1, avar2);
						if (min[0] == null || min[0]>val) min[0] = val;
						if (max[0] == null || max[0]<val) max[0] = val;
			} } );
		return (min[0] == null)?null:(min[0]+max[0])/2;
	}

	@Override
	public Double varianceSeqPairs(final IResults res,
			final IFunction2<IInstanceAdp, Double> feature) {
		if (res.size() < 2) return null;
		final double xsum[] = new double[]{0};
		final double sum[] = new double[]{0};
		BasisIEUtilLib.forEachSeqPairs(res, new IProcedure2<IInstanceAdp>() {
			@Override public void apply(IInstanceAdp avar1, IInstanceAdp avar2) {
				final double val = feature.apply(avar1, avar2);
				xsum[0] += Math.pow(val, 2);
				sum[0] += val;
		} } );
		return xsum[0]/res.size() - Math.pow(sum[0]/res.size(), 2);
	}

	@Override
	public Double stdDevSeqPairs(IResults res,
			IFunction2<IInstanceAdp, Double> feature) {
		final Double d = varianceSeqPairs(res, feature);
		return (d == null)?null:Math.sqrt(d);
	}

	@Override
	public boolean dumpModel(EWPOntSubModel model, String fileName) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. Dump model "+model);
		final OntModelAdp ma =  wpOntSubModels.getOntAdapter(model);
		File f = new File(fileName);
		if (ma == null) {
log.warn("There is no model "+model+" to dump");
			return false;
		}
		try {
//			FileOutputStream ofs = new FileOutputStream(new File("/home/ruslan/tmp/dumps/dump"+fileName+".rdf"));
			FileOutputStream ofs = new FileOutputStream(f);
			ma.getTopRdfModel().write(ofs, "RDF/XML-ABBREV");
if (log.isTraceEnabled()) log.trace("Model "+model+ " was succesfully dumped into "+f.getCanonicalPath());
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
try {
	log.warn("Model "+model+ "was not succesfully dumped into "+f.getCanonicalPath());
} catch (IOException e) {
	e.printStackTrace();
}
		return false;
	}

}
