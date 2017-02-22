/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequenceItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITree;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITreeNode;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 23, 2012 6:28:38 PM
 */
@Singleton
public class LMRdfInstanceFactoryWrap {
	private final static Logger log = Logger.getLogger(LMRdfInstanceFactoryWrap.class);

	private final LMRdfInstanceFactory factory;
	
	@Inject
	public LMRdfInstanceFactoryWrap(final LMRdfInstanceFactory factory) {
		this.factory = factory;
	}
	
	public <T extends IInstanceAdp> Resource createObject(Class<T> typ) {
		return createObject(null, typ);
	}
	
	public <T extends IInstanceAdp> Resource createObject(final Resource rdfInst, Class<T> typ) {
		if (ISequence.class.equals(typ))
			return factory.createEmptySequence(rdfInst);
		else if (ISequenceItem.class.equals(typ))
			return factory.createSequenceItem(rdfInst);
		else if (ITree.class.equals(typ))
			return factory.createEmptyTree(rdfInst);
		else if (ITreeNode.class.equals(typ))
			return factory.createTreeNode(rdfInst);
		else { // if this is not a basic type
			throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
}
