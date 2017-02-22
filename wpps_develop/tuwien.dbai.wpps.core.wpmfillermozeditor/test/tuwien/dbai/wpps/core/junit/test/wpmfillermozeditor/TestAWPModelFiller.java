/**
 * 
 */
package tuwien.dbai.wpps.core.junit.test.wpmfillermozeditor;

import org.eclipse.atf.mozilla.ide.common.IWebBrowser;
import org.eclipse.atf.mozilla.ide.ui.MozIDEUIPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.ide.IDE;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tuwien.dbai.wpps.core.module.InstanceAdaptersModule;
import tuwien.dbai.wpps.core.module.OntologyModule;
import tuwien.dbai.wpps.core.module.WPPSConfigModule;
import tuwien.dbai.wpps.core.wpmodel.ontology.factory.AWPModelFiller;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.embrowser.EMBrowserEditorInput;

import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 28, 2011 11:33:17 PM
 */
public class TestAWPModelFiller {

	Stopwatch stopwatch = new Stopwatch();
	
	Injector inj = null;
	
	AWPModelFiller filler = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println( "=== setUp() ===");
		
		stopwatch.start();
		
		inj = Guice.createInjector(new InstanceAdaptersModule(), new OntologyModule(), new WPPSConfigModule());
		
		filler = inj.getInstance(AWPModelFiller.class);
		
		openBrowserEditor();
		
		System.out.println("time elapsed:"+stopwatch);
	}
	
//	private void openBrowserEditor() {
//	// --- open browser ---
//			IWorkbenchWindow activeWindow= //PlatformUI.
//			MozIDEUIPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
//			IWorkbenchPage activePage= activeWindow.getActivePage();
//			EMBrowserEditorInput editorInput = new EMBrowserEditorInput("");
//			IEditorPart editor = null;
//			try {
//				editor = IDE.openEditor(
//						activePage,
//						editorInput,
//						BLBrowserEditor.ID);
//			} catch (PartInitException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			// ---
//	}
	
	private void openBrowserEditor() {
		// --- open browser ---
		try{
			IWorkbenchWindow activeWindow= MozIDEUIPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow();
	//		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
			
			if (activeWindow == null) {
//				logger.error("Error opening Web Browser. Failed to retrieve active workbench window");
				throw new CoreException( new Status( IStatus.ERROR, MozIDEUIPlugin.PLUGIN_ID, IStatus.ERROR, "Error opening Web Browser... failed to retrieve active workbench window!", null ));
			}
			IWorkbenchPage activePage= activeWindow.getActivePage();
			if (activePage == null) {
//				logger.error("Error opening Web Browser. Could not retrieve active page");
				throw new CoreException( new Status( IStatus.ERROR, MozIDEUIPlugin.PLUGIN_ID, IStatus.ERROR, "Error opening Web Browser... could not retrieve active page!", null ));
			}
			
//			openMozillaBrowser( EMBrowserEditor.DEFAULT_URL, activePage );
			openMozillaBrowser( "file:///home/ruslan/workspaces/WPPS_dev/input/frames.html", activePage );
//			logger.trace("New web browser's tab has been opened");
		} catch (CoreException e) {
//			showError( e.getStatus() );
			e.printStackTrace();
		}
	}
	
	protected static IWebBrowser openMozillaBrowser(String url, IWorkbenchPage page) throws CoreException {

		EMBrowserEditorInput editorInput = new EMBrowserEditorInput(url);
		
		IEditorPart editor = IDE.openEditor(page, editorInput, EMBrowserEditor.ID);
		
		System.err.println("Web Browser should be oppend now.");

		if (editor instanceof EMBrowserEditor) {
			return ((EMBrowserEditor) editor);
		} else {
//			logger.error("Error opening Web Browser. EditorPart did not initialize properly");
			throw new CoreException(new Status(IStatus.ERROR, MozIDEUIPlugin.PLUGIN_ID, IStatus.ERROR, "Error opening Web Browser... EditorPart did not initialize properly!", null));
		}
	}
	


	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.ontology.factory.AWPModelFiller#fill()}.
	 */
	@Test
	public void testFill() {
		System.out.println( "=== testFill() ===");
		
		stopwatch.reset();stopwatch.start();
		
		inj.injectMembers(filler);
		
		Assert.assertTrue(filler.isInited());
		
		filler.fill();
		Assert.assertTrue(filler.isFilled());
		
		System.out.println("time elapsed:"+stopwatch);
		
		stopwatch.reset();stopwatch.start();
		
//		while (stopwatch.elapsedMillis() < 10000)
//			;
		
		
	}

}
