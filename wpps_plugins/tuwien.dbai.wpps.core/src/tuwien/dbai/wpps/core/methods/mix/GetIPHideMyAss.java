/**
 * 
 */
package tuwien.dbai.wpps.core.methods.mix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.callback.IPredicate2;
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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 12, 2012 8:17:57 PM
 */
public class GetIPHideMyAss extends AWPUWrapper {

	/**
	 * @param description
	 */
	public GetIPHideMyAss(AWPUMethodDescription description) {
		super(description);
	}
	
	@Override
	protected void _dumpIntoLM(List<IResults> results) {
		for(final IResults r :results) {
//			System.out.println(r);
			ISequence seq = r.convertTo(ISequence.class);
//			seq.addLabel("Horizontally oriented navigation menu.");
			addLogicalStructure(seq);
			r.convertTo(IBoundingBlock.class);
			r.convertTo(IQntBlock.class);
//			System.out.println(r.as(IQntBlock.class).getArea());
//			System.out.println(seq.getStructElements());
		}
	}
	
	@Override
	protected List<IResults> _extractResults() {
		IIEBasisAPI api = getIEAPI().getIEBasisAPI();
		//1. add relation nearest-east-neighbor-of for all textual elements
		AsymmetricNeighborhoodEnricher e = super.getIEAPI().getEnricher(AsymmetricNeighborhoodEnricher.class);
		e.init(IHtmlText.class, EBlockQltRelation.NEAREST_EAST_NEIGHBORING_BLOCK_OF);
		e.enrich();
		//2. Get textual element with text="IP address"
		@SuppressWarnings("unchecked")
		IResults resHeader = api.getObjectsByType(
				new IPredicate<IInstanceAdp>() {
					@Override public Boolean apply(IInstanceAdp avar) {
						return avar.as(IHtmlText.class).getText().equals("IP address");
					}
				}
				, IHtmlText.class);
		//3. convert first (it is only 1 element) gotten element into quantitative block.
		IQntBlock ipHeader =  resHeader.getResultContent().get(0).as(IQntBlock.class);
		//4. get quantitative block representing web document.
		@SuppressWarnings("unchecked")
		IQntBlock doc = api.getObjectsByType(IWebDocumentBlock.class)
				.get(0).as(IQntBlock.class);
		//5. get all textual elements below text "IP address"
		IResults resText = api.getObjectsContainedInArea(
				new Rectangle2D(ipHeader.getXMin()-10, ipHeader.getYMax()
						, ipHeader.getXMax()+50, doc.getYMax())
				, new IGenericIEFilter<IQntBlock>() {
					@Override public EFilterResult apply(IQntBlock avar) {
						return (avar.canAs(IHtmlText.class))?EFilterResult.ACCEPT
								:EFilterResult.REJECT;
		} } );
		
		//6. Connect textual elements by relation nearest-east-neighbor-of
		IResults textSeqRes
				= api.groupInSeq(resText, new IPredicate2<IInstanceAdp>() {
			@Override public Boolean apply(IInstanceAdp avar1, IInstanceAdp avar2) {
				IQltBlock qltb1 = avar1.as(IQltBlock.class);
				IQltBlock qltb2 = avar2.as(IQltBlock.class);
				return qltb2.hasRelation(qltb1, EBlockQltRelation.NEAREST_EAST_NEIGHBORING_BLOCK_OF);
		} } );
		
		//7. Print results.
		List<IResults> results = new ArrayList<IResults>();
		Iterator<IInstanceAdp> iterRes = textSeqRes.getResultContent().iterator();
		while (iterRes.hasNext()) {
			IResults tmpItRes = iterRes.next().as(IResults.class);
			if (tmpItRes.size() > 3)
				results.add(tmpItRes);
//			Iterator<IInstanceAdp> iterRes2 = tmpItRes.getResultContent().iterator();
//			while (iterRes2.hasNext()) {
//				System.out.print(iterRes2.next().as(IHtmlText.class).getText());
//			}
//			System.out.println();
		}
		return results;
		
	}

}
