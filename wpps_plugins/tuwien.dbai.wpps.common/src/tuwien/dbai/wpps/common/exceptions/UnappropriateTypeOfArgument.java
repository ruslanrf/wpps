/**
 * 
 */
package tuwien.dbai.wpps.common.exceptions;

import org.apache.log4j.Logger;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 15, 2012 1:47:36 AM
 */
public class UnappropriateTypeOfArgument extends GeneralUncheckedException {

	private static final long serialVersionUID = 5449504171452762119L;

private final static Logger log = Logger.getLogger(UnknownValueFromPredefinedList.class);
	
	private static final String msg1="Unappropriate type of the argument: ";
	private static final String msg2=", must be: ";
	
	public UnappropriateTypeOfArgument(final Logger log, final Class<?> arg, final Class<?> cl) {
		super(log, msg1+arg+msg2+cl);
	}
	
	public UnappropriateTypeOfArgument(final Logger log, final Class<?> arg) {
		super(log, msg1+arg);
	}
	
	public UnappropriateTypeOfArgument(final Logger log) {
		super(log, msg1+"?");
	}
	
	public UnappropriateTypeOfArgument(final Class<?> arg, final Class<?> cl) {
		super(log, msg1+arg+msg2+cl);
	}
	
	public UnappropriateTypeOfArgument(final Class<?> arg) {
		super(log, msg1+arg);
	}
	
	public UnappropriateTypeOfArgument() {
		super(log, msg1+"?");
	}
	
}
