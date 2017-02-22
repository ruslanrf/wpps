/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.AlignmentIndexVertRODFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 11:46:43 AM
 */
@Singleton
public class AlignmentIndexVertRODFCalc extends AFeatureCalculator {

	private final IIEBasisAPI api;
	/**
	 * @param featureDescription
	 */
	@Inject
	public AlignmentIndexVertRODFCalc(
			AlignmentIndexVertRODFDesc featureDescription
			, IIEBasisAPI api) {
		super(featureDescription);
		this.api = api;
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		final IQltBlock target = m.get(EConsideredObject.TARGET_OBJECT).getContainedObjects().iterator().next().as(IQltBlock.class);
		IResults res = api.filter(api.toResults(m.get(EConsideredObject.WEB_DOCUMENT).getContainedObjects())
				, new IPredicate<IInstanceAdp>() {
					@Override public Boolean apply(IInstanceAdp avar) {
						return target.hasRelation(avar, EBlockQltRelation.VERTICALLY_ALIGNED_WITH);
					}
			}
		);
		
		res = api.orderBy(res, new IFunction<IInstanceAdp, Double>() {
			@Override public Double apply(IInstanceAdp avar) {
				return avar.as(IQntBlock.class).getYMin();
			}
		}
		, 1);
		
		return new FeatureValue(featureDescription , res.getResultContent().indexOf(target));
	}
}
