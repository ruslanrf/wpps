/**
 * File name: UnknownPrimBrowserEvent.java
 * @created: Oct 17, 2011 4:00:34 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common.exceptions;

import org.apache.log4j.Logger;

/**
 * @created: Oct 17, 2011 4:00:34 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class UnknownPrimBrowserEvent extends GeneralUncheckedException {

	private static final long serialVersionUID = -4804692856913924831L;

	private final static Logger log = Logger.getLogger(UnknownPrimBrowserEvent.class);
	
	private static final String message="Unknown primitive browser event: ";
	
	
	public UnknownPrimBrowserEvent(final Logger log, final Object obj) {
		super(log, message+obj+".");
	}
	
	public UnknownPrimBrowserEvent(final Object obj) {
		super(log, message+obj+".");
	}
	
}
