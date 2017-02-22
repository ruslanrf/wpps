/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHTMLElementWithVisibleText;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.FontSizeIOFDesc;
import tuwien.dbai.wpps.objident.lib.FeatureCalcLib;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 3, 2012 5:08:25 PM
 */
@Singleton
public class FontSizeIOFCalc extends AFeatureCalculator {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(FontSizeIOFCalc.class);
	
	private final ObjidentConfig config;
	
	@Inject
	public FontSizeIOFCalc(
			ObjidentConfig config
			, final FontSizeIOFDesc fontSizeIntrFeatDesc) {
		super(fontSizeIntrFeatDesc);
		this.config = config;
	}
	
	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getRdfTargetObject();
		target = FeatureCalcLib.toOneOf(target, config.getConsideredObjectTypesWithText());
		if (target == null || !(target instanceof IHTMLElementWithVisibleText) )
			return new FeatureValue(featureDescription, null);
		return new FeatureValue(featureDescription
				, ((IHTMLElementWithVisibleText)target).getFontSize());
//		FeatureValue fv = null;
//		if (target.canAs(IHtmlText.class)) {
//			final float fs = target.as(IHtmlText.class).getFontSize();
//			fv = new FeatureValue(featureDescription//, target
//					, fs);
//		}
//		else {
//			fv = new FeatureValue(featureDescription//, target
//					, null);
//		}
//		return fv;
	}

}
