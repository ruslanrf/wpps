package tuwien.dbai.wpps.ui.commandhandlers;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.ui.WPPSUISessionController;

public class SetWPPSConfigFileCmdHdlr implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {

	}

	@Override
	public void dispose() {

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		FileDialog dialog = new FileDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.SAVE);
		dialog.setText("Select configuration file...");
		dialog.setFilterExtensions(new String[] { "*.xml" });
		dialog.setFilterNames(new String[] { "Extensible Markup Language (*.xml)" });
		
		String selectedFileName = dialog.open();
		if (selectedFileName != null)
		{
			UIUtils.getService(WPPSUISessionController.class)
				.getDataContainer().setWPPSConfigFile(new File(selectedFileName));
		}
		
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
