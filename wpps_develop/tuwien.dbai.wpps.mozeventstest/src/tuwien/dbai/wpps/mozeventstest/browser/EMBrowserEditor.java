package tuwien.dbai.wpps.mozeventstest.browser;

import org.apache.log4j.Logger;
import org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditorInput;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.mozilla.interfaces.nsIDOMEvent;

import tuwien.dbai.wpps.common.TSForLog;

public class EMBrowserEditor 
extends org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditor {

	private static final Logger log = Logger.getLogger(EMBrowserEditor.class);

	public static final String ID = "tuwien.dbai.wpps.mozeventstest.browsereditor";

	protected class BrowerListener
		extends org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditor.MozillaBrowserListener{
		
		@Override
		public void init() {
			getMozillaBrowser().addLocationListener(this);
			getMozillaBrowser().addStatusTextListener(this);
			
			getMozillaBrowser().addDisposeListener(new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					getMozillaBrowser().removeLocationListener(BrowerListener.this);
					getMozillaBrowser().removeStatusTextListener(BrowerListener.this);
					
					getMozillaBrowser().removeDisposeListener(this);
					
				}
			});
		}
		
		// ========================
		// LocationListener
		// ========================

		@Override
		public void changing(LocationEvent event) {
			super.changing(event);
//			log.info(TSForLogger.getTS(log)+"Location is changing: "+event.location+".");
		}
		
		@Override
		public void changed(LocationEvent event) {
			super.changed(event);
//			log.info(TSForLogger.getTS(log)+"URL has been changed to "+event.location);//+", "+getMozillaBrowser().getUrl());
		}
		
		// ========================
		// ProgressListener
		// ========================
		
		public void changed(ProgressEvent event) {
			super.changed(event);
//			log.info(TSForLogger.getTS(log)+"Progress of loading web resources was changed: "+event.current+"/"+event.total+".");
		}
		
		@Override
		public void completed(ProgressEvent event) {
			super.completed(event);
//			log.info(TSForLogger.getTS(log)+"Page loading has been completed: "+event.current+"/"+event.total+".");
		}
		
		// ========================
		// StatusTextListener
		// ========================
		
		@Override
		public void changed(StatusTextEvent event) {
			super.changed(event);
//			log.info(TSForLogger.getTS(log)+"Web browser status text was changed: "+event.text+".");
		}

		// ========================
		// nsIDOMEventListener
		// ========================
		//invoked 2 times all the time
		@Override
		public void handleEvent(nsIDOMEvent event) {
			super.handleEvent(event);
//			final String eventType = event.getType();
//			if ("pageshow".equals(eventType)
//					|| "DOMContentLoaded".equals(eventType)) {
//				log.info(TSForLogger.getTS(log)+"Page was shown or DOM tree was generated");
//			}
		}

	}
	
	
	/**
	 * Constructor.
	 */
	public EMBrowserEditor() {
		super();
		// we will use our listener instead of standard one.
		browserListener = new BrowerListener();
		
		//We create our listener for the openning new window to create instance of our browser ({@linkplain EMBrowserEditor}).
		openManager = new EMPopupWindowBrowserListener(
			new IShellProvider() {
				public Shell getShell() {
					return EMBrowserEditor.this.getSite().getShell();
				}
		
			});
	}
	
	// we rewrite "init" function to be able to capture our new input class as well as old one.
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		if (input instanceof EMBrowserEditorInput) {
			setSite(site);
			setInput(input);
		} else
			try {
				super.init(site, input);
			} catch (PartInitException e) {
				log.error(TSForLog.getTS(log)+e.getMessage());
				throw new PartInitException(e.getMessage());
			}
	}
	
	/**
	 * Overriden because of function init.
	 * To prevent setting an input of class  MozBrowserEditorInput to the browser.
	 */
	@Override
	protected void setInput(IEditorInput input) {
		EMBrowserEditorInput bInput = null;
		if (input instanceof MozBrowserEditorInput) {
			bInput = new EMBrowserEditorInput(((MozBrowserEditorInput) input).getURL());
		}
		super.setInput(bInput);
	}
	
//	/**
//	 * Should be invoked from UI thread
//	 */
//	@Override
//	public synchronized void goToURL(String url) {
//		super.goToURL(url);
//	}
}
