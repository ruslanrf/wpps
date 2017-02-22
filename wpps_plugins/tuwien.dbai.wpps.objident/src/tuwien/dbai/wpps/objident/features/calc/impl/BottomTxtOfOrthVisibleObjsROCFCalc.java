/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Map;
import java.util.Set;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.callback.IProcedure;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IAbstractBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHTMLElementWithVisibleText;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.BottomTxtOfOrthVisibleObjsROCFDesc;
import tuwien.dbai.wpps.objident.lib.FeatureCalcLib;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 4, 2012 5:15:16 PM
 */
@Singleton
public class BottomTxtOfOrthVisibleObjsROCFCalc extends AFeatureCalculator {
	
	private final IIEBasisAPI api;
	private final ObjidentConfig config;
	
	@Inject
	public BottomTxtOfOrthVisibleObjsROCFCalc(
			BottomTxtOfOrthVisibleObjsROCFDesc featureDescription
			, final IIEBasisAPI api
			, final ObjidentConfig config) {
		super(featureDescription);
		this.api = api;
		this.config = config;
	}
	
	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		final IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getRdfTargetObject();
		final IQltBlock qltTarget = target.as(IQltBlock.class);
		final Set<IInstanceAdp> contextObjs = ((TObjectContext)m.get(EConsideredObject.CONTEXT))
				.getTargetContextObjects();
		
		IResults res = api.getObjects(new IPredicate<IInstanceAdp>() {
			@Override public Boolean apply(IInstanceAdp avar) {
				if (contextObjs.contains(avar) && avar.canAs(IAbstractBlock.class)) {
					if (qltTarget.hasRelation(avar, EBlockQltRelation.NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF)) {
						return true;
					}
				}
				return false;
		} } );
		
		res = api.orderBy(res,
				new IFunction<IInstanceAdp, Double>() {
					@Override public Double apply(IInstanceAdp avar) {
						return avar.as(IQntBlock.class).getXMin();
		} }	
			, 1);
		
			final StringBuffer textList = new StringBuffer();
			
			api.forEach(res, new IProcedure<IInstanceAdp>() {
				@Override public void apply(IInstanceAdp avar) {
					avar = FeatureCalcLib.toOneOf(avar, config.getConsideredObjectTypesWithText());
					if (avar != null && avar instanceof IHTMLElementWithVisibleText) {
						String txt = ((IHTMLElementWithVisibleText)avar).getText();
						if (txt != null && txt.trim().length()>0)
							textList.append(" "+txt);
					}
			} } );
			String rez = FeatureCalcLib.normalizeGaps(textList.toString());

		return new FeatureValue(featureDescription, (rez.length()==0)?null:rez);
	}

}
