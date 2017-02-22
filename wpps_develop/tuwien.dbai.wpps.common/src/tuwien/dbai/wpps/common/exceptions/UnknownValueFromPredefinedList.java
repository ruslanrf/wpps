/**
 * File name: UnknownValueFromEnum.java
 * @created: Apr 8, 2011 11:08:07 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common.exceptions;

import org.apache.log4j.Logger;

/**
 *
 * This exception is useful for the developer, when we do not consider all the values in the predefined list like "enum" in such constructions as "switch". 
 * 
 * @created: Apr 8, 2011 11:08:07 PM
 * @author Ruslan (ruslanrf@gmail.com)
 *
 */
public final class UnknownValueFromPredefinedList extends GeneralUncheckedException {
	private static final long serialVersionUID = -4456920941451362397L;
	private final static Logger log = Logger.getLogger(UnknownValueFromPredefinedList.class);
	
	private static final String msg1="Unknown value";
	private static final String msg2="from the predefined list";
	
	public UnknownValueFromPredefinedList(final Logger logger, final Object val) {
		super(log, msg1+" \""+val+"\" "+msg2);
	}
	
	public UnknownValueFromPredefinedList(final Object val) {
		super(log, msg1+" \""+val+"\" "+msg2);
	}
	
}
