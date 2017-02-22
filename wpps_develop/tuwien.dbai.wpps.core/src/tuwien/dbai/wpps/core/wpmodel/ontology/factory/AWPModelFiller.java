/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.ontology.factory;

import java.util.Iterator;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.nav.IBrowserNavigator;
import tuwien.dbai.wpps.core.spatialindex.ISpatialIndexManager;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateAttrOrRelInOnt;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateObject;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.factory.BGMRdfInstanceFactory;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.factory.DOMRdfInstanceFactory;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.factory.IMRdfInstanceFactory;
import tuwien.dbai.wpps.core.wpmodel.physmodel.vm.instadp.factory.VMRdfInstanceFactory;

import com.google.inject.Inject;
import com.hp.hpl.jena.rdf.model.InfModel;

/**
 * Instance of this class must be injected before the use.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 28, 2011 2:59:30 PM
 */
//@Singleton cannot be applied for abstract type
public abstract class AWPModelFiller {
	private static final Logger log = Logger.getLogger(AWPModelFiller.class);

	protected IBrowserNavigator browserNav = null;
	
	protected WPOntSubModels uom = null;
	protected WPPSConfig config = null;
	
	protected WhetherCreateObject whetherCreateInstance;
	protected WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt;
	
	protected ISpatialIndexManager spatialIndexManager = null;
	protected RdfInstAdpFactoryWrap instAdpFactory = null;
	
	// TODO: check why do we need it.
	@Deprecated
	protected QntBlockImpl qntBlockImpl = null;
	
	// DOM instance factory
	protected DOMRdfInstanceFactory domRdfInstanceFactory;
		
	// BGM instance factory
	protected BGMRdfInstanceFactory bgmRdfInstanceFactory;
	
	// IM instance factory
	protected IMRdfInstanceFactory imRdfInstanceFactory;
	
	// VM instance factory
	protected VMRdfInstanceFactory vmRdfInstanceFactory;
	
	private boolean isFilled = false;
	private boolean isInited = false;
	/**
	 * @return the isFilled
	 */
	public boolean isFilled() {
		return isFilled;
	}
	public boolean isInited() {
		return isInited;
	}
	
	@Inject
	protected final void init(
			IBrowserNavigator browserNav
			,WPOntSubModels ontModel, // when we as for the WPOntSubModels, it means they will be created as an empty UOM.
			WPPSConfig config
			, final WhetherCreateObject whetherCreateInstance
			, final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt
			// DOM*
			, final DOMRdfInstanceFactory domRdfInstanceFactory
			// BBGM
			, QntBlockImpl qntBlockImpl
			, ISpatialIndexManager spatialIndexManager
			// SBGM
			, final BGMRdfInstanceFactory bgmRdfInstanceFactory
			// IM
			, final IMRdfInstanceFactory imRdfInstanceFactory
			// VM
			, final VMRdfInstanceFactory vmRdfInstanceFactory
			
			, RdfInstAdpFactoryWrap instAdpFactory) {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Initialization");
		if (isInited)
			throw new GeneralUncheckedException(log, "Filler has been initialized already.");
		this.browserNav = browserNav;
		this.uom = ontModel;
		this.config = config;
		
		this.whetherCreateInstance = whetherCreateInstance;
		this.whetherCreateAttrOrRelInOnt = whetherCreateAttrOrRelInOnt;
		
		this.qntBlockImpl = qntBlockImpl;

		this.spatialIndexManager = spatialIndexManager;

		this.domRdfInstanceFactory = domRdfInstanceFactory;
		this.bgmRdfInstanceFactory = bgmRdfInstanceFactory;
		this.imRdfInstanceFactory = imRdfInstanceFactory;
		
		this.vmRdfInstanceFactory = vmRdfInstanceFactory;
		
		this.instAdpFactory = instAdpFactory;
		
		isInited = true;
	}
	
	public final void fill() {
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"START. Filling WPModel");
		if (!isInited)
			throw new GeneralUncheckedException(log, "Filler has not been initialized yet.");
		if (isFilled)
			throw new GeneralUncheckedException(log, "Filler has been executed already.");
		_fill();
		
		Iterator<OntModelAdp> iter = uom.getOntModelAdpList().iterator();
		while (iter.hasNext()) {
			OntModelAdp oma = iter.next();
			if (oma.getTopRdfModel() instanceof InfModel) {
if (log.isTraceEnabled()) log.trace("START. Rebind "+oma);
				((InfModel)oma.getTopRdfModel()).rebind();
if (log.isTraceEnabled()) log.trace("FINISH. Rebind "+oma);
			}
		}
		
		isFilled = true;
if (log.isDebugEnabled()) log.debug(TSForLog.getTS(log)+"FINISH. Filling WPModel");
	}
	
	abstract protected void _fill();

}
