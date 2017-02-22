/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Area of target object.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 14, 2012 12:35:57 AM
 */
@Singleton
public class AreaIOFDesc extends AFeatureDescription {

	public AreaIOFDesc() {
		super("f802d07c-fdd1-11e1-a4f8-00247e160239"
				, "Target object's area"
				, "AreaIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE
				, Double.class);
	}

}
