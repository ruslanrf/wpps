/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Average relative weighted color distance. Weight is based on the size of objects.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 4, 2012 2:34:38 PM
 */
@Singleton
public class AvgWeightedFGColorROCFDesc extends AFeatureDescription {

	public AvgWeightedFGColorROCFDesc() {
		super("b5d443c8-c5d4-11e1-bf8c-00247e160239"
				, "Average wighted foreground color distance"
				, "AvgWeightedFGColorROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT
				, Double.class);
	}

}
