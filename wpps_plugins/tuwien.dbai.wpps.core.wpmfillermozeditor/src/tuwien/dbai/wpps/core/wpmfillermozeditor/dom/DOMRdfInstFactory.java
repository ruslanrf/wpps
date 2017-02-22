/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.dom;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMNamedNodeMap;
import org.mozilla.interfaces.nsIDOMNode;

import tuwien.dbai.wpps.core.wpmfillermozeditor.TextNodesWrapper;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateObject;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.EDOMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.factory.DOMRdfInstanceFactory;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 24, 2012 4:21:07 PM
 */
public class DOMRdfInstFactory {
	private static final Logger log = Logger.getLogger(DOMRdfInstFactory.class);

	private final WhetherCreateObject whetherCreateInstance;
	private final DOMRdfInstanceFactory domRdfInstanceFactory;

	private final Map<nsIDOMNode, Resource> nodeResourceMap = new LinkedHashMap<nsIDOMNode, Resource>(10000);
	
	public DOMRdfInstFactory(final WhetherCreateObject whetherCreateInstance
			, final DOMRdfInstanceFactory domRdfInstanceFactory) {
if (log.isTraceEnabled()) log.trace("Constructing IM RDF instance factory");
		this.whetherCreateInstance = whetherCreateInstance;
		this.domRdfInstanceFactory = domRdfInstanceFactory;
	}

	private static int I1 = 0;
	private static int I2 = 0;
	private static int I3 = 0;
//	private Resource getNearestKnownAncestorAndLinkItsParent(nsIDOMNode node) {
//		I1++;
//		Resource rez = null;
//		final nsIDOMNode parent = node.getParentNode();
//		nsIDOMNode ancestor = parent;
//		while (ancestor != null && rez == null) {
//			rez = nodeResourceMap.get(ancestor);
//			if (rez == null)
//				ancestor = ancestor.getParentNode();
//		}
//		if (rez != null && ancestor != parent) {
//if (log.isTraceEnabled()) log.trace(node+"("+node.getNodeName()+", "+node.getNodeValue()+")"
//		+" has nearest ancestor: "+ancestor+"("+ancestor.getNodeName()+", "+ancestor.getNodeValue()+") "+(I2++)+"/"+I1);
//			nodeResourceMap.put(parent, rez);
//		}
//		if (rez == null) {
//if (log.isTraceEnabled()) log.trace(node+"("+node.getNodeName()+", "+node.getNodeValue()+") has no known parent "+(I3++)+"/"+I1);
//		}
//		return rez;
//	}
	
	/**
	 * TODO: use GetObjectForNode instead.
	 * @param parent
	 * @param childNodeForLogging
	 * @return
	 */
	private Resource getThisOrNearestKnownAncestorAndLinkItInMap(final nsIDOMNode parent, final nsIDOMNode childNodeForLogging) {
if (log.isTraceEnabled()) I1++;
		Resource rez = null;
		nsIDOMNode ancestor = parent;
		while (ancestor != null && rez == null) {
			rez = nodeResourceMap.get(ancestor);
			if (rez == null)
				ancestor = ancestor.getParentNode();
		}
		if (rez != null && ancestor != parent) {
if (log.isTraceEnabled()) log.trace(
		childNodeForLogging+"("+childNodeForLogging.getNodeName()+", "+childNodeForLogging.getNodeValue()+")"
		+" has nearest ancestor: "+ancestor+"("+ancestor.getNodeName()+", "+ancestor.getNodeValue()+") "+(I2++)+"/"+I1);
			nodeResourceMap.put(parent, rez);
		}
		if (rez == null) {
if (log.isTraceEnabled()) log.trace(
		childNodeForLogging+"("+childNodeForLogging.getNodeName()+", "+childNodeForLogging.getNodeValue()
		+") has no known parent "+(I3++)+"/"+I1);
		}
		return rez;
	}
	
	/**
	 * @param rdfInst
	 * @param node Must be instance of nsIDOMNode and not its subclasses/subinterfaces
	 * @param cssProps
	 * @return
	 */
	public Resource createText(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EDOMInstType.DOM_TEXT)) {
			Resource rdfParentInst = getThisOrNearestKnownAncestorAndLinkItInMap(node.getParentNode(), node);
			rdfInst = domRdfInstanceFactory.createText(rdfInst
					, rdfParentInst
					, TextNodesWrapper.getStringContentOfWrapperNode(node));
			if (rdfInst != null) nodeResourceMap.put(node, rdfInst);
			return rdfInst;
		}
		return null;
	}
	
	/**
	 * @param rdfInst
	 * @param node Must be instance of nsIDOMNode and not its subclasses/subinterfaces
	 * @return
	 */
	public Resource createElement(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EDOMInstType.DOM_ELEMENT)) {
			Resource rdfParentInst = getThisOrNearestKnownAncestorAndLinkItInMap(node.getParentNode(), node);
//if (rdfParentInst == null) log.trace(rdfParentInst +" does not have a parent");
//else log.trace(rdfParentInst +" has a parent");
			
			nsIDOMNamedNodeMap attrMap = node.getAttributes();
			final int attrN = (int)attrMap.getLength();
			String[][] attrArr2 = new String[attrN][2];
			for (int i=0; i<attrN; i++) {
				nsIDOMNode attr = attrMap.item(i);
				attrArr2[i][0] = attr.getNodeName();
				attrArr2[i][1] = attr.getNodeValue();
			}
			rdfInst = domRdfInstanceFactory.createElement(rdfInst
							, rdfParentInst
							, node.getNodeName(), attrArr2);
			if (rdfInst != null) nodeResourceMap.put(node, rdfInst);
			return rdfInst;
		}
		return null;
	}
	
	/**
	 * @param rdfInst
	 * @param node Must be instance of nsIDOMNode and not its subclasses/subinterfaces
	 * @param parent
	 * @return
	 */
	public Resource createDocument(Resource rdfInst, final nsIDOMNode node, final nsIDOMNode parent) {
		if (whetherCreateInstance.apply(EDOMInstType.DOM_DOCUMENT)) {
			Resource rdfParentInst = getThisOrNearestKnownAncestorAndLinkItInMap(parent, node);
			rdfInst = domRdfInstanceFactory.createDocument(rdfInst, rdfParentInst);
			if (rdfInst != null) nodeResourceMap.put(node, rdfInst);
			return rdfInst;
		}
		return null;
	}
	
}
