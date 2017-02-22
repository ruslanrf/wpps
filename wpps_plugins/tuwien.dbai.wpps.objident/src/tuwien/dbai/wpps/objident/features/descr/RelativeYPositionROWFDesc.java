/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * Position of the target object relative to the corresponding web page.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#TOP_WEB_PAGE}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 10:04:16 PM
 */
@Singleton
public class RelativeYPositionROWFDesc extends AFeatureDescription {

	public RelativeYPositionROWFDesc() {
		super("c4b8afe0-04f0-11e2-8569-00247e160239"
				, "RelativeYPosition"
				, "RelativeYPositionROW"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.TOP_WEB_PAGE,
				Double.class);
	}

}
