/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 23, 2012 12:51:29 AM
 */
@Singleton
public class LinesQntIOFDesc extends AFeatureDescription {

	public LinesQntIOFDesc() {
		super("121444c2-1c9b-11e2-ac95-00247e160239"
				, "Quantity of lines taken by the target object"
				, "LinesQntIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE,
				Integer.class);
	}
	
}
