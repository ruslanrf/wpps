package tuwien.dbai.wpps.objident.ui.app;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.embrowser.EMBrowserUtil;
import tuwien.dbai.wpps.objident.ObjIdentSessionController;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(800, 600));
        configurer.setShowCoolBar(true);
		configurer.setShowStatusLine(true);
		configurer.setShowFastViewBars(true);
		configurer.setShowMenuBar(true);
        configurer.setTitle("Object Identification"); //$NON-NLS-1$
    }
    
    @Override
    public void postWindowOpen() {
    	UIUtils.getService(ObjIdentSessionController.class).init();
    	// --- open browser ---
    	initWebBrowser(); //Gold. rgb(255,223,0) http://www.rapidtables.com/web/color/Gold_Color.htm
    	initWebBrowser(); //Snow 4  http://www.tayloredmktg.com/rgb/
		// ---
    }
    
    private EMBrowserEditor initWebBrowser() {
    	try {
			EMBrowserEditor wb = EMBrowserUtil.openMozillaBrowser();
			return wb;
		} catch (CoreException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
}
