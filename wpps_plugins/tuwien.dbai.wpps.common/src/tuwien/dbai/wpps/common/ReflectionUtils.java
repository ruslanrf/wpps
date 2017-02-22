/**
 * File name: ReflectionUtils.java
 * @created: May 2, 2011 9:08:46 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common;

/**
 * @type: ReflectionUtils
 *
 * @created: May 2, 2011 9:08:46 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public final class ReflectionUtils {

	/**
	 * Check if clazz2 is a interface of clazz2, checking all hierarchy of inheritance of interfaces.
	 * @param clazz1
	 * @param clazz2
	 * @return
	 */
	public static final boolean hasInterface(final Class<?> clazz1, final Class<?> clazz2) {
		if (clazz1.equals(clazz2))
			return true;
		
		final Class<?>[] interArr = clazz1.getInterfaces();
		if (interArr.length > 0) {
			for (int i=0; i < interArr.length; i++)
				if (hasInterface(interArr[i], clazz2))
					return true;
		}
		return false;
	}
	
}
