/**
 * 
 */
package tuwien.dbai.wpps.objident.ui.events;

import tuwien.dbai.wpps.common.event.WPPSEvent;
import tuwien.dbai.wpps.objident.ui.events.MainEvents.EventTypes;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 1, 2012 10:56:14 PM
 */
public class MainEvents extends WPPSEvent<EventTypes> {

	public static enum EventTypes {
		
//		/**
//		 * Source: {@linkplain EMBrowserManager}
//		 * Data:
//		 * <ol>
//		 * <li>Browser ({@linkplain EMBrowserEditor}).</li>
//		 * <li>Instance adapter selected ({@linkplain TObject}). Can be null.</li>
//		 * </ol>
//		 */
//		@Deprecated
//		TOBJECT_SELECTION,
//		
//		/**
//		 * Source: {@linkplain EMBrowserManager}
//		 * Data:
//		 * <ol>
//		 * <li>Browser ({@linkplain EMBrowserEditor}).</li>
//		 * <li>Target object selected ({@linkplain TObject}).</li>
//		 * <li>List of values (List&lt;{@linkplain FeatureValue}&gt;).</li>
//		 * </ol>
//		 */
//		@Deprecated
//		FEATURES_COMPUTED,
//		
//		/**
//		 * Source: {@linkplain AddExampleCmdHdlr}
//		 * Data:
//		 * <ol>
//		 * <li>Pair of objects to compare {@linkplain TObjectComparativePair}</li>
//		 * </ol>
//		 */
//		@Deprecated
//		NEW_COMPUTED_OBJECTS_FOR_COMPARISON
	}
	
	protected MainEvents(final EventTypes e, final Object source
			, final Object[] targets, final Object[] data) {
		super(e, source, targets, data);
	}
	
	public static MainEvents getInstance(EventTypes event, final Object source, final Object[] data) {
		return new MainEvents(event, source, null, data);
	}
	
	public static MainEvents getInstance(EventTypes event, final Object source
			, final Object[] targets, final Object[] data) {
		return new MainEvents(event, source, targets, data);
	}
	
}
