/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.ontology.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;

import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 28, 2011 2:50:40 AM
 */
@Singleton // binding in Module. singleton
public class WPModelFillerProvider implements Provider<AWPModelFiller> {
	private static final Logger log = Logger.getLogger(WPModelFillerProvider.class);
	
	@Override
	public AWPModelFiller get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");

		// TODO: Implement check for other implementations
		try {
			Class<?> fillerCl = Class.forName("tuwien.dbai.wpps.core.wpmfillermozeditor.WPModelFiller");
				return (AWPModelFiller)fillerCl.newInstance();
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
