package tuwien.dbai.wpps.core.methods.forum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.enriching.AsymmetricNeighborhoodEnricher;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQltRelation;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQltBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;

import com.google.common.base.Stopwatch;


public class SimpleForumWrapper extends AWPUWrapper {

	Stopwatch sw = new Stopwatch();
	
	public SimpleForumWrapper(AWPUMethodDescription description) {
		super(description);
	}
	
	boolean looksLikeBashOrgDate(IInstanceAdp avar){
		IHtmlText iht = avar.as(IHtmlText.class);
		//return iht.getContent().equals("[:||||:]");
		SimpleDateFormat format =
	            new SimpleDateFormat("yyyy-mm-dd HH:mm");
		try{
			format.parse(iht.getText());
			return true;
		}catch (java.text.ParseException e) {
			return false;
		}
		
	}
	

	@Override
	protected void _dumpIntoLM(List<IResults> results) {
//		BGColorFirstEnricher dEnricher = getIEAPI().getEnricher(BGColorFirstEnricher.class);
//		dEnricher.init();
//		dEnricher.enrich();
		/*sw.start();
		List<IResults> lines = display();
		for (IResults res : lines) {
			ISequence seq = res.makeAs(ISequence.class);
			System.out.println(res.size());
			System.out.println();
			for (IInstanceAdp iBlock : res.getResultContent()) {
				System.out.println(iBlock.as(IHtmlText.class).getContent() +"Rectangle: "+iBlock.as(IQntBlock.class).getArea().toString());
			}
			seq.addLabel("line");
			System.out.println(sw + " Before addLogicalStructure");
			addLogicalStructure(seq);
			System.out.println(sw + " After addLogicalStructure");
		
		}
		System.out.println(sw + " after the cycle");
		results = null;*/
	}
	
	protected List<IResults> _extractResults() {
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	private List<IResults> display() {
		IIEBasisAPI ie = getIEAPI().getIEBasisAPI();
		Collection<IBlock> blocks = ie.getObjectsByType(IWebDocumentBlock.class).get(0).as(IWebDocumentBlock.class).getContainedBlocks();
		
		final IResults res = ie.toResults(blocks);
		
		IResults filteredRes = ie.filter(res, new IPredicate<IInstanceAdp>(){

			@Override
			public Boolean apply(IInstanceAdp avar) {
				
				return avar.canAs(IHtmlText.class) && (looksLikeBashOrgDate(avar));
			}
			
		});
		
		
		ArrayList<IResults> headers = new ArrayList<IResults>();
 		for(Iterator<IInstanceAdp> i = filteredRes.iterator(); i.hasNext();){
			IBlock date = (IBlock) i.next();
			final IQntBlock qltb1 = date.as(IQntBlock.class);
			Rectangle2D rightBlock = qltb1.getEastArea();
			rightBlock.yMin -= 10;
			rightBlock.yMax += 10;
			Rectangle2D leftBlock = qltb1.getWestArea();
			leftBlock.yMin -= 10;
			leftBlock.yMax += 10;
			
			IResults right = ie.getObjectsContainedInArea(rightBlock, IHtmlText.class);
			IResults left = ie.getObjectsContainedInArea(leftBlock,  IHtmlText.class);
			IResults candidates = ie.union(left, right);
			candidates.addResult(date);
		    candidates = ie.orderBy(candidates, 
					new Comparator<IInstanceAdp>() {

						@Override
						public int compare(IInstanceAdp arg0, IInstanceAdp arg1) {
							double x0 = arg0.as(IQntBlock.class).getXMin();
						    double x1 = arg1.as(IQntBlock.class).getXMin();
						    if (x0 > x1) return 1;
						    else if (x0 == x1) return 0;
						    else return -1;
						    
						}			
		    	    });
		    candidates.convertTo(IBoundingBlock.class);
		    candidates.convertTo(IQntBlock.class);
		    headers.add(candidates);
 		}
 		    AsymmetricNeighborhoodEnricher e1 = getIEAPI().getEnricher(AsymmetricNeighborhoodEnricher.class);
			e1.init(new Class[]{IHtmlText.class, IBoundingBlock.class}, EBlockQltRelation.NEAREST_NORTH_NEIGHBORING_BLOCK_OF);
			e1.enrich();
			ArrayList<IResults> messages = new ArrayList<IResults>();
			for (IResults candidates : headers)
 		{
		    Rectangle2D area = candidates.as(IQntBlock.class).getSouthArea();
		    area.yMax = area.yMin + 350;
		    area.xMax += 50;
		    area.xMin -= 50;
		    System.out.println("AREA:"+area);
		    IResults textBelow = ie.getObjectsContainedInArea(area, IHtmlText.class);
		    /*for (IInstanceAdp htmlText: textBelow.getResultContent()) {
				System.out.println("textBelow!:: " +  htmlText.as(IHtmlText.class).getContent() +"Rectangle: "+htmlText.as(IQntBlock.class).getArea().toString());
			}*/
		    
		    final IBoundingBlock line = candidates.as(IBoundingBlock.class);
		    final IQltBlock qltLine = line.as(IQltBlock.class);
		    
		    System.out.println( "X_MIN2 :" + line.as(IQntBlock.class).getXMin());
		    
		    System.out.println("Rdf res:"+((IRdfResourceAdp)line).getRdfResource());
		    
		   
		    System.out.println("Size!:::" + ie.getObjectsByType(IBoundingBlock.class).size());
			
		    IResults text = ie.filter(textBelow, new IPredicate<IInstanceAdp>() {
				@Override
				public Boolean apply(IInstanceAdp avar) {

					
					System.out.println("SSS:"+avar.as(IQntBlock.class).getArea()+"::"+line.as(IQntBlock.class).getArea()
							+" => "+qltLine.hasRelation(avar, EBlockQltRelation.NEAREST_NORTH_NEIGHBORING_BLOCK_OF));
					if (qltLine.hasRelation(avar, EBlockQltRelation.NEAREST_NORTH_NEIGHBORING_BLOCK_OF))
						return true;
					else return false;
				}
			});
			
			this.getIEAPI().getIEBasisAPI().dumpModel(EWPOntSubModel.QLT_BLOCK_MODEL, "a");
			this.getIEAPI().getIEBasisAPI().dumpModel(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL, "b");
			this.getIEAPI().getIEBasisAPI().dumpModel(EWPOntSubModel.INTERFACE_MODEL, "c");
			
			
		    IResults message = ie.union(candidates, text);
		    message.convertTo(IBoundingBlock.class);
		    message.convertTo(IQntBlock.class);
		    messages.add(message);
		}
		
	
		
		return messages;
		
	}

}
