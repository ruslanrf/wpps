/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.annotation.AnnotDOM;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateAttrOrRelInOnt;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateObject;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.EDOMAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.EDOMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.EDOMRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfimpl.DOMNodeImpl;
import tuwien.dbai.wpps.ontschema.DOMOnt;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 11:41:18 PM
 */
@Singleton
public class DOMRdfInstanceFactory {
	private static final Logger log = Logger.getLogger(DOMRdfInstanceFactory.class);

	private final OntModelAdp domOntAdp;
	
	private final DOMNodeImpl domNodeImpl;
	
	private final WhetherCreateObject whetherCreateInstance;
	
	private final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt;
	
	@Inject
	public DOMRdfInstanceFactory(
			 @AnnotDOM final OntModelAdp domOntAdp
			, final DOMNodeImpl domNodeImpl
			, final WhetherCreateObject whetherCreateInstance
			, final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt
			) {
		this.domOntAdp = domOntAdp;
		this.domNodeImpl = domNodeImpl;
		this.whetherCreateInstance = whetherCreateInstance;
		this.whetherCreateAttrOrRelInOnt = whetherCreateAttrOrRelInOnt;
	}
	
	public Resource createDocument(Resource rdfInst, final Resource parentRdfInst) {
		if (whetherCreateInstance.apply(EDOMInstType.DOM_DOCUMENT)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(DOMOnt.Document
						, domOntAdp.getBottomRdfModel(), domOntAdp.getNameSpace());
			else
				domOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, DOMOnt.Document);
			
			if (parentRdfInst != null && whetherCreateAttrOrRelInOnt.apply(EDOMRelation.HAS_CHILDREN_RELATION)) {
				domNodeImpl.addNodeParent(rdfInst, parentRdfInst);
			}

if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createDocument()"+" rdfInst: "+rdfInst+" parentRdfInst"+parentRdfInst);
		return rdfInst;
		}
		else return null;
	}
	
	public Resource createElement(Resource rdfInst, final Resource parentRdfInst
			, final String name, final String[][] attributeArr2) {
		if (whetherCreateInstance.apply(EDOMInstType.DOM_ELEMENT)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(DOMOnt.Element
						, domOntAdp.getBottomRdfModel(), domOntAdp.getNameSpace());
			else
				domOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, DOMOnt.Element);
			if (whetherCreateAttrOrRelInOnt.apply(EDOMRelation.HAS_CHILDREN_RELATION)) {
				if (parentRdfInst != null)
					domNodeImpl.addNodeParent(rdfInst, parentRdfInst);
				else
					log.warn("html element "+rdfInst+" does not have any parent.");
			}
			if (whetherCreateAttrOrRelInOnt.apply(EDOMAttrType.HAS_ELEMENT_NAME)) {
				domNodeImpl.addElementName(rdfInst, name);
			}
			
			if (whetherCreateAttrOrRelInOnt.apply(EDOMAttrType.HAS_ATTRIBUTE)) {
				for (int i = 0; i<attributeArr2.length; i++) {
					domNodeImpl.addElementAttribute(rdfInst, attributeArr2[i][0], attributeArr2[i][1]);
				}
			}
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createElement()"+" rdfInst: "+rdfInst+" parentRdfInst"+parentRdfInst
		+" name: "+name+" attrs: "+attributeArr2.length);
		return rdfInst;
		}
		else return null;
}
	
	public Resource createText(Resource rdfInst, final Resource parentRdfInst, final String content) {
		if (whetherCreateInstance.apply(EDOMInstType.DOM_TEXT)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(DOMOnt.Text
						, domOntAdp.getBottomRdfModel(), domOntAdp.getNameSpace());
			else
				domOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, DOMOnt.Text);
			if (whetherCreateAttrOrRelInOnt.apply(EDOMRelation.HAS_CHILDREN_RELATION)) {
				domNodeImpl.addNodeParent(rdfInst, parentRdfInst);
			}
			if (whetherCreateAttrOrRelInOnt.apply(EDOMAttrType.HAS_TEXT_CONTENT)) {
				domNodeImpl.addTextContent(rdfInst, content);
			}

if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createText()"+" rdfInst: "+rdfInst+" parentRdfInst"+parentRdfInst
		+" content: "+content);
		return rdfInst;
		}
		else return null;
	}
	
}
