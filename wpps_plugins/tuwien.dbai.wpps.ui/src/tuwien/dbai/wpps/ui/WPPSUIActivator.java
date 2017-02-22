package tuwien.dbai.wpps.ui;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * The activator class controls the plug-in life cycle
 */
public class WPPSUIActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "tuwien.dbai.wpps.ui";
	
	static private final Logger log = Logger.getLogger(WPPSUIActivator.class);

	// The shared instance
	private static WPPSUIActivator plugin;
	
	// we can instantiate this in the start() function
	private WPPSUISessionController sessionController = null;
	private BlindzillaController blindzillaController = null;
	
	private ServiceRegistration<WPPSUISessionController> sessionControllerSR = null;
	private ServiceRegistration<BlindzillaController> blindzillaControllerSR = null;
	
	/**
	 * The constructor
	 */
	public WPPSUIActivator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		sessionController = new WPPSUISessionController();
		// register session controllecr as a service.
		sessionControllerSR = 
				context.registerService(WPPSUISessionController.class, sessionController, null);
		
		blindzillaController = new BlindzillaController();
		blindzillaControllerSR = 
				context.registerService(BlindzillaController.class, blindzillaController, null);
		
	}

	public void stop(BundleContext context) throws Exception {
		context.ungetService(sessionControllerSR.getReference());
		context.ungetService(blindzillaControllerSR.getReference());
		
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static WPPSUIActivator getDefault() {
		return plugin;
	}

	
	/**
	 * @return Root directory of the plug-in
	 */
	public static final File getPluginFolder() {
		File pluginFolder = null;
			URL url = Platform.getBundle(PLUGIN_ID).getEntry("/");
			try {
				url = FileLocator.resolve(url);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			URI uri = null;
			try {
				uri = url.toURI();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			pluginFolder = new File(uri);
		return pluginFolder;
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
			log.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
		return url;
	}
	
	
	// DO NOT REMOVE THIS COMMENTED CODE.
	
//	ServiceTracker<ITest, Object> st = new ServiceTracker<ITest, Object>(context, ITest.class, 
//			new ServiceTrackerCustomizer<ITest, Object>() {
//				@Override
//				public ITest addingService(
//						ServiceReference<ITest> reference) {
//					t = (ITest)context.getService(reference);
//					System.out.println("!!!!");
//					return null;
//				}
//
//				@Override
//				public void modifiedService(
//						ServiceReference<ITest> reference, Object service) {
//					System.out.println("modifiedService:: reference: "+reference+" service: "+service);
//				}
//
//				@Override
//				public void removedService(
//						ServiceReference<ITest> reference, Object service) {
//					System.out.println("removedService:: reference: "+reference+" service: "+service);
//					
//				}
//			} );
//	st.open();

	
}
