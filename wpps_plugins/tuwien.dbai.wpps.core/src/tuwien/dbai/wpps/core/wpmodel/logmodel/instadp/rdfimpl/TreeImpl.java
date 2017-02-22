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
 * @created Apr 16, 2012 2:36:15 AM
 */
@Singleton
public class TreeImpl implements ICheckImplInitialization {

	public void setGetRoot(final IArrFunction<Object, Object> getRoot) {
		this.getRoot = getRoot;
	}
	
	private IArrFunction<Object, Object> getRoot = null;
	
	public Resource getRoot(final Resource primRes) {
		return (Resource)getRoot.apply(primRes);
	}

	@Override
	public boolean allFunctionsAreImplemented() {
		return getRoot != null;
	}
	
}
