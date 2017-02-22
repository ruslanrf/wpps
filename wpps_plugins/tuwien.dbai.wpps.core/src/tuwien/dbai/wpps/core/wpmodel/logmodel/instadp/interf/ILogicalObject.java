/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf;

import tuwien.dbai.wpps.core.wpmodel.instadp.IModifiable;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 1:29:04 PM
 */
public interface ILogicalObject extends IInstanceAdp, IModifiable {
	
	/**
	 * Human readable name of the element.
	 * @param label
	 */
	void addLabel(String label);
	
	String getLabel();
	
	/**
	 * Add string which represent this element.
	 * @param str
	 */
	void addStringContent(String str);
	
	String getStringContent();
	
	/**
	 * String which can be used to represent element in xml.
	 * should be called once.
	 * @param tag
	 */
	void addTag(String tag);
	
	String getTag();
	
	void addExternalType(String uri);
	
	boolean hasExternalType(String uri);
	
	void addRelation(String uri, ILogicalObject obj);
	
	void addAttribute(String uri, Number val);
	
	void addAttribute(String uri, char val);
	
	void addAttribute(String uri, boolean val);
	
}
