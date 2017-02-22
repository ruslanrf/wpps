/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.List;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 28, 2012 2:18:34 PM
 */
public interface IHtmlTable extends IHtmlElement {
	
	List<IHtmlTableRow> getRows();
	
	List<IHtmlTableCell> getCells();
	
}
