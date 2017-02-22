package tuwien.dbai.wpps.objident.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.PlatformUI;

@Deprecated
public class OpenFolderForComparisonCmdHdlr implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	// TODO implement!
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		DirectoryDialog dirDialog = new DirectoryDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), SWT.OPEN
				);
	    dirDialog.setText("Select folder");
	    String selectedDir = dirDialog.open();
//	    ObjIdentActivator.getDefSesCont().getCommonObjects().addMapping(
//	    		EProperty.CURRENT_OPENED_FOLDER
//	    		, new File(selectedDir));
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
		// TODO Auto-generated method stub

	}

}
