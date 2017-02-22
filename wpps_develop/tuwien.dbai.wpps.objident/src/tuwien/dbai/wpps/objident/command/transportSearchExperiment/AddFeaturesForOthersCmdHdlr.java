package tuwien.dbai.wpps.objident.command.transportSearchExperiment;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.objident.ObjIdentSessionController;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeaturesCalculationManager;
import tuwien.dbai.wpps.objident.lib.CoreStaticLib;
import tuwien.dbai.wpps.objident.lib.transportSearchExperiment.AddOthers;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModel;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectFactory;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.EWebFormAttributeType;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TObjectExtended;
import tuwien.dbai.wpps.objident.model.transportSearchExperiment.TSEModel;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;

public class AddFeaturesForOthersCmdHdlr implements IHandler {
	private static final Logger log = Logger.getLogger(AddFeaturesForOthersCmdHdlr.class);

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ObjidentConfig config = UIUtils.getService(ObjidentConfig.class);
		ObjIdentSessionController sessionController = UIUtils.getService(ObjIdentSessionController.class);
		EMBrowserEditor editor = sessionController.getActiveBrowserEditor();
		BrowserRelatedModel browserRelatedModel = sessionController.getModelContainer()
				.getBrowserRelatedModel(editor);
		
		if (browserRelatedModel.getFrameworkDependentInj() == null) {
	if (log.isTraceEnabled()) log.trace("START. Create web page dependent injector");
			Injector sessInj = UIUtils.getService(Injector.class);
						browserRelatedModel.setFrameworkDependentInj(
								CoreStaticLib.createFrameworkDependentInjector(sessInj
										, browserRelatedModel, config.getConsideredObjectJavaTypesAsArray()
										, config.getWPPSConfig()));
Preconditions.checkNotNull(browserRelatedModel.getFrameworkDependentInj());
if (log.isTraceEnabled()) log.trace("FINISH. Create web page dependent injector");
						}
		Injector inj = browserRelatedModel.getFrameworkDependentInj();

		TObjectFactory of = inj.getInstance(TObjectFactory.class);
		IIEBasisAPI api = inj.getInstance(IIEBasisAPI.class);
		FeaturesCalculationManager fcm = inj.getInstance(FeaturesCalculationManager.class);
		TSEModel model = UIUtils.getService(TSEModel.class);
		List<TObject> l = AddOthers.run(config, of, api, fcm, model.getCollectedUniqueTObjects());
		model.setWebFormAttrType(EWebFormAttributeType.other);
		for (TObject o : l) {
			model.addCollectedTObject(createExtObj(o, model));
		}
		return null;
	}

	private TObjectExtended createExtObj(TObject tObject, TSEModel model) {
		return new TObjectExtended(tObject, model.getScenario()
				, model.getWebPageId(), model.getWebFormAttrType());
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
