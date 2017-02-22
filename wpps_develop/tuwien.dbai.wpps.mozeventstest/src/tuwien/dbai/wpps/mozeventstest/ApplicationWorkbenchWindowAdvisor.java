package tuwien.dbai.wpps.mozeventstest;

import org.eclipse.atf.mozilla.ide.ui.MozIDEUIPlugin;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.ide.IDE;

import tuwien.dbai.wpps.mozeventstest.browser.EMBrowserEditor;
import tuwien.dbai.wpps.mozeventstest.browser.EMBrowserEditorInput;

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
        configurer.setShowStatusLine(false);
        configurer.setTitle("Test Browser Events"); //$NON-NLS-1$
    }
    
    @Override
    public void postWindowOpen() {
// --- open browser ---
		IWorkbenchWindow activeWindow= //PlatformUI.
		MozIDEUIPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage activePage= activeWindow.getActivePage();
		EMBrowserEditorInput editorInput = new EMBrowserEditorInput("");
		IEditorPart editor = null;
		try {
			editor = IDE.openEditor(
					activePage,
					editorInput,
					EMBrowserEditor.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		// ---
    }
}
