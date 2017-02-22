/**
 * File name: IHtmlElementAttributesConstants.java
 * @created: Oct 31, 2010 5:16:03 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common.html;

/**
 * @type: IHtmlElementAttributesConstants
 *
 * @created: Oct 31, 2010 5:16:03 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public enum EHtmlElementConstants {
	
	HTML("HTML"),
	BODY("BODY"),

	// NS FOR fictitious TEXT ELEMENT
//		public static final String ADDITIONAL_DOM_EL_NS = "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/inst/additionalDomElements#";
		// NAME OF fictitious TEXT ELEMENT. This element is a Text class individual at the MLElement-ontology
	SCRIPT("SCRIPT"),
	
	FRAME("FRAME"),
	IFRAME("IFRAME"),

	FORM("FORM"),
		// ELEMENT NAME
		BUTTON("BUTTON"),
		INPUT("INPUT"),
		SELECT("SELECT"),
		OPTION("OPTION"),
		TEXTAREA("TEXTAREA"),
		
	IMG("IMG"),
	
	
	TABLE("TABLE"),
	TR("TR"),
	TD("TD"),
	
	UL("UL"),
	OL("OL"),
	DL("DL"),
		LI("LI"),
	
	HR("HR"),
		
	// === INLINE TAGS ===
	A("A"),
	B("B"),
	BIG("BIG"),
	EM("EM"),
	FONT("FONT"),
	I("I"),
	STRONG("STRONG"),
	U("U"),
	SUB("SUB"),
	SUP("SUP"),
	SPAN("SPAN"),
	P("P"),
	PRE("PRE"),
	Q("Q"),
	DT("DT"),
	LABEL("LABEL"),
	LEGEND("LEGEND"),
	CAPTION("CAPTION"),
	
	// ===  ===
	
	// ==========
	// ATTRIBUTES
	// ==========
	CLASS_ATTRIBUTE("class"),
	STYLE_ATTRIBUTE("style"),
	CONTENT_EDITABLE_ATTRIBUTE("contenteditable"),
		
	// ==== WEB FORM ATTRIBUTES ===
	
		FORM_METHOD_POST_VALUE("post"),
		FORM_METHOD_GET_VALUE("get"),
		
	// =========================================
	// === WEB FROM ELEMENT ATTRIBUTES ===
	//==========================================
	
	// IMG
	SRC_ATTRIBUTE("src"),
	// A
	HREF_ATTRIBUTE("href"),
	
	
	// ==== WEB FORM INPUT ATTRIBUTES ===
	TYPE_ATTRIBUTE("type"),
		TYPE_BUTTON_VALUE("button"),
		TYPE_CHECKBOX_VALUE("checkbox"),
		TYPE_FILE_VALUE("file"),
		TYPE_IMAGE_VALUE("image"),
		TYPE_PASSWORD_VALUE("password"),
		TYPE_RADIO_VALUE("radio"),
		TYPE_RESET_VALUE("reset"),
		TYPE_SUBMIT_VALUE("submit"),
		TYPE_TEXT_VALUE("text"),
		TYPE_EMAIL_VALUE("email"),
	
	
	;
	
	
	
	private final String str;
	public final String string() { 
		return str; 
	}
	
	private EHtmlElementConstants(final String str) {
		this.str = str;
	}
	
	public static final EHtmlElementConstants valueOfStrRepr(final String val) {
		EHtmlElementConstants[] valArr = EHtmlElementConstants.values();
		for (int i=0; i<valArr.length; i++) {
			if (valArr[i].string().equals(val))
				return valArr[i];
		}
		return null;
	}
	
	public static final EHtmlElementConstants valueOfStrReprIgnCase(final String val) {
		EHtmlElementConstants[] valArr = EHtmlElementConstants.values();
		for (int i=0; i<valArr.length; i++) {
			if (valArr[i].string().equalsIgnoreCase(val))
				return valArr[i];
		}
		return null;
	}
	
	public boolean equalTo(String o) {
		return str.equals(o);
	}
	
	public boolean equalToIgnCase(String o) {
		return str.equalsIgnoreCase(o);
	}
	
	
}
		
//	// NS FOR fictitious TEXT ELEMENT
////	public static final String ADDITIONAL_DOM_EL_NS = "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/inst/additionalDomElements#";
//	// NAME OF fictitious TEXT ELEMENT. This element is a Text class individual at the MLElement-ontology
//	public static final String ADDITIONAL_TEXT_ELEM = "WPPS-TEXTELEMENT";
//	
//
//	public static final String WEB_FORM = "FORM";
//	// ELEMENT NAME
//	public static final String BUTTON = "BUTTON";
//	public static final String INPUT = "INPUT";
//	public static final String SELECT = "SELECT";
//	public static final String OPTION = "OPTION";
//	public static final String TEXTAREA = "TEXTAREA";
//	
//	public static final String IMG = "IMG";
//	public static final String A = "A";
//	
//	public static final String TABLE = "TABLE";
//	public static final String TD = "TD";
//	public static final String TR = "TR";
//	
//	// ELEMENT ATTRIBUTE'S NAME
//	// INPUT
//	public static final String TYPE = "type";
//	// IMG
//	public static final String SRC = "src";
//	// A
//	public static final String HREF = "href";
//	
//	
//	// ELEMENT ATTRIBUTE'S VALUE
//	public static final String TYPE_BUTTON_VALUE = "button";
//	public static final String TYPE_CHECKBOX_VALUE = "checkbox";
//	public static final String TYPE_FILE_VALUE = "file";
//	public static final String TYPE_IMAGE_VALUE = "image";
//	public static final String TYPE_PASSWORD_VALUE = "password";
//	public static final String TYPE_RADIO_VALUE = "radio";
//	public static final String TYPE_RESET_VALUE = "reset";
//	public static final String TYPE_SUBMIT_VALUE = "submit";
//	public static final String TYPE_TEXT_VALUE = "text";
