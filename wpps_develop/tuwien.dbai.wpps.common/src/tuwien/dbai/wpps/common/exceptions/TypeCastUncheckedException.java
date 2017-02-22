/**
 * File name: TypeCastUncheckedException.java
 * @created: Aug 23, 2011 5:10:21 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common.exceptions;

import org.apache.log4j.Logger;

/**
 * @created: Aug 23, 2011 5:10:21 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class TypeCastUncheckedException extends GeneralUncheckedException {
	private static final long serialVersionUID = -5659928415080798339L;

	private final static Logger log = Logger.getLogger(TypeCastUncheckedException.class);
	
	private static final String message1="Cannot convert ";
	private static final String message2=" to ";
	private static final String message3=".";
	
	public TypeCastUncheckedException(final Logger logger, final String type1
			, final String type2) {
		super(logger, message1+type1+message2+type2+message3);
	}
	
	public TypeCastUncheckedException(final Object type1
			, final Object type2) {
		super(log, message1+type1+message2+type2+message3);
	}
	
}