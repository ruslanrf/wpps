/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Number of elements horizontally aligned with the target object within the web document.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#CONTEXT}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 23, 2012 12:14:14 PM
 */
@Singleton
public class AlignmentHorQntROCFDesc extends AFeatureDescription {

	public AlignmentHorQntROCFDesc() {
		super("84198848-0567-11e2-a2cf-00247e160239"
				, "Number of objects in the context horizontally aligned with target object"
				, "AlignmentHorQntROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				Integer.class);
	}
	
}
