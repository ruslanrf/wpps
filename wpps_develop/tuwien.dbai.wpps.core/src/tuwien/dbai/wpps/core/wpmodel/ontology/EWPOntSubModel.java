/**
 * File name: OntModels.java
 * @created: Apr 3, 2011 6:00:47 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.ontology;


/**
 * Ontologies with data corresponding to the document.
 * Visual models, MLModel, DOM-model + logical models.
 *
 * @created: Apr 3, 2011 6:00:47 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public enum EWPOntSubModel {

	STRUCT_BLOCK_GEOM_MODEL(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.BLOCK_ONT, EWPSchemaOntology.STRUCT_BLOCK_GEOMETRIC_MODEL_ONT), // inclides block ontology
	
	QNT_BLOCK_MODEL(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.BLOCK_ONT, EWPSchemaOntology.QNT_BLOCK_MODEL_ONT),
	QLT_BLOCK_MODEL(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.BLOCK_ONT, EWPSchemaOntology.QLT_BLOCK_MODEL_ONT),
	
	STRUCT_VISUAL_MODEL(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.STRUCT_VISUAL_MODEL_ONT),
	
	QNT_VISUAL_MODEL(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.STRUCT_VISUAL_MODEL_ONT, EWPSchemaOntology.QNT_VISUAL_MODEL_ONT),

//	GEOMETRIC_OBJECT_STRUCT_MODEL(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.GEOMETRIC_OBJECT_ONT),

//	QNT_GEOMETRIC_MODEL(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.GEOMETRIC_OBJECT_ONT, EWPSchemaOntology.QNT_GEOMETRIC_MODEL_ONT),
//	QLT_GEOMETRIC_MODEL(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.GEOMETRIC_OBJECT_ONT, EWPSchemaOntology.QLT_GEOMETRIC_MODEL_ONT),

	INTERFACE_MODEL(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.INTERFACE_MODEL_ONT),
	DOM(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.DOM_ONT),
	
	LOGICAL_MODEL(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.LOGICAL_MODEL_ONT);
	
//	RESULTS_MODEL(EWPSchemaOntology.COMMON_ONT, EWPSchemaOntology.BLOCK_ONT
//			, EWPSchemaOntology.STRUCT_BLOCK_GEOMETRIC_MODEL_ONT, EWPSchemaOntology.RESULTS_MODEL_ONT);
	
	private final EWPSchemaOntology[] ontArr;
	
	public final EWPSchemaOntology[] getSchemaOntologyArr() {
		return ontArr;
	}
	
	EWPOntSubModel(EWPSchemaOntology... arr) {
		ontArr = arr;
	}
	
//	@Override
//	public String toString() {
//		return this.name();
//	}
	
	
	
}
