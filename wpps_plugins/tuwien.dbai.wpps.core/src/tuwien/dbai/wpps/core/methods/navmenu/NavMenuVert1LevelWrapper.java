/**
 * 
 */
package tuwien.dbai.wpps.core.methods.navmenu;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import tuwien.dbai.wpps.common.SFuzzyComparatorDouble;
import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IFunction2;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.callback.IPredicate2;
import tuwien.dbai.wpps.common.callback.IProcedure;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.enriching.AsymmetricNeighborhoodEnricher;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;

import com.google.common.collect.Sets;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 17, 2012 7:44:39 AM
 */
public class NavMenuVert1LevelWrapper extends AWPUWrapper {

	public NavMenuVert1LevelWrapper(AWPUMethodDescription description) {
		super(description);
	}
	@Override
	protected void _dumpIntoLM(List<IResults> results) {
		for(final IResults tr :results) {
			ISequence seq = tr.convertTo(ISequence.class);
			seq.addLabel("Vertically oriented navigation menu.");
			addLogicalStructure(seq);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<IResults> _extractResults() {
		final IIEBasisAPI api = getIEAPI().getIEBasisAPI();
		
		// add relation EAST_NEIGHBORING_BLOCK_OF
		AsymmetricNeighborhoodEnricher e = getIEAPI().getEnricher(AsymmetricNeighborhoodEnricher.class);
		e.init(new Class[]{IHtmlText.class}, EBlockQltRelation.NEAREST_NORTH_NEIGHBORING_BLOCK_OF);
		e.enrich();
		
		// get all textual elements, which are links, from the left part of WP
		IWebDocumentBlock doc = api.getObjectsByType(IWebDocumentBlock.class).get(0).as(IWebDocumentBlock.class);
		Rectangle2D wpArea = doc.getTopWebPage().as(IQntBlock.class).getArea();
		wpArea.xMax = wpArea.xMax/2;//px
		wpArea.yMax = wpArea.yMax/2;//px
		IResults textRes = api.getObjectsContainedInArea(wpArea,
				new IGenericIEFilter<IQntBlock>() {
					@Override public EFilterResult apply(
							IQntBlock avar) {
//if (avar.canAs(IHtmlText.class)) System.out.println("HAS LINK: "+(avar.as(IHtmlText.class).getHtmlLink()!=null)+" txt:"+avar.as(IHtmlText.class).getContent());
						if (avar.canAs(IHtmlText.class)
								&& avar.as(IHtmlText.class).getHtmlLink()!=null)
							return EFilterResult.ACCEPT;
						else
							return EFilterResult.REJECT;
				} }, IHtmlText.class);
		
		// connect textual elements by relation EAST_NEAREST_NEIGHBORING_BLOCK_OF and BOTTOM_ALIGNED_WITH
		IResults textSeqRes
				= api.groupInSeq(textRes, new IPredicate2<IInstanceAdp>() {
			@Override public Boolean apply(IInstanceAdp avar1, IInstanceAdp avar2) {
				IQltBlock qltb1 = avar1.as(IQltBlock.class);
				IQltBlock qltb2 = avar2.as(IQltBlock.class);
				return qltb1.hasRelation(qltb2, EBlockQltRelation.NEAREST_NORTH_NEIGHBORING_BLOCK_OF);
			}
		});
		
		// split sequences if a distance is different more than 20% or more than 5 px between elements
		// and if their alignment is different.
		final List<IResults> resList = new LinkedList<IResults>();
		
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(textSeqRes);
		
		api.forEach(textSeqRes
				, new IProcedure<IInstanceAdp>() {
					@Override public void apply(IInstanceAdp avar) {
						resList.add(
							api.splitSeqSimpleSeqPairs(avar.as(IResults.class)
									, new IFunction2<IInstanceAdp, Object>() {
								@Override public Object apply(IInstanceAdp avar1, IInstanceAdp avar2) {
									Object[] rez = new Object[2];
									rez[0] = avar1.as(IQntBlock.class).getRelationAsDouble
											(avar2, EBlockQntRelationType.QNT_DISTANCE);
									Set<EBlockQltRelation> s = new LinkedHashSet<EBlockQltRelation>();
									
									Collection<IOutline> c = avar1.as(IBox.class).getClientRects();
									IQltBlock qb1 = (c.size()>0)?c.iterator().next().as(IQltBlock.class):avar1.as(IQltBlock.class);
									c = avar2.as(IBox.class).getClientRects();
									IQltBlock qb2 = (c.size()>0)?c.iterator().next().as(IQltBlock.class):avar2.as(IQltBlock.class);
									if (qb2.hasRelation(qb1, EBlockQltRelation.LEFT_ALIGNED_WITH))
										s.add(EBlockQltRelation.LEFT_ALIGNED_WITH);
									if (qb2.hasRelation(qb1, EBlockQltRelation.RIGHT_ALIGNED_WITH))
										s.add(EBlockQltRelation.RIGHT_ALIGNED_WITH);
									if (qb2.hasRelation(qb1, EBlockQltRelation.CENTERED_HORIZONTALLY_WITH))
										s.add(EBlockQltRelation.CENTERED_HORIZONTALLY_WITH);
									rez[1] = s;
//System.out.println(avar1.getContent()+":: x:"+qb1.as(IQntBlock.class).getXMin()+" -- "+avar2.getContent()+":: x:"+qb2.as(IQntBlock.class).getXMin());
									return rez; 
								}
								
							}, new IFunction2<Object, Double>() {
								@Override public Double apply(Object avar1, Object avar2) {
									double d = 0;
									double dist1 = (Double)((Object[])avar1)[0];
									double dist2 = (Double)((Object[])avar2)[0];
									if (SFuzzyComparatorDouble.equalProc(dist1, dist2, 20, 5))
										d = 0;
									else if (SFuzzyComparatorDouble.lessProc(dist1, dist2, 20, 5))
										d = -1;
									else d = 1;
									Set<EBlockQltRelation> al1 = (Set<EBlockQltRelation>)((Object[])avar1)[1];
									Set<EBlockQltRelation> al2 = (Set<EBlockQltRelation>)((Object[])avar2)[1];
									if (Sets.intersection(al1, al2).size() == 0)
										return (d<=0)?-1d:1d;
									return d;
								} }
							) );
		} } );
		
		
		// join all resluts --- sequences
		textSeqRes = api.union(resList);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println(textSeqRes);
		
		// filter results
		textSeqRes = api.filter(textSeqRes,
				new IPredicate<IInstanceAdp>() {
					@Override public Boolean apply(IInstanceAdp avar2) {
						IResults avar = avar2.as(IResults.class);
						// !. Quantity of elements: 3<=N<=20
//		System.out.println("===== Size: "+avar.size());
						if (avar.size()<3 || avar.size()>20) {
//		System.out.println(avar.getResultContent().get(0).getContent());
							return false;
						}
						
						Integer fontSim = api.aggr(avar, new IFunction<IInstanceAdp, Set<String>>() {
							@Override public Set<String> apply(IInstanceAdp avar) {
								return new HashSet<String>(avar.as(IHtmlText.class).getFontFamilyList());
							}} , new IFunction<List<Set<String>>, Integer>() {
							@Override public Integer apply(List<Set<String>> avar) {
								Set<String> rez = Collections.emptySet();
								for (Set<String> s : avar) {
									if (rez.size() == 0) rez = s;
									else rez = Sets.intersection(rez, s);
								}
								return rez.size();
							} } );
						
						if (fontSim == 0)
							return false;
						
						// !.
						Double maxDist = api.maxSeqPairs(avar, new IFunction2<IInstanceAdp, Double>() {
									@Override
									public Double apply(IInstanceAdp avar1, IInstanceAdp avar2) {
//		System.out.println("Dist: <"+avar1.getContent()+" | "+avar2.getContent()+"> = "
//		+ avar1.as(IQntBlock.class).getRelationAsDouble(avar2
//				, EBlockQntRelationType.DISTANCE));
										return avar1.as(IQntBlock.class).getRelationAsDouble(avar2
												, EBlockQntRelationType.QNT_DISTANCE);
						} });
						
//		System.out.println("maxDist: "+maxDist);
						
						// !. max height >= max distance between elements
						Double maxHeight = api.max(avar, new IFunction<IInstanceAdp, Double>() {
									@Override public Double apply(IInstanceAdp avar) {
//		System.out.println("Height: "+avar.getContent()+" = "+avar.as(IQntBlock.class).getHeight());
										return avar.as(IQntBlock.class).getHeight();
						} } );
//		System.out.println("maxHeight: "+maxHeight);
						// maxHeight>=maxDist/2
						if (maxHeight<maxDist/2)
							return false;
						
						// !. Quantity of rows <3 for every element.
						Double maxRows = api.max(avar, new IFunction<IInstanceAdp, Double>() {
									@Override public Double apply(IInstanceAdp avar) {
//		System.out.println("Rows: "+avar.getContent()
//		+" = "+avar.as(IBox.class).getClientRects().size());
										return (double)avar.as(IBox.class).getClientRects().size();
						} } );
//		System.out.println("maxRows: "+maxRows);
						if (maxRows > 4)
							return false;
						
						return true;
			} } );
				
//		System.out.println(textSeqRes.size());
				
				return (List) textSeqRes.getResultContent();
				
//				api.dumpModel(EWPOntSubModel.LOGICAL_MODEL, "/home/ruslan/tmp/dumps/dumpLM.rdf");
//				api.dumpModel(EWPOntSubModel.INTERFACE_MODEL, "/home/ruslan/tmp/dumps/dumpIM.rdf");
				
			}

}
