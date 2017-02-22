/**
 * 
 */

package tuwien.dbai.wpps.methods.eval.basic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.callback.IFunction2;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.callback.IPredicate2;
import tuwien.dbai.wpps.common.callback.IProcedure;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.enriching.BasicSpatialRelationsEnricherBasedOnQntInfo;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 26, 2013 1:05:12 AM
 */
public class Cont3Q2CExhSearchBasicRel extends AWPUWrapper {
	private static final Logger log = Logger.getLogger(Cont3Q2CExhSearchBasicRel.class);

private static final String filePath = "/home/ruslan/tmp/eval/Cont3Q2CExhSearchBasicRel.csv";
	
	/**
	 * @param description
	 */
	public Cont3Q2CExhSearchBasicRel(AWPUMethodDescription description) {
		super(description);
	}

	@Override
	protected List<IResults> _extractResults() {
		
		final IIEBasisAPI api = getIEAPI().getIEBasisAPI();
		
		// ---------------------------------------------------------------
//		final int x=5;
//final IResults rez1 =  api.getObjectsByType( // select all blocks which are images and text elements satsfying the predicate specified
//	new IPredicate<IInstanceAdp>() {
//		@Override public Boolean apply(IInstanceAdp avar) {
//			return avar.as(IQntBlock.class).getWidth()>x; // request the interface of the quantitative block and check the width
//	} }
//	, IHtmlImage.class
//	, IHtmlText.class);
		// ---------------------------------------------------------------
//		Rectangle2D area = null;
//final IResults rez2 = api.getObjectsContainedInArea(area
//	, new IGenericIEFilter<IQntBlock>() {
//		@Override public EFilterResult apply(IQntBlock avar) {
//			return (Rectangle2DUtils.area(avar.getArea())>x)
//				?EFilterResult.ACCEPT:EFilterResult.REJECT;
//} } );
		// ---------------------------------------------------------------
IResults rez1 = null;
IResults rezSameFGColorGroups = api.groupInSetsSimple(rez1
	, new IPredicate2<IInstanceAdp>() {
	@Override public Boolean apply(IInstanceAdp avar1, IInstanceAdp avar2) {
		return avar1.as(IHtmlElement.class).getForegroundTColor()
			.equals(avar2.as(IHtmlElement.class).getForegroundTColor());
} } );
		// ---------------------------------------------------------------
//		IResults rez1 = null;
final IResults rezSameHorizontalDirectionGroups = api.groupInSeq(rez1
	, new IPredicate2<IInstanceAdp>() {
	@Override public Boolean apply(IInstanceAdp avar1, IInstanceAdp avar2) {
	IQltBlock qltb1 = avar1.as(IQltBlock.class);
	IQltBlock qltb2 = avar2.as(IQltBlock.class);
	return qltb2.hasRelation(qltb1, EBlockQltRelation.EAST_ORTHOGONAL_VISIBLE_BLOCK_OF);
} } );
		// ---------------------------------------------------------------
//final IResults rez = api.union(rezSameHorizontalDirection, rezSameVerticalDirection);
		// ---------------------------------------------------------------
final List<Double> distAvgArr = new ArrayList<Double>(rezSameHorizontalDirectionGroups.size());

api.forEach(rezSameHorizontalDirectionGroups // iterate over the horizontal sequences of objects
	, new IProcedure<IInstanceAdp>() {
		@Override public void apply(IInstanceAdp avar) {
			distAvgArr.add(
				api.avgSeqPairs(avar.as(IResults.class) // average distance between pairs if adjacent objects in avar
					, new IFunction2<IInstanceAdp, Double>() {
						@Override public Double apply(IInstanceAdp avar1,
								IInstanceAdp avar2) {
							return avar1.as(IQntBlock.class)
									.getRelationAsDouble(avar2, EBlockQntRelationType.QNT_DISTANCE);
				} } )
			);
} } );




		//---------------------------------------------------------------


StatLib S = new StatLib(api);
S.setPhMFillingTS(getState());
System.out.println("PhMFillingTS: "+S.getPhMFillingTS());
		
S.startAcquiringAreaTS();
//		IWebDocumentBlock doc = api.getObjectByType(IWebDocumentBlock.class);
//		final Rectangle2D area = doc.getTopWebPage().as(IQntBlock.class).getArea();
//		area.xMin += area.width() / 4;
//		area.xMax -= area.width() / 3;
//		area.yMin += area.height() / 4;
//		area.yMax -= area.height() / 3;
S.stopAcquiringAreaTS();
System.out.println("AcquiringAreaTS: "+S.getAcquiringAreaTS());
		
S.startEnriching();
//		ObjectsPublicFactory opf = getIEAPI().getObjectsFactory();
//		final IQntBlock areaQnt = opf.createQntBlock(opf.createOutline(null), area);
		
		BasicSpatialRelationsEnricherBasedOnQntInfo e = getIEAPI().getEnricher(BasicSpatialRelationsEnricherBasedOnQntInfo.class);
		e.init(true, EBlockQltRelationType.RCC8);
		e.enrich();
S.stopEnriching();
		
S.startQuerying();
//		final IResults rez = api.getObjects(new IPredicate<IInstanceAdp>() {
//			@Override public Boolean apply(IInstanceAdp avar) {
//				return !avar.equals(areaQnt) && 
//						(avar.as(IQltBlock.class).hasRelation(areaQnt, EBlockQltRelation.NTPP)
//								|| avar.as(IQltBlock.class).hasRelation(areaQnt, EBlockQltRelation.TPP)
//								|| avar.as(IQltBlock.class).hasRelation(areaQnt, EBlockQltRelation.EQUAL)
//						);
//			}
//		});

final List<List<IInstanceAdp>> pairsList = new LinkedList<List<IInstanceAdp>>();
api.getObjects(new IPredicate<IInstanceAdp>() {
	@Override public Boolean apply(final IInstanceAdp avar) {
		final IQltBlock avar_ = avar.as(IQltBlock.class);
		api.getObjects(new IPredicate<IInstanceAdp>(){
			@Override public Boolean apply(final IInstanceAdp avar2) {
				final IQltBlock avar2_ = avar2.as(IQltBlock.class);
				if (
						!avar_.equals(avar2_)
						&& ( avar_.as(IQltBlock.class).hasRelation(avar2_, EBlockQltRelation.EQUAL)
						 || avar_.as(IQltBlock.class).hasRelation(avar2_, EBlockQltRelation.TPP)
						 || avar_.as(IQltBlock.class).hasRelation(avar2_, EBlockQltRelation.NTPP)
						 ) )
					pairsList.add(new ArrayList<IInstanceAdp>(2)
							{{add(avar); add(avar2);}}
					);
				return false;
		} } );
		return false;
	}
});

S.stopQuerying();
S.save(filePath);
		
System.out.println(pairsList.size());

return new ArrayList<IResults>();
	}

	@Override
	protected void _dumpIntoLM(List<IResults> results) {
//		IResults rez = results.get(0);
//		if (rez.size() == 0) {
//if (log.isTraceEnabled()) log.trace("No results");
//			return;
//		}
//		
////		rez.convertTo(IBoundingBlock.class);
////		rez.convertTo(IQntBlock.class);
//		
//		ObjectsPublicFactory opf = getIEAPI().getObjectsFactory();
//		
//		ISequence seq = opf.createEmptySequence(rez);
//		
//		for (IInstanceAdp adp : rez) {
//			seq.appendItem(opf.createSequenceItem(adp));
//		}
//		
//		addLogicalStructure(seq);
	}
	

}
