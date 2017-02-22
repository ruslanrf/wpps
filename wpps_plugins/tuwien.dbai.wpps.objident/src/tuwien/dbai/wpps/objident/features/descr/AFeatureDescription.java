/**
 * 
 */
package tuwien.dbai.wpps.objident.features.descr;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.objident.features.EFeatureDependency;
import tuwien.dbai.wpps.objident.model.EConsideredObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created May 31, 2012 9:11:56 PM
 */
public abstract class AFeatureDescription {
	private static final Logger log = Logger.getLogger(AFeatureDescription.class);
	
	private final String id;
	
	public String getId() {
		return id;
	}
	
	private final String name;
	public String getName() {
		return name;
	}
	
	private final String sysName;
	public String getSysName() {
		return sysName;
	}
	
	private final EFeatureDependency featureDependency;
	
	public EFeatureDependency getFeatureDependency() {
		return featureDependency;
	}
	
	private final EConsideredObject primaryObjectType;
	
	public EConsideredObject getPrimaryObjectType() {
		return primaryObjectType;
	}
	
	/**
	 * can be null
	 */
	private final EConsideredObject referenceObjectType;
	
	/**
	 * @return can be null
	 */
	public EConsideredObject getReferenceObjectType() {
		return referenceObjectType;
	}
	
	private final Class<?> valueType;
	public Class<?> getValueType() {
		return valueType;
	}
	
	/**
	 * @param Id
	 * @param name
	 * @param featureDependency
	 * @param primaryObjectType
	 * @param referenceObjectType can be null
	 * @param valueType
	 */
	protected AFeatureDescription(final String Id
			, final String name
			, final String sysName
			, final EFeatureDependency featureDependency
			, final EConsideredObject primaryObjectType
			, final EConsideredObject referenceObjectType
			, final Class<?> valueType) {
		this.id = Id;
		this.name = name;
		this.sysName = sysName;
		this.featureDependency = featureDependency;
		this.primaryObjectType = primaryObjectType;
		this.referenceObjectType = referenceObjectType;
		this.valueType = valueType;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof AFeatureDescription) {
if (log.isDebugEnabled()) {
	if (id.equals(((AFeatureDescription)o).id)
			&& !sysName.equals(((AFeatureDescription)o).sysName))
		log.warn("2 different features ("+sysName+", "+((AFeatureDescription)o).sysName
				+") have the same id: "+id);
}
			return id.equals(((AFeatureDescription)o).id);
		}
		else return super.equals(o);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
