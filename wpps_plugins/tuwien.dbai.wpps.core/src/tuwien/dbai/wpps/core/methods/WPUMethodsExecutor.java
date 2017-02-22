/**
 * 
 */
package tuwien.dbai.wpps.core.methods;

import java.util.Collection;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.common.exceptions.UnknownType;
import tuwien.dbai.wpps.core.WPPSFramework;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 20, 2012 1:47:37 PM
 */
public class WPUMethodsExecutor {
	private static final Logger log = Logger.getLogger(WPUMethodsExecutor.class);

	private final WPPSFramework framework;
	private final Collection<AWPUMethod> methods;
	
	/**
	 * @param framework
	 * @param methods methods should not be executed. They can be initiated or not. Both is acceptable.
	 */
	public WPUMethodsExecutor(final WPPSFramework framework, Collection<AWPUMethod> methods) {
		this.framework = framework;
		this.methods = methods;
	}
	
	private boolean wasRun = false;
	
	/**
	 * Execute methods.
	 */
	public void run() {
		if (!framework.wasInit())
			framework.init();
		
		for (AWPUMethod m : methods) {
			if (!m.wasInitialized()) {
				if (m instanceof AWPUWrapper)
					((AWPUWrapper)m).init(framework.getLastState(), framework.getIEAPIForLastState());
				else if (m instanceof AWPUEnricher)
						((AWPUEnricher)m).init(framework.getLastState(), framework.getIEAPIForLastState());
				else
					throw new UnknownType(log, m);
			}
			if (m.wasExecuted())
				throw new GeneralUncheckedException(log, "Method "+m+" has been executed already.");
			m.run();
		}
		wasRun = true;
	}
	
	private WPUMethodsExecutionResult exeRes = null;
	
	public WPUMethodsExecutionResult getResult() {
		if (!wasRun)
			throw new GeneralUncheckedException(log, "Executor must be invoked.");
		if (exeRes == null)
			exeRes = new WPUMethodsExecutionResult(methods);
		return exeRes;
	}
	
	public WPUMethodState getFinalState() {
		return getResult().getStates().getFinalState();
	}
	
}
