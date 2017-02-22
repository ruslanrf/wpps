/**
 * 
 */
package tuwien.dbai.wpps.core.methods.mix;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.callback.IPredicate;
import tuwien.dbai.wpps.common.callback.IProcedure;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.impllib.IGenericIEFilter;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMNode;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMText;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMTraversalNode;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlElement;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;

public class TestWrapper extends AWPUWrapper {
	private final static Logger log = Logger.getLogger(TestWrapper.class);

	/**
	 * @param description
	 */
	public TestWrapper(AWPUMethodDescription description) {
		super(description);
	}

	protected void _dumpIntoLM(List<IResults> results) {
//		for (IResults r : results) {
//			addLogicalStructure(r.convertTo(ISequence.class));
//			r.convertTo(IBoundingBlock.class);
//			r.convertTo(IQntBlock.class);
//		}
	}
	
	@SuppressWarnings("unchecked")
	protected List<IResults> _extractResults() {
		// Get API
		final IIEBasisAPI api = getIEAPI().getIEBasisAPI();
		
		Rectangle2D area = null;
		IResults res1 = api.getObjectsIntersectingArea(area, new IGenericIEFilter<IQntBlock>(){
			public IGenericIEFilter.EFilterResult apply(IQntBlock avar) {
				if (true) {
					return EFilterResult.ACCEPT;
				}
				return EFilterResult.REJECT;
			}
		});
		
		IResults res2 = api.orderBy(res1, 
				new IFunction<IInstanceAdp, Integer>() {
					@Override public Integer apply(IInstanceAdp avar) {
						return avar.as(IQntBlock.class).getDrawId();
					}
			
		}
				, -1);
		
		IInstanceAdp inst = res2.get(0);
		
		api.forEach(api.getObjectsByType(IHtmlText.class)
				, new IProcedure<IInstanceAdp>() {
					@Override public void apply(IInstanceAdp avar) {
						if (avar.canAs(IDOMTraversalNode.class)) {
							
						IDOMTraversalNode par = avar.as(IDOMTraversalNode.class).getParent();
						
						if (par != null) {
//							if (avar.canAs(IDOMText.class)) {
//								String str = avar.as(IHtmlText.class).getText();
//								//String str = avar.as(IDOMText.class).getText();
//								if (str != null && str.equals("Яндекс.Браузер"))
//									System.out.println("!!"+par.as(IDOMElement.class).getName());
//							}
							System.out.println(avar + " has parent "+par);
						}
						else
							System.err.println(avar + "has no parent");
						}
						else
							System.err.println(avar + "is not DOM element");
		} } );
		
		return Collections.emptyList();
	}
	
//	@SuppressWarnings("unchecked")
//	protected List<IResults> _extractResults() {
//		// Get API
//		final IIEBasisAPI api = getIEAPI().getIEBasisAPI();
//		
//		// Enrich model with additional relation NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF which is not basic
//		AsymmetricOrthogonalVisibilityEnricher e = getIEAPI().getEnricher(AsymmetricOrthogonalVisibilityEnricher.class);
//		e.init(new Class[]{IHtmlImage.class, IHtmlText.class}, EBlockQltRelation.NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF);
//		e.enrich();
//		
//		final List<IResults> rez = new LinkedList<IResults>(); // results are stored here
//		
//		// get all images
//		IResults imgRes =  api.getObjectsByType(IHtmlImage.class);
//		
//		// for every image...
//		api.forEach(imgRes,
//			
//			// procedure which is called for every image in the result list imgRes
//			new IProcedure<IInstanceAdp>() {
//			@Override public void apply(IInstanceAdp img) {
//				// transform img to qualitative block
//				final IQltBlock qlImg = img.as(IQltBlock.class);
//				
//				// get all html text which is under img
//				IResults txtRes = api.getObjectsByType(new IPredicate<IInstanceAdp>() {
//					@Override public Boolean apply(IInstanceAdp txt) { // get objects (text) where img (qlImg) NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF txt
//						return qlImg.hasRelation(txt, EBlockQltRelation.NORTH_ORTHOGONAL_VISIBLE_BLOCK_OF);
//					} }
//					, IHtmlText.class // get text
//					);
//				
//				if (txtRes.size()>0) {
//					// order all the text elements for the img in descending order by their y-coordinate
//					txtRes = api.orderBy(txtRes, new IFunction<IInstanceAdp, Double>(){
//						@Override public Double apply(IInstanceAdp avar) {
//							return avar.as(IQntBlock.class).getYMin();
//						} }
//					, 1);
//					rez.add(api.toResults(Lists.newArrayList(txtRes.get(0), img))); // if there is at least 1 corresponding text element, return this element and image as a result item
//				}
//				else
//					rez.add(api.toResults(Lists.newArrayList(img))); // if there is no text returs only img as a result item 
//		}});
//		
//		
//		return rez;
//	}
	
}

