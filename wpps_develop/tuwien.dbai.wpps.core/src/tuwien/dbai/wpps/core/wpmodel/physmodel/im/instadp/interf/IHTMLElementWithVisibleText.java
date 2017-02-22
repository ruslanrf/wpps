/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EFontStyle;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.ETextDecoration;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 5, 2012 2:20:07 PM
 */
public interface IHTMLElementWithVisibleText {

	String getText();
	
	List<String> getFontFamilyList();
	
	Float getFontWeight();
	
	EFontStyle getFontStyle();
	
	ETextDecoration getTextDecoration();
	
	Float getFontSize();
	
}
