/**
 * 
 */
package tuwien.dbai.wpps.ui.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.browser.EMBrowserManager;

import com.google.common.eventbus.EventBus;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 1, 2012 10:29:58 PM
 */
public class BrowsersRelatedDataManager {
	
	private final EventBus eventBus;
	
	private final DataContainer modelContainer;
	
	public BrowsersRelatedDataManager(
			DataContainer modelContainer
			, EventBus eventBus) {
		this.modelContainer = modelContainer;
		this.eventBus = eventBus;
//		eventBus.register(this);
	}
	
	private Map<EMBrowserEditor, EMBrowserManager> emBrowserManagerMap
		= new HashMap<EMBrowserEditor, EMBrowserManager>();
	public EMBrowserManager getEMBrowserManager(EMBrowserEditor browser) {
		return emBrowserManagerMap.get(browser);
	}
	public Set<EMBrowserEditor> getStoredEMBrowserEditors() {
		return emBrowserManagerMap.keySet();
	}

	public void buildBrowserRelatedObjects(EMBrowserEditor browserEditor) {
		BrowserRelatedData brm = new BrowserRelatedData(browserEditor
//				, new WPPSFramework(browserEditor)
				, modelContainer.getMethodDescSet()
				, eventBus);
		modelContainer.addBrowserRelatedData(brm);
		emBrowserManagerMap.put(browserEditor
				, new EMBrowserManager(browserEditor, brm, eventBus));
	}
	
	public void disposeBrowserRelatedObjects(EMBrowserEditor browserEditor) {
		modelContainer.rmBrowserRelatedData(browserEditor);
		emBrowserManagerMap.get(browserEditor).dispose();
		emBrowserManagerMap.remove(browserEditor);
	}
	
//	public void dispose() {
//		eventBus.unregister(this);
//	}
	
	
	// ====================================
	// Google's Event Bus based listeners
	// ====================================
		
}
