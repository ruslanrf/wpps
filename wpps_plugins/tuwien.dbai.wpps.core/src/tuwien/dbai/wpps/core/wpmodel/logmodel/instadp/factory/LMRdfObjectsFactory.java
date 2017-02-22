/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory;

import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.IItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 3:25:20 PM
 */
@Singleton @Deprecated
public final class LMRdfObjectsFactory {

	private final LMRdfInstanceFactory lmRdfInstanceFactory;
	private final ILMRdfInstAdpFactory factory;
	
	
	@Inject
	public LMRdfObjectsFactory(
			final LMRdfInstanceFactory lmRdfInstanceFactory
			, final ILMRdfInstAdpFactory factory) {
		this.lmRdfInstanceFactory = lmRdfInstanceFactory;
		this.factory = factory;
	}

	public ISequence createSequence(Resource rdfInst) {
		rdfInst = lmRdfInstanceFactory.createSequence(rdfInst);
		return (rdfInst == null)?null:factory.createSequenceStructure(rdfInst);
	}
	
	public IItem createSequenceItem(Resource rdfInst) {
		rdfInst = lmRdfInstanceFactory.createSequenceItem(rdfInst);
		return (rdfInst == null)?null:factory.createSequenceItem(rdfInst);
	}
	
}
