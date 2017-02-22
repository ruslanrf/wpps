/**
 * 
 */
package tuwien.dbai.wpps.objident.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModel.EBrowserType;
import tuwien.dbai.wpps.objident.ui.browser.EMBrowserManager;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 2, 2012 6:52:09 PM
 */
public class BrowserRelatedModelsManager {
	
//	private final EventBus eventbus;
	
//	private final Injector sessionInjector;
	
//	private final CoreStaticLib coreLib;
	
	private final ModelContainer modelContainer;
	
	public BrowserRelatedModelsManager(
			ModelContainer modelContainer
//			, EventBus eventbus
//			, Injector sessionInjector
//			, CoreStaticLib coreLib
			) {
		this.modelContainer = modelContainer;
//		this.eventbus = UIUtils.getService(ObjIdentActivator.class, EventBus.class);
//		this.sessionInjector = UIUtils.getService(ObjIdentActivator.class, Injector.class);
//		this.coreLib = coreLib;
	}
	
	private Map<EMBrowserEditor, EMBrowserManager> emBrowserManagerMap
		= new HashMap<EMBrowserEditor, EMBrowserManager>();
	public EMBrowserManager getEMBrowserManager(EMBrowserEditor browser) {
		return emBrowserManagerMap.get(browser);
	}
	
//	@Deprecated
//	public void buildBrowserRelatedObjects(EMBrowserEditor browserEditor
//			, BrowserRelatedModel.EBrowserType browserType) {
//		RGB browserColor = modelContainer.getSecondBrowserColor();
//		if (browserType == EBrowserType.WEB_BROWSER_MASTER)
//			browserColor = modelContainer.getMaserBrowserColor();
//		BrowserRelatedModel brm = new BrowserRelatedModel(browserEditor, browserType, browserColor);
//		modelContainer.addBrowserRelatedModel(brm);
//		emBrowserManagerMap.put(browserEditor
//				, new EMBrowserManager(brm, eventbus, sessionInjector));
//	}
	
	public void buildBrowserRelatedObjects(EMBrowserEditor browserEditor
			, BrowserRelatedModel.EBrowserType browserType) {
		// --- set browser editor image
		Display d = PlatformUI.getWorkbench().getDisplay();
		final Image img = new Image(d, 16, 16);
		GC gc = new GC(img);
		RGB browserColor = modelContainer.getSecondBrowserColor();
		if (browserType == EBrowserType.WEB_BROWSER_MASTER)
			browserColor = modelContainer.getMaserBrowserColor();
		gc.setBackground(new Color(d, browserColor));
		gc.fillRectangle(new org.eclipse.swt.graphics.Rectangle(0, 0, 16, 16));
		gc.dispose();
		browserEditor.setTitleImage(img);
		// --- init browser related objects
		BrowserRelatedModel brm = new BrowserRelatedModel(browserEditor, browserType, browserColor
//			, CoreStaticLib.createFrameworkDependentInjector(
//					UIUtils.getService(Injector.class)
//					, browserEditor)
		);
		
		modelContainer.addBrowserRelatedModel(brm);
		emBrowserManagerMap.put(browserEditor, new EMBrowserManager(brm));
	}
	
	public EMBrowserManager getBrowserManager(EBrowserType browserType) {
		return emBrowserManagerMap.get(modelContainer
				.getBrowserRelatedModel(browserType).getBrowserEditor());
	}

}
