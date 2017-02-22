/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IPlainVisualObject;
import tuwien.dbai.wpps.objident.WPPSFrameworkDependentModule;
import tuwien.dbai.wpps.objident.annot.BackgroundColorIOFAnnot;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.BackgroundColorIOFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * TODO: correct computation according to the specification.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 2, 2012 3:05:18 AM
 * @see WPPSFrameworkDependentModule
 * @see BackgroundColorIOFAnnot
 */
@Singleton
public class BackgroundColorIOFCalc extends AFeatureCalculator {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BackgroundColorIOFCalc.class);

	@Inject
	public BackgroundColorIOFCalc(BackgroundColorIOFDesc bgColorInrFeatDesc) {
		super(bgColorInrFeatDesc);
	}
	
	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		final IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getRdfTargetObject();
		TColor c = target.as(IPlainVisualObject.class).getBackgroundTColor();
		if (c != null)
			return new FeatureValue(featureDescription, c);
		return new FeatureValue(featureDescription, null);
	}
}
