/**
 * 
 */
package tuwien.dbai.wpps.methods.cnn;

import java.util.LinkedList;
import java.util.List;

import tuwien.dbai.wpps.common.callback.IFunction;
import tuwien.dbai.wpps.common.geometry.Rectangle2D;
import tuwien.dbai.wpps.common.geometry.Rectangle2DUtils;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.impllib.IIEPredicate;
import tuwien.dbai.wpps.core.ie.impllib.IIEProcedure;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlImage;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.interf.IHtmlText;

import com.google.common.collect.Lists;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 25, 2012 8:28:21 PM
 */
public class ImageAndCaptionQnt extends AWPUWrapper {

	public ImageAndCaptionQnt(AWPUMethodDescription description) {
		super(description);
	}

	final int imageMinArea = 5000;
	final int captionIndent = 20;
	
@Override
protected List<IResults> _extractResults() {
final IIEBasisAPI api = getIEAPI().getIEBasisAPI();
final List<IResults> rez = new LinkedList<IResults>();

// 1. get all images with the area at least 'imageMinArea' (px^2)
IResults imgRes = api.getObjectsByType(
	new IIEPredicate() {
		@Override public Boolean apply(final IInstanceAdp avar) {
			return Rectangle2DUtils.area(avar.as(IQntBlock.class).getArea())>=imageMinArea;
		}
	}
	, IHtmlImage.class);

//2. find relevant caption for each image
api.forEach(imgRes, new IIEProcedure() {
	@Override public void apply(IInstanceAdp img) {
		// 2.1 get get all text elements which intersect a specific area
		Rectangle2D area = img.as(IQntBlock.class).getArea();
		area.yMax+=captionIndent; // increase the area changing the yMax by 'captionIndent'(px)
		IResults txts = api.getObjectsIntersectingArea(area, null, IHtmlText.class); // use R-tree to get text elements intersecting the specific area; null -- additional filter is not used
		
		// 2.2. find a caption (text element with the biggest font size) 
		if (txts.size()>0) { // if at least one text element was selected
			// order a list of the acquired text elements by their font size in the descending order
			txts = api.orderBy(txts, new IFunction<IInstanceAdp, Comparable<?>>() {
				@Override public Comparable<?> apply(IInstanceAdp avar) {
					return avar.as(IHtmlText.class).getFontSize();
				}}, -1); // <0 is descending order, >=0 is ascending order 
			rez.add(api.toResults(Lists.newArrayList(txts.get(0), img))); // add the first element from the list (caption) and image into the result list
		}
		else
			rez.add(api.toResults(Lists.newArrayList(img))); // add only image into the result list
} } );
return rez;
}

	@Override
	protected void _dumpIntoLM(List<IResults> results) {
		for (IResults r : results) {
			addLogicalStructure(r.convertTo(ISequence.class));
			r.convertTo(IBoundingBlock.class);
			r.convertTo(IQntBlock.class);
		}
	}

}


//final List<IInstanceAdp> imgList = new LinkedList<IInstanceAdp>();
//final List<IInstanceAdp> imgList = new LinkedList<IInstanceAdp>();
//api.getObjectsByType(
//		new IPredicate<IInstanceAdp>() {
//			Set<IInstanceAdp> considered = new HashSet<IInstanceAdp>(500);
//			@Override public Boolean apply(final IInstanceAdp avar) {
//				IQntBlock qb = avar.as(IQntBlock.class);
//				double a = Rectangle2DUtils.area(qb.getArea());
//				if (a<5000) {
//				}
//				else {
//					if (!considered.contains(avar)) {
//						considered.add(avar);
//						IQntBlock topImg = qb; Integer di = qb.getDrawId();
//						IResults imgRes2 = api.getObjectsIntersectingArea(qb.getArea(), null, IHtmlImage.class);
//						for (IInstanceAdp img2 : imgRes2) {
//							if (!considered.contains(img2)) {
//								considered.add(img2);
//								IQntBlock qb2 = img2.as(IQntBlock.class);
//								double a2 = Rectangle2DUtils.area(qb2.getArea());
//								if (a<5000) {
//								}
//								else {
//									Integer di2 = qb2.getDrawId();
//									if (di2 != null && di2>di) {
//										di = di2;
//										topImg = qb2;
//									}
//								}
//							}
//						}
//						imgList.add(topImg);
//					}
//				}
//				return false;
//			}
//		}
//		, IHtmlImage.class);
//
//IResults imgRes = api.toResults(imgList);