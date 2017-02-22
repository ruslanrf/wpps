/**
 * File name: PlayTest.java
 * @created: Oct 7, 2011 4:42:44 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.mozeventstest.browser;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIComponentManager;
import org.mozilla.interfaces.nsIDOMClientRect;
import org.mozilla.interfaces.nsIDOMDOMStringList;
import org.mozilla.interfaces.nsIDOMDocumentEvent;
import org.mozilla.interfaces.nsIDOMDocumentView;
import org.mozilla.interfaces.nsIDOMDragEvent;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMEvent;
import org.mozilla.interfaces.nsIDOMEventListener;
import org.mozilla.interfaces.nsIDOMEventTarget;
import org.mozilla.interfaces.nsIDOMHTMLFrameElement;
import org.mozilla.interfaces.nsIDOMKeyEvent;
import org.mozilla.interfaces.nsIDOMMouseEvent;
import org.mozilla.interfaces.nsIDOMMouseScrollEvent;
import org.mozilla.interfaces.nsIDOMNSDataTransfer;
import org.mozilla.interfaces.nsIDOMNSHTMLFrameElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeList;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIDOMWindowUtils;
import org.mozilla.interfaces.nsIDOMXPathEvaluator;
import org.mozilla.interfaces.nsIDOMXPathNSResolver;
import org.mozilla.interfaces.nsIDOMXPathResult;
import org.mozilla.interfaces.nsIInterfaceRequestor;
import org.mozilla.interfaces.nsISupports;
import org.mozilla.interfaces.nsIWebBrowser;
import org.mozilla.xpcom.Mozilla;

import tuwien.dbai.wpps.mozcommon.MozDomUtils;

//inIViewEventUtils veu = (inIViewEventUtils)
//
//Mozilla.getInstance().getServiceManager().getServiceByContractID(
//
//"@mozilla.org/inspector/view-event-utils;1", inIViewEventUtils.INIVIEWEVENTUTILS_IID);

//var utils = aWindow.QueryInterface(Components.interfaces.nsIInterfaceRequestor).
//getInterface(Components.interfaces.nsIDOMWindowUtils);

// nsIScriptLoaderObserver
// nsIXPCScriptNotify


/**
 * @created: Oct 7, 2011 4:42:44 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class PlayTest {
	private static final Logger log = Logger.getLogger(PlayTest.class);
	
	private final EMBrowserEditor browser;
	
	public PlayTest(EMBrowserEditor browser) {
		this.browser = browser;
	}
	
	public void play() {
		log.debug("Start play");
//		playMoz();
		// ya.ru
//		testClickOnButton("mousedown", "/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[3]/input");
//		testClickOnButton("click", "/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[3]/input");
//		sendHTMLEvent("submit", "/html/body/table/tbody/tr[2]/td/form");
		
//		sendHTMLEvent("compositionstart", "/html/body");
		
//		sendMouseScrollEvent2();
//		sendMouseScrollEvent("MozMousePixelScroll");
		
		// s7.ru
//		sendHTMLEvent("focus", "/html/body/div[@id='wrapper_760']/div[@id='main']/div/div[2]/div/div/div[1]/div[@id='tab_buy']/div[3]/div[2]/form/div[2]/input[@id='flight_departure']");
//		testClickOnButton("click",
//		"/html/body/div[@id='wrapper_760']/div[@id='main']/div/div[2]/div/div/div[1]/div[@id='tab_buy']/div[3]/div[2]/form/div[2]/input[@id='flight_departure']");
//		sendHTMLEvent("focus", "/html/body/div[3]/div[1]/table[1]/tbody[1]/tr[2]/td[7]");
//		testClickOnButton("click",
//				"/html/body/div[3]/div/table/tbody/tr[2]/td[3]/a");
				
		
		// checkfelix
//		testClickOnButton("mouseover",
//				"/html/body/div[@id='page']/div[@id='calframe']/table[@id='caltable']/tbody/tr[2]/td[1]/table/tbody/tr[@id='page0']/td[1]/table/tbody/tr[1]/td[2]/table[2]/tbody/tr[3]/td[4]");
//		testClickOnButton("click",
//				"/html/body/div[@id='page']/div[@id='calframe']/table[@id='caltable']/tbody/tr[2]/td[1]/table/tbody/tr[@id='page0']/td[1]/table/tbody/tr[1]/td[2]/table[2]/tbody/tr[3]/td[4]");
		
		// dblclickcheck
//		testClickOnButton("mousedown","/html/body");
//		testClickOnButton("mouseup","/html/body");
//		testClickOnButton("click","/html/body");
//		testClickOnButton("mousedown","/html/body");
//		testClickOnButton("mouseup","/html/body");
//		testClickOnButton("click","/html/body");
		
		// ya.ru
//		sendHTMLEvent("focus", "/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[2]/input[@id='text']");
//		testClickOnButton("click", "/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[2]/input[@id='text']");
//		keypressFull("/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[2]/input[@id='text']", 1092, 1092); // ф
//		keypressFull("/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[2]/input[@id='text']", 1060, 1060);
//		131072
//		1060 Ф
		
		scroll();
//		sendMouseScrollEvent2();
		
		// ya.ru
//		mouse2("/html/body/table/tbody/tr[3]/td/div/p/span","mousedown", 4, 4);
//		mouse2("/html/body/table/tbody/tr[3]/td/div/p/span","mousemove", 4, 4);
//		mouse2("/html/body/table/tbody/tr[3]/td/div/p/span","mousemove", 4, 3);
//		mouse2("/html/body/table/tbody/tr[3]/td/div/p/span","mousemove", 5, 2);
//		mouse2("/html/body/table/tbody/tr[3]/td/div/p/span","mousemove", 5, 1);
//		mouse2("/html/body/table/tbody/tr[3]/td/div/p/span","mousemove", 6, 1);
//		mouse2("/html/body/table/tbody/tr[3]/td/div/p/span","mousemove", 7, -5);
//		mouse2("/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[2]/input[@id='text']","mousemove", -5, 20);
//		mouse2("/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[2]/input[@id='text']","mousemove", -1, 15);
//		mouse2("/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[2]/input[@id='text']","mousemove", 1, 15);
//		mouse2("/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[2]/input[@id='text']","mousemove", 4, 4);
//		mouse2("/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[2]/input[@id='text']","mouseup", 4, 4);
		
//		mouse2("/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[3]/input","mousedown", 4, 4);
//		mouse2("/html/body/table/tbody/tr[2]/td/form/table/tbody/tr/td[3]/input","mouseup", 4, 4);
		
		
//		drag();
		
		
		// Works!
//		String[][] expectedDragData = new String[1][1];
//		expectedDragData[0][0]="text/plain: Privet!";
//		nsIDOMNSDataTransfer dt = synthesizeDragStart("/html/body/table/tbody/tr[3]/td/div/p/span", expectedDragData);
		
		
		
		
		
		
//		nsIDOMNodeList frames = browser.getDocument().getElementsByTagName("FRAME");
//		for (int i = 0; i < frames.getLength(); i++) {
//			nsIDOMHTMLFrameElement frame = (nsIDOMHTMLFrameElement) frames
//					.item(i).queryInterface(
//							nsIDOMHTMLFrameElement.NS_IDOMHTMLFRAMEELEMENT_IID);
//			nsIDOMEventTarget ft = (nsIDOMEventTarget)frame.queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
//			
//			nsIDOMWindow w = ((nsIDOMNSHTMLFrameElement)frame
//					.queryInterface(nsIDOMNSHTMLFrameElement.NS_IDOMNSHTMLFRAMEELEMENT_IID))
//					.getContentWindow();
//			
//			System.err.println(frame.getContentDocument().hashCode());
//		}
		
		
		
		
		
		
		
		
		log.debug("Finish play");
	}
	
	private void playMoz() {
		
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow(); //nsIDOMWindow2 iDOMWindow2; iDOMWindow2.
		nsIDOMDocumentEvent docEv = (nsIDOMDocumentEvent)iDOMWindow.getDocument()
				.queryInterface(nsIDOMDocumentEvent.NS_IDOMDOCUMENTEVENT_IID);
		
		
		nsIDOMMouseScrollEvent mouseScrollEv = (nsIDOMMouseScrollEvent)docEv.createEvent("MouseScrollEvents")
				.queryInterface(nsIDOMMouseScrollEvent.NS_IDOMMOUSESCROLLEVENT_IID);
		
		final nsIDOMDocumentView documentView = (nsIDOMDocumentView)iDOMWindow.getDocument()
				.queryInterface( nsIDOMDocumentView.NS_IDOMDOCUMENTVIEW_IID );
		
		mouseScrollEv.initMouseScrollEvent("DOMMouseScroll", true, true
				, documentView.getDefaultView() // can be null
				, 10, 0, 0, 0, 0, false, false, false, false, 0, null, nsIDOMMouseScrollEvent.VERTICAL_AXIS);
		nsIDOMEventTarget t = (nsIDOMEventTarget)iDOMWindow.getDocument().queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
		
		System.out.println(t.dispatchEvent((nsIDOMEvent)mouseScrollEv.queryInterface(nsIDOMEvent.NS_IDOMEVENT_IID)));
		
		nsIDOMEvent domEv =  (nsIDOMEvent)docEv.createEvent("HTMLEvents").queryInterface(nsIDOMEvent.NS_IDOMEVENT_IID);
		domEv.initEvent("scroll", true, true);
		System.out.println(t.dispatchEvent(domEv));
		
		
//		ev.initMouseScrollEvent(
//                event.type,
//                event.canBubble,
//                event.cancelable,
//                event.view,
//                event.detail,
//                event.screenX,
//                event.screenY,
//                event.clientX,
//                event.clientY,
//                event.ctrlKey,
//                event.altKey,
//                event.shiftKey,
//                event.metaKey,
//                event.button,
//                event.relatedTarget,
//                event.axis)
//            target.dispatchEvent(ev)
//        }
		
		
		
		
		
		
//		nsIDOMWindowUtils wu = (nsIDOMWindowUtils)((nsIInterfaceRequestor)iDOMWindow.queryInterface(nsIInterfaceRequestor.NS_IINTERFACEREQUESTOR_IID))
//				.getInterface(nsIDOMWindowUtils.NS_IDOMWINDOWUTILS_IID);
//		wu.sendMouseScrollEvent(arg0, arg1, arg2, arg3, arg4, arg5, arg6);
		
		
		
	}
	
	private void testClickOnButton(String evType, String xPathStr) {
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow(); //nsIDOMWindow2 iDOMWindow2; iDOMWindow2.
		nsIDOMDocumentEvent docEv = (nsIDOMDocumentEvent)iDOMWindow.getDocument()
				.queryInterface(nsIDOMDocumentEvent.NS_IDOMDOCUMENTEVENT_IID);
		
		final nsIDOMDocumentView documentView = (nsIDOMDocumentView)iDOMWindow.getDocument()
				.queryInterface( nsIDOMDocumentView.NS_IDOMDOCUMENTVIEW_IID );
		
		nsIDOMMouseEvent mouseEv = (nsIDOMMouseEvent)docEv.createEvent("MouseEvents")
				.queryInterface(nsIDOMMouseEvent.NS_IDOMMOUSEEVENT_IID);
		mouseEv.initMouseEvent(evType, true, true, documentView.getDefaultView()
				, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
		
		// ya.ru
		List<nsIDOMNode> nr = runXPath(xPathStr,iDOMWindow.getDocument());
		
		nsIDOMEventTarget t = (nsIDOMEventTarget)nr.get(0).queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
		t.dispatchEvent(mouseEv);
		
	}
	
	private void sendHTMLEvent(String evType, String xPathStr) {
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow(); //nsIDOMWindow2 iDOMWindow2; iDOMWindow2.
		nsIDOMDocumentEvent docEv = (nsIDOMDocumentEvent)iDOMWindow.getDocument()
				.queryInterface(nsIDOMDocumentEvent.NS_IDOMDOCUMENTEVENT_IID);
		
		nsIDOMEvent domEv =  (nsIDOMEvent)docEv.createEvent("HTMLEvents").queryInterface(nsIDOMEvent.NS_IDOMEVENT_IID);
		domEv.initEvent(evType, true, true);
		
		nsIDOMEventTarget t = null; 
				
		if (xPathStr == null) {
			t = (nsIDOMEventTarget)iDOMWindow.getDocument().queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
		}
		else {
			List<nsIDOMNode> nr = runXPath(xPathStr,iDOMWindow.getDocument());
			t = (nsIDOMEventTarget)nr.get(0).queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
			t.dispatchEvent(domEv);
		}
		
		System.out.println(t.dispatchEvent(domEv));
	}
	
	// does not throw DOMMouseScroll and ( and add listener to throw MozMousePixelScroll?)
	private void sendMouseScrollEvent2() {
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();
		
		nsIDOMDocumentEvent docEv = (nsIDOMDocumentEvent)iDOMWindow.getDocument()
				.queryInterface(nsIDOMDocumentEvent.NS_IDOMDOCUMENTEVENT_IID);
		
		nsIDOMMouseScrollEvent mouseScrollEv = (nsIDOMMouseScrollEvent)docEv.createEvent("MouseScrollEvents")
				.queryInterface(nsIDOMMouseScrollEvent.NS_IDOMMOUSESCROLLEVENT_IID);
		
		final nsIDOMDocumentView documentView = (nsIDOMDocumentView)iDOMWindow.getDocument()
				.queryInterface( nsIDOMDocumentView.NS_IDOMDOCUMENTVIEW_IID );
		
//		mouseScrollEv.initMouseScrollEvent("DOMMouseScroll", true, true
//				, documentView.getDefaultView() // can be null
//				, 10, 0, 0, 0, 0, false, false, false, false, 0, null, nsIDOMMouseScrollEvent.VERTICAL_AXIS);
//		nsIDOMEventTarget t = (nsIDOMEventTarget)iDOMWindow.getDocument().queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
		
//		t.dispatchEvent((nsIDOMEvent)mouseScrollEv.queryInterface(nsIDOMEvent.NS_IDOMEVENT_IID));
		
		nsIDOMWindowUtils wu = (nsIDOMWindowUtils)((nsIInterfaceRequestor)iDOMWindow
				.queryInterface(nsIInterfaceRequestor.NS_IINTERFACEREQUESTOR_IID))
				.getInterface(nsIDOMWindowUtils.NS_IDOMWINDOWUTILS_IID);
		wu.sendMouseScrollEvent("MozMousePixelScroll", 0, 0, 0, nsIDOMMouseScrollEvent.VERTICAL_AXIS | 0x08, 10, 0);
//		wu.sendMouseScrollEvent("DOMMouseScroll", 0, 0, 0, nsIDOMMouseScrollEvent.VERTICAL_AXIS, 10, 0);
		
	}
	
	// Does not work
	private void sendMouseScrollEvent(String evType) { // "MozMousePixelScroll"
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();
		
		nsIDOMDocumentEvent docEv = (nsIDOMDocumentEvent)iDOMWindow.getDocument()
				.queryInterface(nsIDOMDocumentEvent.NS_IDOMDOCUMENTEVENT_IID);
		
		nsIDOMMouseScrollEvent mse =  (nsIDOMMouseScrollEvent)docEv.createEvent("MouseScrollEvents")
				.queryInterface(nsIDOMMouseScrollEvent.NS_IDOMMOUSESCROLLEVENT_IID);

		final nsIDOMDocumentView documentView = (nsIDOMDocumentView)iDOMWindow.getDocument()
				.queryInterface( nsIDOMDocumentView.NS_IDOMDOCUMENTVIEW_IID );
		
		mse.initMouseScrollEvent(evType, true, true, documentView.getDefaultView() // can be null
				, 10, 0, 0, 0, 0, false, false, false, false, 0, null, nsIDOMMouseScrollEvent.VERTICAL_AXIS);
		
//		mouseScrollEv.initMouseScrollEvent("DOMMouseScroll", true, true
//				, documentView.getDefaultView() // can be null
//				, 10, 0, 0, 0, 0, false, false, false, false, 0, null, nsIDOMMouseScrollEvent.VERTICAL_AXIS);
		
		nsIDOMEventTarget t = (nsIDOMEventTarget)iDOMWindow.getDocument().queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
		
		System.out.println(t.dispatchEvent((nsIDOMEvent)mse.queryInterface(nsIDOMEvent.NS_IDOMEVENT_IID)));
		
	}
	
	// +change +focus
	private void keypressFull(String xPathStr, long keyCode, long charCode) {
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();
		
		nsIDOMDocumentEvent docEv = (nsIDOMDocumentEvent)iDOMWindow.getDocument()
				.queryInterface(nsIDOMDocumentEvent.NS_IDOMDOCUMENTEVENT_IID);
		
		nsIDOMKeyEvent keyEv = (nsIDOMKeyEvent)docEv.createEvent("KeyEvents")
				.queryInterface(nsIDOMKeyEvent.NS_IDOMKEYEVENT_IID);
		
		final nsIDOMDocumentView documentView = (nsIDOMDocumentView)iDOMWindow.getDocument()
				.queryInterface( nsIDOMDocumentView.NS_IDOMDOCUMENTVIEW_IID );
		
		List<nsIDOMNode> nr = runXPath(xPathStr,iDOMWindow.getDocument());
		
		nsIDOMEventTarget t =(nsIDOMEventTarget)nr.get(0).queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
		
		// https://developer.mozilla.org/en/DOM/event.initKeyEvent
		keyEv.initKeyEvent("keydown", true, true, documentView.getDefaultView() // may be null
				, false, false, true, false, keyCode, charCode);
		
		t.dispatchEvent((nsIDOMEvent)keyEv.queryInterface(nsIDOMEvent.NS_IDOMEVENT_IID));
		
		keyEv.getCharCode();
		keyEv.initKeyEvent("keypress", true, true, documentView.getDefaultView() // may be null
				, false, false, true, false, keyCode, charCode);
		
		t.dispatchEvent((nsIDOMEvent)keyEv.queryInterface(nsIDOMEvent.NS_IDOMEVENT_IID));
		
		keyEv.initKeyEvent("keyup", true, true, documentView.getDefaultView() // may be null
				, false, false, true, false, keyCode, charCode);
		
		t.dispatchEvent((nsIDOMEvent)keyEv.queryInterface(nsIDOMEvent.NS_IDOMEVENT_IID));
		
	}
	
	// use it + additional fictitious events
	private void scroll() {
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();
		
		iDOMWindow.scrollBy(10, 10);
	}
	   
	private List<nsIDOMNode> runXPath(String xPath, nsIDOMNode doc) {
		/* XPATH evaluator */
		final String NS_IDOMXPATHEVALUATOR_CONTRACTID = 
			"@mozilla.org/dom/xpath-evaluator;1";
		
		final Mozilla moz = Mozilla.getInstance();
		nsIComponentManager componentManager = moz.getComponentManager();
		final nsIDOMXPathEvaluator xpathEval = (nsIDOMXPathEvaluator) componentManager.createInstanceByContractID(
				NS_IDOMXPATHEVALUATOR_CONTRACTID, null, nsIDOMXPathEvaluator.NS_IDOMXPATHEVALUATOR_IID);
		
		
		nsIDOMXPathNSResolver res = xpathEval.createNSResolver(doc);
		List<nsIDOMNode> resultNodes = new ArrayList<nsIDOMNode>();

		// Evaluates given XPath in a given context, using the resolver created
		// for the current document as an ordered iterator
		nsISupports obj = xpathEval.evaluate(xPath, doc, res, 
				nsIDOMXPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
		// Obtain the interface corresponding to the XPath XPCOM results object
		nsIDOMXPathResult result = (nsIDOMXPathResult) obj.queryInterface(
				nsIDOMXPathResult.NS_IDOMXPATHRESULT_IID);

		try {
			
			// Extract result nodes for the XPath and add them
			// to a standard List. 

			nsIDOMNode node;
			while((node = result.iterateNext()) != null){
				resultNodes.add(node);
			}
			
		} catch(org.mozilla.xpcom.XPCOMException e){
			throw e;
		}               
		return resultNodes;
	}
	
	
	private void mouse2(String xPathTarget, String evName, int dx, int dy) {
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();

//		nsISelection sel = webBrowser.getContentDOMWindow().getSelection();
		
		List<nsIDOMNode> nr = runXPath(xPathTarget,iDOMWindow.getDocument());
		nsIDOMClientRect cr = MozDomUtils.getBoundingClientRectFor(nr.get(0));
		if (nr.get(0) == null)System.err.println("xpath retirns null");
		System.err.println(nr.get(0).getNodeName());
		
		nsIDOMWindowUtils wu = (nsIDOMWindowUtils)((nsIInterfaceRequestor)iDOMWindow
				.queryInterface(nsIInterfaceRequestor.NS_IINTERFACEREQUESTOR_IID))
				.getInterface(nsIDOMWindowUtils.NS_IDOMWINDOWUTILS_IID);
		
		
//		System.out.println(browser.getMozillaBrowser().getBounds().x);
//		System.out.println(browser.getMozillaBrowser().getClientArea().x);
//		System.out.println(browser.getMozillaBrowser().getLocation().x);
//		System.out.println(browser.getMozillaBrowser().getDisplay().getBounds().x);
		
		nsIDOMElement el = wu.elementFromPoint((int)(iDOMWindow.getScrollX()+cr.getLeft()+4), (int)(iDOMWindow.getScrollY()+cr.getTop()+4), false, false);
		System.err.println(el.getNodeName());
		
		
		wu.sendMouseEvent(evName, iDOMWindow.getScrollX()+cr.getLeft()+dx, iDOMWindow.getScrollY()+cr.getTop()+dy
				, 0, 1, 0, true);
		
		
		
		
//		wu.sendMouseEvent(evName, arg1, arg2, 0, 1, 0, false);
		
	}
	
	
	
	public class DragListener implements nsIDOMEventListener {

		private final String[][] expectedDragData;
		private nsIDOMNSDataTransfer failed;
		
		public DragListener(String[][] expectedDragData, nsIDOMNSDataTransfer failed) {
			this.expectedDragData = expectedDragData;
			this.failed = failed;
		}
		
		@Override
		public nsISupports queryInterface(String id) {
			return Mozilla.queryInterface(this, id);
		}

		@Override
		public void handleEvent(nsIDOMEvent aevent) {
			System.err.println("drag!");
			nsIDOMNSDataTransfer dataTransfer = null;
			try {
				nsIDOMDragEvent event = (nsIDOMDragEvent)aevent.queryInterface(nsIDOMDragEvent.NS_IDOMDRAGEVENT_IID);
				System.err.println("drag2!");
			      dataTransfer = (nsIDOMNSDataTransfer)event.getDataTransfer()
			    		  .queryInterface(nsIDOMNSDataTransfer.NS_IDOMNSDATATRANSFER_IID);
			      if (dataTransfer.getMozItemCount() != expectedDragData.length)
			        throw new Exception("Failed");

			      for (int t = 0; t < dataTransfer.getMozItemCount(); t++) {
			        nsIDOMDOMStringList types = dataTransfer.mozTypesAt(t);
			        String[] expecteditem = expectedDragData[t];
			        if (types.getLength() != expecteditem.length)
			        	throw new Exception("Failed");

			        for (int f = 0; f < types.getLength(); f++) {
			          if (types.item(f) != expecteditem[f].substring(0, types.item(f).length()) ||
			              !dataTransfer.mozGetDataAt(types.item(f), t).toString().equals(
			               expecteditem[f].substring(types.item(f).length() + 2)))
			        	  throw new Exception("Failed");
			        }
			      }
			      
			    } catch(Exception ex) {
			      failed = dataTransfer;
			    }

			aevent.preventDefault();
			aevent.stopPropagation();
		}
		
	}
	
	private nsIDOMNSDataTransfer synthesizeDragStart(String xPathTarget, String[][] expectedDragData)
	{
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();
		final nsIDOMEventTarget domWindowTarget = (nsIDOMEventTarget)iDOMWindow.queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
		
		
		nsIDOMNSDataTransfer failed = null;
//	  var trapDrag = function(event) {
//	  }

		DragListener dl = new DragListener(expectedDragData, failed);
		
	  domWindowTarget.addEventListener("dragstart", dl, false);
	  
	  nsIDOMWindowUtils wu = (nsIDOMWindowUtils)((nsIInterfaceRequestor)iDOMWindow
				.queryInterface(nsIInterfaceRequestor.NS_IINTERFACEREQUESTOR_IID))
				.getInterface(nsIDOMWindowUtils.NS_IDOMWINDOWUTILS_IID);
	  
	  
	  
	  List<nsIDOMNode> nr = runXPath(xPathTarget,iDOMWindow.getDocument());
//	  nsIDOMElement element = (nsIDOMElement)nr.get(0).queryInterface(nsIDOMElement.NS_IDOMELEMENT_IID);
	  nsIDOMClientRect cr = MozDomUtils.getBoundingClientRectFor(nr.get(0));
		
//	  synthesizeMouse(element, 2, 2, { type: "mousedown" });
		wu.sendMouseEvent("mousedown", //iDOMWindow.getScrollX()+
				cr.getLeft()+5, //iDOMWindow.getScrollY()+
				cr.getTop()+5
				, 0, 1, 0, true);
//	  synthesizeMouse(element, 9, 9, { type: "mousemove" });
		wu.sendMouseEvent("mousemove", //iDOMWindow.getScrollX()+
				cr.getLeft()+9, //iDOMWindow.getScrollY()+
				cr.getTop()+9
				, 0, 1, 0, true);
//	  synthesizeMouse(element, 10, 10, { type: "mousemove" });
	  wu.sendMouseEvent("mousemove", //iDOMWindow.getScrollX()+
			  cr.getLeft()+10, //iDOMWindow.getScrollY()+
			  cr.getTop()-20
				, 0, 1, 0, true);
	  domWindowTarget.removeEventListener("dragstart", dl, false);
//	  synthesizeMouse(element, 10, 10, { type: "mouseup" });
	  wu.sendMouseEvent("mouseup", //iDOMWindow.getScrollX()+
			  cr.getLeft()+10, //iDOMWindow.getScrollY()+
			  cr.getTop()-20
				, 0, 1, 0, true);

	  return failed;
	}

	
	private void drag(String xPathTarget) {
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getMozillaBrowser().getWebBrowser();
		nsIDOMWindow iDOMWindow = webBrowser.getContentDOMWindow();
		
		nsIDOMDocumentEvent docEv = (nsIDOMDocumentEvent)iDOMWindow.getDocument()
				.queryInterface(nsIDOMDocumentEvent.NS_IDOMDOCUMENTEVENT_IID);
		
		nsIDOMDragEvent mse =  (nsIDOMDragEvent)docEv.createEvent("DragEvents") //MouseScrollEvents
			.queryInterface(nsIDOMDragEvent.NS_IDOMDRAGEVENT_IID);
		
//		mse.initDragEvent("dragstart", true, true, null, arg4, arg5)
		
		List<nsIDOMNode> nr = runXPath(xPathTarget,iDOMWindow.getDocument());
		nsIDOMEventTarget t =(nsIDOMEventTarget)nr.get(0).queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
		t.dispatchEvent(mse);
		
		System.out.println(mse.getButton());
		
	}

}
