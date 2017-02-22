/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf;

import java.util.Collection;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 7, 2012 7:08:16 PM
 */
public interface IHtmlLink extends IHtmlElement {

	String getUrl();
	
	Collection<IHtmlElement> getContent();
	
}
