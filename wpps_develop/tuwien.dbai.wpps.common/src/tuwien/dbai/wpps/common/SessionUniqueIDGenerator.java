package tuwien.dbai.wpps.common;


/**
 * Generate ID unique for the session. Thread safe class.
 *
 * @created: 9:42:51 PM Feb 5, 2010
 * @author Ruslan (ruslanrf@gmail.com)
 *
 */
public final class SessionUniqueIDGenerator {
	
	private static int newID = 0;
	
	private synchronized final static int incNewID() {
		return newID++;
	}
	
	/**
	* Returns a new unique string for current execution
	*/
	public static final String genHexID() {
		final StringBuffer buff = new StringBuffer(Integer.toHexString(incNewID()).toUpperCase());
		return buff.toString();
	}
	
	/**
	* Returns a new unique id for current execution
	*/
	public static final int genID() {
		return incNewID();
	}
	
}
