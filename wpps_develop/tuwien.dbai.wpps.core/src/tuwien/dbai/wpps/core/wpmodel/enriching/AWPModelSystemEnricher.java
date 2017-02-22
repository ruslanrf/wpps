/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.enriching;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 23, 2012 3:16:10 PM
 */
public abstract class AWPModelSystemEnricher {
	private static final Logger log = Logger.getLogger(AWPModelSystemEnricher.class);
	
	private boolean isInit = false;
	
	/**
	 * must be called in a child class.
	 */
	protected void init() {
		if (isInit)
			throw new GeneralUncheckedException(log, "Enricher has been initialized already.");
		isInit = true;
	}
	
	public final void enrich() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. "+this.getClass());
		if (!isInit)
			throw new GeneralUncheckedException(log, "Enricher was not initialized.");
		_enrich();
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. "+this.getClass());
	}
	
	protected abstract void _enrich();
	

}
