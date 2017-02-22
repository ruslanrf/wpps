/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor;

import org.apache.log4j.Logger;
import org.eclipse.ui.IEditorPart;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIWebBrowser;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.nav.BrowserNavigatorProvider;
import tuwien.dbai.wpps.core.nav.IBrowserNavigator;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;

import com.google.common.eventbus.EventBus;

/**
 * Provided by {@linkplain BrowserNavigatorProvider}.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 10, 2012 4:05:33 PM
 */
public class BrowserNavigator implements IBrowserNavigator {
	private static final Logger log = Logger.getLogger(BrowserNavigator.class);

	// init as null
	private EventBus eventBus = new EventBus("BrowserNavigator event bus");
	
	private EMBrowserEditor dataSource;
	
	public BrowserNavigator() {
		this.dataSource = null;
	}
	
	public BrowserNavigator(EMBrowserEditor dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
	
	@Override
	public void setEventBus(EventBus eb) {
		this.eventBus = eb;
	}
	
	@Override
	public Object getTarget() {
		return dataSource;
	}
	
	public void setTarget(Object source) {
		this.dataSource = (EMBrowserEditor)source;
	}
	
	@Override
	public Object getData() {
		if (dataSource == null) {
//			eventBus.post(EBrowserNavigatorEvents.BEGIN_GET_DATA);
			// --- get main window ---
			final IEditorPart editor = UIUtils.getActiveEditor();
			if (editor == null) {
				throw new GeneralUncheckedException(log, "Open web browser!");
			}
			else
			if (editor instanceof EMBrowserEditor) {
				final nsIDOMWindow mainWindow = ((nsIWebBrowser)((EMBrowserEditor)editor)
						.getMozillaBrowser().getWebBrowser()).getContentDOMWindow();
				return mainWindow;
			}
			else
				throw new GeneralUncheckedException(log, "Active editor is not a web browser!");
		}
		else
			return ((nsIWebBrowser)((EMBrowserEditor)dataSource)
					.getMozillaBrowser().getWebBrowser()).getContentDOMWindow();
	}

	@Override
	public boolean go(String url) {
//		eventBus.post(EBrowserNavigatorEvents.BEGIN_GO);
		final IEditorPart editor = UIUtils.getActiveEditor();
		if (editor == null) {
			throw new GeneralUncheckedException(log, "Open web browser!");
		}
		else
		if (editor instanceof EMBrowserEditor) {
			((EMBrowserEditor)editor).goToURL(url);
			// TODO: add "wait" for loading
			return true;
		}
		else
			throw new GeneralUncheckedException(log, "Active editor is not a web browser!");
	}

	@Override
	public boolean init() {
//		eventBus.post(EBrowserNavigatorEvents.BEGIN_INIT);
		return true;
	}

	@Override
	public boolean deinit() {
//		eventBus.post(EBrowserNavigatorEvents.BEGIN_DEINIT);
		return true;
	}

}
