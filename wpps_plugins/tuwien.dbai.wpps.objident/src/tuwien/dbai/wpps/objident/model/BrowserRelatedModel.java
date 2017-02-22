/**
 * 
 */
package tuwien.dbai.wpps.objident.model;

import org.eclipse.swt.graphics.RGB;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.common.event.WPPSPropertyChangeEvent;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.embrowser.addons.DOMSelectionBoxGroups;
import tuwien.dbai.wpps.objident.ObjIdentActivator;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModel.Event.EventTypes;

import com.google.common.eventbus.EventBus;
import com.google.inject.Injector;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 2, 2012 6:07:16 PM
 */
public class BrowserRelatedModel {
	
	public static enum EBrowserType {
		/**
		 * Web browser for choosing master target object.
		 */
		WEB_BROWSER_MASTER,
		/**
		 * Web browser for choosing target objects for comparing it with master object.
		 */
		WEB_BROWSER_SECOND
	}

	private final EventBus eventBus;
//	public EventBus getEventBus() {
//		return eventBus;
//	}
	
	BrowserRelatedModel(
			EMBrowserEditor browserEditor
			, EBrowserType browserType
			, RGB browserColor
//			, Injector frameworkDependentInj
			) {
		this.browserEditor = browserEditor;
		this.browserType = browserType;
		this.browserColor = browserColor;
//		this.frameworkDependentInj = frameworkDependentInj;
		
		this.eventBus = UIUtils.getService(ObjIdentActivator.class, EventBus.class);
		ObjidentConfig config = UIUtils.getService(ObjIdentActivator.class, ObjidentConfig.class);
		targetColor = config.getTargetColor();
		contextColor = config.getContextColor();
		containedContextObjectsColor = config.getContainedContextObjectsColor();
		positiveExampleColor = config.getPositiveExampleColor();
		negativeExampleColor = config.getNegativeExampleColor();
		masterExampleColor = config.getMasterExampleColor();
	}
	
	public static class Event extends WPPSPropertyChangeEvent<EventTypes> {
		public static enum EventTypes {

			/**
			 * Source: {@link BrowserRelatedModel}
			 * Data:
			 * <ol>
			 * <li>old value ({@linkplain TObject}).</li>
			 * <li>new value ({@linkplain TObject}).</li>
			 * </ol>
			 */
			SET_SELECTED_OBJECT
			
		}
		
		protected Event(EventTypes en, final Object source, final Object oldVal, final Object newVal) {
			super(en, source, null, oldVal, newVal);
		}
		
		public static Event getInstance(EventTypes event, final BrowserRelatedModel source, final Object oldVal, final Object newVal) {
			return new Event(event, source, oldVal, newVal);
		}
		
	}
	
	private final EMBrowserEditor browserEditor;
	public EMBrowserEditor getBrowserEditor() {
		return browserEditor;
	}
	
	
	private final EBrowserType browserType;
	public EBrowserType getBrowserType() {
		return browserType;
	}
	
	// ===========
	// === GUI ===
	// ===========
	
	private final RGB browserColor;
	public RGB getBrowserColor() {
		return browserColor;
	}
	
	private Injector frameworkDependentInj = null;
	public Injector getFrameworkDependentInj() {
		return frameworkDependentInj;
	}
	public void setFrameworkDependentInj(Injector frameworkDependentInj) {
		this.frameworkDependentInj = frameworkDependentInj;
	}
	
	private TObject lastSelectedObject = null;
	public TObject getSelectedObject() {
		return lastSelectedObject;
	}
	public void setSelectedObject2(TObject selectedObject) {
//System.out.println("BrowserRelatedModel#setSelectedObject2");
		eventBus.post(
				Event.getInstance(EventTypes.SET_SELECTED_OBJECT
						, this, this.lastSelectedObject, this.lastSelectedObject = selectedObject)
				);
	}
	
	private DOMSelectionBoxGroups contextHighlighter;
	public DOMSelectionBoxGroups getContextHighlighter() {
		return contextHighlighter;
	}
	public void setContextHighlighter(DOMSelectionBoxGroups contextHighlighter) {
		this.contextHighlighter = contextHighlighter;
	}
	
	private final TColor targetColor;
	public TColor getTargetColor() {
		return targetColor;
	}
	
	private final TColor contextColor;
	public TColor getContextColor() {
		return contextColor;
	}
	
	private final TColor containedContextObjectsColor;
	public TColor getContainedContextObjectsColor() {
		return containedContextObjectsColor;
	}
	
	private final TColor positiveExampleColor;
	public TColor getPositiveExampleColor() {
		return positiveExampleColor;
	}
	private final TColor negativeExampleColor;
	public TColor getNegativeExampleColor() {
		return negativeExampleColor;
	}
	private final TColor masterExampleColor;
	public TColor getMasterExampleColor() {
		return masterExampleColor;
	}
	
}
