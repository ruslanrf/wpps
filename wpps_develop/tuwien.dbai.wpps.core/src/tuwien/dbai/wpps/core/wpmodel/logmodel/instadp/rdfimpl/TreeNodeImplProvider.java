/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.annotation.AnnotLogicalModel;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpllib.LogicalObjectLib;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 16, 2012 2:23:19 AM
 */
@Singleton // binding in Module. singleton
public class TreeNodeImplProvider implements Provider<TreeNodeImpl> {
	private static final Logger log = Logger.getLogger(TreeNodeImplProvider.class);

	@SuppressWarnings("unused")
	private final WPPSConfig config;
	private final OntModelAdp rdfLogicModel;
	
	@Inject
	public TreeNodeImplProvider(
			final WPPSConfig config
			, @AnnotLogicalModel final OntModelAdp rdfLogicModel) {
		this.config = config;
		this.rdfLogicModel = rdfLogicModel;
	}
	
	@Override
	public TreeNodeImpl get() {
		if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		TreeNodeImpl impl = new TreeNodeImpl();
		
		impl.getNextSibling = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return LogicalObjectLib.getNextTreeNodeSibling((Resource)avars[0], rdfLogicModel.getTopRdfModel());
		} };
		
		impl.getFirstChild = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return LogicalObjectLib.getFirstTreeNodeChild((Resource)avars[0], rdfLogicModel.getTopRdfModel());
		} };
		
		impl.getLastChild = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return LogicalObjectLib.getLastTreeNodeChild((Resource)avars[0], rdfLogicModel.getTopRdfModel());
		} };
		
		impl.appendChildNode = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				LogicalObjectLib.appendLastTreeNodeChild((Resource)avars[0], (Resource)avars[1], rdfLogicModel.getBottomRdfModel());
				return null;
		} };
		
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
	}

}
