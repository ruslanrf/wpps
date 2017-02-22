/**
 * 
 */
package tuwien.dbai.wpps.core;

import java.io.File;
import java.util.concurrent.Executors;

import tuwien.dbai.wpps.guava.IHasEventBus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 14, 2012 3:49:20 PM
 */
public final class WPPSCoreSessionController implements IHasEventBus {
	
	public WPPSCoreSessionController() {
//		eventBus = new EventBus("WPPS Core Event Bus");
		eventBus = new AsyncEventBus("WPPS Core Event Bus",
				Executors.newCachedThreadPool());
		
//		wppsConfigFile = new File(WPPSCoreActivator.getPluginFolder(), "config/wpps-config.xml");
		wppsConfigFile = WPPSCoreActivator.getFile("config/wpps-config.xml");
	}
	
//	private final EventBus eventBus;
	private final EventBus eventBus;
	
	public EventBus getEventBus() {
		return eventBus;
	}
	
	private final File wppsConfigFile;
	public File getWPPSConfigFile() {
		return wppsConfigFile;
	}
	
	

}
