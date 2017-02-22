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
import tuwien.dbai.wpps.objident.features.descr.AlignmentVertQntRODFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TOuterObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 18, 2012 12:48:30 AM
 */
@Singleton
public class AlignmentVertQntRODFCalc extends AFeatureCalculator {

	private final IIEBasisAPI api;
	
	/**
	 * @param featureDescription
	 */
	@Inject
	public AlignmentVertQntRODFCalc(
			final AlignmentVertQntRODFDesc featureDescription
			, final IIEBasisAPI api) {
		super(featureDescription);
		this.api = api;
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		final IQltBlock target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getContainedObjects().iterator().next().as(IQltBlock.class);
		Set<IInstanceAdp> set = new HashSet<IInstanceAdp>(((TOuterObject)m.get(EConsideredObject.WEB_DOCUMENT)).getContainedObjects());
		set.remove(target);
		IResults res = api.filter(api.toResults(set)
				, new IPredicate<IInstanceAdp>() {
			@Override public Boolean apply(IInstanceAdp avar) {
				return target.hasRelation(avar, EBlockQltRelation.VERTICALLY_ALIGNED_WITH);
			}
		});
		return new FeatureValue(featureDescription, res.size());
	}

}
