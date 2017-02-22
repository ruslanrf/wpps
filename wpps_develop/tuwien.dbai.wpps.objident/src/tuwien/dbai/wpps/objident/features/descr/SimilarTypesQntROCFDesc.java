/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 4, 2012 6:57:24 PM
 */
@Singleton
public class SimilarTypesQntROCFDesc extends AFeatureDescription {

	public SimilarTypesQntROCFDesc() {
		super("23321370-26a9-11e2-aa9a-00247e160239"
				, "Number of objects of type similar to the target object's type"
				, "SimilarTypesQntROC"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE,
				Integer.class);
	}

}
