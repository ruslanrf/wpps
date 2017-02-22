/**
 * 
 */
package tuwien.dbai.wpps.objident;

import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.guava.IHasEventBus;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModel;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModelsManager;
import tuwien.dbai.wpps.objident.model.ModelContainer;
import tuwien.dbai.wpps.objident.model.ModelContainerManager;
import tuwien.dbai.wpps.objident.ui.app.ApplicationWorkbenchWindowAdvisor;
import tuwien.dbai.wpps.objident.ui.events.BrowserEditorEvent;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created May 31, 2012 1:25:39 AM
 */
public class ObjIdentSessionController implements IHasEventBus {
	private static final Logger log = Logger.getLogger(ObjIdentSessionController.class);
	
	private final EventBus eventBus;
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
	
	private final Injector sessionInjector;
	public Injector getSessionInjector() {
		return sessionInjector;
	}
//	private final CoreStaticLib coreLib;
	
	private final ObjidentConfig config;
	public ObjidentConfig getConfig() {
		return config;
	}
	
	private ModelContainerManager modelContainerManager = null;
	public ModelContainerManager getModelContainerManager() {
		return modelContainerManager;
	}
	public ModelContainer getModelContainer() {
		return modelContainerManager.getModelContainer();
	}
	public BrowserRelatedModelsManager getBrowserRelatedModelsManager() {
		return modelContainerManager.getBrowserRelatedModelsManager(); 
	}
	
	public ObjIdentSessionController() {
//		eventBus = new EventBus();
		eventBus = new AsyncEventBus("ObjIdent UI Event Bus",
				Executors.newCachedThreadPool());
		sessionInjector = Guice.createInjector(new SessionModule(eventBus));
		config = sessionInjector.getInstance(ObjidentConfig.class);
//		coreLib = new CoreStaticLib(sessionInjector);
	}
	
	/**
	 * Must be invoked in {@linkplain ApplicationWorkbenchWindowAdvisor#postWindowOpen()}
	 * Set listeners for events.
	 */
	public void init() {
		modelContainerManager = new ModelContainerManager();
		// listen for opening/closing parts (views and editors).
		PlatformUI.getWorkbench().getActiveWorkbenchWindow()
			.getPartService().addPartListener(partListener);
		eventBus.register(this);
	}
	
	// ==============================
	// Eclipse-based event listeners
	// ==============================
		
		/**
		 * Listener for opening-closing parts (editors, views).
		 */
		private final IPartListener partListener = new IPartListener() {
			private int windowIndex = 0;
//			private List<Image> imgList = new ArrayList<Image>(2);
//			private Map<EMBrowserEditor, Image> imgMap = new HashMap<EMBrowserEditor, Image>(2);
			@Override public void partActivated(IWorkbenchPart part) {}
			@Override
			public void partBroughtToTop(IWorkbenchPart part) {
				if (!(part instanceof EMBrowserEditor)) return; // accept only events from EMBrowserEditor 
if (log.isTraceEnabled()) log.trace("Browser editor "+part+" has been BroughtToTop");
				trackActivatedBrowserEditor((EMBrowserEditor)part);
				eventBus.post(BrowserEditorEvent.getInstance(BrowserEditorEvent.EventTypes.NEW_ACTIVE_EDITOR
						, ObjIdentSessionController.this, new Object[]{part}));
				
			}
			@Override public void partClosed(IWorkbenchPart part) {
//				if (imgMap.containsKey(part)) {
//					imgMap.get(part).dispose();
//					imgMap.remove(part);
//				}
			}
			@Override public void partDeactivated(IWorkbenchPart part) {}
			@Override public void partOpened(IWorkbenchPart part) {}
			private void trackActivatedBrowserEditor(EMBrowserEditor part) {
					switch (windowIndex) {
					case 0:
						getBrowserRelatedModelsManager().buildBrowserRelatedObjects(part, BrowserRelatedModel.EBrowserType.WEB_BROWSER_MASTER);
//						initBrowserWindow(part, getModelContainer()
//								.getMaserBrowserColor(), BrowserRelatedModel.EBrowserType.WEB_BROWSER_MASTER); //Gold. rgb(255,223,0) http://www.rapidtables.com/web/color/Gold_Color.htm
if (log.isTraceEnabled()) log.trace(BrowserRelatedModel.EBrowserType.WEB_BROWSER_MASTER);
						break;
					case 1:
						getBrowserRelatedModelsManager().buildBrowserRelatedObjects(part, BrowserRelatedModel.EBrowserType.WEB_BROWSER_SECOND);
//						initBrowserWindow(part, getModelContainer()
//								.getSecondBrowserColor(), BrowserRelatedModel.EBrowserType.WEB_BROWSER_SECOND); //Snow 4  http://www.tayloredmktg.com/rgb/
if (log.isTraceEnabled()) log.trace(BrowserRelatedModel.EBrowserType.WEB_BROWSER_SECOND);
						break;
					default:
						break;
					}
					windowIndex++;
			}
//			private void initBrowserWindow(EMBrowserEditor wb, RGB rgb, BrowserRelatedModel.EBrowserType browserType) {
//				Display d = PlatformUI.getWorkbench().getDisplay();
//				final Image img = new Image(d, 16, 16);
//				imgMap.put(wb, img);
//				GC gc = new GC(img);
//				gc.setBackground(new Color(d, rgb));
//				gc.fillRectangle(new org.eclipse.swt.graphics.Rectangle(0, 0, 16, 16));
//				gc.dispose();
//				wb.setTitleImage(img);
//				
//				getBrowserRelatedModelsManager().buildBrowserRelatedObjects(wb, browserType);
//			}
		};

		
		public EMBrowserEditor getActiveBrowserEditor() {
			IEditorPart part = UIUtils.getActiveEditor();
			if (part instanceof EMBrowserEditor)
				return (EMBrowserEditor) part;
			return null;
		}
		
		public boolean activeBrowserEditorExists() {
			return getActiveBrowserEditor() != null;
		}
	
	
}
