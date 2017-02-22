/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.fuzzy.Nu;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * AnnotQltBMBorderNu
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 18, 2011 9:37:42 PM
 */
@Singleton // binding in Module. singleton
public class QltBMBorderNuProvider implements Provider<Nu> {
	private static final Logger log = Logger.getLogger(QltBMBorderNuProvider.class);

	private final WPPSConfig config;
	
	@Inject
	public QltBMBorderNuProvider(final WPPSConfig config) {
		this.config = config;
	}
	
	@Override
	public Nu get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
//		MatcherAssert.assertThat(config.getQltBMBorderNu(), Matchers.arrayContaining(Matchers.not(Matchers.)));
		return new Nu(config.getQltBMBorderNu()[0], config.getQltBMBorderNu()[1]);
	}

}
