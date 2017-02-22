/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.im;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMCSS2Properties;
import org.mozilla.interfaces.nsIDOMNode;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.common.html.EHtmlElementConstants;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.GetHtmlTagIMObjectType;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.GetHtmlTagIMObjectType.Result;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EHtmlButtonType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EListType;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 16, 2012 2:58:07 PM
 */
public final class IMRdfInstFactorySupport {
	private static final Logger log = Logger.getLogger(IMRdfInstFactorySupport.class);
	
	final IFunction<nsIDOMNode, Resource> nodeResourceFunc;
	
	private final IMRdfInstFactory imRdfInstFactory;
	public IMRdfInstFactory getImRdfInstFactory() {
		return imRdfInstFactory;
	}

	private final GetHtmlTagIMObjectType getHtmlTagIMObjectType;
	
	public IMRdfInstFactorySupport(
			final IMRdfInstFactory imRdfInstFactory
			, final GetHtmlTagIMObjectType getHtmlTagIMObjectType
			, final IFunction<nsIDOMNode, Resource> nodeResourceFunc
			) {
		this.imRdfInstFactory = imRdfInstFactory;
		this.getHtmlTagIMObjectType = getHtmlTagIMObjectType;
		this.nodeResourceFunc = nodeResourceFunc;
	}
	
	/**
	 * Should be called when go down during depth-first search.
	 * @param rdfInst
	 * @param n
	 * @param el
	 * @param cssProps
	 * @return
	 */
	public Resource createRdfInstanceDown(Resource rdfInst, final nsIDOMNode node//, final nsIDOMElement el
			, final nsIDOMCSS2Properties cssProps) {
		Result res = getHtmlTagIMObjectType.apply(node);
		final EIMInstType imInstType = res.val1;
		if (imInstType == null) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Cannot detect element "+node.getNodeName());
		}
		else {
			switch (imInstType) {
			case HTML_LINK:
				rdfInst = imRdfInstFactory.createHtmlLinkEmpty(rdfInst, node);
				break;
			case HTML_TEXT:
				rdfInst = imRdfInstFactory.createText(rdfInst, node, cssProps);
				break;
			case HTML_IMAGE:
				rdfInst = imRdfInstFactory.createImage(rdfInst, node);
				break;
			case HTML_WEB_FORM:
				rdfInst = imRdfInstFactory.createHtmlWebFormEmpty(rdfInst, node);
				break;
			case HTML_TEXT_INPUT:
				rdfInst = imRdfInstFactory.createHtmlTextInput(rdfInst, node, cssProps);
				break;
			case HTML_TEXT_AREA:
				rdfInst = imRdfInstFactory.createHtmlTextArea(rdfInst, node, cssProps);
				break;
			case HTML_FILE_UPLOAD:
				rdfInst = imRdfInstFactory.createHtmlFileUpload(rdfInst, node, cssProps);
				break;
			case HTML_PASSWORD_INPUT:
				rdfInst = imRdfInstFactory.createHtmlPasswordInput(rdfInst, node);
				break;
			case HTML_BUTTON:
				rdfInst = imRdfInstFactory.createHtmlButton(rdfInst, node, cssProps, (EHtmlButtonType)res.val2, (EHtmlElementConstants)res.val3);
				break;
			case HTML_CHECKBOX:
				rdfInst = imRdfInstFactory.createHtmlCheckBox(rdfInst, node);
				break;
			case HTML_RADIOBUTTON:
				rdfInst = imRdfInstFactory.createHtmlRadioButton(rdfInst, node);
				break;
			case HTML_SELECT:
				rdfInst = imRdfInstFactory.createHtmlSelectEmpty(rdfInst, node, cssProps);
				break;
			case HTML_OPTION:
				rdfInst = imRdfInstFactory.createHtmlOption(rdfInst, node, cssProps);
				break;
			case HTML_LIST:
				rdfInst = imRdfInstFactory.createHtmlListEmpty(rdfInst, node, (EListType)res.val2);
				break;
			case HTML_LIST_ITEM:
				rdfInst = imRdfInstFactory.createHtmlListItem(rdfInst, node);
				break;
			case HTML_TABLE:
				rdfInst = imRdfInstFactory.createHtmlTableEmpty(rdfInst, node);
				break;
			case HTML_TABLE_ROW:
				rdfInst = imRdfInstFactory.createHtmlTableRowEmpty(rdfInst, node);
				break;
			case HTML_TABLE_CELL:
				rdfInst = imRdfInstFactory.createHtmlTableCell(rdfInst, node);
				break;
			default:
				throw new UnknownValueFromPredefinedList(log, imInstType);
			}
		}
//		addHasHtmlLinkIfNecessary(n, rdfInst, imInstType);
		return rdfInst;
	}

	
//	/**
//	 * Functions allows to connect html link's contained elements with itself.
//	 * @param n
//	 * @param nr
//	 * @param imInstType
//	 */
//	public void addHasHtmlLinkIfNecessary(final nsIDOMNode n, final Resource nr, final EIMInstType imInstType) {
//		if (imInstType == EIMInstType.HTML_LINK) {
//			if (nr != null)
//				linkedElLinkResMap.put(n, nr);
//		}
//		else {
//			final nsIDOMNode pn = n.getParentNode();
//			if (pn == null) return;
//			final Resource lr = linkedElLinkResMap.get(pn);
//			if (lr == null) return;
//			if (imInstType != null) { // we link relation only if we know type of the element.
//				if (nr != null)
//					imRdfInstFactory.addHasHtmlLinkRelation(nr, lr);
//			}
//			if (imInstType != EIMInstType.HTML_TEXT && n.hasChildNodes())
//				linkedElLinkResMap.put(n, lr);
//		}
//	}
//	private final Map<nsIDOMNode, Resource> linkedElLinkResMap = new HashMap<nsIDOMNode, Resource>();
	
	
	
//	/**
//	 * Should be called when go down during depth-first search.
//	 * @param rdfInst
//	 * @param n
//	 * @param el
//	 * @param cssProps
//	 * @return
//	 */
//	public Resource createRdfInstanceUp(Resource rdfInst, final nsIDOMNode n, final nsIDOMElement el
//			, final nsIDOMCSS2Properties cssProps) {
//		final EIMInstType imInstType = getHtmlTagIMObjectType.apply(el);
//		if (imInstType == null) {
//if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Cannot detect element "+el.getNodeName());
//		}
//		else {
//			switch (imInstType) {
//			case HTML_TABLE:
//				rdfInst = imRdfInstFactory.createHtmlTableCompleteGoingUp(rdfInst, el, nodeResourceFunc);
//				break;
//			case HTML_TEXT:
//			case HTML_LINK:
//			case HTML_IMAGE:
//			case HTML_TABLE_ROW:
//			case HTML_TABLE_CELL:
//				break;
//			default:
//				throw new UnknownValueFromPredefinedList(log, imInstType);
//			}
//		}
//		addHasHtmlLinkIfNecessary(n, rdfInst, imInstType);
//		return rdfInst;
//	}
	


}
