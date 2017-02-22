/**
 * File name: TypeCastManager.java
 * @created: May 2, 2011 6:21:05 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.ITypeCastManager;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLib;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;

import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Instance of this class is provided to the adapters of ontological objects which implement it.
 * This class is used for type casting.
 * 
 * @created: May 2, 2011 6:21:05 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * @see ITypeCastManager
 * @see TypeCastManagerLib
 * @see TypeCastManagerLibSupport
 */
@Singleton
public final class TypeCastImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(TypeCastImpl.class);
	
//	final Map<EWPOntSubModel, Model> models = new HashMap<EWPOntSubModel, Model>();
	
	public TypeCastImpl() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
//		models.put(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL, bgosModel);
	}

//	public void setCanAs(final IArrFunction<Object, Object> canAsF1) {
//		this.canAs = canAsF1;
//	}
//	
//	public void setAs(final IArrFunction<Object, Object> asF1) {
//		this.as = asF1;
//	}
	
	public IArrFunction<Object, Object> canAs = null;
	public IArrFunction<Object, Object> as = null;
//	public IArrFunction<Object, Object> makeAs = null;
	
	/**
	 * Check the type of the instance and existence of implementation
	 * 
	 * @param <T>
	 * @param inst
	 * @return
	 */
	public boolean canAs(final Resource inst,
			final IRdfInstType clazz, final EWPOntSubModel model) {
		return (Boolean)canAs.apply(inst, clazz, model);
	}

	/**
	 * IInstanceAdp can be converted in different implementation types.
	 * 
	 * @param <T>
	 * @param inst
	 * @return
	 */
	public IRdfInstType as(final Resource inst,
			final IRdfInstType clazz, final EWPOntSubModel model) {
		return (IRdfInstType)as.apply(inst, clazz, model);
	}
	
	@Override
	public boolean allFunctionsAreImplemented() {
		return canAs != null && as != null; //&& makeAs != null;
	}

}
