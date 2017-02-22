/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * font-weight, font-style, font-decoration.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 3, 2012 7:40:11 PM
 */
@Singleton
public class EmphasisIOFDesc extends AFeatureDescription {

	public EmphasisIOFDesc() {
		super("39b57f4e-c536-11e1-b4a0-00247e160239"
				, "Emphasis"
				, "EmphasisIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE,
				Double.class);
	}

}
