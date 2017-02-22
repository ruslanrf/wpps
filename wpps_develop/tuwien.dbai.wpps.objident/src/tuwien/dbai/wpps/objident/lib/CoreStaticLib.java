/**
 * 
 */
package tuwien.dbai.wpps.objident.lib;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMDocument;
import org.mozilla.interfaces.nsIDOMNode;

import tuwien.dbai.wpps.core.WPPSFramework;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.WPPSFrameworkDependentModule;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.FeaturesCalculationManager;
import tuwien.dbai.wpps.objident.model.BrowserRelatedModel;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectFactory;

import com.google.inject.Injector;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Library which contains all important functions for the applictions and which can be used in different chunks of code.
 * This class never throw events implicitly, never modify model implicitly!
 * Only if it is defined explicitly! (e.g. function which manipulate with some data and throwing events is important). 
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 3, 2012 12:11:37 PM
 */
public class CoreStaticLib {
	private static final Logger log = Logger.getLogger(CoreStaticLib.class);
	
//	private Injector sessionInjector;
//	
//	public CoreStaticLib(Injector sessionInjector) {
//		this.sessionInjector = sessionInjector;
//	}
	
	public static Injector createFrameworkDependentInjector(final Injector sessionInjector
			, final BrowserRelatedModel browserRelatedModel, final Class<? extends IInstanceAdp>[] clazzArr
			, File wppsConfigFile) {
		return sessionInjector
				.createChildInjector(new WPPSFrameworkDependentModule(browserRelatedModel, clazzArr, wppsConfigFile));
	}
	
//	public static void initWebDocument(Injector frameworkDependentInjector) {
//		WPPSFramework wppsFramework = frameworkDependentInjector.getInstance(WPPSFramework.class);
//		wppsFramework.init();
//		if (log.isTraceEnabled()) log.trace("new FRAMEWORK has been created");
//		BGColorSimpleSystemEnricher bge = wppsFramework.getIEAPIForLastState()
//				.getEnricher(BGColorSimpleSystemEnricher.class);
//		bge.init();
//		bge.enrich();
//		if (log.isTraceEnabled()) log.trace("Enrichers has been invoked");
//	}
	
//	public static void createFrameworkDependentInjector(
//			final Injector sessionInjector, final EMBrowserEditor browser
//			, Injector frameworkDependentInjector
//			) {
//	}
	
//	@Deprecated
//	public static Injector createFrameworkDependentInjectorDT(final Injector sessionInjector, final EMBrowserEditor browser) {
//		final Injector[] inj = new Injector[1];
//			Display.getDefault().syncExec(
//					new Runnable() {
//						@Override public void run() {
//							inj[0] = createFrameworkDependentInjector(sessionInjector, browser);
//			} } );
//		return inj[0];
//	}
	
	/**
	 * @param node
	 * @param frameworkDependentInj
	 * @return can be null
	 */
	public static TObject createTObjectForNodeSelected(final nsIDOMNode node, Injector frameworkDependentInj) {
		TObject toRez = null;
		WPPSFramework wppsFramework = frameworkDependentInj.getInstance(WPPSFramework.class);
		WPOntSubModels models = wppsFramework.getLastState().getModels();
		final Resource res = models.getRdfResourcesForSourceObjectAsMap_expensive(node, EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL)
				.get(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
if (log.isTraceEnabled()) log.trace("Selected: "+res);
		if (res == null) {
if (log.isTraceEnabled()) log.trace("node "+node+"("+node.getNodeName()+", "+node.getNodeValue()+") does not have a correspoding individual.");
		} else {
			TObjectFactory of = frameworkDependentInj.getInstance(TObjectFactory.class);
			toRez = of.create(models.adaptResource(res));
		}
		return toRez;
	}
	
	public static List<FeatureValue> calcFeaturesForTObject(final TObject tObject, Injector frameworkDependentInj) {
		FeaturesCalculationManager ftc = frameworkDependentInj
				.getInstance(FeaturesCalculationManager.class);
		return ftc.calc(tObject);
	}
	
//	public static boolean acceptedRdfAdpType(final Injector sessionInjector, final IInstanceAdp contained) {
//		final ObjidentConfig conf = sessionInjector.getInstance(ObjidentConfig.class);
//		Iterator<Class<? extends IInstanceAdp>> iter = conf.getConsideredObjectTypes().iterator();
//		while (iter.hasNext()) {
//			if (contained.canAs(iter.next()))
//				return true;
//		}
//		return false;
//	}
	
	public static boolean acceptedRdfAdpType(IInstanceAdp contained, ObjidentConfig conf) {
		Iterator<Class<? extends IInstanceAdp>> iter = conf.getConsideredObjectJavaTypes().iterator();
		while (iter.hasNext()) {
			if (contained.canAs(iter.next()))
				return true;
		}
		return false;
	}
	
	public static final nsIDOMDocument getNsIDOMDocumentForTObject(TObject instAdp, Injector frameworkDependentInj) {
		WPPSFramework wppsFramework = frameworkDependentInj.getInstance(WPPSFramework.class);
		WPOntSubModels models = wppsFramework.getLastState().getModels();
		final nsIDOMNode node = (nsIDOMNode)models.getSourceObjectForAdp(instAdp.getRdfTargetObject());
		if (node == null) {
if (log.isDebugEnabled()) log.debug("Individual "+instAdp.getRdfTargetObject()+"has no corresponding node");
			return null;
		}
		return node.getOwnerDocument();
	}
	
	public static final String toString(nsIDOMNode node) {
		return node+" ("+node.getNodeName()+", "+node.getNodeValue()+")";
	}
	
}
