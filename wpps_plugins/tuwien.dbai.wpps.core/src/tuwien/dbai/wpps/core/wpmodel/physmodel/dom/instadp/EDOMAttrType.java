/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp;

import tuwien.dbai.wpps.ontschema.DOMOnt;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 24, 2012 1:30:59 PM
 */
public enum EDOMAttrType {
	/**
	 * Reflects both {@linkplain DOMOnt#hasAttribute relation} between {@linkplain DOMOnt#Element element}
	 * with attribute and directly {@linkplain DOMOnt#Attribute attribute}.
	 */
	HAS_ATTRIBUTE,
	
	/**
	 * Corresponds to {@linkplain DOMOnt#hasNodeName} for {@linkplain DOMOnt#Element}.
	 */
	HAS_ELEMENT_NAME,
	
	/**
	 * Corresponds to {@linkplain DOMOnt#hasNodeValue} for {@linkplain DOMOnt#Text}.
	 */
	HAS_TEXT_CONTENT
	
}
