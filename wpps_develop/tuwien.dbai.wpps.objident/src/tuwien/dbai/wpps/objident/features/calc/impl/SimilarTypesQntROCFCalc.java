/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.SimilarTypesQntROCFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 4, 2012 6:58:56 PM
 */
@Singleton
public class SimilarTypesQntROCFCalc extends AFeatureCalculator {
	private static final Logger log = Logger.getLogger(TypeIOFCalc.class);

	private final ObjidentConfig config;
	
	
	@Inject
	public SimilarTypesQntROCFCalc(
			SimilarTypesQntROCFDesc featureDescription
			, ObjidentConfig config) {
		super(featureDescription);
		this.config = config;
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		final IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
			.getRdfTargetObject();
		Set<IInstanceAdp> adpSet = ((TObjectContext)m.get(EConsideredObject.CONTEXT))
				.getTargetContextObjects();
		
		Iterator<EIMInstType> iter = config.getConsideredObjectTypes().iterator();
		EIMInstType rez = null;
		while (iter.hasNext()) {
			EIMInstType t = iter.next();
			if (target.canAs(t.getJavaInterface())) {
				rez = t;
				break;
			}
		}
		if (rez == null) {
			log.warn("Target object "+target+" does not correspond to any of listed types.");
			return new FeatureValue(featureDescription, null);
		}
		int cnt = 0;
		for (IInstanceAdp adp : adpSet) {
			if (adp.canAs(rez.getJavaInterface()))
				cnt++;
		}
		return new FeatureValue(featureDescription, cnt);
	}
}