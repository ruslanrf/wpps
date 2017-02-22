/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.TObjectsQntICFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 17, 2012 6:18:44 PM
 */
@Singleton
public class TObjectsQntICFCalc extends AFeatureCalculator {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(TObjectsQntICFCalc.class);

	/**
	 * @param featureDescription
	 */
	@Inject
	protected TObjectsQntICFCalc(TObjectsQntICFDesc featureDescription) {
		super(featureDescription);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		return new FeatureValue(featureDescription, ((TObjectContext)m.get(EConsideredObject.CONTEXT))
				.getTargetContextObjects().size());
	}
}
