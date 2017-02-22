/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Height of the target object relative to the corresponding web page.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#WEB_PAGE}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 10:49:48 PM
 */
@Singleton
public class RelativeHeightROWFDesc extends AFeatureDescription {

	public RelativeHeightROWFDesc() {
		super("1c1ed89e-04f7-11e2-9e5c-00247e160239"
				, "RelativeHeight"
				, "RelativeHeightROW"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.WEB_PAGE,
				Double.class);
	}
	
}
