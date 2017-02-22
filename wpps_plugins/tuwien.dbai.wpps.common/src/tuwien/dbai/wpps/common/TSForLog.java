/**
 * File name: TSForLogger.java
 * @created: Nov 21, 2010 1:35:56 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.common.base.Stopwatch;

/**
 * 
 * get timestamp for logger
 * 
 * @type: TSForLogger
 *
 * @created: Nov 21, 2010 1:35:56 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public class TSForLog {
	static private final Map<String, Stopwatch> loggerTSMap = new HashMap<String, Stopwatch>();
	
	public static final String getTS(Logger log) {
		Stopwatch sw = loggerTSMap.get(log.getName());
		if (sw == null) {
			sw = new Stopwatch();
			sw.start();
			loggerTSMap.put(log.getName(), sw);
			return "[.] ";
		}
		return "["+sw.toString()+"] ";
	}
}
