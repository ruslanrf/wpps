/**
 * 
 */
package tuwien.dbai.wpps.objident.ui.events;

import tuwien.dbai.wpps.common.event.WPPSEvent;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.objident.ObjIdentSessionController;
import tuwien.dbai.wpps.objident.ui.events.BrowserEditorEvent.EventTypes;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 2, 2012 2:28:31 AM
 */
public class BrowserEditorEvent extends WPPSEvent<EventTypes> {

	public static enum EventTypes {

//		<li>browser editor ({@linkplain EMBrowserManager}).</li>
		/**
		 * Source: {@link ObjIdentSessionController}
		 * Data:
		 * <ol>
		 * <li>browser editor ({@linkplain EMBrowserEditor}).</li>
		 * </ol>
		 */
		NEW_ACTIVE_EDITOR
		
	}
	
	protected BrowserEditorEvent(final EventTypes e, final Object source
			, final Object[] targets, final Object[] data) {
		super(e, source, targets, data);
	}
	
	public static BrowserEditorEvent getInstance(EventTypes event, final Object source, final Object[] data) {
		return new BrowserEditorEvent(event, source, null, data);
	}

	public static BrowserEditorEvent getInstance(EventTypes event, final Object source
			, final Object[] targets, final Object[] data) {
		return new BrowserEditorEvent(event, source, targets, data);
	}
	
}
