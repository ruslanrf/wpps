/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 23, 2012 7:07:57 PM
 */
@Singleton
public class OrthogonalVisibleObjQntROCFDesc extends AFeatureDescription {

	public OrthogonalVisibleObjQntROCFDesc() {
		super("3d980bf8-1d34-11e2-9455-00247e160239"
				, "Number of nearest neighbours"
				, "OrthogonalVisibleObjQntROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				Integer.class);
	}

}
