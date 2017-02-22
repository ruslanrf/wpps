/**
 * 
 */
package tuwien.dbai.wpps.objident.model;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;

import tuwien.dbai.wpps.common.UniqueIDGenerator;
import tuwien.dbai.wpps.common.event.WPPSPropertyChangeEvent;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.model.RectangularArea.Event.EventTypes;

import com.google.common.collect.Sets;
import com.google.common.eventbus.EventBus;
import com.google.inject.Injector;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 14, 2012 7:43:59 PM
 */
public abstract class RectangularArea {
	private static final UniqueIDGenerator newID = new UniqueIDGenerator();
	
//	private static final Logger log = Logger.getLogger(RectangularArea.class);
	
	protected final EventBus eventBus;
	public EventBus getEventBus() {
		return eventBus;
	}
	
	/**
	 * Can be used to get data regarding this object. E.g. WPPS Framework
	 */
	@SuppressWarnings("unused")
	private final Injector inj;
	
	protected final BrowserRelatedModel browserRelatedModel;
	public BrowserRelatedModel getBrowserRelatedModel() {
		return browserRelatedModel;
	}
	
	protected RectangularArea(
			BrowserRelatedModel browserRelatedModel
			, Rectangle2D area, Set<IInstanceAdp> containedObjects, EventBus eventBus) {
		this.browserRelatedModel = browserRelatedModel;
		this.containedObjects = containedObjects;
		this.area = area;
		this.eventBus = eventBus;
		this.id = newID.genID();
		this.inj = browserRelatedModel.getFrameworkDependentInj();
	}
	
	public static class Event extends WPPSPropertyChangeEvent<EventTypes> {
		/**
		 * Source: {@link RectangularArea}
		 * Data:
		 * <ol>
		 * <li>old value ({@linkplain FeatureValue}).</li>
		 * <li>new value ({@linkplain FeatureValue}).</li>
		 * </ol>
		 */
		public static enum EventTypes {
			SET_COMPUTED_FEATURE_VALUE_LIST,
			
			/**
			 * <ol>
			 * <li>modified set of values ({@linkplain Set<FeatureValue>}).</li>
			 * </ol>
			 */
			ADD_COMPUTED_FEATURE_VALUE
			
		}
		protected Event(EventTypes en, final Object source, final Object oldVal, final Object newVal, final Object[] data) {
			super(en, source, null, oldVal, newVal, data);
		}
		public static Event getInstance(EventTypes event, final RectangularArea source, final Object oldVal, final Object newVal, final Object... data) {
			return new Event(event, source, oldVal, newVal, data);
		}
	}
	
	private final int id;
	public final int getId() {
		return id;
	}
	
	protected final Set<IInstanceAdp> containedObjects;
	
	protected final Rectangle2D area;
	
	@SuppressWarnings("unchecked")
	private Set<FeatureValue> computedFeatureValueList = new ListOrderedSet();
	public Set<FeatureValue> getComputedFeatureValues() {
		return computedFeatureValueList;
	}

	public void addComputedFeatureValue2(final FeatureValue fv) {
		computedFeatureValueList.add(fv);
		eventBus.post( Event.getInstance(EventTypes.ADD_COMPUTED_FEATURE_VALUE
						, RectangularArea.this, null, fv, computedFeatureValueList) );
	}
	
	/**
	 * @param computedFeatureValueList can be null
	 */
	public void setComputedFeatureValueList(final Collection<FeatureValue> computedFeatureValueList) {
		@SuppressWarnings("unchecked")
		Set<FeatureValue> computedFeatureValueListTmp = new ListOrderedSet();
		if (computedFeatureValueList != null)
			computedFeatureValueListTmp.addAll(computedFeatureValueList);
		this.computedFeatureValueList = computedFeatureValueListTmp;
	}
	public void setComputedFeatureValueList2(final Collection<FeatureValue> computedFeatureValueList) {
		@SuppressWarnings("unchecked")
		Set<FeatureValue> computedFeatureValueListTmp = new ListOrderedSet();
		if (computedFeatureValueList != null)
			computedFeatureValueListTmp.addAll(computedFeatureValueList);
		eventBus.post(
				Event.getInstance(EventTypes.SET_COMPUTED_FEATURE_VALUE_LIST
						, RectangularArea.this
						, this.computedFeatureValueList
						, this.computedFeatureValueList = computedFeatureValueListTmp)
				);
	}
	
	/**
	 * Do not change this value!
	 * @return
	 */
	public Rectangle2D getArea() {
		return area;
	}
	
	public Set<IInstanceAdp> getContainedObjects() {
		return containedObjects;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RectangularArea) {
			RectangularArea a = (RectangularArea)obj;
			return area.equals(a.area) 
					&& Sets.intersection(containedObjects, a.containedObjects).size() == containedObjects.size();
		}
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return area.hashCode();
	}
	
	

}
