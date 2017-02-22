/**
 * 
 */
package tuwien.dbai.wpps.core.methods;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 30, 2012 10:58:46 PM
 */
public class InternalWPUMethodDescription extends AWPUMethodDescription {

	private final String methodClass;
	
	/**
	 * @param id
	 * @param majorName
	 * @param minorName
	 * @param description
	 */
	public InternalWPUMethodDescription(String uuid, EMethodType mType, String majorName, String minorName,
			String description, String methodClass) {
		super(uuid, mType, majorName, minorName, description);
		this.methodClass = methodClass;
	}

	public String getMethodClass() {
		return methodClass;
	}

}
