/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 3:39:50 PM
 */
@Singleton
public class AlignmentIndexHorROCFDesc extends AFeatureDescription {

	public AlignmentIndexHorROCFDesc() {
		super("52ab1970-1de0-11e2-92af-00247e160239"
				, "Index in hor. alignment in context"
				, "AlignmentIndexHorROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				Integer.class);
	}

}
