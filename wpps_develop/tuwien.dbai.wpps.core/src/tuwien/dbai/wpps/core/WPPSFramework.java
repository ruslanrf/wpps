/**
 * 
 */
package tuwien.dbai.wpps.core;

import java.io.File;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.SimpleStopwatch;
import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.UIUtils;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.ie.api.basis.IEAPI;
import tuwien.dbai.wpps.core.methods.WPUMethodState;
import tuwien.dbai.wpps.core.module.BrowserNavigationModule;
import tuwien.dbai.wpps.core.module.IEBasisModule;
import tuwien.dbai.wpps.core.module.InstanceAdaptersModule;
import tuwien.dbai.wpps.core.module.OntologyModule;
import tuwien.dbai.wpps.core.module.WPPSConfigModule;
import tuwien.dbai.wpps.core.nav.IBrowserNavigator;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.ontology.factory.AWPModelFiller;
import tuwien.dbai.wpps.core.wpmodel.ontology.simplification.WPModelSimplification;

import com.google.common.base.Preconditions;
import com.google.inject.Guice;
import com.google.inject.Injector;

// Later we can add possibility to configure source for IE, be it xml file with css or
// browser editor, or remote browser, and based on it generate navigator and choose fragment plug-in.
/**
 * TODO: Check! it seems that bases injector is created 2 times.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 10, 2012 3:35:51 PM
 */
public final class WPPSFramework {
private static final Logger log = Logger.getLogger(WPPSFramework.class);
	
	public final boolean STATISTICS = true;

	private IBrowserNavigator browserNav;
	private Object firstTarget;
	private Injector injBasis;
	
	private File wppsConfogFile;
	
	/**
	 * @param target For instance, EMBrowserEditor.
	 */
	public WPPSFramework(final Object target) {
		this.firstTarget = target;
//		wppsConfogFile = new File(WPPSCoreActivator.getPluginFolder(), "config/wpps-config.xml");
		wppsConfogFile = UIUtils.getService(WPPSCoreSessionController.class).getWPPSConfigFile();
		injBasis = Guice.createInjector(new WPPSConfigModule(wppsConfogFile), new BrowserNavigationModule());
		browserNav = injBasis.getInstance(IBrowserNavigator.class);
	}
	
	public WPPSFramework(final Object target, File wppsConfogFile) {
		this.firstTarget = target;
		this.wppsConfogFile = wppsConfogFile;
//		wppsConfogFile = new File(WPPSCoreActivator.getPluginFolder(), "config/wpps-config.xml");
		injBasis = Guice.createInjector(new WPPSConfigModule(wppsConfogFile), new BrowserNavigationModule());
		browserNav = injBasis.getInstance(IBrowserNavigator.class);
	}
	
	/**
	 * Remove all data, set default data provided to the constructor.
	 * Target (browser) corresponds to the last provided one. 
	 */
	public void flush() {
		wppsConfogFile = UIUtils.getService(WPPSCoreSessionController.class).getWPPSConfigFile();
		injBasis = Guice.createInjector(new WPPSConfigModule(wppsConfogFile), new BrowserNavigationModule());
		browserNav = injBasis.getInstance(IBrowserNavigator.class);
		wasInit = false;
		lastState = null;
	}
	
	private boolean wasInit = false;
	
	public boolean wasInit() {
		return wasInit;
	}
	
	public void init() {
		if (wasInit)
			throw new GeneralUncheckedException(log, "Framework has been already initialized");
		browserNav.setTarget(firstTarget);
		browserNav.setEventBus(WPPSCoreActivator.getDefaultSessionController().getEventBus());
		lastState = _createNewState(firstTarget, injBasis);
	}
	
	public IBrowserNavigator getBrowserNavigator() {
		return browserNav;
	}
	
	private WPUMethodState lastState = null;
	
	public WPUMethodState getLastState() {
		return lastState;
	}

	/**
	 * Create new state with filled unified ontological model (UOM).
	 * @return new state.
	 */
	public WPUMethodState createNewState() {
		if (lastState == null)
			return _createNewState(firstTarget, injBasis);
		else {
			Preconditions.checkState(lastState.getTarget() == firstTarget);
			return _createNewState(lastState.getTarget(), injBasis);
		}
	}
	
	public WPUMethodState createNewState(final Object target) {
		injBasis = Guice.createInjector(new WPPSConfigModule(wppsConfogFile), new BrowserNavigationModule());
		browserNav = injBasis.getInstance(IBrowserNavigator.class);
		this.firstTarget = target;
		return _createNewState(target, injBasis);
	}
	
	private WPUMethodState _createNewState(final Object target, final Injector injBasis) {
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"BEGIN. Create new state");

if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"BEGIN. Init injector.");
		Injector inj = null;
		if (injBasis == null)
			inj = Guice.createInjector(new WPPSConfigModule(wppsConfogFile), new BrowserNavigationModule(),
					new OntologyModule(), new InstanceAdaptersModule(), new IEBasisModule());
		else
			inj = injBasis.createChildInjector(new OntologyModule()
					, new InstanceAdaptersModule(), new IEBasisModule());
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"FINISH. Init injector.");
		
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"Initialize a model filler.");
		AWPModelFiller filler = inj.getInstance(AWPModelFiller.class);
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"Inject model filler.");
		inj.injectMembers(filler);
		Preconditions.checkState(filler.isInited());
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"Fill the UOM.");
		SimpleStopwatch phMFillingTS = new SimpleStopwatch();
		phMFillingTS.start();
		filler.fill();
		phMFillingTS.stop();
		Preconditions.checkState(filler.isFilled());
		
		// TODO add simplification of the UOM here. Check why it is never true
		if (inj.getInstance(WPPSConfig.class).isSimplification()) {
if (log.isDebugEnabled()) log.debug("Model simplification!");
			WPModelSimplification s = inj.getInstance(WPModelSimplification.class);
			s.apply();
		}
//		else
//			System.err.println("XXXXXXX");
		
		
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"START. State obj creation");
		final WPUMethodState state = new WPUMethodState(inj
				, target
				, inj.getInstance(WPPSConfig.class)
				, inj.getInstance(WPOntSubModels.class));
		
		state.getStatistic().setPhMFillingTS(phMFillingTS.getElapsedTime());
		
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"FINISH. State obj creation");
		lastState = state;
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"FINISH. Create new state");
		return state;
	}
	
	// TODO: change! Do not create instance all the time.
	/**
	 * @return API for the last state.
	 */
	public IEAPI getIEAPIForLastState() {
		return new IEAPI(lastState.getInjector());
	}
	
	// TODO: we can return current injector. It can be necessary.
	
//	@SuppressWarnings("unused")
//	@Subscribe
//	private void browserNavListener(EBrowserNavigatorEvents ev) {
//		switch(ev) {
//		case BEGIN_INIT:
//		case BEGIN_GO:
//			newState = true;
//			break;
//		case BEGIN_DEINIT:
//			break;
//		}
//	}

}
