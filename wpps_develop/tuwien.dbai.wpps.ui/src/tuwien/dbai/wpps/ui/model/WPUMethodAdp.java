/**
 * 
 */
package tuwien.dbai.wpps.ui.model;

import tuwien.dbai.wpps.common.event.WPPSPropertyChangeEvent;
import tuwien.dbai.wpps.core.methods.AWPUMethod;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.model.WPUMethodAdp.Event.EventTypes;

import com.google.common.eventbus.EventBus;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 13, 2012 5:51:48 PM
 */
public class WPUMethodAdp {
	
	public static class Event extends WPPSPropertyChangeEvent<EventTypes> {
		public static enum EventTypes {
			/**
			 * Source: {@link BrowserRelatedData}
			 * Data:
			 * <ol>
			 * <li>old value ({@linkplain Boolean}).</li>
			 * <li>new value ({@linkplain Boolean}).</li>
			 * </ol>
			 */
			METHOD_DESC_SELECTION_CHANGE
		}
		protected Event(EventTypes en, final Object source, final Object oldVal, final Object newVal) {
			super(en, source, null, oldVal, newVal);
		}
		public static Event getInstance(EventTypes event, final WPUMethodAdp source, final Object oldVal, final Object newVal) {
			return new Event(event, source, oldVal, newVal);
		}
	}
	
	public WPUMethodAdp(WPUMethodDescAdp descrAdp
			, EMBrowserEditor browserEditor
			, EventBus eventBus) {
		this.method = null;
		this.descrAdp = descrAdp;
		selected = false;
		this.browserEditor = browserEditor;
		this.eventBus = eventBus;
	}
	
	// EMBrowserEditor browserEditor
	public WPUMethodAdp(WPUMethodDescAdp descrAdp
			, AWPUMethod method
			, EMBrowserEditor browserEditor
			, EventBus eventBus) {
		this.method = method;
		this.descrAdp = descrAdp;
		selected = false;
		this.browserEditor = browserEditor;
		this.eventBus = eventBus;
	}

	//=============================
	// === Core related data ===
	// ============================
	
	private AWPUMethod method;
	public AWPUMethod getContent() {
		return method;
	}
	public boolean hasContent() {
		return method != null;
	}
	public void setContent(AWPUMethod method) {
		this.method = method;
	}
	
	private final WPUMethodDescAdp descrAdp;
	public WPUMethodDescAdp getDescription() {
		return descrAdp;
	}
	
	//=============================
	// === GUI related data ===
	// ============================
	
	private EMBrowserEditor browserEditor;
	public EMBrowserEditor getBrowserEditor() {
		return browserEditor;
	}
	
	private boolean selected;
	public boolean isSelected() {
		return selected;
	}
	public void select2(boolean val) {
		eventBus.post(
				Event.getInstance(EventTypes.METHOD_DESC_SELECTION_CHANGE
						, WPUMethodAdp.this, selected, selected = val)
				);
	}
	
	private EventBus eventBus;
	
}
