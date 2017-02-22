/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.AlignmentVertHorRatioROCFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 18, 2012 12:02:27 AM
 */
@Singleton
public class AlignmentVertHorRatioROCFCalc extends AFeatureCalculator {

	private final IIEBasisAPI api;
	
	/**
	 * @param featureDescription
	 */
	@Inject
	protected AlignmentVertHorRatioROCFCalc(
			AlignmentVertHorRatioROCFDesc featureDescription
			, final IIEBasisAPI api) {
		super(featureDescription);
		this.api = api;
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		final IQltBlock target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getContainedObjects().iterator().next().as(IQltBlock.class);
		final IResults contextObjRes = api.toResults(((TObjectContext)m.get(EConsideredObject.CONTEXT)).getTargetContextObjects());
		
		IResults resHor = api.filter(contextObjRes
				, new IPredicate<IInstanceAdp>() {
			@Override public Boolean apply(IInstanceAdp avar) {
				return target.hasRelation(avar, EBlockQltRelation.HORIZONTALLY_ALIGNED_WITH);
			}
		});
		IResults resVert = api.filter(contextObjRes
				, new IPredicate<IInstanceAdp>() {
			@Override public Boolean apply(IInstanceAdp avar) {
				return target.hasRelation(avar, EBlockQltRelation.VERTICALLY_ALIGNED_WITH);
			}
		});
		
		return new FeatureValue(featureDescription
				, (resVert.size()+1)/(double)(resHor.size()+1));
	}

}
