/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts;

import org.mozilla.interfaces.nsIDOMNode;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.html.EHtmlElementConstants;
import tuwien.dbai.wpps.common.optimization.FunctionWithMemory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.TextNodesWrapper;
import tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts.GetHtmlTagIMObjectType.Result;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EHtmlButtonType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EListType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.rdfimpllib.IMLib;

/**
 * TODO: Correct!
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 14, 2012 10:57:58 PM
 * @see IMLib#TagNameToEIMInstType(String, java.util.Map, java.util.Map)
 */
public class GetHtmlTagIMObjectType extends FunctionWithMemory <nsIDOMNode, Result> {
	
	public static class Result {
		public Result(EIMInstType val1, Object val2, Object val3) {
			this.val1 = val1;
			this.val2 = val2;
			this.val3 = val3;
		}
		public Result(EIMInstType val1, Object val2) {
			this.val1 = val1;
			this.val2 = val2;
			this.val3 = null;
		}
		public Result(EIMInstType val1) {
			this.val1 = val1;
			this.val2 = null;
			this.val3 = null;
		}
		public final EIMInstType val1;
		public final Object val2;
		public final Object val3;
		
		public static Result EMPTY = new Result(null, null);
	}
	
	public GetHtmlTagIMObjectType() {
		super(
				new IFunction<nsIDOMNode, Result>() {
					@Override
					public Result apply(nsIDOMNode avar) {
						final String str = avar.getNodeName();
						if (TextNodesWrapper.ADDITIONAL_TEXT_ELEM.equals(str))
							return new Result(EIMInstType.HTML_TEXT);
						if (EHtmlElementConstants.A.string().equals(str))
							return new Result(EIMInstType.HTML_LINK);
						if (EHtmlElementConstants.IMG.string().equals(str))
							return new Result(EIMInstType.HTML_IMAGE);
						if (EHtmlElementConstants.FORM.string().equals(str))
							return new Result(EIMInstType.HTML_WEB_FORM);
						if (EHtmlElementConstants.INPUT.string().equals(str)) {
							final nsIDOMNode typNode = avar.getAttributes().getNamedItem(EHtmlElementConstants.TYPE_ATTRIBUTE.string());
							String typVal = null;
							if (typNode != null) typVal = typNode.getNodeValue();
//							String typ = avar.getAttribute(EHtmlElementConstants.TYPE_ATTRIBUTE.string());
							if (typNode == null || EHtmlElementConstants.TYPE_TEXT_VALUE.equalToIgnCase(typVal)
									|| EHtmlElementConstants.TYPE_EMAIL_VALUE.equalToIgnCase(typVal) )
								return new Result(EIMInstType.HTML_TEXT_INPUT);
							if (EHtmlElementConstants.TYPE_FILE_VALUE.equalToIgnCase(typVal))
								return new Result(EIMInstType.HTML_FILE_UPLOAD);
							if (EHtmlElementConstants.TYPE_PASSWORD_VALUE.equalToIgnCase(typVal))
								return new Result(EIMInstType.HTML_PASSWORD_INPUT);
							if (EHtmlElementConstants.TYPE_BUTTON_VALUE.equalToIgnCase(typVal))
								return new Result(EIMInstType.HTML_BUTTON, EHtmlButtonType.NORMAL, EHtmlElementConstants.INPUT);
							if (EHtmlElementConstants.TYPE_RESET_VALUE.equalToIgnCase(typVal))
								return new Result(EIMInstType.HTML_BUTTON, EHtmlButtonType.RESET, EHtmlElementConstants.INPUT);
							if (EHtmlElementConstants.TYPE_SUBMIT_VALUE.equalToIgnCase(typVal))
								return new Result(EIMInstType.HTML_BUTTON, EHtmlButtonType.SUBMIT, EHtmlElementConstants.INPUT);
							if (EHtmlElementConstants.TYPE_IMAGE_VALUE.equalToIgnCase(typVal))
								return new Result(EIMInstType.HTML_BUTTON, EHtmlButtonType.SUBMIT, EHtmlElementConstants.TYPE_IMAGE_VALUE);
							if (EHtmlElementConstants.TYPE_CHECKBOX_VALUE.equalToIgnCase(typVal))
								return new Result(EIMInstType.HTML_CHECKBOX);
							if (EHtmlElementConstants.TYPE_RADIO_VALUE.equalToIgnCase(typVal))
								return new Result(EIMInstType.HTML_RADIOBUTTON);
						}
						else
						if (EHtmlElementConstants.BUTTON.string().equals(str)) {
							final nsIDOMNode typNode = avar.getAttributes().getNamedItem(EHtmlElementConstants.TYPE_ATTRIBUTE.string());
							String typVal = null;
							if (typNode != null) typVal = typNode.getNodeValue();
							if (typNode == null || EHtmlElementConstants.TYPE_BUTTON_VALUE.equalToIgnCase(typVal))
								return new Result(EIMInstType.HTML_BUTTON, EHtmlButtonType.NORMAL, EHtmlElementConstants.BUTTON);
							if (EHtmlElementConstants.TYPE_RESET_VALUE.equalToIgnCase(typVal))
								return new Result(EIMInstType.HTML_BUTTON, EHtmlButtonType.RESET, EHtmlElementConstants.BUTTON);
							if (EHtmlElementConstants.TYPE_SUBMIT_VALUE.equalToIgnCase(typVal))
								return new Result(EIMInstType.HTML_BUTTON, EHtmlButtonType.SUBMIT, EHtmlElementConstants.BUTTON);
						}
						else
						if (EHtmlElementConstants.TEXTAREA.string().equals(str))
							return new Result(EIMInstType.HTML_TEXT_AREA);
						if (EHtmlElementConstants.SELECT.string().equals(str))
							return new Result(EIMInstType.HTML_SELECT);
						if (EHtmlElementConstants.OPTION.string().equals(str))
							return new Result(EIMInstType.HTML_OPTION);
						if (EHtmlElementConstants.UL.string().equals(str))
							return new Result(EIMInstType.HTML_LIST, EListType.UNORDERED);
						if (EHtmlElementConstants.OL.string().equals(str))
							return new Result(EIMInstType.HTML_LIST, EListType.ORDERED);
						if (EHtmlElementConstants.LI.string().equals(str))
							return new Result(EIMInstType.HTML_LIST_ITEM);
						// TODO: add list description
						if (EHtmlElementConstants.TABLE.string().equals(str))
							return new Result(EIMInstType.HTML_TABLE);
						if (EHtmlElementConstants.TR.string().equals(str))
							return new Result(EIMInstType.HTML_TABLE_ROW);
						if (EHtmlElementConstants.TD.string().equals(str))
							return new Result(EIMInstType.HTML_TABLE_CELL);
						
						
						
						
//						return EHtmlElementConstants.valueOfStrRepr(str);
						return Result.EMPTY;
						
					}
					
				}
				, 10000);
	}

}
