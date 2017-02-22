/**
 * 
 */
package tuwien.dbai.wpps.ui.events;

import tuwien.dbai.wpps.common.event.WPPSEvent;
import tuwien.dbai.wpps.ui.events.MethodsEvent.EventTypes;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 15, 2012 6:08:30 PM
 */
public class MethodsEvent extends WPPSEvent<EventTypes> {
	
	public static enum EventTypes {
	}
	
	protected MethodsEvent(final EventTypes e, final Object source
			, final Object[] targets, final Object[] data) {
		super(e, source, targets, data);
	}
	
	public static MethodsEvent getInstance(EventTypes event, final Object source, final Object[] data) {
		return new MethodsEvent(event, source, null, data);
	}
	
	public static MethodsEvent getInstance(EventTypes event, final Object source
			, final Object[] targets, final Object[] data) {
		return new MethodsEvent(event, source, targets, data);
	}
	
}
