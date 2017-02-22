/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.core.annotation.AnnotLogicalModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLib;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalObject;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.ontschema.CommonOnt;
import tuwien.dbai.wpps.ontschema.LogicalModelOnt;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 4, 2012 8:29:18 PM
 */
@Singleton
public class LogicalObjectImpl {
	private static final Logger log = Logger.getLogger(LogicalObjectImpl.class);
	
	private final OntModelAdp rdfModel;
	
	@Inject
	public LogicalObjectImpl(@AnnotLogicalModel final OntModelAdp rdfModel) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
		this.rdfModel = rdfModel;
	}
	
	public void addLabel(final Resource rdfInst, final String label) {
		rdfModel.getBottomRdfModel().add(rdfInst, RDFS.label, label);
	}
	
	public String getLabel(final Resource rdfInst) {
		return InstAdpLib.getValueAsStringSoft(rdfInst, RDFS.label, rdfModel.getTopRdfModel());
	}
	
	public void addStringContent(final Resource rdfInst, final String str) {
		rdfModel.getBottomRdfModel().add(rdfInst, CommonOnt.hasStringContent, str);
	}
	
	public String getStringContent(final Resource rdfInst) {
		return InstAdpLib.getValueAsStringSoft(rdfInst, CommonOnt.hasStringContent, rdfModel.getTopRdfModel());
	}
	
	public void addTag(final Resource rdfInst, final String tag) {
		rdfModel.getBottomRdfModel().add(rdfInst, LogicalModelOnt.hasTag, tag);
	}
	
	public String getTag(final Resource rdfInst) {
		return InstAdpLib.getValueAsStringSoft(rdfInst, LogicalModelOnt.hasTag, rdfModel.getTopRdfModel());
	}
	
	public void addExternalType(final Resource rdfInst, final String uri) {
		rdfModel.getBottomRdfModel().add(rdfInst, RDF.type, rdfModel.getBottomRdfModel().createResource(uri));
	}
	
	public boolean hasExternalType(final Resource rdfInst, final String uri) {
		return rdfModel.getTopRdfModel().contains(rdfInst, RDF.type, rdfModel.getBottomRdfModel().createResource(uri));
	}
	
	public void addRelation(final Resource rdfInst, final String uri, final ILogicalObject obj) {
		rdfModel.getBottomRdfModel().add(rdfInst, rdfModel.getBottomRdfModel().createProperty(uri), ((IRdfResourceAdp)obj).getRdfResource());
	}
	
	public void addAttribute(final Resource rdfInst, final String uri, final Number val) {
		if (val instanceof Integer || val instanceof Short || val instanceof Byte || val instanceof BigInteger)
			rdfModel.getBottomRdfModel().addLiteral(rdfInst, rdfModel.getBottomRdfModel().createProperty(uri), val.intValue());
		else if (val instanceof Long)
			rdfModel.getBottomRdfModel().addLiteral(rdfInst, rdfModel.getBottomRdfModel().createProperty(uri), val.longValue());
		else if (val instanceof Float)
			rdfModel.getBottomRdfModel().addLiteral(rdfInst, rdfModel.getBottomRdfModel().createProperty(uri), val.floatValue());
		else if (val instanceof Double || val instanceof BigDecimal)
			rdfModel.getBottomRdfModel().addLiteral(rdfInst, rdfModel.getBottomRdfModel().createProperty(uri), val.doubleValue());
		else
			throw new UnknownValueFromPredefinedList(log);
	}
	
	public void addAttribute(final Resource rdfInst, final String uri, final char val) {
		rdfModel.getBottomRdfModel().addLiteral(rdfInst, rdfModel.getBottomRdfModel().createProperty(uri), val);
	}
	
	public void addAttribute(final Resource rdfInst, final String uri, final boolean val) {
		rdfModel.getBottomRdfModel().addLiteral(rdfInst, rdfModel.getBottomRdfModel().createProperty(uri), val);
	}
	
}
