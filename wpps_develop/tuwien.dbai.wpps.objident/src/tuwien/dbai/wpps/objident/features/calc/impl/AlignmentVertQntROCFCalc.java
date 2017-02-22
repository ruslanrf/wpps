/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.AlignmentVertQntROCFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.inject.Inject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 12:29:22 PM
 */
public class AlignmentVertQntROCFCalc extends AFeatureCalculator {

private final IIEBasisAPI api;
	
	/**
	 * @param featureDescription
	 */
	@Inject
	public AlignmentVertQntROCFCalc(
			final AlignmentVertQntROCFDesc featureDescription
			, final IIEBasisAPI api) {
		super(featureDescription);
		this.api = api;
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		final IQltBlock target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getContainedObjects().iterator().next().as(IQltBlock.class);
		Set<IInstanceAdp> set = new HashSet<IInstanceAdp>(((TObjectContext)m.get(EConsideredObject.CONTEXT)).getTargetContextObjects());
		IResults res = api.filter(api.toResults(set)
				, new IPredicate<IInstanceAdp>() {
			@Override public Boolean apply(IInstanceAdp avar) {
				return target.hasRelation(avar, EBlockQltRelation.VERTICALLY_ALIGNED_WITH);
			}
		});
		return new FeatureValue(featureDescription//, target
				, res.size());
	}
	
}
