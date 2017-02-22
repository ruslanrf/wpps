/**
 * 
 */
package tuwien.dbai.wpps.objident.model;

import java.util.HashSet;
import java.util.Set;

import com.google.common.eventbus.EventBus;

import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.objident.WPPSFrameworkDependentModule;

/**
 * Target object's context.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 1, 2012 4:54:48 PM
 * @see WPPSFrameworkDependentModule
 */
public class TObjectContext extends RectangularArea {

	private final TObject target;

	public TObject getTarget() {
		return target;
	}
	
	private final Set<IInstanceAdp> targetContextObjects;
	
	public Set<IInstanceAdp> getTargetContextObjects() {
		return targetContextObjects;
	}
	
	public TObjectContext(BrowserRelatedModel browserRelatedModel
			, TObject target, Rectangle2D area, Set<IInstanceAdp> containedObjects, EventBus eventBus) {
		super(browserRelatedModel, area, containedObjects, eventBus);
		this.target = target;
		targetContextObjects = new HashSet<IInstanceAdp>(containedObjects.size());
		targetContextObjects.addAll(containedObjects);
		targetContextObjects.removeAll(target.getContainedObjects());
	}


//	private final IInstanceAdp target;
//	

//	
//	private final Rectangle2D area;
//	
//	private final Set<IInstanceAdp> elements;
//	
//	public Rectangle2D getArea() {
//		return area;
//	}
//	public Set<IInstanceAdp> getElements() {
//		return elements;
//	}
//	
//	public TObjectContext(final IInstanceAdp target, final Rectangle2D area, final Set<IInstanceAdp> elements) {
//		this.target = target;
//		this.area = area;
//		this.elements = Collections.unmodifiableSet(elements);
//	}
//	
////	public IContext(final Rectangle area, final Set<? extends IInstanceAdp> elements) {
////		this.area = area;
////		this.elements = elements;
////	}
	
}
