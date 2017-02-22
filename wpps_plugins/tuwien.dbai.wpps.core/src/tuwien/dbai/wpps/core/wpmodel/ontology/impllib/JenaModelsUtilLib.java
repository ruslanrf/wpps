/**
 * File name: JenaModelsUtil.java
 * @created: Apr 10, 2011 10:07:22 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.ontology.impllib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.SessionUniqueIDGenerator;

import com.google.common.base.Preconditions;
import com.hp.hpl.jena.ontology.ObjectProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.Ontology;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner.RuleMode;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.ReasonerVocabulary;

/**
 * @created: Apr 10, 2011 10:07:22 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public final class JenaModelsUtilLib {
@SuppressWarnings("unused")
private static final Logger log = Logger.getLogger(JenaModelsUtilLib.class);

	// =================================
	// === Create new empty ontology ===
	// =================================
	
	public static OntModel createOwlModel(String NS, OntModelSpec spec, boolean loadSchemata, String[] schemaBase, String[] altSchemaUri) {
		Preconditions.checkState(schemaBase!=null && schemaBase.length == altSchemaUri.length, "Arrays must be of the same size");
		
if (log.isTraceEnabled()) {
log.trace("schemaBase: "+Arrays.toString(schemaBase)+" altSchemaUri: "+Arrays.toString(altSchemaUri));
}
		
		OntModel model = ModelFactory.createOntologyModel(spec);
		
		if (schemaBase !=null && schemaBase.length >0) {
			final OntDocumentManager dm = model.getDocumentManager();
			dm.setProcessImports(false);
			for (int i=0; i<schemaBase.length; i++) {
				if (altSchemaUri[i] !=null)
					dm.addAltEntry(schemaBase[i], altSchemaUri[i]);
			}
			
			final Ontology ontology = model.createOntology(NS);
			for (int i=0; i<schemaBase.length; i++) {
				ontology.addImport(model.createOntResource(schemaBase[i]));
			}
//System.err.println(model.getOntology(NS));
			
			dm.setProcessImports(true);
			if (loadSchemata)
				model.loadImports();
		}
		
		return model;
	}
	
	public static InfModel createRdfModel(String NS, String spec, boolean loadSchemata, String[] schemaBase, String[] altSchemaUri) {
		
		InfModel res = null;
		
		final Reasoner rdfReasoner = ReasonerRegistry.getRDFSReasoner();
		rdfReasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel, spec);
		
		if (loadSchemata && schemaBase !=null && schemaBase.length>0) {
			Model schemaModel = ModelFactory.createDefaultModel();
			for (int i=0; i<schemaBase.length; i++) {
				String path = null;
				if (altSchemaUri[i] == null)
					path = getCutNS(schemaBase[i]);
				else
					path = altSchemaUri[i];
				schemaModel.add(FileManager.get().loadModel(path));
			}
			res = ModelFactory.createInfModel(rdfReasoner, schemaModel, ModelFactory.createDefaultModel());
		}
		else
			res = ModelFactory.createInfModel(rdfReasoner, ModelFactory.createDefaultModel());
		
		return res;
		
//	    reasoner.setParameter(ReasonerVocabulary.PROPsetRDFSLevel, 
//                ReasonerVocabulary.RDFS_SIMPLE);
//
//or by constructing an RDF configuration description and passing that to the RDFSRuleReasonerFactory e.g.
//
//Resource config = ModelFactory.createDefaultModel()
//            .createResource()
//            .addProperty(ReasonerVocabulary.PROPsetRDFSLevel, "simple");
//Reasoner reasoner = RDFSRuleReasonerFactory.theInstance()Create(config);
	}
	
	public static final InfModel addRuleReasoningLayer(Model model, String fileAbsPath, RuleMode ruleMode) {
		GenericRuleReasoner r = new GenericRuleReasoner(Rule.rulesFromURL(fileAbsPath));
// TODO Temporary!!!
//r.setTransitiveClosureCaching(true);
		r.setMode(ruleMode);
		InfModel infModel = ModelFactory.createInfModel(r, model);
		return infModel;
	}

	// =================================
	// ===                           ===
	// =================================

	
	// =========================
	// === Helping functions ===
	// =========================
	
	/**
	 * Get name space of ontology without last symbol "/" or "#"
	 * @param ns
	 * @return
	 */
	public static String getCutNS(String ns) {
		return ns.substring(0, ns.length()-1);
	}
	
	public static String genOntologyNS(String base) {
		return base+UUID.randomUUID()+"#";
	}
	
	/**
	 * Generate ID for new resoures
	 * @param ontologyNS
	 * @return
	 */
	public static String genRdfResourceId(final String ontologyNS) {
		return ontologyNS+"r"+SessionUniqueIDGenerator.genHexID();
	}
	
	/**
	 * Create new instance in the ontology
	 * 
	 * @param cls --- class of the instance to be created
	 * @param model --- model where instance will be created
	 * @param ontologyNS --- namespace of the ontology, based in this namespace the name for the instance will be generated
	 * @return --- created instance
	 */
	public final static Resource createNewInstance(final Resource cls, final Model model, final String ontologyNS) {
		return model.createResource(genRdfResourceId(ontologyNS), cls);
	}
	
	public final static Resource createNewClass(final Resource parentCls, final Model model, final String ontologyNS) {
		final Resource cl = model.createResource(genRdfResourceId(ontologyNS));
		model.add(cl, RDFS.subClassOf, parentCls);
		return cl;
	}
	
	public final static Resource createNewClass(final String uri, final Resource parentCls
			, final Model model) {
		final Resource cl = model.createResource(uri);
		model.add(cl, RDFS.subClassOf, parentCls);
		return cl;
	}
	
	public final static OntClass createNewOntClass(final OntClass parentCls, final OntModel model, final String ontologyNS) {
		final OntClass cl = model.createClass(genRdfResourceId(ontologyNS));
		cl.addSuperClass(parentCls);
		return cl;
	}
	
	public final static OntClass createNewOntClass(final String uri, final OntClass parentCls
			, final OntModel model) {
		final OntClass cl = model.createClass(uri);
		cl.addSuperClass(parentCls);
		return cl;
	}
	
	public final static Property createNewProperty(final Property parentProp, final Model model, final String ontologyNS) {
		final Property prop = model.createProperty(genRdfResourceId(ontologyNS));
		model.add(prop, RDFS.subPropertyOf, parentProp);
		return prop;
	}
	
	public final static ObjectProperty createNewObjectProperty(final ObjectProperty parentProp, final OntModel model, final String ontologyNS) {
		final ObjectProperty prop = model.createObjectProperty(genRdfResourceId(ontologyNS));
		model.add(prop, RDFS.subPropertyOf, parentProp);
		return prop;
	}
	
	public static final void removeResourceAsSubjectOrObject(final Resource res, final Model model) {
//		model.listStatements(new SimpleSelector(res, null, (RDFNode)null) {
//			@Override
//			public boolean selects(Statement s) {
//				final Resource r = s.getResource();
//				if (r.canAs(Container.class)) {
//					model.removeAll(r, null, (RDFNode)null);
//					model.removeAll(null, null, r);
//				}
//				model.remove(s);
//			}
//		});
//		model.removeAll(res, RDF.type, (RDFNode)null);
		model.removeAll(res, null, (RDFNode)null);
		model.removeAll(null, null, res);
	}
	
	public static final void removeResourcesAsSubjectOrObject(final Collection<Resource> ress, final Model model) {
		final Iterator<Resource> iter = ress.iterator();
		while (iter.hasNext()) {
			removeResourceAsSubjectOrObject(iter.next(), model);
		}
	}
	
	/**
	 * Model is saved in RDF/XML-ABBREV format
	 * @param model
	 * @param file
	 */
	public static final void saveModel(Model model, File file) {
		File f = null;
		try {
			f = file;
			FileOutputStream ofs = new FileOutputStream(f);
			model.write(ofs, "RDF/XML-ABBREV");
			if (log.isTraceEnabled()) log.trace("Model was succesfully dumped into "+f);
					} catch (FileNotFoundException ex) {
			log.warn("Model was not succesfully dumped into "+f);
						ex.printStackTrace();
		}
	}
	
	
	// =========================
	// ===                   ===
	// =========================
	
	
	
////	/**
////	 * Base to generate a NS for the new instance of the ontology
////	 */
////	private static final String ONTOLOGY_INSTANCE_BASE = "http://www.dbai.tuwien.ac.at/proj/wpps/ontologies/inst";
//	
//	public static final String genNewOntologyInstNS(String ontologyInstanceBase) {
//		return ontologyInstanceBase+"-"+UUID.randomUUID()+"#";
//	}
//	
//	
//	/**
//	 * Create ontology based on Jena framework.
//	 * 
//	 * @param specification Specification for the ontology to be created.
//	 * @param schemataArr Array of meta-ontologies (schemata) corresponding to the ontology to be created. 
//	 * @return Ontology.
//	 */
//	public static final Model createJenaModel(AJenaModelSpecification specification, EWPSchemaOntology[] schemataArr) {
//		if (specification instanceof JenaOntSpecification)
//			return createJenaOntology((JenaOntSpecification)specification, schemataArr);
//		else if (specification instanceof SimpleJenaModelSpecification)
//			return createJenaModel((SimpleJenaModelSpecification)specification, schemataArr);
//		else
//			throw new UnknownType(log, specification);
//	}
//	
//	/**
//	 * Create Jena Ontology which correspond to the provided specification.
//	 * @param specification
//	 * @param schemataArr
//	 * @return
//	 * @see #createJenaModel(AJenaModelSpecification, EWPSchemaOntology[])
//	 */
//	public static final OntModel createJenaOntology(JenaOntSpecification specification, EWPSchemaOntology[] schemataArr) {
//		OntModel ontModel = null;
//		
//		ontModel = ModelFactory.createOntologyModel(specification.getOntModelSpec());
//		
//		// importing schemata
//		if ( schemataArr != null && schemataArr.length > 0 ) {
//			if (specification.getWayOfLoadingImports() == EWayOfLoadingImports.IMPORT_AS_SCHEMA) {
//				final OntDocumentManager dm = ontModel.getDocumentManager();
//				dm.setProcessImports(false);
//				
//				for (int i=0; i<schemataArr.length; i++) {
//					if (schemataArr[i].hasAnotherLocation())
//						dm.addAltEntry(schemataArr[i].getCutNS(), schemataArr[i].getFilePathStr());
//					final Ontology ontology = ontModel.createOntology(specification.getOntologyNS());
//					ontology.addImport(ontModel.createOntResource(schemataArr[i].getCutNS()));
//				}
//				
//				dm.setProcessImports(specification.getProcessImports());
//			
//				if (specification.getLoadImports())
//					ontModel.loadImports();
//			} else
//				if (specification.getWayOfLoadingImports() == EWayOfLoadingImports.LOAD_INTO_THE_MODEL) {
//					
//					for (int i=0; i<schemataArr.length; i++) {
//						String path = null;
//						if (schemataArr[i].hasAnotherLocation())
//							path = schemataArr[i].getFilePathStr();
//						else
//							path = schemataArr[i].getCutNS();
//							ontModel.add(FileManager.get().loadModel(path));
//					}
//				}
//		}
//		
//		return ontModel;
//	}
//	
//	/**
//	 * Create simple Jena model which is RDF model without any reasoner applied.
//	 * @param params
//	 * @param schemataArr Schemata of the meta-ontologies.
//	 * @return Ontology.
//	 * @see #createJenaModel(AJenaModelSpecification, EWPSchemaOntology[])
//	 */
//	public static final Model createJenaModel(SimpleJenaModelSpecification params, EWPSchemaOntology[] schemataArr) {
//		Model model = ModelFactory.createDefaultModel();
//		
//		// create resource Ontology even if it is not OWL model
//		model.createResource(params.getOntologyNS(), OWL.Ontology);
//		
//		if ( schemataArr != null && schemataArr.length > 0 
//			&& params.getWayOfLoadingImports() == EWayOfLoadingImports.LOAD_INTO_THE_MODEL ) {
//				for (int i=0; i<schemataArr.length; i++) {
//					String path = null;
//					if (schemataArr[i].hasAnotherLocation())
//						path = schemataArr[i].getFilePathStr();
//					else
//						path = schemataArr[i].getCutNS();
//					model.add(FileManager.get().loadModel(path));
//			}
//		}
//		
//		return model;
//	}
//	
//	/** Generate ID for new individual
//	 * 
//	 * @return ID
//	 */
//	public final static String genNameForNewIndividual(String ontologyNS) {
//		return ontologyNS+SessionUniqueIDGenerator.genHexID();
//	}
//	
//	/**
//	 * Create new instance in the ontology
//	 * 
//	 * @param cls --- class of the instance to be created
//	 * @param model --- model where instance will be created
//	 * @param ontologyNS --- namespace of the ontology, based in this namespace the name for the instance will be generated
//	 * @return --- created instance
//	 */
//	public final static Resource createNewInstance(Resource cls, Model model, String ontologyNS) {
//		return model.createResource(genNameForNewIndividual(ontologyNS), cls);
//	}
	
}
