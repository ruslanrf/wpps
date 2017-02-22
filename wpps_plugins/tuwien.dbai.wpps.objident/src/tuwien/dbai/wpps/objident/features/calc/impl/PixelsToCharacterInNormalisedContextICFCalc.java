/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Iterator;
import java.util.Map;

import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.PixelsToCharacterInNormalisedContextICFDesc;
import tuwien.dbai.wpps.objident.lib.FeatureCalcLib;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 21, 2012 11:10:19 PM
 */
@Singleton
public class PixelsToCharacterInNormalisedContextICFCalc extends AFeatureCalculator {

	@Inject
	public PixelsToCharacterInNormalisedContextICFCalc(
			PixelsToCharacterInNormalisedContextICFDesc featureDescription) {
		super(featureDescription);
	}
	
//	public final static String REG_EX_FOR_GAPS = "(\\s|&nbsp;|\u0020|\u00A0|\u200B|\u3000)+";

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		RectangularArea context = m.get(EConsideredObject.CONTEXT);
		Iterator<IInstanceAdp> iter = context.getContainedObjects().iterator();
		int charCnt = 0;
		while (iter.hasNext()) {
			IInstanceAdp a = iter.next();
			if (a.canAs(IHtmlText.class)) {
				String str = a.as(IHtmlText.class).getText();
				if (!Strings.isNullOrEmpty(str)) {
					str = FeatureCalcLib.normalizeGaps(str);
					charCnt += str.replaceAll(" ", "").length();
				}
			}
		}

		if (charCnt != 0)
			return
			new FeatureValue(featureDescription
					, Rectangle2DUtils.area(context.getArea()) /
						Rectangle2DUtils.area(m.get(EConsideredObject.TOP_WEB_PAGE).getArea())
							/(double)charCnt
							);
		else
			return new FeatureValue(featureDescription
					, null);
		
	}

}
