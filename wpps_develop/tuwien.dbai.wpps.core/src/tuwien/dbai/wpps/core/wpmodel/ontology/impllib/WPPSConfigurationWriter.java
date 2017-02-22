/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.ontology.impllib;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.HierarchicalConfiguration.Node;
import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.config.WPPSConfig.EOneToManyRelation;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPSchemaOntology;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 28, 2012 8:14:24 PM
 */
public class WPPSConfigurationWriter {
	private static final Logger log = Logger.getLogger(WPPSConfigurationWriter.class);
	
	public final static String FILE_NAME = "wpps-config.xml";
	
	private final WPPSConfig config;
	
	public WPPSConfigurationWriter(WPPSConfig config) {
		this.config = config;
	}
	
	public void write(File folder) {
		final XMLConfiguration configFile = new XMLConfiguration();
		configFile.setRootElementName("wpps-config");
		
		// <ontology>
		for (String ontUri : config.getCreateOntology()) {
			configFile.addProperty("ontology(-1).uri", ontUri);
			configFile.addProperty("ontology.type", config.getOntologyType().get(ontUri));
			configFile.addProperty("ontology.reasoner-type", config.getOntologyReasonerType().get(ontUri));
			Map<String, String> jenaReasonerMap = new  HashMap<String, String>();
			jenaReasonerMap.putAll(config.getJenaOWLReasonerName());
			jenaReasonerMap.putAll(config.getJenaRDFSReasoner());
			if (jenaReasonerMap.containsKey(ontUri))
				configFile.addProperty("ontology.jena-reasoner", jenaReasonerMap.get(ontUri));
			configFile.addProperty("ontology.load-schemata", config.getLoadSchema().get(ontUri));
			for (EWPOntSubModel sm : config.getOntologyModels().get(ontUri)) {
				configFile.addProperty("ontology.sub-model(-1)", sm);
			}
		}
		
		// <simplification>
		configFile.addProperty("simplification", config.isSimplification());
		
		// <struct-one-to-many-relation>
		int i = 0;
		for (Entry<EWPOntSubModel, EOneToManyRelation> e : config.getStructOneToManyRelationMap().entrySet()) {
			configFile.addProperty("struct-one-to-many-relation(-1)", e.getValue());
			SubnodeConfiguration node = configFile.configurationAt("struct-one-to-many-relation("+i+")");
//			HierarchicalConfiguration hc = (HierarchicalConfiguration)configFile
//					.getProperty("struct-one-to-many-relation("+i+")");
			HierarchicalConfiguration.Node attr = new Node("sub-model", e.getKey());
			attr.setAttribute(true);
//			hc.getRootNode().addAttribute(attr);
			node.getRootNode().addAttribute(attr);
			i++;
		}
		
		// <schemata>
		for (Entry<EWPSchemaOntology, String> e : config.getAltSchemaUri().entrySet()) {
			configFile.addProperty("schemata.alt-load(-1).schemata-name", e.getKey());
			configFile.addProperty("schemata.alt-load.alt-uri", e.getValue());
		}
		
		// <create-in-ontology>
		for (Object o : config.getCreateInOntology()) {
			configFile.addProperty("create-in-ontology.item", o);
		}
		
		// <support-in-ontology>
		for (Object o : config.getSupportInOntology()) {
			configFile.addProperty("support-in-ontology.item", o);
		}
		
		// <client-rectangle-creation>
		configFile.addProperty("client-rectangle-creation", config.getClientRectangleCreation());
		
		// <compute-by-request basis="quantitative">
		boolean firstIter = false;
		for (Object o : config.getComputeByRequestBasedOnQntFeatures()) {
			if (firstIter)
				configFile.addProperty("compute-by-request(-1).item", o);
			else
				configFile.addProperty("compute-by-request(-1).item", o);
		}
		
		
		
		
		
		try {
			configFile.save(new File(folder, FILE_NAME));
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}

}
