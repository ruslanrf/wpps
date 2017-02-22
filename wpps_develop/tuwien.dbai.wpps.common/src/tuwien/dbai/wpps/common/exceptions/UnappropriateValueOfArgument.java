/**
 * File name: UnappropriateValueOfArgument.java
 * @created: Jul 26, 2011 2:50:24 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common.exceptions;

import org.apache.log4j.Logger;

/**
 * If the argument provided to the method has inappropriate value.
 * @created: Jul 26, 2011 2:50:24 AM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class UnappropriateValueOfArgument extends GeneralUncheckedException {
	private static final long serialVersionUID = 7300253140661706603L;

private final static Logger log = Logger.getLogger(UnknownValueFromPredefinedList.class);
	
	private static final String message="Unappropriate value of the argument: ";
	
	public UnappropriateValueOfArgument(final Logger logger, final Object val) {
		super(logger, message+val+".");
	}
	
	public UnappropriateValueOfArgument(final Object val) {
		super(log, message+val+".");
	}
	
}
