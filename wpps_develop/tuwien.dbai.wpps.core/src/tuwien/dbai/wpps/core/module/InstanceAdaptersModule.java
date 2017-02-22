/**
 * 
 */
package tuwien.dbai.wpps.core.module;

import tuwien.dbai.wpps.core.annotation.AnnotQltBMBorderNu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMCenterMu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMLeftBorderMu;
import tuwien.dbai.wpps.core.annotation.AnnotQltBMRightBorderMu;
import tuwien.dbai.wpps.core.fuzzy.IMuZeroDouble;
import tuwien.dbai.wpps.core.fuzzy.Nu;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.IInstanceAdpFactory;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfframe.RdfInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImplProvider;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.factory.ILMRdfInstAdpFactory;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequenceItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITree;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ITreeNode;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfframe.RdfSequence;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfframe.RdfSequenceItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfframe.RdfTree;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfframe.RdfTreeNode;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.SequenceImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.SequenceImplProvider;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.SequenceItemImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.SequenceItemImplProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.QltBMBorderNuProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.QltBMCenterMuProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.QltBMLeftBorderMuProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.QltBMRightBorderMuProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory.IBGMRdfInstAdpFactory;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IInnerBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOuterBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfInnerBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfOuterBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImplProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImplProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.OutlineImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.OutlineImplProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QltBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QltBlockImplProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImplProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.ViewPortBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.ViewPortBlockImplProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebDocumentBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebDocumentBlockImplProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImplProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.factory.IDOMRdfInstAdpFactory;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMDocument;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMText;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfframe.RdfDOMDocument;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfframe.RdfDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfframe.RdfDOMText;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfimpl.DOMNodeImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.rdfimpl.DOMNodeImplProvider;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.factory.IIMRdfInstAdpFactory;
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
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlButton;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlCheckBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlFileUpload;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlImage;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlLink;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlList;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlListItem;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlOption;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlPasswordInput;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlRadioButton;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlSelect;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlTable;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlTableCell;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlTableRow;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlText;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlTextArea;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlTextInput;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfHtmlWebForm;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfIMCheckBoxGroup;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfIMList;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfIMListItem;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfIMRadioButtonGroup;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfIMTable;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfIMTableCell;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfIMTableColumn;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfframe.RdfIMTableRow;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.factory.IVMRdfInstAdpFactory;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.interf.IPlainVisualObject;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfframe.RdfPlainVisualObject;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpl.PlainVisualObjectImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.rdfimpl.PlainVisualObjectImplProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 24, 2011 2:04:55 AM
 */
public class InstanceAdaptersModule extends AbstractModule {

	@Override
	protected void configure() {
		
		// --- Set providers for implementations ---
		
		bind(TypeCastImpl.class).toProvider(TypeCastImplProvider.class).in(Singleton.class);
		
		// BBGM
		bind(BoundingBlockImpl.class).toProvider(BoundingBlockImplProvider.class).in(Singleton.class);
		bind(BoxImpl.class).toProvider(BoxImplProvider.class).in(Singleton.class);
		bind(ViewPortBlockImpl.class).toProvider(ViewPortBlockImplProvider.class).in(Singleton.class);
		bind(WebDocumentBlockImpl.class).toProvider(WebDocumentBlockImplProvider.class).in(Singleton.class);
		bind(WebPageBlockImpl.class).toProvider(WebPageBlockImplProvider.class).in(Singleton.class);
		bind(OutlineImpl.class).toProvider(OutlineImplProvider.class).in(Singleton.class);
		bind(QntBlockImpl.class).toProvider(QntBlockImplProvider.class).in(Singleton.class);
		bind(QltBlockImpl.class).toProvider(QltBlockImplProvider.class).in(Singleton.class);
		
		// QltBM
		bind(IMuZeroDouble.class).annotatedWith(AnnotQltBMLeftBorderMu.class)
				.toProvider(QltBMLeftBorderMuProvider.class).in(Singleton.class);
		bind(IMuZeroDouble.class).annotatedWith(AnnotQltBMRightBorderMu.class)
				.toProvider(QltBMRightBorderMuProvider.class).in(Singleton.class);
		bind(IMuZeroDouble.class).annotatedWith(AnnotQltBMCenterMu.class)
				.toProvider(QltBMCenterMuProvider.class).in(Singleton.class);
		bind(Nu.class).annotatedWith(AnnotQltBMBorderNu.class)
				.toProvider(QltBMBorderNuProvider.class).in(Singleton.class);
		
		// VM
		bind(PlainVisualObjectImpl.class).toProvider(PlainVisualObjectImplProvider.class).in(Singleton.class);
		
		// DOM
		bind(DOMNodeImpl.class).toProvider(DOMNodeImplProvider.class).in(Singleton.class);
		
		
		// Logical Model
		bind(SequenceImpl.class).toProvider(SequenceImplProvider.class).in(Singleton.class);
		bind(SequenceItemImpl.class).toProvider(SequenceItemImplProvider.class).in(Singleton.class);
		
		// ---  ---
		
		// --- Set up factory for adapters ---
		
		// Top instance adapter type
		install(new FactoryModuleBuilder()
	     .implement(IInstanceAdp.class, RdfInstanceAdp.class)
	     .build(IInstanceAdpFactory.class));
		
		// BBGM
		install(new FactoryModuleBuilder()
	     .implement(IWebDocumentBlock.class, RdfWebDocumentBlock.class)
	     .implement(IWebPageBlock.class, RdfWebPageBlock.class)
	     .implement(IViewPortBlock.class, RdfViewPortBlock.class)
	     .implement(IBoundingBlock.class, RdfBoundingBlock.class)
	     .implement(IBox.class, RdfBox.class)
	     .implement(IInnerBlock.class, RdfInnerBlock.class)
	     .implement(IOuterBlock.class, RdfOuterBlock.class)
//	     .implement(IOutlineBlock.class, RdfOutlineBlock.class)
	     .implement(IOutline.class, RdfOutline.class)
	     .implement(IQntBlock.class, RdfQntBlock.class)
	     .implement(IQltBlock.class, RdfQltBlock.class)
	     .build(IBGMRdfInstAdpFactory.class));
		
		// IM
		install(new FactoryModuleBuilder()
	     .implement(IHtmlElement.class, RdfHtmlElement.class)
	     .implement(IHtmlLink.class, RdfHtmlLink.class)
	     .implement(IHtmlText.class, RdfHtmlText.class)
	     .implement(IHtmlImage.class, RdfHtmlImage.class)
	     .implement(IHtmlWebForm.class, RdfHtmlWebForm.class)
	     .implement(IHtmlTextInput.class, RdfHtmlTextInput.class)
	     .implement(IHtmlTextArea.class, RdfHtmlTextArea.class)
	     .implement(IHtmlFileUpload.class, RdfHtmlFileUpload.class)
	     .implement(IHtmlPasswordInput.class, RdfHtmlPasswordInput.class)
	     .implement(IHtmlButton.class, RdfHtmlButton.class)
	     .implement(IIMCheckBoxGroup.class, RdfIMCheckBoxGroup.class)
	     .implement(IHtmlCheckBox.class, RdfHtmlCheckBox.class)
	     .implement(IIMRadioButtonGroup.class, RdfIMRadioButtonGroup.class)
	     .implement(IHtmlRadioButton.class, RdfHtmlRadioButton.class)
	     .implement(IHtmlSelect.class, RdfHtmlSelect.class)
	     .implement(IHtmlOption.class, RdfHtmlOption.class)
	     .implement(IHtmlList.class, RdfHtmlList.class)
	     .implement(IHtmlListItem.class, RdfHtmlListItem.class)
	     .implement(IIMList.class, RdfIMList.class)
	     .implement(IIMListItem.class, RdfIMListItem.class)
	     .implement(IHtmlTable.class, RdfHtmlTable.class)
	     .implement(IHtmlTableRow.class, RdfHtmlTableRow.class)
	     .implement(IHtmlTableCell.class, RdfHtmlTableCell.class)
	     .implement(IIMTable.class, RdfIMTable.class)
	     .implement(IIMTableRow.class, RdfIMTableRow.class)
	     .implement(IIMTableColumn.class, RdfIMTableColumn.class)
	     .implement(IIMTableCell.class, RdfIMTableCell.class)
	     .build(IIMRdfInstAdpFactory.class));
		
		//VM
		install(new FactoryModuleBuilder()
	     .implement(IPlainVisualObject.class, RdfPlainVisualObject.class)
	     .build(IVMRdfInstAdpFactory.class));
		
		//DOM
		install(new FactoryModuleBuilder()
			.implement(IDOMDocument.class, RdfDOMDocument.class)
			.implement(IDOMElement.class, RdfDOMElement.class)
			.implement(IDOMText.class, RdfDOMText.class)
			.build(IDOMRdfInstAdpFactory.class));
				
		
		//LM
		install(new FactoryModuleBuilder()
	     .implement(ISequenceItem.class, RdfSequenceItem.class)
	     .implement(ISequence.class, RdfSequence.class)
	     .implement(ITree.class, RdfTree.class)
	     .implement(ITreeNode.class, RdfTreeNode.class)
	     .build(ILMRdfInstAdpFactory.class));
		
		// ---  ---
	
		// --- Set up factories for objects ---
		
	}

}

