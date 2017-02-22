/**
 * 
 */

package tuwien.dbai.wpps.methods.eval.basic;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jul 26, 2013 1:05:12 AM
 */
public class Cont11DQueryBasicRelSIRules extends AWPUWrapper {
	private static final Logger log = Logger.getLogger(Cont11DQueryBasicRelSIRules.class);
	
private static final String filePath = "/home/ruslan/tmp/eval/Cont11DQueryBasicRelSIRules.csv";

	/**
	 * @param description
	 */
	public Cont11DQueryBasicRelSIRules(AWPUMethodDescription description) {
		super(description);
	}

	@Override
	protected List<IResults> _extractResults() {
		
		final IIEBasisAPI api = getIEAPI().getIEBasisAPI();
		
StatLib S = new StatLib(api);
S.setPhMFillingTS(getState());
System.out.println("PhMFillingTS: "+S.getPhMFillingTS());
		
S.startAcquiringAreaTS();
		IWebDocumentBlock doc = api.getObjectByType(IWebDocumentBlock.class);
		final Rectangle2D area = doc.getTopWebPage().as(IQntBlock.class).getArea();
		area.xMin += area.width() / 4;
		area.xMax -= area.width() / 3;
		area.yMin += area.height() / 4;
		area.yMax -= area.height() / 3;
S.stopAcquiringAreaTS();
System.out.println("AcquiringAreaTS: "+S.getAcquiringAreaTS());
		
S.startEnriching();
		ObjectsPublicFactory opf = getIEAPI().getObjectsFactory();
		final IQntBlock areaQnt = opf.createQntBlock(opf.createOutline(null), area);
		
		BasicSpatialRelationsEnricherBasedOnQntInfo e = getIEAPI().getEnricher(BasicSpatialRelationsEnricherBasedOnQntInfo.class);
		e.init(false, EBlockQltRelationType.RCC8);
		e.enrich();
S.stopEnriching();
		
S.startQuerying();
		final IResults rez = api.union(
				api.getSubjectsForQltBGMRelation(areaQnt, EBlockQltRelation.NTPP)
				, api.getSubjectsForQltBGMRelation(areaQnt, EBlockQltRelation.TPP)
				, api.getSubjectsForQltBGMRelation(
						new IPredicate<IInstanceAdp>() {
							@Override public Boolean apply(IInstanceAdp avar) {
								return !avar.equals(areaQnt); }}
						, areaQnt, EBlockQltRelation.EQUAL)
				);
S.stopQuerying();
S.save(filePath);
System.out.println("1: "+S.getQueryingTS()+" cnt: "+rez.size());

S.startQuerying();
final IResults  rez2 = api.union(
		api.getSubjectsForQltBGMRelation(areaQnt, EBlockQltRelation.NTPP)
		, api.getSubjectsForQltBGMRelation(areaQnt, EBlockQltRelation.TPP)
		, api.getSubjectsForQltBGMRelation(
				new IPredicate<IInstanceAdp>() {
					@Override public Boolean apply(IInstanceAdp avar) {
						return !avar.equals(areaQnt); }}
				, areaQnt, EBlockQltRelation.EQUAL)
		);
S.stopQuerying();
System.out.println("2: "+S.getQueryingTS()+" cnt: "+rez2.size());


		return new ArrayList<IResults>(){{
			add(rez);
		}};
	}

	@Override
	protected void _dumpIntoLM(List<IResults> results) {
		IResults rez = results.get(0);
		if (rez.size() == 0) {
if (log.isTraceEnabled()) log.trace("No results");
			return;
		}
//		rez.convertTo(IBoundingBlock.class);
//		rez.convertTo(IQntBlock.class);

		ObjectsPublicFactory opf = getIEAPI().getObjectsFactory();
		ISequence seq = opf.createEmptySequence(rez);
		for (IInstanceAdp adp : rez) {
			seq.appendItem(opf.createSequenceItem(adp));
		}
		
		addLogicalStructure(seq);
	}
	

}
