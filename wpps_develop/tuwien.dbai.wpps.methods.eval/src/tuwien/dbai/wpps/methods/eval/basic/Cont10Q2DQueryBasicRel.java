/**
 * 
 */

package tuwien.dbai.wpps.methods.eval.basic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.callback.IPredicate2;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.enriching.BasicSpatialRelationsEnricherBasedOnQntInfo;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelationType;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 26, 2013 1:05:12 AM
 */
public class Cont10Q2DQueryBasicRel extends AWPUWrapper {
	private static final Logger log = Logger.getLogger(Cont10Q2DQueryBasicRel.class);

private static final String filePath = "/home/ruslan/tmp/eval/Cont10Q2DQueryBasicRel.csv";
	
	/**
	 * @param description
	 */
	public Cont10Q2DQueryBasicRel(AWPUMethodDescription description) {
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
		IResults rezEq = api.getForQltBGMRelation(
			new IPredicate2<IInstanceAdp>() {
				@Override public Boolean apply(IInstanceAdp subj,
						IInstanceAdp obj) {
					return !subj.equals(obj);
			}}, EBlockQltRelation.EQUAL);
		final IResults rez = api.union(
				api.getForQltBGMRelation(EBlockQltRelation.NTPP)
				, api.getForQltBGMRelation(EBlockQltRelation.TPP)
				, rezEq
				, swap(rezEq)
				, api.getForQltBGMRelation(EBlockQltRelation.NTPPi)
				, api.getForQltBGMRelation(EBlockQltRelation.TPPi)
				);
S.stopQuerying();

S.save(filePath);

System.out.println(rez.size());

		return new ArrayList<IResults>(){{
			add(rez);
		}};
	}
	
	private IResults swap(IResults rezin) {
		Iterator<IInstanceAdp> iter = rezin.iterator();
		List<IResults> rez = new ArrayList<IResults>(rezin.size());
		while (iter.hasNext()) {
			IResults rezin2 = iter.next().as(IResults.class);
			rez.add(getIEAPI().getIEBasisAPI().toResults(rezin2.getResultContent()));
		}
		return getIEAPI().getIEBasisAPI().toResults(rez);
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
