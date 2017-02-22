/**
 * 
 */
package tuwien.dbai.wpps.core.ie.api.basis;

import tuwien.dbai.wpps.core.wpmodel.enriching.AWPModelSystemEnricher;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.ObjectsPublicFactory;

import com.google.inject.Injector;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 19, 2012 11:44:58 AM
 */
//@Singleton
public final class IEAPI {
	
	private final Injector inj;
	
	public IEAPI(final Injector inj) {
		this.inj = inj;
	}
	
//	@Deprecated
//	public final IGenericIEBasisAPI getGenericIEBasisAPI() {
//		return inj.getInstance(IGenericIEBasisAPI.class);
//	}
	
	public final IIEBasisAPI getIEBasisAPI() {
		return inj.getInstance(IIEBasisAPI.class);
	}
	
	public <T extends AWPModelSystemEnricher> T getEnricher(Class<T> clazz) {
		return inj.getInstance(clazz);
	}
	
	public ObjectsPublicFactory getObjectsFactory() {
		return inj.getInstance(ObjectsPublicFactory.class);
	}
	
}
