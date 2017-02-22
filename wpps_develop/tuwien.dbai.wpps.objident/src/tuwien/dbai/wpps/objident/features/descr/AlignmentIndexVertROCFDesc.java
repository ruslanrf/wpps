/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 3:44:16 PM
 */
@Singleton
public class AlignmentIndexVertROCFDesc extends AFeatureDescription {

	public AlignmentIndexVertROCFDesc() {
		super("ec315582-1de0-11e2-ba7f-00247e160239"
				, "Index in vert. alignment in context"
				, "AlignmentIndexVertROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				Integer.class);
	}
}
