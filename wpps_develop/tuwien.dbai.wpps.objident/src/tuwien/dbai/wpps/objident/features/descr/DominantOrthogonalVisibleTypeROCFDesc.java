/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 3:58:44 PM
 */
public class DominantOrthogonalVisibleTypeROCFDesc extends AFeatureDescription {

	public DominantOrthogonalVisibleTypeROCFDesc() {
		super("f7c2bf38-1de2-11e2-a4ed-00247e160239"
				, "Dominanit type of the orthogonally visible objects"
				, "DominantOrthogonalVisibleTypeROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				Enum.class);
	}
	
}
