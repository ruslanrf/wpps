/**
 * 
 */
package tuwien.dbai.wpps.core.ie.rdfimpl;

import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.ICheckImplInitialization;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 16, 2012 11:42:15 PM
 */
public final class IEBasisAPIImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(IEBasisAPIImpl.class);

	public IEBasisAPIImpl() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
	}
	
	public void setGetObjectsByType(final IArrFunction<Object, Object> getObjectsByType) {
		this.getObjectsByType = getObjectsByType;
	}
	
//	public void setGetObjectsFromRdfModel(final IArrFunction<Object, Object> getObjectsFromRdfModel) {
//		this.getObjectsFromRdfModel = getObjectsFromRdfModel;
//	}
	
	public void setGetObjects(final IArrFunction<Object, Object> getObjects) {
		this.getObjects = getObjects;
	}
	
	private IArrFunction<Object, Object> getObjectsByType = null;
//	private IArrFunction<Object, Object> getObjectsFromRdfModel = null;
	private IArrFunction<Object, Object> getObjects = null;
	
	@SuppressWarnings("unchecked")
	public Set<Resource> getObjectsByType(final Class<? extends IInstanceAdp>[] viewArr) {
		return (Set<Resource>) getObjectsByType.apply((Object)viewArr);
	}
	
//	@SuppressWarnings("unchecked")
//	public Set<Resource> getObjectsFromRdfModel(final EWPOntSubModel m) {
//		return (Set<Resource>) getObjectsFromRdfModel.apply(m);
//	}
	
	@SuppressWarnings("unchecked")
	public Set<Resource> getObjects() {
		return (Set<Resource>) getObjects.apply();
	}
	
	@Override
	public boolean allFunctionsAreImplemented() {
		return getObjectsByType != null;
	}

}
