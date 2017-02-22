/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfimpl;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnimplementedFunctionException;
import tuwien.dbai.wpps.core.annotation.AnnotDOM;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.config.WPPSConfig.EOneToManyRelation;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfimpllib.DOMNodeLib;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 10:40:43 PM
 */
@Singleton
public class DOMNodeImplProvider implements Provider<DOMNodeImpl> {
	private static final Logger log = Logger.getLogger(DOMNodeImplProvider.class);

	private final WPPSConfig config;
	
	private final String domOntNameSpace;
	
	private final Model rdfDOMTop;
	private final Model rdfDOMBottom;
	
	@Inject
	public DOMNodeImplProvider(final WPPSConfig config
			, @AnnotDOM final OntModelAdp rdfDOMAdp) {
		this.config = config;
		this.rdfDOMTop = rdfDOMAdp.getTopRdfModel();
		this.rdfDOMBottom = rdfDOMAdp.getBottomRdfModel();
		this.domOntNameSpace = rdfDOMAdp.getNameSpace();
	}
	
	@Override
	public DOMNodeImpl get() {
		//TODO: add into config check for rules/ type of enricher
		//TODO: checl for using string code for storing attributes

		if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		
		final IArrFunction<Object, Object> nullFunc = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return null; } };
				
		DOMNodeImpl impl = new DOMNodeImpl();
		
		final EOneToManyRelation oneToMany = config.getStructOneToManyRelationMap().get(EWPOntSubModel.DOM);
		
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.getNodeParent = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.getNodeParent = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return DOMNodeLib.getNodeParent((Resource)avars[0], rdfDOMTop);
		} }; }
		else impl.getNodeParent = nullFunc;
		
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
		impl.addNodeParent = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				throw new UnimplementedFunctionException(log);
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.addNodeParent = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					DOMNodeLib.addNodeParent((Resource)avars[0], (Resource)avars[1], rdfDOMBottom);
					return null;
		} }; }
		else impl.addNodeParent = nullFunc;
		
		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
			impl.getNodeChildren = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					throw new UnimplementedFunctionException(log);
		} }; }
		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
			impl.getNodeChildren = new IArrFunction<Object, Object>() {
				@Override public Object apply(Object... avars) {
					return DOMNodeLib.getNodeChildren((Resource)avars[0], rdfDOMTop);
		} }; }
		else impl.getNodeChildren = nullFunc;
		
//		if (oneToMany == EOneToManyRelation.IN_COLLECTION) {
//			impl.addNodeChildren = new IArrFunction<Object, Object>() {
//				@Override public Object apply(Object... avars) {
//					throw new UnimplementedFunctionException(log);
//		} }; }
//		else if (oneToMany == EOneToManyRelation.SEPARATE_STATEMENTS) {
//			impl.addNodeChildren = new IArrFunction<Object, Object>() {
//				@SuppressWarnings("unchecked")
//				@Override public Object apply(Object... avars) {
//					DOMNodeLib.addNodeChildren((Resource)avars[0], (Collection<Resource>)avars[1], rdfDOM);
//					return null;
//		} }; }
		
		impl.getElementName = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return DOMNodeLib.getElementName((Resource)avars[0], rdfDOMTop);
		} };
		
		impl.addElementName = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				DOMNodeLib.addElementName((Resource)avars[0], (String)avars[1], rdfDOMBottom);
				return null;
		} };
		
		impl.getElementAttributesMap = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return DOMNodeLib.getElementAttributesMap((Resource)avars[0], rdfDOMTop);
		} };
		
		impl.addElementAttribute = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				DOMNodeLib.addElementAttribute((Resource)avars[0]
						, (String)avars[1], (String)avars[2], domOntNameSpace, rdfDOMBottom);
				return null;
		} };
		
		impl.getTextContent = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				return DOMNodeLib.getTextContent((Resource)avars[0], rdfDOMTop);
		} };
		
		impl.addTextContent = new IArrFunction<Object, Object>() {
			@Override public Object apply(Object... avars) {
				DOMNodeLib.addTextContent((Resource)avars[0], (String)avars[1], rdfDOMBottom);
				return null;
		} };
		
		if (impl.allFunctionsAreImplemented())
			return impl;
		else
			throw new GeneralUncheckedException(log, "Not all functions are implemented in implementation "+impl.getClass().getName());
	}

}
