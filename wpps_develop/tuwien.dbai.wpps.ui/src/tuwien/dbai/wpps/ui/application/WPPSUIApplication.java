/**
 * File name: BlindzillaApplication.java
 * @created: Nov 19, 2009 7:12:58 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.ui.application;

import org.apache.log4j.Logger;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

/**
 * @type: BlindzillaApplication
 *
 * @created: Nov 19, 2009 7:12:58 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * 
 * Auto-generated and corrected.
 * 
 */
public class WPPSUIApplication implements IApplication {
	final static private Logger logger = Logger.getLogger(WPPSUIApplication.class);
	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#start(org.eclipse.equinox.app.IApplicationContext)
	 */
	@Override
	public Object start(IApplicationContext context) throws Exception {
		Display display = PlatformUI.createDisplay();
		try {
			int returnCode = PlatformUI.createAndRunWorkbench(display, new WPPSUIWorkbenchAdvisor());
			if (returnCode == PlatformUI.RETURN_RESTART) {
				return IApplication.EXIT_RESTART;
			}
			return IApplication.EXIT_OK;
		}
		catch(org.mozilla.xpcom.XPCOMException e) {
			logger.error(e.getMessage());
			display.dispose();
			return null;
		}
		finally {
			display.dispose();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.equinox.app.IApplication#stop()
	 */
	@Override
	public void stop() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench == null)
			return;
		final Display display = workbench.getDisplay();
		display.syncExec(new Runnable() {
			public void run() {
				if (!display.isDisposed())
					workbench.close();
			}
	});
	}

}
