package tuwien.dbai.wpps.core;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;


/**
 * The activator class controls the plug-in life cycle
 */
public class WPPSCoreActivator extends Plugin {
	static private final Logger logger = Logger.getLogger(WPPSCoreActivator.class);
	
	// The plug-in ID
	public static final String PLUGIN_ID = "tuwien.dbai.wpps.core";
	
	// The shared instance
	private static WPPSCoreActivator plugin;
	
	private WPPSCoreSessionController sessionController = null;
	private ServiceRegistration<WPPSCoreSessionController> wppsCoreSessionControllerSR = null;
	
//	private ServiceRegistration<EventBus> eventBusSR = null;
	
	public WPPSCoreSessionController getSessionController() {
		return sessionController;
	}
	
	public static WPPSCoreSessionController getDefaultSessionController() {
		return getDefault().getSessionController();
	}
	
	/**
	 * The constructor
	 */
	public WPPSCoreActivator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		sessionController = new WPPSCoreSessionController();
//		Dictionary<String, String> d = new Dictionary<String, String>() {
//			
//		};
		wppsCoreSessionControllerSR
			= context.registerService(WPPSCoreSessionController.class, sessionController, null);
//		eventBusSR
//			= context.registerService(EventBus.class, sessionController.getEventBus(), null);
		
if (logger.isDebugEnabled())
logger.debug("Plug-in tuwien.dbai.wpps.core has been started");
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		context.ungetService(wppsCoreSessionControllerSR.getReference());
//		context.ungetService(eventBusSR.getReference());
		
		super.stop(context);
if (logger.isDebugEnabled())
logger.debug("Plug-in tuwien.dbai.wpps.core has been stopped");
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static WPPSCoreActivator getDefault() {
		return plugin;
	}
	
	private static File pluginFolder = null;
	/**
	 * @return Root directory of the plug-in
	 */
	public static final File getPluginFolder() {
		if (pluginFolder == null) {
			URL url = Platform.getBundle(PLUGIN_ID).getEntry("/");
			try {
				url = FileLocator.resolve(url);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			URI uri = null;
			try {
				uri = url.toURI();
			} catch (URISyntaxException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
if (logger.isTraceEnabled()) logger.trace("tuwien.dbai.wpps.core URI: "+uri);
			pluginFolder = new File(uri);
		}
		return pluginFolder;
	}
	
	public static final File getFile(String localPath) {
		URL fileURL = Platform.getBundle(PLUGIN_ID).getEntry(localPath);
		File file = null;
		try {
if (logger.isTraceEnabled()) logger.trace("tuwien.dbai.wpps.core URI: "+FileLocator.resolve(fileURL).toURI());
		    file = new File(FileLocator.resolve(fileURL).toURI());
		} catch (URISyntaxException e1) {
		    e1.printStackTrace();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
		return file;
	}
	
	/**
	 * Get URL of the plug-in
	 * @return
	 */
	public static final URL getPluginURL() {
		URL url =  getDefault().getBundle().getEntry("/");
		try {
			url = FileLocator.resolve(url);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		return url;
	}
	
	public static final URL getPluginBinDirURL() {
		URL url =  getDefault().getBundle().getResource("/");
		try {
			url = FileLocator.resolve(url);
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		return url;
	}

}
