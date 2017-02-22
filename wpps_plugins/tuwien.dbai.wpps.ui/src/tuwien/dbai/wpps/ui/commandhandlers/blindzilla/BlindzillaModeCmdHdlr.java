package tuwien.dbai.wpps.ui.commandhandlers.blindzilla;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.handlers.HandlerUtil;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.ui.BlindzillaController;

public class BlindzillaModeCmdHdlr implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Command command = event.getCommand();
	     boolean oldValue = HandlerUtil.toggleCommandState(command);
	     UIUtils.getService(BlindzillaController.class).setMode(!oldValue);
	     
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}

}
