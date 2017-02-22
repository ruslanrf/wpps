/**
 * File name: EMBrowserUtil.java
 * @created: Oct 12, 2011 12:42:10 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.embrowser;

import org.apache.log4j.Logger;
import org.eclipse.atf.mozilla.ide.ui.MozIDEUIPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.browser.Browser;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.IDE;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIWebBrowser;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;

/**
 * Collection of helper methods.
 * This class replace class {@link org.eclipse.atf.mozilla.ide.ui.browser.util.MozBrowserUtil}.
 * This allows us to open necessary browser editor instead of hard coded one.
 * 
 * @created: Oct 12, 2011 12:42:10 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public final class EMBrowserUtil {
	private static final Logger log = Logger.getLogger(EMBrowserUtil.class);
	
	public static EMBrowserEditor getActiveEMBrowserEditor() {
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
	
	public static nsIDOMWindow getNSIDOMWindow(Browser swtMozBrowser) {
		return ((nsIWebBrowser)swtMozBrowser.getWebBrowser()).getContentDOMWindow();
		
	}
	
	/*
	 * Open a Browser Editor to the specified URL and in the page provided
	 */
	public static EMBrowserEditor openMozillaBrowser(String url, IWorkbenchPage page) throws CoreException {

		EMBrowserEditorInput editorInput = new EMBrowserEditorInput(url);

		IEditorPart editor = IDE.openEditor(page, editorInput, EMBrowserEditor.ID);

		if (editor instanceof EMBrowserEditor) {
			return ((EMBrowserEditor) editor);
		} else {
			throw new CoreException(new Status(IStatus.ERROR, MozIDEUIPlugin.PLUGIN_ID, IStatus.ERROR, "Error opening Mozilla Browser... EditorPart did not initialize properly!", null));
		}
	}

	/**
	 * Open a Browser Editor with default (about:blank) URL and in the currently active page
	 * @return
	 * @throws CoreException
	 */
	public static EMBrowserEditor openMozillaBrowser() throws CoreException {
		return openMozillaBrowser(EMBrowserEditor.DEFAULT_URL);
	}
	
	/**
	 * Open a Browser Editor to the specified URL and in the currently active page
	 */
	public static EMBrowserEditor openMozillaBrowser(String url) throws CoreException {

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
