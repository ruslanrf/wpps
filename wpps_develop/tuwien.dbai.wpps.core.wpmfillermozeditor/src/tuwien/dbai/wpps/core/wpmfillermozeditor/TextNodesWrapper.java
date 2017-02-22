/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMAttr;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMDocumentTraversal;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeFilter;
import org.mozilla.interfaces.nsIDOMNodeIterator;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.html.ECSSPropertyConstants;
import tuwien.dbai.wpps.common.html.EHtmlElementConstants;
import tuwien.dbai.wpps.common.optimization.FunctionWithMemory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.ConvertToDOMElement;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.GetDOMCSS2Properties;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.IsATFInternalElement;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.IsElementVisible;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.IsWebFormElementOrSubElement;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/**
 * Class which wraps all visible nonempty text nodes with <code>{@linkplain #ADDITIONAL_TEXT_ELEM}</code> element.
 * It is very inefficient approach. For the versions of xulrunner grather than 2.0,
 * coordinates of text element can be taken from the range.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 3, 2011 10:03:03 PM
 */
public class TextNodesWrapper {
	private static final Logger log = Logger.getLogger(TextNodesWrapper.class);
	
	public static final String ADDITIONAL_TEXT_ELEM = "WPPS-TEXTELEMENT";
	
	private final nsIDOMDocument document;
	
	private final nsIDOMDocumentTraversal traversal;
	
	private final IsElementVisible isElementVisible;
	private final ConvertToDOMElement convertToDOMElement;
	private final IsWebFormElementOrSubElement isWebFormElementOrSubElement;
	private final IsATFInternalElement _isATFInternalElement;
	private final GetDOMCSS2Properties getDOMCSS2Properties;
	
	public TextNodesWrapper(final nsIDOMDocument document
			, final nsIDOMDocumentTraversal traversal
			, final IsElementVisible isElementVisible
			, final ConvertToDOMElement convertToDOMElement
			, final IsWebFormElementOrSubElement isWebFormElementOrSubElement
			, final IsATFInternalElement _isATFInternalElement
			, final GetDOMCSS2Properties getDOMCSS2Properties) {
		this.document = document;
		this.traversal = traversal; 
		this.isElementVisible = isElementVisible;
		this.convertToDOMElement = convertToDOMElement;
		this.isWebFormElementOrSubElement = isWebFormElementOrSubElement;
		this._isATFInternalElement = _isATFInternalElement;
		this.getDOMCSS2Properties = getDOMCSS2Properties;
	}
	
	// Comented string does not for some web sites because of bug in JVM. It does not like \\s+
//		private final static String REG_EX_FOR_GAPS = "(\\s|\u0020|\u00A0|\u200B|\u3000)+";
		
		@SuppressWarnings("unused")
		private final static String REG_EX_FOR_SIMPLE_GAPS = "(\\s)+";
		private final static String REG_EX_FOR_HTML_GAPS = "(&nbsp;)+";
		@SuppressWarnings("unused")
		private final static String REG_EX_FOR_UNICODE_GAPS = "(\u0020|\u00A0|\u200B|\u3000)+";
		@SuppressWarnings("unused")
		private final static String REG_EX_FOR_GAPS = "(\\s|(&nbsp;)|(\u0020)|(\u00A0)|(\u200B)|(\u3000))+";
		
		private final FunctionWithMemory <nsIDOMNode, String> getTrimmedString
		= new FunctionWithMemory<nsIDOMNode, String>(
				new IFunction<nsIDOMNode, String>() {
					@Override
					public String apply(nsIDOMNode avar) {
						// Expensive function !
//						return avar.replaceAll(REG_EX_FOR_HTML_GAPS, " ").replaceAll(REG_EX_FOR_UNICODE_GAPS, " ")
//								.replaceAll(REG_EX_FOR_SIMPLE_GAPS, " ").trim();
						String str = avar.getNodeValue();
						if (Strings.isNullOrEmpty(str))
							return "";
if (log.isTraceEnabled()) log.trace("cleaning text... \""+str+"\", node "+avar);
						str = str.replaceAll(REG_EX_FOR_HTML_GAPS, " ");
						str = str.replaceAll("\u0020+", " ");
						str = str.replaceAll("\u00A0+", " ");
						str = str.replaceAll("\u200B+", " ");
						str = str.replaceAll("\u3000+", " ").trim();
						str = str.replaceAll("\\s+", " ").trim();
if (log.isTraceEnabled()) log.trace("cleaned text: "+str);
						return str;
					}
				}, 10000);
		
		private final boolean isStringEmpty(final nsIDOMNode node) {
			if (Strings.isNullOrEmpty(getTrimmedString.apply(node)))
				return true;
			return false;
		}
		
		/**
		 * Can be used in case, if DOM was wrapped already before, for the previous WP model generation.
		 * @return
		 */
		private final boolean _wasWrapped(String parentNodeName) {
			return ADDITIONAL_TEXT_ELEM.equalsIgnoreCase(parentNodeName);
		}
		
		/**
		 * Wrap visible text nodes. It is used to have an element for every text node to
		 * get its box model with information about coordinates.
		 * It does not split words in original DOM Tree.
		 * Additional tag ADDITIONAL_TEXT_ELEM is added.
		 * Can be optimized! 
		 * 
		 */
		public void wrapTextNodes() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. Wrap text nodes");
			
if (log.isTraceEnabled()) log.trace("Start traversing text nodes");
			// expensive!
			// === Get text nodes ===
			final List<nsIDOMNode> visibleTxtNodes = new ArrayList<nsIDOMNode>(10000);
			final List<nsIDOMElement> visibleTxtNodesParents = new ArrayList<nsIDOMElement>(10000);
			
			final nsIDOMNodeIterator nodeIterator = traversal.createNodeIterator(document, nsIDOMNodeFilter.SHOW_TEXT, null, true);
			// add visible text nodes into the array
			nsIDOMNode node = null;
			while ( (node = nodeIterator.nextNode()) != null) {
				if ( !isStringEmpty(node) ) { // analyse only non-empty strings. It should have at least 1 visible character to be wrapped
					final nsIDOMNode parentNode = node.getParentNode(); // actually every text node must have parent which is element.
					final nsIDOMElement parentEl = convertToDOMElement.apply(parentNode);
					if (parentNode != null && parentNode.getNodeType() == nsIDOMNode.ELEMENT_NODE) {
						if (!_wasWrapped(parentNode.getLocalName())
								&& isElementVisible.apply(parentEl)
								&& !isWebFormElementOrSubElement.apply(parentNode.getLocalName())
								&& !_isATFInternalElement.apply(parentNode)
						) {
							visibleTxtNodes.add(node);
							visibleTxtNodesParents.add(parentEl);
						}
					}
				}
			}
			nodeIterator.detach(); // release resources
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Finish getting text nodes");
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Start to process text nodes");
			// expensive!
			// --- for every text node ---
			for (int i=0; i<visibleTxtNodes.size(); i++) {
				final nsIDOMNode visTxtNode = visibleTxtNodes.get(i);
				final nsIDOMElement textEl =  document.createElement(ADDITIONAL_TEXT_ELEM);
				addAttributes(textEl, visibleTxtNodesParents.get(i), document);
				textEl.appendChild(document.createTextNode(visTxtNode.getNodeValue()));
				visTxtNode.getParentNode().replaceChild(textEl, visTxtNode); // Replacement
				
			}
if (log.isTraceEnabled()) log.trace("Finish to process text nodes");
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. Wrap text nodes");
		}
		
	private final void addAttributes(final nsIDOMElement el, final nsIDOMElement pnode, final nsIDOMDocument doc) {
		String tda = getDOMCSS2Properties.apply(pnode).getTextDecoration();
//if (log.isTraceEnabled()) log.trace("el:"+el+" "+el.getNodeName()+" "+el.getNodeValue()+" pnode:"+pnode+" "+pnode.getNodeName()
//		+" "+pnode.getFirstChild().getNodeValue()
//		+" "+pnode.getNodeValue()+" "+pnode.getNodeType()+" textdecor:"+tda);
		if (!ECSSPropertyConstants.TEXT_DECORATION_NONE_VALUE.equalTo(tda)) {
			final nsIDOMAttr attr = doc.createAttribute(EHtmlElementConstants.STYLE_ATTRIBUTE.string());
			attr.setValue(ECSSPropertyConstants.TEXT_DECORATION.string()+":"+tda);
			el.setAttributeNode(attr);
		}
	}
		
		
	/**
	 * In the DOM tree it is additional WPPS-TEXTELEMENT which has only 1 text node with value of wrapped
	 * original text node.
	 * @return
	 */
	public static final String getStringContentOfWrapperNode(final nsIDOMNode el) {
if (log.isDebugEnabled()) Preconditions.checkArgument(ADDITIONAL_TEXT_ELEM.equals(el.getNodeName()));
		return el.getChildNodes().item(0).getNodeValue();
	}

}
