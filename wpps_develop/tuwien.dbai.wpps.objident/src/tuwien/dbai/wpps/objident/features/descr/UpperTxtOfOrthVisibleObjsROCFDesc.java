/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 23, 2012 8:33:09 PM
 */
@Singleton
public class UpperTxtOfOrthVisibleObjsROCFDesc extends AFeatureDescription {

	public UpperTxtOfOrthVisibleObjsROCFDesc() {
		super("586ab618-1d40-11e2-97e2-00247e160239"
				, "Ordered text of the orthogonally visible upper text"
				, "UpperTxtOfOrthVisibleObjsROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT
				, String.class);
	}

}
