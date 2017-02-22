/**
 * 
 */
package tuwien.dbai.wpps.core.ie.impllib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IFunction2;
import tuwien.dbai.wpps.common.callback.IPredicate2;
import tuwien.dbai.wpps.common.callback.IProcedure2;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.instadp.factory.ResultsObjectsFactory;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.spatialindex.ISpatialIndexManager;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 14, 2012 1:17:53 AM
 */
public final class BasisIEUtilLib {
	
	/**
	 * @param rect
	 * @param reprView can be null.
	 * @param spatialIndex
	 * @param resultsObjectFactory
	 * @param filter can be null.
	 * @param viewArr can be of 0-size
	 * @return
	 */
	public static final <T extends IInstanceAdp> IResults getBlocksContainedInArea(final Rectangle2D rect
			, final Class<T> reprView
			, final ISpatialIndexManager spatialIndex, final ResultsObjectsFactory resultsObjectFactory
			, IGenericIEFilter<IQntBlock> filter
			, final Class<? extends IInstanceAdp>... viewArr) {
		filter = _crFilter(filter, viewArr);
		List<? extends IInstanceAdp> QntBlockCol;
		if (reprView == null)
			QntBlockCol = spatialIndex.getContainments(rect, filter);
		else
			QntBlockCol = spatialIndex.getContainments(rect, filter, reprView);
		final IResults resultBlock = resultsObjectFactory.createResultBlock();
		resultBlock.addResults(QntBlockCol);
		return resultBlock;
	}
	
	/**
	 * @param rect
	 * @param reprView can be null.
	 * @param spatialIndex
	 * @param resultsObjectFactory
	 * @param filter can be null.
	 * @param viewArr can be of 0-size
	 * @return
	 */
	public static final <T extends IInstanceAdp> IResults getBlocksIntersectingArea(final Rectangle2D rect
			, final Class<T> reprView
			, final ISpatialIndexManager spatialIndex, final ResultsObjectsFactory resultsObjectFactory
			, IGenericIEFilter<IQntBlock> filter
			, final Class<? extends IInstanceAdp>... viewArr) {
		filter = _crFilter(filter, viewArr);
		List<? extends IInstanceAdp> QntBlockCol;
		if (reprView == null)
			QntBlockCol = spatialIndex.getIntersections(rect, filter);
		else
			QntBlockCol = spatialIndex.getIntersections(rect, filter, reprView);
		
		final IResults resultBlock = resultsObjectFactory.createResultBlock();
		resultBlock.addResults(QntBlockCol);
		return resultBlock;
	}
	
	private static final <U extends IInstanceAdp> IGenericIEFilter<IQntBlock>
		_crFilter(final IGenericIEFilter<IQntBlock> filter
			, final Class<? extends IInstanceAdp>... viewArr) {
		IGenericIEFilter<IQntBlock> filter2 = filter;
		if (viewArr != null && viewArr.length>0)
			filter2 = new IGenericIEFilter<IQntBlock>() {
				@Override public EFilterResult apply(IQntBlock avar) {
					EFilterResult fr = EFilterResult.ACCEPT;
					if (filter != null) {
						fr = filter.apply(avar);
						switch (fr) {
						case REJECT:
						case REJECT_STOP:
							return fr;
						}
					}
					for (Class<? extends IInstanceAdp> v : viewArr)
						if (avar.canAs(v)) return fr;
					return EFilterResult.REJECT;
					
				} };
		return filter2;
	}
	
//	public static final <T extends IInstanceAdp> IResults getBlocksIntersectingArea(final Rectangle rect
//			, final IGenericIEFilter<IQntBlock> filter, final ISpatialIndexManager spatialIndex, final ResultsObjectsFactory resultsObjectFactory
//			, final Class<T> reprView) {
//		final SpatialIndexQueryResult<T> QntBlockCol = spatialIndex.getIntersections(rect, filter, reprView);
//		final IResults resultBlock = resultsObjectFactory.createResultBlock();
//		final Iterator<T> iter = QntBlockCol.iterator();
//		while (iter.hasNext()) {
//			resultBlock.addResult(iter.next().as(reprView));
//		}
//		return resultBlock;
//	}
	
	public static final IResults orderBy(final IResults res
			, final Comparator<IInstanceAdp>[] comp
			, final ResultsObjectsFactory resultsObjectFactory) {
//		Collections.sort(Lists.newArrayList(res.getResultContent()), ComparatorUtils.chainedComparator(comp));
		final Ordering<IInstanceAdp> o = Ordering.compound(Arrays.asList(comp));
		final IResults res2 = resultsObjectFactory.createResultBlock();
		res2.addResults(o.sortedCopy(res.getResultContent()));
		return res2;
	}
	
	public static final IResults orderBy(final IResults res
			, final Comparator<IInstanceAdp> comp
			, final ResultsObjectsFactory resultsObjectFactory) {
		final Ordering<IInstanceAdp> o = Ordering.from(comp);
		return orderBy(res, o, resultsObjectFactory);
	}
	
	public static final IResults orderBy(final IResults res
			, final Ordering<IInstanceAdp> comp
			, final ResultsObjectsFactory resultsObjectFactory) {
//		Collections.sort(Lists.newArrayList(res.getResultContent()), ComparatorUtils.chainedComparator(comp));
//		final Ordering<T> o = Ordering.compound(Arrays.asList(comp));
		final IResults res2 = resultsObjectFactory.createResultBlock();
		res2.addResults(comp.sortedCopy(res.getResultContent()));
		return res2;
	}
	
	/**
	 * Order results by their attributes {@code attrs} and {@code orders}.
	 * @param res
	 * @param attrs
	 * @param order <0 - descending order, >=0 - ascending order 
	 * @param resultsObjectFactory
	 * @return
	 */
	public static final IResults orderBy(final IResults res,
			final IFunction<IInstanceAdp, Comparable<?>>[] attrs, final int[] orders
			, final ResultsObjectsFactory resultsObjectFactory) {
		final Ordering<IInstanceAdp> o = _createOrderingObjFromAttrArr(attrs, orders);
		final IResults res2 = resultsObjectFactory.createResultBlock();
		res2.addResults(o.sortedCopy(res.getResultContent()));
		return res2;
	}
	
	public static final IResults orderBy(final IResults res,
			final List<IFunction<IInstanceAdp, Comparable<?>>> attrs, final int[] orders
			, final ResultsObjectsFactory resultsObjectFactory) {
		@SuppressWarnings("unchecked")
		final Ordering<IInstanceAdp> o = _createOrderingObjFromAttrArr(
				attrs.toArray(new IFunction[attrs.size()])
				, orders);
		final IResults res2 = resultsObjectFactory.createResultBlock();
		res2.addResults(o.sortedCopy(res.getResultContent()));
		return res2;
	}
	
	private static final Ordering<IInstanceAdp>
		_createOrderingObjFromAttrArr(final IFunction<IInstanceAdp, ? extends Comparable<?>>[] attrs, final int[] orders) {
		Preconditions.checkArgument(attrs.length == orders.length);
		final List<Ordering<IInstanceAdp>> compList = new ArrayList<Ordering<IInstanceAdp>>(attrs.length);
		for (int i=0; i<attrs.length; i++) {
			final int j = i;
			compList.add( (orders[j]>=0)?
					Ordering.natural().nullsFirst().onResultOf(new Function<IInstanceAdp, Comparable<?>>() {
						@Override public Comparable<?> apply(IInstanceAdp arg0) {
							return attrs[j].apply(arg0);
						}
					} )
					: Ordering.natural().nullsFirst().onResultOf(new Function<IInstanceAdp, Comparable<?>>() {
						@Override public Comparable<?> apply(IInstanceAdp arg0) {
							return attrs[j].apply(arg0);
						} 
					} ).reverse()
			);
		}
		final Ordering<IInstanceAdp> o = Ordering.compound(compList);
		return o;
	}
	
	public static final <T extends IInstanceAdp> IResults intersection(
			IResults[] resArr
//			, final Class<T> reprView
			, final ResultsObjectsFactory resultsObjectFactory) {
		final IResults resultBlock = resultsObjectFactory.createResultBlock();
		
		final List<Set<IInstanceAdp>> resArrList = new ArrayList<Set<IInstanceAdp>>(resArr.length);
		int IndWithMinSize = -1;
		int minSize = -1;
		for (int i=0; i<resArr.length; i++) {
			resArrList.add(new LinkedHashSet<IInstanceAdp>(resArr[i].getResultContent()));
			if (IndWithMinSize == -1 || minSize > resArrList.get(i).size()) {
				IndWithMinSize = i;
				minSize = resArrList.get(i).size();
			}
		}
		
		final Set<IInstanceAdp> a = resArrList.get(IndWithMinSize);
		final Iterator<IInstanceAdp> iter = a.iterator();
		while (iter.hasNext()) {
			final IInstanceAdp s = iter.next();
			boolean in = true;
			for (int j=0; in && j<resArrList.size(); j++) {
				if (j != IndWithMinSize) {
					in = resArrList.get(j).contains(s);
				}
			}
			if (in)
				resultBlock.addResult(s);
		}
		return resultBlock;
	}
	
	public static final <T extends IInstanceAdp> IResults union(
			IResults[] resArr
//			, final Class<T> reprView
			,final ResultsObjectsFactory resultsObjectFactory) {
		final IResults resultBlock = resultsObjectFactory.createResultBlock();
		final Set<IInstanceAdp> rez = new LinkedHashSet<IInstanceAdp>();
		for (int i=0; i<resArr.length; i++) {
			final Iterator<? extends IInstanceAdp> iter = resArr[i].getResultContent().iterator();
			while (iter.hasNext()) {
				rez.add(iter.next());
			}
		}
		resultBlock.addResults(rez);
		return resultBlock;
	}
	
	public static final IResults groupInSetsSimple(
			final IResults res
			, final IPredicate2<IInstanceAdp> p
			, final ResultsObjectsFactory resultsObjectFactory) {
		final Map<IInstanceAdp, IResults> groupMap
			= new HashMap<IInstanceAdp, IResults>(100);
		final Iterator<IInstanceAdp> titer = res.getResultContent().iterator();
		while (titer.hasNext()) {
			final IInstanceAdp tval = titer.next();
			boolean eqFound = false;
			final Iterator<Entry<IInstanceAdp, IResults>> titer2 = groupMap.entrySet().iterator();
			while(!eqFound && titer2.hasNext()) {
				final Entry<IInstanceAdp, IResults> e = titer2.next();
				if (p.apply(tval, e.getKey())) {
					e.getValue().addResult(tval);
					eqFound = true;
				}
			}
			if (!eqFound) {
				final IResults resCont
					= resultsObjectFactory.createResultBlock();
				resCont.addResult(tval);
				groupMap.put(tval, resCont);
			}
		}
		
		final IResults resultBlock
			= resultsObjectFactory.createResultBlock();
		final Iterator<Entry<IInstanceAdp, IResults>> grMapIter = groupMap.entrySet().iterator();
		while (grMapIter.hasNext()) {
			resultBlock.addResult(grMapIter.next().getValue());
		}
		return resultBlock;
	}

	
	
	
	public static final IResults groupInSeqSimple(
			final IResults res
			, final IPredicate2<IInstanceAdp> p
			, final ResultsObjectsFactory resultsObjectFactory) {
		final List<LinkedList<IInstanceAdp>> sequenceLists = new LinkedList<LinkedList<IInstanceAdp>>();
		
		// create sequences
		final Iterator<IInstanceAdp> titer = res.getResultContent().iterator();
		while (titer.hasNext()) {
			final IInstanceAdp tval = titer.next();
			boolean eqFound = false;
			final Iterator<LinkedList<IInstanceAdp>> seqIter = sequenceLists.iterator();
			while(!eqFound && seqIter.hasNext()) {
				final LinkedList<IInstanceAdp> l = seqIter.next();
				if (p.apply(l.getLast(), tval)) {
					l.addLast(tval);
					eqFound = true;
				}
				else if (p.apply(tval, l.getFirst())) {
					l.addFirst(tval);
					eqFound = true;
				}
			}
			if (!eqFound) {
				final LinkedList<IInstanceAdp> l2 = new LinkedList<IInstanceAdp>();
				l2.add(tval);
				sequenceLists.add(l2);
			}
		}
		
		final IResults resultBlock
			= resultsObjectFactory.createResultBlock();
		
		// merge sequences
		final Iterator<LinkedList<IInstanceAdp>> seqIter3 = sequenceLists.iterator();
		while(seqIter3.hasNext()) {
			final LinkedList<IInstanceAdp> l3 = seqIter3.next();
			final ListIterator<LinkedList<IInstanceAdp>> seqIter4 = sequenceLists.listIterator();
			if (seqIter4.hasNext()) seqIter4.next(); // go to the second element
			boolean eqFound2 = false;
			while(!eqFound2 && seqIter4.hasNext()) {
				final LinkedList<IInstanceAdp> l4 = seqIter4.next();
				if (p.apply(l4.getLast(), l3.getFirst())) {
					l4.addAll(l3);
					eqFound2 = true;
				}
				else if (p.apply(l3.getLast(), l4.getFirst())) {
					l3.addAll(l4);
					seqIter4.set(l3);
					eqFound2 = true;
				}
			}
			if (!eqFound2) {
				final IResults resSeq
					= resultsObjectFactory.createResultBlock();
				resSeq.addResults(l3);
				resultBlock.addResult(resSeq);
			}
			
			seqIter3.remove();
		}
		
		return resultBlock;
	}
	
	public static final <T extends IInstanceAdp> IResults unpack(
			final IResults res
//			, final Class<T> reprView
			, final ResultsObjectsFactory resultsObjectFactory) {
		final IResults resultBlock = resultsObjectFactory.createResultBlock();
		Iterator<IInstanceAdp> iter = res.getResultContent().iterator();
		
		while (iter.hasNext()) {
			final IInstanceAdp val = iter.next();
			if (val.canAs(IResults.class)) {
				final Iterator<IInstanceAdp> iter2
					= val.as(IResults.class).getResultContent().iterator();
				while (iter2.hasNext()) {
					resultBlock.addResult(iter2.next());
				}
			}
			else
				resultBlock.addResult(val);
		}
		return resultBlock;
	}
	
	public static final void forEachSeqPairs(final IResults res,
			final IProcedure2<IInstanceAdp> proc) {
		if (res.size()<2) return;
		final Iterator<IInstanceAdp> iter = res.iterator();
		IInstanceAdp t1 = iter.next();
		while (iter.hasNext()) {
			final IInstanceAdp t2 = iter.next();
			proc.apply(t1, t2);
			t1 = t2;
		}
	}
	
	/**
	 * Split results.
	 */
	public static final <U> IResults splitSeqSimpleSeqPairs1(
			final IResults res, final IFunction2<IInstanceAdp, U> feature, final IFunction2<U, Integer> similar
			, final ResultsObjectsFactory resultsObjectFactory) {
		final IResults splitRes = resultsObjectFactory.createResultBlock();
		if (res.size()<2) {
			splitRes.addResult(res);
			return splitRes;
		}
		final int[] sim = new int[]{0};
		final IInstanceAdp[] lastvar = new IInstanceAdp[]{null};
		final IResults[] lastSplitedRes = new IResults[]{null};
		final IProcedure2<IInstanceAdp> proc = new IProcedure2<IInstanceAdp>() {
			private U u1 = null;
			@Override public void apply(IInstanceAdp avar1, IInstanceAdp avar2) {
				lastvar[0] = avar2;
				if (u1 == null) {
					u1 = feature.apply(avar1, avar2);
					lastSplitedRes[0] = resultsObjectFactory.createResultBlock();
					splitRes.addResult(lastSplitedRes[0]);
					lastSplitedRes[0].addResult(avar1);
				} else {
					final U u2 = feature.apply(avar1, avar2);
					sim[0] = similar.apply(u1, u2);
					if (sim[0] == 0) {
						lastSplitedRes[0].addResult(avar1);
					} else if (sim[0] < 0) {
						lastSplitedRes[0].addResult(avar1);
						lastSplitedRes[0] = resultsObjectFactory.createResultBlock();
						splitRes.addResult(lastSplitedRes[0]);
						u1 = u2;
					} else if (sim[0] > 0) {
						if (lastSplitedRes[0].size()>0) {
							lastSplitedRes[0] = resultsObjectFactory.createResultBlock();
							splitRes.addResult(lastSplitedRes[0]);
						}
						lastSplitedRes[0].addResult(avar1);
						u1 = u2;
					}
				}
			} };
		forEachSeqPairs(res, proc);
		lastSplitedRes[0].addResult(lastvar[0]);
		return splitRes;
	}
	
	/**
	 * Split results, algorithm tries to avoud of creating groups of size 1.
	 */
	public static final <U> IResults splitSeqSimpleSeqPairs2(
			final IResults res, final IFunction2<IInstanceAdp, U> feature, final IFunction2<U, Double> similar
			, final ResultsObjectsFactory resultsObjectFactory) {
		final IResults splitRes = resultsObjectFactory.createResultBlock();
		if (res.size()<2) {
			splitRes.addResult(res);
			return splitRes;
		}
		final double[] sim = new double[]{0};
		final IInstanceAdp[] lastvar = new IInstanceAdp[]{null};
		final IResults[] lastSplitedRes = new IResults[]{null};
		final IProcedure2<IInstanceAdp> proc = new IProcedure2<IInstanceAdp>() {
			private U u1 = null;
			@Override public void apply(IInstanceAdp avar1, IInstanceAdp avar2) {
				lastvar[0] = avar2;
				if (u1 == null) {
					u1 = feature.apply(avar1, avar2);
					lastSplitedRes[0] = resultsObjectFactory.createResultBlock();
					splitRes.addResult(lastSplitedRes[0]);
					lastSplitedRes[0].addResult(avar1);
				} else {
					final U u2 = feature.apply(avar1, avar2);
					sim[0] = similar.apply(u1, u2);
					if (sim[0] == 0) {
						lastSplitedRes[0].addResult(avar1);
					} else if (sim[0] < 0) {
						lastSplitedRes[0].addResult(avar1);
						if (lastSplitedRes[0].size() >1) {
							lastSplitedRes[0] = resultsObjectFactory.createResultBlock();
							splitRes.addResult(lastSplitedRes[0]);
						}
						u1 = u2;
					} else if (sim[0] > 0) {
						if (lastSplitedRes[0].size()>0) {
							lastSplitedRes[0] = resultsObjectFactory.createResultBlock();
							splitRes.addResult(lastSplitedRes[0]);
						}
						lastSplitedRes[0].addResult(avar1);
						u1 = u2;
					}
				}
			} };
		forEachSeqPairs(res, proc);
		lastSplitedRes[0].addResult(lastvar[0]);
		return splitRes;
	}
	
	// === Tree ===
	
	public static final  <U extends Comparable<?>> IResults groupInTreeSimple(
			final IResults res
			, final IPredicate2<IInstanceAdp> totalOrderPred // grouping in seqs
			, final IFunction<IInstanceAdp, U>[] nodeTypeAttrs, int[] nodeTypesOrders
			, final ResultsObjectsFactory resultsObjectFactory
			, boolean mainElOnTheTop
			) {
		return groupInTreeSimple(
				res
				, new IFunction<IResults, IResults>() {
					@Override public IResults apply(IResults avar) {
						return groupInSeqSimple(res, totalOrderPred, resultsObjectFactory);
				} }
				, _createOrderingObjFromAttrArr(nodeTypeAttrs, nodeTypesOrders)
				, resultsObjectFactory
				, mainElOnTheTop
				);
	}
	
	
//	, boolean mainElOnTheTop
	public static final IResults groupInTreeSimple(
			final IResults res
			, IFunction<IResults, IResults> totalSeqGrouping
			, Comparator<IInstanceAdp> nodeTypesComparator
			, final ResultsObjectsFactory resultsObjectFactory
			, boolean mainElOnTheTop
			) {
		IResults rez = resultsObjectFactory.createResultBlock();
		// find all sequences with total order defined
		IResults totalSeqs = totalSeqGrouping.apply(res);
		for (IInstanceAdp tmpInst : totalSeqs) {
			IResults tmpRes = tmpInst.as(IResults.class);
			Preconditions.checkState(tmpRes.size()>0);
			Iterator<IInstanceAdp> iter = tmpRes.iterator();
			IInstanceAdp el = iter.next();
			if (mainElOnTheTop) {
				// get the max type
				IInstanceAdp topType = orderBy(tmpRes, nodeTypesComparator, resultsObjectFactory).get(0);
				// pass first elements which are lower than top type
				boolean found = false;
				while (!found && iter.hasNext()) {
					el = iter.next();
					final int comp = nodeTypesComparator.compare(topType, el);
					if (comp<=0) {
						Preconditions.checkState(comp==0);
						found = true;
					}
				}
				Preconditions.checkState(found);
			}
			final IResults currLevel = resultsObjectFactory.createResultBlock();
			rez.addResult(currLevel);
			IInstanceAdp lastElement = _groupInTreeSimpleRecursion(currLevel, el, iter, nodeTypesComparator, resultsObjectFactory, 0);
			Preconditions.checkState(!iter.hasNext());
			Preconditions.checkNotNull(lastElement);
		}
		return rez;
	}
	
	private static final IInstanceAdp _groupInTreeSimpleRecursion(
			final IResults currResultLevel
			, final IInstanceAdp currInstance
			, final Iterator<IInstanceAdp> iter
			, Comparator<IInstanceAdp> nodeTypesComparator
			, final ResultsObjectsFactory resultsObjectFactory
			, final int depth
			) {
		IResults currResEl = resultsObjectFactory.createResultBlock();
		currResultLevel.addResult(currResEl);
		currResEl.addResult(currInstance);
		IInstanceAdp tmpInst = currInstance;
		while (iter.hasNext()) {
			tmpInst = iter.next();
			int cmp = nodeTypesComparator.compare(currInstance, tmpInst);
			if (cmp>0) {
				IResults childResultLevel = resultsObjectFactory.createResultBlock();
				currResEl.addResult(childResultLevel);
				tmpInst = _groupInTreeSimpleRecursion(childResultLevel, tmpInst, iter, nodeTypesComparator, resultsObjectFactory, depth+1);
				cmp = nodeTypesComparator.compare(currInstance, tmpInst);
				Preconditions.checkState(cmp<=0);
			}
			if (cmp == 0 || cmp<0 && depth==0 ) {
				currResEl = resultsObjectFactory.createResultBlock();
				currResultLevel.addResult(currResEl);
				currResEl.addResult(tmpInst);
			}
		}
		return tmpInst;
		
	}
	
	
}
