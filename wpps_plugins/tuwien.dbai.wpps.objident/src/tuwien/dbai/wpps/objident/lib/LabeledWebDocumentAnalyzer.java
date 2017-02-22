/**
 * 
 */
package tuwien.dbai.wpps.objident.lib;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMTraversalNode;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.objident.ObjidentConfig;
import tuwien.dbai.wpps.objident.features.FeatureValue;
import tuwien.dbai.wpps.objident.features.FeaturesCalculationManager;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair;
import tuwien.dbai.wpps.objident.features.TObjectComparativePair.EExampleType;
import tuwien.dbai.wpps.objident.model.ModelContainer;
import tuwien.dbai.wpps.objident.model.TObject;
import tuwien.dbai.wpps.objident.model.TObjectFactory;

import com.google.common.base.Preconditions;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 2, 2012 1:14:53 PM
 */
public class LabeledWebDocumentAnalyzer {
	
	private static final Logger log = Logger.getLogger(LabeledWebDocumentAnalyzer.class);
	
	public static final String SELECTED_ELEMENT_ATTR_NAME = "abba-selected";
	public static final String MASTER_ATTR_VALUE = "master";
	public static final String YES_ATTR_VALUE = "yes";
	public static final String NO_ATTR_VALUE = "no";
	
	public static List<TObjectComparativePair> getListOfComparativePairs(
			ObjidentConfig config
			, EMBrowserEditor browserEditor
			, TObjectFactory tObjectFactory
			, IIEBasisAPI api) {
//api.dumpModel(EWPOntSubModel.DOM, "dom.rdf");
		
		final Set<IDOMTraversalNode> masterCol = new HashSet<IDOMTraversalNode>();
		final Set<IDOMTraversalNode> positiveCol = new HashSet<IDOMTraversalNode>();
		final Set<IDOMTraversalNode> negativeCol = new HashSet<IDOMTraversalNode>();
		
//		IResults rr = api.getObjectsByType(IDOMTraversalNode.class);
//		Iterator<IInstanceAdp> iter234e = rr.iterator();
//		while (iter234e.hasNext()) {
//log.trace("w2e4fr:: "+iter234e.next());
//		}
		
		
		api.getObjectsByType2(IDOMTraversalNode.class
				, new IPredicate<IInstanceAdp>() {
					@Override public Boolean apply(IInstanceAdp avar) {
						Map<String, String> attrMap = avar.as(IDOMElement.class).getAttributesMap();
log.trace(" z36e87fd: "+avar+" href: "+attrMap.get("href")
+ " sel: " + attrMap.get(SELECTED_ELEMENT_ATTR_NAME));
						String val = attrMap.get(SELECTED_ELEMENT_ATTR_NAME);
						if (val != null) {
							if (MASTER_ATTR_VALUE.equals(val))
								masterCol.add(avar.as(IDOMElement.class));
							else if (YES_ATTR_VALUE.equals(val))
								positiveCol.add(avar.as(IDOMElement.class));
							else if (NO_ATTR_VALUE.equals(val))
								negativeCol.add(avar.as(IDOMElement.class));
						}
						return false;
					} }
					, IDOMElement.class); // https://calmail.berkeley.edu/
		
		Preconditions.checkState(masterCol.size()>0, "There is no master.");
		Preconditions.checkState(masterCol.size()==1, "There are more than 1 master.");
		IDOMTraversalNode master = masterCol.iterator().next();
		
		List<IDOMTraversalNode> trMasterNodeList = getAcceptedElementsInRec(master, config);
		List<TObjectComparativePair> pairs = new LinkedList<TObjectComparativePair>();
		if (trMasterNodeList.size()>0) {
			TObject tomaster = tObjectFactory.create(trMasterNodeList.get(0));
			processExamples(tomaster, positiveCol, EExampleType.POSITIVE, config, tObjectFactory, pairs);
			processExamples(tomaster, negativeCol, EExampleType.NEGATIVE, config, tObjectFactory, pairs);
		}
		else {
			log.warn("Cannot find apprapriate node for the master "+master);
		}
		
		return pairs;
	}
	
	private static void processExamples(TObject tomaster, Set<IDOMTraversalNode> exmplCol, EExampleType exmplType
			, ObjidentConfig config
			, TObjectFactory tObjectFactory
			, List<TObjectComparativePair> pairs) {
		
		Iterator<IDOMTraversalNode> iter = exmplCol.iterator();
		while (iter.hasNext()) {
			List<IDOMTraversalNode> trnodeList = getAcceptedElementsInRec(iter.next(), config);
			for (IDOMTraversalNode trnode : trnodeList ) {
				TObject tonode = tObjectFactory.create(trnode);
				pairs.add(
						new TObjectComparativePair(tomaster, tonode, exmplType
				) );
			}
			
			
		}
	}
	
	private static List<IDOMTraversalNode> getAcceptedElementsInRec(final IDOMTraversalNode adp, final ObjidentConfig config) {
		if (CoreStaticLib.acceptedRdfAdpType(adp, config)) {
//if (adp.canAs(IDOMText.class)) {
//}
			List<IDOMTraversalNode> rez = new ArrayList<IDOMTraversalNode>(1);
			rez.add(adp);
			return rez;
		}
		List<IDOMTraversalNode> rez = new LinkedList<IDOMTraversalNode>();
		Iterator<IDOMTraversalNode> trIter = adp.getChildren().iterator();
		while (trIter.hasNext()) {
			List<IDOMTraversalNode> trNode = getAcceptedElementsInRec(trIter.next(), config);
			rez.addAll(trNode);
		}
		return rez;
		
	}
	
	public static void applyCollectedComparativePairs(
			List<TObjectComparativePair> pairs
			, FeaturesCalculationManager featuresCalculationManager
			, ModelContainer modelContainer) {
		Iterator<TObjectComparativePair> iter = pairs.iterator();
		Set<TObject> toComputed = new HashSet<TObject>();
		while (iter.hasNext()) {
			TObjectComparativePair pair = iter.next();
			if (!toComputed.contains(pair.getMasterObj())) {
				List<FeatureValue> fvList = featuresCalculationManager.calc(pair.getMasterObj());
				//TODO remove 2
				pair.getMasterObj().setComputedFeatureValueList(fvList);
				toComputed.add(pair.getMasterObj());
			}
			if (!toComputed.contains(pair.getComparativeObj())) {
				List<FeatureValue> fvList = featuresCalculationManager.calc(pair.getComparativeObj());
				//TODO remove 2
				pair.getComparativeObj().setComputedFeatureValueList(fvList);
				toComputed.add(pair.getComparativeObj());
			}
			modelContainer.addComparativePair2(pair);
		}
	}
	

}
