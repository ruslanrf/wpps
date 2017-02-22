/**
 * 
 */
package tuwien.dbai.wpps.ui;

import java.io.File;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import tuwien.dbai.wpps.colors.ColorGenerator;
import tuwien.dbai.wpps.core.methods.DefaultWPUMethodsDescriptionsManager;
import tuwien.dbai.wpps.core.methods.WPUMethodsFactory;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.guava.IHasEventBus;
import tuwien.dbai.wpps.ui.application.WPPSUIWorkbenchWindowAdvisor;
import tuwien.dbai.wpps.ui.commandhandlers.RunSelectedMethodsCmdHdlr;
import tuwien.dbai.wpps.ui.events.BrowserEditorRelatedEvent;
import tuwien.dbai.wpps.ui.model.BrowsersRelatedDataManager;
import tuwien.dbai.wpps.ui.model.DataContainer;
import tuwien.dbai.wpps.ui.model.SesDataContainerManager;
import tuwien.dbai.wpps.ui.utils.WPPSUILib;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

/**
 * Classes which access this class:
 * <ul>
 * <li>{@link RunSelectedMethodsCmdHdlr}</li>
 * </ul>
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 14, 2012 3:50:26 PM
 */
public class WPPSUISessionController implements IHasEventBus {
	private static final Logger log = Logger.getLogger(WPPSUISessionController.class);

	private final EventBus eventBus;
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
	
	private final File wppsConfigFile;
	public File getWPPSConfigFile() {
		return wppsConfigFile;
	}
	
	private final SesDataContainerManager dataContainerManager;
	public SesDataContainerManager getDataContainerManager() {
		return dataContainerManager;
	}
	public DataContainer getDataContainer() {
		return dataContainerManager.getDataContainer();
	}
	public BrowsersRelatedDataManager getBrowserRelatedDataManagers() {
		return dataContainerManager.getBrowserRelatedDataManagers(); 
	}
	
	private WPUMethodsFactory methodsFactory;
	public WPUMethodsFactory getWPUMethodsFactory() {
		return methodsFactory;
	}
	
	private final ColorGenerator colorGenerator;
	public ColorGenerator getColorGenerator() {
		return colorGenerator;
	}
	
	public WPPSUISessionController() {
		this.eventBus = new AsyncEventBus("WPPS UI Event Bus",
				Executors.newCachedThreadPool());
		this.wppsConfigFile = new File(WPPSUIActivator.getPluginFolder(), "config/wpps-config.xml");
		this.colorGenerator = new ColorGenerator();
		this.dataContainerManager = new SesDataContainerManager(
				new DefaultWPUMethodsDescriptionsManager(), this.colorGenerator, wppsConfigFile, eventBus);
		this.methodsFactory = new WPUMethodsFactory();
	}
	
//	private final BrowserRelatedDataManagers berdm;
//	/**
//	 * Current active browser {@linkplain EMBrowserEditor}.
//	 * variable is set when the event from Workbench is gotten;
//	 * {@linkplain IPartListener#partBroughtToTop(IWorkbenchPart)}.
//	 * @see #partListener
//	 */
//	private EMBrowserEditor activeBrowserEditor;
	
//	private final Mapping1Typed objects;
//	public Mapping1Typed getCommonObjects() {
//		return objects;
//	}
//	
//	private final Mapping2Typed objectsProps;
//	public Mapping2Typed getCommonObjectsProps() {
//		return objectsProps;
//	}
	
	public EMBrowserEditor getActiveBrowserEditor() {
		return WPPSUILib.getActiveBrowserEditorSoftDT();
	}
	
	public boolean activeBrowserEditorExists() {
		return WPPSUILib.getActiveBrowserEditorSoftDT() != null;
	}
	
	/**
	 * Must be invoked in {@linkplain WPPSUIWorkbenchWindowAdvisor#postWindowOpen()}
	 * Set listeners for events.
	 */
	public void activate() {
//if (log.isTraceEnabled()) log.trace("Activating Session Controller (setting listeners up)...");
		// listen for opening/closing parts (views and editors).
		PlatformUI.getWorkbench().getActiveWorkbenchWindow()
			.getPartService().addPartListener(partListener);
		eventBus.register(this);
//		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().addSelectionListener();
		// TODO: load methods!
		
//		DefaultWPUMethodsDescriptionsManager methodsDescriptionsManager = new DefaultWPUMethodsDescriptionsManager();
//		Set<AWPUMethodDescription> md = methodsDescriptionsManager.getMethodsDescriptions();
//		objects.addMapping(EProperty.AVAILABLE_METHODS_DESCRIPTIONS, md);
//		objects.addMapping(EProperty.METHODS_FACTORY, new WPUMethodsFactory());
		
//		_activateHelper();
		
	}

//	private void _activateHelper() {
//		// Set colors for method descriptions
//		final ColorGenerator cg = objects.getMappedObjectAs(EProperty.COLOR_GENERATOR, ColorGenerator.class);
//		for (Object m : objects.getMappedObjectAs(EProperty.AVAILABLE_METHODS_DESCRIPTIONS, Set.class)) {
//			objectsProps.addMapping(m, EProperty.COLOR, cg.getNextColor());
//		}
//	}
	
	// ==============================
	// Eclipse-based event listeners
	// ==============================
	
	/**
	 * Listener for opening-closing parts (editors, views).
	 */
	private final IPartListener partListener = new IPartListener() {
		@Override public void partActivated(IWorkbenchPart part) {
			if (!(part instanceof EMBrowserEditor)) return;
if (log.isTraceEnabled()) log.trace("Editor has been Activated");
		}
		@Override
		public void partBroughtToTop(IWorkbenchPart part) {
			if (!(part instanceof EMBrowserEditor)) return;
if (log.isTraceEnabled()) log.trace("Browser editor has been BroughtToTop");
trackActivatedBrowserEditor((EMBrowserEditor)part);
eventBus.post(BrowserEditorRelatedEvent.getInstance(BrowserEditorRelatedEvent.EventTypes.NEW_ACTIVE_EDITOR
			, WPPSUISessionController.this, new Object[]{part}));
		}
		@Override
		public void partClosed(IWorkbenchPart part) {
			if (!(part instanceof EMBrowserEditor)) return;
			dataContainerManager.getBrowserRelatedDataManagers()
				.disposeBrowserRelatedObjects((EMBrowserEditor)part);
			if (log.isTraceEnabled()) log.trace("Editor has been Closed");
		}
		@Override
		public void partDeactivated(IWorkbenchPart part) {
			if (!(part instanceof EMBrowserEditor)) return;
if (log.isTraceEnabled()) log.trace("Editor has been Deactivated");
		}
		@Override
		public void partOpened(IWorkbenchPart part) {
			if (!(part instanceof EMBrowserEditor)) return;
if (log.isTraceEnabled()) log.trace("Editor has been Opened");
		}
		/**
		 * Initialize data related to the editor opened.
		 * @param part
		 */
		private void trackActivatedBrowserEditor(EMBrowserEditor part) {
			if (!dataContainerManager.getBrowserRelatedDataManagers()
					.getStoredEMBrowserEditors().contains(part)) { // if there is no data about this browser
				dataContainerManager.getBrowserRelatedDataManagers()
					.buildBrowserRelatedObjects(part);
			}
		}
	};
	
	
	
}
