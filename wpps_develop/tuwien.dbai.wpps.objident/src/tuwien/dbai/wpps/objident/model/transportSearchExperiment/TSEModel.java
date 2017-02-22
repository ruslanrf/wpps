/**
 * 
 */
package tuwien.dbai.wpps.objident.model.transportSearchExperiment;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.list.SetUniqueList;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 14, 2012 11:41:40 PM
 */
public class TSEModel {
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	private EScenario scenario = EScenario.flight;
	
	private EWebFormAttributeType webFormAttrType = EWebFormAttributeType.depLoc;
	
	private String webPageId = "webPageId";
	
	public EScenario getScenario() {
		return scenario;
	}
	public void setScenario(EScenario scenario) {
		propertyChangeSupport.firePropertyChange("scenario"
				, this.scenario, this.scenario = scenario);
	}
	public EWebFormAttributeType getWebFormAttrType() {
		return webFormAttrType;
	}
	public void setWebFormAttrType(EWebFormAttributeType webFormElement) {
		propertyChangeSupport.firePropertyChange("webFormAttrType"
				, this.webFormAttrType, this.webFormAttrType = webFormElement);
	}
	public String getWebPageId() {
		return webPageId;
	}
	public void setWebPageId(String webPageId) {
		propertyChangeSupport.firePropertyChange("webPageId"
				, this.webPageId, this.webPageId = webPageId);
	}
	
	@SuppressWarnings("unchecked")
	private List<TObjectExtended> collectedUniqueTObjects = SetUniqueList.decorate(new LinkedList<TObjectExtended>());
	public void addCollectedTObject(TObjectExtended o) {
		collectedUniqueTObjects.add(o);
	}
	@SuppressWarnings("unchecked")
	public void setCollectedTObject(Collection<TObjectExtended> col) {
		collectedUniqueTObjects = SetUniqueList.decorate(new LinkedList<TObjectExtended>());
		collectedUniqueTObjects.addAll(col);
	}
	public List<TObjectExtended> getCollectedUniqueTObjects() {
		return collectedUniqueTObjects;
	}

	public void addPropertyChangeListener(String propertyName,
		      PropertyChangeListener listener) {
		    propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
		  }
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
	    propertyChangeSupport.removePropertyChangeListener(listener);
	  }
	
}
