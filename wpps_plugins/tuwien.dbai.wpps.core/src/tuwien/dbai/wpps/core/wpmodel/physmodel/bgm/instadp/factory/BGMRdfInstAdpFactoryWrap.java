/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnknownType;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IInnerBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOuterBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Wrapper for {@link IBGMRdfInstAdpFactory}. It provides more convenient was to get adapters for rdf instances in Jena.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 22, 2011 6:51:20 PM
 */
@Singleton
public final class BGMRdfInstAdpFactoryWrap {
	private static final Logger log = Logger.getLogger(BGMRdfInstAdpFactoryWrap.class);

	protected final TypeCastImpl typeCastImpl;

	private final IBGMRdfInstAdpFactory factory;
	
	
	@Inject
	public BGMRdfInstAdpFactoryWrap(final TypeCastImpl typeCastImpl
			,IBGMRdfInstAdpFactory factory) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"In constructor. It is singleton");
		this.typeCastImpl = typeCastImpl;
		this.factory = factory;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IInstanceAdp> T createAdp(Resource inst, Class<T> typ) {
		if (IWebDocumentBlock.class.equals(typ))
			return (T)factory.createWebPageBlockAdp(inst);
		else if (IWebPageBlock.class.equals(typ))
			return (T)factory.createWindowBlockAdp(inst);
		else if(IViewPortBlock.class.equals(typ))
			return (T)factory.createViewPortBlockAdp(inst);
		else if(IBoundingBlock.class.equals(typ))
			return (T)factory.createBoundingBlockAdp(inst);
		else if(IBox.class.equals(typ))
			return (T)factory.createBoxAdp(inst);
		else if(IInnerBlock.class.equals(typ))
			return (T)factory.createInnerBlockAdp(inst);
		else if(IOuterBlock.class.equals(typ))
			return (T)factory.createOuterBlockAdp(inst);
//		else if(IOutlineBlock.class.equals(typ))
//			return (T)factory.createOutlineBlockAdp(inst);
		else if(IOutline.class.equals(typ))
			return (T)factory.createOutlineAdp(inst);
		else if(IQntBlock.class.equals(typ))
			return (T)factory.createQntBlockAdp(inst);
		else if(IQltBlock.class.equals(typ))
			return (T)factory.createQltBlockAdp(inst);
		else { // if this is not a basic type
			// get corresponding type as enum + its model
			final Object[] res = TypeCastManagerLibSupport.getMainInstTypeAndModel(typ);
			// if it must have an implimentation
			if (((IRdfInstType)res[0]).isCanBeInstantiated())
				throw new UnknownType(log, typ.getSimpleName());
			
			// if this type (its structural counterpart) corresponds to the BGM
			if (EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL.equals(res[1])) {
				final IRdfInstType instType = typeCastImpl.as(inst, (IRdfInstType)res[0], (EWPOntSubModel)res[1]);
				return (T)createAdp(inst, instType.getJavaInterface());
			}
			else
				throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
	
}
