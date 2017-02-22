/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.annotation.AnnotLogicalModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherSupportObjectInOntology;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.ELMInstType;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;
import tuwien.dbai.wpps.ontschema.LogicalModelOnt;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 19, 2012 9:01:45 PM
 */
@Singleton
public final class LMRdfInstanceFactory {
	private static final Logger log = Logger.getLogger(LMRdfInstanceFactory.class);
	
	private final OntModelAdp lmOntAdp;
	
	private final WhetherSupportObjectInOntology whetherSupportObjectInOntology;
	
	@Inject
	public LMRdfInstanceFactory(
			@AnnotLogicalModel final OntModelAdp lmOntAdp
			, final WhetherSupportObjectInOntology whetherCreateInstance
			) {
		this.lmOntAdp = lmOntAdp;
		this.whetherSupportObjectInOntology = whetherCreateInstance;
	}
	
	public Resource createEmptySequence(Resource rdfInst) {
		if (whetherSupportObjectInOntology.apply(ELMInstType.SEQUENCE)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(LogicalModelOnt.Sequence
						, lmOntAdp.getBottomRdfModel(), lmOntAdp.getNameSpace());
			else
				lmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, LogicalModelOnt.Sequence);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createEmptySequence()"+" rdfInst: "+rdfInst);
		return rdfInst;
		}
		else return null;
	}
	
	public Resource createSequenceItem(Resource rdfInst) {
		if (whetherSupportObjectInOntology.apply(ELMInstType.SEQUENCE_ITEM)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(LogicalModelOnt.SequenceItem
						, lmOntAdp.getBottomRdfModel(), lmOntAdp.getNameSpace());
			else
				lmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, LogicalModelOnt.SequenceItem);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createSequenceItem()"+" rdfInst: "+rdfInst);
		return rdfInst;
		}
		else return null;
	}
	
	public Resource createEmptyTree(Resource rdfInst) {
		if (whetherSupportObjectInOntology.apply(ELMInstType.TREE)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(LogicalModelOnt.Tree
						, lmOntAdp.getBottomRdfModel(), lmOntAdp.getNameSpace());
			else
				lmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, LogicalModelOnt.Tree);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createTree()"+" rdfInst: "+rdfInst);
		return rdfInst;
		}
		else return null;
	}
	
	public Resource createTreeNode(Resource rdfInst) {
		if (whetherSupportObjectInOntology.apply(ELMInstType.TREE_NODE)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(LogicalModelOnt.TreeNode
						, lmOntAdp.getBottomRdfModel(), lmOntAdp.getNameSpace());
			else
				lmOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, LogicalModelOnt.TreeNode);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createTreeNode()"+" rdfInst: "+rdfInst);
		return rdfInst;
		}
		else return null;
	}

}
