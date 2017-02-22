/**
 * 
 */
package tuwien.dbai.wpps.embrowser;

import org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditor;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIWebProgressListener;

import tuwien.dbai.wpps.common.event.WPPSEvent;
import tuwien.dbai.wpps.embrowser.EMBrowserEvent.EventTypes;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 15, 2012 7:11:32 PM
 */
public class EMBrowserEvent extends WPPSEvent<EventTypes> {
	
	public static enum EventTypes {

		/**
		 * Source: browser editor ({@linkplain EMBrowserEditor}).
		 * Data: ---.
		 */
		BROWSER_INITIALISED,
		/**
		 * Source: browser editor ({@linkplain EMBrowserEditor}).
		 * Data: url (String).
		 * 
		 */
		LOCATION_CHANGED,
		/**
		 * Event which we get from {@linkplain MozBrowserEditor}.
		 * It is not precise enough.
		 * 
		 * Source: browser editor ({@linkplain EMBrowserEditor}).
		 * Data: url (String).
		 */
		LOADING_COMPLETE,
		
		/**
		 * Event which we get from {@linkplain nsIWebProgressListener}.
		 * It is more precise than LOADING_COMPLETE.
		 * 
		 * Source: browser editor ({@linkplain EMBrowserEditor}).
		 * Data: url (String).
		 */
		LOADING_COMPLETE_2,
		
		/**
		 * It is standard selection which can be done using GUI. Selection of DOM element nodes.
		 * Source: browser editor ({@linkplain EMBrowserEditor}).
		 * Data: DOM node ({@linkplain nsIDOMNode}).
		 */
		ELEMENT_NODE_SELECTED
		
	}

	protected EMBrowserEvent(final EventTypes e, final Object source, final Object[] targets
			, final Object[] data) {
		super(e, source, targets, data);
	}
	
	public static EMBrowserEvent getInstance(EventTypes event, final Object source
			, final Object[] data) {
		return new EMBrowserEvent(event, source, null, data);
	}
	
	public static EMBrowserEvent getInstance(EventTypes event, final Object source
			, final Object[] targets, final Object[] data) {
		return new EMBrowserEvent(event, source, targets, data);
	}
	
}
