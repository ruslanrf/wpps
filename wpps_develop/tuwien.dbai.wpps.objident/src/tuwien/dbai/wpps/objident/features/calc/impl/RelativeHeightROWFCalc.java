/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.RelativeHeightROWFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 10:50:54 PM
 */
@Singleton
public class RelativeHeightROWFCalc extends AFeatureCalculator {

	/**
	 * @param featureDescription
	 */
	@Inject
	protected RelativeHeightROWFCalc(RelativeHeightROWFDesc featureDescription) {
		super(featureDescription);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		TObject target = (TObject)m.get(EConsideredObject.TARGET_OBJECT);
		Rectangle2D oArea = target.getRdfTargetObject().as(IQntBlock.class).getArea();
		Rectangle2D twpArea = m.get(EConsideredObject.WEB_PAGE).getArea();
//		Rectangle2D twpArea = target.getRdfTargetObject().as(IBox.class).getWebPage().as(IQntBlock.class).getArea();
		return new FeatureValue(featureDescription
				, (oArea.yMax-oArea.yMin)/(twpArea.yMax - twpArea.yMin));
	}
	
}
