/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Spatial density of text within the context: area taken by text divided by area of context.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#INTRINSIC Intrinsic}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#CONTEXT} - {@linkplain EConsideredObject#NONE}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 1:43:18 PM
 */
@Singleton
public class TextSpatialDensityICFDesc extends AFeatureDescription {

	public TextSpatialDensityICFDesc() {
		super("e62fa020-04aa-11e2-be61-00247e160239"
				, "Spatial text density"
				, "TextSpatialDensityIC"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.CONTEXT
				, EConsideredObject.NONE
				, Double.class);
	}

}
