/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.fuzzy.IMuZeroDouble;
import tuwien.dbai.wpps.core.fuzzy.IntervalMuZeroDouble;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * AnnotQltBMCenterMu.
 * 
 * Mu for the Qlt Block Model.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 31, 2011 6:32:32 AM
 */
@Singleton // binding in Module. singleton
public class QltBMCenterMuProvider implements Provider<IMuZeroDouble> {
private static final Logger log = Logger.getLogger(QltBMCenterMuProvider.class);
	
	private final WPPSConfig config;
	
	@Inject
	public QltBMCenterMuProvider(WPPSConfig config) {
		this.config = config;
	}

	@Override // @AnnotQltBMLeftBorderMu
	public IMuZeroDouble get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		switch (config.getQltBMBorderMuType()) {
		case INTERVAL:
			return new IntervalMuZeroDouble(config.getQltBMCenterInterval()[0]
					, config.getQltBMCenterInterval()[1]);
		default:
			throw new UnknownValueFromPredefinedList(log, config.getQltBMBorderMuType());
		}
	}
}
