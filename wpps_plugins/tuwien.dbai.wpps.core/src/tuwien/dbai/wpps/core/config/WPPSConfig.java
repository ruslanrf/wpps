/**
 * 
 */
package tuwien.dbai.wpps.core.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import tuwien.dbai.wpps.common.geometry.Point2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPSchemaOntology;

import com.google.inject.Singleton;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner.RuleMode;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 22, 2011 6:23:32 PM
 */
@Singleton
public final class WPPSConfig {
	
	// ==============================
	// === Ontology configuration ===
	// ==============================
	
	/**
	 * Element {@code ontology-instance-ns-gen-base} first part of the namespaces generated for the ontologies.
	 * Optional parameter.
	 */
	private String ontologyInstanceNSGenBase = null;
	
	/**
	 * Elements {@code ontology/sub-model}. Set of namespaces of new ontologies.
	 * At least 1 namespace should be specified.
	 */
	private Set<String> createOntology = new HashSet<String>();
	
	/**
	 * Mapping between namespaces of ontologies ({@linkplain #createOntology})
	 * and the UOM-submodels to be created.
	 */
	private Map<String, EWPOntSubModel[]> ontologyModels = new HashMap<String, EWPOntSubModel[]>();
	
	/**
	 * Ontology representation formalism
	 */
	public enum EOntologyFormalism {RDF, OWL};
	/**
	 * Mapping between namespaces of ontologies ({@linkplain #createOntology})
	 * and the UOM-submodels to be created.
	 */
	private Map<String, EOntologyFormalism> ontologyType = new HashMap<String, EOntologyFormalism>();
	
	public enum EReasonerType {/* RDF: */ NONE, RDFS, RULE_BASED
		, /* OWL: */ OWL_LITE, OWL_DL, OWL_FULL
		, /* RDF or OWL: */ JENA_REASONER};
	private Map<String, EReasonerType> ontologyReasonerType = new HashMap<String, EReasonerType>();
	
//	"full", "default", "simple" ...  http://jena.sourceforge.net/inference/#rdfs http://www.openjena.org/javadoc/com/hp/hpl/jena/ontology/OntModelSpec.html http://www.openjena.org/javadoc/com/hp/hpl/jena/reasoner/ReasonerRegistry.html
	private Map<String, String> jenaRDFSReasoner = new HashMap<String, String>();
	
	private Map<String, OntModelSpec> jenaOWLReasoner = new HashMap<String, OntModelSpec>();
	
	private Map<String, String> jenaOWLReasonerName = new HashMap<String, String>();
	
	private Map<String, Boolean> loadSchema = new HashMap<String, Boolean>();
	
	private Map<String, RuleMode> jenaModelRules = new HashMap<String, RuleMode>();
	
	private Map<String, String> jenaModelRulesUri = new HashMap<String, String>();
	
	private Map<EWPSchemaOntology, String> altSchemaUri = new HashMap<EWPSchemaOntology, String>();
	
	private boolean simplification = false;
	
	/**
	 * @return the ontologyInstanceNSGenBase
	 */
	public String getOntologyInstanceNSGenBase() {
		return ontologyInstanceNSGenBase;
	}
	/**
	 * @param ontologyInstanceNSGenBase the ontologyInstanceNSGenBase to set
	 */
	public void setOntologyInstanceNSGenBase(String ontologyInstanceNSGenBase) {
		this.ontologyInstanceNSGenBase = ontologyInstanceNSGenBase;
	}
	/**
	 * @return the createOntology
	 */
	public Set<String> getCreateOntology() {
		return createOntology;
	}
//	/**
//	 * @param createOntology the createOntology to set
//	 */
//	public void setCreateOntology(Set<String> createOntology) {
//		this.createOntology = createOntology;
//	}
	/**
	 * @return the ontologyModels
	 */
	public Map<String, EWPOntSubModel[]> getOntologyModels() {
		return ontologyModels;
	}
//	/**
//	 * @param ontologyModels the ontologyModels to set
//	 */
//	public void setOntologyModels(Map<String, EWPOntModel[]> ontologyModels) {
//		this.ontologyModels = ontologyModels;
//	}
	/**
	 * @return the ontologyType
	 */
	public Map<String, EOntologyFormalism> getOntologyType() {
		return ontologyType;
	}
//	/**
//	 * @param ontologyType the ontologyType to set
//	 */
//	public void setOntologyType(Map<String, EOntologyType> ontologyType) {
//		this.ontologyType = ontologyType;
//	}
	/**
	 * @return the ontologyReasoner
	 */
	public Map<String, EReasonerType> getOntologyReasonerType() {
		return ontologyReasonerType;
	}
//	/**
//	 * @param ontologyReasoner the ontologyReasoner to set
//	 */
//	public void setOntologyReasonerType(Map<String, EReasonerType> ontologyReasonerType) {
//		this.ontologyReasonerType = ontologyReasonerType;
//	}
	
	/**
	 * @return the jenaRDFSReasoner
	 */
	public Map<String, String> getJenaRDFSReasoner() {
		return jenaRDFSReasoner;
	}
//	/**
//	 * @param jenaRDFSReasoner the jenaRDFSReasoner to set
//	 */
//	public void setJenaRDFSReasoner(Map<String, String> jenaRDFSReasoner) {
//		this.jenaRDFSReasoner = jenaRDFSReasoner;
//	}
	/**
	 * @return the jenaOWLReasoner
	 */
	public Map<String, OntModelSpec> getJenaOWLReasoner() {
		return jenaOWLReasoner;
	}
	
	public Map<String, String> getJenaOWLReasonerName() {
		return jenaOWLReasonerName;
	}
	
//	/**
//	 * @param jenaOWLReasoner the jenaOWLReasoner to set
//	 */
//	public void setJenaOWLReasoner(Map<String, OntModelSpec> jenaOWLReasoner) {
//		this.jenaOWLReasoner = jenaOWLReasoner;
//	}
	
	/**
	 * @return the loadSchema
	 */
	public Map<String, Boolean> getLoadSchema() {
		return loadSchema;
	}
//	/**
//	 * @param loadSchema the loadSchema to set
//	 */
//	public void setLoadSchema(Map<String, Boolean> loadSchema) {
//		this.loadSchema = loadSchema;
//	}
	
	public Map<String, RuleMode> getJenaModelRules() {
		return jenaModelRules;
	}
	
	public Map<String, String> getJenaModelRulesUri() {
		return jenaModelRulesUri;
	}
	
	
	/**
	 * @return the altSchemaUrl
	 */
	public Map<EWPSchemaOntology, String> getAltSchemaUri() {
		return altSchemaUri;
	}
//	/**
//	 * @param altSchemaUri the altSchemaUrl to set
//	 */
//	public void setAltSchemaUri(Map<EWPSchemaOntology, String> altSchemaUri) {
//		this.altSchemaUri = altSchemaUri;
//	}
	
	/**
	 * @return the simplification
	 */
	public boolean isSimplification() {
		return simplification;
	}
	/**
	 * @param simplification the simplification to set
	 */
	public void setSimplification(boolean simplification) {
		this.simplification = simplification;
	}
	
	// ==============================
	// ===                        ===
	// ==============================

	
	// ========================================
	// === Ontology Instances configuration ===
	// ========================================
	
	private Set<Object> createInOntology = new HashSet<Object>();
	
//	/**
//	 * NONE --- all instances are created independently.
//	 * INTERFACE_MODEL --- all instances are created for the IM Element,
//	 * specified in {@linkplain WPPSConfig#createInOntology}.
//	 * This property defines an intersection of types which should be taken into account
//	 * during the instance creation.
//	 */
//	public enum EInstanceCreationDependencies {NONE, INTERFACE_MODEL};
//	private EInstanceCreationDependencies instanceCreationDependencies = null;
	
	private Set<Object> computeByRequestBasedOnQntFeatures = new HashSet<Object>();
	
	private Set<Object> computeByRequestBasedOnFundFeatures = new HashSet<Object>();
	
	private Set<Object> compositeBasicDependence = new HashSet<Object>();
	
	private Set<Object> supportInOntology = new HashSet<Object>();
	
	public enum EOneToManyRelation {IN_COLLECTION, SEPARATE_STATEMENTS}
	private Map<EWPOntSubModel, EOneToManyRelation> structOneToManyRelationMap
		= new HashMap<EWPOntSubModel, EOneToManyRelation>();
	
	public enum ELocation {ALL, OVERLAPS_VIEW_PORT, INSIDE_VIEW_PORT, OVERLAPS_AREA, INSIDE_AREA}
	private ELocation location = null;
	
	private Rectangle2D area = new Rectangle2D(Point2D.getUndefinedPoint(), Point2D.getUndefinedPoint());
	
	/**
	 * @return the createInOntology
	 */
	public Set<Object> getCreateInOntology() {
		return createInOntology;
	}
	
//	public EInstanceCreationDependencies getInstanceCreationDependencies() {
//		return instanceCreationDependencies;
//	}
//	/**
//	 * @param oneToManyRelations the oneToManyRelations to set
//	 */
//	public void setInstanceCreationDependencies(EInstanceCreationDependencies instanceCreationDependencies) {
//		this.instanceCreationDependencies = instanceCreationDependencies;
//	}
	
	public Set<Object> getComputeByRequestBasedOnQntFeatures() {
		return computeByRequestBasedOnQntFeatures;
	}
	
	public Set<Object> getComputeByRequestBasedOnFundFeatures() {
		return computeByRequestBasedOnFundFeatures;
	}
	
	public Set<Object> getCompositeBasicDependence() {
		return compositeBasicDependence;
	}
	
	public Set<Object> getSupportInOntology() {
		return supportInOntology;
	}
	
	/**
	 * @return the oneToManyRelations
	 */
	public Map<EWPOntSubModel, EOneToManyRelation> getStructOneToManyRelationMap() {
		return structOneToManyRelationMap;
	}
	public ELocation getLocation() {
		return location;
	}
	public void setLocation(ELocation location) {
		this.location = location;
	}
	/**
	 * @return the area
	 */
	public Rectangle2D getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(Rectangle2D area) {
		this.area = area;
	}
	
	
	// ========================================
	// === BGM Ontology Instances configuration ===
	// ========================================
	
	public enum EClientRectangleCreation {
	/**
	 * Client rectangle is always created
	 */
	ALL
	, /**
	 * Client rectangle is created only if there are more than 1 rectangle for the box.
	 */
	AUTO
	, NONE}
	private EClientRectangleCreation clientRectangleCreation = null;
	
	/**
	 * TODO: add NONE value (computation without fuzziness).
	 */
	public enum EQltBMBorderMuType{
		/**
	 * Interval definition of the fuzzy function of equality between intervals.
	 */
	INTERVAL};
	private EQltBMBorderMuType qltBMBorderMuType = null;
	
	private double[] qltBMLeftBorderInterval = new double[]{Double.NaN, Double.NaN};
	private double[] qltBMRightBorderInterval = new double[]{Double.NaN, Double.NaN};
	private double[] qltBMCenterInterval = new double[]{Double.NaN, Double.NaN};
	
	private double[] qltBMBorderNu = new double[]{Double.NaN, Double.NaN};// center, delta
	
//	public enum EZOrderCreation {CREATE_DRAW_ID, CREATE_LAYER_ID}
//	private Set<EZOrderCreation> zOrderCreation = new HashSet<WPPSConfig.EZOrderCreation>();
	
	/**
	 * @return the clientRectangle
	 */
	public EClientRectangleCreation getClientRectangleCreation() {
		return clientRectangleCreation;
	}
	/**
	 * @param clientRectangle the clientRectangle to set
	 */
	public void setClientRectangleCreation(EClientRectangleCreation clientRectangle) {
		this.clientRectangleCreation = clientRectangle;
	}
	/**
	 * @return the qltBMBorderMuType
	 */
	public EQltBMBorderMuType getQltBMBorderMuType() {
		return qltBMBorderMuType;
	}
	/**
	 * @param qltBMBorderMuType the qltBMBorderMuType to set
	 */
	public void setQltBMBorderMuType(EQltBMBorderMuType qltBMBorderMuType) {
		this.qltBMBorderMuType = qltBMBorderMuType;
	}
	/**
	 * @return the qltBMLeftBorderInterval
	 */
	public double[] getQltBMLeftBorderInterval() {
		return qltBMLeftBorderInterval;
	}
	/**
	 * @return the qltBMRightBorderInterval
	 */
	public double[] getQltBMRightBorderInterval() {
		return qltBMRightBorderInterval;
	}
	public double[] getQltBMCenterInterval() {
		return qltBMCenterInterval;
	}
	/**
	 * @return the qltBMBorderNu
	 */
	public double[] getQltBMBorderNu() {
		return qltBMBorderNu;
	}
//	/**
//	 * @return the zOrderCreation
//	 */
//	public Set<EZOrderCreation> getzOrderCreation() {
//		return zOrderCreation;
//	}
	
	// ========================================
	// ===                                  ===
	// ========================================
	
}
