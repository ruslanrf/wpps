/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.vm;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMCSS2Properties;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateObject;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.EVMInstType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.factory.VMRdfInstanceFactory;
import tuwien.dbai.wpps.mozcommon.MozStringUtils;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jan 18, 2012 1:46:26 PM
 */
public class QntVMRdfInstFactory {
	private static final Logger log = Logger.getLogger(QntVMRdfInstFactory.class);

	private final WhetherCreateObject whetherCreateInstance;
	private final VMRdfInstanceFactory vmRdfInstanceFactory;
	private final OntModelAdp ontModelAdp;
	
	public QntVMRdfInstFactory(
			final WhetherCreateObject whetherCreateInstance
			, final VMRdfInstanceFactory vmRdfInstanceFactory
			, final OntModelAdp ontModelAdp
			) {
if (log.isTraceEnabled()) log.trace("Constructing QntVM RDF instance factory");
		
		this.whetherCreateInstance = whetherCreateInstance;
		this.vmRdfInstanceFactory = vmRdfInstanceFactory;
		this.ontModelAdp = ontModelAdp;
	}
	
	// TODO: move to other class
	public void createPlainVisualObject(final Resource rdfInst, final nsIDOMCSS2Properties props) {
		if (whetherCreateInstance.apply(EVMInstType.PLAIN_VISUAL_OBJECT)) {
			vmRdfInstanceFactory.createPlainVisualObject(rdfInst
					, MozStringUtils.getTColorFromCSSAttributeColor(props.getColor())
					, MozStringUtils.getTColorFromCSSAttributeBGColor(props.getBackgroundColor()));
			if (log.isTraceEnabled()) 
				log.trace(TSForLog.getTS(log)+"addAttributesForPlainVisualObject():: "+"rdfInst: "+rdfInst
		+" color: "+props.getColor()+" bg-color: "+props.getBackgroundColor());
		}
	}

}


