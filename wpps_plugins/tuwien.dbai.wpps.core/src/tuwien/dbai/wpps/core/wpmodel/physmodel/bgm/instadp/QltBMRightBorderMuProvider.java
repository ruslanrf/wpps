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
 * AnnotQltBMRightBorderMu.
 * 
 * Mu for the Qlt Block Model.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 18, 2011 9:03:28 PM
 */
@Singleton // binding in Module. singleton
public class QltBMRightBorderMuProvider implements Provider<IMuZeroDouble> {
	private static final Logger log = Logger.getLogger(QltBMRightBorderMuProvider.class);
	
	private final WPPSConfig config;
	
	@Inject
	public QltBMRightBorderMuProvider(WPPSConfig config) {
		this.config = config;
	}

	@Override // @AnnotQltBMRightBorderMu
	public IMuZeroDouble get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		switch (config.getQltBMBorderMuType()) {
		case INTERVAL:
			return new IntervalMuZeroDouble(config.getQltBMRightBorderInterval()[0]
					, config.getQltBMRightBorderInterval()[1]);
		default:
			throw new UnknownValueFromPredefinedList(log, config.getQltBMBorderMuType());
		}
	}
	
}
