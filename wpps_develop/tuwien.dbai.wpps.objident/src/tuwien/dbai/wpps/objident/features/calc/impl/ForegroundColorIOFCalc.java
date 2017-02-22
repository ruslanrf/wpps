/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IPlainVisualObject;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.WPPSFrameworkDependentModule;
import tuwien.dbai.wpps.objident.annot.ForegroundColorIOFAnnot;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.ForegroundColorIOFDesc;
import tuwien.dbai.wpps.objident.lib.FeatureCalcLib;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 1, 2012 5:50:26 PM
 * @see WPPSFrameworkDependentModule
 * @see ForegroundColorIOFAnnot
 */
@Singleton
public class ForegroundColorIOFCalc extends AFeatureCalculator {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ForegroundColorIOFCalc.class);
	
	private ObjidentConfig config;
	
	@Inject
	public ForegroundColorIOFCalc(
			ObjidentConfig config
			, ForegroundColorIOFDesc fgColorInrFeatDesc) {
		super(fgColorInrFeatDesc);
		this.config = config;
	}
	
	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		final IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getRdfTargetObject();
		if (FeatureCalcLib.oneOf(target, config.getConsideredObjectTypesWithText())) {
			TColor c = null;
			try {
				c = target.as(IPlainVisualObject.class).getForegroundTColor();
			} catch (GeneralUncheckedException ex) {
				c = TColor.WHITE.copy();
			}

			return new FeatureValue(featureDescription, c);
		}
		return new FeatureValue(featureDescription, null);
	}
	
}
