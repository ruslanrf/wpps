/**
 * 
 */
package tuwien.dbai.wpps.core.wpmfillermozeditor.bgm;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.mozilla.interfaces.nsIDOMCSS2Properties;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.common.html.ECSSPropertyConstants;
import tuwien.dbai.wpps.common.html.EHtmlElementConstants;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.WhetherCreateAttrOrRelInOnt;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntAttrType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl;

import com.hp.hpl.jena.rdf.model.Resource;

/**
 * Class is used to generate draw id and layer id.
 * 
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 24, 2011 8:53:03 PM
 */
public class DrawingAnalyzer {
	private static final Logger log = Logger.getLogger(DrawingAnalyzer.class);

//	public enum E_PARAMETERS { // for registration
//		CREATE_DRAW_ID,
//		CREATE_LAYER_ID
//	}
	
	/**
	 * Class which represent information about DOM element.
	 * 
	 * @author Ruslan (ruslanrf@gmail.com)
	 * @created Dec 27, 2011 9:50:29 PM
	 */
	private static class VisualElementInfo {
		
		/**
		 * Positioning scheme of a DOM element.
		 * <ul>
		 * <li><code>NORMAL_FLOW</code> for <code>position=static</code> or <code>position=relative</code>.</li>
		 * <li><code>FLOATS</code> for <code>float<>null</code> and <code>position=static</code>.</li>
		 * <li><code>ABSOLUTE</code> for <code>position=absolute</code>.</li>
		 * </ul>
		 * @author Ruslan (ruslanrf@gmail.com)
		 * @created Dec 27, 2011 9:51:26 PM
		 */
		private enum EPositioningScheme {
			NORMAL_FLOW,
			FLOATS,
			ABSOLUTE;
		}
		
		/**
		 * Block type.
		 * <ul>
		 * <li><code>NONE</code> for block with <code>display=none</code>.</li>
		 * <li><code>BLOCK_LEVEL</code> for block with <code>display=block</code> or <code>display=list-item</code>
		 * or <code>display=table</code>.</li>
		 * <li><code>INLINE_LEVEL</code> for block with <code>display=inline</code> or <code>display=inline-block</code>
		 * or <code>display=inline-table</code>.</li>
		 * <li><code>TABLE_RELATED_ELEMENT</code> for block with <code>display=table-row</code> or <code>display=table-row-group</code>
		 * or <code>display=table-header-group</code> or <code>display=table-footer-group</code>
		 * or <code>display=table-column</code> or <code>display=table-column-group</code>
		 * or <code>display=table-cell</code> or <code>display=table-caption</code>.</li>
		 * </ul>
		 * @author Ruslan (ruslanrf@gmail.com)
		 * @created Dec 27, 2011 10:13:12 PM
		 */
		private enum EBlockType {
			NONE,
			BLOCK_LEVEL,
			INLINE_LEVEL,
			TABLE_RELATED_ELEMENT
		}
		
		/**
		 * true, if element has name "html". 
		 */
		public Boolean root = null;
		/**
		 * true, if element has name "body".
		 */
		public Boolean rootRelated = null;
		/**
		 * true, if <code>position=relative</code> or <code>position=absolute</code> or <code>position=fixed</code>.
		 */
		public Boolean positioned = null;
		/**
		 * z-index, <code>null</code> for "auto".
		 */
		public Integer z = null;
		/**
		 * Positioning scheme.
		 */
		public EPositioningScheme posScheme = null;
		/**
		 * true, if <code>z-index != "auto" and (position=relative or position=absolute or position=fixed)</code>
		 * or <code>opacity &lt; 1</code> or <code>transform != "none"</code>.
		 */
		public Boolean createNewSC = null;
		/**
		 * Type of block.
		 */
		public EBlockType blockType = null;
		/**
		 * True, if it is a table or table related element.
		 */
		public Boolean tableElement = null;
		
		/**
		 * RDF resource related to this element.
		 */
		public Resource rdfInst = null;
		
//		public IProcedure<Integer> setDrawID = null; // for registration
//		public IProcedure<Integer> setLayerID = null;
		
		/**
		 * @return true if <code>display=none</code>.
		 */
		public boolean isDisplayNone() {
			return (blockType == EBlockType.NONE);
		}
		
		/**
		 * Create instance for DOM element.
		 * @param elName name of a DOM element
		 * @param rdfInst RDF resource which corresponds to this element.
		 * @param cssPosition css attribute "position".
		 * @param cssZIndex css attribute "z-index".
		 * @param cssFloat css attribute "float".
		 * @param cssDisplay css attribute "display".
		 * @return instance of VisualElementInfo.
		 */
		public static VisualElementInfo getInstanceForElement(final String elName, final Resource rdfInst
				, final String cssPosition, final String cssZIndex, final String cssFloat, final String cssDisplay) {
//				, final IProcedure<Integer> setDrawID, final IProcedure<Integer> setLayerID) {
			final VisualElementInfo rez = new VisualElementInfo();
			// rdfInst
			rez.rdfInst = rdfInst;
			
			// visualElType
						if (cssDisplay.equals(ECSSPropertyConstants.DISPLAY_NONE_VALUE.string()))
							rez.blockType = EBlockType.NONE;
						else if ( cssDisplay.equals(ECSSPropertyConstants.DISPLAY_BLOCK_VALUE.string())
									|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_LIST_ITEM_VALUE.string())
									|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_TABLE_VALUE.string())
								)
							rez.blockType = EBlockType.BLOCK_LEVEL;
						else if ( cssDisplay.equals(ECSSPropertyConstants.DISPLAY_INLINE_VALUE.string())
									|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_INLINE_BLOCK_VALUE.string())
									|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_INLINE_TABLE_VALUE.string())
								)
							rez.blockType = EBlockType.INLINE_LEVEL;
						else if ( cssDisplay.equals(ECSSPropertyConstants.DISPLAY_TABLE_ROW_VALUE.string())
								|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_TABLE_ROW_GROUP_VALUE.string())
								|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_TABLE_HEADER_GROUP_VALUE.string())
								|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_TABLE_FOOTER_GROUP_VALUE.string())
								|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_TABLE_COLUMN_VALUE.string())
								|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_TABLE_COLUMN_GROUP_VALUE.string())
								|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_TABLE_CELL_VALUE.string())
								|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_TABLE_CAPTION_VALUE.string())
							)
							rez.blockType = EBlockType.TABLE_RELATED_ELEMENT;
						else {
log.warn("Unknown value for DISPLAY CSS attribute \""+cssDisplay+"\" from the predefined list");
							// just to avoid problems we set block level even if it is incorrect
							rez.blockType = EBlockType.BLOCK_LEVEL;
//							throw new UnknownValueFromPredefinedList(log, "");
						}
			// if display none, we do not analyze the element. It does not play role on drawing orders
			if (rez.isDisplayNone())
				return rez;
						
			// root
			if ( elName.equals(EHtmlElementConstants.HTML.string()) )
				rez.root = true;
			else
				rez.root = false;
						
			// rootRelated
			if (elName.equals(EHtmlElementConstants.BODY.string()))
				rez.rootRelated = true;
			else
				rez.rootRelated = false;
			// positioned
			rez.positioned = (cssPosition.equals(ECSSPropertyConstants.POSITION_STATIC_VALUE.string()))? false:true;
			// z
			if (!cssZIndex.equals(ECSSPropertyConstants.ZINDEX_AUTO_VALUE.string())) {
				try {
					rez.z = NumberFormat.getIntegerInstance().parse(cssZIndex).intValue();
//					rez.z = Integer.valueOf(cssZIndex);
				} catch(final ParseException e) {
					rez.z = null;
					log.warn("String "+cssZIndex+" cannot be converted into Integer."+elName+" "+rdfInst);
				}
			}
			// posScheme
			boolean floatBool = !ECSSPropertyConstants.FLOAT_NONE_VALUE.string().equals(cssFloat);
			if ( cssPosition.equals(ECSSPropertyConstants.POSITION_STATIC_VALUE.string())
					&& !floatBool
					|| cssPosition.equals(ECSSPropertyConstants.POSITION_RELATIVE_VALUE.string())
					)
				rez.posScheme = EPositioningScheme.NORMAL_FLOW;
			else if ( cssPosition.equals(ECSSPropertyConstants.POSITION_ABSOLUTE_VALUE.string())
						|| cssPosition.equals(ECSSPropertyConstants.POSITION_FIXED_VALUE.string())
					)
				rez.posScheme = EPositioningScheme.ABSOLUTE;
			else if (floatBool)
				rez.posScheme = EPositioningScheme.FLOATS;
			else
				throw new UnknownValueFromPredefinedList(log, "");
			// createNewSC
			if (rez.positioned && rez.z!=null // || Integer.parseInt(cssProps.getOpacity())!=1 || !cssProps.getTransform().equalsIgnoreCase("none")
					)
				rez.createNewSC = true;
			else
				rez.createNewSC = false;
			
			// table
			if (  rez.blockType == EBlockType.TABLE_RELATED_ELEMENT
					|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_TABLE_VALUE.string())
					|| cssDisplay.equals(ECSSPropertyConstants.DISPLAY_INLINE_TABLE_VALUE.string()) )
					rez.tableElement = true;
			else
				rez.tableElement = false;

//			rez.setDrawID = setDrawID; // for registration
//			rez.setLayerID = setDrawID;
			
			return rez;
		}
		
	}
	
	
	/**
	 * Class which wrap element to represent a tree of stacks. 
	 * 
	 * @author Ruslan (ruslanrf@gmail.com)
	 * @created Dec 27, 2011 10:50:40 PM
	 */
	private static class StackingContext {
		
		/**
		 * STACKING_CONTEXT for the element which create stacking context. 
		 * SEMI_STACKING_CONTEXT for the elements which play a role as a stacking context only for inline blocks which
		 * do not create their own stacking context.
		 * FICTIVE_STACKING_CONTEXT for inline and block level elements  as well as floats which do not create new stacking context.
		 * @author Ruslan (ruslanrf@gmail.com)
		 * @created Dec 27, 2011 11:14:01 PM
		 */
		public static enum EStackingContextType {
			STACKING_CONTEXT,
			SEMI_STACKING_CONTEXT,
			FICTIVE_STACKING_CONTEXT
		}
		
		/**
		 * This var. used during draw-index detection.
		 */
		public final EStackingContextType stackingContextType;
		
		public final VisualElementInfo visualElementInfo;
		
		/**
		 * This parameter is used for detecting layers.
		 * false, if element has only 1 group of elements which whether all are root related (html, body),
		 * or inflow elements and floats that do not form new SC,
		 * or positioned elements which have 1 common z-index and each of these elements has hasSeveralDescSCGroup = false.
		 * Otherwise --- true.
		 */
		public boolean hasSeveralDescSCGroup = false;
		
		/**
		 * This parameter is used for detecting layers.
		 * false, if element has only 1 group of elements which whether all are root related (html, body),
		 * or inflow elements and floats that do not form new SC,
		 * or positioned elements which have 1 common z index and each of these elements has hasSeveralDescSCGroup = false .
		 * Otherwise --- true.
		 * Difference with hasSeveralDescSCGroup is that if element has 1 child element
		 * it will always has hasSeveralChildSCGroup = false.
		 */
		public boolean hasSeveralChildSCGroup = false;
		
		public List<StackingContext> rootRelatedSC;
		// we use 6 stacking levels
		// http://www.vanseodesign.com/css/css-stack-z-index/
		/**
		 * Elements which create new stacking context.
		 * it is element which has either position value equal to relative, absolute or fixed,
		 * and z-index<0
		 */
		public List<StackingContext> negativeSC;
		/**
		 * Block Boxes of normal flow
		 */
		public List<StackingContext> normalFlowBlockBoxes;
		/**
		 * Boxes form floating flow
		 */
		public List<StackingContext> floatBoxes;
		/**
		 * Inline Boxes from normal flow
		 */
		public List<StackingContext> normalFlowInlineBoxes;
		/**
		 * it is element which has either position value equal to relative, absolute or fixed,
		 * and z-index=0 or auto
		 * or non-pos. element with opacity<1 (z-index does not play any role)
		 */
		public List<StackingContext> zeroOrAutoSC;
		/**
		 * Elements which create new stacking context.
		 * it is element which has either position value equal to relative, absolute or fixed,
		 * and z-index>0
		 */
		public List<StackingContext> positiveSC;
		
		public StackingContext(final EStackingContextType stackingContextType, final VisualElementInfo visualElement
				, final StackingContext parentSC) {
			this.stackingContextType = stackingContextType;
			this.visualElementInfo = visualElement;
			
			switch (stackingContextType) {
			case STACKING_CONTEXT:
				rootRelatedSC = new LinkedList<StackingContext>();
				negativeSC = new SortedStackingContextList(); // add sorted
				normalFlowBlockBoxes = new LinkedList<StackingContext>();
				floatBoxes = new LinkedList<StackingContext>();
				normalFlowInlineBoxes = new LinkedList<StackingContext>();
				zeroOrAutoSC = new LinkedList<StackingContext>();// add to the end
				positiveSC = new SortedStackingContextList(); // add sorted
				break;
			case SEMI_STACKING_CONTEXT:
				rootRelatedSC = null;
				negativeSC = parentSC.negativeSC; // add sorted
				normalFlowBlockBoxes = new LinkedList<StackingContext>();
				floatBoxes = new LinkedList<StackingContext>();
				normalFlowInlineBoxes = new LinkedList<StackingContext>();
				zeroOrAutoSC = parentSC.zeroOrAutoSC;// add to the end
				positiveSC = parentSC.positiveSC; // add sorted
				break;
			case FICTIVE_STACKING_CONTEXT:
				rootRelatedSC = null;
				negativeSC = null; // add sorted
				normalFlowBlockBoxes = null;
				floatBoxes = null;
				normalFlowInlineBoxes = null;
				zeroOrAutoSC = null;// add to the end
				positiveSC = null; // add sorted
				break;
			default:
				throw new UnknownValueFromPredefinedList(log, stackingContextType);
			}
		}
	}
	

	/**
	 * 
	 * Sorted list of StackingContexts which is sorted based on z-index value.
	 * Example: -5, -5, -2, auto, auto, 0, auto, 1, 1, 10
	 * @type: SortedStackingContextList
	 *
	 * @created: Nov 10, 2010 9:45:12 AM
	 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
	 *
	 */
	private static class SortedStackingContextList extends LinkedList<StackingContext> {
		private static final long serialVersionUID = -8000554838996115820L;
	
		public void addSorted(final StackingContext sc) {
			ListIterator<StackingContext> iter = this.listIterator();
			boolean found = false;
			while (!found && iter.hasNext()) {
				final StackingContext scTmp = iter.next();
				if ( (sc.visualElementInfo.z == null /*auto*/ && scTmp.visualElementInfo.z != null && scTmp.visualElementInfo.z >0)
						|| (sc.visualElementInfo.z != null && scTmp.visualElementInfo.z != null && 
								sc.visualElementInfo.z < scTmp.visualElementInfo.z) )
					found = true;
			}
			if (found)
				iter.previous();
			iter.add(sc);
		}
		
		@Override
		public boolean add(final StackingContext sc) {
			addSorted(sc);
			return true;
		}
	}
	
	private static enum EVisualElementType {
		ROOT_RELATED,
		POSITIONED_NEGATIVE_SC,
		NORMAL_FLOW_BLOCK_BOX,
//		NORMAL_FLOW_BLOCK_TABLE,
		NORMAL_FLOW_BLOCK_BOX_SC,
		FLOAT_BOX,
		FLOAT_BOX_SC,
		NORMAL_FLOW_INLINE_BOX,
//		NORMAL_FLOW_INLINE_TABLE,
		NORMAL_FLOW_INLINE_BOX_SC,
		POSITIONED_AUTO_SC,
		POSITIONED_AUTO,
		POSITIONED_ZERO_SC,
		POSITIONED_POSITIVE_SC
	}
	
//	private final OntModelAdp ontModelAdp;
	
	private final boolean createDrawId;
	private final boolean createLayerId;
	
	private final QntBlockImpl qntBlockImpl;
	
	private final List<StackingContext> orderOfCreatingSC = new ArrayList<StackingContext>(10000);
	private int currSCIndex;
	
	private final List<List<StackingContext>> webPageTreeIndeces = new ArrayList<List<StackingContext>>(5);
	private int webPageLayerIndex = -1;
	
	
	public DrawingAnalyzer(
//			final OntModelAdp ontModelAdp
			final WPPSConfig config
			, final QntBlockImpl qntBlockImpl
			, final WhetherCreateAttrOrRelInOnt whetherCreateAttrOrRelInOnt) {
		
//		this.ontModelAdp = ontModelAdp;
		this.qntBlockImpl = qntBlockImpl;
		createDrawId = whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.DRAW_ID);
		createLayerId = whetherCreateAttrOrRelInOnt.apply(EBlockQntAttrType.LAYER_ID);
		currSCIndex = -1;
	}
		
	private static final EVisualElementType getElType(VisualElementInfo ve) {
		if (ve.rootRelated)
			return EVisualElementType.ROOT_RELATED;
		
		if (ve.positioned && ve.z !=null && ve.z<0)
			return EVisualElementType.POSITIONED_NEGATIVE_SC;
		
		if (!ve.positioned && ve.posScheme == VisualElementInfo.EPositioningScheme.NORMAL_FLOW
				&& (ve.blockType == VisualElementInfo.EBlockType.BLOCK_LEVEL
						||  ve.blockType == VisualElementInfo.EBlockType.TABLE_RELATED_ELEMENT)) {
			if (ve.createNewSC)
				return EVisualElementType.NORMAL_FLOW_BLOCK_BOX_SC;
			else
				return EVisualElementType.NORMAL_FLOW_BLOCK_BOX;
		}
		
		if (!ve.positioned && ve.posScheme == VisualElementInfo.EPositioningScheme.FLOATS) {
			if (ve.createNewSC)
				return EVisualElementType.FLOAT_BOX_SC;
			else 
				return EVisualElementType.FLOAT_BOX;
		}
		
		if (!ve.positioned && ve.posScheme == VisualElementInfo.EPositioningScheme.NORMAL_FLOW
				&& ve.blockType == VisualElementInfo.EBlockType.INLINE_LEVEL) {
			if (ve.createNewSC)
				return EVisualElementType.NORMAL_FLOW_INLINE_BOX_SC;
			else
					return EVisualElementType.NORMAL_FLOW_INLINE_BOX;
		}
		
		if (ve.positioned && ve.z ==null) {
			if (ve.createNewSC)
				return EVisualElementType.POSITIONED_AUTO_SC;
			else
				return EVisualElementType.POSITIONED_AUTO;
		}
		
		if (ve.positioned && ve.z !=null && ve.z == 0)
			return EVisualElementType.POSITIONED_ZERO_SC;
		
		if (ve.positioned && ve.z !=null && ve.z > 0)
			return EVisualElementType.POSITIONED_POSITIVE_SC;
		
		throw new UnknownValueFromPredefinedList(log, "");
		
	}
	
	private static final List<StackingContext> getCorrespondingList(final EVisualElementType vet, final StackingContext sc) {
		switch (vet) {
		case ROOT_RELATED:
			return sc.rootRelatedSC;
		case POSITIONED_NEGATIVE_SC:
			return sc.negativeSC;
		case NORMAL_FLOW_BLOCK_BOX:
//		case NORMAL_FLOW_BLOCK_TABLE:
			return sc.normalFlowBlockBoxes;
		case FLOAT_BOX:
			return sc.floatBoxes;
		case NORMAL_FLOW_INLINE_BOX:
//		case NORMAL_FLOW_INLINE_TABLE:
			return sc.normalFlowInlineBoxes;
		case NORMAL_FLOW_BLOCK_BOX_SC:
		case FLOAT_BOX_SC:
		case NORMAL_FLOW_INLINE_BOX_SC:
		case POSITIONED_AUTO:
		case POSITIONED_ZERO_SC:
			return sc.zeroOrAutoSC;
		case POSITIONED_POSITIVE_SC:
			return sc.positiveSC;
		default:
			throw new UnknownValueFromPredefinedList(log, vet);
		}
		
	}
	
	public final Object goDown(final String elName, final Resource rdfInst, final nsIDOMCSS2Properties cssProps) {
		if (!createDrawId && !createLayerId)
			return null;
		return _goDown(elName, rdfInst, cssProps.getPosition(), cssProps.getZIndex()
				, cssProps.getCssFloat(), cssProps.getDisplay());
	}
	
	
	private final Object _goDown(final String elName, final Resource rdfInst
			, final String cssPosition, final String cssZIndex, final String cssFloat, final String cssDisplay){
//			, final IProcedure<Integer> setDrawID, final IProcedure<Integer> setLayerID) {
//		if (!createDrawId && !createLayerId)
//			return null;
		final VisualElementInfo ve = VisualElementInfo.getInstanceForElement(elName, rdfInst, cssPosition, cssZIndex, cssFloat, cssDisplay);
		if (ve.isDisplayNone())
			return null;
		StackingContext sc = null;
		
		if (ve.root) {
			sc = new StackingContext(StackingContext.EStackingContextType.STACKING_CONTEXT, ve, null);
			
			webPageLayerIndex++;
if (log.isDebugEnabled())
MatcherAssert.assertThat(webPageTreeIndeces, Matchers.hasSize(Matchers.greaterThanOrEqualTo(webPageLayerIndex)));
			
			if (webPageTreeIndeces.size() == webPageLayerIndex) // last element +1 in the frameTreeIndeces
				webPageTreeIndeces.add(new ArrayList<StackingContext>(10));
			webPageTreeIndeces.get(webPageLayerIndex).add(sc);
			
		}
		else 
		{
			final EVisualElementType vet = getElType(ve);
			if (ve.tableElement)
				sc = new StackingContext(StackingContext.EStackingContextType.SEMI_STACKING_CONTEXT, ve, orderOfCreatingSC.get(currSCIndex));
			else {
				switch (vet) {
				case POSITIONED_NEGATIVE_SC:
				case NORMAL_FLOW_BLOCK_BOX_SC:
				case FLOAT_BOX_SC:
				case NORMAL_FLOW_INLINE_BOX_SC:
				case POSITIONED_AUTO_SC:
				case POSITIONED_ZERO_SC:
				case POSITIONED_POSITIVE_SC:
					sc = new StackingContext(StackingContext.EStackingContextType.STACKING_CONTEXT, ve, null);
					break;
				case FLOAT_BOX:
				case POSITIONED_AUTO:
					sc = new StackingContext(StackingContext.EStackingContextType.SEMI_STACKING_CONTEXT, ve, orderOfCreatingSC.get(currSCIndex));
					break;
				case ROOT_RELATED:
				case NORMAL_FLOW_BLOCK_BOX:
				case NORMAL_FLOW_INLINE_BOX:
					sc = new StackingContext(StackingContext.EStackingContextType.FICTIVE_STACKING_CONTEXT, ve, orderOfCreatingSC.get(currSCIndex));
					break;
				default:
					throw new UnknownValueFromPredefinedList(log, vet);
				}
			}
			getCorrespondingList(vet, orderOfCreatingSC.get(currSCIndex)).add(sc);
		}
		
		if (sc.stackingContextType != StackingContext.EStackingContextType.FICTIVE_STACKING_CONTEXT) {
			orderOfCreatingSC.add(sc);
			currSCIndex++;
		}
		return sc;
	}
	
	private boolean _sameZ(final Integer z1, final Integer z2) {
		if (z1 == null && z2 == null)
			return true;
		if (z1 == null || z2 == null)
			return false;
		return z1.equals(z2);
	}
	
	/**
	 * @param sc
	 * @param mode 0 --- has several child SC groups, 1 --- has several descendant SC groups.
	 * @return
	 */
	private boolean _scHasSeveralSCGroups(final StackingContext sc, int mode) {
		// one potetntial group exists 
		final int potentialGroupsNum = (int)(Math.signum(sc.rootRelatedSC.size()) + Math.signum(sc.negativeSC.size())
				+ Math.signum(sc.normalFlowBlockBoxes.size() + sc.floatBoxes.size() + sc.normalFlowInlineBoxes.size())
				+ Math.signum(sc.zeroOrAutoSC.size()) + Math.signum(sc.positiveSC.size()));
		if (potentialGroupsNum == 0)
			return false;
		if (potentialGroupsNum > 1)
			return true;
		// 1 potential group --- root related elements
		if (sc.rootRelatedSC.size()>0) {
if (log.isDebugEnabled()) MatcherAssert.assertThat(sc.visualElementInfo.root, Matchers.equalTo(true));
			return false;
		}
		// 1 potential group --- normalflow elements and floats
		if (sc.normalFlowBlockBoxes.size() + sc.floatBoxes.size() + sc.normalFlowInlineBoxes.size() >0)
			if (sc.visualElementInfo.root)
				return true;
			else
				return false;
		
		if (sc.visualElementInfo.root)
			return true;
		
		// get corresponding list
		List<StackingContext> scList = null;
		if (sc.negativeSC.size() > 0)
			scList = sc.negativeSC;
		else if (sc.zeroOrAutoSC.size() > 0)
			scList = sc.zeroOrAutoSC;
		else if (sc.positiveSC.size() > 0)
			scList = sc.positiveSC;
		else
			throw new UnknownValueFromPredefinedList(log, "");
		
		// list has 1 element
		if (scList.size() == 1) {
			if (mode == 0)
				return false;
			else
				return scList.get(0).hasSeveralDescSCGroup;
		}
		
		final Iterator<StackingContext> iter = scList.iterator();
		final StackingContext sc1 = iter.next();
		if (sc1.hasSeveralDescSCGroup)
			return true;
		// next elements with the same Z and have no fork
		while (iter.hasNext()) {
			final StackingContext sc2 = iter.next();
			if (!_sameZ(sc1.visualElementInfo.z, sc2.visualElementInfo.z) || sc2.hasSeveralDescSCGroup) {
				return true;
			}
		}
		return false;
	}
	
	
	public void goUp(final Object id) {
		if (id == null) // we did not analyzed element going down.
			return;
if (log.isDebugEnabled()) MatcherAssert.assertThat(id, Matchers.instanceOf(StackingContext.class));
		final StackingContext sc = orderOfCreatingSC.get(currSCIndex);
if (log.isDebugEnabled()) MatcherAssert.assertThat(sc, Matchers.notNullValue());
		if (sc == id) {
			if (sc.stackingContextType == StackingContext.EStackingContextType.STACKING_CONTEXT) {
				sc.hasSeveralChildSCGroup = _scHasSeveralSCGroups(sc, 0);
				sc.hasSeveralDescSCGroup = _scHasSeveralSCGroups(sc, 1);
			}
			if (sc.visualElementInfo.root) {
				webPageLayerIndex--;
			}
			orderOfCreatingSC.remove(currSCIndex);
			currSCIndex--;
			
		}
	}
	
	public void compute() {
		if (!createDrawId && !createLayerId)
			return;
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"START. Computing z-related attributes.");
		// go through frames
		for (int i=webPageTreeIndeces.size()-1; i>=0; i--) {
			final Iterator<StackingContext> iter = webPageTreeIndeces.get(i).iterator();
			while (iter.hasNext()) {
			goThroughSCOfDOMTree(iter.next());
			}
		}
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"FINISH. Computing z-related attributes.");
	}
	
	private int currDrawId = 0;
	private boolean drawIdUsed = false;
	private int currLayerId = 0;
	private boolean layerIdUsed = false;
	
	private final boolean _setDrawId(VisualElementInfo ve, int id) {
		if (createDrawId && ve.rdfInst != null) {
if (log.isTraceEnabled()) log.trace("DrawId:: "+ve.rdfInst+":: "+id);
			qntBlockImpl.addDrawId(ve.rdfInst, id);
			return true;
		}
		return false;
	}
	
	private final boolean _setLayerId(VisualElementInfo ve, int id) {
		if (createLayerId && ve.rdfInst != null) {
if (log.isTraceEnabled()) log.trace("LayerId:: "+ve.rdfInst+":: "+id);
			qntBlockImpl.addLayerId(ve.rdfInst, id);
			return true;
		}
		return false;
	}
	
	private void _goThroughSCList(List<StackingContext> list) {
		final Iterator<StackingContext> iter = list.iterator();
		while (iter.hasNext()) {
			goThroughSCOfDOMTree(iter.next());
		}
	}
	
	private void _goThroughRootRelatedGroup(final List<StackingContext> list) {
		if (list.size() != 0) {
//			if (layerIdUsed) {
//				currLayerId++;
//				layerIdUsed = false;
//			}
			_goThroughSCList(list);
		}
	}
	
	private void _goThroughInflowFloatingRelatedGroup(final List<StackingContext> blockLevel, final List<StackingContext> floats
			, final List<StackingContext> inlineLevel) {
		_goThroughSCList(blockLevel);
		_goThroughSCList(floats);
		_goThroughSCList(inlineLevel);
		
	}
	
	private void goThroughPositionedGroups(final List<StackingContext> list) {
		if (list.size() != 0) {
			final Iterator<StackingContext> iter = list.iterator();
			StackingContext prevSC = null;
			while (iter.hasNext()) {
				final StackingContext sc = iter.next();
				if (prevSC != null && (!_sameZ(prevSC.visualElementInfo.z, sc.visualElementInfo.z)
						|| prevSC.hasSeveralDescSCGroup || sc.hasSeveralDescSCGroup) )
					if (layerIdUsed) {
						currLayerId++;
						layerIdUsed = false;
					}
				goThroughSCOfDOMTree(sc);
				prevSC = sc;
			}
		}
	}
	
	private void goThroughSCOfDOMTree(StackingContext sc) {
		if (drawIdUsed) {
			currDrawId++;
			drawIdUsed = false;
		}
		drawIdUsed = _setDrawId(sc.visualElementInfo, currDrawId);
		layerIdUsed = _setLayerId(sc.visualElementInfo, currLayerId);
		
		// for floats, tables and positioned elements with z-index="auto".
		if (sc.stackingContextType == StackingContext.EStackingContextType.SEMI_STACKING_CONTEXT) {
			_goThroughInflowFloatingRelatedGroup(sc.normalFlowBlockBoxes, sc.floatBoxes, sc.normalFlowInlineBoxes);
		}
		else if (sc.stackingContextType == StackingContext.EStackingContextType.STACKING_CONTEXT) {
			_goThroughRootRelatedGroup(sc.rootRelatedSC);
			if (sc.hasSeveralChildSCGroup)
				if (layerIdUsed) {
					currLayerId++;
					layerIdUsed = false;
				}
			goThroughPositionedGroups(sc.negativeSC);
			if (sc.hasSeveralChildSCGroup)
				if (layerIdUsed) {
					currLayerId++;
					layerIdUsed = false;
				}
			_goThroughInflowFloatingRelatedGroup(sc.normalFlowBlockBoxes, sc.floatBoxes, sc.normalFlowInlineBoxes);
			if (sc.hasSeveralChildSCGroup)
				if (layerIdUsed) {
					currLayerId++;
					layerIdUsed = false;
				}
			goThroughPositionedGroups(sc.zeroOrAutoSC);
			if (sc.hasSeveralChildSCGroup)
				if (layerIdUsed) {
					currLayerId++;
					layerIdUsed = false;
				}
			goThroughPositionedGroups(sc.positiveSC);
		}
	}
	
}
