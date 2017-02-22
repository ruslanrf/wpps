/**
 * 
 */
package tuwien.dbai.wpps.core.module;

import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.api.basis.RdfIEBasisAPI;
import tuwien.dbai.wpps.core.ie.rdfimpl.IEBasisAPIImpl;
import tuwien.dbai.wpps.core.ie.rdfimpl.RdfIEBasisAPIImplProvider;
import tuwien.dbai.wpps.core.spatialindex.ISpatialIndexManager;
import tuwien.dbai.wpps.core.spatialindex.RTreeManager;
import tuwien.dbai.wpps.core.spatialindex.RectangleDynamicUtilsProvider;
import tuwien.dbai.wpps.core.spatialindex.jsi.RectangleDynamicUtils;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 10, 2012 8:06:09 PM
 */
public class IEBasisModule extends AbstractModule {

	@Override
	protected void configure() {
		
		bind(RectangleDynamicUtils.class)
			.toProvider(RectangleDynamicUtilsProvider.class).in(Singleton.class);
		bind(ISpatialIndexManager.class).to(RTreeManager.class).in(Singleton.class);
	
	
//		bind(IGenericIEBasisAPI.class).to(GenericRdfIEBasisAPI.class).in(Singleton.class);
		bind(IIEBasisAPI.class).to(RdfIEBasisAPI.class).in(Singleton.class);
		bind(IEBasisAPIImpl.class).toProvider(RdfIEBasisAPIImplProvider.class).in(Singleton.class);
		
		
//		IBGMObjectsFactory
		
	}

}
