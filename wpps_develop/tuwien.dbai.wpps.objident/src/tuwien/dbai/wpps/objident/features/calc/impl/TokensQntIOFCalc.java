/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHTMLElementWithVisibleText;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.TokensQntIOFDesc;
import tuwien.dbai.wpps.objident.lib.FeatureCalcLib;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.common.base.Strings;
import com.google.inject.Inject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 23, 2012 1:24:41 AM
 */
public class TokensQntIOFCalc extends AFeatureCalculator {
	
	private final ObjidentConfig config;
	
	/**
	 * @param featureDescription
	 */
	@Inject
	protected TokensQntIOFCalc(
			ObjidentConfig config
			, TokensQntIOFDesc featureDescription) {
		super(featureDescription);
		this.config = config;
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT)).getRdfTargetObject();
		target = FeatureCalcLib.toOneOf(target, config.getConsideredObjectTypesWithText());
		if (target == null || !(target instanceof IHTMLElementWithVisibleText))
			return new FeatureValue(featureDescription, null);
		String str = ((IHTMLElementWithVisibleText)target).getText();
		int cnt = 0;
		if (!Strings.isNullOrEmpty(str)) {
			str = FeatureCalcLib.normalizeGaps(str);
			cnt = str.split(" ").length;
		}
		return new FeatureValue(featureDescription, cnt);
	}
}
