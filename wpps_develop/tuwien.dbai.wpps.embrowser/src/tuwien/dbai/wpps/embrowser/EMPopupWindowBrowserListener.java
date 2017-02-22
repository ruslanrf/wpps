/**
 * File name: EMPopupWindowBrowserListener.java
 * @created: Oct 12, 2011 12:42:46 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.embrowser;

import org.eclipse.atf.mozilla.ide.common.IWebBrowser;
import org.eclipse.atf.mozilla.ide.ui.MozIDEUIPlugin;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.ui.IWorkbenchPage;

/**
 * <p>Modified class PopupWindowBrowserListener from the package org.eclipse.atf.mozilla.ide.ui.browser.
 * This allows us to open our browser in the new tab</p>
 * <p>
 * This class takes care of detecting when a new browser window needs to be opened. It gives the end-user the option
 * of opening the new browser as an editor or in a popup dialog.
 * 
 * Some limitations:
 * - The Event does not provide any information about size so we are defaulting to 300x300
 * </p>
 * 
 * @created: Oct 12, 2011 12:42:46 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class EMPopupWindowBrowserListener
	extends org.eclipse.atf.mozilla.ide.ui.browser.PopupWindowBrowserListener {
	public EMPopupWindowBrowserListener(IShellProvider shellProvider) {
		super(shellProvider);
	}
	
	@Override
	protected void openAsEditor(WindowEvent event) {
		try {
			IWorkbenchPage page = MozIDEUIPlugin.getDefault().getWorkbench()
					.getActiveWorkbenchWindow().getActivePage();

			IWebBrowser wb = EMBrowserUtil.openMozillaBrowser(EMBrowserEditor.DEFAULT_URL,
					page);
			Browser b = (Browser) wb.getAdapter(Browser.class);
			event.browser = b;

		} catch (Exception e) {
			MozIDEUIPlugin.log(e);
		}
	}
}
