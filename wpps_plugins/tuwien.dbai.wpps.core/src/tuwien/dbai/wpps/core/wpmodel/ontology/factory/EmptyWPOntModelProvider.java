/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.ontology.factory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.IInstanceAdpFactory;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPSchemaOntology;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 26, 2011 1:20:04 AM
 */
@Singleton // binding in Module. singleton
public class EmptyWPOntModelProvider implements Provider<WPOntSubModels>  {
	private static final Logger log = Logger.getLogger(EmptyWPOntModelProvider.class);

	private final WPPSConfig config;
	private final IInstanceAdpFactory instanceAdpFactory;
	
	@Inject
	public EmptyWPOntModelProvider(final WPPSConfig config,
			final IInstanceAdpFactory instanceAdpFactory) {
		this.config = config;
		this.instanceAdpFactory = instanceAdpFactory;
	}

	@Override
	public WPOntSubModels get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
		if (config.getCreateOntology() == null)
			return null;
		
		final WPOntSubModels wpOntModel = new WPOntSubModels(instanceAdpFactory);
		
		final Iterator<String> iter = config.getCreateOntology().iterator();
		while(iter.hasNext()) {
			final String uri = iter.next();
			Model bottomOntology = null;
			Model topOntology = null;
			EWPOntSubModel[] eWPOntModelArr = config.getOntologyModels().get(uri);
			
			switch (config.getOntologyType().get(uri)) {
			case RDF:
				switch(config.getOntologyReasonerType().get(uri)) {
				case JENA_REASONER: {
					
					String[][] schemata = _returnSchemataAndAltUris(config.getOntologyModels().get(uri)
							, config.getAltSchemaUri());
					
					bottomOntology = JenaModelsUtilLib.createRdfModel(uri
							, config.getJenaRDFSReasoner().get(uri)
							, config.getLoadSchema().get(uri)
							, schemata[0], schemata[1]);
					break;
				}
				default:
					throw new UnknownValueFromPredefinedList(log, config.getOntologyReasonerType().get(uri));
				}
				
				break;
				
			case OWL:
				switch(config.getOntologyReasonerType().get(uri)) {
				case JENA_REASONER: {
					
					String[][] schemata = _returnSchemataAndAltUris(config.getOntologyModels().get(uri)
							, config.getAltSchemaUri());
					
					bottomOntology = JenaModelsUtilLib.createOwlModel(uri
							, config.getJenaOWLReasoner().get(uri)
							, config.getLoadSchema().get(uri)
							, schemata[0], schemata[1]);
					break;
				}
				default:
					throw new UnknownValueFromPredefinedList(log, config.getOntologyReasonerType().get(uri));
				}
				
				break;
				
			default:
				throw new UnknownValueFromPredefinedList(log, config.getOntologyType().get(uri));
			}
			
			if (config.getJenaModelRulesUri().containsKey(uri)) {
				topOntology = JenaModelsUtilLib.addRuleReasoningLayer(bottomOntology, config.getJenaModelRulesUri().get(uri), config.getJenaModelRules().get(uri));
if (log.isTraceEnabled()) log.trace("Ontology: "+uri+". Reasoner "+config.getJenaModelRulesUri().get(uri)+" added, rule model: "+config.getJenaModelRules().get(uri));
			}
			else topOntology = bottomOntology;
			
			if (eWPOntModelArr != null) {
				final OntModelAdp omaTmp = new OntModelAdp(uri, bottomOntology, topOntology, (topOntology instanceof OntModel));
if (log.isTraceEnabled()) log.trace("ONTOLOGY FOR MODELS: "+omaTmp+" "+Arrays.toString(eWPOntModelArr));
				for (int i=0; i<eWPOntModelArr.length; i++) {
					wpOntModel.addOntAdapter(eWPOntModelArr[i], omaTmp);
				}
			}
			
			
		}
		
		return wpOntModel;
	}
	
	@SuppressWarnings("unchecked")
	private static final String[][] _returnSchemataAndAltUris(final EWPOntSubModel[] wpOntModelArr
			, final Map<EWPSchemaOntology, String> altSchemaUriMap) {
		
		Preconditions.checkNotNull(wpOntModelArr);
		final Set<String> schemaBase = new ListOrderedSet();
		final List<String> altSchemaUri = new ArrayList<String>(10);
		for (int i=0; i<wpOntModelArr.length; i++) {
			final EWPSchemaOntology[] schemataArr = wpOntModelArr[i].getSchemaOntologyArr();
			if (schemataArr!=null) {
				for (int j=0; j<schemataArr.length; j++) {
					if (!schemaBase.contains(schemataArr[j].getCutNS())) {
						schemaBase.add(schemataArr[j].getCutNS());
						altSchemaUri.add(altSchemaUriMap.get(schemataArr[j]));
					}
				}
			}
		}
		
		String[][] res = new String[2][schemaBase.size()];
		res[0] = schemaBase.toArray(new String[schemaBase.size()]);
		res[1] = altSchemaUri.toArray(new String[altSchemaUri.size()]);
		return res;
	}
	
	
}
