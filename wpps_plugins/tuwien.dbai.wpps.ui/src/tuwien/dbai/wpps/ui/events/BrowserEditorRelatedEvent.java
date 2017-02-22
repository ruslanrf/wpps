/**
 * 
 */
package tuwien.dbai.wpps.ui.events;

import tuwien.dbai.wpps.common.event.WPPSEvent;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.WPPSUISessionController;
import tuwien.dbai.wpps.ui.events.BrowserEditorRelatedEvent.EventTypes;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 17, 2012 1:28:59 AM
 */
public class BrowserEditorRelatedEvent extends WPPSEvent<EventTypes> {
	
	public static enum EventTypes {

		/**
		 * Open new or previously inactive browser editor.
		 * Source: {@link WPPSUISessionController}
		 * Data:
		 * <ol>
		 * <li>browser editor ({@linkplain EMBrowserEditor}).</li>
		 * </ol>
		 */
		NEW_ACTIVE_EDITOR

		
//		CLOSE_EDITOR;
		
	}
	protected BrowserEditorRelatedEvent(final EventTypes e, final Object source
			, final Object[] targets, final Object[] data) {
		super(e, source, targets, data);
	}
	public static BrowserEditorRelatedEvent getInstance(EventTypes event, final Object source, final Object[] data) {
		return new BrowserEditorRelatedEvent(event, source, null, data);
	}
	public static BrowserEditorRelatedEvent getInstance(EventTypes event, final Object source
			, final Object[] targets, final Object[] data) {
		return new BrowserEditorRelatedEvent(event, source, targets, data);
	}
}
