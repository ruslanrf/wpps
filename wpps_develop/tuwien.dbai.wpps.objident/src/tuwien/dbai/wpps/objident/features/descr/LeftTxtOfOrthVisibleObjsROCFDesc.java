/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 4, 2012 5:23:49 PM
 */
public class LeftTxtOfOrthVisibleObjsROCFDesc extends AFeatureDescription {

	public LeftTxtOfOrthVisibleObjsROCFDesc() {
		super("0bac5218-269c-11e2-8315-00247e160239"
				, "Ordered text of the orthogonally visible text on the left"
				, "LeftTxtOfOrthVisibleObjsROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				String.class);
	}
	
}
