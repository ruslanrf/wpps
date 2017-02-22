/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.list.SetUniqueList;

import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMDocument;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMNode;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMText;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMTraversalNode;
import tuwien.dbai.wpps.ontschema.DOMOnt;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 8:03:57 PM
 */
public enum EDOMInstType implements IRdfInstType {

	// --- structural elements (Main Elements): ---
	DOM_NODE(IDOMNode.class, DOMOnt.Node, true, false),
		TRAVERSAL_NODE(IDOMTraversalNode.class, DOMOnt.TraversalNode, true, false),
			DOM_DOCUMENT(IDOMDocument.class, DOMOnt.Document, true, true),
			DOM_ELEMENT(IDOMElement.class, DOMOnt.Element, true, true),
			DOM_TEXT(IDOMText.class, DOMOnt.Text, true, true);
			
			private final Class<? extends IInstanceAdp> javaInterface;
			private final Resource rdfResource;
			private final boolean canBeInstantiated; // originally hasImplementation
			/**
			 * true if this type is in one of structural models.
			 */
			private final boolean isMainType; 
			
			@SuppressWarnings("unchecked")
			private List<EDOMInstType> children = SetUniqueList.decorate(
					new LinkedList<EDOMInstType>());
			/**
			 * @return the javaInterface
			 */
			@Override
			public Class<? extends IInstanceAdp> getJavaInterface() {
				return javaInterface;
			}
			@Override
			public Resource getRdfResource() {
				return rdfResource;
			}
			
			/**
			 * @return the canBeInstantiated
			 */
			@Override
			public boolean isCanBeInstantiated() {
				return canBeInstantiated;
			}
			
			@Override
			public boolean isMainType() {
				return isMainType;
			}
			
			private final EWPOntSubModel wpOntSubModel;
			
			@Override
			public EWPOntSubModel getWPSubModelType() {
				return wpOntSubModel;
			}
			
			private EDOMInstType(Class<? extends IInstanceAdp> javaInterface, Resource rdfResource
					, boolean isMainType, boolean canBeInstantiated) {
				this.javaInterface = javaInterface;
				this.rdfResource = rdfResource;
				this.isMainType = isMainType;
				this.canBeInstantiated = canBeInstantiated;
				this.wpOntSubModel = EWPOntSubModel.DOM;
			}
			
			private final void _addParent(EDOMInstType el) {
				el.children.add(this);
			}
			
			// It is executed after the constructor.
			// We set hierarchy of elements.
			static {
				TRAVERSAL_NODE._addParent(DOM_NODE);
				DOM_ELEMENT._addParent(TRAVERSAL_NODE);
				DOM_TEXT._addParent(TRAVERSAL_NODE);
				DOM_DOCUMENT._addParent(TRAVERSAL_NODE);
			}

			@Override
			public boolean hasChildren() {
				return children.size()>0;
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public List<IRdfInstType> getChildren() {
				return (List)children;
			}
			
			public static final <T extends IInstanceAdp> EDOMInstType getInstTypeByJavaClass(Class<T> clazz) {
				final EDOMInstType[] types = EDOMInstType.values();
				for (int i=0; i<types.length; i++) {
					if (clazz.equals(types[i].getJavaInterface()))
						return types[i];
				}
				return null;
			}

			public static final EDOMInstType getMainRoot() {
				return DOM_NODE;
			}

}
