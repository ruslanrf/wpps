/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnknownType;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IPlainVisualObject;

import com.google.inject.Inject;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 18, 2012 9:14:51 PM
 */
public class VMRdfInstAdpFactoryWrap {
	private static final Logger log = Logger.getLogger(VMRdfInstAdpFactoryWrap.class);

	protected final TypeCastImpl typeCastImpl;

	private final IVMRdfInstAdpFactory factory;
	
	
	@Inject
	public VMRdfInstAdpFactoryWrap(final TypeCastImpl typeCastImpl
			,IVMRdfInstAdpFactory factory) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"In constructor. It is singleton");
		this.typeCastImpl = typeCastImpl;
		this.factory = factory;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IInstanceAdp> T createAdp(Resource inst, Class<T> typ) {
		if (IPlainVisualObject.class.equals(typ))
			return (T)factory.createPlainVisualObjectAdp(inst);
		else { // if this is not a basic type
			// get corresponding type as enum + its model
			final Object[] res = TypeCastManagerLibSupport.getMainInstTypeAndModel(typ);
			// if it must have an implimentation
			if (((IRdfInstType)res[0]).isCanBeInstantiated())
				throw new UnknownType(log, typ.getSimpleName());
			// if this type (its structural counterpart) corresponds to the BGM
			if (EWPOntSubModel.STRUCT_VISUAL_MODEL.equals(res[1])) {
				final IRdfInstType instType = typeCastImpl.as(inst, (IRdfInstType)res[0], (EWPOntSubModel)res[1]);
				return (T)createAdp(inst, instType.getJavaInterface());
			}
			else
				throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
}
