/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Collection;
import java.util.Map;

import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.LinesQntIOFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 23, 2012 12:52:57 AM
 */
@Singleton
public class LinesQntIOFCalc extends AFeatureCalculator {

	/**
	 * @param featureDescription
	 */
	@Inject
	protected LinesQntIOFCalc(LinesQntIOFDesc featureDescription) {
		super(featureDescription);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		TObject target = (TObject)m.get(EConsideredObject.TARGET_OBJECT);
		Collection<IOutline> crCol = target.getRdfTargetObject().as(IBox.class).getClientRects();
		int rez = 1;
		if (crCol != null && crCol.size()>0) {
			rez = crCol.size();
		}
		
		return new FeatureValue(featureDescription, rez);
	}
	
}
