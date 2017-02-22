/**
 * File name: GeneralUncheckedException.java
 * @created: Mar 17, 2011 9:04:09 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common.exceptions;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;

/**
 * @type: GeneralUncheckedException
 *
 * @created: Mar 17, 2011 9:04:09 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public class GeneralUncheckedException extends RuntimeException {
	private final static Logger log = Logger.getLogger(GeneralUncheckedException.class);
	
	private static final long serialVersionUID = 1511832544702487978L;
	
	public GeneralUncheckedException(String message) {
		super(message);
		log.error(TSForLog.getTS(log)+message);
	}
	
	public GeneralUncheckedException(final Logger logger, String message) {
		super(message);
		logger.error(TSForLog.getTS(logger)+message);
	}

}
