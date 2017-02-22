package tuwien.dbai.wpps.objident.command.transportSearchExperiment;

import java.io.File;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.PlatformUI;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.objident.lib.transportSearchExperiment.PlainCSVFeaturesWriter;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TObjectExtended;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TSEModel;

public class SaveFeaturesCmdHdlr implements IHandler {

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
			List<TObjectExtended> oCol = UIUtils.getService(TSEModel.class).getCollectedUniqueTObjects();
			PlainCSVFeaturesWriter.write(selectedFile, oCol);
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
