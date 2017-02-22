/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc;

import java.util.Map;

import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.descr.AFeatureDescription;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 15, 2012 7:23:59 AM
 */
public abstract class AFeatureCalculator {

	protected final AFeatureDescription featureDescription;
	
	public final AFeatureDescription getFeatureDescription() {
		return featureDescription;
	}
	
	protected AFeatureCalculator(final AFeatureDescription featureDescription) {
		this.featureDescription = featureDescription;
	}
	
	abstract public FeatureValue calc(Map<EConsideredObject, RectangularArea> m);
	
}
