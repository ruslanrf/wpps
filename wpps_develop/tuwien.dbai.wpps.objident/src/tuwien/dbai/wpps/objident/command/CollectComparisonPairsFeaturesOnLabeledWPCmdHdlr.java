package tuwien.dbai.wpps.objident.command;

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
import tuwien.dbai.wpps.objident.features.TObjectComparativePair;
import tuwien.dbai.wpps.objident.lib.CoreStaticLib;
import tuwien.dbai.wpps.objident.lib.LabeledWebDocumentAnalyzer;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModel;
import tuwien.dbai.wpps.objident.model.ModelContainer;
import tuwien.dbai.wpps.objident.model.TObjectFactory;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;

public class CollectComparisonPairsFeaturesOnLabeledWPCmdHdlr implements IHandler {
	private static final Logger log = Logger.getLogger(CollectComparisonPairsFeaturesOnLabeledWPCmdHdlr.class);

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
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

		log.trace("s24ed: "+1);
		TObjectFactory of = inj.getInstance(TObjectFactory.class);
		log.trace("s24ed: "+2);
		IIEBasisAPI api = inj.getInstance(IIEBasisAPI.class);
		
		log.trace("s24ed: "+3);
		
		List<TObjectComparativePair> pairs = LabeledWebDocumentAnalyzer
				.getListOfComparativePairs(config, editor, of, api);
		log.trace("s24ed: "+4);
		
		FeaturesCalculationManager fcm = inj.getInstance(FeaturesCalculationManager.class);
		log.trace("s24ed: "+5);
		ModelContainer mc = sessionController.getModelContainer();
		log.trace("s24ed: "+6);
		LabeledWebDocumentAnalyzer.applyCollectedComparativePairs(pairs, fcm, mc);
		log.trace("s24ed: "+7);
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
