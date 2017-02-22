/**
 * 
 */
package tuwien.dbai.wpps.core.methods;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 30, 2012 10:52:47 PM
 */
public abstract class AWPUMethodDescription {
	
	public static enum EMethodType {WRAPPER, ENRICHER, SPIDER}
	
	private final String uuid;
	private final EMethodType mType;
	private final String majorName;
	private final String minorName;
	private final String description;
	
//	private AMethod implementation = null;
	
	public AWPUMethodDescription(String uuid
			, EMethodType mType
			, String majorName, String minorName, String description) {
		this.uuid = uuid;
		this.mType = mType;
		this.majorName = majorName;
		this.minorName = minorName;
		this.description = description;
	}

	public String getId() {
		return uuid;
	}

	public String getMajorName() {
		return majorName;
	}

	public String getMinorName() {
		return minorName;
	}

	public String getDescription() {
		return description;
	}

	public EMethodType getmType() {
		return mType;
	}
	
//	public void setImplementation(AMethod implementation) {
//		this.implementation = implementation;
//	}
//	
//	public AMethod getImplementation() {
//		return implementation;
//	}
//	
//	public boolean hasImplementation() {
//		return (implementation != null);
//	}
	
}
