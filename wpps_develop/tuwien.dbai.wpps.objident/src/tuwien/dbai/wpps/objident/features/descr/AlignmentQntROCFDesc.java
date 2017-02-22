/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Number of elements aligned with the target object within its context.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#CONTEXT}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 17, 2012 11:23:12 AM
 */
@Singleton
public class AlignmentQntROCFDesc extends AFeatureDescription {

	/**
	 * @param Id
	 * @param name
	 * @param featureDependency
	 * @param primaryObjectType
	 * @param referenceObjectType
	 * @param valueType
	 */
	public AlignmentQntROCFDesc() {
		super("2a53acb0-00aa-11e2-b738-00247e160239"
				, "Number of alignments with the target object within the context" // Number of alignment with the target object within the context
				, "AlignmentQntROC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				Integer.class);
	}

}
