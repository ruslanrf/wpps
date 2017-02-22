/**
 * 
 */
package tuwien.dbai.wpps.objident.features;

import java.util.Map;

import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 16, 2012 6:39:27 PM
 */
public interface IPrepareForFeatureCalculation {
	
	void enrichment();

	Map<EConsideredObject, RectangularArea> prepare(TObject tObject);
	
}
