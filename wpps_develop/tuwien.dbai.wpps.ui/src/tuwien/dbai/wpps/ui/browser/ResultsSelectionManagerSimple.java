/**
 * 
 */
package tuwien.dbai.wpps.ui.browser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.RGB;
import org.mozilla.interfaces.nsIDOMDocument;

import toxi.color.TColor;
import tuwien.dbai.wpps.colors.ColorsUtil;
import tuwien.dbai.wpps.common.Mapping1_1N_generic;
import tuwien.dbai.wpps.common.Mapping1_1N_generic.ECollectionType;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.methods.WPUMethodState;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalDataStructure;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalObject;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.embrowser.addons.DOMSelectionBox.Config;
import tuwien.dbai.wpps.embrowser.addons.DOMSelectionBoxGroup;
import tuwien.dbai.wpps.ui.model.WPUMethodAdp;

/**
 * All methods should be invoked from the display thread.
 * 
 * Allows to show result as a sequence of rectangular blocks on the web document (browser editor),
 * where every block has color of the corresponding method.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 19, 2012 4:47:09 PM
 */
public class ResultsSelectionManagerSimple {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ResultsSelectionManagerSimple.class);
	
//	private Map<WPUMethodAdp, DOMSelectionBoxGroup> methodSelectionMap
//			= new HashMap<WPUMethodAdp, DOMSelectionBoxGroup>();
	
	private Mapping1_1N_generic<WPUMethodAdp, DOMSelectionBoxGroup> methodSelectionMap =
			new Mapping1_1N_generic<WPUMethodAdp, DOMSelectionBoxGroup>(ECollectionType.LIST);
		
	
//	private Map<AWPUMethod, DOMSelectionBoxGroups> methodSelectionMap
//			= new HashMap<AWPUMethod, DOMSelectionBoxGroups>();

	public ResultsSelectionManagerSimple(nsIDOMDocument mainDoc
			, Collection<WPUMethodAdp> methods
			, WPUMethodState state
			) {
		for (WPUMethodAdp m : methods) {
			if (!m.hasContent()) continue;
			for ( ILogicalDataStructure lds : state.getLogicalStructures(m.getContent()) ) {
				// all objects constitute current LO including itself.
				List<ILogicalObject> tmpL = lds.getStructElements();
				final List<Rectangle2D> rectList = new ArrayList<Rectangle2D>(tmpL.size()+1);
				Iterator<ILogicalObject> iter = tmpL.iterator();
				while (iter.hasNext()) {
					Rectangle2D rTmp = _getRectangleForLO(iter.next());
					if (rTmp != null) {
						rectList.add(rTmp);
					}
				}
				Rectangle2D rTmp = _getRectangleForLO(lds);
				if (rTmp != null) {
					rectList.add(rTmp);
				}
//System.out.println("qs2:"+rectList);
//				methodSelectionMap.put(m, new DOMSelectionBoxGroup(
//						rectList, mainDoc
//						, genConfig(m.getDescription().getColor())
//				) );
				methodSelectionMap.addMapping(m, new DOMSelectionBoxGroup(
						rectList, mainDoc, genConfig(m.getDescription().getColor()) ));
			}
		}
	}
	
	
	// TODO: Do not remove! May be it can be reused.
//	public ResultsSelectionManagerSimple(nsIDOMDocument mainDoc,
//			Collection<AWPUMethod> methods, WPOntSubModels subModels
////			, Mapping2Typed props
//			) {
//		// nodes which has a document and which is not contained in the set specified are not considered.
//		final Set<nsIDOMDocument> acceptedDocs = MozDomUtils.getDOMDocumentsForWebDocument(mainDoc);
//
//		for (AWPUMethod m : methods) {
//			Mapping1_1N_generic<nsIDOMDocument, Rectangle2D> docLoMap
//				= new Mapping1_1N_generic<nsIDOMDocument, Rectangle2D>(Mapping1_1N_generic.ECollectionType.LIST);
//				WPUMethodState state = m.getStates().getLast();
//				for (ILogicalDataStructure lds : state.getLogicalStructures(m)) {
//					// all objects constitute current LO including itself.
//					List<ILogicalObject> tmpL = lds.getStructElements();
//					List<ILogicalObject> loLost =  new ArrayList<ILogicalObject>(tmpL.size()+1);
//					loLost.add(lds); loLost.addAll(tmpL);
//					
//					for (ILogicalObject lo : loLost) {
//						Object o = subModels.getSourceObjectForAdp(lo); // get nsIDOMNode of the element,
//						nsIDOMDocument docDOMTmp = mainDoc;
//						if (o != null && o instanceof nsIDOMNode) {
//							nsIDOMDocument docTmp = ((nsIDOMNode)o).getOwnerDocument();
//							if (docTmp != null && acceptedDocs.contains(docTmp)) {
//								docDOMTmp = docTmp;
//							}
//							else {
//								log.warn(TSForLog.getTS(log)+" Logical object "+lo+ "has corresponding DOM Node, but its own DOM-tree is unknown.");
//								docDOMTmp = null;
//							}
//						}
//						if (docDOMTmp != null) {
//							final Rectangle2D rTmp = _getRectangleForLO(lo);
//							if (rTmp!=null)
//								docLoMap.addMapping(docDOMTmp, rTmp);
//						}
//					}
//				}
////				methodSelectionMap.put(m, 
////						new DOMSelectionBoxGroups(docLoMap.getMap()
////								, ((TColor)props.getMappedObject(m.getDescription(), EProperty.COLOR)).toHex())
////						);
//				
////				DOMSelectionBoxGroups (final Map<nsIDOMDocument, Collection<Rectangle2D>> instAdpCol
////						, final Map<nsIDOMDocument, Point2D> offsets, final String containedRectColor) {
//		}
//	}
	
	/**
	 * @param lo
	 * @return area taken by logical model, null if object cannot be converted to the QntBlock.
	 */
	private Rectangle2D _getRectangleForLO(ILogicalObject lo) {
		if (lo.canAs(IQntBlock.class)) {
			return lo.as(IQntBlock.class).getArea();
		}
		return null;
	}
	
	private Config genConfig(RGB color) {
		Config c = new Config();
		c.setBorderColor(ColorsUtil.convertSWTRGBToTColor(color));
		TColor tc = ColorsUtil.convertSWTRGBToTColor(color);
		tc.setAlpha(0.1f);
		c.setBackgroundColor(tc);
		c.setCoverAreaSelected(true);
		return c;
	}
	
	public void show(Collection<WPUMethodAdp> methods) {
		for (WPUMethodAdp m : methods) {
			Iterator<DOMSelectionBoxGroup> iter = methodSelectionMap.getMappedObjects(m).iterator();
			while (iter.hasNext()) {
				iter.next().showAll();
			}
//			if (methodSelectionMap.containsKey(m)) {
//				methodSelectionMap.get(m).showAll();
//			}
		}
	}
	
	public void hide(Collection<WPUMethodAdp> methods) {
		for (WPUMethodAdp m : methods) {
			Iterator<DOMSelectionBoxGroup> iter = methodSelectionMap.getMappedObjects(m).iterator();
			while (iter.hasNext()) {
				iter.next().hideAll();
			}
//			if (methodSelectionMap.containsKey(m)) {
//				methodSelectionMap.get(m).hideAll();
//			}
		}
	}
	
	public void dispose() {
		Iterator<Collection<DOMSelectionBoxGroup>> iter1 = methodSelectionMap.getMap().values().iterator();
		while (iter1.hasNext()) {
			Iterator<DOMSelectionBoxGroup> iter2 = iter1.next().iterator();
			while (iter2.hasNext()) {
				iter2.next().dispose();
			}
		}
//		for (DOMSelectionBoxGroup sbCol : methodSelectionMap.values()) {
//			sbCol.dispose();
//		}
	}


}
