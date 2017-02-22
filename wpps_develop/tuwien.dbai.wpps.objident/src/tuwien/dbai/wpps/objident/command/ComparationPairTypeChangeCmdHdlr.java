package tuwien.dbai.wpps.objident.command;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.objident.ObjIdentSessionController;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair;
import tuwien.dbai.wpps.objident.model.ModelContainer;

public class ComparationPairTypeChangeCmdHdlr implements IHandler {
	
	public static final String POSITIVE_EXAMPLE_STATE = "positive";
	public static final String NEGATIVE_EXAMPLE_STATE = "negative";

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if(HandlerUtil.matchesRadioState(event))
	        return null; // we are already in the updated state - do nothing
		
		ModelContainer modelContainer = UIUtils.getService(ObjIdentSessionController.class).getModelContainer();
	    String currentState = event.getParameter(RadioState.PARAMETER_ID);
	    // perform task for current state
	    if(currentState.equals(POSITIVE_EXAMPLE_STATE))
	    	modelContainer.setComparationObjectSelectionMode(TObjectComparativePair.EExampleType.POSITIVE);
	    else if(currentState.equals(NEGATIVE_EXAMPLE_STATE))
	    	modelContainer.setComparationObjectSelectionMode(TObjectComparativePair.EExampleType.NEGATIVE);

	    // and finally update the current state
	    HandlerUtil.updateRadioState(event.getCommand(), currentState);
	    
	    
	    
//	    ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
//	    if (commandService != null) {
////	    	commandService.getCommand("tuwien.dbai.wpps.objident.commands.exampleType").getState("aaa").setValue("xxx");
//			commandService.refreshElements("tuwien.dbai.wpps.objident.commands.exampleType", null);
//		}

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
		// TODO Auto-generated method stub

	}
	
//	@Override
//	public void updateElement(UIElement element, Map parameters) {
////		ICommandService commandService = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
////	    if (commandService != null) {
////	    	State state = commandService.getCommand(NegativeExampleTypeCmdHdlr.ID)
////	    		.getState("org.eclipse.ui.commands.toggleState");
////	    	System.out.println(state.getValue());
////		}
//	}

}
