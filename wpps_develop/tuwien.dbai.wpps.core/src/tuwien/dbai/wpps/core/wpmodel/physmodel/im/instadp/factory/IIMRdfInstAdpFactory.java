/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.factory;

import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlButton;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlCheckBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlFileUpload;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlImage;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlLink;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlList;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlListItem;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlOption;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlPasswordInput;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlRadioButton;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlSelect;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTable;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTableCell;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTableRow;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTextArea;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTextInput;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlWebForm;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMCheckBoxGroup;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMList;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMListItem;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMRadioButtonGroup;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTable;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTableCell;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTableColumn;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTableRow;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 14, 2012 10:24:24 PM
 */
public interface IIMRdfInstAdpFactory {
	
	IHtmlElement createHtmlElement(Resource inst);

	IHtmlLink createHtmlLink(Resource inst);
	
	IHtmlText createHtmlText(Resource inst);
	
	IHtmlImage createHtmlImage(Resource inst);
	
	IHtmlWebForm createHtmlWebForm(Resource inst);
	
	IHtmlTextInput createHtmlTextInput(Resource inst);
	
	IHtmlTextArea createHtmlTextArea(Resource inst);
	
	IHtmlFileUpload createHtmlFileUpload(Resource inst);
	
	IHtmlPasswordInput createHtmlPasswordInput(Resource inst);
	
	IHtmlButton createHtmlButton(Resource inst);
	
	IIMCheckBoxGroup createIMCheckBoxGroup(Resource inst);
	
	IHtmlCheckBox createHtmlCheckBox(Resource inst);
	
	IIMRadioButtonGroup createIMRadioButtonGroup(Resource inst);
	
	IHtmlRadioButton createHtmlRadioButton(Resource inst);
	
	IHtmlSelect createHtmlSelect(Resource inst);
	
	IHtmlOption createHtmlOption(Resource inst);
	
	IHtmlList createHtmlList(Resource inst);
	
	IHtmlListItem createHtmlListItem(Resource inst);
	
	IIMList createIMList(Resource inst);
	
	IIMListItem createIMListItem(Resource inst);
	
	IHtmlTable createHtmlTable(Resource inst);
	
	IHtmlTableRow createHtmlTableRow(Resource inst);
	
	IHtmlTableCell createHtmlTableCell(Resource inst);
	
	IIMTable createIMTable(Resource inst);
	
	IIMTableRow createIMTableRow(Resource inst);
	
	IIMTableColumn createIMTableColumn(Resource inst);
	
	IIMTableCell createIMTableCell(Resource inst);
	
}
