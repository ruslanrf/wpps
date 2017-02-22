/**
 * 
 */
package tuwien.dbai.wpps.objident.model;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 2, 2012 6:35:36 PM
 */
public class ModelContainerManager {

//	@SuppressWarnings("unused")
//	private final EventBus eventBus;
	
//	@SuppressWarnings("unused")
//	private final Injector sessionInjector;
	
//	private final CoreStaticLib coreLib;
	
	private final ModelContainer modelContainer;
	
	private final BrowserRelatedModelsManager browserRelatedModelsManager;
	
	public ModelContainerManager(
//			Injector sessionInjector
//			, EventBus eventBus
//			, CoreStaticLib coreLib
			) {
//		this.sessionInjector = sessionInjector;
//		this.eventBus = eventBus;
//		this.coreLib = coreLib;
//		eventBus.register(this);
		modelContainer = new ModelContainer();
		browserRelatedModelsManager = new BrowserRelatedModelsManager(modelContainer);
	}
	
	public ModelContainer getModelContainer() {
		return modelContainer;
	}
	
	public BrowserRelatedModelsManager getBrowserRelatedModelsManager() {
		return browserRelatedModelsManager; 
	}
	
	// GOOGLE LISTENER
	
}
