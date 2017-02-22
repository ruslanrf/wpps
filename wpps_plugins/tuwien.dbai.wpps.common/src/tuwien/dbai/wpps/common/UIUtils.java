/**
 * File name: UIUtils.java
 * @created: Sep 14, 2011 3:27:56 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.IHandlerService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import tuwien.dbai.wpps.common.callback.ICommand;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;


/**
 * @created: Sep 14, 2011 3:27:56 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public final class UIUtils {

	public static IEditorPart getActiveEditor() {
		final IWorkbenchWindow activeWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		final IWorkbenchPage page = activeWindow.getActivePage();
		final IEditorPart editor = page.getActiveEditor();
		return editor;
	}
	
	public static IViewPart getOpenedView(String id) {
		IViewReference viewReferences[] = PlatformUI.getWorkbench()
		.getActiveWorkbenchWindow().getActivePage().getViewReferences();
		for (int i = 0; i < viewReferences.length; i++) {
		if (id.equals(viewReferences[i].getId())) {
		return viewReferences[i].getView(false);
		}
		}
		return null;
	}
	
	/**
	 * @param name can be name of command for instance.
	 * @return
	 */
	public static final IHandlerService getHandlerService() {
		return (IHandlerService)PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().getActivePart().getSite().getService(IHandlerService.class);
	}
	
	public static final void executeCommand(final String name) {
		try {
			getHandlerService().executeCommand(name, null);
    	  } catch (Exception ex) {
    	    throw new RuntimeException("add.command not found");
    	    }
	}
	
	public static final boolean currThreadIsGUIThread() {
		return PlatformUI.getWorkbench().getDisplay().getThread().equals(Thread.currentThread());
	}
	
	public static final void runInGUIThreadSync(final ICommand cmd) {
		if (currThreadIsGUIThread()) {
			cmd.run();
		} else {
			PlatformUI.getWorkbench().getDisplay().syncExec(
				new Runnable() {
					@Override public void run() {
						cmd.run();
					} } );
		}
	}
	
	public static final void runInGUIThreadAsync(final ICommand cmd) {
		PlatformUI.getWorkbench().getDisplay().asyncExec(
			new Runnable() {
				@Override public void run() {
					cmd.run();
				} } );
	}
	
	public static <T> T getService(Class<?> bundleClass, Class<T> c) {
		BundleContext ctx = FrameworkUtil.getBundle(bundleClass).getBundleContext();
		ServiceReference<T> sr = ctx.getServiceReference(c);
		if (sr == null)
			throw new GeneralUncheckedException("Service "+c.getCanonicalName()+" cannot be found");
		return ctx.getService(sr);
	}
	
	public static <T> T getService(Class<T> c) {
		BundleContext ctx = FrameworkUtil.getBundle(c).getBundleContext();
		ServiceReference<T> sr = ctx.getServiceReference(c);
		if (sr == null)
			throw new GeneralUncheckedException("Service "+c.getCanonicalName()+" cannot be found");
		return ctx.getService(sr);
	}
	
}
