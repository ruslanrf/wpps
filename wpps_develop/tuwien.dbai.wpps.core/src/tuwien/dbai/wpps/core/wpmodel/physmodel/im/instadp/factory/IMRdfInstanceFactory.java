/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.factory;

import java.util.Arrays;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.annotation.AnnotInterfaceModel;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateAttrOrRelInOnt;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateObject;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EFontStyle;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EHtmlButtonType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EListType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.ETextDecoration;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EWebFormMethod;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpl.IMImpl;
import tuwien.dbai.wpps.ontschema.InterfaceModelOnt;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * TODO: implement button-element relation in the same way we have link-element relation.
 * 
 * Class which is responsible for creation of individuals in IM.
 * The WPPS configuration is checked for the possibility to create individual or its roles.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 24, 2012 7:04:29 PM
 */
@Singleton
public final class IMRdfInstanceFactory {
	private static final Logger log = Logger.getLogger(IMRdfInstanceFactory.class);

	private final WhetherCreateObject whetherCreateInstance;
	private final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt;
	private final OntModelAdp imOntAdp;
	private final IMImpl imImpl;
	
	@Inject
	public IMRdfInstanceFactory(
			final WhetherCreateObject whetherCreateInstance
			, final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt
			, @AnnotInterfaceModel final OntModelAdp imOntAdp
			, final IMImpl imImpl
			) {
		this.whetherCreateInstance = whetherCreateInstance;
		this.whetherCreateAttrOrRelInOnt = whetherCreateAttrOrRelInOnt;
		this.imOntAdp = imOntAdp;
		this.imImpl = imImpl;
	}
	
	public Resource createHtmlElement(Resource rdfInst, final Resource rdfHtmlLink) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_ELEMENT)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlElement
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlElement);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);

if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlElement():: rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink);
		return rdfInst;
		}
		else return null;
	}
	
//	@Deprecated
//	public Resource createHtmlLink(Resource rdfInst, final String url, final Resource... htmlElements) {
//		if (whetherCreateInstance.apply(EIMInstType.HTML_LINK)) {
//			if (rdfInst == null)
//				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlLink
//						, imOntAdp.getRdfModel(), imOntAdp.getNameSpace());
//			else
//				imOntAdp.getRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlLink);
//			imImpl.addUrl(rdfInst, url);
//			
//			for (Resource e : htmlElements) {
//				imImpl.addHtmlLink(e, rdfInst);
//			}
//			
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlLink() rdfInst: "+rdfInst+" url: "+url);
//			return rdfInst;
//		}
//		else return null;
//	}
	
	public Resource createHtmlLinkEmpty(Resource rdfInst, final String url) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_LINK)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlLink
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlLink);
			if (url != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.HAS_IM_URL))
				imImpl.addUrl(rdfInst, url);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlLinkEmpty() rdfInst: "+rdfInst+" url: "+url);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlText(Resource rdfInst, final Resource rdfHtmlLink, final String content, final Float fontWeight, final Float fontSize
			, final String[] fontFamily, final EFontStyle fontStyle, final ETextDecoration textDecoration) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TEXT)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlText
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlText);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			
			if (content != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_VALUE))
				imImpl.addTextValue(rdfInst, content);
			if (fontWeight != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_WEIGHT))
				imImpl.addFontWeight(rdfInst, fontWeight);
			if (fontSize != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_SIZE))
				imImpl.addFontSize(rdfInst, fontSize);
			if (fontStyle != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_STYLE))
				imImpl.addFontStyle(rdfInst, fontStyle);
			if (textDecoration != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_DECORATION))
				imImpl.addTextDecoration(rdfInst, textDecoration);
			if (fontFamily != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_FAMILY))
				imImpl.addFontFamily(rdfInst, fontFamily);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlText() rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" content: "+content
+" fontWeight: "+fontWeight
+" fontSize: "+fontSize
+" fontFamily: "+Arrays.toString(fontFamily)
+" fontStyle: "+fontStyle
+" textDecoration "+textDecoration);
		return rdfInst;
		}
		else return null;
	}

	public Resource createHtmlImage(Resource rdfInst, final Resource rdfHtmlLink, final String url, final String altText) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_IMAGE)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlImage
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlImage);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			
			if (url != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.HAS_IM_URL))
				imImpl.addUrl(rdfInst, url);
			if (altText != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.HAS_IM_ALT_TEXT))
				imImpl.addAltText(rdfInst, altText);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlImage() rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" url: "+url
+" altText: "+altText
);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlWebFormEmpty(Resource rdfInst, final Resource rdfHtmlLink, final EWebFormMethod method, String action) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_WEB_FORM)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlWebForm
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlWebForm);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			
			if (method != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_WEB_FORM_METHOD))
				imImpl.addWebFormMethod(rdfInst, method);
			if (action != null & whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_WEB_FORM_ACTION))
				imImpl.addWebFormAction(rdfInst, action);

if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlWebFormEmpty():: rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" method: "+method
+ " action: "+action);
		return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlTextInput(Resource rdfInst, final Resource rdfHtmlLink, Resource webForm 
			, final String content, final Float fontWeight, final Float fontSize
			, final String[] fontFamily, final EFontStyle fontStyle, final ETextDecoration textDecoration) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TEXT_INPUT)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlTextInput
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlTextInput);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (webForm != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_WEB_FORM_ELEMENTS))
				imImpl.addWebFormElement(webForm, rdfInst);
			
			if (content != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_VALUE))
				imImpl.addTextValue(rdfInst, content);
			if (fontWeight != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_WEIGHT))
				imImpl.addFontWeight(rdfInst, fontWeight);
			if (fontSize != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_SIZE))
				imImpl.addFontSize(rdfInst, fontSize);
			if (fontStyle != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_STYLE))
				imImpl.addFontStyle(rdfInst, fontStyle);
			if (textDecoration != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_DECORATION))
				imImpl.addTextDecoration(rdfInst, textDecoration);
			if (fontFamily != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_FAMILY))
				imImpl.addFontFamily(rdfInst, fontFamily);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlTextInput() rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" webForm: "+webForm
+" content: "+content
+" fontWeight: "+fontWeight
+" fontSize: "+fontSize
+" fontFamily: "+Arrays.toString(fontFamily)
+" fontStyle: "+fontStyle
+" textDecoration "+textDecoration);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlTextArea(Resource rdfInst, final Resource rdfHtmlLink, Resource webForm
			, final String content, final Float fontWeight, final Float fontSize
			, final String[] fontFamily, final EFontStyle fontStyle, final ETextDecoration textDecoration) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TEXT_AREA)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlTextArea
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlTextArea);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (webForm != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_WEB_FORM_ELEMENTS))
				imImpl.addWebFormElement(webForm, rdfInst);
			
			if (content != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_VALUE))
				imImpl.addTextValue(rdfInst, content);
			if (fontWeight != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_WEIGHT))
				imImpl.addFontWeight(rdfInst, fontWeight);
			if (fontSize != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_SIZE))
				imImpl.addFontSize(rdfInst, fontSize);
			if (fontStyle != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_STYLE))
				imImpl.addFontStyle(rdfInst, fontStyle);
			if (textDecoration != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_DECORATION))
				imImpl.addTextDecoration(rdfInst, textDecoration);
			if (fontFamily != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_FAMILY))
				imImpl.addFontFamily(rdfInst, fontFamily);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlTextInput() rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" webForm: "+webForm
+" content: "+content
+" fontWeight: "+fontWeight
+" fontSize: "+fontSize
+" fontFamily: "+Arrays.toString(fontFamily)
+" fontStyle: "+fontStyle
+" textDecoration "+textDecoration);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlFileUpload(Resource rdfInst, final Resource rdfHtmlLink, Resource webForm 
			, final String content, final Float fontWeight, final Float fontSize
			, final String[] fontFamily, final EFontStyle fontStyle, final ETextDecoration textDecoration) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_FILE_UPLOAD)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlFileUpload
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlFileUpload);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (webForm != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_WEB_FORM_ELEMENTS))
				imImpl.addWebFormElement(webForm, rdfInst);
			
			if (content != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_VALUE))
				imImpl.addTextValue(rdfInst, content);
			if (fontWeight != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_WEIGHT))
				imImpl.addFontWeight(rdfInst, fontWeight);
			if (fontSize != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_SIZE))
				imImpl.addFontSize(rdfInst, fontSize);
			if (fontStyle != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_STYLE))
				imImpl.addFontStyle(rdfInst, fontStyle);
			if (textDecoration != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_DECORATION))
				imImpl.addTextDecoration(rdfInst, textDecoration);
			if (fontFamily != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_FAMILY))
				imImpl.addFontFamily(rdfInst, fontFamily);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlFileUpload() rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" webForm: "+webForm
+" content: "+content
+" fontWeight: "+fontWeight
+" fontSize: "+fontSize
+" fontFamily: "+Arrays.toString(fontFamily)
+" fontStyle: "+fontStyle
+" textDecoration "+textDecoration);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlPasswordInput(Resource rdfInst, final Resource rdfHtmlLink, Resource webForm) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_PASSWORD_INPUT)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlPasswordInput
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlPasswordInput);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (webForm != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_WEB_FORM_ELEMENTS))
				imImpl.addWebFormElement(webForm, rdfInst);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlFileUpload() rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" webForm: "+webForm);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlButton(Resource rdfInst, final Resource rdfHtmlLink, Resource webForm
			, EHtmlButtonType btnType
			, Boolean isHtmlInputButton
			, final String content, final Float fontWeight, final Float fontSize
			, final String[] fontFamily, final EFontStyle fontStyle, final ETextDecoration textDecoration) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_BUTTON)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlButton
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlButton);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (webForm != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_WEB_FORM_ELEMENTS))
				imImpl.addWebFormElement(webForm, rdfInst);
			
			if (btnType != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.HAS_IM_BUTTON_TYPE))
				imImpl.addHtmlButtonType(rdfInst, btnType);
			if (btnType != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IS_HTML_INPUT_BUTTON))
				imImpl.addHtmlButtonInputType(rdfInst, isHtmlInputButton);
			if (content != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_VALUE))
				imImpl.addTextValue(rdfInst, content);
			if (fontWeight != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_WEIGHT))
				imImpl.addFontWeight(rdfInst, fontWeight);
			if (fontSize != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_SIZE))
				imImpl.addFontSize(rdfInst, fontSize);
			if (fontStyle != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_STYLE))
				imImpl.addFontStyle(rdfInst, fontStyle);
			if (textDecoration != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_DECORATION))
				imImpl.addTextDecoration(rdfInst, textDecoration);
			if (fontFamily != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_FAMILY))
				imImpl.addFontFamily(rdfInst, fontFamily);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlButton() rdfInst: "+rdfInst
+" btnType: "+btnType
+" rdfHtmlLink: "+rdfHtmlLink
+" webForm: "+webForm
+" content: "+content
+" fontWeight: "+fontWeight
+" fontSize: "+fontSize
+" fontFamily: "+Arrays.toString(fontFamily)
+" fontStyle: "+fontStyle
+" textDecoration "+textDecoration);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createIMCheckBoxGroupEmpty(Resource rdfInst) {
		if (whetherCreateInstance.apply(EIMInstType.IM_CHECKBOX_GROUP)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.WPBICheckBoxGroup
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.WPBICheckBoxGroup);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createIMCheckBoxGroupEmpty() rdfInst: "+rdfInst);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlCheckBox(Resource rdfInst, final Resource rdfHtmlLink, final Resource webForm
			, final Resource rdfCheckBoxGroup, final Boolean isChecked) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_CHECKBOX)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlCheckBox
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlCheckBox);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (webForm != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_WEB_FORM_ELEMENTS))
				imImpl.addWebFormElement(webForm, rdfInst);
			if (rdfCheckBoxGroup != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_CHECK_BOX))
				imImpl.addIMCheckBoxIntoGroup(rdfCheckBoxGroup, rdfInst);
			
			if (isChecked != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IS_IM_CHECKED))
				imImpl.addSelectedState(rdfInst, isChecked);
			

if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlWebFormEmpty():: rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" webForm: "+webForm
+ " rdfCheckBoxGroup: "+rdfCheckBoxGroup
+ " isChecked: "+isChecked);
		return rdfInst;
		}
		else return null;
	}
	
	public Resource createIMRadioButtonGroupEmpty(Resource rdfInst) {
		if (whetherCreateInstance.apply(EIMInstType.IM_RADIOBUTTON_GROUP)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.WPBIRadioButtonGroup
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.WPBIRadioButtonGroup);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createIMRadioButtonGroupEmpty() rdfInst: "+rdfInst);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlRadioButton(Resource rdfInst, final Resource rdfHtmlLink, final Resource webForm
			, final Resource rdfRadioButtonGroup, final Boolean isChecked) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_RADIOBUTTON)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlRadioButton
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlRadioButton);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (webForm != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_WEB_FORM_ELEMENTS))
				imImpl.addWebFormElement(webForm, rdfInst);
			if (rdfRadioButtonGroup != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_CHECK_BOX))
				imImpl.addIMRadioButtonIntoGroup(rdfRadioButtonGroup, rdfInst);
			
			if (isChecked != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IS_IM_CHECKED))
				imImpl.addSelectedState(rdfInst, isChecked);
			

if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlRadioButton():: rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" webForm: "+webForm
+ " rdfRadioButtonGroup: "+rdfRadioButtonGroup
+ " isChecked: "+isChecked);
		return rdfInst;
		}
		else return null;
	}
	
//	@Deprecated
//	public Resource createHtmlSelectComplete(Resource rdfInst, List<Resource> rdfOptCol, final Resource rdfHtmlLink, Resource webForm
//			, final String content, final Float fontWeight, final Float fontSize
//			, final String[] fontFamily, final EFontStyle fontStyle, final ETextDecoration textDecoration) {
//		if (whetherCreateInstance.apply(EIMInstType.HTML_SELECT)) {
//			if (rdfInst == null)
//				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlSelect
//						, imOntAdp.getRdfModel(), imOntAdp.getNameSpace());
//			else
//				imOntAdp.getRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlSelect);
//			
//			if (rdfOptCol != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_OPTION))
//				imImpl.addHtmlOptions(rdfOptCol, rdfInst);
//			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
//				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
//			if (webForm != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_WEB_FORM_ELEMENTS))
//				imImpl.addWebFormElement(webForm, rdfInst);
//			
//			if (content != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.TEXT_VALUE))
//				imImpl.addTextValue(rdfInst, content);
//			if (fontWeight != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.FONT_WEIGHT))
//				imImpl.addFontWeight(rdfInst, fontWeight);
//			if (fontSize != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.FONT_SIZE))
//				imImpl.addFontSize(rdfInst, fontSize);
//			if (fontStyle != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.FONT_STYLE))
//				imImpl.addFontStyle(rdfInst, fontStyle);
//			if (textDecoration != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.TEXT_DECORATION))
//				imImpl.addTextDecoration(rdfInst, textDecoration);
//			if (fontFamily != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.FONT_FAMILY))
//				imImpl.addFontFamily(rdfInst, fontFamily);
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlText() rdfInst: "+rdfInst
//+" rdfHtmlLink: "+rdfHtmlLink
//+" rdfOptCol: "+rdfOptCol
//+" webForm: "+webForm
//+" content: "+content
//+" fontWeight: "+fontWeight
//+" fontSize: "+fontSize
//+" fontFamily: "+fontFamily
//+" fontStyle: "+fontStyle
//+" textDecoration "+textDecoration);
//		return rdfInst;
//		}
//		else return null;
//	}
//	
//	@Deprecated
//	public Resource createHtmlOptionForCompleteSelectCreation(Resource rdfInst, final Resource rdfHtmlLink
//			, final String content, final Float fontWeight, final Float fontSize
//			, final String[] fontFamily, final EFontStyle fontStyle, final ETextDecoration textDecoration) {
//		if (whetherCreateInstance.apply(EIMInstType.HTML_OPTION)) {
//			if (rdfInst == null)
//				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlOption
//						, imOntAdp.getRdfModel(), imOntAdp.getNameSpace());
//			else
//				imOntAdp.getRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlOption);
//			
//			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
//				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
//			
//			if (content != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.TEXT_VALUE))
//				imImpl.addTextValue(rdfInst, content);
//			if (fontWeight != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.FONT_WEIGHT))
//				imImpl.addFontWeight(rdfInst, fontWeight);
//			if (fontSize != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.FONT_SIZE))
//				imImpl.addFontSize(rdfInst, fontSize);
//			if (fontStyle != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.FONT_STYLE))
//				imImpl.addFontStyle(rdfInst, fontStyle);
//			if (textDecoration != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.TEXT_DECORATION))
//				imImpl.addTextDecoration(rdfInst, textDecoration);
//			if (fontFamily != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.FONT_FAMILY))
//				imImpl.addFontFamily(rdfInst, fontFamily);
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlText() rdfInst: "+rdfInst
//+" rdfHtmlLink: "+rdfHtmlLink
//+" content: "+content
//+" fontWeight: "+fontWeight
//+" fontSize: "+fontSize
//+" fontFamily: "+fontFamily
//+" fontStyle: "+fontStyle
//+" textDecoration "+textDecoration);
//		return rdfInst;
//		}
//		else return null;
//	}
	
	public Resource createHtmlSelectEmpty(Resource rdfInst, final Resource rdfHtmlLink, Resource webForm
			// , final String content
			, final Float fontWeight, final Float fontSize
			, final String[] fontFamily, final EFontStyle fontStyle, final ETextDecoration textDecoration) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_SELECT)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlSelect
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlSelect);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (webForm != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_WEB_FORM_ELEMENTS))
				imImpl.addWebFormElement(webForm, rdfInst);
			
//			if (content != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_VALUE))
//				imImpl.addTextValue(rdfInst, content);
			if (fontWeight != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_WEIGHT))
				imImpl.addFontWeight(rdfInst, fontWeight);
			if (fontSize != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_SIZE))
				imImpl.addFontSize(rdfInst, fontSize);
			if (fontStyle != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_STYLE))
				imImpl.addFontStyle(rdfInst, fontStyle);
			if (textDecoration != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_DECORATION))
				imImpl.addTextDecoration(rdfInst, textDecoration);
			if (fontFamily != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_FAMILY))
				imImpl.addFontFamily(rdfInst, fontFamily);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlText() rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" webForm: "+webForm
//+" content: "+content
+" fontWeight: "+fontWeight
+" fontSize: "+fontSize
+" fontFamily: "+Arrays.toString(fontFamily)
+" fontStyle: "+fontStyle
+" textDecoration "+textDecoration);
		return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlOption(Resource rdfInst, final Resource rdfHtmlLink, final Resource rdfHtmlSelect
			, Boolean isSelected
			, final String content, final Float fontWeight, final Float fontSize
			, final String[] fontFamily, final EFontStyle fontStyle, final ETextDecoration textDecoration) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_OPTION)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlOption
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlOption);
			
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (rdfHtmlSelect != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_OPTION))
				imImpl.addHtmlOption(rdfHtmlSelect, rdfInst);
			
			if (isSelected != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.HAS_IM_SELECTED_OPTION))
				imImpl.addSelectedState(rdfInst, isSelected);
			if (content != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_VALUE))
				imImpl.addTextValue(rdfInst, content);
			if (fontWeight != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_WEIGHT))
				imImpl.addFontWeight(rdfInst, fontWeight);
			if (fontSize != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_SIZE))
				imImpl.addFontSize(rdfInst, fontSize);
			if (fontStyle != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_STYLE))
				imImpl.addFontStyle(rdfInst, fontStyle);
			if (textDecoration != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_TEXT_DECORATION))
				imImpl.addTextDecoration(rdfInst, textDecoration);
			if (fontFamily != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.IM_FONT_FAMILY))
				imImpl.addFontFamily(rdfInst, fontFamily);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlText() rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" rdfHtmlSelect: "+rdfHtmlSelect
+" content: "+content
+" fontWeight: "+fontWeight
+" fontSize: "+fontSize
+" fontFamily: "+Arrays.toString(fontFamily)
+" fontStyle: "+fontStyle
+" textDecoration "+textDecoration);
		return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlListEmpty(Resource rdfInst, final Resource rdfHtmlLink, final EListType listType) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_LIST)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlList
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlList);
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (listType != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.HAS_IM_LIST_TYPE))
				imImpl.addListType(rdfInst, listType);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlListEmpty() rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" listType: "+listType);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlListItem(Resource rdfInst, final Resource rdfHtmlLink, final Resource rdfList) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_LIST_ITEM)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlListItem
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlListItem);
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			
			if (rdfList != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LIST_ITEM))
				imImpl.addListItem(rdfList, rdfInst);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlListItem() rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" rdfList: "+rdfList);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createIMlListEmpty(Resource rdfInst, final EListType listType) {
		if (whetherCreateInstance.apply(EIMInstType.IM_LIST)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.WPBIList
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.WPBIList);
			if (listType != null && whetherCreateAttrOrRelInOnt.apply(EIMAttrType.HAS_IM_LIST_TYPE))
				imImpl.addListType(rdfInst, listType);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createIMlListEmpty() rdfInst: "+rdfInst
		+" listType: "+listType);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createIMListItem(Resource rdfInst, final Resource rdfList) {
		if (whetherCreateInstance.apply(EIMInstType.IM_LIST_ITEM)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.WPBIListItem
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.WPBIListItem);
			if (rdfList != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LIST_ITEM))
				imImpl.addListItem(rdfList, rdfInst);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createIMListItem() rdfInst: "+rdfInst
		+" rdfList: "+rdfList);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlTableEmpty(Resource rdfInst, final Resource rdfHtmlLink) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TABLE)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlTable
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlTable);
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);

if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlTableEmpty():: rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlTableRowEmpty(Resource rdfInst, final Resource rdfHtmlLink, final Resource htmlTable) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TABLE_ROW)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlTableRow
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlTableRow);
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (htmlTable != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_TABLE_ROW))
				imImpl.addTableRow(htmlTable, rdfInst);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlTableRowEmpty():: rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" htmlTable: "+htmlTable);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createHtmlTableCell(Resource rdfInst, final Resource rdfHtmlLink, final Resource htmlRow) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TABLE_CELL)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlTableCell
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlTableCell);
			if (rdfHtmlLink != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_LINK))
				imImpl.addHtmlLink(rdfInst, rdfHtmlLink);
			if (htmlRow != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_TABLE_CELL))
				imImpl.addTableCell(htmlRow, rdfInst);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlTableCell():: rdfInst: "+rdfInst
+" rdfHtmlLink: "+rdfHtmlLink
+" htmlRow: "+htmlRow);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createIMTableEmpty(Resource rdfInst) {
		if (whetherCreateInstance.apply(EIMInstType.IM_TABLE)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.WPBITable
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.WPBITable);
			
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createIMTableEmpty():: rdfInst: "+rdfInst);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createIMTableRowEmpty(Resource rdfInst, final Resource imTable) {
		if (whetherCreateInstance.apply(EIMInstType.IM_TABLE_ROW)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.WPBITableRow
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.WPBITableRow);
			if (imTable != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_TABLE_ROW))
				imImpl.addTableRow(imTable, rdfInst);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createIMTableRowEmpty():: rdfInst: "+rdfInst
+" imTable: "+imTable);
			return rdfInst;
		}
		else return null;
	}

	public Resource createIMTableColumnEmpty(Resource rdfInst, final Resource imTable) {
		if (whetherCreateInstance.apply(EIMInstType.IM_TABLE_COLUMN)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.WPBITableColumn
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.WPBITableColumn);
			if (imTable != null && whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_IM_TABLE_COLUMN))
				imImpl.addTableColumn(imTable, rdfInst);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createIMTableColumnEmpty():: rdfInst: "+rdfInst
+" imTable: "+imTable);
			return rdfInst;
		}
		else return null;
	}
	
	public Resource createIMTableCell(Resource rdfInst, final Resource imRow, final Resource imColumn) {
		if (whetherCreateInstance.apply(EIMInstType.IM_TABLE_CELL)) {
			if (rdfInst == null)
				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.WPBITableCell
						, imOntAdp.getBottomRdfModel(), imOntAdp.getNameSpace());
			else
				imOntAdp.getBottomRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.WPBITableCell);
			if (whetherCreateAttrOrRelInOnt.apply(EIMRelation.HAS_HTML_TABLE_CELL)) {
				if (imRow != null) imImpl.addTableCell(imRow, rdfInst);
				if (imColumn != null) imImpl.addTableCell(imColumn, rdfInst);
			}
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createIMTableCell():: rdfInst: "+rdfInst
+" imRow: "+imRow
+" imColumn: "+imColumn);
			return rdfInst;
		}
		else return null;
	}
	
//	/**
//	 * TODO comsider precise this function.
//	 * 
//	 * We suppose that parameter resopnsible for creating table's rows and table's cells has the same value as parameter for table (for optimization).
//	 * @param rdfInst
//	 * @param cellContent
//	 * @return
//	 */
//	@Deprecated
//	public Resource createHtmlTableComplete(Resource rdfInst, final Collection<Collection<Collection<Resource>>> cellsContentList) {
//		if (whetherCreateInstance.apply(EIMInstType.HTML_TABLE)
////				&& whetherCreateInstance.apply(EIMInstType.HTML_TABLE_ROW) && whetherCreateInstance.apply(EIMInstType.HTML_TABLE_CELL)
//				) {
//			if (rdfInst == null)
//				rdfInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlTable
//						, imOntAdp.getRdfModel(), imOntAdp.getNameSpace());
//			else
//				imOntAdp.getRdfModel().add(rdfInst, RDF.type, InterfaceModelOnt.HtmlTable);
//			
//			final List<Resource> rowAdplist = new ArrayList<Resource>(cellsContentList.size());
//			for (final Collection<Collection<Resource>>  rowCellsContentList: cellsContentList) {
//				final Resource rdfRowInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlTableRow
//						, imOntAdp.getRdfModel(), imOntAdp.getNameSpace());
//				rowAdplist.add(rdfRowInst);
//				final List<Resource> cellAdplist = new ArrayList<Resource>(rowCellsContentList.size());
//				for (final Collection<Resource> cellContentlist: rowCellsContentList) {
//					final Resource rdfCellInst = JenaModelsUtilLib.createNewInstance(InterfaceModelOnt.HtmlTableCell
//							, imOntAdp.getRdfModel(), imOntAdp.getNameSpace());
//					cellAdplist.add(rdfCellInst);
//					imImpl.addContainedHtmlElementsToRdfSeq(rdfCellInst, cellContentlist);
//				}
//				imImpl.addTableCells(rdfRowInst, cellAdplist);
//			}
//			imImpl.addTableRows(rdfInst, rowAdplist);
//			
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"createHtmlTable():: rdfInst: "+rdfInst+" rows: "+null+" cells:"+null);
//			return rdfInst;
//		}
//		else {
//			if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"createHtmlTableCell(): Cannot create instance because of the configuration.");
//							return null;
//			}
//	}
	
	
	
	
	
	
}
