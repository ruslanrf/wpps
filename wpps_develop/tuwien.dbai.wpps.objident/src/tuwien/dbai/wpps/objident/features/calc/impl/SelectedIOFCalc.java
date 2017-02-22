/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlCheckBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlRadioButton;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.SelectedIOFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 2:53:05 PM
 */
@Singleton
public class SelectedIOFCalc extends AFeatureCalculator {

	/**
	 * @param featureDescription
	 */
	@Inject
	protected SelectedIOFCalc(
			SelectedIOFDesc featureDescription
			) {
		super(featureDescription);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getRdfTargetObject();
		Boolean rez = null;
		if (target.canAs(IHtmlCheckBox.class)) {
			rez = target.as(IHtmlCheckBox.class).isChecked();
		}
		else
			if (target.canAs(IHtmlRadioButton.class)) {
				rez = target.as(IHtmlRadioButton.class).isChecked();
			}
		
		return new FeatureValue(featureDescription, rez);
	}

}