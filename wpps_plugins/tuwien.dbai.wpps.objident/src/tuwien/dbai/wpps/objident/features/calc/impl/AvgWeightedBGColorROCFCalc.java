/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import java.util.Collection;
import java.util.Map;

import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IPlainVisualObject;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.calc.AFeatureCalculator;
import tuwien.dbai.wpps.objident.features.descr.AvgWeightedBGColorROCFDesc;
import tuwien.dbai.wpps.objident.lib.FeatureCalcLib;
import tuwien.dbai.wpps.objident.model.EConsideredObject;
import tuwien.dbai.wpps.objident.model.RectangularArea;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectContext;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 11:17:00 AM
 */
@Singleton
public class AvgWeightedBGColorROCFCalc  extends AFeatureCalculator {

	private static final Logger log = Logger.getLogger(AvgWeightedFGColorROCFCalc.class);

	/**
	 * @param targetObjectContentFactory
	 */
	@Inject
	public AvgWeightedBGColorROCFCalc(AvgWeightedBGColorROCFDesc avgBGColorIntrFeatDesc) {
		super(avgBGColorIntrFeatDesc);
	}

	@Override
	public FeatureValue calc(Map<EConsideredObject, RectangularArea> m) {
		Collection<IInstanceAdp> cntxObjSet = ((TObjectContext)m.get(EConsideredObject.CONTEXT))
				.getContainedObjects();
		final IInstanceAdp target = ((TObject)m.get(EConsideredObject.TARGET_OBJECT))
				.getRdfTargetObject();
		final TColor targetFColor = target.as(IPlainVisualObject.class).getBackgroundTColor();
		if (targetFColor == null) {
if (log.isTraceEnabled()) log.trace("Object "+target+" does not have background color");
			return new FeatureValue(featureDescription, null);
		}
			
//		final double area = Rectangle2DUtils.area(target.as(IQntBlock.class).getArea());
		
		Double rez = FeatureCalcLib.aggrForEachElement(cntxObjSet
				, new IFunction<IInstanceAdp, Object[]>() {
					@Override public Object[] apply(IInstanceAdp ind) {
						TColor bgColor2 = ind.as(IPlainVisualObject.class).getBackgroundTColor();
						if (bgColor2 == null) {
if (log.isDebugEnabled()) log.debug("Individ "+ind+" does not have any background color.");
							return null;
						}
						double d = targetFColor.distanceToHSV(bgColor2);
						double v = Rectangle2DUtils.area(ind.as(IQntBlock.class).getArea());
if (log.isTraceEnabled()) log.trace("ind: "+ind+" target: "+target+" dist: "+d+" vol: "+v);
						return new Object[]{d, v};
					}
				}
				, new IFunction<Collection<Object[]>, Double>() {
					@Override public Double apply(Collection<Object[]> avar1) {
						double numerator = 0;
						double denominator = 0;
						for (Object[] oArr : avar1) {
							if (oArr != null) {
								numerator+=(double)oArr[0]*(double)oArr[1];
								denominator+=(double)oArr[1];
							}
						}
						if (denominator == 0) {
							denominator = Double.MIN_NORMAL;
if (log.isDebugEnabled()) log.debug("denominator is 0");
						}
						return numerator/denominator;
					}
				});
		
if (log.isTraceEnabled()) log.trace("avg bg color distance: "+rez);
	Preconditions.checkNotNull(rez);
			return new FeatureValue(featureDescription//, target
					, rez);
	}
	
}
