/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory.LMRdfObjectsFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalObject;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory.BGMRdfObjectsFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IAbstractBlock;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Create object. Depends on the configuration.
 * TODO: add dependence from configuration. Based on it using specified factories,
 * this class decides would it be rdf or java object, which attributes and relations it should have.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 15, 2012 6:24:24 PM
 */
@Singleton @Deprecated
public class RdfObjectsFactoryWrap {
	private static final Logger log = Logger.getLogger(RdfObjectsFactoryWrap.class);

	private final BGMRdfObjectsFactoryWrap bgmObjectsFactoryWrap;
	private final LMRdfObjectsFactoryWrap lmRdfObjectsFactoryWrap;
	
	@Inject
	public RdfObjectsFactoryWrap(
			final BGMRdfObjectsFactoryWrap bgmObjectsFactoryWrap
			, final LMRdfObjectsFactoryWrap lmRdfObjectsFactoryWrap) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"In constructor. It is singleton");
		this.bgmObjectsFactoryWrap = bgmObjectsFactoryWrap;
		this.lmRdfObjectsFactoryWrap = lmRdfObjectsFactoryWrap;
	}
	
	public <T extends IInstanceAdp> T createObject(final Class<T> typ, final Object... params) {
		return createObject(null, typ, params);
	}
	
	/**
	 * Corresponding factory is detected by the typ and hierarchy of Java classes.
	 * Alternative approach can be using function
	 * {@linkplain TypeCastManagerLibSupport#getMainInstTypeAndModel(Class)}.
	 * 
	 * @param inst
	 * @param typ
	 * @return
	 */
	public <T extends IInstanceAdp> T createObject(final Resource rdfInst, final Class<T> typ, final Object... params) {
		if (IAbstractBlock.class.isAssignableFrom(typ))
			return bgmObjectsFactoryWrap.createObject(rdfInst, typ, params);
		if (ILogicalObject.class.isAssignableFrom(typ))
			return lmRdfObjectsFactoryWrap.createObject(rdfInst, typ);
		
//		else if (IIMElement.class.isAssignableFrom(typ))
//			return imRdfInstAdpFactoryWrap.createAdp(inst, typ);
//		else if (IAbstractVisualObject.class.isAssignableFrom(typ))
//			return vmRdfInstAdpFactoryWrap.createAdp(inst, typ);
//		else if (ILogicalObject.class.isAssignableFrom(typ))
//			return lmRdfInstAdpFactoryWrap.createAdp(inst, typ);
log.warn(TSForLog.getTS(log)+"Unknown type "+typ.getName());
		return null;
	}
}
