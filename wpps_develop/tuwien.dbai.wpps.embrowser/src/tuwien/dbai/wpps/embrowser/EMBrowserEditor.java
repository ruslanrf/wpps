/**
 * File name: EMBrowserEditor.java
 * @created: Oct 12, 2011 12:13:37 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.embrowser;

import org.apache.log4j.Logger;
import org.eclipse.atf.mozilla.ide.common.IDOMNodeSelection;
import org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditorInput;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIRequest;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIURI;
import org.mozilla.interfaces.nsIWebBrowser;
import org.mozilla.interfaces.nsIWebProgress;
import org.mozilla.interfaces.nsIWebProgressListener;
import org.mozilla.xpcom.Mozilla;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.UnappropriateTypeOfArgument;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.embrowser.addons.WebDocumentSelectionBoxManager;
import tuwien.dbai.wpps.guava.IHasEventBus;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;

/**
 * Web browser editor based on the mozilla browser.
 * Class is a subclass of MozBrowserEditor from the
 * <a href="http://www.eclipse.org/atf/">ATF project</a>.
 * 
 * 
 * @created: Oct 12, 2011 12:13:37 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class EMBrowserEditor
	extends org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditor
	implements IHasEventBus {
	
	private static final Logger log = Logger.getLogger(EMBrowserEditor.class);
	
	public static final String ID = "tuwien.dbai.wpps.editors.embrowser";
	
	private EventBus eventBus = new EventBus("EMBrowserEditor event bus");
	
	/**
	 * Selection Box Manager to control manual selection on the web page using functions
	 * {@link #showSelection(Rectangle2D, String, nsIDOMDocument)},
	 * {@link #hideSelection(Rectangle2D, nsIDOMDocument)},
	 * {@link #flashSelection(Rectangle2D, String, nsIDOMDocument)}.
	 */
	private WebDocumentSelectionBoxManager webDocSelBoxManager = null;
	
	/**
	 * Progress listener which is associated with interface 
	 * {@linkplain nsIWebProgressListener}.
	 */
	private final WebProgressListener webProgressListener = new WebProgressListener();
	
	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
	private void postEvent(EMBrowserEvent event) {
		if (eventBus != null)
			eventBus.post(event);
	}

	/**
	 * Constructor.
	 */
	public EMBrowserEditor() {
		super();
		// We create our listener for the openning new window to create instance of our browser ({@linkplain EMBrowserEditor}).
		super.openManager = new EMPopupWindowBrowserListener(
			new IShellProvider() {
				public Shell getShell() {
					return EMBrowserEditor.this.getSite().getShell();
				}
			}); 
		// we will use our listener instead of standard one.
		super.browserListener = new EMBrowerListener();
		
	}
	
	@Override
	public void init(final IEditorSite site, final IEditorInput input) throws PartInitException {
		super.init(site, input);
if (log.isInfoEnabled()) log.info(TSForLog.getTS(log)+"Browser has been initialized.");
		postEvent(EMBrowserEvent.getInstance(EMBrowserEvent.EventTypes.BROWSER_INITIALISED
				, EMBrowserEditor.this, new Object[]{}));
	}
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		// --- add selection listener
		EMBrowserEditor.this.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (event.getSelection() instanceof IDOMNodeSelection) {
					final nsIDOMNode node = ((IDOMNodeSelection)event.getSelection()).getSelectedNode();
					if (node != null) { // can be null!
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Node "+node+" ("+node.getNodeName()+", "+node.getNodeValue()+") has been selected.");
						postEvent(EMBrowserEvent.getInstance(EMBrowserEvent.EventTypes.ELEMENT_NODE_SELECTED
								, EMBrowserEditor.this, new Object[]{node}));
					}
				}
			}
		});
		// --- add progress listener
		final nsIWebBrowser webBrowser = (nsIWebBrowser)EMBrowserEditor.this.getMozillaBrowser().getWebBrowser();
		webBrowser.addWebBrowserListener(webProgressListener, nsIWebProgressListener.NS_IWEBPROGRESSLISTENER_IID);
		
	}
	
	/**
	 * Overriden because of function init.
	 * To prevent setting an input of class  MozBrowserEditorInput to the browser.
	 */
	@Override
	protected void setInput(IEditorInput input) {
		EMBrowserEditorInput bInput = null;
		if (input instanceof EMBrowserEditorInput)
			bInput = (EMBrowserEditorInput)input;
		else if (input instanceof MozBrowserEditorInput) {
			bInput = new EMBrowserEditorInput((MozBrowserEditorInput)input);
		}
		else {
			throw new UnappropriateTypeOfArgument(log, input.getClass(), EMBrowserEditorInput.class);
		}
		super.setInput(bInput);
	}
	
	
	protected class EMBrowerListener
	extends org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditor.MozillaBrowserListener {
		@Override
		public void init() {
			getMozillaBrowser().addLocationListener(this);
			getMozillaBrowser().addStatusTextListener(this);
			getMozillaBrowser().addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					getMozillaBrowser().removeLocationListener(EMBrowerListener.this);
					getMozillaBrowser().removeStatusTextListener(EMBrowerListener.this);
					getMozillaBrowser().removeDisposeListener(this);
				}
			});
		}
		@Override
		public void changed(LocationEvent event) {
			super.changed(event);
			webDocSelBoxManager = null;
if (log.isInfoEnabled()) log.info(TSForLog.getTS(log)+"URL has been changed to "+event.location);
			postEvent(EMBrowserEvent.getInstance(EMBrowserEvent.EventTypes.LOCATION_CHANGED
					, EMBrowserEditor.this, new Object[]{event.location}));
			// -- sett title as URL during the load. After that super class will change the title to the title of web page.
			EMBrowserEditor.super.setPartName(event.location);
		}
		@Override
		public void completed(ProgressEvent event) {
			super.completed(event);
			if (webDocSelBoxManager == null)
				webDocSelBoxManager = new WebDocumentSelectionBoxManager(getDocument());
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"Page loading is complete: "+EMBrowserEditor.this.getMozillaBrowser().getUrl());
			postEvent(EMBrowserEvent.getInstance(EMBrowserEvent.EventTypes.LOADING_COMPLETE
					, EMBrowserEditor.this, new Object[]{EMBrowserEditor.this.getMozillaBrowser().getUrl()}));
		}
	}
	
	/**
	 * Class responsable for listening progress of loads in the web browser.
	 */
	private class WebProgressListener implements nsIWebProgressListener {
		@Override
		public void onLocationChange(nsIWebProgress arg0, nsIRequest arg1,
				nsIURI arg2) {}
		@Override
		public void onProgressChange(nsIWebProgress arg0, nsIRequest arg1,
				int arg2, int arg3, int arg4, int arg5) {}
		@Override
		public void onSecurityChange(nsIWebProgress arg0, nsIRequest arg1,
				long arg2) {}
		@Override
		public void onStateChange(nsIWebProgress arg0, nsIRequest arg1,
				long arg2, long arg3) {
//			log.trace("STATE_STOP: "+(nsIWebProgressListener.STATE_STOP & arg2)
//					+ ". STATE_IS_DOCUMENT: "+(nsIWebProgressListener.STATE_IS_DOCUMENT & arg2)
//					+ ". STATE_IS_WINDOW: "+(nsIWebProgressListener.STATE_IS_WINDOW & arg2)
//					+ ". STATE_IS_NETWORK: "+(nsIWebProgressListener.STATE_IS_NETWORK & arg2)
//					+". arg0.getIsLoadingDocument(): "+arg0.getIsLoadingDocument()
//					+ " window: "+arg0.getDOMWindow()
//					);
			if ( (nsIWebProgressListener.STATE_STOP & arg2) >0 // This flag indicates the completion of a request.
					&& (nsIWebProgressListener.STATE_IS_DOCUMENT & arg2) >0
					&& !arg0.getIsLoadingDocument() ) {
				final nsIDOMWindow mainWindow = ((nsIWebBrowser)EMBrowserEditor.this.getMozillaBrowser().getWebBrowser())
						.getContentDOMWindow();
				Preconditions.checkNotNull(mainWindow);
				
				// check for root window: final boolean isRoot = arg0.getDOMWindow().equals(arg0.getDOMWindow().getParent());
				
				if (mainWindow.equals(arg0.getDOMWindow())){
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"Page loading is complete 2: "+EMBrowserEditor.this.getMozillaBrowser().getUrl());
					postEvent(EMBrowserEvent.getInstance(EMBrowserEvent.EventTypes.LOADING_COMPLETE_2
							, EMBrowserEditor.this, new Object[]{EMBrowserEditor.this.getMozillaBrowser().getUrl()}));
					if (webDocSelBoxManager == null)
						webDocSelBoxManager = new WebDocumentSelectionBoxManager(getDocument());
				}
			}
		}
		@Override
		public void onStatusChange(nsIWebProgress arg0, nsIRequest arg1,
				long arg2, String arg3) {}
		@Override
		public nsISupports queryInterface(String id) {
			return Mozilla.queryInterface(this, id);
		}
	}
	
	@Override
	public void setPartName(String partName) {
		super.setPartName(partName);
	}
	
	@Override
	public void setTitleImage(Image titleImage) {
		super.setTitleImage(titleImage);
	}
	
	public void showSelection(Rectangle2D rect, String hoverBorderColor, nsIDOMDocument domDoc) {
		if (webDocSelBoxManager != null)
			webDocSelBoxManager.show(rect, TColor.newHex(hoverBorderColor), domDoc);
	}
	
	public void showSelection(Rectangle2D rect, TColor borderColor, nsIDOMDocument domDoc) {
		if (webDocSelBoxManager != null)
			webDocSelBoxManager.show(rect, borderColor, domDoc);
	}
	
	public void hideSelection() {
		if (webDocSelBoxManager != null)
			webDocSelBoxManager.hide();
	}
	
	public void flashSelection(Rectangle2D rect, String borderColor, nsIDOMDocument domDoc) {
		if (webDocSelBoxManager != null)
			webDocSelBoxManager.flash(rect, TColor.newHex(borderColor), domDoc);
	}
	
	public void flashSelection(Rectangle2D rect, TColor borderColor, nsIDOMDocument domDoc) {
		if (webDocSelBoxManager != null)
			webDocSelBoxManager.flash(rect, borderColor, domDoc);
	}
	
//	/**
//	 * Should be invoked from UI thread
//	 */
//	@Override
//	public synchronized void goToURL(String url) {
//		super.goToURL(url);
//	}
	
}
