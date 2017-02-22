/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;


/**
 * Type of the object according to the hierarchy of corresponding java interfaces.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 14, 2012 12:49:11 PM
 */
@Singleton
public class TypeIOFDesc extends AFeatureDescription {

	public TypeIOFDesc() {
		super("5e269922-fe38-11e1-b498-00247e160239"
				, "Target object's type"
				, "TypeIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE,
				Enum.class);
	}

}
