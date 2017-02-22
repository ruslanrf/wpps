/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 12:28:02 PM
 */
public class AlignmentVertQntROCFDesc extends AFeatureDescription {

	public AlignmentVertQntROCFDesc() {
		super("adfee3d0-0119-11e2-afb5-00247e160239"
				, "Number of objects in the context vertically aligned with target object"
				, "AlignmentVertQntROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				Integer.class);
	}
	
}
