/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;


import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 3, 2012 5:09:54 PM
 */
@Singleton
public class FontSizeIOFDesc extends AFeatureDescription {

	public FontSizeIOFDesc() {
		super("3436496e-c521-11e1-b4b5-00247e160239"
				, "Font size"
				, "FontSizeIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE,
				Float.class);
	}

}
