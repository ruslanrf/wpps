/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory;

import tuwien.dbai.wpps.core.WPPSFramework;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalObject;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * TODO: Under development :-)
 * TODO: Cjange as 
 * This class is used by wrapper developer.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 22, 2012 10:01:49 PM
 * @see WPPSFramework
 */
@Deprecated
@Singleton
public final class LMObjectsFactory {
//	private static final Logger log = Logger.getLogger(LMObjectsFactory.class);
	
	private final LMRdfInstanceFactoryWrap lmRdfInstanceFactoryWrap;
	private final LMRdfInstAdpFactoryWrap lmRdfInstAdpFactoryWrap;
	
	@Inject
	public LMObjectsFactory(
			final LMRdfInstanceFactoryWrap lmRdfInstanceFactoryWrap
			, final LMRdfInstAdpFactoryWrap lmRdfInstAdpFactoryWrap) {
		this.lmRdfInstanceFactoryWrap = lmRdfInstanceFactoryWrap;
		this.lmRdfInstAdpFactoryWrap = lmRdfInstAdpFactoryWrap;
	}
	
	public <T extends ILogicalObject> T createObject(Class<T> typ) {
		return createObject(null, typ);
	}
	
	/**
	 * Create object in the ontology for particular adapter from Physical model. 
	 * @param inst Adapter of the RDF instance in the Physical model. Can be {@code null}.
	 * @param typ Class of adapter whcih has its implementation (can be instantiated).
	 * @return instance of adapter of type {@code typ}.
	 */
	public <T extends ILogicalObject> T createObject(final IInstanceAdp inst, Class<T> typ) {
		if (inst == null)
			return lmRdfInstAdpFactoryWrap.createAdp(
					lmRdfInstanceFactoryWrap.createObject(null, typ)
					, typ);
		return lmRdfInstAdpFactoryWrap.createAdp(
				lmRdfInstanceFactoryWrap.createObject(InstAdpLibSupport.getResourceOrNull(inst), typ)
				, typ);
	}
	
//	/**
//	 * Instantiate object in the logical ontological model.
//	 * @param res result container.
//	 * @param typ type of the concept to be created in the logical model.
//	 * @return adapter for the type created.
//	 */
//	public <U extends IInstanceAdp, T extends ICompositeLO> T
//			createComplexObjectFromResult(final IResults<U> res, Class<T> typ) {
////		if (!ICompositeLO.class.isAssignableFrom(typ))
////			throw new GeneralUncheckedException(log, "Parameter should be of type ");
//		if (ISequence.class.equals(typ)) {
//			final ISequence seq = createObject(res, ISequence.class);
//			final Iterator<U> iter = res.iterator();
//			while (iter.hasNext()) {
//				final IItem item = createObject(iter.next(), IItem.class);
//				seq.appendItem(item);
//			}
//		}
//		throw new UnknownType(log, typ);
//	}

}
