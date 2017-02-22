/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.RelativeWidthROWFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 10:46:08 PM
 */
@Singleton
public class RelativeWidthROWFCalc extends AFeatureCalculator {

	/**
	 * @param featureDescription
	 */
	@Inject
	protected RelativeWidthROWFCalc(RelativeWidthROWFDesc featureDescription) {
		super(featureDescription);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		TObject target = (TObject)m.get(EConsideredObject.TARGET_OBJECT);
		Rectangle2D oArea = target.getArea();
		Rectangle2D twpArea = m.get(EConsideredObject.WEB_PAGE).getArea();
//		Rectangle2D twpArea = target.getRdfTargetObject().as(IBox.class).getWebPage().as(IQntBlock.class).getArea();
		return new FeatureValue(featureDescription
				, (oArea.xMax-oArea.xMin)/(twpArea.xMax - twpArea.xMin));
	}

}
