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
 * @created Apr 16, 2012 2:20:00 AM
 */
@Singleton
public class TreeNodeImpl implements ICheckImplInitialization {

//	public void setGetNextSibling(final IArrFunction<Object, Object> getNextSibling) {
//		this.getNextSibling = getNextSibling;
//	}
//	
//	public void setGetFirstChild(final IArrFunction<Object, Object> getFirstChild) {
//		this.getFirstChild = getFirstChild;
//	}
//	
//	public void setGetLastChild(final IArrFunction<Object, Object> getLastChild) {
//		this.getLastChild = getLastChild;
//	}
	
	IArrFunction<Object, Object> getNextSibling = null;
	IArrFunction<Object, Object> getFirstChild = null;
	IArrFunction<Object, Object> getLastChild = null;
	IArrFunction<Object, Object> appendChildNode = null;
	
	public Resource getNextSibling(final Resource primRes) {
		return (Resource)getNextSibling.apply(primRes);
	}

	public Resource getFirstChild(final Resource primRes) {
		return (Resource)getFirstChild.apply(primRes);
	}
	
	public Resource getLastChild(final Resource primRes) {
		return (Resource)getLastChild.apply(primRes);
	}
	
	public void appendChildNode(final Resource primRes, final Resource refRes) {
		appendChildNode.apply(primRes, refRes);
	}
	
	@Override
	public boolean allFunctionsAreImplemented() {
		return getNextSibling != null && getFirstChild != null && getLastChild != null && appendChildNode != null;
	}
	
	
}
