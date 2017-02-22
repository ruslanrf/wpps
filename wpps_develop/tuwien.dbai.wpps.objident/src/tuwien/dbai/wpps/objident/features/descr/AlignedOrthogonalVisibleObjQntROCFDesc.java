/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 23, 2012 8:02:17 PM
 */
@Singleton
public class AlignedOrthogonalVisibleObjQntROCFDesc extends AFeatureDescription {

	public AlignedOrthogonalVisibleObjQntROCFDesc() {
		super("6062278c-1d3d-11e2-8301-00247e160239"
				, "Number of aligned orthogonally visible objects"
				, "AlignedOrthogonalVisibleObjQntROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				Integer.class);
	}

}
