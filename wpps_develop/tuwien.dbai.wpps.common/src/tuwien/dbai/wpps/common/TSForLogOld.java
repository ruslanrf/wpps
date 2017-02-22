/**
 * 
 */
package tuwien.dbai.wpps.common;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 2, 2011 6:34:34 PM
 */
@Deprecated
public class TSForLogOld {
static private final Map<String, Long> loggerTSMap = new HashMap<String, Long>();
	
	public interface TimeWriter {
		public String getTimeLogInfo(long ts1, long ts2);
	}
	
	private static TimeWriter timeWriter = new  TimeWriter() {
		@Override
		public String getTimeLogInfo(long ts1, long ts2) {
			long tm = Long.MIN_VALUE;
			if (ts1<0)
				return "[.] ";
			else 
				tm = ts2 - ts1;
			
			long hours, minutes, seconds, ms;
		    ms = tm % 1000;
		    long timeInSeconds = tm / 1000;
		    hours = timeInSeconds / 3600;
		    timeInSeconds = timeInSeconds - (hours * 3600);
		    minutes = timeInSeconds / 60;
		    timeInSeconds = timeInSeconds - (minutes * 60);
		    seconds = timeInSeconds;
		    timeInSeconds = timeInSeconds - (seconds * 60);
		    
		    final StringBuffer sb = new StringBuffer(20);
		    
		    sb.append("[");
		    if (hours != 0) {
		    	sb.append(hours);
		    	sb.append(":");
		    }
		    if (minutes != 0) {
		    	sb.append(minutes);
		    	sb.append(":");
		    }
		    if (seconds != 0) {
		    	sb.append(seconds);
		    }
		    sb.append(".");
		    if (ms < 10)
		    	sb.append("00");
		    else if (ms < 100)
		    	sb.append("0");
		    sb.append(ms);
		    
		    sb.append("] ");
			
			return sb.toString();
		}
	};
	
	public static final void setTimeWriter(TimeWriter tw) {
		timeWriter = tw;
	}
	
	public static final String getTS(Logger logger) {
		Long lastTS = loggerTSMap.get(logger.getName());
		if (lastTS == null)
			lastTS = -1l;
		final long currTS =  System.currentTimeMillis();
		loggerTSMap.put(logger.getName(), currTS);
		return timeWriter.getTimeLogInfo(lastTS, currTS);
	}
}
