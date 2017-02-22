/**
 * 
 */
package tuwien.dbai.wpps.core.ie.api.basis;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IFunction2;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.callback.IPredicate2;
import tuwien.dbai.wpps.common.callback.IProcedure;
import tuwien.dbai.wpps.common.callback.IProcedure2;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;

/**
 * TODO: everywhere U extends IInstanceAdp to ? extends IInstanceAdp.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 14, 2012 12:54:01 AM
 */
public interface IIEBasisAPI {

	// =========================
	// Get objects from the Ontology
	// =========================
		
		<T extends IInstanceAdp> T getObjectByType(Class<T> clazz);
		
		IResults getObjectsForQltBGMRelation(IInstanceAdp refObj, EBlockQltRelation rel);
		
		IResults getSubjectsForQltBGMRelation(IInstanceAdp refObj, EBlockQltRelation rel);
		
		IResults getForQltBGMRelation(EBlockQltRelation rel);
		
		IResults getObjectsForQltBGMRelation(IPredicate<IInstanceAdp> p, IInstanceAdp refObj, EBlockQltRelation rel);
		
		IResults getSubjectsForQltBGMRelation(IPredicate<IInstanceAdp> p, IInstanceAdp refObj, EBlockQltRelation rel);
		
		IResults getForQltBGMRelation(IPredicate2<IInstanceAdp> p, EBlockQltRelation rel);
		
		/** Get all individuals from the ontologies. All individuals will be wrapperd by the adapter {@linkplain IInstanceAdp}.
		 * @return Result with all objects from the ontologies.
		 */
		IResults getObjects();
		
		/**
		 * Get all individuals from the ontologies according to the predicate defined.
		 * All individuals will be wrapperd by the adapter {@linkplain IInstanceAdp}.
		 * @param p predicate.
		 * @return Result with objects.
		 */
		IResults getObjects(IPredicate<IInstanceAdp> p);
		
		/**
		 * TODO: +fetobjectbytypes
		 * TODO: If it is 1 element in the array, convert all objects gotten into it...
		 * Get all individuals from the ontologies according to the classes specified.
		 * All individuals will be wrapped by the adapter {@linkplain IInstanceAdp}.
		 * @param view
		 * @return
		 */
		@SafeVarargs
		IResults getObjectsByType(Class<? extends IInstanceAdp>... viewArr);
		
		/**
		 * Get all individuals from the ontologies according to the classes specified.
		 * @param reprView type which will be used to represent collection of objects in the results. It is an efficient to have type the same as needed in predicate.
		 * @param viewArr types of the objects to select.
		 * @return Result which contains list of objects specified from the ontology.
		 */
		<T extends IInstanceAdp> IResults getObjectsByType2(Class<T> reprView, Class<? extends IInstanceAdp>... viewArr);
		
		<T extends IInstanceAdp> IResults getObjectsByType2(Class<T> reprView, Collection<Class<? extends IInstanceAdp>> viewCol);
		
		/**
		 * Get objects from the ontologies by their type and according to the predicate defined.
		 * @param view types of the objects to select.
		 * @param p predicate.
		 * @return
		 */
		@SafeVarargs
//		<T extends IInstanceAdp> IResults getObjectsByType(IPredicate<IInstanceAdp> p, Class<T>... viewArr);
		IResults getObjectsByType(IPredicate<IInstanceAdp> p, Class<? extends IInstanceAdp>... viewArr);
		
		/**
		 * Get objects from the ontologies by their types and according to the predicate defined.
		 * @param reprView type which will be used to represent collection of objects in the results. It is an efficient to have type the same as needed in predicate.
		 * @param p predicate.
		 * @param viewArr types of the objects to select.
		 * @return
		 */
		@SafeVarargs
		<T extends IInstanceAdp> IResults getObjectsByType2(Class<T> reprView
				, IPredicate<? super T> p, Class<? extends IInstanceAdp>... viewArr);
		
		<T extends IInstanceAdp> IResults getObjectsByType2(Class<T> reprView
				, IPredicate<? super T> p, Collection<Class<? extends IInstanceAdp>> viewCol);
		
		// =========================
		// Converting to results
		// =========================
		
		/**
		 * Convert collection to {@linkplain IGenericResults}.
		 * @param col
		 * @param reprView current type of the collection.
		 * @return results' object which contains all elements from the collection.
		 */
		IResults toResults(Collection<? extends IInstanceAdp> col);
		
		// ====================
		// Containment in area
		// ====================
		
		// TODO: add function "for all objects in area"
		
		IResults getObjectsContainedInArea(Rectangle2D area);
		
		IResults getObjectsContainedInArea(Rectangle2D area, IGenericIEFilter<IQntBlock> filter);
		
		<T extends IInstanceAdp> IResults getObjectsContainedInArea(Rectangle2D area, Class<T>... view);

		<T extends IInstanceAdp, U extends IInstanceAdp> IResults getObjectsContainedInArea2(
				Class<T> reprView, Rectangle2D area, Class<U>... viewArr);
		
		/**
		 * Get objects from the area specified.
		 * @param area specified rectangular area.
		 * @param filter filter for the objects to be extracted.
		 * @param reprView type to which all extracted objects will be converted.
		 * @return result with objects extracted.
		 */
		<T extends IInstanceAdp> IResults getObjectsContainedInArea(
				Rectangle2D area, IGenericIEFilter<IQntBlock> filter, Class<T>... viewArr);
		
		/**
		 * @param reprView all extracted objects will be converted to this type.
		 * @param area
		 * @param filter Can be {@code null}.
		 * @param viewArr only objects of these types will be considered. Can be {@code null}.
		 * @return
		 */
		<T extends IInstanceAdp> IResults getObjectsContainedInArea2(
				Class<T> reprView, Rectangle2D area, IGenericIEFilter<IQntBlock> filter, Class<? extends IInstanceAdp>... viewArr);
		
		<T extends IInstanceAdp> IResults getObjectsContainedInArea2(
				Class<T> reprView, Rectangle2D area, IGenericIEFilter<IQntBlock> filter, Collection<Class<? extends IInstanceAdp>> viewArr);
		
		/**
		 * Get objects from intersecting the area specified.
		 * @param area specified rectangular area.
		 * @param filter filter for the objects to be extracted. (can be null)
		 * @param viewArr types of objects to be considered. Can be {@code null}.
		 * @return result with objects extracted.
		 */
		@SafeVarargs
		IResults getObjectsIntersectingArea(Rectangle2D area
				, IGenericIEFilter<IQntBlock> filter, Class<? extends IInstanceAdp>... viewArr);
		
		// =============
		// Ordering
		// =============
		
		/**
		 * Order results by the comparators provided in ascending order.
		 * @param res results
		 * @param comps comparators
		 * @return Ortered results.
		 */
		IResults orderBy(IResults res, Comparator<IInstanceAdp>... comps);
		
		/**
		 * @param res
		 * @param attr attribute which is used for sorting.
		 * @param order order &lt;0 - descending order, &gt;=0 - ascending order.
		 * @return
		 */
		<U extends Comparable<?>> IResults orderBy(IResults res, IFunction<IInstanceAdp, U> attr, int order);
		
		/**
		 * @param res
		 * @param attrs Attribute which are used for sorting.
		 * @param orders Array. Order <0 - descending order, >=0 - ascending order.
		 * @return
		 */
		IResults orderBy(IResults res, IFunction<IInstanceAdp, Comparable<?>>[] attrs, int[] orders);
		
		IResults orderBy(IResults res, List<IFunction<IInstanceAdp, Comparable<?>>> attrs, int... orders);
		
		
		// =================================
		// Binary operations with results
		// =================================

		/**
		 * Intersection of the results. A nesting of results is not considered. 
		 * @param reprView type into which results will be casted.
		 * @param resArr enumeration of results.
		 * @return intersection of results.
		 */
		IResults intersection(IResults... resArr);
//		<T extends IInstanceAdp> IResults intersection(Class<T> reprView, IResults... resArr);
		
		// TODO: add intersection with collection of results.
		
		/**
		 * Union of the results. A nesting of results is not considered.
		 * @param view type into which results will be casted.
		 * @param resArr enumeration of results.
		 * @return union of results.
		 */
		IResults union(IResults... resArr);
//		<T extends IInstanceAdp> IResults union(Class<T> view, IResults... resArr);
		
		/**
		 * Union of the results. A nesting of results is not considered.
		 * @param view type into which results will be casted.
		 * @param resCol collection of results.
		 * @return union of results.
		 */
		IResults union(Collection<IResults> resCol);
//		<T extends IInstanceAdp> IResults union(Class<T> view, Collection<IResults> resCol);
		
		// ===================
		// Grouping results
		// ====================
		
		/**
		 * Group results in sets where sets are formed based on the predicate provided.
		 * For every set we use the first element added which is compared with new element.
		 * New element can be added if the predicate is true, otherwise new set is created.
		 * @param res results
		 * @param p predicate to compare 2 values.
		 * @return results of grouped results.
		 */
		IResults groupInSetsSimple(IResults res, IPredicate2<IInstanceAdp> p);
		
//		<T extends IInstanceAdp> IResults<IResults<T>> groupInSetsSimple(
//				IResults<T> res
//				, Predicate p);
		
		/**
		 * Groups results in sequences. Every new element can be append at the beginning or at the end of the sequence.
		 * If there is no such a sequence, we create a new one.
		 * @param res results.
		 * @param p predicate to compare 2 values.
		 * @return results of grouped results.
		 */
		IResults groupInSeq(IResults res, IPredicate2<IInstanceAdp> p);
		
//		<T extends IInstanceAdp> IResults<IResults<T>> groupInSeqSimple(
//				IResults<T> res
//				, Predicate p);

		/**
		 * Results' collection is considered as sequence. Going from the first pair of values to the last one,
		 * compare features that correspond to two pairs of results following one after another.
		 * If results are similar ({@code similar = 0}) first parts of couples correspond to the same group,
		 * if {@code similar < 0}, pairs are not similar, first part is added to the same group as the first part
		 * of previous pair, new empty group is created;
		 * if {@code similar > 0}, pairs are not similar, new empty group is created, first part is added to
		 * the new group. Last result is added to the last created group.
		 * As a result of this function, implementation should try to avoid creation of
		 * empty groups or groups with 1 element.
		 * @param res results
		 * @param feature between two elements
		 * @param similar similarity measure. 0 -- sequential pair of neighbouring elements is equal,
		 * >0 -- elements are not equal, first element belongs to next group,
		 * <0 -- elements are not equal, first elements belongs to the current group.
		 * @return split groups.
		 */
		<U> IResults splitSeqSimpleSeqPairs(IResults res, IFunction2<IInstanceAdp, U> feature, IFunction2<U, Double> similar);
		
		/**
		 * The same implementation as in
		 * {@linkplain IGenericIEBasisAPI#splitSeqSimpleSeqPairs(IGenericResults, IFunction2, IFunction2)}.
		 * @param res results.
		 * @param feature feature between pair of results to be  considered.
		 * @param devProc deviation in procent, within the scope of which features are treated as equal.
		 * @param minD minimal quantitative deviation, within the scope of which features are treated as equal.
		 * @return split groups
		 */
		IResults splitSeqSimpleSeqPairs(IResults res, IFunction2<IInstanceAdp, Double> feature, double devProc, double minD);
		
		// === Tree ===

		<U extends Comparable<?>> IResults groupInTreeSimple(IResults res, IPredicate2<IInstanceAdp> totalOrderPred
				, IFunction<IInstanceAdp, U>[] nodeTypeAttrs, int[] nodeTypesOrders);

		IResults groupInTreeSimple(IResults res, IFunction<IResults, IResults> totalSeqGrouping
				, Comparator<IInstanceAdp> nodeTypesComparator);
		
		// =================================
		// Ungrouping results
		// =================================
		
		/**
		 * "Unpack results". If results' container {@code A} contains a result {@code B}
		 * which is a results' container, containing set of results {@code V},
		 * then after applying this function results {@code V} will be a results of results' container {@code A}.
		 * @param res results' container.
		 * @param view type in which all results will be casted.
		 * @return unpacked results.
		 */
		IResults unpack(IResults res);
//		<T extends IInstanceAdp> IResults unpack(IResults res, Class<T> view);
		
		// ===========================================
		// Additional functions. Analytical functions
		// ===========================================
		
		/**
		 * Filter results using predicate provided.
		 * @param res Results' container.
		 * @param p predicate.
		 * @return Results filtered.
		 */
		IResults filter(IResults res, IPredicate<IInstanceAdp> p);
		
		/**
		 * Do a procedure specified for every result.
		 * @param res results,
		 * @param proc procedure
		 */
		void forEach(IResults res, IProcedure<IInstanceAdp> proc);
		
		/**
		 * Analytic function.
		 * Aggregation of all results for computing a value.
		 * @param res results
		 * @param feature Feature to be analyzed.
		 * @param aggrCalc Function for computing aggregated value for list of features provided.
		 * @return value representing aggregated results.
		 */
		<U, V> V aggr(IResults res, IFunction<IInstanceAdp, U> feature, IFunction<List<U>, V> aggrCalc);

		/**
		 * Compute mean (arithmetic average value).
		 */
		Double avg(IResults res, IFunction<IInstanceAdp, Double> feature);// mean
		/**
		 * Compute minimum.
		 */
		Double min(IResults res, IFunction<IInstanceAdp, Double> feature);
		/**
		 * Compute maximum.
		 */
		Double max(IResults res, IFunction<IInstanceAdp, Double> feature);
		
		//TODO: add mostfrequentvalue function
		
		/**
		 * Compute midrage.
		 */
		Double midrange(IResults res, IFunction<IInstanceAdp, Double> feature);
		/** Compute variance.
		 */
		Double variance(IResults res, IFunction<IInstanceAdp, Double> feature);
		/**
		 * Compute standard deviation.
		 */
		Double stdDev(IResults res, IFunction<IInstanceAdp, Double> feature);
		
		/**
		 * Invoke a procedure specified for every successive pairs of results following one after another.
		 */
		void forEachSeqPairs(IResults res, IProcedure2<IInstanceAdp> proc);
		/**
		 * Aggregation based on the features of successive pairs of results following one after another.
		 */
		<U, V> V aggrSeqPairs(IResults res, IFunction2<IInstanceAdp, U> feature, IFunction<List<U>, V> val);
		
		/**
		 * Mean for the features of successive pairs of results following one after another.
		 */
		Double avgSeqPairs(IResults res, IFunction2<IInstanceAdp, Double> feature);
		/**
		 * Minimum for the features of successive pairs of results following one after another.
		 */
		Double minSeqPairs(IResults res, IFunction2<IInstanceAdp, Double> feature);
		/**
		 * Maximum for the features of successive pairs of results following one after another.
		 */
		Double maxSeqPairs(IResults res, IFunction2<IInstanceAdp, Double> feature);
		
		/**
		 * Midrange for the features of successive pairs of results following one after another.
		 */
		Double midrangeSeqPairs(IResults res, IFunction2<IInstanceAdp, Double> feature);
		/**
		 * Variance for the features of successive pairs of results following one after another.
		 */
		Double varianceSeqPairs(IResults res, IFunction2<IInstanceAdp, Double> feature);
		/**
		 * Standard deviation for the features of successive pairs of results following one after another.
		 */
		Double stdDevSeqPairs(IResults res, IFunction2<IInstanceAdp, Double> feature);
		
		// Entropy
		
		// Dump
		boolean dumpModel(EWPOntSubModel model, String fileName);
		
}
