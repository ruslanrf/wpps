/**
 * 
 */
package tuwien.dbai.wpps.ui.model;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import tuwien.dbai.wpps.embrowser.EMBrowserEditor;

import com.google.common.eventbus.EventBus;

/**
 * TODO: rename to session data container
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 13, 2012 3:03:35 PM
 */
public class DataContainer {
	
	public DataContainer(
			Set<WPUMethodDescAdp> methodDescSet
			, File wppsConfigFile
			, EventBus eventBus) {
		this.methodDescSet = methodDescSet;
		this.wppsConfigFile = wppsConfigFile;
		this.eventBus = eventBus;
	}

	//=============================
	// === Core related data ===
	// ============================	
	
	private final Map<EMBrowserEditor, BrowserRelatedData> browserRelatedDataMap
		= new HashMap<EMBrowserEditor, BrowserRelatedData>();
	public BrowserRelatedData getBrowserRelatedData(EMBrowserEditor browser) {
		return browserRelatedDataMap.get(browser);
	}
	public void addBrowserRelatedData(BrowserRelatedData browserRelatedData) {
		browserRelatedDataMap.put(browserRelatedData.getBrowserEditor(), browserRelatedData);
	}
	public void rmBrowserRelatedData(EMBrowserEditor emBrowserEditor) {
		browserRelatedDataMap.remove(emBrowserEditor);
	}
	
	private final Set<WPUMethodDescAdp> methodDescSet; 
	public Set<WPUMethodDescAdp> getMethodDescSet() {
		return methodDescSet;
	}
	
	private File wppsConfigFile;
	public File getWPPSConfigFile() {
		return wppsConfigFile;
	}
	public void setWPPSConfigFile(File wppsConfigFile) {
		this.wppsConfigFile = wppsConfigFile;
	}

	//=============================
	// === GUI related data ===
	// ============================
	
	@SuppressWarnings("unused")
	private final EventBus eventBus;
	
}
