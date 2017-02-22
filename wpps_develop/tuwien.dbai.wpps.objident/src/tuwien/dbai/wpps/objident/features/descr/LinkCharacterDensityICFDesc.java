/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Ratio of characters in links to all characters in the context.
 * 
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#INTRINSIC Intrinsic}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#CONTEXT} - {@linkplain EConsideredObject#NONE}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 12:05:28 AM
 */
@Singleton
public class LinkCharacterDensityICFDesc extends AFeatureDescription {

	public LinkCharacterDensityICFDesc() {
		super("b432406c-0498-11e2-bd84-00247e160239"
				,"Link symbol density"
				, "LinkCharacterDensityIC"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.CONTEXT
				, EConsideredObject.NONE
				, Double.class);
	}
	
}
