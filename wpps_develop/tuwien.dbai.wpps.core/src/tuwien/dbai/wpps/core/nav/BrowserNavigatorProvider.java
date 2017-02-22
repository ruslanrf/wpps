/**
 * 
 */
package tuwien.dbai.wpps.core.nav;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.config.WPPSConfig;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 10, 2012 4:08:56 PM
 */
@Singleton // binding in Module. singleton
public class BrowserNavigatorProvider implements Provider<IBrowserNavigator> {
	private static final Logger log = Logger.getLogger(BrowserNavigatorProvider.class);

	private final WPPSConfig config;
	
	@Inject
	public BrowserNavigatorProvider(WPPSConfig config) {
		this.config = config;
	}
	
	@Override
	public IBrowserNavigator get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");

//TODO: Implement for other implementations
		try {
			Class<?> fillerCl = Class.forName("tuwien.dbai.wpps.core.wpmfillermozeditor.BrowserNavigator");
				return (IBrowserNavigator)fillerCl.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
