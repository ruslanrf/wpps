/**
 * 
 */
package tuwien.dbai.wpps.core.ie.instadp.factory;

import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.ie.instadp.javaframe.JavaResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Factory which create instance of {@linkplain IGenericResults}
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 15, 2012 7:43:51 PM
 * @see IGenericResults
 * @see JavaGenericResults
 * 
 */
@Singleton
public class ResultsObjectsFactory {

	@SuppressWarnings("unused")
	private final WPPSConfig config; // TODO: configuration can be considered.
//	private final GenericResultsRdfConverter genericResultsRdfConverter;
	private final ResultsRdfConverter resultsRdfConverter;
	
	private final TypeCastImpl typeCastImpl;
	private final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap;
	
	@Inject
	public ResultsObjectsFactory(final WPPSConfig config
//			, final GenericResultsRdfConverter genericResultsRdfConverter
			, final ResultsRdfConverter resultsRdfConverter
			, TypeCastImpl typeCastImpl
			, RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap) {
		this.config = config;
//		this.genericResultsRdfConverter = genericResultsRdfConverter;
		this.resultsRdfConverter = resultsRdfConverter;
		this.typeCastImpl = typeCastImpl;
		this.rdfInstAdpFactoryWrap = rdfInstAdpFactoryWrap;
	}
	
//	@Deprecated
//	public final <T extends IInstanceAdp> IGenericResults<T> createGenericResultBlock(final Class<T> clazz) {
//		return new JavaGenericResults<T>(clazz, genericResultsRdfConverter);
//	}
	
	public final IResults createResultBlock() {
		return new JavaResults(resultsRdfConverter, typeCastImpl, rdfInstAdpFactoryWrap);
	}
	
}
