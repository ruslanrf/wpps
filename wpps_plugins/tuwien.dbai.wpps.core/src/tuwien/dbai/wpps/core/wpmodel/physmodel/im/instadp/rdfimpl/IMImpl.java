/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.collections.Transformer;
import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.annotation.AnnotInterfaceModel;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.ICheckImplInitialization;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLib;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EFontStyle;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EHtmlButtonType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EListType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.ETextDecoration;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EWebFormMethod;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTableCell;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlTableRow;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpllib.IMLib;
import tuwien.dbai.wpps.ontquerying.SPARQLCommonQueryExecutor;
import tuwien.dbai.wpps.ontschema.InterfaceModelOnt;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * TODO: make "get-request" "soft".
 * 
 * This implementation does not have a provider.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 14, 2012 9:08:34 PM
 */
@Singleton
public final class IMImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(IMImpl.class);
	
	@SuppressWarnings("unused")
	private final WPPSConfig config;

	private final OntModelAdp rdfModelIM;
	
	@Inject
	public IMImpl(final WPPSConfig config
			, @AnnotInterfaceModel final OntModelAdp rdfModelIM) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
		this.config = config;
		this.rdfModelIM = rdfModelIM;
	}
	
	
	// =====================
	// ===== IHtmlText =====
	// =====================
	
	/**
	 * @param imTextRes
	 * @return
	 */
	public String getTextValue(final Resource imTextRes) {
		return InstAdpLib.getValueAsStringSoft(imTextRes, InterfaceModelOnt.hasTextValue, rdfModelIM.getTopRdfModel());
	}
	/**
	 * @param imTextRes
	 * @param content
	 */
	public void addTextValue(final Resource imTextRes, final String content) {
		rdfModelIM.getBottomRdfModel().add(imTextRes, InterfaceModelOnt.hasTextValue, content);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getFontFamilyList(final Resource imTextRes) {
//if (log.isTraceEnabled()) log.trace(InstAdpLib.fillCollectionOfRDFNodesFromRdfSeqSoft(imTextRes
//						, InterfaceModelOnt.hasFontFamilies, rdfModelIM, new LinkedList<RDFNode>()));
		return ListUtils.transformedList(
				InstAdpLib.fillCollectionOfRDFNodesFromRdfSeqSoft(imTextRes
						, InterfaceModelOnt.hasFontFamilies, rdfModelIM.getTopRdfModel(), new LinkedList<RDFNode>())
				, new Transformer() {
					@Override public Object transform(Object arg0) {
						return ((RDFNode)arg0).asLiteral().getString();
					} });
	}
	
//	public void addFontFamily(final Resource imTextRes, final String val) {
//if (log.isTraceEnabled()) log.trace(val);
//		InstAdpLib.appendObjectToRdfSeqOrCreate(imTextRes, InterfaceModelOnt.hasFontFamilies
//				, val, rdfModelIM);
//	}
	
	public void addFontFamily(final Resource imTextRes, final String... val) {
		InstAdpLib.appendObjectsToRdfSeqOrCreate(imTextRes, InterfaceModelOnt.hasFontFamilies
				, Lists.newArrayList(val), rdfModelIM.getBottomRdfModel());
	}
	
	public Float getFontWeight(final Resource imTextRes) {
		return InstAdpLib.jenaLiteralTypeCastSoft(InstAdpLib
				.getValueAsRDFNodeSoft(imTextRes, InterfaceModelOnt.hasFontWeight, rdfModelIM.getTopRdfModel())
				, Float.class);
	}
	
	public void addFontWeight(final Resource imTextRes, final float val) {
		rdfModelIM.getBottomRdfModel().addLiteral(imTextRes, InterfaceModelOnt.hasFontWeight, val);
	}
	
	public Float getFontSize(final Resource imTextRes) {
		return InstAdpLib.jenaLiteralTypeCastSoft(InstAdpLib
				.getValueAsRDFNodeSoft(imTextRes, InterfaceModelOnt.hasFontSize, rdfModelIM.getTopRdfModel())
				, Float.class);
//		return (float)InstAdpLib.getValueAsDouble(imTextRes, InterfaceModelOnt.hasFontSize, rdfModelIM);
	}
	
	public void addFontSize(final Resource imTextRes, final float val) {
		rdfModelIM.getBottomRdfModel().addLiteral(imTextRes, InterfaceModelOnt.hasFontSize, val);
	}
	
	public EFontStyle getFontStyle(final Resource imTextRes) {
		return IMLib.getFontStyle(imTextRes, rdfModelIM.getTopRdfModel());
	}
	
	public void addFontStyle(final Resource imTextRes, final EFontStyle fs) {
		IMLib.addFontStyle(imTextRes, fs, rdfModelIM.getBottomRdfModel());
	}
	
	public ETextDecoration getTextDecoration(final Resource imTextRes) {
		return IMLib.getTextDecoration(imTextRes, rdfModelIM.getTopRdfModel());
	}
	
	public void addTextDecoration(final Resource imTextRes, final ETextDecoration td) {
		IMLib.addTextDecoration(imTextRes, td, rdfModelIM.getBottomRdfModel());
	}
	
	// =====            =====

	
	// ======================
	// ===== IHtmlImage =====
	// ======================
	
	/**
	 * Works for Image and Links.
	 * @return url or null.
	 */
	public String getUrl(final Resource instRdf) {
		return InstAdpLib.getValueAsStringSoft(instRdf, InterfaceModelOnt.hasUri, rdfModelIM.getTopRdfModel());
	}
	
	/**
	 * Works for Image and Links.
	 * @param instRdf
	 * @param str
	 */
	public void addUrl(final Resource instRdf, final String str) {
		rdfModelIM.getBottomRdfModel().add(instRdf, InterfaceModelOnt.hasUri, str);
	}
	
	public String getAltText(final Resource instRdf) {
		return InstAdpLib.getValueAsString(instRdf, InterfaceModelOnt.hasAltText, rdfModelIM.getTopRdfModel());
	}
	
	public void addAltText(final Resource instRdf, final String str) {
		rdfModelIM.getBottomRdfModel().add(instRdf, InterfaceModelOnt.hasAltText, str);
	}
	
	// =====            =====
	
	// ==========================
	// ====== IHtmlLink =========
	// ==========================
	
	//TODO add implementation with container.
	public boolean hasHtmlLink(final Resource instRdf) {
		return rdfModelIM.getTopRdfModel().contains(instRdf, InterfaceModelOnt.hasHtmlLink, (RDFNode)null);
	}
	
	public Resource getHtmlLink(final Resource instRdf) {
		final RDFNode rez = InstAdpLib.getValueAsRDFNodeSoft(instRdf
				, InterfaceModelOnt.hasHtmlLink, rdfModelIM.getTopRdfModel());
		return InstAdpLib.jenaTypeCastSoft(rez, Resource.class);
	}
	
	public void addHtmlLink(final Resource prim, final Resource refLink) {
		rdfModelIM.getBottomRdfModel().add(prim, InterfaceModelOnt.hasHtmlLink, refLink);
	}
	
	// TODO should it be a collection instead?
	public List<Resource> getHtmlElementsForHtmlLink(final Resource htmlLink) {
		return InstAdpLib.getListOfSubjectsAsResources(htmlLink, InterfaceModelOnt.hasHtmlLink, rdfModelIM.getTopRdfModel());
	}

	// =====            =====
	
	// ==========================
	// ===== IHtmlTableCell =====
	// ==========================
	
	/**
	 * Belongs to {@linkplain IHtmlTableCell}.
	 * @param htmlTableCell
	 * @return never null
	 */
	public final List<Resource> getContainedHtmlElementsForRdfSeq(final Resource htmlTableCell) {
		List<Resource> rez = InstAdpLib.getResourcesFromSeqAsArrayListSoft(
				htmlTableCell, InterfaceModelOnt.hasHtmlElements, rdfModelIM.getTopRdfModel());
		return rez;
	}
	
	public final void addContainedHtmlElementsToRdfSeq(final Resource htmlTableCell, final Collection<Resource> htmlContent) {
		InstAdpLib.appendResourcesToRdfSeqOrCreate(
				htmlTableCell, InterfaceModelOnt.hasHtmlElements, htmlContent, rdfModelIM.getBottomRdfModel());
	}
	
	public final void addContainedHtmlElementToRdfSeq(final Resource htmlTableCell, final Resource htmlContent) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(
				htmlTableCell, InterfaceModelOnt.hasHtmlElements, htmlContent, rdfModelIM.getBottomRdfModel());
	}
	
	// =====            =====
	
	
	// =========================
	// ===== IHtmlTableRow, IIMTableRow, IIMTableColumn =====
	// =========================
	
	/**
	 * Belongs to {@linkplain IHtmlTableRow}.
	 * @param htmlTableCell
	 * @return never null
	 */
	public final List<Resource> getTableCells(final Resource tableRow) {
		List<Resource> rez = InstAdpLib.getResourcesFromSeqAsArrayListSoft(
				tableRow, InterfaceModelOnt.hasTableCells, rdfModelIM.getTopRdfModel());
		return rez;
	}
	
	public final void addTableCells(final Resource tableRow, final Collection<Resource> tableCells) {
		InstAdpLib.appendResourcesToRdfSeqOrCreate(
				tableRow, InterfaceModelOnt.hasTableCells, tableCells, rdfModelIM.getBottomRdfModel());
	}
	
	public final void addTableCell(final Resource tableRow, final Resource tableCell) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(tableRow, InterfaceModelOnt.hasTableCells
				, tableCell, rdfModelIM.getBottomRdfModel());
	}
	
	// =====            =====
	
	// =========================
	// ===== IHtmlTable, IIMTable =====
	// =========================
	
	public List<Resource> getTableRows(final Resource table) {
		List<Resource> rez = InstAdpLib.getResourcesFromSeqAsArrayListSoft(
				table, InterfaceModelOnt.hasTableRows, rdfModelIM.getTopRdfModel());
		return rez;
	}
	
	public final void addTableRows(final Resource table, final Collection<Resource> tableRows) {
		InstAdpLib.appendResourcesToRdfSeqOrCreate(
				table, InterfaceModelOnt.hasTableRows, tableRows, rdfModelIM.getBottomRdfModel());
	}
	
	public final void addTableRow(final Resource table, final Resource tableRow) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(table, InterfaceModelOnt.hasTableRows
				, tableRow, rdfModelIM.getBottomRdfModel());
	}
	
	public List<Resource> getTableColumns(final Resource table) {
		List<Resource> rez = InstAdpLib.getResourcesFromSeqAsArrayListSoft(
				table, InterfaceModelOnt.hasTableColumns, rdfModelIM.getTopRdfModel());
		return rez;
	}
	
	public final void addTableColumns(final Resource table, final Collection<Resource> tableColumns) {
		InstAdpLib.appendResourcesToRdfSeqOrCreate(table, InterfaceModelOnt.hasTableColumns, tableColumns, rdfModelIM.getBottomRdfModel());
	}
	
	public final void addTableColumn(final Resource table, final Resource tableColumn) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(table, InterfaceModelOnt.hasTableColumns, tableColumn, rdfModelIM.getBottomRdfModel());
	}
	
	// =====            =====
	
	
	// =========================
	// ===== IHtmlWebForm =====
	// =========================
	
	public final List<Resource> getWebFormElements(Resource webForm) {
		return InstAdpLib.fillCollectionOfResourcesFromRdfSeqSoft(webForm, InterfaceModelOnt.hasWebFormElements
				, rdfModelIM.getTopRdfModel(), new LinkedList<Resource>());
	}
	public final void addWebFormElement(Resource webForm, Resource webFormEl) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(
				webForm, InterfaceModelOnt.hasWebFormElements, webFormEl, rdfModelIM.getBottomRdfModel());
	}
	
	public Resource getWebForm(Resource webFormElement) {
		Collection<Resource> rez = SPARQLCommonQueryExecutor
				.getResourceWhichHasSpecifiedResourceInItsPropertyContainer(
				rdfModelIM.getTopRdfModel(), InterfaceModelOnt.hasWebFormElements.getURI(), webFormElement.getURI());
		int n = rez.size();
		if (n == 0) return null;
		if (n>1) {
log.warn("There are more than 1 form for element "+webFormElement+". Forms: "+rez);
			return null;
		}
	return rez.iterator().next();
	}
	
	public final String getWebFormAction(Resource webForm) {
		return InstAdpLib.getValueAsStringSoft(
				webForm, InterfaceModelOnt.hasAction, rdfModelIM.getTopRdfModel());
	}
	
	public final void addWebFormAction(Resource webForm, String action) {
		rdfModelIM.getBottomRdfModel().add(webForm, InterfaceModelOnt.hasAction, action);
	}
	
	public final EWebFormMethod getWebFormMethod(Resource webForm) {
		return InstAdpLib.getObjectAsEnumSoft(
				EWebFormMethod.class, webForm, InterfaceModelOnt.hasWebFormMethod, rdfModelIM.getTopRdfModel());
	}
	
	public final void addWebFormMethod(Resource webForm, EWebFormMethod method) {
		rdfModelIM.getBottomRdfModel().add(webForm, InterfaceModelOnt.hasWebFormMethod, method.getRdfResource());
	}

	// ==============================
	
	// =========================
	// ===== IHtmlButton =====
	// =========================
	
	public final EHtmlButtonType getHtmlButtonType(Resource button) {
		return InstAdpLib.getObjectAsEnumSoft(EHtmlButtonType.class, button, RDF.type, rdfModelIM.getTopRdfModel());
	}
	
	public final void addHtmlButtonType(Resource button, EHtmlButtonType btnType) {
		rdfModelIM.getBottomRdfModel().add(button, RDF.type, btnType.getRdfResource());
	}
	
	//TODO add implementation with container.
	public boolean hasHtmlButton(final Resource element) {
		return rdfModelIM.getTopRdfModel().contains(element, InterfaceModelOnt.inHtmlButton, (RDFNode)null);
	}
	// TODO implement everywhere containment relation for a button
	public void addHtmlButtonReference(final Resource element, final Resource button) {
		rdfModelIM.getBottomRdfModel().add(element, InterfaceModelOnt.inHtmlButton, button);
	}
	
	public Resource getHtmlButtonReference(final Resource element) {
		final RDFNode rez = InstAdpLib.getValueAsRDFNodeSoft(element
				, InterfaceModelOnt.inHtmlButton, rdfModelIM.getTopRdfModel());
		return InstAdpLib.jenaTypeCastSoft(rez, Resource.class);
	}
	
	public void addHtmlButtonInputType(final Resource button, final boolean isInputButton) {
		rdfModelIM.getBottomRdfModel().addLiteral(button, InterfaceModelOnt.isHtmlInputButton, isInputButton);
	}
	
	public Boolean getHtmlButtonInputType(final Resource button) {
		return InstAdpLib.jenaLiteralTypeCastSoft(
				InstAdpLib.getValueAsRDFNode(button, InterfaceModelOnt.isHtmlInputButton, rdfModelIM.getTopRdfModel())
				, Boolean.class);
	}
	
	// ==============================
	
	// =========================
	// ===== IIMCheckBoxGroup =====
	// =========================
	
	public List<Resource> getContainedIMCheckBoxes(Resource imChBGroup) {
		return InstAdpLib.fillCollectionOfResourcesFromRdfSeqSoft(imChBGroup
				, InterfaceModelOnt.hasCheckBoxes
				, rdfModelIM.getTopRdfModel()
				, new ArrayList<Resource>());
	}
	public void addIMCheckBoxIntoGroup(Resource imChBGroup, Resource imChBG) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(imChBGroup, InterfaceModelOnt.hasCheckBoxes, imChBG, rdfModelIM.getBottomRdfModel());
	}
	
	// ==============================
	
	// ==============================
	// ========= IIMCheckBox, IIMRadioButton =========
	// ==============================
	
	public boolean isSelected(Resource imChB) {
		return InstAdpLib.jenaLiteralTypeCastSoft(
				InstAdpLib.getValueAsRDFNodeSoft(imChB, InterfaceModelOnt.isChecked, rdfModelIM.getTopRdfModel())
				, Boolean.class);
	}
	public void addSelectedState(Resource imChB, boolean isChecked) {
		rdfModelIM.getBottomRdfModel().addLiteral(imChB, InterfaceModelOnt.isChecked, isChecked);
	}
	
	public Resource getCheckBoxGroupForCheckBox(Resource imChB) {
		Collection<Resource> rez = SPARQLCommonQueryExecutor.getResourceWhichHasSpecifiedResourceInItsPropertyContainer(
				rdfModelIM.getTopRdfModel(), InterfaceModelOnt.hasCheckBoxes.getURI(), imChB.getURI());
		int n = rez.size();
		if (n == 0) return null;
//		if (n == 0) {
//			log.warn("There is no group for checkbox "+imChB);
//			return null;
//		}
		if (n>1) {
			log.warn("There are more than 1 group for checkbox "+imChB+". Groups: "+rez);
			return null;
		}
	return rez.iterator().next();
	}
	
	public Resource getRadioButtonGroupForRadioButton(Resource imRB) {
		Collection<Resource> rez = SPARQLCommonQueryExecutor.getResourceWhichHasSpecifiedResourceInItsPropertyContainer(
				rdfModelIM.getTopRdfModel(), InterfaceModelOnt.hasRadioButtons.getURI(), imRB.getURI());
		int n = rez.size();
		if (n == 0) return null;
//		if (n == 0) {
//			log.warn("There is no group for radiobutton "+imRB);
//			return null;
//		}
		if (n>1) {
			log.warn("There are more than 1 group for radiobutton "+imRB+". Groups: "+rez);
			return null;
		}
	return rez.iterator().next();
	}
	
	// ==============================
	
	
	// ==============================
	// ========= IIMList, IHtmlList =
	// ==============================
	
	public List<Resource> getListItems(Resource list) {
		return InstAdpLib.fillCollectionOfResourcesFromRdfSeqSoft(list
				, InterfaceModelOnt.hasListItems
				, rdfModelIM.getTopRdfModel()
				, new ArrayList<Resource>());
	}
	
	public void addListItem(Resource list, Resource item) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(list, InterfaceModelOnt.hasListItems, item, rdfModelIM.getBottomRdfModel());
	}
	
	public final EListType getListType(Resource list) {
		return InstAdpLib.getObjectAsEnumSoft(EListType.class, list, RDF.type, rdfModelIM.getTopRdfModel());
	}
	
	public final void addListType(Resource list, EListType listType) {
		rdfModelIM.getBottomRdfModel().add(list, RDF.type, listType.getRdfResource());
	}
	
	// ==============================
	
	
	// ==============================
	// ========= IHtmlSelect ========
	// ==============================
	
	public List<Resource> getOptions(Resource select) {
		return InstAdpLib.fillCollectionOfResourcesFromRdfSeqSoft(select
				, InterfaceModelOnt.hasOptions
				, rdfModelIM.getTopRdfModel()
				, new ArrayList<Resource>());
	}
	public void addHtmlOption(Resource select, Resource option) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(
				select, InterfaceModelOnt.hasOptions, option, rdfModelIM.getBottomRdfModel());
	}
	public void addHtmlOption(Resource select, List<Resource> options) {
		InstAdpLib.appendResourcesToRdfSeqOrCreate(
				select, InterfaceModelOnt.hasOptions, options, rdfModelIM.getBottomRdfModel());
	}
	
	public Resource getHtmlSelectForOption(Resource option) {
		Collection<Resource> rez = SPARQLCommonQueryExecutor
				.getResourceWhichHasSpecifiedResourceInItsPropertyContainer(
				rdfModelIM.getTopRdfModel(), InterfaceModelOnt.hasOptions.getURI(), option.getURI());
		int n = rez.size();
		if (n == 0) return null;
		if (n>1) {
			log.warn("There are more than 1 group for checkbox "+option+". Groups: "+rez);
			return null;
		}
	return rez.iterator().next();
	}
	
	// ==============================
	
	// =========================
	// ===== IIMRadioButtonGroup =====
	// =========================
	
	public List<Resource> getContainedIMRadioButton(Resource imRBGroup) {
		return InstAdpLib.fillCollectionOfResourcesFromRdfSeqSoft(imRBGroup
				, InterfaceModelOnt.hasRadioButtons
				, rdfModelIM.getTopRdfModel()
				, new ArrayList<Resource>());
	}
	public void addIMRadioButtonIntoGroup(Resource imRBGroup, Resource imRBG) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(
				imRBGroup, InterfaceModelOnt.hasRadioButtons, imRBG, rdfModelIM.getBottomRdfModel());
	}
		
	// ==============================
	
	// ==============================
	// === IIMListItem  ===
	// ==============================
	
	public final List<Resource> getContainedIMElementsForRdfSeq(final Resource htmlTableCell) {
		List<Resource> rez = InstAdpLib.getResourcesFromSeqAsArrayListSoft(
				htmlTableCell, InterfaceModelOnt.hasWPBIElements, rdfModelIM.getTopRdfModel());
		return rez;
	}
	
	public final void addContainedIMElementsToRdfSeq(final Resource htmlTableCell, final Collection<Resource> htmlContent) {
		InstAdpLib.appendResourcesToRdfSeqOrCreate(
				htmlTableCell, InterfaceModelOnt.hasWPBIElements, htmlContent, rdfModelIM.getBottomRdfModel());
	}
	
	public final void addContainedIMElementToRdfSeq(final Resource htmlTableCell, final Resource htmlContent) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(
				htmlTableCell, InterfaceModelOnt.hasWPBIElements, htmlContent, rdfModelIM.getBottomRdfModel());
	}
	
	// ==============================
	
	// =========================
	// ===== IIMTable =====
	// =========================
	
	// =====            =====
	
	@Override
	public boolean allFunctionsAreImplemented() {
		return true;
	}

}
