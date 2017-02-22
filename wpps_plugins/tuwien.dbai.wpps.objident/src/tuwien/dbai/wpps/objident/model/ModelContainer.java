/**
 * 
 */
package tuwien.dbai.wpps.objident.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.list.SetUniqueList;
import org.eclipse.swt.graphics.RGB;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.common.event.WPPSPropertyChangeEvent;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.objident.ObjIdentActivator;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair;
import tuwien.dbai.wpps.objident.model.ModelContainer.Event.EventTypes;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 2, 2012 6:21:05 PM
 */
public class ModelContainer {
	
//	private final Injector sessionInjector;
	
	private final RGB maserBrowserColor;
	public RGB getMaserBrowserColor() {
		return maserBrowserColor;
	}

	private final RGB secondBrowserColor;
	public RGB getSecondBrowserColor() {
		return secondBrowserColor;
	}
	
	private final EventBus eventBus;
	
	private Map<EMBrowserEditor, BrowserRelatedModel> browserRelatedModelMap
		= new HashMap<EMBrowserEditor, BrowserRelatedModel>();
//	public Collection<BrowserRelatedModel> getBrowserRelatedModelCol() {
//		return browserRelatedModelMap.values();
//	}
	public BrowserRelatedModel getBrowserRelatedModel(EMBrowserEditor browser) {
		return browserRelatedModelMap.get(browser);
	}
	public BrowserRelatedModel getBrowserRelatedModel(BrowserRelatedModel.EBrowserType browserType) {
		Iterator<BrowserRelatedModel> iter = browserRelatedModelMap.values().iterator();
		while (iter.hasNext()) {
			BrowserRelatedModel tmp = iter.next();
			if (tmp.getBrowserType() == browserType)
				return tmp;
		}
		throw new GeneralUncheckedException("Web browser editor of type "+browserType+" has not been found.");
	}
	public void addBrowserRelatedModel(BrowserRelatedModel browserRelatedModel) {
		browserRelatedModelMap.put(browserRelatedModel.getBrowserEditor(), browserRelatedModel);
	}
	
	private List<TObjectComparativePair> comparationList
		= new LinkedList<TObjectComparativePair>();
	public void addComparativePair2(TObjectComparativePair pair) {
		Preconditions.checkArgument(pair.getMasterObj() != null && pair.getComparativeObj() != null);
		comparationList.add(pair);
		eventBus.post(
				Event.getInstance(EventTypes.ADD_COMPARATIVE_PAIR
						, ModelContainer.this
						, null
						, pair)
				);
	}
	/**
	 * @param comparationList can be null
	 */
	public void setComparativePairs2(Collection<TObjectComparativePair> comparationList) {
		List<TObjectComparativePair> computedFeatureValueListTmp
			= new LinkedList<TObjectComparativePair>();
		if (comparationList != null)
			computedFeatureValueListTmp.addAll(comparationList);
		eventBus.post(
				Event.getInstance(EventTypes.SET_COMPARATIVE_PAIRS
						, ModelContainer.this
						, this.comparationList
						, this.comparationList = computedFeatureValueListTmp)
				);		
	}
	
//	public void setComputedFeatureValueList2(final Collection<FeatureValue> computedFeatureValueList) {
//		@SuppressWarnings("unchecked")
//		Set<FeatureValue> computedFeatureValueListTmp = new ListOrderedSet();
//		if (computedFeatureValueList != null)
//			computedFeatureValueListTmp.addAll(computedFeatureValueList);
//		eventBus.post(
//				Event.getInstance(EventTypes.SET_COMPUTED_FEATURE_VALUE_LIST
//						, RectangularArea.this
//						, this.computedFeatureValueList
//						, this.computedFeatureValueList = computedFeatureValueListTmp)
//				);
//	}
	public List<TObjectComparativePair> getComparationList() {
		return comparationList;
	}
	
//	public static enum EComparationObjectSelectionMode {
//		POSITIVE, NEGATIVE
//	}
	private TObjectComparativePair.EExampleType comparationObjectSelectionMode;
	public TObjectComparativePair.EExampleType getComparationObjectSelectionMode() {
		return comparationObjectSelectionMode;
	}
	public void setComparationObjectSelectionMode(
			TObjectComparativePair.EExampleType comparationObjectSelectionMode) {
		this.comparationObjectSelectionMode = comparationObjectSelectionMode;
	}
	ModelContainer(//Injector sessionInjector,
//			EventBus eventBus
			) {
		ObjidentConfig config = UIUtils.getService(ObjidentConfig.class);
		maserBrowserColor = config.getMaserBrowserColor();
		secondBrowserColor = config.getSecondBrowserColor(); // Fuchsia (Crayola) (193, 84, 193)
		comparationObjectSelectionMode = config.getComparationObjectSelectionMode();
		this.eventBus = UIUtils.getService(ObjIdentActivator.class, EventBus.class);
//		this.sessionInjector = sessionInjector;
	}
	
	public static class Event extends WPPSPropertyChangeEvent<EventTypes> {
		public static enum EventTypes {
			/**
			 * Source: {@link ModelContainer}
			 * Data:
			 * <ol>
			 * <li>old value ({@linkplain TObject}).</li>
			 * <li>new value ({@linkplain TObject}).</li>
			 * </ol>
			 */
			ADD_COMPARATIVE_PAIR,
			/**
			 * <ol>
			 * <li>old value (List<{@linkplain TObjectComparativePair}>).</li>
			 * <li>new value (List<{@linkplain TObjectComparativePair}>).</li>
			 * </ol>
			 */
			SET_COMPARATIVE_PAIRS
			
		}
		protected Event(EventTypes en, final Object source, final Object oldVal, final Object newVal) {
			super(en, source, null, oldVal, newVal);
		}
		public static Event getInstance(EventTypes event, final ModelContainer source, final Object oldVal, final Object newVal) {
			return new Event(event, source, oldVal, newVal);
		}
	}
	
}
