/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 23, 2012 1:22:24 AM
 */
@Singleton
public class TokensQntIOFDesc extends AFeatureDescription {

	public TokensQntIOFDesc() {
		super("8879960e-1c9f-11e2-a755-00247e160239"
				, "Quantity of tokens of the target object"
				, "TokensQntIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE,
				Integer.class);
	}

}
