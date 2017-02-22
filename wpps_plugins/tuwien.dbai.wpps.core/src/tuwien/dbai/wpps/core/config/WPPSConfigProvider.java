/**
 * 
 */
package tuwien.dbai.wpps.core.config;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.apache.commons.configuration.tree.ExpressionEngine;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;
import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IProcedure;
import tuwien.dbai.wpps.common.exceptions.UnknownType;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.core.WPPSCoreActivator;
import tuwien.dbai.wpps.core.annotation.AnnotWPPSConfig;
import tuwien.dbai.wpps.core.config.WPPSConfig.EClientRectangleCreation;
import tuwien.dbai.wpps.core.config.WPPSConfig.ELocation;
import tuwien.dbai.wpps.core.config.WPPSConfig.EOneToManyRelation;
import tuwien.dbai.wpps.core.config.WPPSConfig.EOntologyFormalism;
import tuwien.dbai.wpps.core.config.WPPSConfig.EQltBMBorderMuType;
import tuwien.dbai.wpps.core.config.WPPSConfig.EReasonerType;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.ELMInstType;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPSchemaOntology;
import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBGMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockStructRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.EDOMAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.EDOMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.EDOMRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.EVMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.EVOQntAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.EVOQntRelationType;

import com.google.common.base.Enums;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner.RuleMode;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

/**
 * Guice Provider.
 * Simple configurator which create an instance of  {@link WPPSConfig}.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 22, 2011 8:19:56 PM
 */
@Singleton // binding must be in a module + Singletone
public final class WPPSConfigProvider implements Provider<WPPSConfig> {
	private static final Logger log = Logger.getLogger(WPPSConfigProvider.class);
	
	private final XPathExpressionEngine xPathExpressionEngine = new XPathExpressionEngine();
	
	private File wppsConfigFile;
	
	@Inject
	public WPPSConfigProvider(@AnnotWPPSConfig File wppsConfogFile) {
		this.wppsConfigFile = wppsConfogFile;
	}
	
	
	@Override
	public WPPSConfig get() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Providing instance.");
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. Read configuration");

		final WPPSConfig config = new WPPSConfig();
		
		try
		{
			final XMLConfiguration configFile = new XMLConfiguration(wppsConfigFile);
//		    final XMLConfiguration configFile = new XMLConfiguration(new File(WPPSCoreActivator.getPluginFolder()
//		    		, "config/wpps-config.xml"));
//		    configFile.setExpressionEngine(new XPathExpressionEngine());
		    
		    // --- Ontology configuration
			configureOntology(configFile, config);
			// --- Configuration parameters ---
			createInOntology(configFile, config);

			computeByRequestBasedOnQntFeatures(configFile, config);
			computeByRequestBasedOnFundFeatures(configFile, config);
			compositeBasicDependence(configFile, config);
			supportInOntology(configFile, config);
			structOneToManyRelationMap(configFile, config);
			locationAndArea(configFile, config);
			clientRectangleCreation(configFile, config);
			qltBGMFuzzyness(configFile, config);
		    
		}
		catch(ConfigurationException cex)
		{
			log.fatal("Cannot initialize wpps configuration. Reason: "+cex.getMessage());
			cex.printStackTrace();
		    return null;
		}
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. Read configuration");
		return config;
	}
	
	private final boolean isNullEmptyOrBlank(final String str) {
		return str == null || str.trim().length() == 0;
	}
	
	private final <T extends Enum<T>> T opt1(final AbstractConfiguration configFile, String xmlPath, Class<T> e) {
		final String str = configFile.getString(xmlPath);
		Preconditions.checkArgument(!isNullEmptyOrBlank(str));
		T ontType = (T)Enums.valueOfFunction(e).apply(str);
		Preconditions.checkNotNull(ontType);
		return ontType;
	}
	
	/**
	 * Read a configuration of ontology into memory.
	 * Configured: {@linkplain WPPSConfig#setOntologyInstanceNSGenBase(String)}, {@linkplain WPPSConfig#getCreateOntology()},
	 * {@linkplain WPPSConfig#getOntologyType()}, {@linkplain WPPSConfig#getOntologyReasonerType()},
	 * {@linkplain WPPSConfig#getJenaRDFSReasoner()}, {@linkplain WPPSConfig#getJenaOWLReasoner()},
	 * {@linkplain WPPSConfig#getLoadSchema()}, {@linkplain WPPSConfig#getOntologyModels()},
	 * {@linkplain WPPSConfig#getAltSchemaUri()}, {@linkplain WPPSConfig#setSimplification(boolean)}.
	 * @param configFile
	 * @param config
	 */
	@SuppressWarnings("rawtypes")
	private final void configureOntology(final XMLConfiguration configFile, final WPPSConfig config) {
		// ---
		String str = configFile.getString("ontology-instance-ns-gen-base");
		if (!isNullEmptyOrBlank(str))
			config.setOntologyInstanceNSGenBase(str);
		// ---
		List list = configFile.configurationsAt("ontology");
		Preconditions.checkArgument(list.size()>0, "Non of the ontologies are configured to be created.");
		for (Iterator it = list.iterator(); it.hasNext();) {
			HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();
			// ---
			str = sub.getString("uri");
			String str2 = null;
			if (isNullEmptyOrBlank(str)) {
				Preconditions.checkNotNull(config.getOntologyInstanceNSGenBase()
						, "Ontology must have property ontology-instance-ns-gen-base or uri defined.");
				str2 = JenaModelsUtilLib.genOntologyNS(config.getOntologyInstanceNSGenBase());
			}
			else { str2 = str; }
			config.getCreateOntology().add(str2);
			// ---
			config.getOntologyType().put(str2, opt1(sub, "type", EOntologyFormalism.class));
			// ---
			config.getOntologyReasonerType().put(str2, opt1(sub, "reasoner-type", EReasonerType.class));
			// ---
			str = sub.getString("jena-reasoner");
			if (str != null && str.trim().length() != 0) {
				String rdfsR = returnRDFSReasonerSpec(str);
				OntModelSpec owlR = null;
				if (rdfsR == null) {
					owlR = returnOWLReasonerSpec(str);
					if (owlR == null)
						throw new UnknownType(log, str);
					else {
						config.getJenaOWLReasoner().put(str2, owlR);
						config.getJenaOWLReasonerName().put(str2, str);
					}
				}
				else
					config.getJenaRDFSReasoner().put(str2, rdfsR);
			}
			// ---
			boolean bool = sub.getBoolean("load-schemata"); // NoSuchElementException
			config.getLoadSchema().put(str2, bool);
			// ---
			List subModels = sub.getList("sub-model");
			Preconditions.checkArgument(subModels.size() > 0, "Ontology "+str2+" does not contain any models.");
			EWPOntSubModel[] ontModelArr = new EWPOntSubModel[subModels.size()];
			int it3 = 0;
			for (Iterator it2 = subModels.iterator(); it2.hasNext();) {
				String subModel = (String) it2.next();
				EWPOntSubModel ontModel = Enums.valueOfFunction(EWPOntSubModel.class).apply(subModel);
				Preconditions.checkNotNull(ontModel);
				ontModelArr[it3] = ontModel;
				it3++;
			}
			config.getOntologyModels().put(str2, ontModelArr);
			
			// --- RULES ---
			// --- rules.uri
			str = sub.getString("rules.uri");
			if (str != null && str.trim().length() != 0) {
				config.getJenaModelRulesUri().put(str2, str);
			} else {
				str = sub.getString("rules.relative-uri");
				if (str != null && str.trim().length() != 0) {
					str = "file://"+WPPSCoreActivator.getFile(str).getAbsolutePath();
					config.getJenaModelRulesUri().put(str2, str);
				}
			}
			
			// --- rules.mode
			str = sub.getString("rules.ruleMode");
			if (str != null && str.trim().length() != 0) {
				RuleMode ruleMode = returnRuleModel(str);
				if (ruleMode == null)
					throw new UnknownType(log, str);
				config.getJenaModelRules().put(str2, ruleMode);
			}
			
		}
		// ---
		list = configFile.configurationsAt("schemata.alt-load"); // It can be of size 0.
		for (Iterator it = list.iterator(); it.hasNext();) {
			HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();
			// ---
			EWPSchemaOntology schName = opt1(sub, "schemata-name", EWPSchemaOntology.class);
			// ---
			str = sub.getString("alt-uri");
			if (isNullEmptyOrBlank(str)) {
				// ---
				str = sub.getString("alt-relative-uri");
				Preconditions.checkArgument(!isNullEmptyOrBlank(str), "Either alt-relative-uri or alt-uri should be defined.");
				str = "file://"+WPPSCoreActivator.getFile(str).getAbsolutePath();
//				str = "file://"+(new File(WPPSCoreActivator.getPluginFolder(), str)).getAbsolutePath();
			}
			config.getAltSchemaUri().put(schName, str);
		}
		
		
		// ---
		boolean bol = configFile.getBoolean("simplification");
		config.setSimplification(bol);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private final void listProcedure(final AbstractConfiguration configFile, final String xmlPath, final IProcedure<Object> proc
			, final Class<? extends Enum>[] e ) {
		List list = configFile.getList(xmlPath);
		for (Iterator it = list.iterator(); it.hasNext();) {
			String item = (String) it.next();
			for (int i=0; i<e.length; i++) {
				Enum instType = (Enum)Enums.valueOfFunction(e[i]).apply(item);
				if (instType != null) {
					proc.apply(instType);
					break;
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static final Class<? extends Enum>[] arrayOfEnums = (Class<? extends Enum>[]) new Class[] {
		// --- BGM Instances ---
		EBGMInstType.class,
		// --- SBGM relations types ---
		EBlockStructRelation.class,
		// --- QntBGM attributes ---
		EBlockQntAttrType.class
		// --- QntBGM relations types ---
		, EBlockQntRelationType.class,
		// --- QltBGM attributes ---
		// --- QltBGM relations types ---
		EBlockQltRelationType.class,
		// --- IM Instances, attributes and relations ---
		EIMInstType.class,
		EIMAttrType.class,
		EIMRelation.class,
		// --- VM Instances ---
		EVMInstType.class,
		// --- QntVM attributes ---
		EVOQntAttrType.class,
		// --- QntVM relations types ---
		EVOQntRelationType.class,
		// --- LM Instances ---
		ELMInstType.class,
		// --- DOM Instances ---
		EDOMInstType.class,
		// --- DOM attributes ---
		EDOMAttrType.class,
		// --- DOM relations ---
		EDOMRelation.class
	};
	
	@SuppressWarnings("rawtypes")
	private final <T extends Enum<T>> Class<? extends Enum>[] getArrayOfEnums() {
		return arrayOfEnums;
	}
	
	/**
	 * Configured: {@linkplain WPPSConfig#getCreateInOntology()}.
	 * @param configFile
	 * @param config
	 */
	private final void createInOntology(final XMLConfiguration configFile, final WPPSConfig config) {
		listProcedure(configFile, "create-in-ontology.item"
				, new IProcedure<Object>() {
					@Override public void apply(Object avar) {
						config.getCreateInOntology().add(avar);
		} }, getArrayOfEnums() );
	}

	/**
	 * Configured: {@linkplain WPPSConfig#getComputeByRequestBasedOnQntFeatures()}
	 * @param configFile
	 * @param config
	 */
	private final void computeByRequestBasedOnQntFeatures(final XMLConfiguration configFile, final WPPSConfig config) {
		final ExpressionEngine ee = configFile.getExpressionEngine();
		configFile.setExpressionEngine(xPathExpressionEngine);
		listProcedure(configFile, "compute-by-request[@basis='quantitative']/item"
				, new IProcedure<Object>() {
					@Override public void apply(Object avar) {
						config.getComputeByRequestBasedOnQntFeatures().add(avar);
		} }, getArrayOfEnums() );
		configFile.setExpressionEngine(ee);
	}
	
	/**
	 * Configured: {@linkplain WPPSConfig#getComputeByRequestBasedOnFundFeatures()}
	 * @param configFile
	 * @param config
	 */
	private final void computeByRequestBasedOnFundFeatures(final XMLConfiguration configFile, final WPPSConfig config) {
		final ExpressionEngine ee = configFile.getExpressionEngine();
		listProcedure(configFile, "compute-by-request[@bases='fundamental']/item"
				, new IProcedure<Object>() {
					@Override public void apply(Object avar) {
						config.getComputeByRequestBasedOnFundFeatures().add(avar);
		} }, getArrayOfEnums() );
		configFile.setExpressionEngine(ee);
	}
	
	/**
	 * Configured: {@linkplain WPPSConfig#getCompositeBasicDependence()}.
	 * @param configFile
	 * @param config
	 */
	private final void compositeBasicDependence(final XMLConfiguration configFile, final WPPSConfig config) {
		listProcedure(configFile, "composite-basic-dependence.item"
				, new IProcedure<Object>() {
					@Override public void apply(Object avar) {
						config.getCompositeBasicDependence().add(avar);
		} }, getArrayOfEnums() );
	}
	
	/**
	 * Configured: {@linkplain WPPSConfig#getSupportInOntology()}.
	 * @param configFile
	 * @param config
	 */
	private final void supportInOntology(final XMLConfiguration configFile, final WPPSConfig config) {
		listProcedure(configFile, "support-in-ontology.item"
				, new IProcedure<Object>() {
					@Override public void apply(Object avar) {
						config.getSupportInOntology().add(avar);
		} }, getArrayOfEnums() );
	}
	
	
	
	/**
	 * Configured: {@linkplain WPPSConfig#getStructOneToManyRelationMap()}.
	 * @param configFile
	 * @param config
	 */
	private final void structOneToManyRelationMap(final XMLConfiguration configFile, final WPPSConfig config) {
		List<HierarchicalConfiguration> nodes = configFile.configurationsAt("struct-one-to-many-relation");
		for (HierarchicalConfiguration c : nodes) {
			ConfigurationNode node = c.getRootNode();
			EOneToManyRelation oneToMany = Enums.valueOfFunction(EOneToManyRelation.class)
					.apply(node.getValue().toString());
			Preconditions.checkNotNull(oneToMany);
			EWPOntSubModel subModel = Enums.valueOfFunction(EWPOntSubModel.class)
					.apply(((ConfigurationNode)node.getAttributes("sub-model").get(0)).getValue().toString());
			Preconditions.checkNotNull(subModel);
			config.getStructOneToManyRelationMap().put(subModel, oneToMany);
		}
	}
	
	/**
	 * Configured: {@linkplain WPPSConfig#getLocation()}, {@linkplain WPPSConfig#getArea()}
	 * @param configFile
	 * @param config
	 */
	@SuppressWarnings("rawtypes")
	private final void locationAndArea(final XMLConfiguration configFile, final WPPSConfig config) {
		// ---
		ELocation loc = opt1(configFile, "location", ELocation.class);
		config.setLocation(loc);
		// ---
		List list = configFile.configurationsAt("area");
		Preconditions.checkArgument(loc != ELocation.OVERLAPS_AREA && loc != ELocation.INSIDE_AREA || list.size() > 0
				, "Location is not correctly specified.");
		for (Iterator it = list.iterator(); it.hasNext();) {
			HierarchicalConfiguration sub = (HierarchicalConfiguration) it.next();
			config.getArea().xMin = sub.getDouble("top-left-x");
			config.getArea().yMin = sub.getDouble("top-left-y");
			config.getArea().xMax = sub.getDouble("bottom-right-x");
			config.getArea().yMax = sub.getDouble("bottom-right-y");
		}
	}
	
	/**
	 * Configured: {@linkplain WPPSConfig#getClientRectangleCreation()}.
	 * @param configFile
	 * @param config
	 */
	private final void clientRectangleCreation(final XMLConfiguration configFile, final WPPSConfig config) {
		// ---
		EClientRectangleCreation crc = opt1(configFile, "client-rectangle-creation", EClientRectangleCreation.class);
		config.setClientRectangleCreation(crc);
	}
	
	/**
	 * Configured: {@linkplain WPPSConfig#getQltBMBorderMuType()}, {@linkplain WPPSConfig#getQltBMLeftBorderInterval()},
	 * {@linkplain WPPSConfig#getQltBMRightBorderInterval()}, {@linkplain WPPSConfig#getQltBMCenterInterval()},
	 * {@linkplain WPPSConfig#getQltBMBorderNu()}.
	 * @param configFile
	 * @param config
	 */
	@SuppressWarnings("rawtypes")
	private final void qltBGMFuzzyness(final XMLConfiguration configFile, final WPPSConfig config) {
		// ---
		EQltBMBorderMuType muType = opt1(configFile, "qltbm-border-mu-type", EQltBMBorderMuType.class);
		config.setQltBMBorderMuType(muType);
		switch (muType) {
		case INTERVAL:
		{
			// ---
			List list = configFile.configurationsAt("qltbm-left-border-mu");
			Preconditions.checkArgument(list.size() > 0, "Mu for the left border is not defined.");
			HierarchicalConfiguration sub = (HierarchicalConfiguration) list.iterator().next();
			config.getQltBMLeftBorderInterval()[0] = sub.getDouble("left-point");
			config.getQltBMLeftBorderInterval()[1] = sub.getDouble("right-point");
			// ---
			list = configFile.configurationsAt("qltbm-right-border-mu");
			Preconditions.checkArgument(list.size() > 0, "Mu for the right border is not defined.");
			sub = (HierarchicalConfiguration) list.iterator().next();
			config.getQltBMRightBorderInterval()[0] = sub.getDouble("left-point");
			config.getQltBMRightBorderInterval()[1] = sub.getDouble("right-point");
			// ---
			list = configFile.configurationsAt("qltbm-center-mu");
			Preconditions.checkArgument(list.size() > 0, "Mu for the center is not defined.");
			sub = (HierarchicalConfiguration) list.iterator().next();
			config.getQltBMCenterInterval()[0] = sub.getDouble("left-point");
			config.getQltBMCenterInterval()[1] = sub.getDouble("right-point");
		}
			break;
		default:
			throw new UnknownValueFromPredefinedList(log, muType);
		}
		// ---
		List list = configFile.configurationsAt("qltbm-border-nu");
		Preconditions.checkArgument(list.size() > 0, "Nu for the borders is not defined.");
		// TODO: add possibility to omit this value
		HierarchicalConfiguration sub = (HierarchicalConfiguration) list.iterator().next();
		config.getQltBMBorderNu()[0] = sub.getDouble("center");
		config.getQltBMBorderNu()[1] = sub.getDouble("delta");
	}
	
	// =====================
	// Supporting functions
	// =====================
		
		/**
		 * <a href="http://www.openjena.org/javadoc/com/hp/hpl/jena/ontology/OntModelSpec.html">For OWL model</a>.
		 * @param input
		 * @return
		 */
		private static OntModelSpec returnOWLReasonerSpec(String input) {
			if ("OWL_MEM".equals(input))
				return OntModelSpec.OWL_MEM;
			if ("OWL_MEM_TRANS_INF".equals(input))
				return OntModelSpec.OWL_MEM_TRANS_INF;
			if ("OWL_DL_MEM_RDFS_INF".equals(input))
				return OntModelSpec.OWL_DL_MEM_RDFS_INF;
			if ("OWL_DL_MEM_RULE_INF".equals(input))
				return OntModelSpec.OWL_DL_MEM_RULE_INF;
			
			// ...
			return null;
		}
		
		
		/**
		 * <a href="http://jena.sourceforge.net/javadoc/com/hp/hpl/jena/vocabulary/ReasonerVocabulary.html">For RDF model</a>.
		 * @param input
		 * @return
		 */
		private static String returnRDFSReasonerSpec(String input) {
			if ("RDFS_SIMPLE".equals(input))
				return ReasonerVocabulary.RDFS_SIMPLE;
			if ("RDFS_DEFAULT".equals(input))
				return ReasonerVocabulary.RDFS_DEFAULT;
			if ("RDFS_FULL".equals(input))
				return ReasonerVocabulary.RDFS_FULL;
			return null;
		}
		
		private static RuleMode returnRuleModel(String input) {
			if ("FORWARD".equals(input))
				return GenericRuleReasoner.FORWARD;
			if ("FORWARD_RETE".equals(input))
				return GenericRuleReasoner.FORWARD_RETE;
			if ("BACKWARD".equals(input))
				return GenericRuleReasoner.BACKWARD;
			if ("HYBRID".equals(input))
				return GenericRuleReasoner.HYBRID;
			
			// ...
			return null;
		}

}
