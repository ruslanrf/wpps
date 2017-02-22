/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Ratio between objects aligned with target object within its context to all objects considered in the context.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#CONTEXT}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 17, 2012 5:47:59 PM
 */
@Singleton
public class AlignmentFactorROCFDesc extends AFeatureDescription {

	public AlignmentFactorROCFDesc() {
		super("20d9129e-00df-11e2-b323-00247e160239"
				, "Alignment factor within the context"
				, "AlignmentFactorROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				Double.class);
	}
	
}
