/**
 * File name: GeneralException.java
 * @created: Oct 30, 2010 11:25:13 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common.exceptions;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;

/**
 * @type: GeneralException
 *
 * @created: Oct 30, 2010 11:25:13 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public class GeneralException extends Exception {
	private final static Logger logger = Logger.getLogger(GeneralException.class);
	
	private static final long serialVersionUID = -2882028440683704311L;
	
	public GeneralException(String message) {
		super(message);
		logger.error(TSForLog.getTS(logger)+message);
	}
	
	public GeneralException(Logger logger, String message) {
		super(message);
		logger.error(TSForLog.getTS(logger)+message);
	}
	
//	public GeneralException(String message, Priority p) {
//		super(message);
//		logger.log(p, message);
//	}

}
