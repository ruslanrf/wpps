/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory.LMRdfInstanceFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalObject;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory.BGMRdfInstanceFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IAbstractBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.factory.DOMRdfInstanceFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMNode;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.factory.IMRdfInstanceFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.factory.VMRdfInstanceFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IAbstractVisualObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 23, 2012 6:23:38 PM
 */
@Singleton
public class RdfInstanceFactoryWrap {
	private static final Logger log = Logger.getLogger(RdfInstanceFactoryWrap.class);

	protected final DOMRdfInstanceFactoryWrap domRdfInstanceFactoryWrap;
	private final BGMRdfInstanceFactoryWrap bgmRdfInstanceFactoryWrap;
	private final IMRdfInstanceFactoryWrap imRdfInstanceFactoryWrap;
	protected final VMRdfInstanceFactoryWrap vmRdfInstanceFactoryWrap;
	
	private final LMRdfInstanceFactoryWrap lmRdfInstanceFactoryWrap;
	
	@Inject
	public RdfInstanceFactoryWrap(
			final DOMRdfInstanceFactoryWrap domRdfInstanceFactoryWrap
			, final BGMRdfInstanceFactoryWrap bgmRdfInstanceFactoryWrap
			, final IMRdfInstanceFactoryWrap imRdfInstanceFactoryWrap
			, final LMRdfInstanceFactoryWrap lmRdfInstanceFactoryWrap
			, final VMRdfInstanceFactoryWrap vmRdfInstanceFactoryWrap) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"In constructor. It is singleton");
		this.domRdfInstanceFactoryWrap = domRdfInstanceFactoryWrap;
		this.bgmRdfInstanceFactoryWrap = bgmRdfInstanceFactoryWrap;
		this.imRdfInstanceFactoryWrap = imRdfInstanceFactoryWrap;
		this.vmRdfInstanceFactoryWrap = vmRdfInstanceFactoryWrap;
		this.lmRdfInstanceFactoryWrap = lmRdfInstanceFactoryWrap;
	}
	
//	public <T extends IInstanceAdp> Resource createObject(final Class<T> typ, final Object... params) {
//		return createObject(null, typ, params);
//	}
	
	/**
	 * Corresponding factory is detected by the typ and hierarchy of Java classes.
	 * Alternative approach can be using function
	 * {@linkplain TypeCastManagerLibSupport#getMainInstTypeAndModel(Class)}.
	 * 
	 * @param inst can be null.
	 * @param typ
	 * @return
	 */
	public <T extends IInstanceAdp> Resource createObject(final Resource rdfInst, final Class<T> typ, final Object... params) {
		Resource tmpRdfInst = null;
		if (IDOMNode.class.isAssignableFrom(typ))
			tmpRdfInst = domRdfInstanceFactoryWrap.createObject(rdfInst, typ, params);
		if (IAbstractBlock.class.isAssignableFrom(typ))
			tmpRdfInst = bgmRdfInstanceFactoryWrap.createObject(rdfInst, typ, params);
		else if (IIMElement.class.isAssignableFrom(typ))
			tmpRdfInst = imRdfInstanceFactoryWrap.createObject(rdfInst, typ, params);
		else if (IAbstractVisualObject.class.isAssignableFrom(typ))
			tmpRdfInst = vmRdfInstanceFactoryWrap.createObject(rdfInst, typ, params);
		else if (ILogicalObject.class.isAssignableFrom(typ))
			tmpRdfInst = lmRdfInstanceFactoryWrap.createObject(rdfInst, typ);
		else
			log.warn(TSForLog.getTS(log)+"Unknown type "+typ.getName());
		
//		else if (IIMElement.class.isAssignableFrom(typ))
//			return imRdfInstAdpFactoryWrap.createAdp(inst, typ);
//		else if (IAbstractVisualObject.class.isAssignableFrom(typ))
//			return vmRdfInstAdpFactoryWrap.createAdp(inst, typ);
//		else if (ILogicalObject.class.isAssignableFrom(typ))
//			return lmRdfInstAdpFactoryWrap.createAdp(inst, typ);

		return tmpRdfInst;
	}
}
