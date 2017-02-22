/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 23, 2012 8:13:15 PM
 */
@Singleton
public class FullyAlignedOrthogonalVisibleObjQntROCFDesc extends AFeatureDescription {

	public FullyAlignedOrthogonalVisibleObjQntROCFDesc() {
		super("57c3a254-1d3d-11e2-93b7-00247e160239"
				, "Number of fully aligned orthogonally visible objects"
				, "FullyAlignedOrthogonalVisibleObjQntROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				Integer.class);
	}

}
