/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.GridLocationX3ROTWFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Solution is based on Wolfgang's code.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 11:14:15 PM
 */
@Singleton
public class GridLocationX3ROTWFCalc extends AFeatureCalculator {

	/**
	 * @param featureDescription
	 */
	@Inject
	public GridLocationX3ROTWFCalc(GridLocationX3ROTWFDesc featureDescription) {
		super(featureDescription);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		Rectangle2D r = m.get(EConsideredObject.TARGET_OBJECT).getArea();
		Rectangle2D twp = m.get(EConsideredObject.TOP_WEB_PAGE).getArea();
		
		Preconditions.checkArgument(twp.xMin == 0);
		Preconditions.checkArgument(twp.yMin == 0);
		
		double w3 = twp.xMax / 3.0;
		double h3 = twp.yMax / 3.0;
		
		Rectangle2D[] sections = {
				new Rectangle2D(0, 0, w3, h3),
				new Rectangle2D(w3, 0, w3*2, h3),
				new Rectangle2D(w3*2, 0, w3*3, h3),

				new Rectangle2D(0, h3, w3, h3*2),
				new Rectangle2D(w3, h3, w3*2, h3*2),
				new Rectangle2D(w3*2, h3, w3*3, h3*2),

				new Rectangle2D(0, h3*2, w3, h3*3),
				new Rectangle2D(w3, h3*2, w3*2, h3*3),
				new Rectangle2D(w3*2, h3*2, w3*3, h3*3),
		};
		
		int bitmask = 0;
		for (int i=0; i< sections.length; i++) {
			if (Rectangle2DUtils.overlapRectangle(r, sections[i]))
				{ bitmask |= (1<<i); }
		}
		return new FeatureValue(featureDescription, bitmask);
	}

}
