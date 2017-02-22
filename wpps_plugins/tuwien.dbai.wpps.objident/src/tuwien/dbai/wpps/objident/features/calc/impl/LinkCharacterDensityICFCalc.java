/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Iterator;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.LinkCharacterDensityICFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 11:48:42 AM
 */
@Singleton
public class LinkCharacterDensityICFCalc extends AFeatureCalculator {

	/**
	 * @param featureDescription
	 */
	@Inject
	protected LinkCharacterDensityICFCalc(LinkCharacterDensityICFDesc featureDescription) {
		super(featureDescription);
	}
	
	public final static String REG_EX_FOR_GAPS = "(\\s|&nbsp;|\u0020|\u00A0|\u200B|\u3000)+";

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		Iterator<IInstanceAdp> iter = m.get(EConsideredObject.CONTEXT)
				.getContainedObjects().iterator();
		int cAll = 0;
		int cLinks = 0;
		while (iter.hasNext()) {
			IInstanceAdp ins = iter.next();
			if (ins.canAs(IHtmlText.class)) {
				IHtmlText txt = ins.as(IHtmlText.class);
				int c = txt.getText().replaceAll(REG_EX_FOR_GAPS, "").length();
				cAll += c;
				if (txt.hasHtmlLink()) {
					cLinks += c;
				}
			}
				
		}
		if (cAll != 0)
			return new FeatureValue(featureDescription
					, cLinks/(double)cAll);
		else
			return new FeatureValue(featureDescription
					, null);
	}

}
