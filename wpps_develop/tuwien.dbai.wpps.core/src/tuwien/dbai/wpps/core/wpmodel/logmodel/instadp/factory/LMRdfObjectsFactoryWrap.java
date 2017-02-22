/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.IItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 22, 2012 7:08:15 PM
 */
@Singleton @Deprecated
public class LMRdfObjectsFactoryWrap {
	private final static Logger log = Logger.getLogger(LMRdfObjectsFactoryWrap.class);

	private final LMRdfObjectsFactory factory;
	
	@Inject
	public LMRdfObjectsFactoryWrap(final LMRdfObjectsFactory factory) {
		this.factory = factory;
	}
	
	public <T extends IInstanceAdp> T createObject(Class<T> typ) {
		return createObject(null, typ);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IInstanceAdp> T createObject(final Resource rdfInst, Class<T> typ) {
		if (ISequence.class.equals(typ))
			return (T)factory.createSequence(rdfInst);
		else if (IItem.class.equals(typ))
			return (T)factory.createSequenceItem(rdfInst);
		else { // if this is not a basic type
			throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
	
}
