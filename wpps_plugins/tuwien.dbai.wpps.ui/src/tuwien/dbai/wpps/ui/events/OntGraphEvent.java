/**
 * 
 */
package tuwien.dbai.wpps.ui.events;

import toxi.color.TColor;
import tuwien.dbai.wpps.common.event.WPPSEvent;
import tuwien.dbai.wpps.core.methods.WPUMethodState;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.commandhandlers.ontgraph.SetOntGraphLayoutCmdHdlr;
import tuwien.dbai.wpps.ui.events.OntGraphEvent.EventTypes;
import tuwien.dbai.wpps.ui.views.ontgraph.Node;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphView;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphView.ELayout;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 4, 2012 2:51:28 AM
 */
public class OntGraphEvent extends WPPSEvent<EventTypes> {

	public static enum EventTypes {
		/**
		 * Select resource, e.g. in {@linkplain OntologiesGraphView}.
		 * 
		 * Source: {@linkplain OntologiesGraphView}
		 * Data:
		 * <ol>
		 * <li>Node ({@linkplain Node}).</li>
		 * <li>Color ({@linkplain TColor}).</li>
		 * <li>browser editor ({@linkplain EMBrowserEditor}).</li>
		 * <li>current state of browser editor ({@linkplain WPUMethodState})</li>
		 * </ol>
		 */
		INDIVIDUAL_IS_SELECTED,
		
		/**
		 * Source: {@linkplain SetOntGraphLayoutCmdHdlr}
		 * DataArr:
		 * <ol>
		 * <li>ELayout ({@linkplain ELayout})</li>
		 * </ol>
		 */
		SET_GRAPH_LAYOUT,
		
	}
	
	protected OntGraphEvent(EventTypes en, Object source, Object[] targets,
			Object[] data) {
		super(en, source, targets, data);
	}
	
	public static OntGraphEvent getInstance(EventTypes event, final Object source, final Object[] data) {
		return new OntGraphEvent(event, source, null, data);
	}
	
	public static OntGraphEvent getInstance(EventTypes event, final Object source
			, final Object[] targets, final Object[] data) {
		return new OntGraphEvent(event, source, targets, data);
	}

}
