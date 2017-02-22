/**
 * 
 */
package tuwien.dbai.wpps.core.nav;

import tuwien.dbai.wpps.guava.IHasEventBusModify;

import com.google.common.eventbus.EventBus;

// TODO: Navigator must suggest should we create new state or not.
/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 10, 2012 3:59:41 PM
 */
public interface IBrowserNavigator extends IHasEventBusModify {
	
	EventBus getEventBus();
	
	void setEventBus(EventBus eb);
	
	/**
	 * @return Source which is used by navigator (e.g. Web browser: {@linkplain EMBrowserEditor})
	 */
	Object getTarget();
	
	void setTarget(Object tarfet);
	
//	public static enum EBrowserNavigatorEvents{BEGIN_INIT, BEGIN_DEINIT, BEGIN_GO, BEGIN_GET_DATA};
	
	/**
	 * @return data of the source (e.g. browser's main window: {@linkplain nsIDOMWindow})
	 */
	Object getData();
	
	/**
	 * Initialization of the navigator.
	 * @return true, if navigator is successfully initialized.
	 */
	boolean init();
	
	// TODO: change to action()
	/**
	 * @param url source
	 * @return true, if current location is set to the specified one.
	 */
	boolean go(String url);
	
	/**
	 * De-initialization of the navigator.
	 * @return true, if navigator is successfully deactivated.
	 */
	boolean deinit();
	
}
