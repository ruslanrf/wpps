/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.im;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMCSS2Properties;
import org.mozilla.interfaces.nsIDOMHTMLAnchorElement;
import org.mozilla.interfaces.nsIDOMHTMLButtonElement;
import org.mozilla.interfaces.nsIDOMHTMLFormElement;
import org.mozilla.interfaces.nsIDOMHTMLImageElement;
import org.mozilla.interfaces.nsIDOMHTMLInputElement;
import org.mozilla.interfaces.nsIDOMHTMLOptionElement;
import org.mozilla.interfaces.nsIDOMHTMLOptionsCollection;
import org.mozilla.interfaces.nsIDOMHTMLSelectElement;
import org.mozilla.interfaces.nsIDOMHTMLTextAreaElement;
import org.mozilla.interfaces.nsIDOMNSHTMLElement;
import org.mozilla.interfaces.nsIDOMNode;

import tuwien.dbai.wpps.common.Mapping2;
import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.common.html.ECSSPropertyConstants;
import tuwien.dbai.wpps.common.html.EHtmlElementConstants;
import tuwien.dbai.wpps.core.wpmfillermozeditor.TextNodesWrapper;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.GetObjectForNode;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateObject;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EFontStyle;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EHtmlButtonType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EListType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.ETextDecoration;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EWebFormMethod;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.factory.IMRdfInstanceFactory;
import tuwien.dbai.wpps.mozcommon.MozStringUtils;

import com.google.common.base.Strings;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * TODO: if String.length == 0 set "null"
 * TODO create IMTable.
 * TODO: rename propeties for tables and lists. IM and Html can link on the same object.
 * TODO create IMLink in proper way.
 * TODO optimize multiple acquisition of interface nsIDOMHTMLInputElement.
 * TODO add "if corresponding web form resoiurce is not found"
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 16, 2012 2:36:12 PM
 */
public final class IMRdfInstFactory {
	private static final Logger log = Logger.getLogger(IMRdfInstFactory.class);

	private final Map<nsIDOMHTMLFormElement, Resource> formResourceMap
		= new LinkedHashMap<nsIDOMHTMLFormElement, Resource>(20);
	private final Map<nsIDOMNode, Resource> optionSelResourceMap
		= new LinkedHashMap<nsIDOMNode, Resource>(20);
	
	private final GetObjectForNode getLinkForNode = new GetObjectForNode();
	private final GetObjectForNode getHtmlListForListItem = new GetObjectForNode();
	private final GetObjectForNode getIMListForListItem = new GetObjectForNode();
	
	private final GetObjectForNode getHtmlTableForRow = new GetObjectForNode();
	private final GetObjectForNode getHtmlRowForCell = new GetObjectForNode();
	private final GetObjectForNode getIMTableForRow = new GetObjectForNode();
	private final GetObjectForNode getIMRowForCell = new GetObjectForNode();
	
	private final Mapping2 checkBoxGroup = new Mapping2();
	private final Mapping2 radioButtonGroup = new Mapping2();
	
	private final WhetherCreateObject whetherCreateInstance;
	private final IMRdfInstanceFactory imRdfInstanceFactory;
	private final OntModelAdp ontModelAdp;

	public IMRdfInstFactory(final WhetherCreateObject whetherCreateInstance
			, final IMRdfInstanceFactory imRdfInstanceFactory
			, final OntModelAdp ontModelAdp) {
if (log.isTraceEnabled()) log.trace("Constructing IM RDF instance factory");
		this.whetherCreateInstance = whetherCreateInstance;
		this.imRdfInstanceFactory = imRdfInstanceFactory;
		this.ontModelAdp = ontModelAdp;
	}
	
	private String EmptyStringToNull(String str) {
		if (str == null || str.length() == 0 || str.trim().length() == 0) 
			return null;
		return str;
	}

	public Resource createHtmlLinkEmpty(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_LINK)) {
			nsIDOMHTMLAnchorElement a = (nsIDOMHTMLAnchorElement)
					node.queryInterface(nsIDOMHTMLAnchorElement.NS_IDOMHTMLANCHORELEMENT_IID);
			rdfInst = imRdfInstanceFactory.createHtmlLinkEmpty(rdfInst, a.getHref());
			if (rdfInst != null) getLinkForNode.add(node, rdfInst);
			return rdfInst;
		}
		return null;
	}
	
	public Resource createText(Resource rdfInst, final nsIDOMNode node, final nsIDOMCSS2Properties cssProps) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TEXT)) {
//if (log.isTraceEnabled()) log.trace("FontFamily: "+cssProps.getFontFamily()
//		+" ParseFontFamily:"+Arrays.toString(MozStringUtils.parseFontFamily(cssProps.getFontFamily().toLowerCase()))
//		);
			rdfInst = imRdfInstanceFactory.createHtmlText(
					rdfInst
					, (Resource)getLinkForNode.apply(node)
					, TextNodesWrapper.getStringContentOfWrapperNode(node)
					, MozStringUtils.parseFontWeight(cssProps.getFontWeight())
					, Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getFontSize()))
					, MozStringUtils.parseFontFamily(cssProps.getFontFamily().toLowerCase())
					, getFontStyle(cssProps)
					, getTextDecoration(cssProps));
			return rdfInst;
		}
		return null;
	}
	
	private EFontStyle getFontStyle(final nsIDOMCSS2Properties cssProps) {
		final String fontStyle = cssProps.getFontStyle();
		if (ECSSPropertyConstants.FONT_STYLE_NORMAL_VALUE.string().equals(fontStyle))
			return EFontStyle.NORMAL_FONT_STYLE;
		if (ECSSPropertyConstants.FONT_STYLE_ITALIC_VALUE.string().equals(fontStyle))
			return EFontStyle.ITALIC;
		if (ECSSPropertyConstants.FONT_STYLE_OBLIQUE_VALUE.string().equals(fontStyle))
			return EFontStyle.OBLIQUE;
		else
			throw new UnknownValueFromPredefinedList(log, fontStyle);
	}
	
	private ETextDecoration getTextDecoration(final nsIDOMCSS2Properties cssProps) {
		final String fontDecor = cssProps.getTextDecoration();
		if (ECSSPropertyConstants.TEXT_DECORATION_NONE_VALUE.string().equals(fontDecor))
			return ETextDecoration.NONE;
		if (ECSSPropertyConstants.TEXT_DECORATION_UNDERLINE_VALUE.string().equals(fontDecor))
			return ETextDecoration.UNDERLINE;
		if (ECSSPropertyConstants.TEXT_DECORATION_OVERLINE_VALUE.string().equals(fontDecor))
			return ETextDecoration.OVERLINE;
		if (ECSSPropertyConstants.TEXT_DECORATION_LINE_THROUGH_VALUE.string().equals(fontDecor))
			return ETextDecoration.LINE_THROUGH;
		if (ECSSPropertyConstants.TEXT_DECORATION_BLINK_VALUE.string().equals(fontDecor))
			return ETextDecoration.BLINK;
		else
			throw new UnknownValueFromPredefinedList(log, fontDecor);
	}
	
//	public void addHasHtmlLinkRelation(final Resource el, final Resource link) {
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"addHasHtmlLinkRelation():: el:"+el+", link:"+link);
//		ontModelAdp.getRdfModel().add(el, InterfaceModelOnt.hasHtmlLink, link);
//	}
	
	public Resource createImage(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_IMAGE)) {
			final nsIDOMHTMLImageElement img  = (nsIDOMHTMLImageElement)node
					.queryInterface(nsIDOMHTMLImageElement.NS_IDOMHTMLIMAGEELEMENT_IID);
			rdfInst = imRdfInstanceFactory.createHtmlImage(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, img.getSrc()
					, img.getAlt());
			return rdfInst;
		}
		return null;
	}
	
	public Resource createHtmlWebFormEmpty(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_WEB_FORM)) {
			nsIDOMHTMLFormElement form = (nsIDOMHTMLFormElement)node
					.queryInterface(nsIDOMHTMLFormElement.NS_IDOMHTMLFORMELEMENT_IID);
			rdfInst = imRdfInstanceFactory.createHtmlWebFormEmpty(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, _getWebFormMethod(form.getMethod())
					, form.getAction());
			if (rdfInst != null) formResourceMap.put(form, rdfInst);
			return rdfInst;
		}
		return null;
	}
	
	private EWebFormMethod _getWebFormMethod(final String value) {
		if (EHtmlElementConstants.FORM_METHOD_GET_VALUE.string().equals(value))
			return EWebFormMethod.GET;
		if (EHtmlElementConstants.FORM_METHOD_POST_VALUE.string().equals(value))
			return EWebFormMethod.POST;
if (log.isDebugEnabled()) log.debug("Method "+value+" of web the form is unknown or empty");
		return null;
	}
	
	public Resource createHtmlTextInput(Resource rdfInst, final nsIDOMNode node, final nsIDOMCSS2Properties cssProps) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TEXT_INPUT)) {
			nsIDOMHTMLInputElement fe = (nsIDOMHTMLInputElement)node
					.queryInterface(nsIDOMHTMLInputElement.NS_IDOMHTMLINPUTELEMENT_IID);
			rdfInst = imRdfInstanceFactory.createHtmlTextInput(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, formResourceMap.get(fe.getForm())
					, fe.getValue()
					, MozStringUtils.parseFontWeight(cssProps.getFontWeight())
					, Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getFontSize()))
					, MozStringUtils.parseFontFamily(cssProps.getFontFamily().toLowerCase())
					, getFontStyle(cssProps)
					, getTextDecoration(cssProps));
			return rdfInst;
		}
		return null;
	}
	
	public Resource createHtmlTextArea(Resource rdfInst, final nsIDOMNode node, final nsIDOMCSS2Properties cssProps) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TEXT_AREA)) {
			nsIDOMHTMLTextAreaElement fe = (nsIDOMHTMLTextAreaElement)node
					.queryInterface(nsIDOMHTMLTextAreaElement.NS_IDOMHTMLTEXTAREAELEMENT_IID);
			rdfInst = imRdfInstanceFactory.createHtmlTextArea(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, formResourceMap.get(fe.getForm())
					, fe.getValue()
					, MozStringUtils.parseFontWeight(cssProps.getFontWeight())
					, Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getFontSize()))
					, MozStringUtils.parseFontFamily(cssProps.getFontFamily().toLowerCase())
					, getFontStyle(cssProps)
					, getTextDecoration(cssProps));
			return rdfInst;
		}
		return null;
	}
	
	public Resource createHtmlFileUpload(Resource rdfInst, final nsIDOMNode node, final nsIDOMCSS2Properties cssProps) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_FILE_UPLOAD)) {
			nsIDOMHTMLInputElement fe = (nsIDOMHTMLInputElement)node
					.queryInterface(nsIDOMHTMLInputElement.NS_IDOMHTMLINPUTELEMENT_IID);
			rdfInst = imRdfInstanceFactory.createHtmlFileUpload(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, formResourceMap.get(fe.getForm())
					, fe.getValue()
					, MozStringUtils.parseFontWeight(cssProps.getFontWeight())
					, Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getFontSize()))
					, MozStringUtils.parseFontFamily(cssProps.getFontFamily().toLowerCase())
					, getFontStyle(cssProps)
					, getTextDecoration(cssProps));
			return rdfInst;
		}
		return null;
	}
	
	public Resource createHtmlPasswordInput(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_PASSWORD_INPUT)) {
			nsIDOMHTMLInputElement fe = (nsIDOMHTMLInputElement)node
					.queryInterface(nsIDOMHTMLInputElement.NS_IDOMHTMLINPUTELEMENT_IID);
			rdfInst = imRdfInstanceFactory.createHtmlPasswordInput(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, formResourceMap.get(fe.getForm()));
			return rdfInst;
		}
		return null;
	}
	
	public Resource createHtmlButton(Resource rdfInst, final nsIDOMNode node
			, final nsIDOMCSS2Properties cssProps, EHtmlButtonType btnType, EHtmlElementConstants htmlElType) {
//if (log.isTraceEnabled()) log.trace("createHtmlButton:: rdfInst: "+rdfInst+" btnType: "+btnType+" node.name: "+node.getNodeName());
			switch (htmlElType) {
			case INPUT:
				return _createHtmlButtonInput(rdfInst, node, cssProps, btnType);
			case BUTTON:
				return _createHtmlButton2(rdfInst, node, cssProps, btnType);
			case TYPE_IMAGE_VALUE: {
				return _createHtmlImageInput(rdfInst, node, cssProps);
			}
			default:
log.warn(TSForLog.getTS(log)+"Unknown type: "+htmlElType);
				break;
			}
		return null;
	}
	
	private Resource _createHtmlImageInput(Resource rdfInst, final nsIDOMNode node
			, final nsIDOMCSS2Properties cssProps) {
		final boolean createButton = whetherCreateInstance.apply(EIMInstType.HTML_BUTTON);
		final boolean createImage = whetherCreateInstance.apply(EIMInstType.HTML_IMAGE);
		if (createButton || createImage) {
			nsIDOMHTMLInputElement fe = (nsIDOMHTMLInputElement)node
					.queryInterface(nsIDOMHTMLInputElement.NS_IDOMHTMLINPUTELEMENT_IID);
			Resource link = (Resource)getLinkForNode.apply(node);
			if (createButton) {
				rdfInst = imRdfInstanceFactory.createHtmlButton(rdfInst
						, link
						, formResourceMap.get(fe.getForm())
						, EHtmlButtonType.SUBMIT
						, true
						, fe.getValue()
						, MozStringUtils.parseFontWeight(cssProps.getFontWeight())
						, Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getFontSize()))
						, MozStringUtils.parseFontFamily(cssProps.getFontFamily().toLowerCase())
						, getFontStyle(cssProps)
						, getTextDecoration(cssProps));
			}
			if (createImage) {
				rdfInst = imRdfInstanceFactory.createHtmlImage(rdfInst
						, link
						, fe.getSrc()
						, fe.getAlt());
			}
				return rdfInst;
		}
		return null;
	} 
	
	private Resource _createHtmlButtonInput(Resource rdfInst, final nsIDOMNode node
			, final nsIDOMCSS2Properties cssProps, EHtmlButtonType btnType) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_BUTTON)) {
			nsIDOMHTMLInputElement fe = (nsIDOMHTMLInputElement)node
					.queryInterface(nsIDOMHTMLInputElement.NS_IDOMHTMLINPUTELEMENT_IID);
			rdfInst = imRdfInstanceFactory.createHtmlButton(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, formResourceMap.get(fe.getForm())
					, btnType
					, true
					, fe.getValue()
					, MozStringUtils.parseFontWeight(cssProps.getFontWeight())
					, Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getFontSize()))
					, MozStringUtils.parseFontFamily(cssProps.getFontFamily().toLowerCase())
					, getFontStyle(cssProps)
					, getTextDecoration(cssProps));
			return rdfInst;
		}
		return null;
	}
	
	private Resource _createHtmlButton2(Resource rdfInst, final nsIDOMNode node
			, final nsIDOMCSS2Properties cssProps, EHtmlButtonType btnType) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_BUTTON)) {
			nsIDOMHTMLButtonElement fe = (nsIDOMHTMLButtonElement)node
					.queryInterface(nsIDOMHTMLButtonElement.NS_IDOMHTMLBUTTONELEMENT_IID);
			nsIDOMNSHTMLElement el2 = (nsIDOMNSHTMLElement)fe
					.queryInterface(nsIDOMNSHTMLElement.NS_IDOMNSHTMLELEMENT_IID);
			String cnt = el2.getInnerHTML();
			if (Strings.isNullOrEmpty(cnt))
				cnt = null;
			else {
				cnt = cnt.replaceAll("<(.|\n)*?>", " "); // TODO: problematic regexp
				cnt.trim();
			}
			rdfInst = imRdfInstanceFactory.createHtmlButton(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, formResourceMap.get(fe.getForm())
					, btnType
					, false
					, cnt // fe.getValue() is wrong value here. TODO: implement relation between button and container elements
					, MozStringUtils.parseFontWeight(cssProps.getFontWeight())
					, Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getFontSize()))
					, MozStringUtils.parseFontFamily(cssProps.getFontFamily().toLowerCase())
					, getFontStyle(cssProps)
					, getTextDecoration(cssProps));
			return rdfInst;
			}
		return null;
	}
	
	
//	public Resource createIMCheckBoxGroupEmpty(Resource rdfInst) {
//		if (whetherCreateInstance.apply(EIMInstType.IM_CHECKBOX_GROUP)) {
//			rdfInst =imRdfInstanceFactory.createIMCheckBoxGroupEmpty(rdfInst);
//			return rdfInst;
//		}
//		return null;
//	}
	
	private Resource _getIMCheckBoxGroup(String name, nsIDOMHTMLFormElement form) {
		if (whetherCreateInstance.apply(EIMInstType.IM_CHECKBOX_GROUP)) {
			Object group = checkBoxGroup.getMappedObject(form, name);
			if (group == null) {
				group = imRdfInstanceFactory.createIMCheckBoxGroupEmpty(null);
				checkBoxGroup.addMapping(form, name, group);
			}
			return (Resource)group;
		}
		return null;
	}
	
	public Resource createHtmlCheckBox(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_CHECKBOX)) {
			nsIDOMHTMLInputElement fe = (nsIDOMHTMLInputElement)node
					.queryInterface(nsIDOMHTMLInputElement.NS_IDOMHTMLINPUTELEMENT_IID);
			nsIDOMHTMLFormElement form = fe.getForm();
			String name = fe.getName();
			rdfInst = imRdfInstanceFactory.createHtmlCheckBox(
					rdfInst
					, (Resource)getLinkForNode.apply(node)
					, formResourceMap.get(fe.getForm())
					, _getIMCheckBoxGroup(name, form)
					, fe.getChecked());
			return rdfInst;
		}
		return null;
	}
	
	private Resource _getIMRadioButtonGroup(String name, nsIDOMHTMLFormElement form) {
		if (whetherCreateInstance.apply(EIMInstType.IM_RADIOBUTTON_GROUP)) {
			Object group = radioButtonGroup.getMappedObject(form, name);
			if (group == null) {
				group = imRdfInstanceFactory.createIMRadioButtonGroupEmpty(null);
				radioButtonGroup.addMapping(form, name, group);
			}
			return (Resource)group;
		}
		return null;
	}
	
	public Resource createHtmlRadioButton(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_RADIOBUTTON)) {
			nsIDOMHTMLInputElement fe = (nsIDOMHTMLInputElement)node
					.queryInterface(nsIDOMHTMLInputElement.NS_IDOMHTMLINPUTELEMENT_IID);
			nsIDOMHTMLFormElement form = fe.getForm();
			String name = fe.getName();
			rdfInst = imRdfInstanceFactory.createHtmlRadioButton(
					rdfInst
					, (Resource)getLinkForNode.apply(node)
					, formResourceMap.get(fe.getForm())
					, _getIMRadioButtonGroup(name, form)
					, fe.getChecked());
			return rdfInst;
		}
		return null;
	}
	
//	public Resource createHtmlSelectComplete(Resource rdfInst, final nsIDOMNode node
//			, final nsIDOMCSS2Properties cssProps) {
//		if (whetherCreateInstance.apply(EIMInstType.HTML_SELECT)) {
//			nsIDOMHTMLSelectElement sel = (nsIDOMHTMLSelectElement)node
//					.queryInterface(nsIDOMHTMLSelectElement.NS_IDOMHTMLSELECTELEMENT_IID);
//			nsIDOMHTMLOptionsCollection opts = sel.getOptions();
//			int n = (int)opts.getLength();
//			List<Resource> optList = new ArrayList<Resource>(n);
//			for (int i=0; i<n; i++) {
//				nsIDOMNode optNode = opts.item(i);
//				_createHtmlOptionForCompleteSelectCreation(null, optNode, cssProps);
//			}
//			rdfInst = imRdfInstanceFactory.createHtmlSelectEmpty(rdfInst
//					, (Resource)getLinkForNode.apply(node)
//					, formResourceMap.get(sel.getForm())
//					, sel.getValue()
//					, MozStringUtils.parseFontWeight(cssProps.getFontWeight())
//					, Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getFontSize()))
//					, MozStringUtils.parseFontFamily(cssProps.getFontFamily().toLowerCase())
//					, getFontStyle(cssProps)
//					, getTextDecoration(cssProps));
//					
//		}
//		return null;
//	}
	
	public Resource createHtmlSelectEmpty(Resource rdfInst, final nsIDOMNode node
			, final nsIDOMCSS2Properties cssProps) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_SELECT)) {
			nsIDOMHTMLSelectElement sel = (nsIDOMHTMLSelectElement)node
					.queryInterface(nsIDOMHTMLSelectElement.NS_IDOMHTMLSELECTELEMENT_IID);
			rdfInst = imRdfInstanceFactory.createHtmlSelectEmpty(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, formResourceMap.get(sel.getForm())
					//sel.getValue()
					, MozStringUtils.parseFontWeight(cssProps.getFontWeight())
					, Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getFontSize()))
					, MozStringUtils.parseFontFamily(cssProps.getFontFamily().toLowerCase())
					, getFontStyle(cssProps)
					, getTextDecoration(cssProps));
			if (rdfInst != null) {
				nsIDOMHTMLOptionsCollection opts = sel.getOptions();
				int n = (int)opts.getLength();
				for (int i=0; i<n; i++) 
					optionSelResourceMap.put(opts.item(i), rdfInst);
			}
			return rdfInst;
		}
		return null;
	}
	
	public Resource createHtmlOption(Resource rdfInst, final nsIDOMNode node
			, final nsIDOMCSS2Properties cssProps) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_OPTION)) {
			nsIDOMHTMLOptionElement opt = (nsIDOMHTMLOptionElement)node.
					queryInterface(nsIDOMHTMLOptionElement.NS_IDOMHTMLOPTIONELEMENT_IID);
			rdfInst = imRdfInstanceFactory.createHtmlOption(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, optionSelResourceMap.get(node)
					, opt.getSelected()
					, opt.getText()
					, MozStringUtils.parseFontWeight(cssProps.getFontWeight())
					, Float.parseFloat(MozStringUtils.getNumericalPartWOPx(cssProps.getFontSize()))
					, MozStringUtils.parseFontFamily(cssProps.getFontFamily().toLowerCase())
					, getFontStyle(cssProps)
					, getTextDecoration(cssProps));
			return rdfInst;
		}
		return null;
	}
	
	public Resource createHtmlListEmpty(Resource rdfInst, final nsIDOMNode node, final EListType listType) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_LIST)) {
			rdfInst = imRdfInstanceFactory.createHtmlListEmpty(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, listType);
			getHtmlListForListItem.add(node, rdfInst);
			return rdfInst;
		}
		return null;
	}
	
	public Resource createHtmlListItem(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_LIST_ITEM)) {
			rdfInst = imRdfInstanceFactory.createHtmlListItem(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, (Resource)getHtmlListForListItem.apply(node));
			return rdfInst;
		}
		return null;
	}
	
	public Resource createIMListEmpty(Resource rdfInst, final nsIDOMNode node, final EListType listType) {
		if (whetherCreateInstance.apply(EIMInstType.IM_LIST)) {
			rdfInst = imRdfInstanceFactory.createIMlListEmpty(rdfInst, listType);
			getIMListForListItem.add(node, rdfInst);
			return rdfInst;
		}
		return null;
	}
	
	public Resource createIMListItem(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EIMInstType.IM_LIST_ITEM)) {
			rdfInst = imRdfInstanceFactory.createIMListItem(rdfInst
					, (Resource)getIMListForListItem.apply(node));
			return rdfInst;
		}
		return null;
	}
	
	public Resource createHtmlTableEmpty(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TABLE)) {
			rdfInst = imRdfInstanceFactory.createHtmlTableEmpty(rdfInst, (Resource)getLinkForNode.apply(node));
			getHtmlTableForRow.add(node, rdfInst);
			return rdfInst;
		}
		return null;
	}
	
	public Resource createHtmlTableRowEmpty(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TABLE_ROW)) {
			rdfInst = imRdfInstanceFactory.createHtmlTableRowEmpty(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, (Resource)getHtmlTableForRow.apply(node));
			getHtmlRowForCell.add(node, rdfInst);
			return rdfInst;
		}
		return null;
	}
	
	public Resource createHtmlTableCell(Resource rdfInst, final nsIDOMNode node) {
		if (whetherCreateInstance.apply(EIMInstType.HTML_TABLE_CELL)) {
			rdfInst = imRdfInstanceFactory.createHtmlTableCell(rdfInst
					, (Resource)getLinkForNode.apply(node)
					, (Resource)getHtmlRowForCell.apply(node));
			return rdfInst;
		}
		return null;
	}
	
//	public Resource createHtmlTableCompleteGoingUp(Resource rdfInst, final nsIDOMElement el
//			, final IFunction<nsIDOMNode, Resource> nodeResourceFunc) {
//		if (whetherCreateInstance.apply(EIMInstType.HTML_TABLE)) {
//			final nsIDOMHTMLTableElement table = (nsIDOMHTMLTableElement)el
//					.queryInterface(nsIDOMHTMLTableElement.NS_IDOMHTMLTABLEELEMENT_IID);
//			final nsIDOMHTMLCollection rows = table.getRows();
//			final Collection<Collection<Collection<Resource>>> tableCellsContentCol
//				= new ArrayList<Collection<Collection<Resource>>>((int)rows.getLength());
//			for (int i=0; i<rows.getLength(); i++) {
//				nsIDOMHTMLTableRowElement row = (nsIDOMHTMLTableRowElement)rows.item(i)
//						.queryInterface(nsIDOMHTMLTableRowElement.NS_IDOMHTMLTABLEROWELEMENT_IID);
//				final nsIDOMHTMLCollection cells = row.getCells();
//				final Collection<Collection<Resource>> rowCellsContentCol
//					= new ArrayList<Collection<Resource>>((int)cells.getLength());
//				tableCellsContentCol.add(rowCellsContentCol);
//				for (int j=0; j<cells.getLength(); j++) {
//					final Collection<Resource> cellContentCol
//						= new ArrayList<Resource>((int)cells.getLength());
//if (log.isTraceEnabled()) log.trace("cell nam: "+cells.item(j).getNodeName()+" cell val: "+cells.item(j).getNodeValue()+" res:"+nodeResourceFunc.apply((nsIDOMNode)cells.item(j).queryInterface(nsIDOMNode.NS_IDOMNODE_IID)));
//					final nsIDOMNodeList childNodes = cells.item(j).getChildNodes();
//					final int kk = (int)childNodes.getLength();
//					for (int k = 0; k<kk; k++) {
//						final nsIDOMNode chn = childNodes.item(k);
//						final Resource chnr = nodeResourceFunc.apply(chn);
//if (log.isTraceEnabled()) log.trace("nam: "+chn.getNodeName()+" val: "+chn.getNodeValue()+" res:"+chnr);
//						if (chnr != null && ontModelAdp.getRdfModel().containsResource(chnr)) {
//							cellContentCol.add(chnr);
//						}
//					}
//					rowCellsContentCol.add(cellContentCol);
//				}
//			}
//			rdfInst = imRdfInstanceFactory.createHtmlTableComplete(rdfInst, tableCellsContentCol);
//			return rdfInst;
//		}
//		return null;
//	}
	
	
	
	
}
