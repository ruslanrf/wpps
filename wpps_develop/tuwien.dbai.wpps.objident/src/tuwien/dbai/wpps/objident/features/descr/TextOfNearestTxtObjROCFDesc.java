/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 4, 2012 6:34:13 PM
 */
@Singleton
public class TextOfNearestTxtObjROCFDesc extends AFeatureDescription {

	protected TextOfNearestTxtObjROCFDesc() {
		super("e8679970-26a5-11e2-8f2c-00247e160239" 
				, "Nearest text"
				, "TextOfNearestTxtObjROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				String.class);
	}
	
}
