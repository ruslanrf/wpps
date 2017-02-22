/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EFontStyle;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.ETextDecoration;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHTMLElementWithVisibleText;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.EmphasisIOFDesc;
import tuwien.dbai.wpps.objident.lib.FeatureCalcLib;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 3, 2012 7:44:46 PM
 */
@Singleton
public class EmphasisIOFCalc extends AFeatureCalculator {
	private static final Logger log = Logger.getLogger(EmphasisIOFCalc.class);

	private final ObjidentConfig config;
	
	@Inject
	public EmphasisIOFCalc(
			ObjidentConfig config
			, EmphasisIOFDesc emphasisSimple1IntrFeatDesc) {
		super(emphasisSimple1IntrFeatDesc);
		this.config = config;
	}
	
	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getContainedObjects().iterator().next();
		target = FeatureCalcLib.toOneOf(target, config.getConsideredObjectTypesWithText());
		if (target == null || !(target instanceof IHTMLElementWithVisibleText) )
			return new FeatureValue(featureDescription, null);
		
		
		IHTMLElementWithVisibleText targetText = (IHTMLElementWithVisibleText)target;
		Float fw = targetText.getFontWeight();
		EFontStyle fs = targetText.getFontStyle();
		ETextDecoration td = targetText.getTextDecoration();
		if (fw == null || fs == null || td == null) {
log.warn("Target object "+targetText+" has no expected textual attributes");
			return new FeatureValue(featureDescription, null);
		}
		
		double fontWeight = Math.max(100, Math.min(900, fw));
		int fontStyle = EFontStyle.NORMAL_FONT_STYLE.equals(fs)?1:2;
		int textDecoration = ETextDecoration.NONE.equals(td)?1:2;
		
		if (fontWeight>=400)
			fontWeight = (fontWeight-400)/300 + 1;
		else
			fontWeight = 1-(400-fontWeight)/300;

		double rez = (fontWeight + fontStyle + textDecoration) / 3;
		
		return new FeatureValue(featureDescription, rez);
	}

}
