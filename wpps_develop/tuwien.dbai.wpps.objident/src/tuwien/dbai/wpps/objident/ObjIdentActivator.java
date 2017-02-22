package tuwien.dbai.wpps.objident;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TSEModel;

import com.google.common.eventbus.EventBus;
import com.google.inject.Injector;


/**
 * The activator class controls the plug-in life cycle
 */
public class ObjIdentActivator extends AbstractUIPlugin {
	static private final Logger logger = Logger.getLogger(ObjIdentActivator.class);

	// The plug-in ID
	public static final String PLUGIN_ID = "tuwien.dbai.wpps.objident"; //$NON-NLS-1$

	// The shared instance
	private static ObjIdentActivator plugin;
	
	private ObjIdentSessionController sessionController = null;
	
	private ServiceRegistration<ObjIdentSessionController> objIdentSessionControllerSR = null;
	private ServiceRegistration<EventBus> objIdentEventBusSR = null;
	private ServiceRegistration<Injector> objIdentSessionInjectorSR = null;
	private ServiceRegistration<ObjidentConfig> objIdentConfigSR = null;
	
	/**
	 * Transport search experiment.
	 */
	private ServiceRegistration<TSEModel> tseSR = null;
	
	
	/**
	 * The constructor
	 */
	public ObjIdentActivator() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		sessionController = new ObjIdentSessionController();
		
		objIdentSessionControllerSR
			= context.registerService(ObjIdentSessionController.class, sessionController, null);
		objIdentEventBusSR
			= context.registerService(EventBus.class, sessionController.getEventBus(), null);
		objIdentSessionInjectorSR
			= context.registerService(Injector.class, sessionController.getSessionInjector(), null);
		objIdentConfigSR
			= context.registerService(ObjidentConfig.class, sessionController.getConfig(), null);
		tseSR
			= context.registerService(TSEModel.class, new TSEModel(), null);
	}

	public void stop(BundleContext context) throws Exception {
		context.ungetService(objIdentSessionControllerSR.getReference());
		context.ungetService(objIdentEventBusSR.getReference());
		context.ungetService(objIdentSessionInjectorSR.getReference());
		context.ungetService(objIdentConfigSR.getReference());
		
		context.ungetService(tseSR.getReference());
		
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static ObjIdentActivator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
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
				e1.printStackTrace();
			}
			URI uri = null;
			try {
				uri = url.toURI();
			} catch (URISyntaxException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			pluginFolder = new File(uri);
		}
		return pluginFolder;
	}

}
