/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Average relative weighted background color distance. Weight is based on the size of objects.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#CONTEXT}. </li>
 * <ul>
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 11:10:58 AM
 */
@Singleton
public class AvgWeightedBGColorROCFDesc extends AFeatureDescription {

	public AvgWeightedBGColorROCFDesc() {
		super("bab78eee-055e-11e2-9fb7-00247e160239"
				, "Average wighted background color distance"
				, "AvgWeightedBGColorROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT
				, Double.class);
	}
	
}
