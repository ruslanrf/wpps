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
 * AnnotQltBMLeftBorderMu.
 * 
 * Mu for the Qlt Block Model.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 18, 2011 9:03:28 PM
 */
@Singleton // binding in Module. singleton
public class QltBMLeftBorderMuProvider implements Provider<IMuZeroDouble> {
	private static final Logger log = Logger.getLogger(QltBMLeftBorderMuProvider.class);
	
	private final WPPSConfig config;
	
	@Inject
	public QltBMLeftBorderMuProvider(WPPSConfig config) {
		this.config = config;
	}

	@Override // @AnnotQltBMLeftBorderMu
	public IMuZeroDouble get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		switch (config.getQltBMBorderMuType()) {
		case INTERVAL:
			return new IntervalMuZeroDouble(config.getQltBMLeftBorderInterval()[0]
					, config.getQltBMLeftBorderInterval()[1]);
		default:
			throw new UnknownValueFromPredefinedList(log, config.getQltBMBorderMuType());
		}
	}
	
}
