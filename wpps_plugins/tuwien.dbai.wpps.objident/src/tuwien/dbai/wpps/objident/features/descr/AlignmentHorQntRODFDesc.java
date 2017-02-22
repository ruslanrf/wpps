/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Number of elements horizontally aligned with the target object within the web document.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#WEB_DOCUMENT}. </li>
 * <ul>
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 18, 2012 1:05:47 AM
 */
@Singleton
public class AlignmentHorQntRODFDesc extends AFeatureDescription {

	public AlignmentHorQntRODFDesc() {
		super("42f66f42-011c-11e2-bba8-00247e160239"
				, "Number of objects in the document horizontally aligned with target object"
				, "AlignmentHorQntROD"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.WEB_DOCUMENT,
				Integer.class);
	}
	
}
