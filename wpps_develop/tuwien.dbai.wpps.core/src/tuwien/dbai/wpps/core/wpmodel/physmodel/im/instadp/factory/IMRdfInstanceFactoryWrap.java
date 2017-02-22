/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EFontStyle;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EHtmlButtonType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EListType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.ETextDecoration;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EWebFormMethod;
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

import com.google.inject.Inject;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Wrapper-adapter for {@link IMRdfInstanceFactory}. 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 7, 2012 7:46:31 PM
 */
public class IMRdfInstanceFactoryWrap {
	private final static Logger log = Logger.getLogger(IMRdfInstanceFactoryWrap.class);

	private final IMRdfInstanceFactory factory;
	
	@Inject
	public IMRdfInstanceFactoryWrap(final IMRdfInstanceFactory factory) {
		this.factory = factory;
	}
	
	public <T extends IInstanceAdp> Resource createObject(Class<T> typ, Object... params) {
		return createObject(null, typ, params);
	}

	public <T extends IInstanceAdp> Resource createObject(final Resource rdfInst, Class<T> typ, Object... params) {
		if (IHtmlElement.class.equals(typ))
			return factory.createHtmlElement(rdfInst, (Resource)params[0]);
		else if (IHtmlLink.class.equals(typ))
			return factory.createHtmlLinkEmpty(rdfInst, (String)params[0]);
		else if (IHtmlText.class.equals(typ))
			return factory.createHtmlText(rdfInst, (Resource)params[0], (String)params[1], (Float)params[2], (Float)params[3], (String[])params[4]
					, (EFontStyle)params[5], (ETextDecoration)params[6]);
		else if (IHtmlImage.class.equals(typ))
			return factory.createHtmlImage(rdfInst, (Resource)params[0], (String)params[1], (String)params[2]);
		else if (IHtmlWebForm.class.equals(typ))
			return factory.createHtmlWebFormEmpty(rdfInst, (Resource)params[0], (EWebFormMethod)params[1], (String)params[2]);
		else if (IHtmlTextInput.class.equals(typ))
			return factory.createHtmlTextInput(rdfInst, (Resource)params[0], (Resource)params[1]
				, (String)params[2], (Float)params[3], (Float)params[4], (String[])params[5]
				, (EFontStyle)params[6], (ETextDecoration)params[7]);
		else if (IHtmlTextArea.class.equals(typ))
			return factory.createHtmlTextArea(rdfInst, (Resource)params[0], (Resource)params[1]
				, (String)params[2], (Float)params[3], (Float)params[4], (String[])params[5]
				, (EFontStyle)params[6], (ETextDecoration)params[7]);
		else if (IHtmlFileUpload.class.equals(typ))
			return factory.createHtmlFileUpload(rdfInst, (Resource)params[0], (Resource)params[1]
					, (String)params[2], (Float)params[3], (Float)params[4], (String[])params[5]
							, (EFontStyle)params[6], (ETextDecoration)params[7]);
		else if (IHtmlPasswordInput.class.equals(typ))
			return factory.createHtmlPasswordInput(rdfInst, (Resource)params[0], (Resource)params[1]);
		else if (IHtmlButton.class.equals(typ))
			return factory.createHtmlButton(rdfInst, (Resource)params[0], (Resource)params[1]
					, (EHtmlButtonType) params[2], (Boolean) params[3]
					, (String)params[4], (Float)params[5], (Float)params[6], (String[])params[7]
							, (EFontStyle)params[8], (ETextDecoration)params[9]);
		else if (IIMCheckBoxGroup.class.equals(typ))
			return factory.createIMCheckBoxGroupEmpty(rdfInst);
		else if (IHtmlCheckBox.class.equals(typ))
			return factory.createHtmlCheckBox(rdfInst, (Resource)params[0]
					, (Resource)params[1], (Resource)params[2], (Boolean)params[3]);
		else if (IIMRadioButtonGroup.class.equals(typ))
			return factory.createIMRadioButtonGroupEmpty(rdfInst);
		else if (IHtmlRadioButton.class.equals(typ))
			return factory.createHtmlRadioButton(rdfInst, (Resource)params[0]
					, (Resource)params[1], (Resource)params[2], (Boolean)params[3]);
		else if (IHtmlSelect.class.equals(typ))
			return factory.createHtmlSelectEmpty(rdfInst, (Resource)params[0], (Resource)params[1]
					, (Float)params[2], (Float)params[3], (String[])params[4]
							, (EFontStyle)params[5], (ETextDecoration)params[6]);
		else if (IHtmlOption.class.equals(typ))
			return factory.createHtmlOption(rdfInst, (Resource)params[0], (Resource)params[1]
					, (Boolean)params[2]
					, (String)params[3], (Float)params[4], (Float)params[5], (String[])params[6]
							, (EFontStyle)params[7], (ETextDecoration)params[8]);
		else if (IHtmlList.class.equals(typ))
			return factory.createHtmlListEmpty(rdfInst, (Resource)params[0], (EListType)params[1]);
		else if (IHtmlListItem.class.equals(typ))
			return factory.createHtmlListItem(rdfInst, (Resource)params[0], (Resource)params[1]);
		else if (IIMList.class.equals(typ))
			return factory.createIMlListEmpty(rdfInst, (EListType)params[0]);
		else if (IIMListItem.class.equals(typ))
			return factory.createIMListItem(rdfInst, (Resource)params[0]);
		else if (IHtmlTable.class.equals(typ))
			return factory.createHtmlTableEmpty(rdfInst, (Resource)params[0]);
		else if (IHtmlTableRow.class.equals(typ))
			return factory.createHtmlTableRowEmpty(rdfInst, (Resource)params[0], (Resource)params[1]);
		else if (IHtmlTableCell.class.equals(typ))
			return factory.createHtmlTableCell(rdfInst, (Resource)params[0], (Resource)params[1]);
		else if (IIMTable.class.equals(typ))
			return factory.createIMTableEmpty(rdfInst);
		else if (IIMTableRow.class.equals(typ))
			return factory.createIMTableRowEmpty(rdfInst, (Resource)params[0]);
		else if (IIMTableColumn.class.equals(typ))
			return factory.createIMTableColumnEmpty(rdfInst, (Resource)params[0]);
		else if (IIMTableCell.class.equals(typ))
			return factory.createIMTableCell(rdfInst, (Resource)params[0], (Resource)params[1]);
		else { // if this is not a basic type
			throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
}
