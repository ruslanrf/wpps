/**
 * 
 */
package tuwien.dbai.wpps.core.methods;

import tuwien.dbai.wpps.core.ie.api.basis.IEAPI;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Apr 26, 2012 10:34:58 PM
 */
public abstract class AWPUEnricher extends AWPUMethod {

	private IEAPI ieapi;
	
	/**
	 * @param description
	 */
	public AWPUEnricher(final AWPUMethodDescription description) {
		super(description);
	}
	
	public final void init(final WPUMethodState state, final IEAPI ieapi) {
		super.init();
		getStates().add(state);
		state.addAppliedMethod(this);
		this.ieapi = ieapi;
	}
	
	public WPUMethodState getState() {
		return getStates().getFinalState();
	}

	public IEAPI getIEAPI() {
		return ieapi;
	}

}
