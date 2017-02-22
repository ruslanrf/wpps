/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.supportfuncts;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.html.EHtmlElementConstants;
import tuwien.dbai.wpps.common.optimization.FunctionWithMemory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.DOMWalker;

/**
 * Gives a very little advantage
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 13, 2011 9:52:31 PM
 */
//public class IsWebFormElementOrSubElement implements IFunction<String, Boolean> {
//
//	private final Map<String, Boolean> map = new HashMap<String, Boolean>(10000);
//	
//	@Override
//	public Boolean apply(final String elName) {
//		Boolean vis = map.get(elName);
//		if (vis == null) {
////			vis = HTMLUtils.isWebFormElementOrSubElement(avar);
//			vis =  EHtmlElementConstants.BUTTON.string().equalsIgnoreCase(elName)
//					|| EHtmlElementConstants.INPUT.string().equalsIgnoreCase(elName)
////					|| EHtmlElementConstants.SELECT.string().equalsIgnoreCase(elName)
//					|| EHtmlElementConstants.OPTION.string().equalsIgnoreCase(elName)
//					|| EHtmlElementConstants.TEXTAREA.string().equalsIgnoreCase(elName);
//			
//			map.put(elName, vis);
//		}
//		return vis;
//	}
//	
//}

/**
 * Functor which check if it is potentially aloud to go
 * to children of web form elements in {@linkplain DOMWalker}. 
 */
public class IsWebFormElementOrSubElement extends FunctionWithMemory<String, Boolean> {
	public IsWebFormElementOrSubElement() {
		super( new IFunction<String, Boolean>() {
			@Override public Boolean apply(String elName) {
				return
//						EHtmlElementConstants.BUTTON.string().equalsIgnoreCase(elName)
				EHtmlElementConstants.INPUT.string().equalsIgnoreCase(elName)
//				|| EHtmlElementConstants.SELECT.string().equalsIgnoreCase(elName)
				|| EHtmlElementConstants.OPTION.string().equalsIgnoreCase(elName)
				|| EHtmlElementConstants.TEXTAREA.string().equalsIgnoreCase(elName);
		} } );
	}
}