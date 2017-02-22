/**
 * File name: UnknownType.java
 * @created: Apr 8, 2011 11:12:24 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common.exceptions;

import org.apache.log4j.Logger;

/**
 * This exception is useful for the developer, when we do not consider
 * all types (classes, interfaces, etc.) in a condition. 
 *
 * @created: Apr 8, 2011 11:12:24 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * @see UnknownValueFromPredefinedList
 */
public final class UnknownType extends GeneralUncheckedException {
	private static final long serialVersionUID = -3515385162031452883L;

	private final static Logger log = Logger.getLogger(UnknownType.class);
	
//	private static final String message1="Not all cases were considered.";
	private static final String message2="Not all cases were considered for the object: ";
	
//	public UnknownType(final Logger log) {
//		super(log, message1);
//	}
	
//	public UnknownType() {
//		super(log, message1);
//	}
	
//	public UnknownType(final Logger log, final String obj) {
//		super(log, message2+obj+".");
//	}
	
	public UnknownType(final Logger log, final Object obj) {
		super(log, message2+obj+".");
	}
	
//	public UnknownType(final String obj) {
//		super(log, message2+obj+".");
//	}
	
	public UnknownType(final Object obj) {
		super(log, message2+obj+".");
	}
	
}
