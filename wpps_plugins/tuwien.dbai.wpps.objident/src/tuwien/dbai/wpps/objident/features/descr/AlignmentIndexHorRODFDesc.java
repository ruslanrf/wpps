/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;


/**
 * Index of target object in horizontally aligned elements within the web document.
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#WEB_DOCUMENT}. </li>
 * <ul>
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 18, 2012 12:12:10 PM
 */
@Singleton
public class AlignmentIndexHorRODFDesc extends AFeatureDescription {

	public AlignmentIndexHorRODFDesc() {
		super("69826b18-0179-11e2-9847-00247e160239"
				, "Index in hor. alignment in doc"
				, "AlignmentIndexHorROD"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.WEB_DOCUMENT,
				Integer.class);
	}

}
