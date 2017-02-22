/**
 * 
 */
package tuwien.dbai.wpps.ui.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.event.WPPSPropertyChangeEvent;
import tuwien.dbai.wpps.core.WPPSFramework;
import tuwien.dbai.wpps.core.methods.AWPUMethod;
import tuwien.dbai.wpps.core.methods.WPUMethodState;
import tuwien.dbai.wpps.core.methods.WPUMethodsExecutionResult;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLibSupport;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.WPPSUISessionController;
import tuwien.dbai.wpps.ui.commandhandlers.RunSelectedMethodsCmdHdlr;
import tuwien.dbai.wpps.ui.model.BrowserRelatedData.Event.EventTypes;

import com.google.common.eventbus.EventBus;

/**
 * Model.
 * Class is created in {@link WPPSUISessionController}.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 15, 2012 9:06:06 PM
 * @see BrowsersRelatedDataManager
 */
public class BrowserRelatedData {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(BrowserRelatedData.class);

	public static class Event extends WPPSPropertyChangeEvent<EventTypes> {
		public static enum EventTypes {
			/**
			 * Source: {@link BrowserRelatedData}
			 * Data:
			 * <ol>
			 * <li>old value (Array of objects {@linkplain WPUMethodsExecutionResult} and {@linkplain WPUMethodState}).</li>
			 * <li>new value (Array of objects {@linkplain WPUMethodsExecutionResult} and {@linkplain WPUMethodState}).</li>
			 * </ol>
			 */
			METHODS_RESULTS_CHANGE
		}
		protected Event(EventTypes en, final Object source, final Object oldVal, final Object newVal) {
			super(en, source, null, oldVal, newVal);
		}
		public static Event getInstance(EventTypes event, final BrowserRelatedData source, final Object oldVal, final Object newVal) {
			return new Event(event, source, oldVal, newVal);
		}
	}
	
	public BrowserRelatedData(EMBrowserEditor browserEditor
//			, WPPSFramework wppsFramework
			, Set<WPUMethodDescAdp> methodDescSet
			, EventBus eventBus) {
		this.browserEditor = browserEditor;
//		this.wppsFramework = wppsFramework;
		this.eventBus = eventBus;
		Iterator<WPUMethodDescAdp> iter = methodDescSet.iterator();
		while (iter.hasNext()) {
			methodAdpSet.add(new WPUMethodAdp(iter.next(), browserEditor, eventBus));
		}
	}
	
	//=============================
	// === Core related data ===
	// ============================
	
	private final EMBrowserEditor browserEditor;
	public EMBrowserEditor getBrowserEditor() {
		return browserEditor;
	}
	
	private WPPSFramework wppsFramework;
	public WPPSFramework getFramework() {
		return wppsFramework;
	}
	public void setFramework(WPPSFramework wppsFramework) {
		this.wppsFramework = wppsFramework;
	}
	
	private WPUMethodsExecutionResult methodsExecutionResult = null;
	/**
	 * This property should be defined together with {@linkplain BrowserRelatedData#methodsExecutionResult}.
	 */
	private WPUMethodState currState = null;
	/**
	 * TODO: use index for curr state.
	 * 
	 * Results must be set after execution of methods.
	 * Class {@linkplain RunSelectedMethodsCmdHdlr} is responsable for executing methods
	 * and setting this parameter up.
	 * @param results
	 */
	public void setMethodInvokationResults2(WPUMethodsExecutionResult methodsExecutionResult
			, WPUMethodState currState) {
		eventBus.post(
			Event.getInstance(EventTypes.METHODS_RESULTS_CHANGE
				, BrowserRelatedData.this
				, new Object[]{methodsExecutionResult, currState}
				, new Object[]{this.methodsExecutionResult = methodsExecutionResult, this.currState = currState})
				);
	}
	
	public WPUMethodsExecutionResult getInvokationResults() {
		return methodsExecutionResult;
	}
	
	public WPUMethodState getCurrState() {
		return currState;
	}
	
	private final Set<WPUMethodAdp> methodAdpSet = new HashSet<WPUMethodAdp>();
	public Set<WPUMethodAdp> getMethodSet() {
		return methodAdpSet;
	}
	public WPUMethodAdp getMethod(WPUMethodDescAdp md) {
		Iterator<WPUMethodAdp> iter = methodAdpSet.iterator();
		while (iter.hasNext()) {
			WPUMethodAdp m = iter.next();
			if (m.getDescription().equals(md))
				return m;
		}
		return null;
	}
	public WPUMethodAdp getMethod(AWPUMethod method) {
		Iterator<WPUMethodAdp> iter = methodAdpSet.iterator();
		while (iter.hasNext()) {
			WPUMethodAdp m = iter.next();
			if (method.equals(m.getContent()))
				return m;
		}
		return null;
	}
	public Collection<WPUMethodAdp> getMethods(Collection<AWPUMethod> method) {
		Collection<WPUMethodAdp> col = InstAdpLibSupport
				.createCollection(method, WPUMethodAdp.class);
		Iterator<AWPUMethod> iter = method.iterator();
		while (iter.hasNext()) {
			WPUMethodAdp ma = getMethod(iter.next());
			if (ma != null)
				col.add(ma);
		}
		return col;
	}
	
	//=============================
	// === GUI related data ===
	// ============================
	
	private final EventBus eventBus;
	
}
