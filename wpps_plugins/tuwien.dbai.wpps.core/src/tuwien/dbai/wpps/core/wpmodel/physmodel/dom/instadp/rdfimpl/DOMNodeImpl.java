/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfimpl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.ICheckImplInitialization;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMText;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMTraversalNode;

import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 8:42:37 PM
 */
@Singleton
public class DOMNodeImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(DOMNodeImpl.class);

	public DOMNodeImpl() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation: "+DOMNodeImpl.class);
		}
	
	// ===== IDOMTraversalNode =====
	/**
	 * {@link IDOMTraversalNode#getParent()}
	 */
	IArrFunction<Object, Object> getNodeParent = null;
	
	IArrFunction<Object, Object> addNodeParent = null;
	/**
	 * {@link IDOMTraversalNode#getChildren()}
	 */
	IArrFunction<Object, Object> getNodeChildren = null;

//	@Deprecated
//	IArrFunction<Object, Object> addNodeChildren = null;
	
	// ===== IDOMElement =====
	/**
	 * {@link IDOMElement#getName()}
	 */
	IArrFunction<Object, Object> getElementName = null;
	
	IArrFunction<Object, Object> addElementName = null;
	
	/**
	 * {@link IDOMElement#getAttributesMap()}
	 */
	IArrFunction<Object, Object> getElementAttributesMap = null;
	
	IArrFunction<Object, Object> addElementAttribute = null;
	
	// ===== IDOMText =====
	/**
	 * {@link IDOMText#getText()}
	 */
	IArrFunction<Object, Object> getTextContent = null;
	
	IArrFunction<Object, Object> addTextContent = null;
	
	
	
	public Resource getNodeParent(final Resource traversalNode) {
		return (Resource)getNodeParent.apply(traversalNode);
	}
	
	@SuppressWarnings("unchecked")
	public List<Resource> getNodeChildren(final Resource traversalNode) {
		return (List<Resource>)getNodeChildren.apply(traversalNode);
	}

	public void addNodeParent(final Resource traversalNode, Resource parent) {
		addNodeParent.apply(traversalNode, parent);
	}
	
//	@Deprecated
//	public void addNodeChildren(final Resource traversalNode, Collection<Resource> chCol) {
//		addNodeChildren.apply(traversalNode, chCol);
//	}
	
	
	public String getElementName(final Resource element) {
		return (String)getElementName.apply(element);
	}
	
	public void addElementName(final Resource element, String name) {
		addElementName.apply(element, name);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, String> getElementAttributesMap(final Resource element) {
		return (Map<String, String>)getElementAttributesMap.apply(element);
	}
	
	public void addElementAttribute(final Resource element, String name, String val) {
		addElementAttribute.apply(element, name, val);
	}
	
	public String getTextContent(final Resource text) {
		return (String)getTextContent.apply(text);
	}
	
	public void addTextContent(final Resource text, String content) {
		addTextContent.apply(text, content);
	}
	
	
	
	@Override
	public boolean allFunctionsAreImplemented() {
		 return  getNodeParent != null
				 && addNodeParent != null
				 && getNodeChildren != null
				 && getElementName != null
				 && addElementName != null
				 && getElementAttributesMap != null
				 && addElementAttribute != null
				 && getTextContent != null
				 && addTextContent != null;
	}
	
}
