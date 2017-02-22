/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.factory;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.core.config.WPPSConfig;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 13, 2012 1:25:01 PM
 */
@Singleton
public class WhetherCreateAttrOrRelInOnt implements IFunction<Enum<?>, Boolean> {

	private final WPPSConfig config;

	@Inject
	public WhetherCreateAttrOrRelInOnt(final WPPSConfig config) {
		this.config = config;
	}
	
	@Override
	public Boolean apply(Enum<?> avar) {
		return config.getCreateInOntology().contains(avar);
	}

}

//@Singleton
//public class WhetherCreateAttrOrRelInOnt extends FunctionWithMemory<Enum<?>, Boolean> {
//
//	@Inject
//	public WhetherCreateAttrOrRelInOnt(final WPPSConfig config) {
//		super(new IFunction<Enum<?>, Boolean>() {
//			@Override public Boolean apply(final Enum<?> avar) {
//				return config.getCreateInOntology().contains(avar);
//			} }, 50);
//	}
//
//}
