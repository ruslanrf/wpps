/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;


/**
 * TODO: Feature is not considered
 * Average area of the context which can be used to draw a character. Area is normalised by the size of top window.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#INTRINSIC Intrinsic}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#CONTEXT} - {@linkplain EConsideredObject#NONE}. </li>
 * <ul>
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 21, 2012 11:05:03 PM
 * @see PixelsToCharacterICFDesc
 */
@Singleton
public class PixelsToCharacterInNormalisedContextICFDesc extends AFeatureDescription {

	public PixelsToCharacterInNormalisedContextICFDesc() {
		super("622d3bd7-5a25-452d-bb82-bdb2a620baab"
				,"Normalised pixels to character"
				, "pixelsToCharacterInNormalizedContext"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.CONTEXT
				, EConsideredObject.NONE
				, Double.class);
	}

}
