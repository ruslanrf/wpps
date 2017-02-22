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
import tuwien.dbai.wpps.objident.features.descr.RightTxtOfOrthVisibleObjsROCFDesc;
import tuwien.dbai.wpps.objident.lib.FeatureCalcLib;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 4:33:12 PM
 */
@Singleton
public class RightTxtOfOrthVisibleObjsROCFCalc extends AFeatureCalculator {
	
	private final IIEBasisAPI api;
	private final ObjidentConfig config;
	
	@Inject
	public RightTxtOfOrthVisibleObjsROCFCalc(
			RightTxtOfOrthVisibleObjsROCFDesc featureDescription
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
					if (qltTarget.hasRelation(avar, EBlockQltRelation.WEST_ORTHOGONAL_VISIBLE_BLOCK_OF)) {
						return true;
					}
				}
				return false;
		} } );
		
		res = api.orderBy(res,
				new IFunction<IInstanceAdp, Double>() {
					@Override public Double apply(IInstanceAdp avar) {
						return avar.as(IQntBlock.class).getYMin();
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
	
//	private final IIEBasisAPI api;
//	private final ObjidentConfig config;
//	
//	@Inject
//	public OrdTxtOfOrthVisibleEastObjROCFCalc(
//			OrdTxtOfOrthVisibleEastObjROCFDesc featureDescription
//			, final IIEBasisAPI api
//			, final ObjidentConfig config) {
//		super(featureDescription);
//		this.api = api;
//		this.config = config;
//	}
//
//	@Override
//	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
//		final IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
//				.getRdfTargetObject();
//		final IQltBlock qltTarget = target.as(IQltBlock.class);
//		final Set<IInstanceAdp> contextObjs = ((TObjectContext)m.get(EConsideredObject.CONTEXT))
//				.getTargetContextObjects();
//		
//		final Map<IInstanceAdp, Integer> orderMap = new HashMap<IInstanceAdp, Integer>();
//		
//		IResults res = api.getObjects(new IPredicate<IInstanceAdp>() {
//			@Override public Boolean apply(IInstanceAdp avar) {
//				if (contextObjs.contains(avar) && avar.canAs(IAbstractBlock.class)) {
//					if (qltTarget.hasRelation(avar, EBlockQltRelation.NEAREST_NORTH_NEIGHBORING_BLOCK_OF)) {
//						orderMap.put(avar, 4);
//						return true;
//					}
//					if (qltTarget.hasRelation(avar, EBlockQltRelation.NEAREST_EAST_NEIGHBORING_BLOCK_OF)) {
//						orderMap.put(avar, 1);
//						return true;
//					}
//					if (qltTarget.hasRelation(avar, EBlockQltRelation.NEAREST_SOUTH_NEIGHBORING_BLOCK_OF)) {
//						orderMap.put(avar, 2);
//						return true;
//					}
//					if (qltTarget.hasRelation(avar, EBlockQltRelation.NEAREST_WEST_NEIGHBORING_BLOCK_OF)) {
//						orderMap.put(avar, 3);
//						return true;
//					}
//				}
//				return false;
//		} } );
//		
//		final List<IInstanceAdp> textList = new ArrayList<IInstanceAdp>(4);
//		
//		api.forEach(res, new IProcedure<IInstanceAdp>() {
//			@Override public void apply(IInstanceAdp avar) {
//				avar = FeatureCalcLib.toOneOf(avar, config.getConsideredObjectTypesWithText());
//				if (avar != null && avar instanceof IHTMLElementWithVisibleText) {
//					String txt = ((IHTMLElementWithVisibleText)avar).getText();
//					if (txt != null && txt.trim().length()>0)
//						textList.add(avar);
//				}
//			} } );
//
//		if (res.size() > 0) {
//			final IQntBlock qntTarget = target.as(IQntBlock.class);
//			
//			List<IFunction<IInstanceAdp, Comparable<?>>> fList = new ArrayList<IFunction<IInstanceAdp, Comparable<?>>>(2);
//			fList.add(new IFunction<IInstanceAdp, Comparable<?>>() {
//						@Override public Comparable<?> apply(IInstanceAdp avar) {
//								return qntTarget.getRelationAsDouble(avar, EBlockQntRelationType.QNT_DISTANCE);
//			} } );
//			fList.add(new IFunction<IInstanceAdp, Comparable<?>>() {
//				@Override public Comparable<?> apply(IInstanceAdp avar) {
//					return orderMap.get(avar);
//				} } );
//			
//			res = api.orderBy(api.toResults(textList), fList, new int[]{1, 1});
//			
//			StringBuffer rez = new StringBuffer();
//			Iterator<IInstanceAdp> iter = res.iterator();
//			while (iter.hasNext()) {
//				String txt = ((IHTMLElementWithVisibleText)iter.next()).getText();
//				if (txt != null) txt = txt.trim();
//				txt = (txt == null || txt.length() == 0)?null:txt;
//				if (txt != null)
//					rez.append(txt+" ");
//			}
//			String rez2 = (rez.length() == 0)?null:rez.toString().trim();
//			
//			return new FeatureValue(featureDescription, rez2);
//		}
//		else
//			return new FeatureValue(featureDescription, null);
//		
//	}
	
}