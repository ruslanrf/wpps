/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Position of the target object relative to the corresponding web page.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#TOP_WEB_PAGE}. </li>
 * <ul>
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 9:44:43 PM
 */
@Singleton
public class RelativeXPositionROWFDesc extends AFeatureDescription {

	protected RelativeXPositionROWFDesc() {
		super("4afb39b8-04ee-11e2-a0ab-00247e160239"
				, "RelativeXPosition"
				, "RelativeXPositionROW"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.TOP_WEB_PAGE,
				Double.class);
	}

}
