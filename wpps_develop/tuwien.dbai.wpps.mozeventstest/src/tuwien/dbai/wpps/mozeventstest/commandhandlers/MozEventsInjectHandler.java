package tuwien.dbai.wpps.mozeventstest.commandhandlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.IEditorPart;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.mozeventstest.browser.EMBrowserEditor;
import tuwien.dbai.wpps.mozeventstest.browser.TestBrowerListener;

public class MozEventsInjectHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	private boolean isEnabled = true;
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IEditorPart editor = UIUtils.getActiveEditor();
		if (editor instanceof EMBrowserEditor)
		{
			TestBrowerListener tbl = new TestBrowerListener(((EMBrowserEditor)editor));
			tbl.init();
			isEnabled = false;
//			ICommandService.refreshElements();
//			HandlerUtil.toggleCommandState(this);
		}
		
		return null;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}

}
