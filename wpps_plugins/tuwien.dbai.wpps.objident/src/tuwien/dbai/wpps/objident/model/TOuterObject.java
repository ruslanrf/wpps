/**
 * 
 */
package tuwien.dbai.wpps.objident.model;

import java.util.Set;

import com.google.common.eventbus.EventBus;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;

/**
 * It can be web document, viewport, web page.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 15, 2012 7:25:27 AM
 * @see TOuterObject
 */
public class TOuterObject extends RectangularArea {

	private final IInstanceAdp rdfObjAdp;
	public IInstanceAdp getRdfObjAdp() {
		return rdfObjAdp;
	}

	/**
	 * @param area
	 * @param containedObjects
	 */
	protected TOuterObject(BrowserRelatedModel browserRelatedModel
			, IInstanceAdp rdfObjAdp, Rectangle2D area, Set<IInstanceAdp> containedObjects, EventBus eventBus) {
		super(browserRelatedModel, area, containedObjects, eventBus);
		this.rdfObjAdp = rdfObjAdp;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TOuterObject) {
			TOuterObject o = (TOuterObject)obj;
			return rdfObjAdp.equals(o.rdfObjAdp) && super.equals(o);
		}
		return super.equals(obj);
	}

}
