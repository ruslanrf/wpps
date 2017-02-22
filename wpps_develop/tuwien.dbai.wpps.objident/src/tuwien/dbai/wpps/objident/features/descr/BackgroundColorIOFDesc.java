/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import toxi.color.TColor;
import tuwien.dbai.wpps.objident.SessionModule;
import tuwien.dbai.wpps.objident.annot.BackgroundColorIOFAnnot;
import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 1, 2012 5:46:57 PM
 * @see SessionModule
 * @see BackgroundColorIOFAnnot
 */
@Singleton
public class BackgroundColorIOFDesc extends AFeatureDescription {

	public BackgroundColorIOFDesc() {
		super("32c3e160-ac01-11e1-80b1-00247e160239"
				, "Target object's background color"
				, "BackgroundColorIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE
				, TColor.class);
	}

}
