/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import com.google.inject.Singleton;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;


/**
 * Ratio of objects vertically aligned with target object +1 to horizontally aligned objects +1 in the context.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#CONTEXT}. </li>
 * <ul>
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 18, 2012 12:00:16 AM
 */
@Singleton
public class AlignmentVertHorRatioROCFDesc extends AFeatureDescription {

	protected AlignmentVertHorRatioROCFDesc() {
		super("23a9deca-0113-11e2-b99f-00247e160239"
				, "Hor. to vert. alignment rate within the context"
				, "AlignmentVertHorRatioROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT
				, Double.class);
	}

}
