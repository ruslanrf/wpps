/**
 * 
 */
package tuwien.dbai.wpps.core.spatialindex;

import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMBorderNu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMLeftBorderMu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMRightBorderMu;
import tuwien.dbai.wpps.core.fuzzy.FuzzyComparator;
import tuwien.dbai.wpps.core.fuzzy.IMuZeroDouble;
import tuwien.dbai.wpps.core.fuzzy.Nu;
import tuwien.dbai.wpps.core.spatialindex.jsi.RectangleDynamicUtils;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.BlockQltRelationsLib;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 1, 2012 2:29:21 PM
 */
@Singleton // binding in Module. singleton
public class RectangleDynamicUtilsProvider implements Provider<RectangleDynamicUtils> {
	
	private final FuzzyComparator c;
	
	public RectangleDynamicUtilsProvider(final IMuZeroDouble[][] mu, final Nu nu) {
		this.c = new FuzzyComparator(mu, nu);
	}
	
	@Inject
	public RectangleDynamicUtilsProvider(
			@AnnotQltBMLeftBorderMu final IMuZeroDouble muPtMin
			, @AnnotQltBMRightBorderMu final IMuZeroDouble muPtMax
			, @AnnotQltBMBorderNu final Nu nu) {
		this.c = new FuzzyComparator(new IMuZeroDouble[][]{{muPtMin, muPtMax}, {muPtMin, muPtMax}}, nu);
	}

	@Override
	public RectangleDynamicUtils get() {
		return new RectangleDynamicUtils(
				new IArrFunction<Float, Boolean>() {
					@Override
					public Boolean apply(Float... avars) {
						return BlockQltRelationsLib.hasRCC8_O(avars[0], avars[1], avars[2]
								, avars[3], avars[4], avars[5], avars[6], avars[7], c);
					}
				},
				new IArrFunction<Float, Boolean>() {
					@Override
					public Boolean apply(Float... avars) {
						return BlockQltRelationsLib.hasRCC8_P(avars[4], avars[5], avars[6], avars[7]
								, avars[0], avars[1], avars[2], avars[3], c);
					}
				}
				);
	}
	
}
