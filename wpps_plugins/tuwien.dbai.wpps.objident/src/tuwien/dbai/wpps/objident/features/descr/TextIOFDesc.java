/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 2:29:15 PM
 */
@Singleton
public class TextIOFDesc extends AFeatureDescription {

	public TextIOFDesc() {
		super("7755b24e-1dd6-11e2-8e7b-00247e160239"
				, "Target object's text"
				, "TextIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE,
				String.class);
	}

}
