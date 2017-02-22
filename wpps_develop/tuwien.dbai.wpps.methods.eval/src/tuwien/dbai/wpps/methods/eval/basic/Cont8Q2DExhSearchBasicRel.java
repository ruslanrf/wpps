/**
 * 
 */

package tuwien.dbai.wpps.methods.eval.basic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.enriching.BasicSpatialRelationsEnricherBasedOnQntInfo;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.ObjectsPublicFactory;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 26, 2013 1:05:12 AM
 */
public class Cont8Q2DExhSearchBasicRel extends AWPUWrapper {
	private static final Logger log = Logger.getLogger(Cont8Q2DExhSearchBasicRel.class);

private static final String filePath = "/home/ruslan/tmp/eval/Cont8Q2DExhSearchBasicRel.csv";
	
	/**
	 * @param description
	 */
	public Cont8Q2DExhSearchBasicRel(AWPUMethodDescription description) {
		super(description);
	}

	@Override
	protected List<IResults> _extractResults() {
		final IIEBasisAPI api = getIEAPI().getIEBasisAPI();
		
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
		e.init(false, EBlockQltRelationType.RCC8);
		e.enrich();
S.stopEnriching();

S.startQuerying();
//		final IResults rez = api.getObjects(new IPredicate<IInstanceAdp>() {
//			@Override public Boolean apply(IInstanceAdp avar) {
//				return !avar.equals(areaQnt) && 
//						(avar.as(IQltBlock.class).hasRelation(areaQnt, EBlockQltRelation.NTPP)
//								|| avar.as(IQltBlock.class).hasRelation(areaQnt, EBlockQltRelation.TPP)
//								|| avar.as(IQltBlock.class).hasRelation(areaQnt, EBlockQltRelation.EQUAL)
//						|| areaQnt.as(IQltBlock.class).hasRelation(avar, EBlockQltRelation.NTPPi)
//								|| areaQnt.as(IQltBlock.class).hasRelation(avar, EBlockQltRelation.TPPi)
//								|| areaQnt.as(IQltBlock.class).hasRelation(avar, EBlockQltRelation.EQUAL)
//						);
//			}
//		});

final List<List<IInstanceAdp>> pairsList = new LinkedList<List<IInstanceAdp>>();
api.getObjects(new IPredicate<IInstanceAdp>() {
	@Override public Boolean apply(final IInstanceAdp avar) {
		api.getObjects(new IPredicate<IInstanceAdp>(){
			@Override public Boolean apply(final IInstanceAdp avar2) {
				if (
						!avar.equals(avar2)
						&& ( avar.as(IQltBlock.class).hasRelation(avar2, EBlockQltRelation.NTPP)
						 || avar.as(IQltBlock.class).hasRelation(avar2, EBlockQltRelation.TPP)
						 || avar.as(IQltBlock.class).hasRelation(avar2, EBlockQltRelation.EQUAL)
						 || avar2.as(IQltBlock.class).hasRelation(avar, EBlockQltRelation.NTPPi)
						 || avar2.as(IQltBlock.class).hasRelation(avar, EBlockQltRelation.TPPi)
						 || avar2.as(IQltBlock.class).hasRelation(avar, EBlockQltRelation.EQUAL)
						 )
				)
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
////		rez.convertTo(IBoundingBlock.class);
////		rez.convertTo(IQntBlock.class);
//		
//		ObjectsPublicFactory opf = getIEAPI().getObjectsFactory();
//		ISequence seq = opf.createEmptySequence(rez);
//		for (IInstanceAdp adp : rez) {
//			seq.appendItem(opf.createSequenceItem(adp));
//		}
//		
//		addLogicalStructure(seq);
	}
	

}
