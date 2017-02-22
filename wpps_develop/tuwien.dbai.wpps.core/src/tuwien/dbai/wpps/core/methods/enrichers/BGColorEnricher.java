/**
 * 
 */
package tuwien.dbai.wpps.core.methods.enrichers;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.methods.AWPUEnricher;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.wpmodel.enriching.BGColorSimpleSystemEnricher;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 26, 2012 10:45:30 PM
 */
public class BGColorEnricher extends AWPUEnricher {
	private static final Logger log = Logger.getLogger(BGColorEnricher.class); 

	/**
	 * @param description
	 */
	public BGColorEnricher(AWPUMethodDescription description) {
		super(description);
	}

	@Override
	protected void _run() {
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"START. BGColorEnricher");
		BGColorSimpleSystemEnricher e = getIEAPI().getEnricher(BGColorSimpleSystemEnricher.class);
		e.init();
		e.enrich();
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"FINISH. BGColorEnricher");
	}

}
