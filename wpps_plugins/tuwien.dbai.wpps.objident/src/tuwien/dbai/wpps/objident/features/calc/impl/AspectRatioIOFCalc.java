/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.AspectRatioIOFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 4:52:17 PM
 */
public class AspectRatioIOFCalc extends AFeatureCalculator {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(AreaIOFCalc.class);

	@Inject
	public AspectRatioIOFCalc(AspectRatioIOFDesc areaIntrFeatDesc) {
		super(areaIntrFeatDesc);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		Rectangle2D v = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
					.getRdfTargetObject().as(IQntBlock.class).getArea();
		Double d = v.aspectRatio();
		if (Double.isInfinite(d) || Double.isNaN(d))
			d = null;
		return new FeatureValue(featureDescription, d);
	}

}
