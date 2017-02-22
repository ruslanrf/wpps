/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import tuwien.dbai.wpps.common.Mapping1;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IAbstractBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.DominantOrthogonalVisibleTypeROCFDesc;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 24, 2012 4:00:41 PM
 */
@Singleton
public class DominantOrthogonalVisibleTypeROCFCalc extends AFeatureCalculator {
	
	private final IIEBasisAPI api;
	private final ObjidentConfig config;
	
	@Inject
	public DominantOrthogonalVisibleTypeROCFCalc(
			DominantOrthogonalVisibleTypeROCFDesc featureDescription
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
					if (qltTarget.hasRelation(avar, EBlockQltRelation.EAST_ORTHOGONAL_VISIBLE_BLOCK_OF)) {
						return true;
					}
					if (qltTarget.hasRelation(avar, EBlockQltRelation.SOUTH_ORTHOGONAL_VISIBLE_BLOCK_OF)) {
						return true;
					}
					if (qltTarget.hasRelation(avar, EBlockQltRelation.WEST_ORTHOGONAL_VISIBLE_BLOCK_OF)) {
						return true;
					}
				}
				return false;
		} } );
			
		Mapping1 map = new Mapping1();
		for (IInstanceAdp adp : res.getResultContent()) {
			Iterator<EIMInstType> iter = config.getConsideredObjectTypes().iterator();
			while (iter.hasNext()) {
				EIMInstType t = iter.next();
				if (adp.canAs(t.getJavaInterface())) {
					Integer p = (Integer)map.getMappedObject(t);
					map.addMapping(t, (p==null)?1:p+1);
					break;
				}
			}
		}
		
		Entry<Object, Object> e = null;
		for (Entry<Object, Object> e2 : map.getUnderlyingMap().entrySet()) {
			if (e == null || ((Integer)e2.getValue())>((Integer)e.getValue()) )
				e = e2;
		}
		
		return new FeatureValue(featureDescription, (e == null)?null:e.getKey());
	}

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
//		if (res.size()>0) {
//			final IQntBlock qntTarget = target.as(IQntBlock.class);
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
//			res = api.orderBy(res, fList, new int[]{1, 1});
//			IInstanceAdp n = res.iterator().next();
//			
//			Iterator<EIMInstType> iter = config.getConsideredObjectTypes().iterator();
//			while (iter.hasNext()) {
//				EIMInstType t = iter.next();
//				if (n.canAs(t.getJavaInterface()))
//					return new FeatureValue(featureDescription, t);
//			}
//throw new GeneralUncheckedException(log, "Target object "+n+" does not correspond to the list of considered types.");
//		}
//		return new FeatureValue(featureDescription, null);
//
//	}
	
	
}