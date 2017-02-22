/**
 * File name: OpenBrowserCmdHandler.java
 * @created: Oct 12, 2011 3:31:55 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.embrowser.commands;

import org.apache.log4j.Logger;
import org.eclipse.atf.mozilla.ide.common.IWebBrowser;
import org.eclipse.atf.mozilla.ide.ui.MozIDEUIPlugin;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.embrowser.EMBrowserEditorInput;

/**
 * @created: Oct 12, 2011 3:31:55 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class OpenBrowserCmdHandler implements IHandler {
	
	public static final String ID = "tuwien.dbai.wpps.commands.openbrowser";

protected static final String ERROR_MSG = "Error opening Web Browser!";
	
	static public final Logger logger = Logger.getLogger(OpenBrowserCmdHandler.class);
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try{
			IWorkbenchWindow activeWindow= MozIDEUIPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
//			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
			
			if (activeWindow == null) {
				logger.error("Error opening Web Browser. Failed to retrieve active workbench window");
				throw new CoreException( new Status( IStatus.ERROR, MozIDEUIPlugin.PLUGIN_ID, IStatus.ERROR, "Error opening Web Browser... failed to retrieve active workbench window!", null ));
			}
			IWorkbenchPage activePage= activeWindow.getActivePage();
			if (activePage == null) {
				logger.error("Error opening Web Browser. Could not retrieve active page");
				throw new CoreException( new Status( IStatus.ERROR, MozIDEUIPlugin.PLUGIN_ID, IStatus.ERROR, "Error opening Web Browser... could not retrieve active page!", null ));
			}
			
			openMozillaBrowser( EMBrowserEditor.DEFAULT_URL, activePage );
//			openMozillaBrowser( "http://www.ya.ru", activePage );
if (logger.isTraceEnabled()) logger.trace("New web browser's tab has been opened");
		} catch (CoreException e) {
			showError( e.getStatus() );		
		}
		return null;
	}
	
	/**
	 * Open a Browser Editor to the specified URL and in the page provided
	 */
	protected static IWebBrowser openMozillaBrowser(String url, IWorkbenchPage page) throws CoreException {

		EMBrowserEditorInput editorInput = new EMBrowserEditorInput(url);

		IEditorPart editor = IDE.openEditor(page, editorInput, EMBrowserEditor.ID);

		if (editor instanceof EMBrowserEditor) {
			return ((EMBrowserEditor) editor);
		} else {
			logger.error("Error opening Web Browser. EditorPart did not initialize properly");
			throw new CoreException(new Status(IStatus.ERROR, MozIDEUIPlugin.PLUGIN_ID, IStatus.ERROR, "Error opening Web Browser... EditorPart did not initialize properly!", null));
		}
	}
	
	protected void showError( IStatus status ){
		try{
			ErrorDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), null, ERROR_MSG, status );
		}
		catch( Exception e ){
			//in case of an NPE getting the Active Workbench Window
		}
	}

	private boolean _isEnabled = true;
	@Override
	public boolean isEnabled() {
		return _isEnabled;
	}

	private boolean _isHandled = true;
	@Override
	public boolean isHandled() {
		return _isHandled;
	}

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
