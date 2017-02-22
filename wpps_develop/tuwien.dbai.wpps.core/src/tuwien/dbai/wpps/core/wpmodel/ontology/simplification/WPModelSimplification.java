/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.ontology.simplification;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.BoxLibSupport;
import tuwien.dbai.wpps.ontquerying.SPARQLCommonQueryExecutor;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

// TODO rename as a simplfyer of BGM
// Is not included into the model generation. Workks quite slowly.

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 30, 2011 5:38:56 AM
 */
@Singleton
public class WPModelSimplification {
	private static final Logger log = Logger.getLogger(WPModelSimplification.class);

	private final WPPSConfig config;
	final WPOntSubModels models;
//	final RdfInstAdpFactoryWrap factory;
	final BoxImpl boxImpl;
	
	private int individsNumBefore = 0;
	
	@Inject
	public WPModelSimplification(final WPPSConfig config, final WPOntSubModels models//, final RdfInstAdpFactoryWrap factory
			, final BoxImpl boxImpl) {
		this.config = config;
		this.models = models;
//		this.factory = factory;
		this.boxImpl = boxImpl;
	}
	
	public void apply() {
if (log.isDebugEnabled()) log.debug("START. Model Simplification");
		
		// ont model
		final Model model = ModelFactory.createDefaultModel();
//		final Set<Model> modelSet = new HashSet<Model>();
//		Iterator<Entry<EWPOntModel, OntModelAdp>> modelsIter = models.getOntologyAdapterMap().entrySet().iterator();
//		while (modelsIter.hasNext()) {
//			final Model tmpModel = modelsIter.next().getValue();
//			if (modelSet.contains(tmpModel)) {
//				
//			}
//		}
		final OntModelAdp SBGMAdp = models.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
		
//		SBGMAdp.getTopRdfModel().lis
if (log.isDebugEnabled()) {
	individsNumBefore = numOfResources(SBGMAdp.getBottomRdfModel());
	log.debug("SIMPLIFICATION: INDIVIDS BEFORE: "+individsNumBefore);
}
		
		
		if (SBGMAdp != null) {
			model.add(SBGMAdp.getTopRdfModel());
		}
		final OntModelAdp QntBMAdp = models.getOntAdapter(EWPOntSubModel.QNT_BLOCK_MODEL);
		if (QntBMAdp != null) {
			model.add(QntBMAdp.getTopRdfModel());
		}
		
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. query");
		final List<Resource[]> ressList = SPARQLCommonQueryExecutor.getBoxesSpatialNestingAccordingDrawIdAndPage(model);
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. query");
		
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. analysis");
		final Set<Resource> removedRes = new LinkedHashSet<Resource>();
		final Set<Resource> firstRes = new LinkedHashSet<Resource>();
		
		int testCntRemoveElements = 0;
		
		final Iterator<Resource[]> resIter = ressList.iterator();
		while (resIter.hasNext()) {
			final Resource[] res2 = resIter.next();
			if (!removedRes.contains(res2[1]) && mayRemove(res2[0], res2[1])) {
				final Collection<Resource> ress = BoxLibSupport.getBoxComponents(res2[1], boxImpl);
				JenaModelsUtilLib.removeResourceAsSubjectOrObject(res2[1], QntBMAdp.getBottomRdfModel());
				JenaModelsUtilLib.removeResourcesAsSubjectOrObject(ress, QntBMAdp.getBottomRdfModel());
				JenaModelsUtilLib.removeResourceAsSubjectOrObject(res2[1], SBGMAdp.getBottomRdfModel());
				JenaModelsUtilLib.removeResourcesAsSubjectOrObject(ress, SBGMAdp.getBottomRdfModel());
				
				JenaModelsUtilLib.removeResourceAsSubjectOrObject(res2[1], QntBMAdp.getTopRdfModel());
				JenaModelsUtilLib.removeResourcesAsSubjectOrObject(ress, QntBMAdp.getTopRdfModel());
				JenaModelsUtilLib.removeResourceAsSubjectOrObject(res2[1], SBGMAdp.getTopRdfModel());
				JenaModelsUtilLib.removeResourcesAsSubjectOrObject(ress, SBGMAdp.getTopRdfModel());
				removedRes.add(res2[1]);
if (log.isTraceEnabled()) log.trace("REMOVED: "+res2[1]);
				firstRes.add(res2[0]);
				
				testCntRemoveElements += ress.size()+1;
			}
		}
		
		final Iterator<Resource> resIter2 = firstRes.iterator();
		while (resIter2.hasNext()) {
			final Resource tmpRes = resIter2.next();
			if (!removedRes.contains(tmpRes) && mayRemove(tmpRes)) {
				final Collection<Resource> ress = BoxLibSupport.getBoxComponents(tmpRes, boxImpl);
				JenaModelsUtilLib.removeResourceAsSubjectOrObject(tmpRes, QntBMAdp.getBottomRdfModel());
				JenaModelsUtilLib.removeResourcesAsSubjectOrObject(ress, QntBMAdp.getBottomRdfModel());
				JenaModelsUtilLib.removeResourceAsSubjectOrObject(tmpRes, SBGMAdp.getBottomRdfModel());
				JenaModelsUtilLib.removeResourcesAsSubjectOrObject(ress, SBGMAdp.getBottomRdfModel());
				
				JenaModelsUtilLib.removeResourceAsSubjectOrObject(tmpRes, QntBMAdp.getTopRdfModel());
				JenaModelsUtilLib.removeResourcesAsSubjectOrObject(ress, QntBMAdp.getTopRdfModel());
				JenaModelsUtilLib.removeResourceAsSubjectOrObject(tmpRes, SBGMAdp.getTopRdfModel());
				JenaModelsUtilLib.removeResourcesAsSubjectOrObject(ress, SBGMAdp.getTopRdfModel());
if (log.isTraceEnabled()) log.trace("REMOVED: "+tmpRes);
//				removedRes.add(tmpRes);
				
				testCntRemoveElements += ress.size()+1;
			}
			
		}
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. analysis. "+testCntRemoveElements+" elements were removed.");

if (log.isDebugEnabled()) {
	int individsNumAfter = numOfResources(SBGMAdp.getTopRdfModel());
	log.debug("SIMPLIFICATION: INDIVIDS AFTER: "+individsNumAfter
			+" reduction: "+(100 - ((double)individsNumAfter/(double)individsNumBefore * 100)) );
}
		
if (log.isDebugEnabled()) log.debug("FINISH. Model Simplification");
	}
	
	private final boolean mayRemove(final Resource res1, final Resource res2) {
		return true;
	}
	
	private final boolean mayRemove(final Resource res1) {
		return false;
	}
	
	private int numOfResources(Model m) {
		StmtIterator iter = m.listStatements(null, RDF.type, (RDFNode)null);
		Set<Resource> set = new HashSet<Resource>();
		while (iter.hasNext()) {
			set.add(iter.next().getSubject());
		}
		return set.size();
	}
	
	
}
