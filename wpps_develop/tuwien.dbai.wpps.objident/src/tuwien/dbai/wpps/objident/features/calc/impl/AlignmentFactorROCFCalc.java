/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.AlignmentFactorROCFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 17, 2012 6:09:00 PM
 * @see AlignmentFactorROCFDesc
 */
@Singleton
public class AlignmentFactorROCFCalc extends AFeatureCalculator {

	private final TObjectsQntICFCalc numObjectsROCFCalc;

	private final AlignmentQntROCFCalc numAlignmentROCFCalc;

	/**
	 * @param featureDescription
	 */
	@Inject
	protected AlignmentFactorROCFCalc(final AlignmentFactorROCFDesc featureDescription
			, final TObjectsQntICFCalc numObjectsROCFCalc
			, final AlignmentQntROCFCalc numAlignmentROCFCalc) {
		super(featureDescription);
		this.numObjectsROCFCalc = numObjectsROCFCalc;
		this.numAlignmentROCFCalc = numAlignmentROCFCalc;
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		Integer numObjectsROCFRes = (Integer)numObjectsROCFCalc.calc(m).getValue();
		Integer numAlignmentROCFRes = (Integer)numAlignmentROCFCalc.calc(m).getValue();
		
//		if (numObjectsROCFRes != 0)
			return new FeatureValue(featureDescription
					, (numAlignmentROCFRes+1)/((double)numObjectsROCFRes+1));
//		else
//			return new FeatureValue(featureDescription, null);
		
	}

}
