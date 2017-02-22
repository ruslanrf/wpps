/**
 * 
 */
package tuwien.dbai.wpps.objident.model.transportSearchExperiment;

import tuwien.dbai.wpps.objident.model.TObject;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 14, 2012 8:09:32 PM
 */
public class TObjectExtended {
	
	public TObjectExtended(TObject tObject
			, EScenario scenario
			, String webPageId
			, EWebFormAttributeType webFormElement) {
		this.tObject = tObject;
		this.scenario = scenario;
		this.webPageId = webPageId;
		this.webFormElement = webFormElement;
	}
	
	public TObject gettObject() {
		return tObject;
	}

	public void settObject(TObject tObject) {
		this.tObject = tObject;
	}

	public void setScenario(EScenario scenario) {
		this.scenario = scenario;
	}

	public void setWebPageId(String webPageId) {
		this.webPageId = webPageId;
	}

	public void setWebFormElement(EWebFormAttributeType webFormElement) {
		this.webFormElement = webFormElement;
	}

	public EScenario getScenario() {
		return scenario;
	}

	public String getWebPageId() {
		return webPageId;
	}

	public EWebFormAttributeType getWebFormElement() {
		return webFormElement;
	}

	private TObject tObject;
	private EScenario scenario;
	private String webPageId;
	private EWebFormAttributeType webFormElement;
	

//	TObjectExtended(BrowserRelatedModel browserRelatedModel, Rectangle2D area,
//			Set<IInstanceAdp> contained, IWebPageBlock webPage, EventBus eventBus
//			, EScenario scenario, String webPageId, EWebFormAttributeType webFormElement) {
//		super(browserRelatedModel, area, contained, webPage, eventBus);
//		this.scenario = scenario;
//		this.webPageId = webPageId;
//		this.webFormElement = webFormElement;
//	}
	
//	public static TObjectExtended createInstance(TObject o
//			, EScenario scenario, String webPageId, EWebFormAttributeType webFormElement) {
//		return new TObjectExtended(o.getBrowserRelatedModel()
//				, o.getArea()
//				, o.getContainedObjects()
//				, o.getWebPage()
//				, o.getEventBus()
//				, scenario
//				, webPageId
//				, webFormElement);
//	}

}
