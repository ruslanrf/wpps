/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.AreaIOFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 14, 2012 12:40:05 AM
 */
@Singleton
public class AreaIOFCalc extends AFeatureCalculator {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(AreaIOFCalc.class);

	@Inject
	public AreaIOFCalc(AreaIOFDesc areaIntrFeatDesc) {
		super(areaIntrFeatDesc);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		double v = Rectangle2DUtils.area(
				((TObject)m.get(EConsideredObject.TARGET_OBJECT))
					.getRdfTargetObject().as(IQntBlock.class).getArea());
		FeatureValue fv = new FeatureValue(featureDescription, v);
		return fv;
	}

}
