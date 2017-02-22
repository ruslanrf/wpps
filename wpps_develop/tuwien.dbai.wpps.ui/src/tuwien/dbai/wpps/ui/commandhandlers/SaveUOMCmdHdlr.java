package tuwien.dbai.wpps.ui.commandhandlers;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.OntologyWriter;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.WPPSConfigurationWriter;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.WPPSUISessionController;
import tuwien.dbai.wpps.ui.model.BrowserRelatedData;

public class SaveUOMCmdHdlr implements IHandler {
	private static final Logger log = Logger.getLogger(SaveUOMCmdHdlr.class);

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		DirectoryDialog dlg = new DirectoryDialog(new Shell(Display.getCurrent()));

        // Set the initial filter path according
        // to anything they've selected or typed in
//        dlg.setFilterPath(text.getText());

        // Change the title bar text
        dlg.setText("Save the UOM as...");

        // Customizable message displayed in the dialog
        dlg.setMessage("Select a directory to save the Unified Ontological Model together with its configuration.");

        // Calling open() will open and run the dialog.
        // It will return the selected directory, or
        // null if user cancels
        String dir = dlg.open();
        if (dir == null) {
if (log.isInfoEnabled()) log.info("Directory has not been selected.");
        	return null;
        }
		
		WPPSUISessionController sessionController = UIUtils.getService(WPPSUISessionController.class);
		EMBrowserEditor br = sessionController.getActiveBrowserEditor();
		if (br == null) {
if (log.isInfoEnabled()) log.info("There is no active browser");
return null;
		}
		BrowserRelatedData brd = sessionController.getDataContainer().getBrowserRelatedData(br);
		if (brd.getCurrState() == null) {
if (log.isInfoEnabled()) log.info("There is no UOM");
return null;
		}
		WPOntSubModels models = brd.getCurrState().getModels();
		OntologyWriter ow = new OntologyWriter(models);
		ow.write(new File(dir)); //"file://"+
		
		WPPSConfigurationWriter cw = new WPPSConfigurationWriter(brd.getCurrState().getConfig());
		cw.write(new File(dir));
		
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
