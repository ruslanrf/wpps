/**
 * File name: MDXPath.java
 * @created: Oct 19, 2011 4:09:35 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common;

/**
 * @created: Oct 19, 2011 4:09:35 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class MDXPath {
	
	public static final String DOC_MARKER = "/WPPS-DOC";
	public static final String DOC_TAG = "WPPS-DOC";
//	public static final String ROOT = "/wpps-browser";
	public static final String EMPTY = "";
	
//	public static final String join(String mdXPath1, String mdXPath2) {
//		return mdXPath1+DOC_SEPARATOR+mdXPath2;
//	}
	
	public static final String appendXPath(String mdXPath, String xPath) {
		return mdXPath+xPath;
	}
	
	public static final String appendXPathToInnerDoc(String mdXPath, String xPath) {
		return mdXPath+xPath+DOC_MARKER;
	}

}
