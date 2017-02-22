/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 3:03:39 PM
 */
@Singleton
public class EditableIOFDesc extends AFeatureDescription {

	public EditableIOFDesc() {
		super("48e427f6-1ddb-11e2-b1d6-00247e160239"
				, "Editable"
				, "EditableIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE
				, Boolean.class);
	}

}
