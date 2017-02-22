/**
 * 
 */
package tuwien.dbai.wpps.common;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 19, 2012 4:13:26 PM
 */
public class UniqueIDGenerator {

	private int newID = 0;
	
	private final int incNewID() {
		return newID++;
	}
	
	/**
	* Returns a new unique string for current execution
	*/
	public final String genHexID() {
		final StringBuffer buff = new StringBuffer(Integer.toHexString(incNewID()).toUpperCase());
		return buff.toString();
	}
	
	/**
	* Returns a new unique id for current execution
	*/
	public final int genID() {
		return incNewID();
	}
}
