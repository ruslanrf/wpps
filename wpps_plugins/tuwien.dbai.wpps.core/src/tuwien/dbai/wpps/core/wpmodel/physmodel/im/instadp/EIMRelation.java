/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 4, 2012 4:58:24 PM
 */
public enum EIMRelation  {

	HAS_HTML_LINK,
	
	IN_HTML_BUTTON,
	
	// === IHtmlWebForm ===
	
	HAS_HTML_WEB_FORM_ELEMENTS,
	
	// === IIMCheckBoxGroup ===
	HAS_HTML_CHECK_BOX,
	
	// === IIMCheckBoxGroup ===
	HAS_HTML_RADIO_BUTTON,
	
	// === IHtmlSelect ===
	HAS_HTML_OPTION,
	
	// === IHtmlList, IIMList ===
	HAS_HTML_LIST_ITEM,
	
	// === IHtmlTable, IIMTable ===
	HAS_HTML_TABLE_ROW,
	
	// === IIMTable ===
	HAS_IM_TABLE_COLUMN,
	
	// === IHtmlTableRow, IIMTableRow, IIMTableColumn ===
	HAS_HTML_TABLE_CELL
	
	
}
