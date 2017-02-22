/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 11:45:20 AM
 */
@Singleton
public class AlignmentIndexVertRODFDesc extends AFeatureDescription {

	public AlignmentIndexVertRODFDesc() {
		super("7183ee20-0563-11e2-be73-00247e160239"
				, "Index in vert. alignment in doc"
				, "AlignmentIndexVertROD"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.WEB_DOCUMENT,
				Integer.class);
	}
}
