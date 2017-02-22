/**
 * File name: MozDomUtils.java
 * @created: Oct 12, 2010 8:09:22 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.mozcommon;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.mozilla.dom.DocumentImpl;
import org.mozilla.dom.html.HTMLDocumentImpl;
import org.mozilla.interfaces.nsIBoxObject;
import org.mozilla.interfaces.nsIDOMCSS2Properties;
import org.mozilla.interfaces.nsIDOMCSSStyleDeclaration;
import org.mozilla.interfaces.nsIDOMClientRect;
import org.mozilla.interfaces.nsIDOMClientRectList;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMDocumentTraversal;
import org.mozilla.interfaces.nsIDOMDocumentView;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMHTMLButtonElement;
import org.mozilla.interfaces.nsIDOMHTMLElement;
import org.mozilla.interfaces.nsIDOMHTMLInputElement;
import org.mozilla.interfaces.nsIDOMHTMLSelectElement;
import org.mozilla.interfaces.nsIDOMHTMLTextAreaElement;
import org.mozilla.interfaces.nsIDOMNSElement;
import org.mozilla.interfaces.nsIDOMNSHTMLElement;
import org.mozilla.interfaces.nsIDOMNSHTMLFrameElement;
import org.mozilla.interfaces.nsIDOMNamedNodeMap;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeFilter;
import org.mozilla.interfaces.nsIDOMNodeList;
import org.mozilla.interfaces.nsIDOMTreeWalker;
import org.mozilla.interfaces.nsIDOMViewCSS;
import org.mozilla.interfaces.nsIDOMWindow;
import org.mozilla.interfaces.nsIDOMWindowInternal;
import org.mozilla.interfaces.nsISupports;
import org.w3c.dom.Document;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.TypeCastUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.common.html.ECSSPropertyConstants;
import tuwien.dbai.wpps.common.html.EHtmlElementConstants;

import com.google.common.base.Preconditions;

/**
 * 
 * Utils for Mozilla DOM elements.
 * Get some interfaces for DOM elements or another objects or data, related to the element
 * 
 * @type: MozDomUtils
 *
 * @created: Oct 12, 2010 8:09:22 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
final public class MozDomUtils {
	private static final Logger log = Logger.getLogger(MozDomUtils.class);
	
	// ==============================
	//      	 CSS
	// ==============================
	
	public static final nsIDOMViewCSS getDOMViewCSS(final nsIDOMDocument doc) {
		final nsIDOMDocumentView documentView = (nsIDOMDocumentView)doc
			.queryInterface( nsIDOMDocumentView.NS_IDOMDOCUMENTVIEW_IID );
		final nsIDOMViewCSS viewCss = (nsIDOMViewCSS)documentView.getDefaultView()
			.queryInterface( nsIDOMViewCSS.NS_IDOMVIEWCSS_IID );
		return viewCss;
	}
	
	public static final nsIDOMViewCSS getDOMViewCSS(final nsIDOMElement el) {
		return getDOMViewCSS(el.getOwnerDocument());
	}
	
	public static final nsIDOMCSSStyleDeclaration getComputedStyle(final nsIDOMElement el) {
		final nsIDOMViewCSS viewCss = getDOMViewCSS(el);
		final nsIDOMCSSStyleDeclaration sd = viewCss.getComputedStyle(el, null);
		return sd;
	}
	
	/**
	 * Returns computed style for particular element of DOM tree
	 * @param el
	 * @return
	 */
	public static final nsIDOMCSS2Properties getComputedCSSProperties(final nsIDOMElement el) {
	final nsIDOMViewCSS viewCss = getDOMViewCSS(el);
	final nsIDOMCSS2Properties css2Prop = (nsIDOMCSS2Properties)viewCss.getComputedStyle(el, null)
			.queryInterface(nsIDOMCSS2Properties.NS_IDOMCSS2PROPERTIES_IID);
		return css2Prop;
	}
	
	/**
	 * Expensive!
	 * @param el
	 * @param viewCss
	 * @return
	 */
	public static final nsIDOMCSS2Properties getComputedCSSProperties(final nsIDOMElement el, final nsIDOMViewCSS viewCss) {
		final nsIDOMCSSStyleDeclaration compStyle = viewCss.getComputedStyle(el, null);
		if (compStyle == null) {
			log.warn("Element "+el.getLocalName()+" does not have a css style");
if (log.isTraceEnabled())
	log.trace(((nsIDOMNSHTMLElement)el.getOwnerDocument().getDocumentElement().queryInterface(nsIDOMNSHTMLElement.NS_IDOMNSHTMLELEMENT_IID)).getInnerHTML());
			return null;
		}
		final nsIDOMCSS2Properties css2Prop = (nsIDOMCSS2Properties)compStyle.queryInterface(nsIDOMCSS2Properties.NS_IDOMCSS2PROPERTIES_IID);
			return css2Prop;
		}
	
	// ==============================
	// 
	// ==============================
	
	
	
	// ==============================
	//       get bounding rectangles
	// ==============================
	
	/**
	 * <code><a href="https://developer.mozilla.org/en/XPCOM_Interface_Reference/nsIDOMClientRect">nsIDOMClientRect</a></code>
	 * provides important fields of the spatial location and expansion
	 * of the visualized element, such as
	 * <ul>
	 * <li><code>bottom</code> --- Y-coordinate, relative to the viewport origin, of the bottom of the rectangle box.</li>
	 * <li><code>height</code> --- Height of the rectangle box (This is identical to bottom minus top).</li>
	 * <li><code>left</code> --- X-coordinate, relative to the viewport origin, of the left of the rectangle box.</li>
	 * <li><code>right</code> --- X-coordinate, relative to the viewport origin, of the right of the rectangle box.</li>
	 * <li><code>top</code> --- Y-coordinate, relative to the viewport origin, of the top of the rectangle box.</li>
	 * <li><code>width</code> --- Width of the rectangle box (This is identical to right minus left).</li>
	 * </ul>
	 */
	public static final nsIDOMClientRect getBoundingClientRectFor(final nsIDOMNode node) {
		final nsIDOMNSElement el = (nsIDOMNSElement)node.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
		return el.getBoundingClientRect();
	}
	
	/**
	 * Get list of client rectangles which is used to provide more precise description of occupied space. 
	 */
	public static final nsIDOMClientRectList getBoundingClientRectListFor(final nsIDOMNode node) {
		final nsIDOMNSElement el = (nsIDOMNSElement)node.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
		return el.getClientRects();
	}
	
	/**
	 * @param el
	 * @param window
	 * @return get coordinates related to the top-left corner of DOM window (current {@linkplain nsIDOMWindow}).
	 */
	public static final Rectangle2D getBoxCoordinates(final nsIDOMElement el, final nsIDOMWindow window) {
		final nsIDOMNSElement el2 = (nsIDOMNSElement)el.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
		// get coordinates related to the viewport
		final nsIDOMClientRect rect = el2.getBoundingClientRect();
		return new Rectangle2D(rect.getLeft()+window.getScrollX()
				, rect.getTop()+window.getScrollY()
				, rect.getRight()+window.getScrollX()
				, rect.getBottom()+window.getScrollY());
	}
	
	/**
	 * Method {@link #getBoxCoordinates(nsIDOMElement, nsIDOMWindow)} is better.
	 * @param el
	 * @return
	 */
	public static final Rectangle2D getBoxCoordinates(nsIDOMElement element) {
		nsIDOMNSElement nselement = (nsIDOMNSElement) element
				.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
		nsIDOMClientRect rect = nselement.getBoundingClientRect();

		/*
		 * Getting the root element of the document (assuming HTML) and using
		 * its Screen coordinates as the origin.
		 */
		nsIDOMDocument rootdocument = element.getOwnerDocument(); // since the
																	// SelectionBox
																	// DIV is in
																	// the root
																	// document
		nsIDOMElement rootElement = rootdocument.getDocumentElement(); // should
																		// be
																		// the
																		// HTML
																		// element
		nsIDOMNSElement rootnsdocument = (nsIDOMNSElement) rootElement
				.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
		nsIDOMClientRect rootBox = rootnsdocument.getBoundingClientRect();
		int originX = (int) rootBox.getLeft();
		int originY = (int) rootBox.getTop();

		// return new Rectangle((re - originX), (box.getScreenY() - originY),
		// box.getWidth(), box.getHeight());

		return new Rectangle2D((int) rect.getLeft() - originX,
				(int) rect.getTop() - originY, (int) rect.getWidth(),
				(int) rect.getHeight());
	}
	
	public static Point2D[] getBoxCoordinatesToViewport(nsIDOMElement el) {
		final nsIDOMNSElement el2 = (nsIDOMNSElement)el.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
		final nsIDOMClientRect rect = el2.getBoundingClientRect();
		Point2D[] pArr = new Point2D[2];
		pArr[0] = new Point2D(rect.getLeft(), rect.getTop());
		pArr[1] = new Point2D(rect.getRight(), rect.getBottom());
		return pArr;
	}
	
	/**
	 * Coordinates of an inner block which are computed based on coordinates of a corresponding box and its CSS properties.
	 * @param boxCoors
	 * @param cssProps
	 * @return
	 */
	public static final Rectangle2D getInnerBlockCoordinates(final Rectangle2D boxCoors, final nsIDOMCSS2Properties cssProps) {
		final Rectangle2D rez = new Rectangle2D();
		rez.xMin = boxCoors.xMin
				+Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getBorderLeftWidth()));
		rez.yMin = boxCoors.yMin
				+Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getBorderTopWidth()));
		rez.xMax = boxCoors.xMax
				-Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getBorderRightWidth()));
		rez.yMax = boxCoors.yMax
				-Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getBorderBottomWidth()));
		return rez;
	}
	
//	@Deprecated
//	public static final Point2D[] getInnerBlockCoordinates(final Point2D[] boxCoors, final nsIDOMCSS2Properties cssProps) {
//		final Point2D[] rez = Point2DUtils.getUndefinedPointArr(2);
//		rez[0].x = boxCoors[0].x
//				+Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getBorderLeftWidth()));
//		rez[0].y = boxCoors[0].y
//				+Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getBorderTopWidth()));
//		rez[1].x = boxCoors[1].x
//				-Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getBorderRightWidth()));
//		rez[1].y = boxCoors[1].y
//				-Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getBorderBottomWidth()));
//		return rez;
//	}
	
	// ==============================
	// 
	// ==============================
	
	// ==============================
	//  Viewport
	// ==============================
	
	/**
	 * @param domWindow
	 * @return top-left and bottom-right coordinates of the nsIDOMWindow's viewport.
	 * Top-left corner of a page is a origin of coordinates.
	 */
	public static Rectangle2D getViewPortCoordinates(nsIDOMWindow domWindow) {
		Point2D[] pArr = new Point2D[2];
		pArr[0] = new Point2D(domWindow.getScrollX(), domWindow.getScrollY());
		final nsIDOMWindowInternal wi = (nsIDOMWindowInternal)domWindow.queryInterface(nsIDOMWindowInternal.NS_IDOMWINDOWINTERNAL_IID);
		pArr[1] = new Point2D(pArr[0].x+wi.getInnerWidth(), pArr[0].y+wi.getInnerHeight());
		return new Rectangle2D(pArr[0], pArr[1]);
	}
	
	/**
	 * Size of a viewport does not include scroll bar.
	 * @param domWindow
	 * @return
	 */
	public static Point2D getViewportSize(nsIDOMWindow domWindow) {
		final nsIDOMWindowInternal wi = (nsIDOMWindowInternal)domWindow
				.queryInterface(nsIDOMWindowInternal.NS_IDOMWINDOWINTERNAL_IID);
		return new Point2D(wi.getInnerWidth(), wi.getInnerHeight());
	}
	
	// ==============================
	//  DOM Window
	// ==============================
	
	/**
	 * Get size of DOM window which includes scrollbars
	 * @param domWindow
	 * @return
	 */
	public static Point2D getDOMWindowSizeWithScrollBars(nsIDOMWindow domWindow) {
		final nsIDOMWindowInternal wi = (nsIDOMWindowInternal)domWindow.queryInterface(nsIDOMWindowInternal.NS_IDOMWINDOWINTERNAL_IID);
		return new Point2D(wi.getScrollMaxX()+wi.getInnerWidth(), wi.getScrollMaxY() + wi.getInnerHeight());
	}
	
	// ==============================
	// 
	// ==============================
	
	// ==============================
	//  Document traversal
	// ==============================
	
	public static nsIDOMTreeWalker getDomTreeWalker(final nsIDOMWindow window) {
		final nsIDOMDocumentTraversal traversal = (nsIDOMDocumentTraversal)
				window.getDocument().queryInterface(nsIDOMDocumentTraversal.NS_IDOMDOCUMENTTRAVERSAL_IID);
		return traversal.createTreeWalker(window.getDocument(), nsIDOMNodeFilter.SHOW_ELEMENT, null, true);
	}
	
	// ==============================
	// 
	// ==============================
	
	// ==============================
	// 
	// ==============================
	
	/**
	 * Convert a W3C HTML Document implementation corresponding to
	 * the Mozilla DOM HTML document currently loaded in the browser.
	 */
	public static final Document getW3CDocument(nsIDOMDocument mozDocument) {
		DocumentImpl mozDocumentImpl = HTMLDocumentImpl.getDOMInstance(mozDocument);
		return (Document) mozDocumentImpl;
	}
	
	// ==============================
	// 
	// ==============================
	
	// ==============================
	//    MIX
	// ==============================
	
	// TODO: change! There is a similar function from ATF exists.
	@Deprecated
	public static final String ATF_IINTERNAL_ELEMENT_CLASS = "___ATF_INTERNAL";
	@Deprecated
	public static final boolean isATFInternalElement(final nsIDOMNode node) {
//		String a = MozIDEUIPlugin.ATF_INTERNAL;
		final nsIDOMNamedNodeMap map = node.getAttributes();
		if (map == null)
			return false;
		final nsIDOMNode classAttr = map
				.getNamedItem(EHtmlElementConstants.CLASS_ATTRIBUTE.string());
		if (classAttr == null)
			return false;
		return ATF_IINTERNAL_ELEMENT_CLASS.equals(classAttr.getNodeValue());
	}
	
	/**
	 * @param frame
	 * @param props CSS properties
	 * @return Frame's DOM window offset relative to the viewport of the frame's DOM Window.
	 */
	public static Point2D getFrameDOMWindowOffsetToViewPort(final nsIDOMElement frame, final nsIDOMCSS2Properties props) {
		final Point2D rez = Point2D.getUndefinedPoint();
		final nsIDOMNSElement framens = (nsIDOMNSElement)frame.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
		final nsIDOMClientRect rect = framens.getBoundingClientRect();
		
		if (EHtmlElementConstants.FRAME.string().equalsIgnoreCase(frame.getLocalName())) {
			rez.x = rect.getLeft();
			rez.y = rect.getTop();
		}
		else if (EHtmlElementConstants.IFRAME.string().equalsIgnoreCase(frame.getLocalName())) {
			rez.x = rect.getLeft()+framens.getClientLeft()+
					Float.parseFloat(MozStringUtils.getNumericalPartWOPx(props.getPaddingLeft()));
			rez.y = rect.getTop()+framens.getClientTop()+
					Float.parseFloat(MozStringUtils.getNumericalPartWOPx(props.getPaddingTop()));
			
		}
		else
			throw new UnknownValueFromPredefinedList(log, frame.getLocalName());
		return rez;
	}
	
	/**
	 * @param frame
	 * @param props
	 * @param domWindow
	 * @return Frame's DOM window offset relative to frame's DOM Window.
	 */
	public static Point2D getFrameDOMWindowOffset(final nsIDOMElement frame, final nsIDOMCSS2Properties props
			, final nsIDOMWindow domWindow) {
		final Point2D rez = Point2D.getUndefinedPoint();
		final nsIDOMNSElement framens = (nsIDOMNSElement)frame.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
		final nsIDOMClientRect rect = framens.getBoundingClientRect();
		
		if (EHtmlElementConstants.FRAME.string().equalsIgnoreCase(frame.getLocalName())) {
			rez.x = domWindow.getScrollX()+rect.getLeft()+framens.getClientLeft();
			rez.y = domWindow.getScrollY()+rect.getTop()+framens.getClientTop();
		}
		else if (EHtmlElementConstants.IFRAME.string().equalsIgnoreCase(frame.getLocalName())) {
			rez.x =  domWindow.getScrollX()+rect.getLeft()+framens.getClientLeft()+
					Float.parseFloat(MozStringUtils.getNumericalPartWOPx(props.getPaddingLeft()));
			rez.y = domWindow.getScrollY()+rect.getTop()+framens.getClientTop()+
					Float.parseFloat(MozStringUtils.getNumericalPartWOPx(props.getPaddingTop()));
			
		}
		else
			throw new UnknownValueFromPredefinedList(log, frame.getLocalName());
		return rez;
	}
	
	/**
	 * Get frame's view port relative to the left top corner of the current DOM window.
	 * @param frameCoords frame's coordinates relative to the DOM-Window's left-top corner (0, 0). This coordinates can be gotten from {@linkplain #getBoxCoordinates(nsIDOMElement, nsIDOMWindow)}.
	 * @param frame
	 * @param props
	 * @return coordinates relative to the DOM-Window left-top corner (0, 0).
	 */
	public static Point2D getFrameViewPortOffset(final Rectangle2D frameCoords, final nsIDOMElement frame
			, final nsIDOMCSS2Properties props) {
		final Point2D rez = Point2D.getUndefinedPoint();
		final nsIDOMNSElement framens = (nsIDOMNSElement)frame.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
//if (log.isTraceEnabled()) log.trace("getFrameViewPortOffset:: frame:"+frame.getLocalName());
		if ( EHtmlElementConstants.FRAME.equalTo(frame.getLocalName().toUpperCase()) ) {
			rez.x = frameCoords.xMin+framens.getClientLeft();
			rez.y = frameCoords.yMin+framens.getClientTop();
		}
		else if (EHtmlElementConstants.IFRAME.equalTo(frame.getLocalName().toUpperCase()) ) {
			rez.x =  frameCoords.xMin+framens.getClientLeft()+
					Float.parseFloat(MozStringUtils.getNumericalPartWOPx(props.getPaddingLeft()));
			rez.y = frameCoords.yMin+framens.getClientTop()+
					Float.parseFloat(MozStringUtils.getNumericalPartWOPx(props.getPaddingTop()));
			
		}
		else
			throw new UnknownValueFromPredefinedList(log, frame.getLocalName());
		return rez;
	}
	
	
	/**
	 * Check visibility of element looking at CSS attributes.
	 * @param element
	 * @param cssProps
	 * @return
	 */
	public static boolean checkElementCSSVisibility(final nsIDOMElement element, final nsIDOMCSS2Properties cssProps) {
		if (!ECSSPropertyConstants.DISPLAY_NONE_VALUE.string().equals(cssProps.getDisplay())
				&& ECSSPropertyConstants.VISIBILITY_VISIBLE_VALUE.string().equals(cssProps.getVisibility()))
			return true;
		else
			return false;
	}
	
	
//	/**
//	 * Function checks only value of CSS attribute Display.
//	 * @param element
//	 * @param cssProps
//	 * @return
//	 */
//	public static boolean areChildElementsVisible(final nsIDOMElement element, final nsIDOMCSS2Properties cssProps) {
//		return !ECSSPropertyConstants.DISPLAY_NONE_VALUE.equalTo(cssProps.getDisplay());
//		
////		final boolean hidden = ECSSPropertyStringConstants.VISIBILITY_HIDDEN_VALUE.string().equals(cssProps.getVisibility());
////		if (ECSSPropertyStringConstants.DISPLAY_NONE_VALUE.string().equals(cssProps.getDisplay())
////				|| ( hidden && ("frame".equalsIgnoreCase(element.getLocalName()) || "iframe".equalsIgnoreCase(element.getLocalName())
////							|| "frameset".equalsIgnoreCase(element.getLocalName())) )
////				)
////			return false;
////		else
////			return true;
//	}
	
	
	public static nsIDOMWindow getDOMWindowForFrame(final nsIDOMElement el) {
		return ((nsIDOMNSHTMLFrameElement)el.queryInterface(nsIDOMNSHTMLFrameElement.NS_IDOMNSHTMLFRAMEELEMENT_IID))
				.getContentWindow();
	}
	
	private static final nsIDOMWindow getDOMWindowForFrame(final nsIDOMNode node) {
		return ((nsIDOMNSHTMLFrameElement)node.queryInterface(nsIDOMNSHTMLFrameElement.NS_IDOMNSHTMLFRAMEELEMENT_IID))
				.getContentWindow();
	}
	
	/**
	 * Get all instances of {@linkplain naIDOMDocument} of the current document and all descendant ones (from frames).
	 * @param doc
	 * @return
	 */
	public static Set<nsIDOMDocument> getDOMDocumentsForWebDocument(final nsIDOMDocument doc) {
		final Set<nsIDOMDocument> rez = new HashSet<nsIDOMDocument>();
		_getDOMDocumentsForWebPage(doc, rez);
		return rez;
	}
	private static final void _getDOMDocumentsForWebPage(final nsIDOMDocument doc, final Set<nsIDOMDocument> rez) {
		rez.add(doc);
		Preconditions.checkNotNull(doc);
		final nsIDOMNodeList l1 = doc.getElementsByTagName(EHtmlElementConstants.FRAME.string());
		for (int i=0; i<l1.getLength(); i++) {
			final nsIDOMDocument doc1 = getDOMWindowForFrame(l1.item(i)).getDocument();
			if (doc1 != null) _getDOMDocumentsForWebPage(doc1, rez);
		}
		final nsIDOMNodeList l2 = doc.getElementsByTagName(EHtmlElementConstants.IFRAME.string());
		for (int i=0; i<l2.getLength(); i++) {
			final nsIDOMDocument doc2 = getDOMWindowForFrame(l2.item(i)).getDocument();
			if (doc2 != null) _getDOMDocumentsForWebPage(doc2, rez);
		}
	}
	
	
	/**
	 * Get textual value for the element <code>el</code>.
	 * If it is form element, we get real value, if it is other element we get its inner html code. 
	 */
	public static final String getStrValue(nsIDOMNode node) {
		String strValue = null;
		
		if (node.getNodeType() == nsIDOMNode.ELEMENT_NODE) {
			final String nodeName = node.getLocalName();
			if (nodeName.equals("INPUT")) {
				final nsIDOMHTMLInputElement inputEl = (nsIDOMHTMLInputElement)node
						.queryInterface(nsIDOMHTMLInputElement.NS_IDOMHTMLINPUTELEMENT_IID);
				strValue = inputEl.getValue();
			}
			else if (nodeName.equals("TEXTAREA")) {
				final nsIDOMHTMLTextAreaElement textAreaEl = (nsIDOMHTMLTextAreaElement)node
						.queryInterface(nsIDOMHTMLTextAreaElement.NS_IDOMHTMLTEXTAREAELEMENT_IID);
				strValue = textAreaEl.getValue();
			}
			else if (nodeName.equals("BUTTON")) {
				final nsIDOMHTMLButtonElement button = (nsIDOMHTMLButtonElement)node
						.queryInterface(nsIDOMHTMLButtonElement.NS_IDOMHTMLBUTTONELEMENT_IID);
				strValue = button.getValue();
			}
			else if (nodeName.equals("SELECT")) {
				final nsIDOMHTMLSelectElement select = (nsIDOMHTMLSelectElement)node
						.queryInterface(nsIDOMHTMLSelectElement.NS_IDOMHTMLSELECTELEMENT_IID);
				strValue = select.getValue();
			}
			else {
				strValue = ((nsIDOMNSHTMLElement)node.queryInterface(nsIDOMNSHTMLElement.NS_IDOMNSHTMLELEMENT_IID)).getInnerHTML();
			}
		}
		else {
			strValue = node.getNodeValue();
		}
		
		return strValue;
	}
	
	// ==============================
	// 
	// ==============================
	
	
	
	// ==============================
	//           Type Cast
	// ==============================
	
	/**
	 * <span class="col-red">Does not work!</span>
	 * Convert XPCOM object to another type. Instance generation based on the
	 * name of the field which store IID.
	 * We suppose that field which provide IID hase name which looks like
	 * <code>NS_+"name of the interface"+"_IID"</code>.
	 * @param inst XPCOM object.
	 * @param clazz interface which returned object should implement.
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends nsISupports> T as(final nsISupports inst, final Class<T> clazz) {
		final Field[] fieldArr = clazz.getDeclaredFields();
		final String interfaceName = "NS_"+clazz.getSimpleName().substring(2).toUpperCase()+"_IID";
		for (int i=0; i<fieldArr.length; i++) {
			try {
				if (Modifier.isStatic(fieldArr[i].getModifiers())
						&& fieldArr[i].getType().getName().equals(interfaceName)) {
					return (T) inst.queryInterface((String) fieldArr[i].get(null));
				}
			} catch (IllegalArgumentException e) {
				throw new GeneralUncheckedException(log, e.getMessage());
			} catch (IllegalAccessException e) {
				throw new GeneralUncheckedException(log, e.getMessage());
			}
		}
		throw new TypeCastUncheckedException(log, inst.getClass().getName(), clazz.getName());
	}
	
	/**
	 * <p>Convert XPCOM object to another type. This method more reliable because does not depend
	 * on the field's name.</p>
	 * <p><code>nsIDOMNSElement</code> can be used to get coordinates of the visualized element
	 * <ul>
	 * <li><code>scrollTop</code> --- The vertical scroll position of the element, or 0 if the element is not
	 * scrollable. This property may be assigned a value to change the
	 * vertical scroll position.</li>
	 * <li><code>scrollLeft</code> --- The horizontal scroll position of the element, or 0 if the element is not
	 * scrollable. This property may be assigned a value to change the
	 * horizontal scroll position.</li>
	 * <li><code>scrollHeight</code> --- The height of the scrollable area of the element. If the element is not
	 * scrollable, scrollHeight is equivalent to the offsetHeight.</li>
	 * <li><code>scrollWidth</code> --- The width of the scrollable area of the element. If the element is not
   * scrollable, scrollWidth is equivalent to the offsetWidth.</li>
	 * <li><code>clientTop</code> --- The height in CSS pixels of the element's top border.</li>
	 * <li><code>clientLeft</code> --- The width in CSS pixels of the element's left border and scrollbar
   * if it is present on the left side.</li>
	 * <li><code>clientHeight</code> --- The height in CSS pixels of the element's padding box. If the element is
   * scrollable, the scroll bars are included inside this width.</li>
	 * <li><code>clientWidth</code> --- The width in CSS pixels of the element's padding box. If the element is
   * scrollable, the scroll bars are included inside this height.</li>
	 * </ul>
	 * </p>
	 * <p><code>nsIDOMNSHTMLElement</code> can be used to get coordinates of the visualized element.
	 * (It provides the same data as <code>nsIBoxObject</code>.)
	 * <ul>
	 * <li><code>offsetParent</code></li>
	 * <li><code>offsetTop</code> --- coordinates of the top border relative to the offsetParent.</li>
	 * <li><code>offsetLeft</code> --- coordinates of the left border relative to the offsetParent.</li>
	 * <li><code>offsetWidth</code> --- width of the box.</li>
	 * <li><code>offsetHeight</code> --- height of the box.</li>
	 * </ul>
	 * </p>
	 * @param inst XPCOM object.
	 * @param IID
	 * @return automatic type cast.
	 */
	@SuppressWarnings("unchecked")
	public static final <T extends nsISupports> T as(final nsISupports inst, final String IID) {
		return (T) inst.queryInterface(IID);
	}
	
	
//	/*
//	 * This is a helper method used to hook the DOMEventListener to the
//	 * DOMDocument. In the case of Frames and IFrames, the boolean hookToSubDocs
//	 * controls the recursive calls to any hook the listener to the sub
//	 * documents.
//	 */
//	public static void hookDOMEventListener(String eventType,
//			nsIDOMEventListener listener, nsIDOMDocument document,
//			boolean hookSubDocs) {
//		nsIDOMEventTarget docEventTarget = (nsIDOMEventTarget) document
//				.queryInterface(nsIDOMEventTarget.NS_IDOMEVENTTARGET_IID);
//		docEventTarget.addEventListener(eventType, listener, true);
//
//		if (!hookSubDocs)
//			return;
//
//		// going to have to search for FRAME and IFRAME in order to find	
//		// subdocuments
//		nsIDOMNodeList frames = document.getElementsByTagName("FRAME");
//		for (int i = 0; i < frames.getLength(); i++) {
//			nsIDOMHTMLFrameElement frame = (nsIDOMHTMLFrameElement) frames
//					.item(i).queryInterface(
//							nsIDOMHTMLFrameElement.NS_IDOMHTMLFRAMEELEMENT_IID);
//			if (frame.getContentDocument() != null)
//				hookDOMEventListener(eventType, listener, frame
//						.getContentDocument(), hookSubDocs);
//		}
//
//		nsIDOMNodeList iframes = document.getElementsByTagName("IFRAME");
//		for (int i = 0; i < iframes.getLength(); i++) {
//			nsIDOMHTMLIFrameElement iframe = (nsIDOMHTMLIFrameElement) iframes
//					.item(i)
//					.queryInterface(
//							nsIDOMHTMLIFrameElement.NS_IDOMHTMLIFRAMEELEMENT_IID);
//			if (iframe.getContentDocument() != null)
//				hookDOMEventListener(eventType, listener, iframe
//						.getContentDocument(), hookSubDocs);
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ==============================
	// TEST
	// ==============================
	
	
//	protected Rectangle getElementBounds(nsIDOMElement element) {
//		nsIDOMNSElement nselement = (nsIDOMNSElement) element.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
//		nsIDOMClientRect rect = nselement.getBoundingClientRect();
//
//		/*
//		 * Getting the root element of the document (assuming HTML) and using its Screen coordinates as the origin.
//		 */
//		nsIDOMElement mainDiv = element;
//		nsIDOMDocument rootdocument = mainDiv.getOwnerDocument(); //since the SelectionBox DIV is in the root document
//		nsIDOMElement rootElement = rootdocument.getDocumentElement(); //should be the HTML element
//		nsIDOMNSElement rootnsdocument = (nsIDOMNSElement) rootElement.queryInterface(nsIDOMNSElement.NS_IDOMNSELEMENT_IID);
//		nsIDOMClientRect rootBox = rootnsdocument.getBoundingClientRect();
//		int originX = (int) rootBox.getLeft();
//		int originY = (int) rootBox.getTop();
//
//		//	return new Rectangle((re - originX), (box.getScreenY() - originY), box.getWidth(), box.getHeight());
//
//		return new Rectangle((int) rect.getLeft() - originX, (int) rect.getTop() - originY, (int) rect.getWidth(), (int) rect.getHeight());
//	}
	
	
	public static final String _debug_boxToString(final nsIBoxObject box) {
		return "{x: "+box.getX()+"; y: "+box.getY()+"; w: "+box.getWidth()+"; h: "
				+box.getHeight()+"; screenX: "+box.getScreenX()+"; screenY: "+box.getScreenY()+"}";
		
	}
	
	public static final String _debug_clientRectToString(final nsIDOMClientRect clientRect) {
		return "{left: "+clientRect.getLeft()+"; top: "+clientRect.getTop()+"; right: "
				+clientRect.getRight()+"; bottom: "+clientRect.getBottom()+"; width: "+clientRect.getWidth()+"; height: "+clientRect.getHeight()+"}";
	}
	
	public static final String _debug_clientRectListToString(final nsIDOMClientRectList clientRectList) {
		if (clientRectList.getLength()>1)
			System.err.println("_debug_clientRectListToString!!!");
		StringBuffer sb = new StringBuffer();
		sb.append("{ -----\n");
		for (int i=0; i<clientRectList.getLength(); i++) {
			sb.append("\t"+_debug_clientRectToString(clientRectList.item(i))+"\n");
		}
		sb.append("} -----");
		return sb.toString();
	}
	
	public static final void _debug_clientRectListCheck(final nsIDOMClientRectList clientRectList) {
		if (clientRectList.getLength()>1)
			System.out.println("_debug_clientRectListToString: Length>1!!!");
	}
	
	public static final String _debug_nsElementToString(final nsIDOMNSElement el) {
		return "{clientLeft: "+el.getClientLeft()+"; clientTop: "+el.getClientTop()
				+"; clientWidth: "+el.getClientWidth()+"; clientHeight: "+el.getClientHeight()
				+"; scrollLeft: "+el.getScrollLeft()+"; scrollTop: "+el.getScrollTop()
				+"; scrollWidth: "+el.getScrollWidth()+"; scrollHeight: "+el.getScrollHeight();
	}
	
	public static final String _debug_nsHTMLElementToString(final nsIDOMNSHTMLElement el) {
		return "{OffsetLeft: "+el.getOffsetLeft()+"; OffsetTop: "+el.getOffsetTop()+
				"; OffsetWidth: "+el.getOffsetWidth()+"; OffsetHeight: "+el.getOffsetHeight()
				+"; ContentEditable: "+el.getContentEditable()+"}";
	}
	
	public static final String _debug_HTMLElementToString(final nsIDOMHTMLElement el) {
		return "{dir: "+el.getDir()+"}";
	}
	
}
