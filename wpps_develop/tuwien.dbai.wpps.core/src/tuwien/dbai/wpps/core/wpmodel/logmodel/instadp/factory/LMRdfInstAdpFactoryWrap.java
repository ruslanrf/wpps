/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnknownType;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequenceItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITree;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITreeNode;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.factory.IMRdfInstAdpFactoryWrap;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 2:21:41 PM
 */
@Singleton
public class LMRdfInstAdpFactoryWrap {
	private static final Logger log = Logger.getLogger(IMRdfInstAdpFactoryWrap.class);

	protected final TypeCastImpl typeCastImpl;

	private final ILMRdfInstAdpFactory factory;
	
	
	@Inject
	public LMRdfInstAdpFactoryWrap(final TypeCastImpl typeCastImpl
			,ILMRdfInstAdpFactory factory) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"In constructor. It is singleton");
		this.typeCastImpl = typeCastImpl;
		this.factory = factory;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IInstanceAdp> T createAdp(Resource inst, Class<T> typ) {
		if (ISequence.class.equals(typ))
			return (T)factory.createSequenceStructure(inst);
		if (ISequenceItem.class.equals(typ))
			return (T)factory.createSequenceItem(inst);
		if (ITree.class.equals(typ))
			return (T)factory.createTreeStructure(inst);
		if (ITreeNode.class.equals(typ))
			return (T)factory.createTreeNode(inst);
		else { // if this is not a basic type
			// get corresponding type as enum + its model
			final Object[] res = TypeCastManagerLibSupport.getMainInstTypeAndModel(typ);
			// if it must have an implimentation
			if (((IRdfInstType)res[0]).isCanBeInstantiated())
				throw new UnknownType(log, typ.getSimpleName());
			
			// if this type (its structural counterpart) corresponds to the IM
			if (EWPOntSubModel.LOGICAL_MODEL.equals(res[1])) {
				final IRdfInstType instType = typeCastImpl.as(inst, (IRdfInstType)res[0], (EWPOntSubModel)res[1]);
				return (T)createAdp(inst, instType.getJavaInterface());
			}
			else
				throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
}
