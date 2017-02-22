package tuwien.dbai.wpps.objident.command;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.objident.ObjIdentSessionController;
import tuwien.dbai.wpps.objident.lib.PlainCSVFeaturesSimpleWriter;
import tuwien.dbai.wpps.objident.lib.PlainCSVFeaturesWriter2;
import tuwien.dbai.wpps.objident.model.ModelContainer;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 25, 2012 7:11:01 PM
 * @see PlainCSVFeaturesSimpleWriter
 */
public class SaveComparisonObjectsFeaturesCmdHdlr implements IHandler {

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
		dialog.setText("Select file...");
		dialog.setFilterExtensions(new String[] { "*.csv" });
		dialog.setFilterNames(new String[] { "Comma-separated values (*.csv)" });
		
		String selectedFileName = dialog.open();
		if (selectedFileName != null)
		{
			File selectedFile = new File(selectedFileName);
			ModelContainer modelContainer = UIUtils.getService(ObjIdentSessionController.class).getModelContainer();
			// PlainCSVFeaturesSimpleWriter
			PlainCSVFeaturesWriter2.write(selectedFile
					, modelContainer.getComparationList() );
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
