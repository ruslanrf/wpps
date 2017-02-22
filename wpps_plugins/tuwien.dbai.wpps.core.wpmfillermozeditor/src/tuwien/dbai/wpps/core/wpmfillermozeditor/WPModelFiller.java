/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMWindow;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.GeneralUncheckedException;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmfillermozeditor.bgm.DrawingAnalyzer;
import tuwien.dbai.wpps.core.wpmfillermozeditor.bgm.QntBMRdfInstFactory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.bgm.SBGMRdfInstFactory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.bgm.SpatialIndexFillHelper;
import tuwien.dbai.wpps.core.wpmfillermozeditor.dom.DOMRdfInstFactory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.im.IMRdfInstFactory;
import tuwien.dbai.wpps.core.wpmfillermozeditor.vm.QntVMRdfInstFactory;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.factory.AWPModelFiller;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;

/**
 * It is representative class of filling the WPOM with data corresponding to the DOM tree.
 * The data is taken from the active {@link EMBrowserEditor}.
 * 
 * It provides main objects and factories for {@linkplain WPModelFillerExecuter}, which is a real
 * implementation.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 28, 2011 3:18:55 AM
 * @see WPModelFillerExecuter
 */
public class WPModelFiller extends AWPModelFiller {
	private static final Logger log = Logger.getLogger(WPModelFiller.class);

	public WPModelFiller() {}
	
	@Override
	protected void _fill() {
		// --- init main factories ---
		final SBGMRdfInstFactory bBGOMInstFactory = new SBGMRdfInstFactory(
				uom.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL)
				, config
				, whetherCreateInstance
				, whetherCreateAttrOrRelInOnt
				, bgmRdfInstanceFactory
				);
		
		final QntBMRdfInstFactory qntBMRdfInstFactory = new QntBMRdfInstFactory(
//				ontModel.getOntAdapter(EWPOntSubModel.QNT_BLOCK_MODEL)
//				, config
//				, qntBlockImpl
				bgmRdfInstanceFactory
				);
		
		final DrawingAnalyzer drawingAnalyzer = new DrawingAnalyzer(
//				ontModel.getOntAdapter(EWPOntSubModel.QNT_BLOCK_MODEL)
				config
				, qntBlockImpl
				, whetherCreateAttrOrRelInOnt);
		
		final SpatialIndexFillHelper spatialIndexFactory = new SpatialIndexFillHelper(
//				config,
				spatialIndexManager
				, instAdpFactory);
		
		final DOMRdfInstFactory domRdfInstFactory = new DOMRdfInstFactory(
				whetherCreateInstance, domRdfInstanceFactory);
		
		final IMRdfInstFactory imRdfInstFactory = new IMRdfInstFactory(
				whetherCreateInstance, imRdfInstanceFactory
				, uom.getOntAdapter(EWPOntSubModel.INTERFACE_MODEL));
		
//		final IMRdfInstFactory imRdfInstFactory = new IMRdfInstFactory(
//				ontModel.getOntAdapter(EWPOntSubModel.INTERFACE_MODEL)
//				, config
//				, imTextImpl);
		
		final QntVMRdfInstFactory qntVMRdfInstFactory = new QntVMRdfInstFactory(
				whetherCreateInstance, vmRdfInstanceFactory
				, uom.getOntAdapter(EWPOntSubModel.QNT_VISUAL_MODEL)
				);
		
		// --- get main window ---
		final Object d = browserNav.getData();
		if (d == null) {
			throw new GeneralUncheckedException(log, "Web page is empty!");
		}
		else if (d instanceof nsIDOMWindow) {
			uom.setSource(d);
			implementation(
					config
					, (nsIDOMWindow)d
					, domRdfInstFactory
					, imRdfInstFactory
					, bBGOMInstFactory
					, qntBMRdfInstFactory
					, drawingAnalyzer
					, spatialIndexFactory
					, qntVMRdfInstFactory
					);
		}
		else
			throw new GeneralUncheckedException(log, "Data must be of the type nsIDOMWindow.");
	}
	
	
	private void implementation(final WPPSConfig config
			, final nsIDOMWindow topDOMWindow
			, final DOMRdfInstFactory domRdfInstFactory
			, final IMRdfInstFactory imRdfInstFactory
			, final SBGMRdfInstFactory bBGOMInstFactory
			, final QntBMRdfInstFactory qntBMRdfInstFactory
			, final DrawingAnalyzer drawingAnalyzer
			, final SpatialIndexFillHelper spatialIndexFactory
			, final QntVMRdfInstFactory qntVMRdfInstFactory) {
		
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. Depth-firsts search for the DOM trees");
		
		final DOMWalker domWalker = new DOMWalker(
				config
				, uom
				, topDOMWindow
				, domRdfInstFactory
				, imRdfInstFactory
				, bBGOMInstFactory
				, qntBMRdfInstFactory
				, drawingAnalyzer
				, spatialIndexFactory
				, qntVMRdfInstFactory
				);
		domWalker.exe();
		domWalker.createWebDocument();
		domWalker.compZIndexRelatedQntData();
		
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. Go deep-firsts search for the DOM tree");
		
	}
	
}
