package tuwien.dbai.wpps.log4j;

import java.io.IOException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * TODO: Problem with logging duiring the init. Correct.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 13, 2012 2:51:53 PM
 */
public class Log4JActivator implements BundleActivator {

	// -Dlog4j.debug
	
	final private static Logger logger = Logger.getLogger(Log4JActivator.class);
	// The plug-in ID
	public static final String PLUGIN_ID = "tuwien.dbai.wpps.log4j"; //$NON-NLS-1$
	
	/** Name of the configuration file of log4j */
	public static final String PROPERTY_FILE_PATH = "/log4j_full.xml"; //$NON-NLS-1$

	static {
		setPropertyFile();
 	}
	
	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Log4JActivator.context = bundleContext;
if (logger.isDebugEnabled()) logger.debug("Plug-in tuwien.dbai.wpps.log4j has been started");
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Log4JActivator.context = null;
if (logger.isDebugEnabled()) logger.debug("Plug-in tuwien.dbai.wpps.log4j has been stopped");
	}

	private static final void setPropertyFile() {
		URL url =  Platform.getBundle(PLUGIN_ID).getResource(PROPERTY_FILE_PATH);
//		URL url =  getContext().getBundle().getResource(PROPERTY_FILE_PATH);
		URL localPath = null;
		try {
			localPath = FileLocator.resolve(url);
//			DOMConfigurator.configure(localPath);
			PropertyConfigurator.configure(localPath);
		} catch (IOException e) {
			log(new Status(IStatus.ERROR, PLUGIN_ID, e.getMessage(), e));
		}
	}
	
	private static void log(IStatus status) {
		Bundle bundle = Platform.getBundle(PLUGIN_ID);
		Platform.getLog(bundle).log(status);
	}
}
