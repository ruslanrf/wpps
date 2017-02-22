/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.Collection;

import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EWebFormMethod;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 4, 2012 2:12:13 PM
 */
public interface IHtmlWebForm extends IHtmlElement {
	
	Collection<IHtmlWebFormElement> getElements();
	
	EWebFormMethod getMethod();
	
	String getAction();
	
}
