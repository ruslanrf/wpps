/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.instadp.factory;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.optimization.FunctionWithMemory;
import tuwien.dbai.wpps.core.config.WPPSConfig;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 1, 2012 3:36:43 PM
 */
@Singleton
public class WhetherComputeAttrOrRelByRequest extends FunctionWithMemory<Enum<?>, Boolean> {

	@Inject
	public WhetherComputeAttrOrRelByRequest(final WPPSConfig config) {
		super(new IFunction<Enum<?>, Boolean>() {
			@Override public Boolean apply(final Enum<?> avar) {
				return config.getComputeByRequestBasedOnQntFeatures().contains(avar);
			} }, 50);
	}
}
