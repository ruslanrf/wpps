/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.RelativeYPositionROWFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 10:05:29 PM
 */
@Singleton
public class RelativeYPositionROWFCalc extends AFeatureCalculator {
	/**
	 * @param featureDescription
	 */
	@Inject
	protected RelativeYPositionROWFCalc(RelativeYPositionROWFDesc featureDescription) {
		super(featureDescription);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		double oy = m.get(EConsideredObject.TARGET_OBJECT).getArea().yMin;
		Rectangle2D twpArea = m.get(EConsideredObject.WEB_PAGE).getArea();
//		if (twpArea.yMin != 0)
//			log.warn("yMin of top web page must be 0, but not "+twpArea.yMin);
		return new FeatureValue(featureDescription
				, (oy - twpArea.yMin)/(twpArea.yMax - twpArea.yMin));
	}

}
