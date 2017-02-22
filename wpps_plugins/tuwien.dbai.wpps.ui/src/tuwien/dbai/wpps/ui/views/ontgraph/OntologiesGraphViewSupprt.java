/**
 * 
 */
package tuwien.dbai.wpps.ui.views.ontgraph;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IGraphContentProvider;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

import toxi.color.TColor;
import tuwien.dbai.wpps.colors.ColorGenerator;
import tuwien.dbai.wpps.colors.ColorsUtil;
import tuwien.dbai.wpps.common.ArrayWrapper;
import tuwien.dbai.wpps.common.CollectionToString;
import tuwien.dbai.wpps.common.Mapping2;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.core.methods.WPUMethodState;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.ui.events.OntGraphEvent;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.EventBus;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 22, 2012 6:40:02 PM
 */
public final class OntologiesGraphViewSupprt {
	private static final Logger log = Logger.getLogger(OntologiesGraphViewSupprt.class);
	
	public static class OntGraphContentProvider extends ArrayContentProvider  implements IGraphContentProvider {
		@Override
		public Object getSource(Object rel) {
			if (rel instanceof RelationGroup) {
				return ((RelationGroup)rel).getSource();
			}
			return null;
		}
		@Override
		public Object getDestination(Object rel) {
			if (rel instanceof RelationGroup) {
				return ((RelationGroup)rel).getDestination();
			}
			return null;
		}
	}
	
	public static class OntGraphIndividsLabelProvider extends LabelProvider
	implements IConnectionStyleProvider,
	org.eclipse.zest.core.viewers.IEntityStyleProvider, org.eclipse.zest.core.viewers.ISelfStyleProvider
	{
		private static final float LIGHTNES_CHANGE = 0.5f;
		private static final float BLEND = 0.7f;
		
		private final Mapping2 localProps;
		final ColorGenerator colorGenerator;
		
		public OntGraphIndividsLabelProvider(final ColorGenerator colorGenerator, final Mapping2 localProps) {
			this.localProps = localProps;
			this.colorGenerator = colorGenerator;
		}
		
		@Override
		public String getText(Object element) {
			if (element instanceof Node) {
				return ((Node)element).getRes().getLocalName();
			}
			else if (element instanceof RelationGroup) {
				return null;
//				return relsToStr.toString(((RelationGroup)element).getAllRdfProperties());
			}
			if (element instanceof EntityConnectionData) {
//				EntityConnectionData test = (EntityConnectionData) element;
				return "";
			}
			return null;
		}
		@Override
		public Color getNodeHighlightColor(Object entity) {
			return new Color(Display.getDefault(), 
					ColorsUtil.convertTColorToSWTRGB(
					ColorsUtil.convertSWTRGBToTColor(this.getBackgroundColour(entity).getRGB())
					.blend(TColor.WHITE, BLEND)
			) );
		}
		@Override
		public Color getBorderColor(Object entity) {
			return null;
		}
		@Override
		public Color getBorderHighlightColor(Object entity) {
			return null;
		}
		@Override
		public int getBorderWidth(Object entity) {
			return 0;
		}
		@Override
		public Color getBackgroundColour(Object entity) {
			final String str = ((Node)entity).getTypesAsStringForTooltip();
			TColor tc = null;
			if (localProps.hasMappedObject(str, OntologiesGraphView.COLOR)) {
				tc = localProps.getMappedObjectAs(str, OntologiesGraphView.COLOR, TColor.class);
			}
			else {
				tc = colorGenerator.getNextColor();
				localProps.addMapping(str, OntologiesGraphView.COLOR, tc);
			}
			return new Color(Display.getDefault(), 
					ColorsUtil.convertTColorToSWTRGB(tc)
					);
		}
		@Override
		public Color getForegroundColour(Object entity) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public boolean fisheyeNode(Object entity) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public int getConnectionStyle(Object rel) {
			return ZestStyles.CONNECTIONS_DIRECTED;
		}
		@Override
		public Color getColor(Object rel) {
			return new Color(Display.getDefault(),
					ColorsUtil.convertTColorToSWTRGB(
							ColorsUtil.convertSWTRGBToTColor(
									this.getBackgroundColour(((RelationGroup)rel).getSource()).getRGB()
									).darken(LIGHTNES_CHANGE) //.blend(TColor.BLACK, 0.7f)		
							)
					);
		}
		@Override
		public Color getHighlightColor(Object rel) {
			return new Color(Display.getDefault(),
					ColorsUtil.convertTColorToSWTRGB(
							ColorsUtil.convertSWTRGBToTColor(
									this.getColor(rel).getRGB()
									).lighten(LIGHTNES_CHANGE) //.blend(TColor.BLACK, 0.7f)		
							)
					);
		}
		@Override
		public int getLineWidth(Object rel) {
			return 0;
		}
		@Override
		public IFigure getTooltip(Object rel) {
			if (rel instanceof Node) {
				final Node n = (Node)rel;
				final String types = n.getTypesAsStringForTooltip();
				if (n.getTypeSet().isEmpty())
					log.trace(n.getRes()+" does not have any type");
				return new Label(((Node)rel).getRes().getLocalName()+"::\n"+types);
			}
			else if (rel instanceof RelationGroup) {
				final RelationGroup rg = (RelationGroup)rel;
				return new Label(
						rg.getSource().getRes().getLocalName()
						+" -> "
						+rg.getDestination().getRes().getLocalName()
						+"\n"
						+rg.getPropsAsStringForTooltip()
						);
			}
			return null;
		}
		@Override
		public ConnectionRouter getRouter(Object rel) {
			return null;
		}
		@Override
		public void selfStyleConnection(Object element,
				GraphConnection connection) {
		}
		@Override
		public void selfStyleNode(Object element, GraphNode node) {
			FontData[] fD = node.getFont().getFontData();
			fD[0].setHeight(8);
//			if (node.isSelected())
//				fD[0].setStyle(SWT.BOLD);
//			else
//				fD[0].setStyle(SWT.NORMAL);
			node.setFont(new Font(node.getDisplay(),fD[0]));
		}
	}
	
	public static class OntGraphSelections implements ISelectionChangedListener {
		private final EventBus eventBus;
		private final GraphViewer graphViewer;
		private final EMBrowserEditor browser;
		private final WPUMethodState currState;
		private final RdfInstAdpFactoryWrap adpFactory;
		public OntGraphSelections(final EventBus eventBus, final GraphViewer graphViewer
				, final EMBrowserEditor browser, final WPUMethodState currState
				, final RdfInstAdpFactoryWrap adpFactory) {
			this.graphViewer = graphViewer;
			this.eventBus = eventBus;
			this.browser = browser;
			this.currState = currState;
			this.adpFactory = adpFactory; 
		}
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			if (event.getSelection() instanceof IStructuredSelection) {
				IStructuredSelection ss = (IStructuredSelection)event.getSelection();
				Iterator iter = ss.iterator();
				while (iter.hasNext()) {
					Object o = iter.next();
					if (o instanceof Node) {
						final Node n = (Node)o;
						final GraphNode gn = graphViewer.getGraphModelNode(n);
						final TColor color = ColorsUtil.convertSWTRGBToTColor
								(gn.getBackgroundColor().getRGB());
						eventBus.post(
								OntGraphEvent.getInstance(OntGraphEvent.EventTypes.INDIVIDUAL_IS_SELECTED
										, graphViewer, new Object[]{n, color, browser, currState, adpFactory})
								);
					}
				}
			}
		}
	}
	
	/**
	 * Filters all resources without name.
	 */
	public static class OntGraphNullValFilter extends ViewerFilter {
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element instanceof Node) {
				final String n = ((Node)element).getRes().getLocalName();
				if (n == null || n.isEmpty())
					return false;
				return true;
			}
			else if (element instanceof RelationGroup) {
				return true;
			}
			return true;
		}
	}
	
	/**
	 * Leaves individuals and relation between them.
	 */
	public static class OntGraphIndividsFilter extends ViewerFilter {
		private final Set<String> ontSchemataNSCol;
		public OntGraphIndividsFilter(final Set<String> ontSchemataNSCol) {
			this.ontSchemataNSCol = ontSchemataNSCol;
		}
		public OntGraphIndividsFilter(final Collection<OntModelAdp> ontmodelCol) {
			this.ontSchemataNSCol = new HashSet<String>();
			for (OntModelAdp oma : ontmodelCol) {
				ontSchemataNSCol.add(oma.getNameSpace());
			}
		}
		@Override
		public boolean select(Viewer viewer, Object parentElement,
				Object element) {
			if (element instanceof Node) {
				if (ontSchemataNSCol.contains( ((Node)element).getRes().getNameSpace())) {
				return true;
				}
				else return false;
			} else if (element instanceof RelationGroup) {
				return true;
//				final RelationGroup rg = (RelationGroup)element;
//				if (rg.getSource().isIndividual() && rg.getDestination().isIndividual()) {
//					return true;
//				}
			}
			return true;
		}
	}
	
	public static class OntModelsTableLabelProvider extends LabelProvider implements ITableLabelProvider {
		private static final class GetStringEWPOntSubModel implements IFunction<EWPOntSubModel, String> {
			@Override
			public String apply(EWPOntSubModel avar) {
				return avar.name();
			}
		}
		private CollectionToString<EWPOntSubModel> toStr
			= new CollectionToString<EWPOntSubModel>(new GetStringEWPOntSubModel(), "\n");
		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		@Override
		public String getColumnText(Object element, int columnIndex) {
			String rez = "";
			OntModelAdp oma = (OntModelAdp)element;
			oma.getWpOntSubModel();
			switch (columnIndex) {
			case 0:
				rez = toStr.toString(oma.getWpOntSubModel());
				break;
			}
			return rez;
		}
	}
	
	public static final class OntModelsTableCheckStateProvider implements ICheckStateProvider {
		private final OntologiesGraphView ogv;
		public OntModelsTableCheckStateProvider(final OntologiesGraphView ogv) {
			this.ogv = ogv;
		}
		@Override
		public boolean isChecked(Object element) {
			Preconditions.checkArgument(element instanceof OntModelAdp);
			Set<?> b = (Set<?>)ogv.localProps.getMappedObject(ogv.currState, OntologiesGraphView.ONTs_TO_SHOW_PROP);
			return (b == null || b.isEmpty())?false:b.contains(element);
		}
		@Override
		public boolean isGrayed(Object element) {
			return false;
		}
	}
	
	public static final class OntModelsTableCheckStateListener implements ICheckStateListener {
		private final OntologiesGraphView ogv;
		public OntModelsTableCheckStateListener(final OntologiesGraphView ogv) {
			this.ogv = ogv;
		}
		@SuppressWarnings("unchecked")
		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			Preconditions.checkArgument(event.getElement() instanceof OntModelAdp);
			Set<OntModelAdp> b = (Set<OntModelAdp>)ogv.localProps
					.getMappedObject(ogv.currState, OntologiesGraphView.ONTs_TO_SHOW_PROP);
			if (b == null) {
				b = new HashSet<OntModelAdp>();
				ogv.localProps.addMapping(ogv.currState, OntologiesGraphView.ONTs_TO_SHOW_PROP, b);
			}
			if (event.getChecked())
				b.add((OntModelAdp)event.getElement());
			else
				b.remove(event.getElement());
			ogv.changeInOntologySelection(ogv.currState);
		}
		
	};
	
	
	
	public static List<RelationGroup> getRelations(final Collection<OntModelAdp> ontAdpsList) {
		final List<RelationGroup> rez = new LinkedList<RelationGroup>();
		final Map<ArrayWrapper<Resource>, RelationGroup> res2RelMap
				= new LinkedHashMap<ArrayWrapper<Resource>, RelationGroup>(1000000);
		final Map<Resource, Node> resNodeMap = new LinkedHashMap<Resource, Node>(100000);
		
		for (final OntModelAdp ontAdp : ontAdpsList) {
			final StmtIterator stmtiter = ontAdp.getTopRdfModel().listStatements();
			while (stmtiter.hasNext()) {
				final Statement stmtTmp = stmtiter.next();
				if (stmtTmp.getObject() instanceof Resource) {
					final ArrayWrapper<Resource> resArrTmp = new ArrayWrapper<Resource>
							(stmtTmp.getSubject(), (Resource)stmtTmp.getObject());
					RelationGroup rg = res2RelMap.get(resArrTmp);
					if (rg == null) {
						Node source = resNodeMap.get(resArrTmp.getContent()[0]);
						if (source == null) {
							source = new Node(resArrTmp.getContent()[0]);
							resNodeMap.put(resArrTmp.getContent()[0], source);
						}
						Node dest = resNodeMap.get(resArrTmp.getContent()[1]);
						if (dest == null) {
							dest = new Node(resArrTmp.getContent()[1]);
							resNodeMap.put(resArrTmp.getContent()[1], dest);
						}
						rg = new RelationGroup( source, dest );
						res2RelMap.put(resArrTmp, rg);
						rez.add(rg); // contribute to the result
					}
					rg.addRdfProperty(stmtTmp.getPredicate());
					// check if it is an individual
					if (RDF.type.equals(stmtTmp.getPredicate())) {
						rg.getSource().addType(rg.getDestination());
					}
				}
			}
		}
		
		return rez;
	}
	
//	public static final void provideColorsForIndividuals(AdditProps localProps, ColorGenerator colorGenerator, List<RelationGroup>) {
//		
//	}
	
}
