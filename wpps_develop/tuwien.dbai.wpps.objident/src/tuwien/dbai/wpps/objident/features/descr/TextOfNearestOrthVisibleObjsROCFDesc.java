/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 4, 2012 5:31:48 PM
 */
@Singleton
public class TextOfNearestOrthVisibleObjsROCFDesc extends AFeatureDescription {

	public TextOfNearestOrthVisibleObjsROCFDesc() {
		super("9a8fb0ce-26a1-11e2-ae7f-00247e160239"
				, "Text of the nearest textual orthogonally visible object"
				, "TextOfNearestOrthVisibleObjsROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT
				, String.class);
	}

}
