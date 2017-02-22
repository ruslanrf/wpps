/**
 * 
 */
package tuwien.dbai.wpps.ui.commandhandlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.core.WPPSFramework;
import tuwien.dbai.wpps.core.methods.AWPUMethod;
import tuwien.dbai.wpps.core.methods.WPUMethodsExecutor;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.WPPSUISessionController;
import tuwien.dbai.wpps.ui.model.BrowserRelatedData;
import tuwien.dbai.wpps.ui.model.DataContainer;
import tuwien.dbai.wpps.ui.model.WPUMethodAdp;

/**
 * Has full access to {@linkplain WPPSUISessionController}.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 20, 2012 6:55:30 PM
 */
public class RunSelectedMethodsCmdHdlr implements IHandler {
	private static final Logger log = Logger.getLogger(RunSelectedMethodsCmdHdlr.class);
	
	private final WPPSUISessionController sessionController;
	private final DataContainer dataContainer;
	
	public RunSelectedMethodsCmdHdlr() {
		// get session data
//		BundleContext ctx = FrameworkUtil.getBundle(MethodsListView.class).getBundleContext();
//		ServiceReference<WPPSUISessionController> sr = ctx.getServiceReference(WPPSUISessionController.class);
//		if (sr == null)
//			throw new GeneralUncheckedException(log, "Service "+WPPSUISessionController.class+" cannot be found");
//		sessionController = ctx.getService(sr);
		sessionController = UIUtils.getService(WPPSUISessionController.class);
		
		dataContainer = sessionController.getDataContainer();
	}
	
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	// TODO: implement this in some library-like class.
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// We expect this code running in UI thread
		if (!sessionController.activeBrowserEditorExists()) { // there is no editor
if (log.isDebugEnabled()) log.debug("There is no browser editor");
			return null;
		}
		EMBrowserEditor browser = sessionController.getActiveBrowserEditor();
		BrowserRelatedData berd = dataContainer.getBrowserRelatedData(browser);
		if (berd.getCurrState() != null) { // methods were invoked? there is no results {
log.debug("Cannot run methods because they has been executed before");
			return null;
	}
		Set<WPUMethodAdp> mSet = berd.getMethodSet();
		// TODO: order of methods selected
		Collection<AWPUMethod> mExe = new ArrayList<AWPUMethod>(mSet.size());
		for (WPUMethodAdp m : mSet) {
			if (m.isSelected()) {
				if ( !m.hasContent() ) {
					m.setContent(sessionController.getWPUMethodsFactory()
							.createMethod(m.getDescription().getDescription()) );
				}
				mExe.add(m.getContent());
			}
		}
		
		if (mExe.size()>0) {
			WPPSFramework wppsFramework = berd.getFramework();
			if (wppsFramework == null) {
				wppsFramework = new WPPSFramework(browser, dataContainer.getWPPSConfigFile());
				berd.setFramework(wppsFramework);
			}
			WPUMethodsExecutor me = new WPUMethodsExecutor(berd.getFramework(), mExe);
			me.run();
			// final state is the current state
if (log.isTraceEnabled()) log.trace("START. setMethodInvokationResults2");
			berd.setMethodInvokationResults2(me.getResult(), me.getFinalState());
if (log.isTraceEnabled()) log.trace("FINISH. setMethodInvokationResults2");
		}
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
	}

}
