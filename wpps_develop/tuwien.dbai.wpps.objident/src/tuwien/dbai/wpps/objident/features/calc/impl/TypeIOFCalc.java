/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.TypeIOFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 14, 2012 12:46:37 PM
 */
@Singleton
public class TypeIOFCalc extends AFeatureCalculator {
	private static final Logger log = Logger.getLogger(TypeIOFCalc.class);

	private final ObjidentConfig config;
	
	@Inject
	public TypeIOFCalc(
			TypeIOFDesc featureDescription
			, ObjidentConfig config) {
		super(featureDescription);
		this.config = config;
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		final IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
			.getRdfTargetObject();
		Iterator<EIMInstType> iter = config.getConsideredObjectTypes().iterator();
		while (iter.hasNext()) {
			EIMInstType t = iter.next();
			if (target.canAs(t.getJavaInterface()))
				return new FeatureValue(featureDescription, t);
		}
throw new GeneralUncheckedException(log, "Target object "+target+" does not correspond to the list of considered types.");
//		return new FeatureValue(featureDescription, null);
	}
}
