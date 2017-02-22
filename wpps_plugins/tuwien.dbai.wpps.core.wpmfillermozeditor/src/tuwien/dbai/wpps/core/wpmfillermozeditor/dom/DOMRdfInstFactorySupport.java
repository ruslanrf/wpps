/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.dom;

import org.mozilla.interfaces.nsIDOMNode;

import tuwien.dbai.wpps.core.wpmfillermozeditor.TextNodesWrapper;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.GetHtmlTagIMObjectType;

import com.hp.hpl.jena.rdf.model.Resource;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 24, 2012 4:36:08 PM
 */
public class DOMRdfInstFactorySupport {
//	private static final Logger log = Logger.getLogger(DOMRdfInstFactorySupport.class);

	private final DOMRdfInstFactory domRdfInstFactory;
	public DOMRdfInstFactory getDOMRdfInstFactory() {
		return domRdfInstFactory;
	}
	
	public DOMRdfInstFactorySupport(
			final DOMRdfInstFactory domRdfInstFactory
			) {
		this.domRdfInstFactory = domRdfInstFactory;
	}
	
	/**
	 * @param rdfInst
	 * @param node Must be instance of nsIDOMNode and not its subclasses/subinterfaces
	 * @param parent Must be instance of nsIDOMNode and not its subclasses/subinterfaces
	 * @return
	 */
	public Resource createDocumentRdfInst(Resource rdfInst, final nsIDOMNode node, final nsIDOMNode parent) {
		rdfInst = domRdfInstFactory.createDocument(rdfInst, node, parent);
		return rdfInst;
	}
	
	/**
	 * TODO: {@linkplain GetHtmlTagIMObjectType}
	 * @param rdfInst
	 * @param node Must be instance of nsIDOMNode and not its subclasses/subinterfaces
	 * @return
	 */
	public Resource createElementOrTextRdfInst(Resource rdfInst, final nsIDOMNode node) {
		String name = node.getNodeName();
		if (TextNodesWrapper.ADDITIONAL_TEXT_ELEM.equals(name))
			rdfInst = domRdfInstFactory.createText(rdfInst, node);
		else
			rdfInst = domRdfInstFactory.createElement(rdfInst, node);
		return rdfInst;
	}
	
}
