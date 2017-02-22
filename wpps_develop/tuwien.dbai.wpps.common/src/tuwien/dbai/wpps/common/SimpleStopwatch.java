/**
 * File name: Stopwatch.java
 * @created: Feb 8, 2011 7:20:23 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common;

/**
 * @type: Stopwatch
 * TODO use stopwatch from guava.
 *
 * @created: Feb 8, 2011 7:20:23 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public class SimpleStopwatch {
	
	private long timeBegin = 0;
	
	private long elapsedTime = 0;
	public synchronized long getElapsedTime() {
		return elapsedTime;
	}

	public synchronized void start() {
		timeBegin = System.currentTimeMillis();
	}
	
	public synchronized void stop() {
		elapsedTime = System.currentTimeMillis() - timeBegin;
	}
	
}
