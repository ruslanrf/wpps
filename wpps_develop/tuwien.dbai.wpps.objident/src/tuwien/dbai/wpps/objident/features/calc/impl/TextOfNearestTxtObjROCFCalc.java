/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHTMLElementWithVisibleText;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.TextOfNearestTxtObjROCFDesc;
import tuwien.dbai.wpps.objident.lib.FeatureCalcLib;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 4, 2012 6:36:18 PM
 */
@Singleton
public class TextOfNearestTxtObjROCFCalc extends AFeatureCalculator {

	private final IIEBasisAPI api;
	private final ObjidentConfig config;
	
	@Inject
	public TextOfNearestTxtObjROCFCalc(
			TextOfNearestTxtObjROCFDesc featureDescription
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
		final Set<IInstanceAdp> contextObjs = ((TObjectContext)m.get(EConsideredObject.CONTEXT))
				.getTargetContextObjects();
		
		if (contextObjs.size() > 0) {
			final List<IInstanceAdp> textList = new ArrayList<IInstanceAdp>(4);
			
			for (IInstanceAdp avar : contextObjs) {
					avar = FeatureCalcLib.toOneOf(avar, config.getConsideredObjectTypesWithText());
					if (avar != null && avar instanceof IHTMLElementWithVisibleText) {
						String txt = ((IHTMLElementWithVisibleText)avar).getText();
						if (txt != null && FeatureCalcLib.normalizeGaps(txt).length()>0)
							textList.add(avar);
					}
			};

			if (textList.size()>0) {
				final IQntBlock qntTarget = target.as(IQntBlock.class);
				IResults res = api.orderBy(api.toResults(textList)
						,new IFunction<IInstanceAdp, Comparable<?>>() {
							@Override public Comparable<?> apply(IInstanceAdp avar) {
								return qntTarget.getRelationAsDouble(avar, EBlockQntRelationType.QNT_DISTANCE);
							} }
						, 1);
			
				String txt = ((IHTMLElementWithVisibleText)res.iterator().next()).getText();
				if (txt != null) txt = FeatureCalcLib.normalizeGaps(txt);
				txt = (txt == null || txt.length() == 0)?null:txt;
				return new FeatureValue(featureDescription, txt);
			}
		}
		return new FeatureValue(featureDescription, null);
	}
}
