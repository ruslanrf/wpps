/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

import com.google.inject.Singleton;

/**
 * Type of the link, in case if selected object is an html link, regarding the url of corresponding web page.
 * There are three types of link: "local" (host, path are checked), "domain" (protocol, host are checked), "external".
 * <ul>
 * <li>Feature dependency: {@linkplain EFeatureDependency#RELATIVE Relative}.</li>
 * <li>Type of dependency: {@linkplain EConsideredObject#TARGET_OBJECT} - {@linkplain EConsideredObject#WEB_PAGE}. </li>
 * <ul>
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 22, 2012 9:18:04 PM
 */
@Singleton
public class LinkTypeROTWFDesc extends AFeatureDescription {
	
	public static String LOCAL = "local";
	
	public static String DOMAIN = "domain";
	
	public static String EXTERNAL = "external";
	
	protected LinkTypeROTWFDesc() {
		super("598d233c-04ea-11e2-b803-00247e160239" 
				, "Link type"
				, "LinkTypeROTW"
				, EFeatureDependency.RELATIVE
				, EConsideredObject.TARGET_OBJECT
				, EConsideredObject.WEB_PAGE,
				String.class);
	}

}
