/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Number of elements vertically aligned with the target object within the web document.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#WEB_DOCUMENT}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 18, 2012 12:47:06 AM
 */
@Singleton
public class AlignmentVertQntRODFDesc extends AFeatureDescription {

	public AlignmentVertQntRODFDesc() {
		super("5ce61134-138f-11e2-98be-00247e160239"
				, "Number of objects in the document vertically aligned with target object"
				, "AlignmentVertQntROD"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.WEB_DOCUMENT,
				Integer.class);
	}
	
}
