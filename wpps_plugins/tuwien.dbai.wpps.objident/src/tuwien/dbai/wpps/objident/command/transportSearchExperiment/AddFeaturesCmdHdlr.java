package tuwien.dbai.wpps.objident.command.transportSearchExperiment;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.objident.ObjIdentSessionController;
import tuwien.dbai.wpps.objident.model.ModelContainer;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TObjectExtended;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TSEModel;

public class AddFeaturesCmdHdlr implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ObjIdentSessionController sessionContr = UIUtils.getService(ObjIdentSessionController.class);
		ModelContainer modelContainer = sessionContr.getModelContainer();
		TSEModel tseModel = UIUtils.getService(TSEModel.class);
		TObject selObj = modelContainer.getBrowserRelatedModel(sessionContr.getActiveBrowserEditor())
			.getSelectedObject();
		if (selObj != null) {
			tseModel.addCollectedTObject(
					new TObjectExtended(selObj
							, tseModel.getScenario()
							, tseModel.getWebPageId()
							, tseModel.getWebFormAttrType())
					);
		}
		else
			System.err.println("selObj("+(selObj.getRdfTargetObject())+") == null");
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
