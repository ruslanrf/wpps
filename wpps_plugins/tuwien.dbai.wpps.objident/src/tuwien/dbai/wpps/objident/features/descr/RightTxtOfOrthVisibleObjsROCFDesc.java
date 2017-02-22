/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 4:32:15 PM
 */
@Singleton
public class RightTxtOfOrthVisibleObjsROCFDesc extends AFeatureDescription {

	public RightTxtOfOrthVisibleObjsROCFDesc() {
		super("a4ee1974-1de7-11e2-a7ae-00247e160239"
				, "Ordered text of the orthogonally visible text on the right"
				, "RightTxtOfOrthVisibleObjsROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				String.class);
	}
	
}
