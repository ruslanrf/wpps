/**
 * File name: CSSPropertyConstants.java
 * @created: Oct 31, 2010 11:59:19 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common.html;

/**
 * CSS properties: names and values use in the application (WPPS)
 * 
 * @created: Oct 31, 2010 11:59:19 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
// change enum to string?
public enum ECSSPropertyConstants {

	BORDER_TOP_WIDTH("border-top-width"),
	BORDER_RIGHT_WIDTH("border-right-width"),
	BORDER_BOTTOM_WIDTH("border-bottom-width"),
	BORDER_LEFT_WIDTH("border-left-width"),
	DISPLAY("display"),
	DISPLAY_NONE_VALUE("none"),
	// -- block level element
	DISPLAY_BLOCK_VALUE("block"),
	DISPLAY_LIST_ITEM_VALUE("list-item"),
	DISPLAY_TABLE_VALUE("table"),
	// -- inline level element
	DISPLAY_INLINE_VALUE("inline"),
	DISPLAY_INLINE_BLOCK_VALUE("inline-block"),
	DISPLAY_INLINE_TABLE_VALUE("inline-table"),
	// -- table related element
	DISPLAY_TABLE_ROW_VALUE("table-row"),
	DISPLAY_TABLE_ROW_GROUP_VALUE("table-row-group"),
	DISPLAY_TABLE_HEADER_GROUP_VALUE("table-header-group"),
	DISPLAY_TABLE_FOOTER_GROUP_VALUE("table-footer-group"),
	DISPLAY_TABLE_COLUMN_VALUE("table-column"),
	DISPLAY_TABLE_COLUMN_GROUP_VALUE("table-column-group"),
	DISPLAY_TABLE_CELL_VALUE("table-cell"),
	DISPLAY_TABLE_CAPTION_VALUE("table-caption"),
	
	ZINDEX("z-index"),
	ZINDEX_AUTO_VALUE("auto"),
	
	VISIBILITY("visibility"),
	VISIBILITY_VISIBLE_VALUE("visible"),
	VISIBILITY_HIDDEN_VALUE("hidden"),
	
	POSITION("position"),
	POSITION_STATIC_VALUE("static"),
	POSITION_RELATIVE_VALUE("relative"),
	POSITION_ABSOLUTE_VALUE("absolute"),
	POSITION_FIXED_VALUE("fixed"),
	
	FLOAT("float"),
	FLOAT_NONE_VALUE("none"),
	
	OPACITY("opacity"),
	
//	TRANSFORM("transform")
	
	COLOR_TRANSPARENT_VALUE("transparent"),
	
	// ==== FONT ===
	FONT_WEIGHT("font-weight"),
	FONT_WEIGHT_BOLD_VALUE("bold"),
	
	FONT_STYLE("font-style"),
	
	FONT_STYLE_NORMAL_VALUE("normal"),
	FONT_STYLE_ITALIC_VALUE("italic"),
	FONT_STYLE_OBLIQUE_VALUE("oblique"),
	
	TEXT_DECORATION("text-decoration"),
	
	TEXT_DECORATION_NONE_VALUE("none"),
	TEXT_DECORATION_UNDERLINE_VALUE("underline"),
	TEXT_DECORATION_OVERLINE_VALUE("overline"),
	TEXT_DECORATION_LINE_THROUGH_VALUE("line-through"),
	TEXT_DECORATION_BLINK_VALUE("blink"),
	TEXT_DECORATION_INHERIT_VALUE("inherit"),
	
	;
	
	private final String str;
	public final String string() {
		return str;
	}

	ECSSPropertyConstants(final String str) {
		this.str = str;
	}

	public boolean equalTo(String o) {
		return str.equals(o);
	}

	public boolean equalToIgnCase(String o) {
		return str.equalsIgnoreCase(o);
	}

}


//atic final String BORDER_TOP_WIDTH = "border-top-width";
//public static final String BORDER_RIGHT_WIDTH = "border-right-width";
//public static final String BORDER_BOTTOM_WIDTH = "border-bottom-width";
//public static final String BORDER_LEFT_WIDTH = "border-left-width";
//
//public static final String DISPLAY = "display";
//public static final String DISPLAY_NONE_VALUE = "none";
//public static final String DISPLAY_BLOCK_VALUE = "block";
//public static final String DISPLAY_LIST_ITEM_VALUE = "list-item";
//public static final String DISPLAY_TABLE_VALUE = "table";
//public static final String DISPLAY_INLINE_VALUE = "inline";
//public static final String DISPLAY_INLINE_TABLE_VALUE = "inline-table";
//public static final String DISPLAY_INLINE_BLOCK_VALUE = "inline-block";
//// table elements
//public static final String DISPLAY_TABLE_ROW_VALUE = "table-row";
//public static final String DISPLAY_TABLE_ROW_GROUP_VALUE = "table-row-group";
//public static final String DISPLAY_TABLE_HEADER_GROUP_VALUE = "table-header-group";
//public static final String DISPLAY_TABLE_FOOTER_GROUP_VALUE = "table-footer-group";
//public static final String DISPLAY_TABLE_COLUMN_VALUE = "table-column";
//public static final String DISPLAY_TABLE_COLUMN_GROUP_VALUE = "table-column-group";
//public static final String DISPLAY_TABLE_CELL_VALUE = "table-cell";
//public static final String DISPLAY_TABLE_CAPTION_VALUE = "table-caption";
//
//public static final String ZINDEX = "z-index";
//public static final String ZINDEX_AUTO_VALUE = "auto";
//
//public static final String VISIBILITY = "visibility";
//public static final String VISIBILITY_VISIBLE_VALUE = "visible";
//public static final String VISIBILITY_HIDDEN_VALUE = "hidden";
//
//public static final String POSITION = "position";
//public static final String POSITION_STATIC_VALUE = "static";
//public static final String POSITION_RELATIVE_VALUE = "relative";
//public static final String POSITION_ABSOLUTE_VALUE = "absolute";
//public static final String POSITION_FIXED_VALUE = "fixed";
//
//public static final String FLOAT = "float";
//public static final String FLOAT_NONE_VALUE = "none";
//
//public static final String OPACITY = "opacity";
