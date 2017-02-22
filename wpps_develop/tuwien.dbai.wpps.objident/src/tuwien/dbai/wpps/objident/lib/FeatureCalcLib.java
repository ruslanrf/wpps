/**
 * 
 */
package tuwien.dbai.wpps.objident.lib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IFunction2;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 4, 2012 2:59:19 PM
 */
public class FeatureCalcLib {
	
	/**
	 * Aggregation which consider all unique pairs of collection col.
	 * @param col collection of elements.
	 * @param f1 function which process every pair.
	 * @param f2 function which aggregate all computed data using function f1.
	 */
	public static final <T11, T12, T22> T22 aggrForEachUniquePair(final Collection<T11> col
			, final IFunction2<T11, T12> f1, IFunction<Collection<T12>, T22> f2) {
		final List<T11> arr = new ArrayList<T11>(col.size());
		arr.addAll(col);
		final Collection<T12> c1 = new ArrayList<T12>(col.size()*100); // TODO can be optimized
		for (int i=0; i<arr.size()-2; i++) {
			for (int j=i+1; j<arr.size()-1; j++) {
				c1.add(f1.apply(arr.get(i), arr.get(j)));
			}
		}
		return f2.apply(c1);
	}
	
	/**
	 * Aggregation over all collection col.
	 * @param col collection of elements to be aggregated.
	 * @param f1 function to process every element.
	 * @param f2 function to aggregate collection.
	 * @return
	 */
	public static final <T11, T12, T22> T22 aggrForEachElement(final Collection<T11> col
			, final IFunction<T11, T12> f1, IFunction<Collection<T12>, T22> f2) {
		final List<T12> arr = new ArrayList<T12>(col.size());
		Iterator<T11> iter = col.iterator();
		while (iter.hasNext())
			arr.add(f1.apply(iter.next()));
		return f2.apply(arr);
	}
	
//	public final static String REG_EX_FOR_GAPS = "(\\s|&nbsp;|\u0020|\u00A0|\u200B|\u3000)+";
	
//	public final static String REG_EX_FOR_GAP = "\\s|&nbsp;|\u0020|\u00A0|\u200B|\u3000";
	
	public static boolean oneOf(IInstanceAdp target, EIMInstType[] objectsToBeConsidered) {
		for (int i=0; i<objectsToBeConsidered.length; i++) {
			if (target.canAs(objectsToBeConsidered[i].getJavaInterface()))
				return true;
		}
		return false;
	}
	
	public static IInstanceAdp toOneOf(IInstanceAdp target, EIMInstType[] objectsToBeConsidered) {
		for (int i=0; i<objectsToBeConsidered.length; i++) {
			if (target.canAs(objectsToBeConsidered[i].getJavaInterface()))
				return target.as(objectsToBeConsidered[i].getJavaInterface());
		}
		return null;
	}
	
//	@Deprecated
//	public static String trim(String str) {
////		return str.replaceAll("(\n|\r|\t|\\s)+", " ").trim();
//		return str.replaceAll("(\\s)+", " ").trim();
//	}
	
	public static final String normalizeGaps(String str) {
		str = str.replaceAll("(&nbsp;)+", " ");
		str = str.replaceAll("\u0020+", " ");
		str = str.replaceAll("\u00A0+", " ");
		str = str.replaceAll("\u200B+", " ");
		str = str.replaceAll("\u3000+", " ").trim();
		str = str.replaceAll("\\s+", " ").trim();
		return str;
	}
	
	
}
