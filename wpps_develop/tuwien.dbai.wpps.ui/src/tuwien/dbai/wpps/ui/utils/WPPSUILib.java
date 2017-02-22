/**
 * 
 */
package tuwien.dbai.wpps.ui.utils;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 15, 2012 9:50:01 PM
 */
public final class WPPSUILib {
	private static final Logger log = Logger.getLogger(WPPSUILib.class);

	/**
	 * Get active EMBrowserEditor
	 * 
	 * @return EMBrowserEditor, null if there is no active browser.
	 */
	public static EMBrowserEditor getActiveBrowserEditor() {
		// --- get main window ---
		final IEditorPart editor = UIUtils.getActiveEditor();
		if (editor == null) {
			throw new GeneralUncheckedException(log, "Open web browser!");
		}
		else
		if (editor instanceof EMBrowserEditor) {
			return (EMBrowserEditor)editor;
		}
		else
			throw new GeneralUncheckedException(log, "Active editor is not a web browser!");
	}
	
	public static EMBrowserEditor getActiveBrowserEditorSoft() {
		return (EMBrowserEditor)UIUtils.getActiveEditor();
	}
	
	/**
	 * From Display thread.
	 * @return
	 */
	public static EMBrowserEditor getActiveBrowserEditorSoftDT() {
		Display d = PlatformUI.getWorkbench().getDisplay();
		final EMBrowserEditor[] b = new EMBrowserEditor[1];
		d.syncExec(new Runnable() {
			@Override public void run() {
				b[0] = WPPSUILib.getActiveBrowserEditorSoft();
			}
		});
		return b[0];
	}
	
}
