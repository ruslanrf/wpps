package tuwien.dbai.wpps.ui.commandhandlers.ontgraph;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.ui.WPPSUISessionController;
import tuwien.dbai.wpps.ui.events.OntGraphEvent;
import tuwien.dbai.wpps.ui.views.ontgraph.OntologiesGraphView;

import com.google.common.eventbus.EventBus;

public class SetOntGraphLayoutCmdHdlr implements IHandler {

	public static final String DG_R = "DG_R";
	public static final String DG_R_HS = "DG_R_HS";
	public static final String TREE = "Tree";
	public static final String DIRECT = "Direct";
	public static final String SPRING = "Spring";
	public static final String RADIAL = "Radial";
	public static final String GRID = "Grid";
	
	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if(HandlerUtil.matchesRadioState(event))
	        return null; // we are already in the updated state - do nothing
		EventBus eventBus = UIUtils.getService(WPPSUISessionController.class).getEventBus();
		
		String currentState = event.getParameter(RadioState.PARAMETER_ID);
		
		if (currentState.equals(DG_R))
			eventBus.post(OntGraphEvent.getInstance(OntGraphEvent.EventTypes.SET_GRAPH_LAYOUT
					, SetOntGraphLayoutCmdHdlr.this
					, new Object[]{OntologiesGraphView.ELayout.DIRECT_GRAPH_A_RADIAL}));
		else if (currentState.equals(DG_R_HS))
			eventBus.post(OntGraphEvent.getInstance(OntGraphEvent.EventTypes.SET_GRAPH_LAYOUT
					, SetOntGraphLayoutCmdHdlr.this
					, new Object[]{OntologiesGraphView.ELayout.DIRECT_GRAPH_A_RADIAL_A_HOR_SHIFT}));
		else if (currentState.equals(TREE))
			eventBus.post(OntGraphEvent.getInstance(OntGraphEvent.EventTypes.SET_GRAPH_LAYOUT
					, SetOntGraphLayoutCmdHdlr.this
					, new Object[]{OntologiesGraphView.ELayout.TREE}));
		else if (currentState.equals(DIRECT))
			eventBus.post(OntGraphEvent.getInstance(OntGraphEvent.EventTypes.SET_GRAPH_LAYOUT
					, SetOntGraphLayoutCmdHdlr.this
					, new Object[]{OntologiesGraphView.ELayout.DIRECT}));
		else if (currentState.equals(SPRING))
			eventBus.post(OntGraphEvent.getInstance(OntGraphEvent.EventTypes.SET_GRAPH_LAYOUT
					, SetOntGraphLayoutCmdHdlr.this
					, new Object[]{OntologiesGraphView.ELayout.SPRING}));
		else if (currentState.equals(RADIAL))
			eventBus.post(OntGraphEvent.getInstance(OntGraphEvent.EventTypes.SET_GRAPH_LAYOUT
					, SetOntGraphLayoutCmdHdlr.this
					, new Object[]{OntologiesGraphView.ELayout.RADIAL}));
		else if (currentState.equals(GRID))
			eventBus.post(OntGraphEvent.getInstance(OntGraphEvent.EventTypes.SET_GRAPH_LAYOUT
					, SetOntGraphLayoutCmdHdlr.this
					, new Object[]{OntologiesGraphView.ELayout.GRID}));

		// and finally update the current state
	    HandlerUtil.updateRadioState(event.getCommand(), currentState);
	    
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}

}
