/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc;

import tuwien.dbai.wpps.core.WPPSFramework;
import tuwien.dbai.wpps.objident.features.FeatureValue;

/**
 * Instances of this interface are independent from {@linkplain WPPSFramework}.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 1, 2012 3:31:34 PM
 */
public interface IFeatureDistanceCalculator {
	
	double calc(FeatureValue fv1, FeatureValue fv2);

}
