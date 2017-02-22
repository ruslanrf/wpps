/**
 * 
 */
package tuwien.dbai.wpps.core.methods.navmenu;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IFunction2;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.callback.IPredicate2;
import tuwien.dbai.wpps.common.callback.IProcedure;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.ie.impllib.IIEFilter;
import tuwien.dbai.wpps.core.ie.impllib.IIEPredicate2;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.enriching.AsymmetricOrthogonalVisibilityEnricher;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlLink;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;

import com.google.common.collect.Sets;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Mar 15, 2012 9:17:29 PM
 */
public class NavMenuHor1LevelWrapper extends AWPUWrapper {

	public NavMenuHor1LevelWrapper(AWPUMethodDescription description) {
		super(description);
	}
	
	@Override
protected void _dumpIntoLM(List<IResults> results) {
	for(final IResults r :results) {
		ISequence seq = r.convertTo(ISequence.class); // instantiate the identified navigation menu as a sequence
		addLogicalStructure(seq); // inform that this wrapper has created the object to other WPPS methods
		// make the sequence of navigation items to highlight in the WPPS GUI
		r.convertTo(IBoundingBlock.class); // instantiate navigation menu as a bounding block with navigation items as outlines
		r.convertTo(IQntBlock.class); // compute coordinates for the bounding block which is minimum bounding block for the contained elements
	}
}
	
//	@SuppressWarnings("unchecked")
//	@Override
//protected List<IResults> _extractResults() {
//final IIEBasisAPI api = super.getIEAPI().getIEBasisAPI(); // get basis WPPS API
//
//// 1. enrich the PM with the supplementary relation EAST_ORTHOGONAL_VISIBLE_BLOCK_OF only for html links
//AsymmetricOrthogonalVisibilityEnricher e = getIEAPI().getEnricher(AsymmetricOrthogonalVisibilityEnricher.class);
//e.init(IHtmlLink.class, EBlockQltRelation.EAST_ORTHOGONAL_VISIBLE_BLOCK_OF);
//e.enrich();
//		
//// 2. get all links, which contain textual elements, from the top part of a web page
//IWebDocumentBlock doc = api.getObjectByType(IWebDocumentBlock.class); // get document block
//Rectangle2D area = doc.getTopWebPage().as(IQntBlock.class).getArea(); // get top page block and request for the occupied area on a web page canvas
//area.yMax = 250; //px
//// (iii)
//IResults res = api.getObjectsContainedInArea(area // leverage R-tree to get object from the top area of a web page
//// consider only those elements which are links and contains text elements which are not empty  
//, new IIEFilter() {
//	public EFilterResult apply(IQntBlock v) {
//	  if (v.canAs(IHtmlLink.class) && v.as(IHtmlLink.class).getString().length() > 0) // (v) // (vi)
//			  return EFilterResult.ACCEPT;
//		  else return EFilterResult.REJECT;
//} } );
//
//// 3. join objects by the relations EAST_ORTHOGONAL_VISIBLE_BLOCK_OF and and BOTTOM_ALIGNED_WITH
//res = api.groupInSeq(res, new IIEPredicate2() {
//	public Boolean apply(IInstanceAdp v1, IInstanceAdp v2) {
//	  IQltBlock b1 = v1.as(IQltBlock.class);
//	  IQltBlock b2 = v2.as(IQltBlock.class);
//	  return b2.hasRelation(b1, EBlockQltRelation.EAST_ORTHOGONAL_VISIBLE_BLOCK_OF)
//	    && b2.hasRelation(b1, EBlockQltRelation.BOTTOM_ALIGNED_WITH);
//} } );
//
//return (List) res.getResultContent(); // due to the application of the method groupInSeq
//}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<IResults> _extractResults() {
System.err.println("1-NavMenuHor1LevelWrapper");
		IResults horLists = getSequences();
		List<IResults> results = (List)horLists.getResultContent();
		return results;
	}
	
	@SuppressWarnings("unchecked")
	private IResults getSequences() {
//		getState().getConfig().getQltBMRightBorderInterval()[1] = 1.5;
		
		final IIEBasisAPI api = super.getIEAPI().getIEBasisAPI();

		// get all textual elements, which are links, from the top of WP
		IWebDocumentBlock doc = api
				.getObjectsByType(IWebDocumentBlock.class)
				.get(0)
				.as(IWebDocumentBlock.class);
		
		Rectangle2D wpArea = doc.getTopWebPage().as(IQntBlock.class).getArea();
		wpArea.yMax = 250;//px
		IResults textRes = api.getObjectsContainedInArea(wpArea, 
				new IGenericIEFilter<IQntBlock>() {
			@Override public EFilterResult apply(IQntBlock avar) {
				if (avar.canAs(IHtmlLink.class)
						&& avar.as(IHtmlLink.class).getString().length() > 0)
					return EFilterResult.ACCEPT;
				else
					return EFilterResult.REJECT;
		} }
				, IHtmlLink.class);
		
		// add relation EAST_NEIGHBORING_BLOCK_OF for links
		AsymmetricOrthogonalVisibilityEnricher e = getIEAPI()
				.getEnricher(AsymmetricOrthogonalVisibilityEnricher.class);
		e.init(IHtmlLink.class, EBlockQltRelation.EAST_ORTHOGONAL_VISIBLE_BLOCK_OF);
		e.enrich();
				
		// connect textual elements by relation EAST_NEAREST_NEIGHBORING_BLOCK_OF and BOTTOM_ALIGNED_WITH
		IResults textSeqRes
				= api.groupInSeq(textRes, new IPredicate2<IInstanceAdp>() {
			@Override public Boolean apply(IInstanceAdp avar1, IInstanceAdp avar2) {
				IQltBlock qltb1 = avar1.as(IQltBlock.class);
				IQltBlock qltb2 = avar2.as(IQltBlock.class);
				return qltb2.hasRelation(qltb1, EBlockQltRelation.EAST_ORTHOGONAL_VISIBLE_BLOCK_OF)
						&& qltb2.hasRelation(qltb1, EBlockQltRelation.BOTTOM_ALIGNED_WITH);
		} } );
		
		// split sequences if a distance is different more than 20% or more than 5 px between elements.
		final List<IResults> resList = new LinkedList<IResults>();
		api.forEach(textSeqRes, new IProcedure<IInstanceAdp>() {
					@Override public void apply(IInstanceAdp avar) {
						resList.add(
							api.splitSeqSimpleSeqPairs(avar.as(IResults.class), new IFunction2<IInstanceAdp, Double>() {
										@Override public Double apply(IInstanceAdp avar1,IInstanceAdp avar2) {
											return avar1.as(IQntBlock.class).getRelationAsDouble
													(avar2, EBlockQntRelationType.QNT_DISTANCE);
								} }, 20, 5)
		); } } );
		
		// join all resluts --- sequences
		textSeqRes = api.union(resList);
		
		
		// filter results
		textSeqRes = api.filter(textSeqRes,
			new IPredicate<IInstanceAdp>() {
				@Override public Boolean apply(IInstanceAdp avar1) {
					IResults avar = avar1.as(IResults.class);
					// Quantity of elements: 3<=N<=20
					if (avar.size()<3 || avar.size()>20) {
						return false;
					}
					
					Integer fontSim = api.aggr(avar, new IFunction<IInstanceAdp, Set<String>>() {
						@Override public Set<String> apply(IInstanceAdp avar) {
							Set<String> rez = new HashSet<String>();
							for (IHtmlElement el : avar.as(IHtmlLink.class).getContent()) {
								if (el.canAs(IHtmlText.class))
									rez.addAll(el.as(IHtmlText.class).getFontFamilyList());
							}
							return rez;
							
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
									return avar1.as(IQntBlock.class).getRelationAsDouble(avar2
											, EBlockQntRelationType.QNT_DISTANCE);
					} });
					
					// !. max width >= max distance between elements
					Double maxWidth = api.max(avar, new IFunction<IInstanceAdp, Double>() {
								@Override public Double apply(IInstanceAdp avar) {
									return avar.as(IQntBlock.class).getWidth();
					} } );
					// maxWidth>=maxDist
					if (maxWidth<maxDist)
						return false;
				
					// !. Quantity of rows <3 for every element.
					Double maxRows = api.max(avar, new IFunction<IInstanceAdp, Double>() {
								@Override public Double apply(IInstanceAdp avar) {
									return (double)avar.as(IBox.class).getClientRects().size();
					} } );
					if (maxRows > 3)
						return false;
					
					return true;
		} } );
		
		return textSeqRes;
	}

}
