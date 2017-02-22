package tuwien.dbai.wpps.mozeventstest.browser;

import org.apache.log4j.Logger;
import org.eclipse.atf.mozilla.ide.common.IDOMNodeSelection;
import org.eclipse.atf.mozilla.ide.ui.browser.SelectionBox;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.browser.AuthenticationEvent;
import org.eclipse.swt.browser.AuthenticationListener;
import org.eclipse.swt.browser.CloseWindowListener;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextEvent;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.browser.VisibilityWindowListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.TouchEvent;
import org.eclipse.swt.events.TouchListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.mozilla.interfaces.nsIComponentManager;
import org.mozilla.interfaces.nsIDOMClientRect;
import org.mozilla.interfaces.nsIDOMDragEvent;
import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMEventListener;
import org.mozilla.interfaces.nsIDOMEventTarget;
import org.mozilla.interfaces.nsIDOMKeyEvent;
import org.mozilla.interfaces.nsIDOMMouseEvent;
import org.mozilla.interfaces.nsIDOMMouseScrollEvent;
import org.mozilla.interfaces.nsIDOMMutationEvent;
import org.mozilla.interfaces.nsIDOMNSElement;
import org.mozilla.interfaces.nsIDOMNSHTMLElement;
import org.mozilla.interfaces.nsIDOMNSHTMLFrameElement;
import org.mozilla.interfaces.nsIDOMNSUIEvent;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeList;
import org.mozilla.interfaces.nsIDOMUIEvent;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIDOMWindowInternal;
import org.mozilla.interfaces.nsIDOMWindowUtils;
import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISimpleUnicharStreamFactory;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIUnicharInputStream;
import org.mozilla.interfaces.nsIWebBrowser;
import org.mozilla.xpcom.Mozilla;
import org.mozilla.xpcom.XPCOMException;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.mozcommon.MozDomUtils;

/**
 * <a href="http://www.w3.org/TR/2003/NOTE-DOM-Level-3-Events-20031107/events.html">http://www.w3.org/TR/2003/NOTE-DOM-Level-3-Events-20031107/events.html</a>,
 * <a href="http://www.w3.org/TR/DOM-Level-2-Events/events.html">http://www.w3.org/TR/DOM-Level-2-Events/events.html</a>
 * @created: Sep 28, 2011 2:43:17 PM
 * @author Ruslan (ruslanrf@gmail.com)
 * nsIDOMWindowUtils
 * OverflowEvent
 * nsIWebNavigation
 * nsIConverterOutputStream
 * nsICharsetConverterManager
 * nsIDocumentEncoderNodeFixup
 * nsIEncodedChannel
 * nsIDocCharset
 * nsIConverterInputStream
 * https://developer.mozilla.org/en/Code_snippets/Miscellaneous
 * http://www.quirksmode.org/dom/events/
 * http://en.wikipedia.org/wiki/DOM_events
 */
public class TestBrowerListener implements ProgressListener,
	LocationListener, StatusTextListener, nsIDOMEventListener, 
	AuthenticationListener, CloseWindowListener, OpenWindowListener,
	TitleListener, VisibilityWindowListener, ControlListener,
	DisposeListener, DragDetectListener, FocusListener, KeyListener,
	MouseListener, MouseMoveListener, MouseTrackListener, MouseWheelListener,
	TouchListener, TraverseListener, PaintListener,

	ISelectionChangedListener // is used by the parent browser when we change node selection in the DON tree view.
	
{
	private static final Logger log = Logger.getLogger(TestBrowerListener.class);
	
	private final EMBrowserEditor browser;
	
	public TestBrowerListener(final EMBrowserEditor browser) {
		this.browser = browser;
	}
	
	public void init() {
if (log.isInfoEnabled()) log.info("Initialization of the browser event listener");
		
		selBox = new SelectionBox(browser.getDocument());
		
		browser.getMozillaBrowser().addLocationListener(this);
		browser.getMozillaBrowser().addStatusTextListener(this);
		
		browser.getMozillaBrowser().addAuthenticationListener(this);
		browser.getMozillaBrowser().addCloseWindowListener(this);
		browser.getMozillaBrowser().addOpenWindowListener(this);
		browser.getMozillaBrowser().addTitleListener(this);
		browser.getMozillaBrowser().addVisibilityWindowListener(this);
		
		browser.getMozillaBrowser().addControlListener(this);
		browser.getMozillaBrowser().addDisposeListener(this);
		browser.getMozillaBrowser().addDragDetectListener(this);
		browser.getMozillaBrowser().addFocusListener(this);
//		browser.getMozillaBrowser().addGestureListener(listener)
		browser.getMozillaBrowser().addKeyListener(this);
//		browser.getMozillaBrowser().addMenuDetectListener(listener)
		browser.getMozillaBrowser().addMouseListener(this);
		browser.getMozillaBrowser().addMouseMoveListener(this);
		browser.getMozillaBrowser().addMouseTrackListener(this);
		browser.getMozillaBrowser().addMouseWheelListener(this);
		browser.getMozillaBrowser().addPaintListener(this);
		browser.getMozillaBrowser().addProgressListener(this);
		browser.getMozillaBrowser().addTouchListener(this);
		browser.getMozillaBrowser().addTraverseListener(this);
		
		
		browser.addSelectionChangedListener(this);
		
		final nsIDOMEventTarget docEventTarget = (nsIDOMEventTarget)browser.getDocument().queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
//		nsIDOMWindow w; w.
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();
		final nsIDOMEventTarget domWindowTarget = (nsIDOMEventTarget)iDOMWindow.queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
		
//		nsIDOMWindow2 w2; w2.
		
		
		
//		nsIDOMNodeList frames = browser.getDocument().getElementsByTagName("FRAME");
//		for (int i = 0; i < frames.getLength(); i++) {
//			nsIDOMHTMLFrameElement frame = (nsIDOMHTMLFrameElement) frames
//					.item(i).queryInterface(
//							nsIDOMHTMLFrameElement.NS_IDOMHTMLFRAMEELEMENT_IID);
//			nsIDOMEventTarget ft = (nsIDOMEventTarget)frame.queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
//			System.err.println();
//			
//			((nsIDOMEventTarget)((nsIDOMNSHTMLFrameElement)ft.queryInterface(nsIDOMNSHTMLFrameElement.NS_IDOMNSHTMLFRAMEELEMENT_IID))
//				.getContentWindow().queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID))
//					.addEventListener("unload", this, true);
//			System.err.println("unload event set for the frame!!!"+ frame.getContentDocument().hashCode());
//		}
		
		
		
		
//		log.info(TSForLogger.getTS(log)+"start. add selection listener");
//		final Mozilla moz = Mozilla.getInstance();
//		nsIComponentManager componentManager = moz.getComponentManager();
//		final nsISelectionPrivate selPrivate = (nsISelectionPrivate) componentManager.createInstanceByContractID(
//				"@mozilla.org/content/dom-selection;1", null, nsISelectionPrivate.NS_ISELECTIONPRIVATE_IID)
//				.queryInterface(nsISelectionPrivate.NS_ISELECTIONPRIVATE_IID);
		
//		webBrowser.addWebBrowserListener(new nsISelectionListener() {
//			@Override
//			public nsISupports queryInterface(String id) {
//				return Mozilla.queryInterface(this, id);
//			}
//			@Override
//			public void notifySelectionChanged(nsIDOMDocument arg0, nsISelection arg1,
//					short arg2) {
//				log.info(TSForLogger.getTS(log)+"MOZ. nsISelection!");
//			}
//		}, nsISelectionListener.NS_ISELECTIONLISTENER_IID);
		
		
		
//		nsISelectionListener selListener = (nsISelectionListener) componentManager.createInstanceByContractID(
//				"@mozilla.org/typeaheadfind;1", null, nsISelectionListener.NS_ISELECTIONLISTENER_IID)
//				.queryInterface(nsISelectionListener.NS_ISELECTIONLISTENER_IID);
		
//		nsISelectionListener selListener = (nsISelectionListener)((nsIInterfaceRequestor)browser.getDocument()
//				.queryInterface(nsIInterfaceRequestor.NS_IINTERFACEREQUESTOR_IID))
//				.getInterface(nsISelectionListener.NS_ISELECTIONLISTENER_IID);
		
//		nsISelectionListener selListener = (nsISelectionListener)((nsIInterfaceRequestor)componentManager
//				.queryInterface(nsIInterfaceRequestor.NS_IINTERFACEREQUESTOR_IID))
//				.getInterface(nsISelectionListener.NS_ISELECTIONLISTENER_IID);
				
//		selPrivate.addSelectionListener(new nsISelectionListener() {
//			@Override
//			public nsISupports queryInterface(String id) {
//				return Mozilla.queryInterface(this, id);
//			}
//			@Override
//			public void notifySelectionChanged(nsIDOMDocument arg0, nsISelection arg1,
//					short arg2) {
//				log.info(TSForLogger.getTS(log)+"MOZ. nsISelection!");
//			}
//		});
//		selPrivate.addSelectionListener(selListener);
		
if (log.isInfoEnabled()) log.info(TSForLog.getTS(log)+"finish. add selection listener");
		
		docEventTarget.addEventListener("MozScrolledAreaChanged", this, true);
		
		docEventTarget.addEventListener("click", this, true); //+
		docEventTarget.addEventListener("mousedown", this, true); //+
		docEventTarget.addEventListener("mouseup", this, true); //+
		docEventTarget.addEventListener("mouseover", this, true); //+
		docEventTarget.addEventListener("mousemove", this, true); //+
		docEventTarget.addEventListener("mouseout", this, true); //+
		docEventTarget.addEventListener("dblclick", this, true); //+
		
		// 3 UI events
		docEventTarget.addEventListener("DOMFocusIn", this, true);
		docEventTarget.addEventListener("DOMFocusOut", this, true);
		docEventTarget.addEventListener("DOMActivate", this, true);
		
		// DOM Mutation
		docEventTarget.addEventListener("DOMSubtreeModified", this, true);
		docEventTarget.addEventListener("DOMNodeInserted", this, true);
		docEventTarget.addEventListener("DOMNodeRemoved", this, true);
		docEventTarget.addEventListener("DOMNodeRemovedFromDocument", this, true);
		docEventTarget.addEventListener("DOMNodeInsertedIntoDocument", this, true);
		docEventTarget.addEventListener("DOMAttrModified", this, true);
		docEventTarget.addEventListener("DOMCharacterDataModified", this, true);
		docEventTarget.addEventListener("DOMElementNameChanged", this, true);
		docEventTarget.addEventListener("DOMAttributeNameChanged", this, true);
		docEventTarget.addEventListener("DOMLinkAdded", this, true);
		docEventTarget.addEventListener("DOMTitleChanged", this, true);
		docEventTarget.addEventListener("DOMContentLoaded", this, true);
		docEventTarget.addEventListener("DOMFrameContentLoaded", this, true);
		
//		new!
		domWindowTarget.addEventListener("DOMWindowClose", this, true);
		domWindowTarget.addEventListener("MozAfterPaint", this, true); // is not supported from 1.9.2
		domWindowTarget.addEventListener("paint", this, true);
		domWindowTarget.addEventListener("DOMWillOpenModalDialog", this, true);
		domWindowTarget.addEventListener("DOMModalDialogClosed", this, true);
		domWindowTarget.addEventListener("fullscreen", this, true);
		domWindowTarget.addEventListener("PopupWindow", this, true);
		domWindowTarget.addEventListener("ValueChange", this, true);
		domWindowTarget.addEventListener("DOMMenuItemActive", this, true);
		domWindowTarget.addEventListener("DOMMenuItemInactive", this, true);
		domWindowTarget.addEventListener("windowZLevel", this, true);
		
		//Clipboard
		docEventTarget.addEventListener("beforecut", this, true);
		docEventTarget.addEventListener("beforecopy", this, true);
		docEventTarget.addEventListener("beforepaste", this, true);
		
		//Mouse
		docEventTarget.addEventListener("drag", this, true);
		docEventTarget.addEventListener("dragstart", this, true);
		docEventTarget.addEventListener("dragenter", this, true);
		docEventTarget.addEventListener("dragover", this, true);
		docEventTarget.addEventListener("dragleave", this, true);
		docEventTarget.addEventListener("dragend", this, true);
		docEventTarget.addEventListener("drop", this, true);
		docEventTarget.addEventListener("selectstart", this, true);
		docEventTarget.addEventListener("dragdrop", this, true);
		docEventTarget.addEventListener("dragexit", this, true);
		docEventTarget.addEventListener("draggesture", this, true);
		
		docEventTarget.addEventListener("CheckboxStateChange", this, true);
		docEventTarget.addEventListener("RadioStateChange", this, true);
		docEventTarget.addEventListener("close", this, true);
		docEventTarget.addEventListener("input", this, true);
		docEventTarget.addEventListener("command", this, true);
		docEventTarget.addEventListener("commandupdate", this, true);
		
		
		domWindowTarget.addEventListener("beforeunload", this, true);
		
		docEventTarget.addEventListener("overflow", this, true);
		docEventTarget.addEventListener("overflowchanged", this, true);
		docEventTarget.addEventListener("underflow", this, true);
		
		
		domWindowTarget.addEventListener("popuphidden", this, true);
		domWindowTarget.addEventListener("popuphiding", this, true);
		domWindowTarget.addEventListener("popupshowing", this, true);
		domWindowTarget.addEventListener("popupshown", this, true);
		
		docEventTarget.addEventListener("DOMMouseScroll", this, true);
		docEventTarget.addEventListener("MozMousePixelScroll", this, true);
		
		
		
		docEventTarget.addEventListener("broadcast", this, true);
		
		// HTML Events
		
		domWindowTarget.addEventListener("load", this, true); // + for page(no), frames(no)
		domWindowTarget.addEventListener("unload", this, true); // + for page(no), frames(no)
		domWindowTarget.addEventListener("pageshow", this, true);
		domWindowTarget.addEventListener("pagehide", this, true);
		domWindowTarget.addEventListener("abort", this, true);
		domWindowTarget.addEventListener("error", this, true); // +
		domWindowTarget.addEventListener("select", this, true); //+ can be on document
		docEventTarget.addEventListener("change", this, true); // +
		docEventTarget.addEventListener("submit", this, true); //+
		docEventTarget.addEventListener("reset", this, true); //+
		domWindowTarget.addEventListener("focus", this, true); //+ can be Window
		domWindowTarget.addEventListener("blur", this, true); //+ can be Window
		domWindowTarget.addEventListener("resize", this, true);//+ window!!!
		
		domWindowTarget.addEventListener("scroll", this, true); //+
		domWindowTarget.addEventListener("mousewheel", this, true);//+
		docEventTarget.addEventListener("wheel", this, true);//+ no
		
		docEventTarget.addEventListener("textinput", this, true); // no
		docEventTarget.addEventListener("keydown", this, true); // +
		docEventTarget.addEventListener("keyup", this, true); // +
		docEventTarget.addEventListener("keypress", this, true); // +
		
		// http://www.w3.org/TR/DOM-Level-3-Events/#event-type-compositionstart
		docEventTarget.addEventListener("compositionstart", this, true); // no
		
		docEventTarget.addEventListener("contextmenu", this, true);
		docEventTarget.addEventListener("copy", this, true); //+
		docEventTarget.addEventListener("cut", this, true); //+
		docEventTarget.addEventListener("hashchange", this, true);
		
		docEventTarget.addEventListener("paste", this, true); //+
		
		
		
		
		browser.getMozillaBrowser().addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				log.info("Browser is disposed.");
				browser.getMozillaBrowser().removeLocationListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeStatusTextListener(TestBrowerListener.this);
				
				browser.getMozillaBrowser().removeAuthenticationListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeCloseWindowListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeOpenWindowListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeTitleListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeVisibilityWindowListener(TestBrowerListener.this);
				
				browser.getMozillaBrowser().removeControlListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeDisposeListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeDragDetectListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeFocusListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeKeyListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeMouseListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeMouseMoveListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeMouseTrackListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeMouseWheelListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removePaintListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeProgressListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeTouchListener(TestBrowerListener.this);
				browser.getMozillaBrowser().removeTraverseListener(TestBrowerListener.this);
				
				browser.removeSelectionChangedListener(TestBrowerListener.this);
				
				docEventTarget.removeEventListener("click", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("mousedown", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("mouseup", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("mouseover", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("mousemove", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("mouseout", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("dblclick", TestBrowerListener.this, true);
				
				docEventTarget.removeEventListener("DOMFocusIn", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMFocusOut", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMActivate", TestBrowerListener.this, true);
				
				docEventTarget.removeEventListener("DOMSubtreeModified", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMNodeInserted", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMNodeRemoved", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMNodeRemovedFromDocument", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMNodeInsertedIntoDocument", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMAttrModified", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMCharacterDataModified", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMElementNameChanged", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMAttributeNameChanged", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMLinkAdded", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMTitleChanged", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMContentLoaded", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("DOMFrameContentLoaded", TestBrowerListener.this, true);
				
				docEventTarget.removeEventListener("DOMMouseScroll", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("MozMousePixelScroll", TestBrowerListener.this, true);
				
				docEventTarget.removeEventListener("broadcast", TestBrowerListener.this, true);
				
//				new!
				domWindowTarget.removeEventListener("DOMWindowClose", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("MozAfterPaint", TestBrowerListener.this, true); // is not supported from 1.9.2
				domWindowTarget.removeEventListener("paint", TestBrowerListener.this, true);
				
				domWindowTarget.removeEventListener("DOMWillOpenModalDialog", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("DOMModalDialogClosed", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("fullscreen", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("PopupWindow", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("ValueChange", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("DOMMenuItemActive", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("DOMMenuItemInactive", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("windowZLevel", TestBrowerListener.this, true);
				
				//Clipboard
				docEventTarget.removeEventListener("beforecut", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("beforecopy", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("beforepaste", TestBrowerListener.this, true);
				
				//Mouse
				docEventTarget.removeEventListener("drag", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("dragstart", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("dragenter", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("dragover", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("dragleave", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("dragend", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("drop", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("selectstart", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("dragdrop", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("dragexit", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("draggesture", TestBrowerListener.this, true);
				
				docEventTarget.removeEventListener("CheckboxStateChange", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("RadioStateChange", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("close", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("input", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("command", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("commandupdate", TestBrowerListener.this, true);
				
				
				docEventTarget.removeEventListener("beforeunload", TestBrowerListener.this, true);
				
				docEventTarget.removeEventListener("overflow", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("overflowchanged", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("underflow", TestBrowerListener.this, true);
				
				
				domWindowTarget.removeEventListener("popuphidden", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("popuphiding", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("popupshowing", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("popupshown", TestBrowerListener.this, true);
				
				
				docEventTarget.removeEventListener("load", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("unload", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("pageshow", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("pagehide", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("abort", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("error", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("select", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("change", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("submit", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("reset", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("focus", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("blur", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("resize", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("scroll", TestBrowerListener.this, true);
				
				docEventTarget.removeEventListener("textinput", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("keydown", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("keyup", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("keypress", TestBrowerListener.this, true);
				
				docEventTarget.removeEventListener("compositionstart", TestBrowerListener.this, true);
				
				docEventTarget.removeEventListener("contextmenu", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("copy", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("cut", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("hashchange", TestBrowerListener.this, true);
				domWindowTarget.removeEventListener("mousewheel", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("paste", TestBrowerListener.this, true);
				docEventTarget.removeEventListener("wheel", TestBrowerListener.this, true);
				
				browser.getMozillaBrowser().removeDisposeListener(this);
				
			}
		});
	}
	
	
	
		// ========================
		// LocationListener
		// ========================

	
	nsIDOMWindow tmp = null;
		@Override
		public void changing(LocationEvent event) {
//			Browser w = (Browser)event.getSource();
//			System.err.println(event.getSource());
//			System.err.println(event.widget.getData()); // null
//			System.err.println(event.data); // null
//			nsIDOMWindow a = (nsIDOMWindow)event.data;
//			System.err.println(a.getName());
//			System.err.println(a.getNodeName()+"::"+a.getNodeValue());
			log.info(TSForLog.getTS(log)+"Location is changing: "+event.location+".");
			
			System.err.println(((nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser())
					.getContentDOMWindow());
			nsIDOMNodeList frames = browser.getDocument().getElementsByTagName("FRAME");
			for (int i = 0; i < frames.getLength(); i++) {
				nsIDOMNSHTMLFrameElement frame = (nsIDOMNSHTMLFrameElement) frames
						.item(i).queryInterface(
								nsIDOMNSHTMLFrameElement.NS_IDOMNSHTMLFRAMEELEMENT_IID);
//				nsIDOMEventTarget ft = (nsIDOMEventTarget)frame.queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
					tmp = frame.getContentWindow();
				System.err.println("1: "+frame.getContentWindow());
//				System.err.println("1"+frame.getContentDocument().hashCode());
			}
			
//			nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
////			nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();
//			final nsIDOMEventTarget domWindowTarget = (nsIDOMEventTarget)webBrowser.getContentDOMWindow()
//					.queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
//			domWindowTarget.addEventListener("load", this, true);
		}
		
		
		@Override
		public void changed(LocationEvent event) {
			log.info(TSForLog.getTS(log)+"URL has been changed to "+event.location);//+", "+getMozillaBrowser().getUrl());
			System.err.println(((nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser())
					.getContentDOMWindow());
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
//		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();
//		final nsIDOMEventTarget domWindowTarget = (nsIDOMEventTarget)webBrowser.getContentDOMWindow()
//				.queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
		
		final nsIDOMEventTarget domWindowTarget = (nsIDOMEventTarget)tmp
				.queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
		
		domWindowTarget.addEventListener("load", this, true);
		domWindowTarget.addEventListener("DOMFrameContentLoaded", this, true);
		domWindowTarget.addEventListener("DOMContentLoaded", this, true);
		
		log.debug(".");	
		
		
//			nsIDOMNodeList frames = browser.getDocument().getElementsByTagName("FRAME");
//			for (int i = 0; i < frames.getLength(); i++) {
//				nsIDOMHTMLFrameElement frame = (nsIDOMHTMLFrameElement) frames
//						.item(i).queryInterface(
//								nsIDOMHTMLFrameElement.NS_IDOMHTMLFRAMEELEMENT_IID);
//				nsIDOMEventTarget ft = (nsIDOMEventTarget)frame.queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
//				System.err.println("2"+frame.getContentDocument().hashCode());
//			}
		
		System.err.println(((nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser())
				.getContentDOMWindow());
		nsIDOMNodeList frames = browser.getDocument().getElementsByTagName("FRAME");
		for (int i = 0; i < frames.getLength(); i++) {
			nsIDOMNSHTMLFrameElement frame = (nsIDOMNSHTMLFrameElement) frames
					.item(i).queryInterface(
							nsIDOMNSHTMLFrameElement.NS_IDOMNSHTMLFRAMEELEMENT_IID);
//			nsIDOMEventTarget ft = (nsIDOMEventTarget)frame.queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
			System.err.println("2: "+frame.getContentWindow());
//			System.err.println("1"+frame.getContentDocument().hashCode());
		}
		
		}
		
		// ========================
		// ProgressListener
		// ========================
		
		public void changed(ProgressEvent event) {
//				log.info(TSForLogger.getTS(log)+"Progress of loading web resources was changed: "+event.current+"/"+event.total+".");
		}
		
		@Override
		public void completed(ProgressEvent event) {
			log.info(TSForLog.getTS(log)+"Page loading has been completed: "+event.current+"/"+event.total+".");
		}
		
		// ========================
		// StatusTextListener
		// ========================
		
		@Override
		public void changed(StatusTextEvent event) {
//				log.info(TSForLogger.getTS(log)+"Web browser status text was changed: "+event.text+".");
		}

		// ========================
		// AuthenticationListener
		// ========================
		
		@Override
		public void authenticate(AuthenticationEvent event) {
			log.info(TSForLog.getTS(log)+"Authentification. Location: "+event.location+"; User: "+event.user
					+"; Passwd: "+event.password+"; Time: "+event.time);
		}

		// ========================
		// CloseWindowListener
		// ========================
		
		@Override
		public void close(WindowEvent event) {
			log.info(TSForLog.getTS(log)+"Browser is about to be closed. (time: "+event.time+").");
		}

		// ========================
		// OpenWindowListener
		// ========================
		
		@Override
		public void open(WindowEvent event) {
			log.info(TSForLog.getTS(log)+"An new browser window is about to be open. (time: "+event.time+").");
		}

		// ========================
		// TitleListener
		// ========================
		
		@Override
		public void changed(TitleEvent event) {
			log.info(TSForLog.getTS(log)+"Title was changed. (title: "+event.title+" time: "+event.time+").");
		}

		@Override
		public void hide(WindowEvent event) {
			log.info(TSForLog.getTS(log)+"Browser window is hidden. (time: "+event.time+").");
			
		}

		@Override
		public void show(WindowEvent event) {
			log.info(TSForLog.getTS(log)+"Browser window is shown. (time: "+event.time+").");
		}
		
		// ========================
		// ISelectionChangedListener
		// ========================
		
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			log.info(TSForLog.getTS(log)+"Selection was changed.");
			
			ISelection sel = event.getSelection();
			if (sel instanceof IDOMNodeSelection) {
				nsIDOMNode node = ((IDOMNodeSelection)sel).getSelectedNode();
				log.info(TSForLog.getTS(log)+"A node was selected. Name: "+node.getNodeName()+" Value: "+node.getNodeValue());
			}
		}

		// ========================
		// ControlListener
		// ========================
		
		@Override
		public void controlMoved(ControlEvent e) {
			log.info(TSForLog.getTS(log)+"controlMoved");
		}

		@Override
		public void controlResized(ControlEvent e) {
			log.info(TSForLog.getTS(log)+"controlResized");
		}
		
		// ========================
		// DisposeListener
		// ========================
		
		@Override
		public void widgetDisposed(DisposeEvent e) {
			log.info(TSForLog.getTS(log)+"widgetDisposed");
		}
		
		// ========================
		// DragDetectListener
		// ========================
		
		@Override
		public void dragDetected(DragDetectEvent e) {
			log.info(TSForLog.getTS(log)+"dragDetected"); // informative
		}
		
		// ========================
		// FocusListener
		// ========================
		
		@Override
		public void focusGained(FocusEvent e) {
			log.info(TSForLog.getTS(log)+"focusGained");
		}

		@Override
		public void focusLost(FocusEvent e) {
			log.info(TSForLog.getTS(log)+"focusLost");
		}
		
		// ========================
		// KeyListener
		// ========================
		
		@Override
		public void keyPressed(KeyEvent e) {
			log.info(TSForLog.getTS(log)+"keyPressed character:"+e.character
					+" keyCode:"+e.keyCode+" keyCode char:"+((char)e.keyCode)); // informative
		}

		@Override
		public void keyReleased(KeyEvent e) {
			log.info(TSForLog.getTS(log)+"keyReleased");
		}
		
		// ========================
		// MouseListener
		// ========================
		
		@Override
		public void mouseDoubleClick(MouseEvent e) {
			log.info(TSForLog.getTS(log)+"mouseDoubleClick");
		}

		@Override
		public void mouseDown(MouseEvent e) {
			log.info(TSForLog.getTS(log)+"mouseDown");
		}

		@Override
		public void mouseUp(MouseEvent e) {
			log.info(TSForLog.getTS(log)+"mouseUp");
		}
		
		// ========================
		// MouseMoveListener
		// ========================
		
		@Override
		public void mouseMove(MouseEvent e) {
//			log.info(TSForLogger.getTS(log)+"mouseMove");
		}
		
		// ========================
		// MouseTrackListener
		// ========================
		
		@Override
		public void mouseEnter(MouseEvent e) {
//			log.info(TSForLogger.getTS(log)+"mouseEnter");
		}

		@Override
		public void mouseExit(MouseEvent e) {
//			log.info(TSForLogger.getTS(log)+"mouseExit");
		}

		@Override
		public void mouseHover(MouseEvent e) {
//			log.info(TSForLogger.getTS(log)+"mouseHover");
		}
		
		// ========================
		// MouseWheelListener
		// ========================
		
		@Override
		public void mouseScrolled(MouseEvent e) {
			log.info(TSForLog.getTS(log)+"mouseScrolled. Buton:"+e.button+" Count:"+e.count+" Time:"+e.time);
		}
		
		// ========================
		// TouchListener
		// ========================
		
		@Override
		public void touch(TouchEvent e) {
			log.info(TSForLog.getTS(log)+"touch");
		}
		
		// ========================
		// TraverseListener
		// ========================
		
		@Override
		public void keyTraversed(TraverseEvent e) {
			log.info(TSForLog.getTS(log)+"keyTraversed");
		}
		
		// ========================
		// PaintListener
		// ========================
		
		@Override
		public void paintControl(PaintEvent e) {
			log.info(TSForLog.getTS(log)+"paintControl");
		}

		
		// ========================
		// nsIDOMEventListener
		// ========================

		//invoked 2 times all the time
		@Override
		public void handleEvent(nsIDOMEvent event) {
			
//			for (long i=0; i<100000000; i++);
			
			
			final String eventType = event.getType();
			
			
			if ("MozScrolledAreaChanged".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. MozScrolledAreaChanged");
			}
			
			
			// http://www.w3.org/TR/DOM-Level-2-Events/events.html
			
			// mouse events
			if ("click".equals(eventType)) {
				nsIDOMMouseEvent event2 = (nsIDOMMouseEvent)event.queryInterface(nsIDOMMouseEvent.NS_IDOMMOUSEEVENT_IID);
				nsIDOMNode node2 = (nsIDOMNode)event2.getTarget().queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				log.info(TSForLog.getTS(log)+"MOZ. Mouse was clicked. CtrlKey: "+event2.getCtrlKey()+" ShiftKey: "+event2.getShiftKey()
						+" AltKey: "+event2.getAltKey()+" MetaKey: "+event2.getMetaKey()+" Button: "+event2.getButton()
						+" Detail:"+event2.getDetail()
						+ ". Node name: "+node2.getNodeName()
						+ ", val: "+node2.getNodeValue()
						+ " Client:("+event2.getClientX()
						+ ", "+event2.getClientY()+") "
						+ " Screen:("+event2.getScreenX()+", "
						+ event2.getScreenY()+")");
			}
			if ("mousedown".equals(eventType)) {
				nsIDOMMouseEvent event2 = (nsIDOMMouseEvent)event.queryInterface(nsIDOMMouseEvent.NS_IDOMMOUSEEVENT_IID);
				nsIDOMNode node2 = (nsIDOMNode)event2.getTarget().queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				log.info(TSForLog.getTS(log)+"MOZ. Mouse button was pushed down. CtrlKey: "+event2.getCtrlKey()+" ShiftKey: "+event2.getShiftKey()
						+" AltKey: "+event2.getAltKey()+" MetaKey: "+event2.getMetaKey()+" Button: "+event2.getButton()
						+ ". Node name: "+node2.getNodeName()
						+ ", val: "+node2.getNodeValue());
			}
			if ("mouseup".equals(eventType)) {
				nsIDOMMouseEvent event2 = (nsIDOMMouseEvent)event.queryInterface(nsIDOMMouseEvent.NS_IDOMMOUSEEVENT_IID);
				nsIDOMNode node2 = (nsIDOMNode)event2.getTarget().queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				StringBuffer sb = new StringBuffer();
				sb.append(TSForLog.getTS(log)+"MOZ. mouseup. CtrlKey: "+event2.getCtrlKey()+" ShiftKey: "+event2.getShiftKey()
						+" AltKey: "+event2.getAltKey()+" MetaKey: "+event2.getMetaKey()+" Button: "+event2.getButton()
						+ ". Node name: "+node2.getNodeName()
						+ ", val: "+node2.getNodeValue());
				
//				nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
//				nsISelection sel = webBrowser.getContentDOMWindow().getSelection();
				
//				sel.getAnchorNode();
//				sb.append(" Selection ranges:" +(sel.getRangeCount())+" Sel. content:"+sel.getRangeAt(0).getStartContainer().getNodeValue());
				
				
//				nsIBoxObject o = MozDomUtils.getBoxObjectFor(node2);
//				sb.append(" BoxObj:: ScreenX:"+o.getScreenX()+" ScreenY:"+o.getScreenY()
//						+" X:"+o.getX()+" Y:"+o.getY()+" Width:"+o.getWidth()+" Height:"+o.getHeight());
				
				
				nsIDOMClientRect cr = MozDomUtils.getBoundingClientRectFor(node2);
				sb.append(" ClRect: "+cr.getLeft()+" Top:"+cr.getTop()
						+" Right:"+cr.getRight()+" Bottom:"+cr.getBottom()
						+" Width:"+cr.getWidth()+" Height:"+cr.getHeight());
				
				nsIDOMNSElement nsEl = (nsIDOMNSElement)node2
						.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
				sb.append(" DOMNSEL:: ClientLeft:"+nsEl.getClientLeft()+" ClientTop:"+nsEl.getClientTop()
						+" ClientWidth:"+nsEl.getClientWidth()+" ClientHeight:"+nsEl.getClientHeight()
						+" ScrollLeft:"+nsEl.getScrollLeft()+" ScrollTop:"+nsEl.getScrollTop()
						+" ScrollWidth:"+nsEl.getScrollWidth()+" ScrollHeight:"+nsEl.getScrollHeight());
				
				nsIDOMNSHTMLElement hEl = (nsIDOMNSHTMLElement)node2.queryInterface(nsIDOMNSHTMLElement.NS_IDOMNSHTMLELEMENT_IID);
				sb.append(" DOMNSH:: OffsetLeft:"+hEl.getOffsetLeft()+" OffsetTop:"+hEl.getOffsetTop()
						+" OffsetWidth:"+hEl.getOffsetWidth()+" OffsetHeight:"+hEl.getOffsetHeight());
				
				nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
				nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();
				
				
				sb.append(" iDOMWindow:: ScrollX:"+iDOMWindow.getScrollX()+" ScrollY:"+iDOMWindow.getScrollY());
				
				
				nsIDOMWindowUtils wu = (nsIDOMWindowUtils)((nsIInterfaceRequestor)iDOMWindow
						.queryInterface(nsIInterfaceRequestor.NS_IINTERFACEREQUESTOR_IID))
						.getInterface(nsIDOMWindowUtils.NS_IDOMWINDOWUTILS_IID);
				int[] scrollX = new int[1];
				int[] scrollY = new int[1];
				wu.getScrollXY(true, scrollX, scrollY);
				
				sb.append(" nsIDOMWindowUtils:: scrollX:"+scrollX[0]+" scrollY:"+scrollY[0]);
				
//				wu.getScrollXY(true, arg1, arg2)
				
				nsIDOMWindowInternal wi = (nsIDOMWindowInternal)iDOMWindow.queryInterface(nsIDOMWindowInternal.NS_IDOMWINDOWINTERNAL_IID);
				sb.append(" DOMWindowInternal:: ScreenX:"+wi.getScreenX() + " ScreenY:" +wi.getScreenY()
						+ " PageXOffset:"+wi.getPageXOffset()+" PageYOffset:"+wi.getPageYOffset()
						+" ScrollMaxX:"+wi.getScrollMaxX()+" ScrollMaxY:"+wi.getScrollMaxY()
						+" ScrollX:"+wi.getScrollX()+" ScrollY:"+wi.getScrollY());
				
				sb.append(" nsIDOMScreen:: Top"+wi.getScreen().getTop()+" Left:"+wi.getScreen().getLeft());
//				nsIDOMScreen ds;
				
				
				nsIDOMClientRect cr2 = MozDomUtils.getBoundingClientRectFor((nsIDOMNode)node2.getOwnerDocument().getDocumentElement());
				sb.append("Doc:: Left:"+cr2.getLeft()+" Right:"+cr2.getRight());
				
//				nsIBoxObject bo2 = (nsIBoxObject)node2.queryInterface(nsIBoxObject.NS_IBOXOBJECT_IID);
				
				
				log.info(sb.toString());
				
				
				
			}
			
			
			if ("mouseover".equals(eventType)) {
				nsIDOMMouseEvent event2 = (nsIDOMMouseEvent)event.queryInterface(nsIDOMMouseEvent.NS_IDOMMOUSEEVENT_IID);
//				nsIDOMEventTarget tagert = event2.getRelatedTarget();
//				nsIDOMEventTarget tagert = event2.getCurrentTarget();
				nsIDOMEventTarget tagert = event2.getTarget();
				nsIDOMNode node = (nsIDOMNode)tagert.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. Mouse over node (nam:"+node.getNodeName()+";val:"+node.getNodeValue()+"), button: "+event2.getButton());
				
//				domNodeSelection.setParams(true, node);
//				browser.selectionChanged(browser, domNodeSelection);
//				browser.setSelection(domNodeSelection);
				
				if (node.getNodeType() == node.ELEMENT_NODE) {
//					selBox.highlight((nsIDOMElement)node.queryInterface(nsIDOMElement.NS_IDOMELEMENT_IID));
				}
			}
			if ("mousemove".equals(eventType)) {
				nsIDOMMouseEvent event2 = (nsIDOMMouseEvent)event.queryInterface(nsIDOMMouseEvent.NS_IDOMMOUSEEVENT_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. Mouse had been moved ("+event2.getScreenX()+";"+event2.getScreenY()+").");
			}
			if ("mouseout".equals(eventType)) {
				nsIDOMMouseEvent event2 = (nsIDOMMouseEvent)event.queryInterface(nsIDOMMouseEvent.NS_IDOMMOUSEEVENT_IID);
				nsIDOMEventTarget tagert = event2.getTarget();
				nsIDOMNode node = (nsIDOMNode)tagert.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. Mouse out the node (nam:"+node.getNodeName()+";val:"+node.getNodeValue()+"), button: "+event2.getButton());
			}
			if ("dblclick".equals(eventType)) {
				nsIDOMMouseEvent event2 = (nsIDOMMouseEvent)event.queryInterface(nsIDOMMouseEvent.NS_IDOMMOUSEEVENT_IID);
				nsIDOMEventTarget tagert = event2.getTarget();
				nsIDOMNode node = (nsIDOMNode)tagert.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				log.info(TSForLog.getTS(log)+"MOZ. dblclick. node (nam:"+node.getNodeName()+";val:"+node.getNodeValue()+"), button: "+event2.getButton());
			}
			
			// 3 UI Events. When they are invoked?
			if ("DOMFocusIn".equals(eventType)) {
				nsIDOMUIEvent event2 = (nsIDOMUIEvent) event.queryInterface(nsIDOMUIEvent.NS_IDOMUIEVENT_IID);
				nsIDOMEventTarget tagert2 = event2.getTarget();
				nsIDOMNode node2 = (nsIDOMNode)tagert2.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				StringBuffer sb = new StringBuffer();
				sb.append(TSForLog.getTS(log)+"MOZ. DOMFocusIn event. Detail: "+event2.getDetail()+". Target name: "+node2.getNodeName()+", target val: "+node2.getNodeValue()+". ");
				nsIDOMNSUIEvent event3 = (nsIDOMNSUIEvent) event.queryInterface(nsIDOMNSUIEvent.NS_IDOMNSUIEVENT_IID);
				sb.append("PageX: "+event3.getPageX()+"PageY: "+event3.getPageY()+"LayerX: "+event3.getLayerX()+" LayerY: "+event3.getLayerY()+".");
				log.info(TSForLog.getTS(log) + sb);
			}
			
			if ("DOMFocusOut".equals(eventType)) {
				nsIDOMUIEvent event2 = (nsIDOMUIEvent) event.queryInterface(nsIDOMUIEvent.NS_IDOMUIEVENT_IID);
				nsIDOMEventTarget tagert2 = event2.getTarget();
				nsIDOMNode node2 = (nsIDOMNode)tagert2.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				StringBuffer sb = new StringBuffer();
				sb.append(TSForLog.getTS(log)+"MOZ. DOMFocusOut event. Detail: "+event2.getDetail()+". Target name: "+node2.getNodeName()+", target val: "+node2.getNodeValue()+". ");
				nsIDOMNSUIEvent event3 = (nsIDOMNSUIEvent) event.queryInterface(nsIDOMNSUIEvent.NS_IDOMNSUIEVENT_IID);
				sb.append("PageX: "+event3.getPageX()+"PageY: "+event3.getPageY()+"LayerX: "+event3.getLayerX()+" LayerY: "+event3.getLayerY()+".");
				log.info(TSForLog.getTS(log) + sb);
			}
			
			if ("DOMActivate".equals(eventType)) {
				nsIDOMUIEvent event2 = (nsIDOMUIEvent) event.queryInterface(nsIDOMUIEvent.NS_IDOMUIEVENT_IID);
				nsIDOMEventTarget tagert2 = event2.getTarget();
				nsIDOMNode node2 = (nsIDOMNode)tagert2.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				StringBuffer sb = new StringBuffer();
				sb.append(TSForLog.getTS(log)+"MOZ. DOMActivate event. Detail: "+event2.getDetail()+". Target name: "+node2.getNodeName()+", target val: "+node2.getNodeValue()+". ");
				nsIDOMNSUIEvent event3 = (nsIDOMNSUIEvent) event.queryInterface(nsIDOMNSUIEvent.NS_IDOMNSUIEVENT_IID);
				sb.append("PageX: "+event3.getPageX()+"PageY: "+event3.getPageY()+"LayerX: "+event3.getLayerX()+" LayerY: "+event3.getLayerY()+".");
				log.info(TSForLog.getTS(log) + sb);
			}
			
			// DOM Mutation
			
			// https://developer.mozilla.org/En/Listening_to_events
			// http://www.w3.org/TR/DOM-Level-2-Events/events.html
			
			if ("DOMSubtreeModified".equals(eventType)) {
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. DOMSubtreeModified. relatedNode name: "+ event2.getRelatedNode().getNodeName()
//						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
//						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
//						+ "attrName:" + event2.getAttrName());
			}
			if ("DOMNodeInserted".equals(eventType)) {
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. DOMNodeInserted. relatedNode name: "+ event2.getRelatedNode().getNodeName()
//						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
//						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
//						+ "attrName:" + event2.getAttrName());
			}
			if ("DOMNodeRemoved".equals(eventType)) {
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. DOMNodeRemoved. relatedNode name: "+ event2.getRelatedNode().getNodeName()
//						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
//						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
//						+ "attrName:" + event2.getAttrName());
			}
			if ("DOMNodeRemovedFromDocument".equals(eventType)) {
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. DOMNodeRemovedFromDocument. relatedNode name: "+ event2.getRelatedNode().getNodeName()
//						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
//						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
//						+ "attrName:" + event2.getAttrName());
			}
			if ("DOMNodeInsertedIntoDocument".equals(eventType)) {
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. DOMNodeInsertedIntoDocument. relatedNode name: "+ event2.getRelatedNode().getNodeName()
//						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
//						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
//						+ "attrName:" + event2.getAttrName());
			}
			if ("DOMAttrModified".equals(eventType)) {
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. DOMAttrModified. relatedNode name: "+ event2.getRelatedNode().getNodeName()
//						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
//						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
//						+ "attrName:" + event2.getAttrName());
			}
			if ("DOMCharacterDataModified".equals(eventType)) {
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. DOMCharacterDataModified. relatedNode name: "+ event2.getRelatedNode().getNodeName()
//						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
//						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
//						+ "attrName:" + event2.getAttrName());
			}
			
			if ("DOMElementNameChanged".equals(eventType)) {
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. DOMElementNameChanged. relatedNode name: "+ event2.getRelatedNode().getNodeName()
//						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
//						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
//						+ "attrName:" + event2.getAttrName());
			}
			
			if ("DOMAttributeNameChanged".equals(eventType)) {
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
//				log.info(TSForLogger.getTS(log)+"MOZ. DOMAttributeNameChanged. relatedNode name: "+ event2.getRelatedNode().getNodeName()
//						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
//						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
//						+ "attrName:" + event2.getAttrName());
			}
			
			if ("DOMLinkAdded".equals(eventType)) { // Dispatched when a new HTML <link> element is detected in the document.
				// The DOMLinkHandler object is called by the DOMLinkAdded event in order to detect any RSS feeds, site icons, or OpenSearch plugins for the web site.
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
				log.info(TSForLog.getTS(log)+"MOZ. DOMLinkAdded. relatedNode name: "+ event2.getRelatedNode().getNodeName()
						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
						+ "attrName:" + event2.getAttrName());
			}
			
			if ("DOMTitleChanged".equals(eventType)) {
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
				log.info(TSForLog.getTS(log)+"MOZ. DOMTitleChanged. relatedNode name: "+ event2.getRelatedNode().getNodeName()
						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
						+ "attrName:" + event2.getAttrName());
			}
			
			if ("DOMContentLoaded".equals(eventType)) { // Dispatched when the initial DOM for the page is completely loaded.
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
				log.info(TSForLog.getTS(log)+"MOZ. DOMContentLoaded. relatedNode name: "+ event2.getRelatedNode().getNodeName()
						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
						+ "attrName:" + event2.getAttrName());
			}
			
			//https://developer.mozilla.org/en/Gecko-Specific_DOM_Events#DOMMouseScroll
			if ("DOMFrameContentLoaded".equals(eventType)) { // Dispatched when the initial DOM for the page is completely loaded.
				nsIDOMMutationEvent event2 = (nsIDOMMutationEvent)event.queryInterface(nsIDOMMutationEvent.NS_IDOMMUTATIONEVENT_IID);
				log.info(TSForLog.getTS(log)+"MOZ. DOMFrameContentLoaded. relatedNode name: "+ event2.getRelatedNode().getNodeName()
						+ "relatedNode val: "+ event2.getRelatedNode().getNodeValue()
						+ "prevValue: "+ event2.getPrevValue()+" newValue: "+event2.getNewValue()
						+ "attrName:" + event2.getAttrName());
			}
			
			
			
			//https://developer.mozilla.org/en/XPCOM_Interface_Reference/nsIDOMMouseScrollEvent
			if ("DOMMouseScroll".equals(eventType)) { // Dispatched when the initial DOM for the page is completely loaded.
				nsIDOMMouseScrollEvent event2 = (nsIDOMMouseScrollEvent) event.queryInterface(nsIDOMMouseScrollEvent.NS_IDOMMOUSESCROLLEVENT_IID);
				nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
//				if (event.getEventPhase() == event.CAPTURING_PHASE) {
					StringBuffer sb = new StringBuffer();
					sb.append(TSForLog.getTS(log)+"MOZ. DOMMouseScroll. CAPTURING_PHASE. Axis: "+ event2.getAxis()+" Type:"+event.getType()+" Cancelable:"+event.getCancelable()
							+" ScrollX:"+webBrowser.getContentDOMWindow().getScrollX()+" ScrollY:"+webBrowser.getContentDOMWindow().getScrollY()
							+ " getClientX: " + event2.getClientX() + " getClientY: " +event2.getClientY()
							+ " getScreenX: "+ event2.getScreenX() + " getScreenY: "+event2.getScreenY()
							+ "Detail: "+event2.getDetail());

					nsIDOMMouseEvent e3 =(nsIDOMMouseEvent)event.queryInterface(nsIDOMMouseEvent.NS_IDOMMOUSEEVENT_IID);
					sb.append(" Button:"+e3.getButton());
					
					nsIDOMEventTarget t = event.getTarget();
					nsIDOMNode w = (nsIDOMNode)t.queryInterface(nsIDOMNode.NS_IDOMNODE_IID); //nsIDOMWindow, Doc, 
					sb.append(" Node name:"+w.getNodeName());
					
					log.info(sb.toString());
					
					
//				}
//				else if (event.getEventPhase() == event.BUBBLING_PHASE) {
//					log.info(TSForLogger.getTS(log)+"MOZ. DOMMouseScroll. BUBBLING_PHASE. Axis: "+ event2.getAxis()+" Type:"+event.getType()+" Cancelable:"+event.getCancelable()
//							+" ScrollX:"+webBrowser.getContentDOMWindow().getScrollX()+" ScrollY:"+webBrowser.getContentDOMWindow().getScrollY()
//							+ " Target:"+event.getTarget());
//				}
//				else if (event.getEventPhase() == event.AT_TARGET) {
//					StringBuffer sb = new StringBuffer();
//					sb.append(TSForLogger.getTS(log)+"MOZ. DOMMouseScroll. AT_TARGET. Axis: "+ event2.getAxis()+" Type:"+event.getType()+" Cancelable:"+event.getCancelable()
//							+" ScrollX:"+webBrowser.getContentDOMWindow().getScrollX()+" ScrollY:"+webBrowser.getContentDOMWindow().getScrollY());
//
//					nsIDOMMouseEvent e3 =(nsIDOMMouseEvent)event.queryInterface(nsIDOMMouseEvent.NS_IDOMMOUSEEVENT_IID);
//					sb.append(" Button:"+e3.getButton());
//					
//					nsIDOMEventTarget t = event.getTarget();
//					nsIDOMNode w = (nsIDOMNode)t.queryInterface(nsIDOMNode.NS_IDOMNODE_IID); //nsIDOMWindow, Doc, 
//					sb.append(" Node name:"+w.getNodeName());
//					
//					log.info(sb.toString());
//				}
				
			}
			
			// https://developer.mozilla.org/en/Gecko-Specific_DOM_Events#MozMousePixelScroll
			if ("MozMousePixelScroll".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. MozMousePixelScroll");
			}
			
			if ("broadcast".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. broadcast");
			}
			
			
			
			
//			new!
			if ("DOMWindowClose".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. DOMWindowClose");
			}
			if ("MozAfterPaint".equals(eventType)) {
//				log.info(TSForLogger.getTS(log)+"MOZ. MozAfterPaint");
			}
			
			if ("paint".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. paint");
			}
			
			if ("DOMWillOpenModalDialog".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. DOMWillOpenModalDialog");
			}
			if ("DOMModalDialogClosed".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. DOMModalDialogClosed");
			}
			if ("fullscreen".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. fullscreen");
			}
			if ("PopupWindow".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. PopupWindow");
			}
			if ("ValueChange".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. ValueChange");
			}
			if ("DOMMenuItemActive".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. DOMMenuItemActive");
			}
			if ("DOMMenuItemInactive".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. DOMMenuItemInactive");
			}
			if ("windowZLevel".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. windowZLevel");
			}
			
			//Clipboard
			if ("beforecut".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. beforecut");
			}
			if ("beforecopy".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. beforecopy");
			}
			if ("beforepaste".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. beforepaste");
			}
			
			//Mouse
			if ("drag".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. drag");
			}
			if ("dragstart".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. dragstart");
			}
			if ("dragenter".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. dragenter");
			}
			if ("dragover".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. dragover");
			}
			if ("dragleave".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. dragleave");
			}
			if ("dragend".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. dragend");
				
			}
			if ("drop".equals(eventType)) {
				
				nsIDOMDragEvent dragEv = (nsIDOMDragEvent)event.queryInterface(nsIDOMDragEvent.NS_IDOMDRAGEVENT_IID);
				
				log.info(TSForLog.getTS(log)+"MOZ. drop. Drop Effect:"+dragEv.getDataTransfer().getDropEffect()
						+" Data:"+dragEv.getDataTransfer().getData("text/plain"));
			}
			if ("selectstart".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. selectstart");
			}
			if ("dragdrop".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. dragdrop");
			}
			if ("dragexit".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. dragexit");
			}
			if ("draggesture".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. draggesture");
			}
			
			if ("CheckboxStateChange".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. CheckboxStateChange");
			}
			if ("RadioStateChange".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. RadioStateChange");
			}
			if ("close".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. close");
			}
			if ("input".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. input");
			}
			if ("command".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. command");
			}
			if ("commandupdate".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. commandupdate");
			}
			
			
			if ("beforeunload".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. beforeunload");
			}
			
			if ("overflow".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. overflow");
			}
			if ("overflowchanged".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. overflowchanged");
			}
			if ("underflow".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. underflow");
			}
			
			if ("popuphidden".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. popuphidden");
			}
			if ("popuphiding".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. popuphiding");
			}
			if ("popupshowing".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. popupshowing");
			}
			if ("popupshown".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. popupshown");
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			// HTML events
			// http://www.w3.org/TR/DOM-Level-2-Events/events.html
			// https://developer.mozilla.org/En/Listening_to_events
			
			if ("load".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. load");
			}
			
			if ("unload".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. unload");
//				event.
//				final nsIWebBrowser browser2 = (nsIWebBrowser)(browser.getMozillaBrowser().getWebBrowser());
//				browser2.getContentDOMWindow();
			}
			
			if ("pageshow".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. pageshow");
			}
			
			if ("pagehide".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. pagehide");
			}
			
			if ("abort".equals(eventType)) {
				nsIDOMEventTarget tagert = event.getTarget();
				nsIDOMNode node = (nsIDOMNode)tagert.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				log.info(TSForLog.getTS(log)+"MOZ. abort."+event.getTarget()+" node name:"+node.getNodeName()
						+" val:"+node.getNodeValue());
			}
			
			if ("error".equals(eventType)) {
				nsIDOMEventTarget tagert = event.getTarget();
				nsIDOMNode node = (nsIDOMNode)tagert.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				log.info(TSForLog.getTS(log)+"MOZ. error."+event.getTarget()+" node name:"+node.getNodeName()
						+" val:"+node.getNodeValue());
			}
			
			if ("select".equals(eventType)) {
				nsIDOMEventTarget tagert = event.getTarget();
				nsIDOMNode node = (nsIDOMNode)tagert.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				log.info(TSForLog.getTS(log)+"MOZ. select."+event.getTarget()+" node name:"+node.getNodeName()
						+" val:"+node.getNodeValue());
			}
			
			if ("change".equals(eventType)) {
				nsIDOMEventTarget tagert = event.getTarget();
				nsIDOMNode node = (nsIDOMNode)tagert.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				log.info(TSForLog.getTS(log)+"MOZ. change."+event.getTarget()+" node name:"+node.getNodeName()
						+" val:"+node.getNodeValue());
			}
			
			// form as a target
			if ("submit".equals(eventType)) {
				nsIDOMEventTarget tagert = event.getTarget();
				nsIDOMNode node = (nsIDOMNode)tagert.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				nsIDOMNode node2 = (nsIDOMNode)event.getCurrentTarget().queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				log.info(TSForLog.getTS(log)+"MOZ. submit. "+event.getTarget()+
						" node name:"+node.getNodeName() + " val:"+node.getNodeValue()
						+" node name:"+node2.getNodeName() + " val:"+node2.getNodeValue());
			}
			
			if ("reset".equals(eventType)) {
				nsIDOMEventTarget tagert = event.getTarget();
				nsIDOMNode node = (nsIDOMNode)tagert.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				log.info(TSForLog.getTS(log)+"MOZ. reset."+event.getTarget()+" node name:"+node.getNodeName()
						+" val:"+node.getNodeValue());
			}
			
			if ("focus".equals(eventType)) {
				nsIDOMEventTarget tagert = event.getTarget();
				nsIDOMNode node = (nsIDOMNode)tagert.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				log.info(TSForLog.getTS(log)+"MOZ. focus."+event.getTarget()+" node name:"+node.getNodeName()
						+" val:"+node.getNodeValue());
			}
			
			if ("blur".equals(eventType)) {
				nsIDOMEventTarget tagert = event.getTarget();
				nsIDOMNode node = (nsIDOMNode)tagert.queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
				log.info(TSForLog.getTS(log)+"MOZ. blur."+event.getTarget()+" node name:"+node.getNodeName()
						+" val:"+node.getNodeValue());
			}
			
			// does not work
			if ("resize".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. resize");
			}
			
			if ("scroll".equals(eventType)) {
//				nsIDOMMouseScrollEvent event2 = (nsIDOMMouseScrollEvent) event.queryInterface(nsIDOMMouseScrollEvent.NS_IDOMMOUSESCROLLEVENT_IID);
				nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
				StringBuffer sb = new StringBuffer(); 
				sb.append(TSForLog.getTS(log)+"MOZ. scroll. Type:"+event.getType()+" Cancelable:"+event.getCancelable()
						+" ScrollX:"+webBrowser.getContentDOMWindow().getScrollX()+" ScrollY:"+webBrowser.getContentDOMWindow().getScrollY());
				
				try {
				if (event.getTarget() != null) {
					nsIDOMNode tn = (nsIDOMNode)event.getTarget().queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
					sb.append(" target("+tn.getNodeName()+", "+tn.getNodeValue()+") ");
				}
				if (event.getCurrentTarget() != null) {
//					nsIDOMNode ctn = (nsIDOMNode)event.getCurrentTarget().queryInterface(nsIDOMNode.NS_IDOMNODE_IID);
//					sb.append(" curr target("+ctn.getNodeName()+", "+ctn.getNodeValue()+") ");
				}
				log.info(sb.toString());
				} catch(XPCOMException e) {
					System.err.println("EXSEPTION scrolling");
				}
					
			}
			
			// Key pressing
			if ("textinput".equals(eventType)) {
				nsIDOMKeyEvent ke = (nsIDOMKeyEvent)event.queryInterface(nsIDOMKeyEvent.NS_IDOMKEYEVENT_IID);
				log.info(TSForLog.getTS(log)+"MOZ. textInput. AltKey:"+ke.getAltKey()
						+" CharCode:"+ke.getCharCode() + " CharCode char:"+((char)ke.getCharCode())+
						" Detail:"+(ke.getDetail())+" KeyCode:"+ke.getKeyCode()+" KeyCode char:"+(char)ke.getKeyCode()+ " CtrlKey:"+ke.getCtrlKey()
						+" MetaKey:"+ke.getMetaKey()+" ShiftKey:"+ke.getShiftKey()+" Target:"+ke.getTarget());
			}
			
			if ("keydown".equals(eventType)) {
				nsIDOMKeyEvent ke = (nsIDOMKeyEvent)event.queryInterface(nsIDOMKeyEvent.NS_IDOMKEYEVENT_IID);
				log.info(TSForLog.getTS(log)+"MOZ. keydown. AltKey:"+ke.getAltKey()+" CharCode:"+ke.getCharCode()
						+" Detail:"+ke.getDetail()+" KeyCode:"+ke.getKeyCode()+" CtrlKey:"+ke.getCtrlKey()
						+" MetaKey:"+ke.getMetaKey()+" ShiftKey:"+ke.getShiftKey()+" Target:"+ke.getTarget());
			}
			
			if ("keyup".equals(eventType)) {
				nsIDOMKeyEvent ke = (nsIDOMKeyEvent)event.queryInterface(nsIDOMKeyEvent.NS_IDOMKEYEVENT_IID);
				log.info(TSForLog.getTS(log)+"MOZ. keyup. AltKey:"+ke.getAltKey()+" CharCode:"+ke.getCharCode()
						+ " CharCode char:"+((char)ke.getCharCode())
						+" Detail:"+ke.getDetail()+" KeyCode:"+ke.getKeyCode()+" KeyCode char:"+(char)ke.getKeyCode()+" CtrlKey:"+ke.getCtrlKey()
						+" MetaKey:"+ke.getMetaKey()+" ShiftKey:"+ke.getShiftKey()+" Target:"+ke.getTarget());
			}
			
			if ("keypress".equals(eventType)) {
				nsIDOMKeyEvent ke = (nsIDOMKeyEvent)event.queryInterface(nsIDOMKeyEvent.NS_IDOMKEYEVENT_IID);
				log.info(TSForLog.getTS(log)+"MOZ. keypress. AltKey:"+ke.getAltKey()+" CharCode:"+ke.getCharCode()
						+ " CharCode char:"+((char)ke.getCharCode())
						+" Detail:"+ke.getDetail()+" KeyCode:"+ke.getKeyCode()+" KeyCode char:"+(char)ke.getKeyCode()+" CtrlKey:"+ke.getCtrlKey()
						+" MetaKey:"+ke.getMetaKey()+" ShiftKey:"+ke.getShiftKey()+" Target:"+ke.getTarget());
				
				nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
				nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();
				
				
				nsIDOMWindowUtils wu = (nsIDOMWindowUtils)((nsIInterfaceRequestor)iDOMWindow
						.queryInterface(nsIInterfaceRequestor.NS_IINTERFACEREQUESTOR_IID))
						.getInterface(nsIDOMWindowUtils.NS_IDOMWINDOWUTILS_IID);
				
				
				
//				final Mozilla moz = Mozilla.getInstance();
//				nsIComponentManager componentManager = moz.getComponentManager();
//				nsICharsetConverterManager cm = (nsICharsetConverterManager)componentManager
//						.getClassObjectByContractID("@mozilla.org/charset-converter-manager;1"
//						, nsICharsetConverterManager.NS_ICHARSETCONVERTERMANAGER_IID)
//						.queryInterface(nsICharsetConverterManager.NS_ICHARSETCONVERTERMANAGER_IID);
//				cm.getUnicodeDecoder("UTF-8");
				
				
				final Mozilla moz = Mozilla.getInstance();
				nsIComponentManager componentManager = moz.getComponentManager();
				nsISimpleUnicharStreamFactory susf = (nsISimpleUnicharStreamFactory)componentManager
						.getClassObjectByContractID("@mozilla.org/xpcom/simple-unichar-stream-factory;1"
						, nsISimpleUnicharStreamFactory.NS_ISIMPLEUNICHARSTREAMFACTORY_IID)
						.queryInterface(nsISimpleUnicharStreamFactory.NS_ISIMPLEUNICHARSTREAMFACTORY_IID);
				
				String.valueOf((char)ke.getCharCode());
				
				nsIUnicharInputStream uis = susf.createInstanceFromString(String.valueOf((char)ke.getCharCode()));
				String[] strArr = new String[1];
				uis.readString(2, strArr);
				System.out.println("1:"+(char)ke.getCharCode()+" 2:"+strArr[0]+" 3:"+(char)ke.getCharCode());

				//nsISimpleUnicharStreamFactory.NS_ISIMPLEUNICHARSTREAMFACTORY_IID
				
//				final nsIDOMXPathEvaluator xpathEval = (nsIDOMXPathEvaluator) componentManager.createInstanceByContractID(
//						NS_IDOMXPATHEVALUATOR_CONTRACTID, null, nsIDOMXPathEvaluator.NS_IDOMXPATHEVALUATOR_IID);
				
				
				
				
				
			}
			
			
//			var manager = Components.classes["@mozilla.org/charset-converter-manager;1"].
//					getService(Components.interfaces.nsICharsetConverterManager);
//
//					var encoder=manager.getUnicodeEncoder("windows-1251");

					
					
					
					
			// does not work
			if ("compositionstart".equals(eventType)) {
//				nsIDOMKeyEvent ke = (nsIDOMKeyEvent)event.queryInterface(nsIDOMKeyEvent.NS_IDOMKEYEVENT_IID);
				log.info(TSForLog.getTS(log)+"MOZ. compositionstart");
			}
			
			
			if ("contextmenu".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. contextmenu");
			}
			
			if ("copy".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. copy");
			}
			if ("cut".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. cut");
			}
			if ("hashchange".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. hashchange");
			}
			if ("mousewheel".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. mousewheel");
			}
			if ("paste".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. paste");
			}
			if ("wheel".equals(eventType)) {
				log.info(TSForLog.getTS(log)+"MOZ. wheel");
			}
			
			
//			if ("pageshow".equals(eventType)
//					|| "DOMContentLoaded".equals(eventType)) {
//				log.info(TSForLogger.getTS(log)+"Page was shown or DOM tree was generated");
//			}
			
			
			
		}
				
		@Override
		public nsISupports queryInterface(String id) {
			return Mozilla.queryInterface(this, id);
		}
		
		private SelectionBox selBox = null;

		

		
		
//		private static final DOMNodeSelection domNodeSelection = new DOMNodeSelection(); 
//		
//		private static class DOMNodeSelection implements IDOMNodeSelection {
//
//			private boolean isEmpty;
//			private nsIDOMNode selectedNode;
//			
//			public DOMNodeSelection() {
//				this.isEmpty = true;
//				this.selectedNode = null;
//			}
//			
//			public DOMNodeSelection(boolean isEmpty, nsIDOMNode selectedNode) {
//				this.isEmpty = isEmpty;
//				this.selectedNode = selectedNode;
//			}
//			
//			public void setParams(boolean isEmpty, nsIDOMNode selectedNode) {
//				this.isEmpty = isEmpty;
//				this.selectedNode = selectedNode;
//			}
//			
//			@Override
//			public boolean isEmpty() {
//				return isEmpty;
//			}
//
//			@Override
//			public nsIDOMNode getSelectedNode() {
//				return selectedNode;
//			}
//			
//		}

}
