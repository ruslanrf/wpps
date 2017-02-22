/**
 * 
 */
package tuwien.dbai.wpps.core.methods;

import java.util.Collection;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 22, 2012 2:11:38 AM
 */
public class WPUMethodsExecutionResult {
	private static final Logger log = Logger.getLogger(WPUMethodsExecutionResult.class);

	private final Collection<AWPUMethod> methods;
	
	private WPUMethodStates states = null;
	
	WPUMethodsExecutionResult(final Collection<AWPUMethod> methods) {
		this.methods = methods;
	}
	//TODO: must be a list
	public Collection<AWPUMethod> getMethods() {
		return methods;
	}
	
	public WPUMethodStates getStates() {
		if (states == null) {
			states = new WPUMethodStates();
			for (AWPUMethod m : methods) {
				if (!states.isEmpty()) {
					if (!states.getLast().equals(m.getStates().getFinalState()))
						throw new GeneralUncheckedException(log, "First and last states of consequtive methods must be equal");
					states.removeLast();
				}
				states.addAll(m.getStates());
			}
		}
		return states;
	}
	
	
	
}
