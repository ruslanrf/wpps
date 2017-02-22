/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Width of the target object relative to the corresponding web page.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#WEB_PAGE}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 10:44:36 PM
 */
@Singleton
public class RelativeWidthROWFDesc extends AFeatureDescription {

	public RelativeWidthROWFDesc() {
		super("6a9fe5c2-04f6-11e2-a9e4-00247e160239"
				, "RelativeWidth"
				, "RelativeWidthROW"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.WEB_PAGE,
				Double.class);
	}
}
