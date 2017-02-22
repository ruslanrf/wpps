/**
 * 
 */
package tuwien.dbai.wpps.objident.model;

import java.util.HashSet;
import java.util.Set;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;

import com.google.common.eventbus.EventBus;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 14, 2012 7:30:50 PM
 * @see TObjectFactory
 */
public class TObject extends RectangularArea {

	private final IWebPageBlock webPage;
	
	/**
	 * Use Factory.
	 * @param browserRelatedModel
	 * @param area
	 * @param contained
	 * @param eventBus
	 */
	public TObject(BrowserRelatedModel browserRelatedModel
			, Rectangle2D area, IInstanceAdp contained, EventBus eventBus) {
		super(browserRelatedModel, area, new HashSet<IInstanceAdp>(), eventBus);
		super.containedObjects.add(contained);
		this.webPage = contained.as(IBox.class).getWebPage();
	}
	
	/**
	 * Use Factory.
	 * @param containedObjects
	 */
	public TObject(BrowserRelatedModel browserRelatedModel
			, Rectangle2D area, Set<IInstanceAdp> contained, IWebPageBlock webPage, EventBus eventBus) {
		super(browserRelatedModel, area, contained, eventBus);
		this.webPage = webPage;
	}
	
	public IInstanceAdp getRdfTargetObject() {
		return containedObjects.iterator().next();
	}
	
	public IWebPageBlock getWebPage() {
		return webPage;
	}
	
}
