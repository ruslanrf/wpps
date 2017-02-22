/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import toxi.color.TColor;
import tuwien.dbai.wpps.objident.SessionModule;
import tuwien.dbai.wpps.objident.annot.ForegroundColorIOFAnnot;
import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 1, 2012 5:46:57 PM
 * @see SessionModule
 * @see ForegroundColorIOFAnnot
 */
@Singleton
public class ForegroundColorIOFDesc extends AFeatureDescription {

	public ForegroundColorIOFDesc() {
		super("7cfda698-ac52-11e1-bde8-00247e160239"
				, "Target object's foreground color"
				, "ForegroundColorIO"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.NONE
				, TColor.class);
	}

}
