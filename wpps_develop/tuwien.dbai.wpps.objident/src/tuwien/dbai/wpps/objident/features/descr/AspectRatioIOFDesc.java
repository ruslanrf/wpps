/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 4:51:08 PM
 */
public class AspectRatioIOFDesc extends AFeatureDescription {

	public AspectRatioIOFDesc() {
		super("47e3cec4-1dea-11e2-aa1f-00247e160239"
				, "Aspect ratio"
				, "AspectRatioIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE
				, Double.class);
	}
	
}