/**
 * 
 */
package tuwien.dbai.wpps.objident.features.calc.impl;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.objident.WPPSFrameworkDependentModule;
import tuwien.dbai.wpps.objident.features.calc.ITObjectContextAreaFactory;
import tuwien.dbai.wpps.objident.model.TObject;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Not a singletone.
 * TODO: move constants to the config.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 4, 2012 7:25:57 PM
 * @see WPPSFrameworkDependentModule
 */
@Singleton
public class TObjectContextAreaFactory implements ITObjectContextAreaFactory {
	private final Logger log = Logger.getLogger(TObjectContextAreaFactory.class);

	public static final float xDMinMargin = 250;//px
	
	public static final float yDMinMargin = 250;//px
	
	public static final float xMarginRatio = 50;//%
	
	public static final float yMarginRatio = 20;//%
	
//	private final Rectangle2D topWebPageArea;
	
	@Inject
	public TObjectContextAreaFactory() {
//		this.topWebPageArea = api.getObjectsByType(IWebDocumentBlock.class)
//				.get(0).as(IWebDocumentBlock.class).getTopWebPage().as(IQntBlock.class).getArea();
	}
	
	@Override
	public Rectangle2D create(final TObject target) {
		try{
			return create(target.getArea()
					, target.getWebPage()
					.as(IQntBlock.class)
					.getArea());
		}
		catch (java.lang.IllegalArgumentException e) {
log.warn("Cannot create a context for "+target.getRdfTargetObject());
			throw e;
		}
	}
	
//	public Rectangle2D create(final IInstanceAdp target, final IInstanceAdp webpage) {
//		Rectangle2D c = new Rectangle2D();
//		if (target.canAs(IQntBlock.class) && webpage.canAs(IQntBlock.class)) {
//			final IQntBlock qntB = target.as(IQntBlock.class);
//			final IQntBlock qntW = webpage.as(IQntBlock.class);
//			c = create(qntB.getArea(), qntW.getArea());
//			
//if (log.isTraceEnabled()) log.trace("target: "+target+" target area: "+qntB.getArea()
//		+" webpage: "+webpage+" webpage area: "+qntW.getArea()+" context area: "+c);
//		} else {
//			throw new GeneralUncheckedException(log, target +" is not a singletone.");
////if (log.isDebugEnabled()) log.debug("target: "+target+" target area: - context area: -");
//		}
//		return c;
//	}

	private Rectangle2D create(Rectangle2D target, final Rectangle2D webpage) {
if (log.isDebugEnabled()) {
		Preconditions.checkArgument(target.isValid());
if (!Rectangle2DUtils.containsRectangle(target, webpage))
	log.warn("webpage: "+webpage+" target: "+target+". Target should be inside the webpage");
//		Preconditions.checkArgument(Rectangle2DUtils.containsRectangle(target, webpage)
//				, "webpage: "+webpage+" target: "+target);
		Preconditions.checkArgument(webpage.isValid());
		Preconditions.checkArgument(webpage.xMin>=0);
		Preconditions.checkArgument(webpage.yMin>=0);
}
		target = _rafContext(target); // create a context
		
		if (target.xMax<webpage.xMin || target.yMax<webpage.yMin
				|| target.xMin>webpage.xMax || target.yMin>webpage.yMax) {
if (log.isDebugEnabled()) log.debug("target object "+target+" is outside the "+webpage);
			return Rectangle2D.getZeroRectangle();
		}
		
		if (target.xMin<webpage.xMin)
			target.xMin = webpage.xMin;
		if (target.yMin<webpage.yMin)
			target.yMin = webpage.yMin;
		if (target.xMax>webpage.xMax)
			target.xMax = webpage.xMax;
		if (target.yMax>webpage.yMax)
			target.yMax = webpage.yMax;
		
		return target;
	}
	
	/**
	 * if context cannot be computed, we set (0,0;0,0)
	 * @param target
	 * @return
	 */
	private Rectangle2D _rafContext(final Rectangle2D target) {
		double w = target.xMax-target.xMin;
		double h = target.yMax-target.yMin;
		
		double dw= w*xMarginRatio/100;
		double dh= h*yMarginRatio/100;
		
		dw = Math.max(dw, xDMinMargin);
		dh = Math.max(dh, yDMinMargin);
		
		return new Rectangle2D(
				new Point2D(Math.max(0, target.xMin-dw), Math.max(0, target.yMin-dh))
				, new Point2D(target.xMax+dw, target.yMax+dh));
	}

}
