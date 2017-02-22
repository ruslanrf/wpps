/**
 * File name: UnimplementedFunctionException.java
 * @created: Apr 27, 2011 7:25:52 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common.exceptions;

import org.apache.log4j.Logger;

/**
 * @type: UnimplementedFunctionException
 * 
 * This exception is used for a developer. To be informed about calling unimplemented function,
 * which is exists as a stub.
 *
 * @created: Apr 27, 2011 7:25:52 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public class UnimplementedFunctionException extends GeneralUncheckedException {
	private static final long serialVersionUID = 2856455424820694947L;

	private final static Logger log = Logger.getLogger(UnimplementedFunctionException.class);
	
	private static final String message="Function is not implemented.";
	
	public UnimplementedFunctionException(final Logger logger) {
		super(logger, message);
	}
	
	public UnimplementedFunctionException() {
		super(log, message);
	}
}
