/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Ratio of objects vertically aligned with target object +1 to horizontally aligned objects +1 in the web document.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#WEB_DOCUMENT}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 18, 2012 12:18:30 AM
 */
@Singleton
public class AlignmentVertHorRatioRODFDesc extends AFeatureDescription {

	protected AlignmentVertHorRatioRODFDesc() {
		super("cd9c5dde-0115-11e2-b447-00247e160239"
			, "Hor. to vert. alignment rate in web doc."
			, "AlignmentVertHorRatioROD"
			, EFeatureDependency.RELATIVE
			, EConsideredObject.TARGET_OBJECT
			, EConsideredObject.WEB_DOCUMENT
			, Double.class);
	}

}
