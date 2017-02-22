/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 2:51:31 PM
 */
@Singleton
public class SelectedIOFDesc extends AFeatureDescription { 

	public SelectedIOFDesc() {
		super("8bc95b6a-1dd9-11e2-be94-00247e160239"
				, "Target object selection"
				, "SelectedIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE,
				Boolean.class);
	}
	
}
