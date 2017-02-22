/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHTMLElementWithVisibleText;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlImage;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.TextIOFDesc;
import tuwien.dbai.wpps.objident.lib.FeatureCalcLib;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 2:30:36 PM
 */
@Singleton
public class TextIOFCalc extends AFeatureCalculator {

	private final ObjidentConfig config;
	
	/**
	 * @param featureDescription
	 */
	@Inject
	protected TextIOFCalc(
			TextIOFDesc featureDescription
			, ObjidentConfig config
			) {
		super(featureDescription);
		this.config = config;
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getRdfTargetObject();
		String rez = null;
		if (target.canAs(IHtmlImage.class)) {
			rez = target.as(IHtmlImage.class).getAltText();
		}
		else
			target = FeatureCalcLib.toOneOf(target, config.getConsideredObjectTypesWithText());
		if (target != null && target instanceof IHTMLElementWithVisibleText)
			rez = ((IHTMLElementWithVisibleText)target).getText();
		if (rez != null)
			rez = FeatureCalcLib.normalizeGaps(rez);
		if (rez != null && rez.length() == 0)
			rez = null;
		
		return new FeatureValue(featureDescription, rez);
	}

}
