/**
 * File name: BlindzillaWorkbenchWindowAdvisor.java
 * @created: Nov 19, 2009 7:30:14 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.ui.application;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.embrowser.commands.OpenBrowserCmdHandler;
import tuwien.dbai.wpps.ui.WPPSUIActivator;
import tuwien.dbai.wpps.ui.WPPSUISessionController;

/**
 * @created: Nov 19, 2009 7:30:14 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 * Auto-generated and corrected.
 */
public class WPPSUIWorkbenchWindowAdvisor
	extends WorkbenchWindowAdvisor{
	static public final Logger log = Logger.getLogger(WPPSUIWorkbenchWindowAdvisor.class);

	public WPPSUIWorkbenchWindowAdvisor(
			IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}
	
	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new WPPSUIActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		configurer.setInitialSize(new Point(800, 600));
		configurer.setShowPerspectiveBar(true);
		configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setShowFastViewBars(true);
		configurer.setShowMenuBar(true);
		configurer.setTitle("WPPS"); //$NON-NLS-N$
//if (log.isTraceEnabled())log.trace("Main application's window is opening...");
	}
	
    @Override
    public void postWindowOpen() {
//if (log.isTraceEnabled()) log.trace("Invoke: activate session controller");
//    	WPPSUIActivator.getDefault().getSessionController().activate();
    	UIUtils.getService(WPPSUISessionController.class).activate();
    	
    	UIUtils.executeCommand(OpenBrowserCmdHandler.ID);
    }

}
