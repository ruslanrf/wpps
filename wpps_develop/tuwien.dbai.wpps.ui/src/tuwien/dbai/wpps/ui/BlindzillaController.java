/**
 * 
 */
package tuwien.dbai.wpps.ui;

import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.guava.IHasEventBus;

import com.google.common.eventbus.EventBus;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created May 23, 2013 5:45:26 PM
 */
public class BlindzillaController implements IHasEventBus {

	private boolean mode = false;
	public boolean isMode() {
		return mode;
	}
	public void setMode(boolean mode) {
		this.mode = mode;
	}
	
	private final EventBus eventBus;
	@Override
	public EventBus getEventBus() {
		return null;
	}

	public BlindzillaController() {
		this.eventBus = UIUtils.getService(WPPSUISessionController.class).getEventBus();
	}

}
