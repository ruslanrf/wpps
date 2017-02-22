/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.ontology.impllib;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;

/**
 * Write top ontologies
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 28, 2012 1:54:37 PM
 */
public class OntologyWriter {
	private static final Logger log  = Logger.getLogger(OntologyWriter.class);

	private final WPOntSubModels models;
	
	public OntologyWriter(
			WPOntSubModels models
			) {
		this.models = models;
	}
	
	public void write(File folder) {
		Map<EWPOntSubModel, OntModelAdp> modelOntMap = models.getOntologyAdapterMap();
		Map<OntModelAdp, String> ontNameMap = new HashMap<OntModelAdp, String>(10);
		for (Entry<EWPOntSubModel, OntModelAdp> e : modelOntMap.entrySet()) {
			String val = e.getKey().name();
			if (ontNameMap.containsKey(e.getValue()))
				val = ontNameMap.get(e.getValue())+val;
			ontNameMap.put(e.getValue(), val);
		}
		for (Entry<OntModelAdp, String> e : ontNameMap.entrySet()) {
			JenaModelsUtilLib.saveModel(e.getKey().getTopRdfModel()
					, new File(folder, e.getValue()+".rdf"));
//			File f = null;
//			try {
//				f = new File(folder, e.getValue()+".rdf");
//				FileOutputStream ofs = new FileOutputStream(f);
//				e.getKey().getRdfModel().write(ofs, "RDF/XML-ABBREV");
//if (log.isTraceEnabled()) log.trace("Model "+e.getValue()+ " was succesfully dumped into "+f);
//			} catch (FileNotFoundException ex) {
//log.warn("Model "+e.getValue()+ " was not succesfully dumped into "+f);
//				ex.printStackTrace();
//			}
		}
	}
	
}
