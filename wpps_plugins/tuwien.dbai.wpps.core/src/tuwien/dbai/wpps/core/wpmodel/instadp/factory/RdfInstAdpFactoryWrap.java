/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.factory;

import org.apache.commons.collections.ListUtils;
import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory.LMRdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalObject;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory.BGMRdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IAbstractBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.factory.DOMRdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMNode;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.factory.IMRdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.factory.VMRdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IAbstractVisualObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 22, 2011 7:01:47 PM
 */
@Singleton
public class RdfInstAdpFactoryWrap {
	private static final Logger log = Logger.getLogger(RdfInstAdpFactoryWrap.class);

	private final IInstanceAdpFactory iInstanceAdpFactory;
	private final BGMRdfInstAdpFactoryWrap bgmRdfInstAdpFactoryWrap;
	private final DOMRdfInstAdpFactoryWrap domRdfInstAdpFactoryWrap;
	private final IMRdfInstAdpFactoryWrap imRdfInstAdpFactoryWrap;
	private final VMRdfInstAdpFactoryWrap vmRdfInstAdpFactoryWrap;
	private final LMRdfInstAdpFactoryWrap lmRdfInstAdpFactoryWrap;
	
	private final TypeCastImpl typeCastImpl;
	
	@Inject
	public RdfInstAdpFactoryWrap(
			final IInstanceAdpFactory iInstanceAdpFactory
			, final BGMRdfInstAdpFactoryWrap bgmRdfInstAdpFactoryWrap
			, final DOMRdfInstAdpFactoryWrap domRdfInstAdpFactoryWrap
			, final IMRdfInstAdpFactoryWrap imRdfInstAdpFactoryWrap
			, final VMRdfInstAdpFactoryWrap vmRdfInstAdpFactoryWrap
			, final LMRdfInstAdpFactoryWrap lmRdfInstAdpFactoryWrap
			, final TypeCastImpl typeCastImpl) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"In constructor. It is singleton");
		this.iInstanceAdpFactory = iInstanceAdpFactory;
		this.bgmRdfInstAdpFactoryWrap = bgmRdfInstAdpFactoryWrap;
		this.domRdfInstAdpFactoryWrap = domRdfInstAdpFactoryWrap;
		this.imRdfInstAdpFactoryWrap = imRdfInstAdpFactoryWrap;
		this.vmRdfInstAdpFactoryWrap = vmRdfInstAdpFactoryWrap;
		this.lmRdfInstAdpFactoryWrap = lmRdfInstAdpFactoryWrap;
		
		this.typeCastImpl = typeCastImpl;
	}
	
	
	/**
	 * TODO implement: IHtmlWebFormElement
	 * 
	 * Corresponding factory is detected by the typ and hierarchy of Java classes.
	 * Alternative approach can be using function
	 * {@linkplain TypeCastManagerLibSupport#getMainInstTypeAndModel(Class)}.
	 * 
	 * This function does not check a possibility to create an adapter for the individual provided. 
	 * 
	 * @param inst
	 * @param typ
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends IInstanceAdp> T createAdp(Resource inst, Class<T> typ) {
		if (IInstanceAdp.class.equals(typ))
			return (T)iInstanceAdpFactory.createInstanceAdp(inst);
		else if (IAbstractBlock.class.isAssignableFrom(typ))
			return bgmRdfInstAdpFactoryWrap.createAdp(inst, typ);
		else if (IIMElement.class.isAssignableFrom(typ))
			return imRdfInstAdpFactoryWrap.createAdp(inst, typ);
		else if (IDOMNode.class.isAssignableFrom(typ))
			return domRdfInstAdpFactoryWrap.createAdp(inst, typ);
		else if (IAbstractVisualObject.class.isAssignableFrom(typ))
			return vmRdfInstAdpFactoryWrap.createAdp(inst, typ);
		else if (ILogicalObject.class.isAssignableFrom(typ))
			return lmRdfInstAdpFactoryWrap.createAdp(inst, typ);
log.error(TSForLog.getTS(log)+"Unknown type "+typ.getName());
		return null;
	}
	
	/**
	 * Check if adpater can be created for the individual provided.
	 * It checks it's classes.
	 * @param inst
	 * @param typ
	 * @return
	 */
	public <T extends IInstanceAdp> boolean canCreateAdp(Resource inst, Class<T> typ) {
		final Object[] res = TypeCastManagerLibSupport.getMainInstTypeAndModel(typ);
		return typeCastImpl.canAs(inst, (IRdfInstType)res[0], (EWPOntSubModel)res[1]);
	}
	
}
