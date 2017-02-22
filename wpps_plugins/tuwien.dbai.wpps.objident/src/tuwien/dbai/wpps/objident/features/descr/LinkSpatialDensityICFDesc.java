/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * Spatial density of links within the context: area taken by links divided by area of context.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#INTRINSIC Intrinsic}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#CONTEXT} - {@linkplain EConsideredObject#NONE}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 3:05:34 PM
 */
@Singleton
public class LinkSpatialDensityICFDesc extends AFeatureDescription {

	public LinkSpatialDensityICFDesc() {
		super("7f83f536-04b6-11e2-9fed-00247e160239"
				, "Spatial link density"
				, "LinkSpatialDensityIC"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.CONTEXT
				, EConsideredObject.NONE
				, Double.class);
	}
	
}
