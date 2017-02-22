/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl;

import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.ICheckImplInitialization;

import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 19, 2012 11:43:06 PM
 */
@Singleton
public class SequenceItemImpl implements ICheckImplInitialization {
	
	public void setNext(final IArrFunction<Object, Object> next) {
		this.next = next;
	}
	
	public void setGetSequence(final IArrFunction<Object, Object> getSequence) {
		this.getSequence = getSequence;
	}
	
	private IArrFunction<Object, Object> next = null;
	private IArrFunction<Object, Object> getSequence = null;
	
	public Resource next(final Resource primRes) {
		return (Resource)next.apply(primRes);
	}
	
	public Resource getSequence(final Resource primRes) {
		return (Resource)getSequence.apply(primRes);
	}
	
	@Override
	public boolean allFunctionsAreImplemented() {
		return next != null && getSequence != null;
	}

}
