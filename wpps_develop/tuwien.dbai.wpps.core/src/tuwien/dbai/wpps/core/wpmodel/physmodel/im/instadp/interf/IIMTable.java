/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.List;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 1:48:24 PM
 */
public interface IIMTable extends IIMElement {

	List<IIMTableRow> getRows();
	
	List<IIMTableColumn> getColumns();
	
	List<IIMTableCell> getCells();
	
}
