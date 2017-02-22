/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Grid location of size 3.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#WEB_DOCUMENT}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 11:12:15 PM
 */
@Singleton
public class GridLocationX3ROTWFDesc extends AFeatureDescription {

	public GridLocationX3ROTWFDesc() {
		super("49307b5a-04fa-11e2-bf16-00247e160239"
				, "Grid location"
				, "GridLocationX3ROTW"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.TOP_WEB_PAGE,
				Integer.class);
	}
	
}
