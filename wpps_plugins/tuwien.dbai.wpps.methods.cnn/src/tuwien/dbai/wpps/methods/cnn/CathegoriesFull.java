package tuwien.dbai.wpps.methods.cnn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.callback.IPredicate2;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.enriching.AsymmetricOrthogonalVisibilityEnricher;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.ObjectsPublicFactory;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;

/**
 * 
 */

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 25, 2012 4:01:13 PM
 */
public class CathegoriesFull extends AWPUWrapper {

	public CathegoriesFull(AWPUMethodDescription description) {
		super(description);
	}

	@Override
	protected List<IResults> _extractResults() {
		IIEBasisAPI api = getIEAPI().getIEBasisAPI();
		
		AsymmetricOrthogonalVisibilityEnricher e = getIEAPI()
				.getEnricher(AsymmetricOrthogonalVisibilityEnricher.class);
		e.init(IHtmlText.class, EBlockQltRelation.NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF);
		e.enrich();
		
		// --- get left aligned links of the same color, font size, with distance about < 0.5 of the font size ---
		
		
		// -- get all links
		IResults res = api.getObjectsByType(
			new IPredicate<IInstanceAdp>() {
				@Override public Boolean apply(IInstanceAdp avar) {
					return avar.as(IHtmlText.class).hasHtmlLink();
				}
			}
			, IHtmlText.class);
		
		
		res = api.groupInSeq(res
				, new IPredicate2<IInstanceAdp>() {
					@Override public Boolean apply(IInstanceAdp avar1, IInstanceAdp avar2) {
						float fs = avar1.as(IHtmlText.class).getFontSize();
						double dist = avar1.as(IQntBlock.class).getRelationAsDouble(avar2, EBlockQntRelationType.QNT_DISTANCE);
						boolean upper = avar1.as(IQltBlock.class).hasRelation(avar2, EBlockQltRelation.NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF);
						return dist<0.5*fs && upper;
		} });
		
		
		List<IResults> rezList = new ArrayList<IResults>(res.size());
		for (IInstanceAdp adp : res.getResultContent()) {
			IResults r = adp.as(IResults.class);
			if (r.size()>1)
				rezList.add(r);
		}
		
		
		List<IResults> cathTitleList = new ArrayList<IResults>(rezList.size());
		
		for (final IResults resTmp : rezList) {
			IResults res2 = api.getObjectsByType( new IPredicate<IInstanceAdp>() {
				@Override public Boolean apply(IInstanceAdp avar) {
					boolean upper = avar.as(IQltBlock.class).hasRelation(resTmp.get(0), EBlockQltRelation.NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF);
					if (upper) {
						float fs = avar.as(IHtmlText.class).getFontSize();
						float fs2 = resTmp.get(0).as(IHtmlText.class).getFontSize();
						if (fs>fs2) {
							double dist = avar.as(IQntBlock.class).getRelationAsDouble(resTmp.get(0), EBlockQntRelationType.QNT_DISTANCE);
							if (dist <= fs)
								return true;
						}
					}
					return false;
			} }
			, IHtmlText.class );
			cathTitleList.add(res2);
		}
		
		List<IResults> fullCathsList = new ArrayList<IResults>(rezList.size());
		for (int i=0; i<rezList.size(); i++) {
			fullCathsList.add(api.toResults(Arrays.asList(cathTitleList.get(i), rezList.get(i))));
		}
		
		return fullCathsList;
	}

	@Override
	protected void _dumpIntoLM(List<IResults> results) {
		 ObjectsPublicFactory opf = getIEAPI().getObjectsFactory();
		for(IResults r : results) {
			ISequence seq = opf.createEmptySequence(r);
			addLogicalStructure(seq);
			if (((IResults)r.get(0)).size()>0) {
				IResults resTmp = (IResults)r.get(0);
				resTmp.convertTo(IBoundingBlock.class);
				resTmp.convertTo(IQntBlock.class);
				seq.appendItem(opf.createSequenceItem(
						resTmp.get(0)));
			}
			IResults elList = (IResults)r.get(1);
			elList.convertTo(IBoundingBlock.class);
			elList.convertTo(IQntBlock.class);
			for (IInstanceAdp adp : elList){
				seq.appendItem(opf.createSequenceItem(adp));
			}
			r.convertTo(IBoundingBlock.class);
			r.convertTo(IQntBlock.class);
		}
	}

}
