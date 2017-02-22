/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfimpllib;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLib;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;
import tuwien.dbai.wpps.ontschema.DOMOnt;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 8:59:28 PM
 */
public final class DOMNodeLib {
	private static final Logger log = Logger.getLogger(DOMNodeLib.class);
	
	// =============================
	// ===== IDOMTraversalNode =====
	// =============================
	
	public static final Resource getNodeParent(final Resource node, final Model model) {
		return InstAdpLib.getSequentialStructureContainerSoft(node, DOMOnt.hasFirstChild, DOMOnt.justBeforeSibling, model);
	}
	
	public static final void addNodeParent(final Resource child, Resource parent, final Model model) {
		InstAdpLib.appendResourceToSequentialStructure(parent, DOMOnt.hasFirstChild, DOMOnt.hasLastChild
				, DOMOnt.justBeforeSibling, child, model);
	}
	
	public static final List<Resource> getNodeChildren(final Resource node, final Model model) {
		return InstAdpLib.getResourcesFromSequentialStructureSoft(
				node, DOMOnt.hasFirstChild, DOMOnt.justBeforeSibling, model);
	}

	@Deprecated
	public static final void addNodeChildren(final Resource node, Collection<Resource> chCol, final Model model) {
		InstAdpLib.appendResourcesToSequentialStructure(node, DOMOnt.hasFirstChild, DOMOnt.hasLastChild
				, DOMOnt.justBeforeSibling, chCol, model);
	}
	
	// =======================
	// ===== IDOMElement =====
	// =======================
	
	public static final String getElementName(final Resource element, final Model model) {
		return InstAdpLib.getValueAsStringSoft(element, DOMOnt.hasNodeName, model);
	}
	
	public static final void addElementName(final Resource element, String name, final Model model) {
		model.add(element, DOMOnt.hasNodeName, name);
	}
	
	public static final Map<String, String> getElementAttributesMap(final Resource element, final Model model) {
		final Map<String, String> rez = new HashMap<String, String>();
		Iterator<Resource> iter = InstAdpLib.fillCollectionOfValuesAsResources
				(element, DOMOnt.hasAttribute, model, new LinkedList<Resource>()).iterator();
		while (iter.hasNext()) {
			Resource attr = iter.next();
			String name = InstAdpLib.getValueAsString(attr, DOMOnt.hasNodeName, model);
			String val = InstAdpLib.getValueAsStringSoft(attr, DOMOnt.hasNodeValue, model);
			if (name == null && val != null) {
				log.warn("Resource "+element+". attr name="+name+", val="+val);
			}
			else {
				rez.put(name, val);
			}
		}
		return rez;
	}
	
	public static final void addElementAttribute(final Resource element
			, String name, String val, final String ontologyNS, final Model model) {
		Resource attr = JenaModelsUtilLib.createNewInstance(DOMOnt.Attribute, model, ontologyNS);
		model.add(attr, DOMOnt.hasNodeName, name);
		if (val != null)
			model.add(attr, DOMOnt.hasNodeValue, val);
		model.add(element, DOMOnt.hasAttribute, attr);
	}
	
	// ====================
	// ===== IDOMText =====
	// ====================
	
	public static final String getTextContent(final Resource element, final Model model) {
		return InstAdpLib.getValueAsStringSoft(element, DOMOnt.hasNodeValue, model);
	}
	
	public static final void addTextContent(final Resource text, String content, final Model model) {
		model.add(text, DOMOnt.hasNodeValue, content);
	}

}
