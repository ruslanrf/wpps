/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpl;

import org.apache.log4j.Logger;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.ICheckImplInitialization;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;

import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 17, 2012 4:28:38 PM
 */
@Singleton
public class PlainVisualObjectImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(PlainVisualObjectImpl.class);

	public PlainVisualObjectImpl() {
		if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
	}
	
	public void setGetForegroundTColor(IArrFunction<Object, Object> getForegroundTColor) {
		this.getForegroundTColor = getForegroundTColor;
	}
	
	public void setAddForegroundTColor(IArrFunction<Object, Object> addForegroundTColor) {
		this.addForegroundTColor = addForegroundTColor;
	}
	
	public void setForegroundGetRGBColorDistance(IArrFunction<Object, Object> getRGBColorDistance) {
		this.getForegroundRGBColorDistance = getRGBColorDistance;
	}
	
	public void setForegroundGetHSVColorDistance(IArrFunction<Object, Object> getHSVColorDistance) {
		this.getForegroundHSVColorDistance = getHSVColorDistance;
	}
	
	public void setGetBackgroundTColor(IArrFunction<Object, Object> getBackgroundTColor) {
		this.getBackgroundTColor = getBackgroundTColor;
	}
	
	public void setAddBackgroundTColor(IArrFunction<Object, Object> addBackgroundTColor) {
		this.addBackgroundTColor = addBackgroundTColor;
	}
	
	public void setBackgroundGetRGBColorDistance(IArrFunction<Object, Object> getBackgroundRGBColorDistance) {
		this.getBackgroundRGBColorDistance = getBackgroundRGBColorDistance;
	}
	
	public void setBackgroundGetHSVColorDistance(IArrFunction<Object, Object> getBackgroundHSVColorDistance) {
		this.getBackgroundHSVColorDistance = getBackgroundHSVColorDistance;
	}
	
	private IArrFunction<Object, Object> getForegroundTColor = null;
	private IArrFunction<Object, Object> addForegroundTColor = null;
	private IArrFunction<Object, Object> getForegroundRGBColorDistance = null;
	private IArrFunction<Object, Object> getForegroundHSVColorDistance = null;
	
	private IArrFunction<Object, Object> getBackgroundTColor = null;
	private IArrFunction<Object, Object> addBackgroundTColor = null;
	private IArrFunction<Object, Object> getBackgroundRGBColorDistance = null;
	private IArrFunction<Object, Object> getBackgroundHSVColorDistance = null;
	
	public TColor getForegroundTColor(final Resource instRdf) {
		return (TColor)getForegroundTColor.apply(instRdf);
	}
	
	public void addForegroundTColor(final Resource instRdf, final TColor c) {
		addForegroundTColor.apply(instRdf, c);
	}
	
	public double getForegroundRGBColorDistance(final Resource instRdf1,final Resource instRdf2) {
		return (Double)getForegroundRGBColorDistance.apply(instRdf1, instRdf2);
	}
	
	public double getForegroundHSVColorDistance(final Resource instRdf1,final Resource instRdf2) {
		return (Double)getForegroundHSVColorDistance.apply(instRdf1, instRdf2);
	}
	
	public TColor getBackgroundTColor(final Resource instRdf) {
		return (TColor)getBackgroundTColor.apply(instRdf);
	}
	
	public void addBackgroundTColor(final Resource instRdf, final TColor c) {
		addBackgroundTColor.apply(instRdf, c);
	}
	
	public double getBackgroundRGBColorDistance(final Resource instRdf1,final Resource instRdf2) {
		return (Double)getBackgroundRGBColorDistance.apply(instRdf1, instRdf2);
	}
	
	public double getBackgroundHSVColorDistance(final Resource instRdf1,final Resource instRdf2) {
		return (Double)getBackgroundHSVColorDistance.apply(instRdf1, instRdf2);
	}
	
	@Override
	public boolean allFunctionsAreImplemented() {
		return getForegroundTColor != null && addForegroundTColor != null
				&& getForegroundRGBColorDistance != null && getForegroundHSVColorDistance != null;
	}

}
