/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * Number of elements aligned with the target object within the web document.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#WEB_DOCUMENT}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 17, 2012 12:01:43 PM
 */
@Singleton
public class AlignmentQntRODFDesc extends AFeatureDescription {

	public AlignmentQntRODFDesc() {
		super("c362dd8c-00ae-11e2-bfa4-00247e160239"
				, "Number of alignment within the doc"
				, "AlignmentQntROD"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.WEB_DOCUMENT,
				Integer.class);
	}
}
