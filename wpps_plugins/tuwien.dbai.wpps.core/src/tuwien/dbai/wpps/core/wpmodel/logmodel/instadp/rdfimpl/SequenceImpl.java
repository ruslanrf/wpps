/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl;

import java.util.Collection;
import java.util.List;

import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.ICheckImplInitialization;

import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 9, 2012 9:57:40 PM
 */
@Singleton
public class SequenceImpl implements ICheckImplInitialization {
	
	public void setGetItems(final IArrFunction<Object, Object> getItems) {
		this.getItems = getItems;
	}
	
	public void setAppendItem(final IArrFunction<Object, Object> appendItem) {
		this.appendItem = appendItem;
	}
	
	public void setAppendItems(final IArrFunction<Object, Object> appendItems) {
		this.appendItems = appendItems;
	}
	
	private IArrFunction<Object, Object> getItems = null;
	private IArrFunction<Object, Object> appendItem = null;
	private IArrFunction<Object, Object> appendItems = null;

	@SuppressWarnings("unchecked")
	public List<Resource> getItems(final Resource primRes) {
		return (List<Resource>)getItems.apply(primRes);
	}
	
	public void appendItem(final Resource primRes, final Resource refRes) {
		appendItem.apply(primRes, refRes);
	}
	
	public void appendItems(final Resource primRes, final Collection<Resource> refResCol) {
		appendItems.apply(primRes, refResCol);
	}

	@Override
	public boolean allFunctionsAreImplemented() {
		return getItems != null && appendItem != null && appendItems != null;
	}

}
