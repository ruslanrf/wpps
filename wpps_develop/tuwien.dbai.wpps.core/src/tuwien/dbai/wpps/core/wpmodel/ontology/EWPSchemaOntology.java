package tuwien.dbai.wpps.core.wpmodel.ontology;

import tuwien.dbai.wpps.core.wpmodel.ontology.impllib.JenaModelsUtilLib;
import tuwien.dbai.wpps.ontschema.BlockOnt;
import tuwien.dbai.wpps.ontschema.CommonOnt;
import tuwien.dbai.wpps.ontschema.DOMOnt;
import tuwien.dbai.wpps.ontschema.InterfaceModelOnt;
import tuwien.dbai.wpps.ontschema.LogicalModelOnt;
import tuwien.dbai.wpps.ontschema.QltBlockModelOnt;
import tuwien.dbai.wpps.ontschema.QntBlockModelOnt;
import tuwien.dbai.wpps.ontschema.QntVisualModelOnt;
import tuwien.dbai.wpps.ontschema.ResultOnt;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;
import tuwien.dbai.wpps.ontschema.StructVisualModelOnt;

	/**
	 *	Ontologies which contain meta-data.
	 *	This enum provide file path to them, and cut namespace. 
	 *
	 * @created: Apr 1, 2011 5:31:13 PM
	 * @author Ruslan (ruslanrf@gmail.com)
	 *
	 */
	public enum EWPSchemaOntology {
		COMMON_ONT(CommonOnt.NS),
		
		BLOCK_ONT(BlockOnt.NS),
	
		QNT_BLOCK_MODEL_ONT(QntBlockModelOnt.NS),
		QLT_BLOCK_MODEL_ONT(QltBlockModelOnt.NS),
	
		STRUCT_BLOCK_GEOMETRIC_MODEL_ONT(StructBlockGeomModelOnt.NS),
		
		STRUCT_VISUAL_MODEL_ONT(StructVisualModelOnt.NS),
		
		QNT_VISUAL_MODEL_ONT(QntVisualModelOnt.NS),
	
		INTERFACE_MODEL_ONT(InterfaceModelOnt.NS),
		
		DOM_ONT(DOMOnt.NS),
		
		LOGICAL_MODEL_ONT(LogicalModelOnt.NS);
		
//		RESULTS_MODEL_ONT(ResultOnt.NS);
		
		EWPSchemaOntology(String ns) {
			this.ns = ns;
		}
		
		private String ns;
		
		public String getNS() {
			return ns;
		}
		
		/**
		 * Get name space of ontology without last symbol "/" or "#"
		 * @param ns
		 * @return
		 */
		public String getCutNS() {
			return JenaModelsUtilLib.getCutNS(ns);
		}
			
		
//		COMMON_ONT(CommonOnt.NS, true, WPPSCoreActivator.getPluginURL().toExternalForm()+"ontologies/common.owl"),
//		
//		BLOCK_ONT(BlockOnt.NS, true, WPPSCoreActivator.getPluginURL().toExternalForm()+"ontologies/block.owl"),
//	
//		QNT_BLOCK_MODEL_ONT(QntBlockModelOnt.NS, true, WPPSCoreActivator.getPluginURL().toExternalForm()+"ontologies/qnt-block-model.owl"),
//		QLT_BLOCK_MODEL_ONT(QltBlockModelOnt.NS, true, WPPSCoreActivator.getPluginURL().toExternalForm()+"ontologies/qlt-block-model.owl"),
//	
//		BLOCK_GEOMETRIC_OBJECT_ONT(BlockGeomObjectOnt.NS, true, WPPSCoreActivator.getPluginURL().toExternalForm()+"ontologies/block-geometric-object.owl"),
//	
////		GEOMETRIC_OBJECT_ONT(GeomObjectOnt.NS, true, WPPSCoreActivator.getPluginURL().toExternalForm()+"ontologies/geometric-object.owl"),
//	
////		QNT_GEOMETRIC_MODEL_ONT(QntGeomModelOnt.NS, true, WPPSCoreActivator.getPluginURL().toExternalForm()+"ontologies/qnt-geometric-model.owl"),
////		QLT_GEOMETRIC_MODEL_ONT(QltGeomModelOnt.NS, true, WPPSCoreActivator.getPluginURL().toExternalForm()+"ontologies/qlt-geometric-model.owl"),
//	
//		WPBI_MODEL_ONT(WPBIModelOnt.NS, true, WPPSCoreActivator.getPluginURL().toExternalForm()+"ontologies/wpbi-model.owl"),
//		DOM_ONT(DOMOnt.NS, true, WPPSCoreActivator.getPluginURL().toExternalForm()+"ontologies/dom.owl"),
//		
//		LOGICAL_MODEL_ONT(DOMOnt.NS, true, WPPSCoreActivator.getPluginURL().toExternalForm()+"ontologies/logical-model.owl");
//		
//		private static final Logger log = Logger.getLogger(EWPSchemaOntology.class);
//		
//		EWPSchemaOntology(String ns, boolean hasAnotherLocation, String filePath) {
//			this.ns = ns;
//			_hasAnotherLocation = hasAnotherLocation;
//			this.filePath = filePath;
//		}
//		
//		private String filePath;
//		public String getFilePathStr() {
//			return filePath;
//		}
//		public URL getFilePathUrl() {
//			try {
//				return new URL(filePath);
//			} catch (MalformedURLException e) {
//				throw new GeneralUncheckedException(log, e.getMessage());
//			}
//		}
//		
//		private boolean _hasAnotherLocation;
//		public boolean hasAnotherLocation() {
//			return _hasAnotherLocation;
//		}
//		
//		private String ns;
//		
//		public String getNS() {
//			return ns;
//		}
//		
//		/**
//		 * Get name space of ontology without last symbol "/" or "#"
//		 * @param ns
//		 * @return
//		 */
//		public String getCutNS() {
//			return JenaModelsUtil.getCutNS(ns);
//		}
		
	}