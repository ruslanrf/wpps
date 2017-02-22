/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.list.SetUniqueList;

import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHTMLElementWithVisibleText;
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
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlWebFormElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMCheckBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMCheckBoxGroup;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMList;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMListItem;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMRadioButton;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMRadioButtonGroup;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTable;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTableCell;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTableColumn;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMTableRow;
import tuwien.dbai.wpps.ontschema.InterfaceModelOnt;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * TODO: implement H1, H2...(?), P
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 14, 2012 8:41:19 PM
 */
public enum EIMInstType implements IRdfInstType {
	// --- structural elements (Main Elements): ---
	IM_ELEMENT(IIMElement.class, InterfaceModelOnt.WPBIElement, true, false),
		
		HTML_ELEMENT(IHtmlElement.class, InterfaceModelOnt.HtmlElement, true, true),
//			HTML_ELEMENT_WITH_VISIBLE_TEXT(IHTMLElementWithVisibleText.class
//					, InterfaceModelOnt.HtmlElement, true, false),
		
			HTML_IMAGE(IHtmlImage.class, InterfaceModelOnt.HtmlImage, true, true),
			HTML_LINK(IHtmlLink.class, InterfaceModelOnt.HtmlLink, true, true),
			HTML_LIST(IHtmlList.class, InterfaceModelOnt.HtmlList, true, true),
			// TODO: ordered/unordered list!!
				HTML_LIST_ITEM(IHtmlListItem.class, InterfaceModelOnt.HtmlListItem, true, true),
	
			HTML_TABLE(IHtmlTable.class, InterfaceModelOnt.HtmlTable, true, true),
				HTML_TABLE_ROW(IHtmlTableRow.class, InterfaceModelOnt.HtmlTableRow, true, true),
					HTML_TABLE_CELL(IHtmlTableCell.class, InterfaceModelOnt.HtmlTableCell, true, true),
					
			HTML_TEXT(IHtmlText.class, InterfaceModelOnt.HtmlText, true, true),
			
			HTML_WEB_FORM(IHtmlWebForm.class, InterfaceModelOnt.HtmlWebForm, true, true),
			HTML_WEB_FORM_ELEMENT(IHtmlWebFormElement.class, InterfaceModelOnt.HtmlWebFormElement, true, false),
				HTML_BUTTON(IHtmlButton.class, InterfaceModelOnt.HtmlButton, true, true),
				HTML_FILE_UPLOAD(IHtmlFileUpload.class, InterfaceModelOnt.HtmlFileUpload, true, true),
				HTML_PASSWORD_INPUT(IHtmlPasswordInput.class, InterfaceModelOnt.HtmlPasswordInput, true, true),
				HTML_TEXT_AREA(IHtmlTextArea.class, InterfaceModelOnt.HtmlTextArea, true, true),
				HTML_TEXT_INPUT(IHtmlTextInput.class, InterfaceModelOnt.HtmlTextInput, true, true),
				HTML_SELECT(IHtmlSelect.class, InterfaceModelOnt.HtmlSelect, true, true),
				HTML_OPTION(IHtmlOption.class, InterfaceModelOnt.HtmlOption, true, true),
				HTML_CHECKBOX(IHtmlCheckBox.class, InterfaceModelOnt.HtmlCheckBox, true, true),
				HTML_RADIOBUTTON(IHtmlRadioButton.class, InterfaceModelOnt.HtmlRadioButton, true, true),
				
		IM_CHECKBOX_GROUP(IIMCheckBoxGroup.class, InterfaceModelOnt.WPBICheckBoxGroup, true, true),
			IM_CHECKBOX(IIMCheckBox.class, InterfaceModelOnt.WPBICheckBox, true, false),
			
		IM_RADIOBUTTON_GROUP(IIMRadioButtonGroup.class, InterfaceModelOnt.WPBIRadioButtonGroup, true, true),
			IM_RADIOBUTTON(IIMRadioButton.class, InterfaceModelOnt.WPBIRadioButton, true, false),
			
		IM_LIST(IIMList.class, InterfaceModelOnt.WPBIList, true, true),
			IM_LIST_ITEM(IIMListItem.class, InterfaceModelOnt.WPBIListItem, true, true),
			
		IM_TABLE(IIMTable.class, InterfaceModelOnt.WPBITable, true, true),
			IM_TABLE_ROW(IIMTableRow.class, InterfaceModelOnt.WPBITableRow, true, true),
			IM_TABLE_COLUMN(IIMTableColumn.class, InterfaceModelOnt.WPBITableColumn, true, true),
				IM_TABLE_CELL(IIMTableCell.class, InterfaceModelOnt.WPBITableCell, true, true),
				
	;

	private final Class<? extends IInstanceAdp> javaInterface;
	private final Resource rdfResource;
	private final boolean canBeInstantiated; // originally hasImplementation
	/**
	 * true if this type is in one of structural models.
	 */
	private final boolean isMainType; 
	
	@SuppressWarnings("unchecked")
	private List<EIMInstType> children = SetUniqueList.decorate(
			new LinkedList<EIMInstType>());
	/**
	 * @return the javaInterface
	 */
	@Override
	public Class<? extends IInstanceAdp> getJavaInterface() {
		return javaInterface;
	}
	@Override
	public Resource getRdfResource() {
		return rdfResource;
	}
	
	/**
	 * @return the canBeInstantiated
	 */
	@Override
	public boolean isCanBeInstantiated() {
		return canBeInstantiated;
	}
	
	@Override
	public boolean isMainType() {
		return isMainType;
	}
	
	private final EWPOntSubModel wpOntSubModel;
	
	@Override
	public EWPOntSubModel getWPSubModelType() {
		return wpOntSubModel;
	}
	
	private EIMInstType(final Class<? extends IInstanceAdp> javaInterface, final Resource rdfResource
			, final boolean isMainType, final boolean canBeInstantiated) {
		this.javaInterface = javaInterface;
		this.rdfResource = rdfResource;
		this.isMainType = isMainType;
		this.canBeInstantiated = canBeInstantiated;
		this.wpOntSubModel = EWPOntSubModel.INTERFACE_MODEL;
	}
	
	private final void _addParent(EIMInstType el) {
		el.children.add(this);
	}
	
	// It is executed after the constructor.
	// We set hierarchy of elements.
	static {
		HTML_ELEMENT._addParent(IM_ELEMENT);
			HTML_IMAGE._addParent(HTML_ELEMENT);
			HTML_LINK._addParent(HTML_ELEMENT);
			HTML_LIST._addParent(HTML_ELEMENT);
				HTML_LIST_ITEM._addParent(HTML_ELEMENT);
			
			HTML_TABLE._addParent(HTML_ELEMENT);
				HTML_TABLE_ROW._addParent(HTML_ELEMENT);
					HTML_TABLE_CELL._addParent(HTML_ELEMENT);
				
			HTML_TEXT._addParent(HTML_ELEMENT);
		
			HTML_WEB_FORM._addParent(HTML_ELEMENT);
			HTML_WEB_FORM_ELEMENT._addParent(HTML_ELEMENT);
				HTML_BUTTON._addParent(HTML_WEB_FORM_ELEMENT);
				HTML_FILE_UPLOAD._addParent(HTML_WEB_FORM_ELEMENT);
				HTML_PASSWORD_INPUT._addParent(HTML_WEB_FORM_ELEMENT);
				HTML_TEXT_AREA._addParent(HTML_WEB_FORM_ELEMENT);
				HTML_TEXT_INPUT._addParent(HTML_WEB_FORM_ELEMENT);
				HTML_SELECT._addParent(HTML_WEB_FORM_ELEMENT);
				HTML_OPTION._addParent(HTML_WEB_FORM_ELEMENT);
				HTML_CHECKBOX._addParent(HTML_WEB_FORM_ELEMENT);
				HTML_CHECKBOX._addParent(IM_CHECKBOX);
				HTML_RADIOBUTTON._addParent(HTML_WEB_FORM_ELEMENT);
				HTML_RADIOBUTTON._addParent(IM_RADIOBUTTON);
			
		IM_CHECKBOX_GROUP._addParent(IM_ELEMENT);
		IM_CHECKBOX._addParent(IM_ELEMENT);
		
		IM_RADIOBUTTON_GROUP._addParent(IM_ELEMENT);
		IM_RADIOBUTTON._addParent(IM_ELEMENT);
		
		IM_LIST._addParent(IM_ELEMENT);
		IM_LIST_ITEM._addParent(IM_ELEMENT);
			
		IM_TABLE._addParent(IM_ELEMENT);
			IM_TABLE_ROW._addParent(IM_ELEMENT);
			IM_TABLE_COLUMN._addParent(IM_ELEMENT);
				IM_TABLE_CELL._addParent(IM_ELEMENT);
			
	}

	@Override
	public boolean hasChildren() {
		return children.size()>0;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<IRdfInstType> getChildren() {
		return (List)children;
	}
	
	public static final <T extends IInstanceAdp> EIMInstType getInstTypeByJavaClass(Class<T> clazz) {
		final EIMInstType[] types = EIMInstType.values();
		for (int i=0; i<types.length; i++) {
			if (clazz.equals(types[i].getJavaInterface()))
				return types[i];
		}
		return null;
	}

	public static final IRdfInstType getMainRoot() {
		return IM_ELEMENT;
	}
}
