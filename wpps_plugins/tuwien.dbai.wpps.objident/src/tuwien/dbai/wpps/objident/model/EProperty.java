/**
 * 
 */
package tuwien.dbai.wpps.objident.model;

import tuwien.dbai.wpps.common.Mapping1;
import tuwien.dbai.wpps.common.Mapping2;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.objident.ObjIdentSessionController;
import tuwien.dbai.wpps.objident.ui.browser.EMBrowserManager;
import tuwien.dbai.wpps.objident.ui.events.MainEvents.EventTypes;

import com.google.inject.Injector;

/**
 * Names which allows to access corresponding objects and attributes.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created May 31, 2012 4:54:05 PM
 * @see Mapping1
 * @see Mapping2
 * @see ObjIdentSessionController
 */
@Deprecated
public enum EProperty {
	
	// =========
	//  OBJECTS
	// =========
	/**
	 * Web browser for choosing master target object.
	 * {@link EMBrowserEditor}
	 */
	WEB_BROWSER_MASTER,
	/**
	 * Web browser for choosing target objects for comparing it with master object.
	 * Type: {@link EMBrowserEditor}
	 */
	WEB_BROWSER_SECOND,
	
	/**
	 * Type: {@linkplain AddingForComparisonListener}.
	 * Set of listeners which listen for the event {@linkplain EventTypes#ADD_COMPUTED_OBJECTS_FOR_COMPARISON}.
	 */
	LISTENER_OF_COMPARITIVE_PAIRS,
	
	/**
	 * Object of type File. Folder with labeled HTML files.
	 */
	CURRENT_OPENED_FOLDER,
	
	/**
	 * Type: {@linkplain Injector}.
	 * instantiated in {@linkplain ObjIdentSessionController}.
	 */
	SESSION_INJECTOR,
	
//	/**
//	 * {@link WPPSFramework}
//	 */
//	FRAMEWORK,
//	/**
//	 * Integer. Number of web browser's tab
//	 */
//	WEB_BROWSER_WINDOW_NUM,
	
	// ============
	//  PROPERTIES
	// ============
	
	/**
	 * It is a property of objects of types {@link EMBrowserEditor};
	 * Type: {@link EMBrowserManager}
	 */
	WEB_BROWSER_MANAGER
	
}