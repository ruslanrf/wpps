/**
 * 
 */
package tuwien.dbai.wpps.ui.model;

import java.util.Set;

import tuwien.dbai.wpps.core.methods.AWPUMethod;
import tuwien.dbai.wpps.core.methods.WPUMethodsFactory;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.browser.EMBrowserManager;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 16, 2012 3:55:47 PM
 */
@Deprecated
public enum EProperty {
	
	// =========================
	// Mapping 1
	// =========================
	
	/**
	 * Type: {@link tuwien.dbai.wpps.colors.ColorGenerator}
	 */
	COLOR_GENERATOR(tuwien.dbai.wpps.colors.ColorGenerator.class),
	
	/**
	 * Type: {@link Set<AMethod>}
	 */
	AVAILABLE_METHODS_DESCRIPTIONS(Set.class),
	
//	ACTIVE_BROWSER(EMBrowserEditor.class),
	
	/**
	 * Type: {@link MethodsFactory}
	 */
	METHODS_FACTORY(WPUMethodsFactory.class),
	
	// =========================
	// Mapping 2
	// =========================
	
	/**
	 * Type: {@link BrowserEditorData}
	 * Objects: {@linkplain EMBrowserEditor}
	 */
	BROWSER_DATA(BrowserRelatedData.class),
	
	/**
	 * Type: {@link EMBrowserEditorManager}
	 * Objects: {@linkplain EMBrowserEditor}
	 */
	BROWSER_MANAGER(EMBrowserManager.class),
	
	/**
	 * Type: {@link toxi.color.TColor}
	 * Objects: {@linkplain AMethodDescription}
	 */
	COLOR(toxi.color.TColor.class),
//	/**
//	 * Type: Boolean.
//	 * Objects: {@linkplain AWPAMethod}
//	 */
//	APPLIED(Boolean.class),
	/**
	 * Type: Boolean.
	 * Objects: {@linkplain AMethodDescription}
	 */
	SELECTED(Boolean.class),
	
	IMPLEMENTATION(AWPUMethod.class);

	
	
	private final Class<?> clazz;
	
	public Class<?> getDataClass() {
		return clazz;
	}
	
	private EProperty(final Class<?> clazz) {
		this.clazz = clazz;
	}
	

	
}
