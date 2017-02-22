/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Iterator;
import java.util.Map;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.TextSpatialDensityICFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 1:52:20 PM TextSpatialDensityDesc
 */
@Singleton
public class TextSpatialDensityICCalc extends AFeatureCalculator {

	/**
	 * @param featureDescription
	 */
	@Inject
	protected TextSpatialDensityICCalc(TextSpatialDensityICFDesc featureDescription) {
		super(featureDescription);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		RectangularArea contextArea = m.get(EConsideredObject.CONTEXT);
		Iterator<IInstanceAdp> iter = contextArea.getContainedObjects().iterator();
		double textArea = 0;
		while (iter.hasNext()) {
			IInstanceAdp inst = iter.next();
			if (inst.canAs(IHtmlText.class)) {
				Iterator<IOutline> iter2 = inst.as(IBox.class).getClientRects().iterator();
				if (iter2.hasNext()) {
					while (iter2.hasNext()) {
						Rectangle2D r = iter2.next().as(IQntBlock.class).getArea();
						if (r.isValid())
						textArea += Rectangle2DUtils.area(r);
					}
				}
				else {
					Rectangle2D r = inst.as(IQntBlock.class).getArea();
					if (r.isValid())
						textArea += Rectangle2DUtils.area(r);
				}
				
			}
		}
		
		Rectangle2D r1 = contextArea.getArea();
		if (r1.isValid()) {
			double a = Rectangle2DUtils.area(r1);
			if (a>0)
				return new FeatureValue(featureDescription, textArea/a);
		}
		return new FeatureValue(featureDescription, null);
	}

}
