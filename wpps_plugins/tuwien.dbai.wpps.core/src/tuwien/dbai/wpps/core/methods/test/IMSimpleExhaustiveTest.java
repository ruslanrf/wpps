/**
 * 
 */
package tuwien.dbai.wpps.core.methods.test;

import java.util.ArrayList;
import java.util.List;

import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
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
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlWebFormElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMCheckBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMCheckBoxGroup;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IIMRadioButtonGroup;
import tuwien.dbai.wpps.ontschema.InterfaceModelOnt;

import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 8, 2012 10:52:29 PM
 */
public class IMSimpleExhaustiveTest extends AWPUWrapper {

	/**
	 * @param description
	 */
	public IMSimpleExhaustiveTest(AWPUMethodDescription description) {
		super(description);
	}
	
	protected void _dumpIntoLM(List<IResults> results) {
		
	}

	@Override
	protected List<IResults> _extractResults() {
		final IIEBasisAPI api = super.getIEAPI().getIEBasisAPI();
		
		api.dumpModel(EWPOntSubModel.INTERFACE_MODEL, "im.rdf");
		api.dumpModel(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL, "sbgm.rdf");
		api.dumpModel(EWPOntSubModel.QNT_BLOCK_MODEL, "qntbgm.rdf");
		api.dumpModel(EWPOntSubModel.QLT_BLOCK_MODEL, "qltbgm.rdf");
		api.dumpModel(EWPOntSubModel.DOM, "dom.rdf");
		
		System.out.println("\n\n=====================\n=== START. OUTPUT ===\n=====================\n");
		
		System.out.println("\n === IMAGE ===\n");
		
		IResults res = api.getObjectsByType(IHtmlImage.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlImage img = adp.as(IHtmlImage.class);
			System.out.println("---img: "+img+"\nurl: "+img.getUrl()
					+"\nalt: "+img.getAltText()+"\nlink: "+img.getHtmlLink());
		}
		
		System.out.println("\n === LINK ===\n");
		
		res = api.getObjectsByType(IHtmlLink.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlLink l = adp.as(IHtmlLink.class);
			System.out.println("---link: "+l+"\nurl: "+l.getUrl()
					+"\ncontent: "+l.getContent()+"\ngetLink==nul: "+(l.getHtmlLink()==null));
		}
		
		System.out.println("\n === LIST ===\n");
		
		res = api.getObjectsByType(IHtmlList.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlList l = adp.as(IHtmlList.class);
			System.out.println("---list: "+l+"\ntype: "+l.getType()
					+"\ncontent: "+l.getItems()+"\nlink: "+l.getHtmlLink());
		}
		
		System.out.println("\n === LIST_ITEMS ===\n");
		
		res = api.getObjectsByType(IHtmlListItem.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlListItem l = adp.as(IHtmlListItem.class);
			System.out.println("---listItems: "+l+"\ntype: "+l.getContent()+"\nlink: "+l.getHtmlLink());
		}
		
		System.out.println("\n === TABLE ===\n");
		
		res = api.getObjectsByType(IHtmlTable.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlTable l = adp.as(IHtmlTable.class);
			
			System.out.println("---table: "+l+"\nrows: "+l.getRows()+"\ncells: "+l.getCells()+"\nlink: "+l.getHtmlLink());
		}
		
		System.out.println("\n === TABLE ROWS ===\n");
		
		res = api.getObjectsByType(IHtmlTableRow.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlTableRow l = adp.as(IHtmlTableRow.class);
			System.out.println("---row: "+l+"\nrows: "+l.getCells()+"\nlink: "+l.getHtmlLink());
		}
		
		System.out.println("\n === TABLE CELLS ===\n");
		
		res = api.getObjectsByType(IHtmlTableCell.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlTableCell l = adp.as(IHtmlTableCell.class);
			System.out.println("---cell: "+l+"\ncontent: "+l.getContent()+"\nlink: "+l.getHtmlLink());
		}
		
		System.out.println("\n === HTML TEXT ===\n");
		
		res = api.getObjectsByType(IHtmlText.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlText l = adp.as(IHtmlText.class);
			System.out.println("---text: "+l
					+"\nfont size: "+l.getFontSize()
					+"\nfont weight: "+l.getFontWeight()
					+"\ntext: "+l.getText()
					+"\nfont family: "+l.getFontFamilyList()
					+"\nfont style: "+l.getFontStyle()
					+"\ntext decoration: "+l.getTextDecoration()
					+"\nlink: "+l.getHtmlLink());
		}
		
		System.out.println("\n === WEB FORM ===\n");
		
		res = api.getObjectsByType(IHtmlWebForm.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlWebForm l = adp.as(IHtmlWebForm.class); 
			System.out.println("---cell: "+l+" action: "+l.getAction()+" method: "
					+l.getMethod()+"\nelements: "+l.getElements()+"\nlink: "+l.getHtmlLink());
		}
		
		System.out.println("\n === HTML_BUTTON ===\n");
		
		res = api.getObjectsByType(IHtmlButton.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlButton l = adp.as(IHtmlButton.class);
			System.out.println("---text: "+l
					+"\ntype: "+l.getType()
					+"\ninput button: "+l.isInputButton()
					+"\nfont size: "+l.getFontSize()
					+"\nfont weight: "+l.getFontWeight()
					+"\ntext: "+l.getText()
					+"\nfont family: "+l.getFontFamilyList()
					+"\nfont style: "+l.getFontStyle()
					+"\ntext decoration: "+l.getTextDecoration()
					+"\nlink: "+l.getHtmlLink()
					+"\nform: "+l.getWebForm());
		}
		
		System.out.println("\n === HTML_FILE_UPLOAD ===\n");
		
		res = api.getObjectsByType(IHtmlFileUpload.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlFileUpload l = adp.as(IHtmlFileUpload.class);
			System.out.println("---text: "+l
					+"\nfont size: "+l.getFontSize()
					+"\nfont weight: "+l.getFontWeight()
					+"\ntext: "+l.getText()
					+"\nfont family: "+l.getFontFamilyList()
					+"\nfont style: "+l.getFontStyle()
					+"\ntext decoration: "+l.getTextDecoration()
					+"\nlink: "+l.getHtmlLink()
					+"\nform: "+l.getWebForm());
		}
		
		System.out.println("\n === HTML_PASSWORD ===\n");
		
		res = api.getObjectsByType(IHtmlPasswordInput.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlPasswordInput l = adp.as(IHtmlPasswordInput.class);
			System.out.println("---text: "+l
					+"\nlink: "+l.getHtmlLink()
					+"\nform: "+l.getWebForm());
		}
		
		System.out.println("\n === HTML_TEXT_AREA ===\n");
		
		res = api.getObjectsByType(IHtmlTextArea.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlTextArea l = adp.as(IHtmlTextArea.class);
			System.out.println("---text: "+l
					+"\nfont size: "+l.getFontSize()
					+"\nfont weight: "+l.getFontWeight()
					+"\ntext: "+l.getText()
					+"\nfont family: "+l.getFontFamilyList()
					+"\nfont style: "+l.getFontStyle()
					+"\ntext decoration: "+l.getTextDecoration()
					+"\nlink: "+l.getHtmlLink()
					+"\nform: "+l.getWebForm());
		}
		
		System.out.println("\n === HTML_TEXT_INPUT ===\n");
		
		res = api.getObjectsByType(IHtmlTextInput.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlTextInput l = adp.as(IHtmlTextInput.class);
			System.out.println("---text: "+l
					+"\nfont size: "+l.getFontSize()
					+"\nfont weight: "+l.getFontWeight()
					+"\ntext: "+l.getText()
					+"\nfont family: "+l.getFontFamilyList()
					+"\nfont style: "+l.getFontStyle()
					+"\ntext decoration: "+l.getTextDecoration()
					+"\nlink: "+l.getHtmlLink()
					+"\nform: "+l.getWebForm());
		}
		
		System.out.println("\n === HTML_TEXT_SELECT ===\n");
		
		res = api.getObjectsByType(IHtmlSelect.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlSelect l = adp.as(IHtmlSelect.class);
			System.out.println("---text: "+l
					+"\nfont size: "+l.getFontSize()
					+"\nfont weight: "+l.getFontWeight()
					+"\ntext: "+l.getText()
					+"\nfont family: "+l.getFontFamilyList()
					+"\nfont style: "+l.getFontStyle()
					+"\ntext decoration: "+l.getTextDecoration()
					+"\nlink: "+l.getHtmlLink()
					+"\nform: "+l.getWebForm()
					+"\noptions: "+l.getOptions()
					+"\nselected: "+l.getSelected());
		}
		
		System.out.println("\n === HTML_TEXT_OPTION ===\n");
		
		res = api.getObjectsByType(IHtmlOption.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlOption l = adp.as(IHtmlOption.class);
			System.out.println("---text: "+l
					+"\nselected: "+l.isSelected()
					+"\nfont size: "+l.getFontSize()
					+"\nfont weight: "+l.getFontWeight()
					+"\ntext: "+l.getText()
					+"\nfont family: "+l.getFontFamilyList()
					+"\nfont style: "+l.getFontStyle()
					+"\ntext decoration: "+l.getTextDecoration()
					+"\nlink: "+l.getHtmlLink()
					+"\nform: "+l.getWebForm()
					+"\nselect: "+l.getSelect());
		}
		
		System.out.println("\n === IM_CHECKBOX_GROUP ===\n");
		
		res = api.getObjectsByType(IIMCheckBoxGroup.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IIMCheckBoxGroup l = adp.as(IIMCheckBoxGroup.class);
			System.out.println("---CHBG: "+l
					+"\ncheckboxes: "+l.getCheckBoxes());
		}
		
		System.out.println("\n === HTML_CHECKBOX ===\n");
		
		res = api.getObjectsByType(IHtmlCheckBox.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlCheckBox l = adp.as(IHtmlCheckBox.class);
			System.out.println("---text: "+l
					+"\nischecked: "+l.isChecked()
					+"\nlink: "+l.getHtmlLink()
					+"\nform: "+l.getWebForm()
					+"\nCHBGroup"+l.getCheckBoxGroup());
		}
		
		System.out.println("\n === IM_RADIOBUTTON_GROUP ===\n");
		
		res = api.getObjectsByType(IIMRadioButtonGroup.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IIMRadioButtonGroup l = adp.as(IIMRadioButtonGroup.class);
			System.out.println("---RBG: "+l
					+"\ncheckboxes: "+l.getRadioButtons());
		}
		
		System.out.println("\n === HTML_RADIOBUTTON ===\n");
		
		res = api.getObjectsByType(IHtmlRadioButton.class);
		for (IInstanceAdp adp : res.getResultContent()) {
			IHtmlRadioButton l = adp.as(IHtmlRadioButton.class);
			System.out.println("---RB: "+l
					+"\nischecked: "+l.isChecked()
					+"\nlink: "+l.getHtmlLink()
					+"\nform: "+l.getWebForm()
					+"\nRBGroup:"+l.getRadioButtonGroup());
		}
		
		System.out.println("\n === IM_ELEMENT ===\n");
		
		res = api.getObjectsByType(IIMElement.class);
		System.out.println("QNT: "+res.getResultContent().size()+"\ncontent: "+res.getResultContent());
		
		System.out.println("\n === HTML_ELEMENT ===\n");
		
		IResults res2 = api.getObjectsByType(IHtmlElement.class);
		System.out.println("QNT: "+res2.getResultContent().size()+"\ncontent: "+res2.getResultContent());
		
		System.out.println("\n === DIF ===\n");
		
		List<IInstanceAdp> x = new ArrayList<IInstanceAdp>(res.getResultContent());
		x.removeAll(res2.getResultContent());
		
		System.out.println("QNT: "+x.size()+"\ncontent: "+x);
		
		System.out.println("\n === HTML_WEB_FORM_ELEMENT ===\n");
		
		res = api.getObjectsByType(IHtmlWebFormElement.class);
		System.out.println("QNT: "+res.getResultContent().size()+"\ncontent: "+res.getResultContent());
		
		System.out.println("\n ===  ===\n");
		
		res = api.getObjectsByType(IHtmlCheckBox.class);
		System.out.println("QNT: "+res.getResultContent().size()+"\ncontent: "+res.getResultContent());
		
		System.out.println("\n ===  ===\n");
		
		res = api.getObjectsByType(IIMCheckBox.class);
		System.out.println("QNT: "+res.getResultContent().size()+"\ncontent: "+res.getResultContent());
		
		System.out.println("\n ===  ===\n");
		
		StmtIterator iter = 
		this.getState().getModels().getOntAdapter(EWPOntSubModel.INTERFACE_MODEL).getTopRdfModel()
			.listStatements(null, RDF.type, InterfaceModelOnt.WPBICheckBox);
		while (iter.hasNext()) {
			System.out.print(iter.next().getSubject()+" | ");
		}
		
		
		System.out.println("\n\n==================\n=== ENT.OUTPUT ===\n==================\n");
		
		return null;
	}

}
