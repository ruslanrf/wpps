package tuwien.dbai.wpps.objident.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.objident.ObjIdentSessionController;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair.EExampleType;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModel;
import tuwien.dbai.wpps.objident.model.ModelContainer;
import tuwien.dbai.wpps.objident.model.TObject;

public class AddComparisonPairFeaturesCmdHdlr implements IHandler {
//	private static final Logger log = Logger.getLogger(AddExampleCmdHdlr.class);
	
//	public AddExampleCmdHdlr() {
//		ObjIdentActivator.getDefSesCont().getEventBus().register(this);
//	}
	
//	private TObject masterObject = null;
//	private TObject comparativeObject = null;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ObjIdentSessionController sessionContr = UIUtils.getService(ObjIdentSessionController.class);
		ModelContainer modelContainer = sessionContr.getModelContainer();
		
		BrowserRelatedModel brmMaser = modelContainer
				.getBrowserRelatedModel(BrowserRelatedModel.EBrowserType.WEB_BROWSER_MASTER);
		BrowserRelatedModel brmSecond = modelContainer
				.getBrowserRelatedModel(BrowserRelatedModel.EBrowserType.WEB_BROWSER_SECOND);
		
		TObject masterObject = brmMaser.getSelectedObject();
		TObject comparativeObject = brmSecond.getSelectedObject();		
				
		if (masterObject != null && comparativeObject != null) {
//			TColor masterColor = brmMaser.getMasterExampleColor();
//			TColor secondColor = brmSecond.getNegativeExampleColor();
	    	EExampleType exType = EExampleType.NEGATIVE;
	    	if (EExampleType.POSITIVE ==
	    			modelContainer.getComparationObjectSelectionMode()) {
	    		exType = EExampleType.POSITIVE;
//	    		secondColor = brmSecond.getPositiveExampleColor();
	    	}
	    	
	    	modelContainer.addComparativePair2(new TObjectComparativePair(masterObject, comparativeObject, exType
//	    				, brmMaser.getBrowserEditor(), brmSecond.getBrowserEditor()
	    				));
	    	
//	    	EMBrowserManager bm = ObjIdentActivator.getDefSesCont().getBrowserRelatedModelsManager()
//	    			.getBrowserManager(EBrowserType.WEB_BROWSER_MASTER);
//	    	bm.highlightObject(masterObject.getArea()
//	    			, CoreStaticLib.getNsIDOMDocumentForTObject(masterObject, brmMaser.getFrameworkDependentInj()).getDocumentElement()
//	    			, masterColor);
//	    	
//	    	bm = ObjIdentActivator.getDefSesCont().getBrowserRelatedModelsManager()
//	    			.getBrowserManager(EBrowserType.WEB_BROWSER_SECOND);
//	    	bm.highlightObject(comparativeObject.getArea()
//	    			, CoreStaticLib.getNsIDOMDocumentForTObject(comparativeObject, brmSecond.getFrameworkDependentInj()).getDocumentElement()
//	    			, secondColor);
		}
		return null;
	}
	
//	@Subscribe
//	public void _processObjFeatureEvents(MainEvents e) {
//		
//		switch (e.getEventType()) {
//		case TOBJECT_SELECTION: {
//			switch (((EMBrowserManager)e.getData()[0]).getBrowserType()) {
//			case WEB_BROWSER_MASTER:
//				masterObject = null;
//				break;
//			case WEB_BROWSER_SECOND:
//				comparativeObject = null;
//				break;
//			default:
//				log.warn("Wrong value: "+((EMBrowserManager)e.getData()[0]).getBrowserType());
//			}
//			break;
//		}
//		case FEATURES_COMPUTED: {
//			switch (((EMBrowserManager)e.getData()[0]).getBrowserType()) {
//			case WEB_BROWSER_MASTER:
//				masterObject = ((TObject)e.getData()[1]);
//				break;
//			case WEB_BROWSER_SECOND:
//				comparativeObject = ((TObject)e.getData()[1]);
//				break;
//			default:
//				log.warn("Wrong value: "+((EMBrowserManager)e.getData()[0]).getBrowserType());
//			}
//			break;
//		}
//		}
//	}
	
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

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
