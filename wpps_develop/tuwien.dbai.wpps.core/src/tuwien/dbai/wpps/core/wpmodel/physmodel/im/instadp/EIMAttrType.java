/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 4, 2012 4:57:13 PM
 */
public enum EIMAttrType {

	// === IHtmlText ===
	
	IM_TEXT_VALUE, // for IHtmlTextInput, IHtmlTextArea + implementing IHas VisibleText interface 
	IM_FONT_WEIGHT,
	IM_FONT_SIZE,
	IM_FONT_STYLE,
	IM_TEXT_DECORATION,
	IM_FONT_FAMILY,
	
	// === IHtmlImage, IHtmlLink ===
	
	HAS_IM_URL,
	HAS_IM_ALT_TEXT,
	
	// === IHtmlWebForm ===
	
	IM_WEB_FORM_ACTION,
	IM_WEB_FORM_METHOD,
	
	// === IHtmlTextInput ===
//	HTML_TEXT_INPUT_CONTENT,
	
	// === IHtmlTextArea ===
//	HTML_TEXT_AREA_CONTENT,
	
	// === IHtmlButton ===
	HAS_IM_BUTTON_TYPE,
	IS_HTML_INPUT_BUTTON,
	
	// === IIMCheckBox, IIMRadioButtonBox ===
	IS_IM_CHECKED,
	
	// === IIMList, IHtmlList ===
	HAS_IM_LIST_TYPE,
	
	// === IHtmlSelect ===
	HAS_IM_SELECTED_OPTION,
		
}
