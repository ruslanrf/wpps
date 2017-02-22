/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;
import java.util.Set;

import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.OrthogonalVisibleObjQntROCFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 23, 2012 7:09:41 PM
 */
@Singleton
public class OrthogonalVisibleObjQntROCFCalc extends AFeatureCalculator {
	private final IIEBasisAPI api;
	
	@Inject
	public OrthogonalVisibleObjQntROCFCalc(
			OrthogonalVisibleObjQntROCFDesc featureDescription
			, final IIEBasisAPI api) {
		super(featureDescription);
		this.api = api;
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		final IQltBlock qltTarget = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
			.getRdfTargetObject().as(IQltBlock.class);
		final Set<IInstanceAdp> contextObjs = ((TObjectContext)m.get(EConsideredObject.CONTEXT))
				.getTargetContextObjects();
		
		IResults res = api.getObjects(new IPredicate<IInstanceAdp>() {
			@Override public Boolean apply(IInstanceAdp avar) {
				if (contextObjs.contains(avar) && avar.canAs(IQntBlock.class))
					return qltTarget.hasRelation(avar, EBlockQltRelation.NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF)
							|| qltTarget.hasRelation(avar, EBlockQltRelation.EAST_ORTHOGONAL_VISIBLE_BLOCK_OF)
							|| qltTarget.hasRelation(avar, EBlockQltRelation.SOUTH_ORTHOGONAL_VISIBLE_BLOCK_OF)
							|| qltTarget.hasRelation(avar, EBlockQltRelation.WEST_ORTHOGONAL_VISIBLE_BLOCK_OF);
				else return false;
		} } );
		return new FeatureValue(featureDescription, res.size());
	}
}
