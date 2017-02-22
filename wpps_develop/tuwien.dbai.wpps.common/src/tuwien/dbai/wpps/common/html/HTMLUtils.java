/**
 * 
 */
package tuwien.dbai.wpps.common.html;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 13, 2011 7:51:33 PM
 */
public final class HTMLUtils {

	public static final boolean isWebFormElementWithChildNode(String elName) {
		return EHtmlElementConstants.SELECT.string().equalsIgnoreCase(elName)
				|| EHtmlElementConstants.TEXTAREA.string().equalsIgnoreCase(elName);
	}
	
	public static final boolean isWebFormElementOrSubElement(String elName) {
		return EHtmlElementConstants.BUTTON.string().equalsIgnoreCase(elName)
				|| EHtmlElementConstants.INPUT.string().equalsIgnoreCase(elName)
				|| EHtmlElementConstants.SELECT.string().equalsIgnoreCase(elName)
				|| EHtmlElementConstants.OPTION.string().equalsIgnoreCase(elName)
				|| EHtmlElementConstants.TEXTAREA.string().equalsIgnoreCase(elName);
	}
	
	
	
}
