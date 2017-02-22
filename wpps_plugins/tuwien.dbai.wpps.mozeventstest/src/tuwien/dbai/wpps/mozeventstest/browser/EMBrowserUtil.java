/**
 * File name: MozBrowserUtil.java
 * @created: Sep 21, 2011 3:09:54 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.mozeventstest.browser;

import org.eclipse.atf.mozilla.ide.common.IWebBrowser;
import org.eclipse.atf.mozilla.ide.ui.MozIDEUIPlugin;
import org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditor;
import org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditorInput;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.IDE;

/**
 * Collection of helper methods.
 * This class replace class {@link org.eclipse.atf.mozilla.ide.ui.browser.util.MozBrowserUtil}.
 * This allows us to open necessary browser editor instead of hard coded one.
 * 
 * 
 * @created: Sep 21, 2011 3:09:54 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class EMBrowserUtil {

	/*
	 * Open a Browser Editor to the specified URL and in the page provided
	 */
	public static IWebBrowser openMozillaBrowser(String url, IWorkbenchPage page) throws CoreException {

		EMBrowserEditorInput editorInput = new EMBrowserEditorInput(url);

		IEditorPart editor = IDE.openEditor(page, editorInput, EMBrowserEditor.ID);

		if (editor instanceof EMBrowserEditor) {
			return ((EMBrowserEditor) editor);
		} else {
			throw new CoreException(new Status(IStatus.ERROR, MozIDEUIPlugin.PLUGIN_ID, IStatus.ERROR, "Error opening Mozilla Browser... EditorPart did not initialize properly!", null));
		}
	}

	/*
	 * Open a Browser Editor to the specified URL and in the currently active page
	 */
	public static IWebBrowser openMozillaBrowser(String url) throws CoreException {

		IWorkbenchWindow activeWindow = MozIDEUIPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
		if (activeWindow == null) {
			throw new CoreException(new Status(IStatus.ERROR, MozIDEUIPlugin.PLUGIN_ID, IStatus.ERROR, "Error opening Mozilla Browser... failed to retrieve active workbench window!", null));
		}
		IWorkbenchPage activePage = activeWindow.getActivePage();
		if (activePage == null) {
			throw new CoreException(new Status(IStatus.ERROR, MozIDEUIPlugin.PLUGIN_ID, IStatus.ERROR, "Error opening Mozilla Browser... could not retrieve active page!", null));
		}

		return openMozillaBrowser(url, activePage);

	}
	
}
