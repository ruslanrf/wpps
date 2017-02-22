/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.factory;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnknownType;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfInstType;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.TypeCastManagerLibSupport;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
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

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 14, 2012 10:26:08 PM
 */
@Singleton
public class IMRdfInstAdpFactoryWrap {
	private static final Logger log = Logger.getLogger(IMRdfInstAdpFactoryWrap.class);

	protected final TypeCastImpl typeCastImpl;

	private final IIMRdfInstAdpFactory factory;
	
	
	@Inject
	public IMRdfInstAdpFactoryWrap(final TypeCastImpl typeCastImpl
			,IIMRdfInstAdpFactory factory) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"In constructor. It is singleton");
		this.typeCastImpl = typeCastImpl;
		this.factory = factory;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends IInstanceAdp> T createAdp(Resource inst, Class<T> typ) {
		if (IHtmlElement.class.equals(typ))
			return (T)factory.createHtmlElement(inst);
		if (IHtmlLink.class.equals(typ))
			return (T)factory.createHtmlLink(inst);
		if (IHtmlText.class.equals(typ))
			return (T)factory.createHtmlText(inst);
		if (IHtmlImage.class.equals(typ))
			return (T)factory.createHtmlImage(inst);
		if (IHtmlWebForm.class.equals(typ))
			return (T)factory.createHtmlWebForm(inst);
		if (IHtmlTextInput.class.equals(typ))
			return (T)factory.createHtmlTextInput(inst);
		if (IHtmlTextArea.class.equals(typ))
			return (T)factory.createHtmlTextArea(inst);
		if (IHtmlFileUpload.class.equals(typ))
			return (T)factory.createHtmlFileUpload(inst);
		if (IHtmlPasswordInput.class.equals(typ))
			return (T)factory.createHtmlPasswordInput(inst);
		if (IHtmlButton.class.equals(typ))
			return (T)factory.createHtmlButton(inst);
		if (IIMCheckBoxGroup.class.equals(typ))
			return (T)factory.createIMCheckBoxGroup(inst);
		if (IHtmlCheckBox.class.equals(typ))
			return (T)factory.createHtmlCheckBox(inst);
		if (IIMRadioButtonGroup.class.equals(typ))
			return (T)factory.createIMRadioButtonGroup(inst);
		if (IHtmlRadioButton.class.equals(typ))
			return (T)factory.createHtmlRadioButton(inst);
		if (IHtmlSelect.class.equals(typ))
			return (T)factory.createHtmlSelect(inst);
		if (IHtmlOption.class.equals(typ))
			return (T)factory.createHtmlOption(inst);
		if (IHtmlList.class.equals(typ))
			return (T)factory.createHtmlList(inst);
		if (IHtmlListItem.class.equals(typ))
			return (T)factory.createHtmlListItem(inst);
		if (IIMList.class.equals(typ))
			return (T)factory.createIMList(inst);
		if (IIMListItem.class.equals(typ))
			return (T)factory.createIMListItem(inst);
		if (IHtmlTable.class.equals(typ))
			return (T)factory.createHtmlTable(inst);
		if (IHtmlTableRow.class.equals(typ))
			return (T)factory.createHtmlTableRow(inst);
		if (IHtmlTableCell.class.equals(typ))
			return (T)factory.createHtmlTableCell(inst);
		if (IIMTable.class.equals(typ))
			return (T)factory.createIMTable(inst);
		if (IIMTableRow.class.equals(typ))
			return (T)factory.createIMTableRow(inst);
		if (IIMTableColumn.class.equals(typ))
			return (T)factory.createIMTableColumn(inst);
		if (IIMTableCell.class.equals(typ))
			return (T)factory.createIMTableCell(inst);
		else { // if this is not a basic type
			// get corresponding type as enum + its model
			final Object[] res = TypeCastManagerLibSupport.getMainInstTypeAndModel(typ);
			// if it must have an implimentation
			if (((IRdfInstType)res[0]).isCanBeInstantiated())
				throw new UnknownType(log, typ.getSimpleName());
			
			// if this type (its structural counterpart) corresponds to the IM
			if (EWPOntSubModel.INTERFACE_MODEL.equals(res[1])) {
				final IRdfInstType instType = typeCastImpl.as(inst, (IRdfInstType)res[0], (EWPOntSubModel)res[1]);
				Preconditions.checkNotNull(instType, "arg. instType is null for type "+res[0]+" of model "+res[1]);
				return (T)createAdp(inst, instType.getJavaInterface());
			}
			else
				throw new GeneralUncheckedException(log, typ.getName()+" is wrong for this factory");
		}
	}
	
}
