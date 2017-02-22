package tuwien.dbai.wpps.objident.command.transportSearchExperiment;

import java.util.Collections;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TSEModel;

public class CleanCollectionOfFeaturesCmdHdlr implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		TSEModel tseModel = UIUtils.getService(TSEModel.class);
		tseModel.setCollectedTObject(Collections.EMPTY_LIST);
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
