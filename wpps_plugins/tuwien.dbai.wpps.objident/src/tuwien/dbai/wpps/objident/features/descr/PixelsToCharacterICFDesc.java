/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Average area in the context in pixels which can be used to draw a character.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#INTRINSIC Intrinsic}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#CONTEXT} - {@linkplain EConsideredObject#NONE}. </li>
 * <ul>
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 21, 2012 11:52:39 PM
 * @see PixelsToCharacterInNormalisedContextICFDesc
 */
@Singleton
public class PixelsToCharacterICFDesc extends AFeatureDescription {

	protected PixelsToCharacterICFDesc() {
		super("35378702-8d49-42be-9f02-1a32eb12ef7f"
				,"Pixels to character"
				,"PixelsToCharacterIC"
				, EFeatureDependency.INTRINSIC
				, EConsideredObject.CONTEXT
				, EConsideredObject.NONE
				, Double.class);
	}

}
