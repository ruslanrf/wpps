/**
 * File name: MozEventsPlayHandler.java
 * @created: Oct 7, 2011 8:17:24 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.mozeventstest.commandhandlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorPart;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.mozeventstest.browser.EMBrowserEditor;
import tuwien.dbai.wpps.mozeventstest.browser.PlayTest;
import tuwien.dbai.wpps.mozeventstest.browser.TestBrowerListener;

/**
 * @created: Oct 7, 2011 8:17:24 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class MozEventsPlayHandler implements IHandler {

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#addHandlerListener(org.eclipse.core.commands.IHandlerListener)
	 */
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart editor = UIUtils.getActiveEditor();
		if (editor instanceof EMBrowserEditor)
		{
			PlayTest tbl = new PlayTest(((EMBrowserEditor)editor));
			tbl.play();
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#isHandled()
	 */
	@Override
	public boolean isHandled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#removeHandlerListener(org.eclipse.core.commands.IHandlerListener)
	 */
	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
