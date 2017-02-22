/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 4, 2012 5:13:01 PM
 */
@Singleton
public class BottomTxtOfOrthVisibleObjsROCFDesc extends AFeatureDescription {

	public BottomTxtOfOrthVisibleObjsROCFDesc() {
		super("8bd6b8cc-269a-11e2-9e69-00247e160239"
				, "Ordered text of the orthogonally visible bottom text"
				, "BottomTxtOfOrthVisibleObjsROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT
				, String.class);
	}

}
