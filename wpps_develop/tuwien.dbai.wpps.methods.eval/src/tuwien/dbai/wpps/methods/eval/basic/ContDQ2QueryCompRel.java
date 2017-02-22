/**
 * 
 */

package tuwien.dbai.wpps.methods.eval.basic;

import java.util.ArrayList;
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
public class ContDQ2QueryCompRel extends AWPUWrapper {
	private static final Logger log = Logger.getLogger(ContDQ2QueryCompRel.class);

private static final String filePath = "/home/ruslan/tmp/eval/Cont13Q2DQueryRDFSCompRelIRules.csv"; //TODO modify!
	
	/**
	 * @param description
	 */
	public ContDQ2QueryCompRel(AWPUMethodDescription description) {
		super(description);
	}

	@Override
	protected List<IResults> _extractResults() {
		final IIEBasisAPI api = getIEAPI().getIEBasisAPI();

StatLib S = new StatLib(api);
S.setPhMFillingTS(getState());
System.out.println("PhMFillingTS: "+S.getPhMFillingTS());

S.startAcquiringAreaTS();
//		final IWebDocumentBlock doc = api.getObjectByType(IWebDocumentBlock.class);
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
System.out.println("Enriching: "+S.getEnrichingTS());
		
S.startQuerying();
//		final IResults rez = api.getSubjectsForQltBGMRelation(
//				new IPredicate<IInstanceAdp>() {
//					@Override public Boolean apply(IInstanceAdp avar) {
//						return !avar.equals(areaQnt); }}
//				, areaQnt, EBlockQltRelation.P);
		
		final IResults rez = api.getForQltBGMRelation(
				new IPredicate2<IInstanceAdp>() {
					@Override public Boolean apply(IInstanceAdp subj, IInstanceAdp obj) {
						return !subj.equals(obj); }}
				, EBlockQltRelation.P);
		
		
S.stopQuerying();
S.save(filePath);
		
System.out.println(rez.size());

return new ArrayList<IResults>(){{
	add(rez);
}};
		
	}

	@Override
	protected void _dumpIntoLM(List<IResults> results) {
//if (log.isTraceEnabled()) log.trace("START. Dump into LM");
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
//		addLogicalStructure(seq);
//if (log.isTraceEnabled()) log.trace("FINISH. Dump into LM");
	}
	

}
