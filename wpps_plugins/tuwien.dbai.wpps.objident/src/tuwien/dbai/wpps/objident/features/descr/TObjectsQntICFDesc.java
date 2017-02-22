/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Number of objects in the target object's context excluding the target object.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#CONTEXT}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 17, 2012 6:15:12 PM
 */
@Singleton
public class TObjectsQntICFDesc extends AFeatureDescription {

	public TObjectsQntICFDesc() {
		super("ea4af64e-00e2-11e2-a17f-00247e160239" 
				, "Number of object in context"
				, "TObjectsQntIC"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.CONTEXT,
				Integer.class);
	}

}
