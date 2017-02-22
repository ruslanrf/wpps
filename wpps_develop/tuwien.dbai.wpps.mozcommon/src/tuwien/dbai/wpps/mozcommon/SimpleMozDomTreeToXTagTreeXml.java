/**
 * 
 */
package tuwien.dbai.wpps.mozcommon;

import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMDocumentTraversal;
import org.mozilla.interfaces.nsIDOMNSHTMLFrameElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeFilter;
import org.mozilla.interfaces.nsIDOMTreeWalker;

import tuwien.dbai.wpps.common.MDXPath;
import tuwien.dbai.wpps.common.html.EHtmlElementConstants;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 4, 2011 5:33:53 PM
 */
public class SimpleMozDomTreeToXTagTreeXml {
//	private static final Logger log = Logger.getLogger(SimpleMozDomTreeToXTagTreeXml.class);
	
	
	public SimpleMozDomTreeToXTagTreeXml(final nsIDOMDocument document) {
		final nsIDOMDocumentTraversal traversal = (nsIDOMDocumentTraversal)
				document.queryInterface(nsIDOMDocumentTraversal.NS_IDOMDOCUMENTTRAVERSAL_IID);
		aWalker = traversal.createTreeWalker(document, nsIDOMNodeFilter.SHOW_ELEMENT | nsIDOMNodeFilter.SHOW_TEXT, null, true);
			
	}
	
	public String getXMLString() {
		_getXMLString();
		return "<"+MDXPath.DOC_TAG+">"+sb.toString()+"</"+MDXPath.DOC_TAG+">";
	}
	
	private final nsIDOMTreeWalker aWalker;
	private final StringBuffer sb = new StringBuffer();
	
	private void _getXMLString() {
		final nsIDOMNode currNode = aWalker.getCurrentNode();
		final String currNodeName = currNode.getNodeName();
		boolean hasChild = false;
		
		if (EHtmlElementConstants.SCRIPT.string().equalsIgnoreCase(currNodeName)
				||
				currNode.getAttributes()!=null && currNode.getAttributes().getNamedItem("class") != null
				&& "___ATF_INTERNAL".equals(currNode.getAttributes().getNamedItem("class").getNodeValue()))
				return;
		//___ATF_INTERNAL
		
		switch (currNode.getNodeType()) {
			case nsIDOMNode.ELEMENT_NODE: { // TODO: do not provide node value
					sb.append("<"+currNodeName+">");
					
					if (EHtmlElementConstants.FRAME.string().equalsIgnoreCase(currNodeName)
							|| EHtmlElementConstants.IFRAME.string().equalsIgnoreCase(currNodeName)) {
						hasChild = true;
						final nsIDOMNSHTMLFrameElement frame = (nsIDOMNSHTMLFrameElement)currNode
								.queryInterface(nsIDOMNSHTMLFrameElement.NS_IDOMNSHTMLFRAMEELEMENT_IID);
						sb.append(
								(new SimpleMozDomTreeToXTagTreeXml(frame.getContentWindow().getDocument())).getXMLString()
								);
					}
				break;
			}
			case nsIDOMNode.TEXT_NODE: { // TODO: do not provide node name
				sb.append(currNode.getNodeValue());
				break;
			}
		}
		
		
		for (nsIDOMNode childNode = aWalker.firstChild(); childNode!=null; childNode = aWalker.nextSibling()) {
			hasChild = true;
			// --- RECURSION ---
			_getXMLString();
		}
		
		switch (currNode.getNodeType()) {
			case nsIDOMNode.ELEMENT_NODE: {
					if (hasChild)
						sb.append("</"+currNode.getNodeName()+">");
					else {
						sb.deleteCharAt(sb.length()-1);
						sb.append("/>");
					}
				break;
			}
		}
		
		
		aWalker.setCurrentNode(currNode);
	}

}
