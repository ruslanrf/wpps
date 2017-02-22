/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.RelativeXPositionROWFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 9:49:41 PM
 */
@Singleton
public class RelativeXPositionROWFCalc extends AFeatureCalculator {

	/**
	 * @param featureDescription
	 */
	@Inject
	protected RelativeXPositionROWFCalc(RelativeXPositionROWFDesc featureDescription) {
		super(featureDescription);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		TObject target = (TObject)m.get(EConsideredObject.TARGET_OBJECT);
		double ox = target.getArea().xMin;
		Rectangle2D twpArea = m.get(EConsideredObject.WEB_PAGE).getArea();
//		if (twpArea.xMin != 0)
//			log.warn("xMin of top web page must be 0, but not "+twpArea.xMin);
		return new FeatureValue(featureDescription
				, (ox - twpArea.xMin)/(twpArea.xMax - twpArea.xMin));
	}

}
