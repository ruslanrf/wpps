/**
 * 
 */
package tuwien.dbai.wpps.core.methods.segmentation.xycut;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jdsl.core.api.ObjectIterator;
import jdsl.core.api.Tree;
import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.ObjectsPublicFactory;
import tuwien.dbai.wpps.core.wpmodel.instadp.interf.IInstanceAdp;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.im.instadp.EIMInstType;

import com.google.common.base.Preconditions;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 30, 2012 12:58:49 PM
 */
public class XYCut extends AWPUWrapper {
	
	private static final Set<EIMInstType> DEFAULT_CONSIDERED_OBJECT_TYPES
		= new LinkedHashSet<EIMInstType>();

	static {
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_BUTTON);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_CHECKBOX);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_FILE_UPLOAD);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_IMAGE);
	//	DEFAULT_CONSIDERED_OBJECT_TYPES.add(IHtmlLink.class);
	//	DEFAULT_CONSIDERED_OBJECT_TYPES.add(IHtmlListItem.class);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_PASSWORD_INPUT);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_RADIOBUTTON);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_SELECT);
	//	DEFAULT_CONSIDERED_OBJECT_TYPES.add(IHtmlTableCell.class);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_TEXT);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_TEXT_AREA);
		DEFAULT_CONSIDERED_OBJECT_TYPES.add(EIMInstType.HTML_TEXT_INPUT);
	}

	private final Set<EIMInstType> consideredObjectTypes;
	//private final Set<Class<? extends IInstanceAdp>> consideredObjectJavaTypes;
	public EIMInstType[] getConsideredObjectTypesAsArray() {
		return (EIMInstType[])consideredObjectTypes.toArray(new EIMInstType[consideredObjectTypes.size()]);
	}
	public Set<EIMInstType> getConsideredObjectTypes() {
		return consideredObjectTypes;
	}
	
	private IInstanceAdp webPage = null;

	public XYCut(AWPUMethodDescription description) {
		super(description);
		consideredObjectTypes = DEFAULT_CONSIDERED_OBJECT_TYPES;
	}

	private Collection<Class<? extends IInstanceAdp>> getConsideredObjectsJavaTypes() {
		Collection<Class<? extends IInstanceAdp>> consideredObjectJavaTypes
			= new HashSet<Class<? extends IInstanceAdp>>();
		for(EIMInstType t : consideredObjectTypes) {
			consideredObjectJavaTypes.add(t.getJavaInterface());
		}
		return consideredObjectJavaTypes;
	}
//	private List<IInstanceAdp> getConsideredObjects(IInstanceAdp webPageBlock, IIEBasisAPI api
//			, Collection<Class<? extends IInstanceAdp>> consideredObjectsJavaTypes) {
//		final IInstanceAdp wpb = webPageBlock;
//		IResults res = api.getObjectsByType2(IQntBlock.class, 
//				new IPredicate<IQntBlock>() {
//					@Override public Boolean apply(IQntBlock avar) {
//						return wpb.equals(avar.as(IBox.class).getWebPage());
//		} }
//		, consideredObjectsJavaTypes);
//		return res.getResultContent();
//	}
//	private List<IInstanceAdp> getConsideredObjects(IInstanceAdp webPageBlock, IIEBasisAPI api, Rectangle2D area
//			, Collection<Class<? extends IInstanceAdp>> consideredObjectsJavaTypes) {
//		final IInstanceAdp wpb = webPageBlock;
//		IResults res = api.getObjectsContainedInArea2(IQntBlock.class, area
//				, new IGenericIEFilter<IQntBlock>() {
//					@Override public EFilterResult apply(IQntBlock avar) {
//						return (wpb.equals(avar.as(IBox.class).getWebPage()))?EFilterResult.ACCEPT
//								:EFilterResult.REJECT;
//		} } , consideredObjectsJavaTypes);
//		return res.getResultContent();
//	}
	@Override
	protected List<IResults> _extractResults() {
		IIEBasisAPI api = getIEAPI().getIEBasisAPI();
		if (webPage == null)
			webPage = api.getObjectsByType(IWebDocumentBlock.class).get(0)
				.as(IWebDocumentBlock.class).getTopWebPage();
		
//		List<IInstanceAdp> r1 = getConsideredObjects(webPage, api, getConsideredObjectsJavaTypes());
//		List<IInstanceAdp> r2 = getConsideredObjects(webPage, api
//				, webPage.as(IQntBlock.class).getArea()
//				, getConsideredObjectsJavaTypes());
//		Set<IInstanceAdp> s1 = Sets.newHashSet(r1.iterator());
//		Set<IInstanceAdp> s2 = Sets.newHashSet(r2.iterator());
//		System.out.println(Sets.difference(s1, s2));
//		System.out.println(Sets.difference(s2, s1));
//		return Collections.emptyList();
		
		XYCutAlgorithm xyCutAlg = new XYCutAlgorithm(
				webPage, api, getConsideredObjectsJavaTypes());
		Tree xyTree = xyCutAlg.buildXYTree();
		return resultsAsSequence(xyTree);
	}
	
	@Override
	protected void _dumpIntoLM(List<IResults> results) {
		_dumpAsSequence(results);
	}
	
	private List<IResults> resultsAsSequence(Tree xyTree) {
		List<IResults> rezList = new ArrayList<IResults>(1);
		List<IInstanceAdp> items = new ArrayList<IInstanceAdp>(xyTree.size());
		ObjectsPublicFactory opf = getIEAPI().getObjectsFactory();
		ObjectIterator iter = xyTree.elements();
		XYBlock rootBlock = (XYBlock)xyTree.root().element();
		while (iter.hasNext()) {
			XYBlock block = (XYBlock)iter.nextObject();
			if (block != rootBlock) {
				IOutline oln = opf.createOutline(null);
				opf.createQntBlock(oln, block.getArea());
				items.add(oln);
			}
		}
		IResults res = getIEAPI().getIEBasisAPI().toResults(items);
		IBoundingBlock bb = res.convertTo(IBoundingBlock.class);
		opf.createQntBlock(bb, rootBlock.getArea());
		rezList.add(res);
		return rezList;
	}
	
//	private List<IResults> resultsAsSequence(Tree xyTree) {
//		List<IResults> rezList = new ArrayList<IResults>(1);
//		List<IInstanceAdp> items = new ArrayList<IInstanceAdp>(xyTree.size());
//		ObjectIterator iter = xyTree.elements();
//		XYBlock rootBlock = (XYBlock)xyTree.root().element();
//		ObjectsPublicFactory opf = getIEAPI().getObjectsFactory();
//		ISequence seq = opf.createEmptySequence(null);
//		opf.createOutline(seq);
//		opf.createQntBlock(seq, rootBlock.getArea());
//		while (iter.hasNext()) {
//			XYBlock block = (XYBlock)iter.nextObject();
//			if (block != rootBlock) {
//				ISequenceItem seqI = opf.createSequenceItem(null);
//				opf.createOutline(seqI);
//				opf.createQntBlock(seqI, block.getArea());
//				seq.appendItem(seqI);
//				items.add(seqI);
//			}
//		}
//		IResults res = getIEAPI().getIEBasisAPI().toResults(items);
//		res.mapTo(seq);
//		rezList.add(res);
//		addLogicalStructure(seq);
//		return rezList;
//	}
	
	private void _dumpAsSequence(List<IResults> results) {
		for (IResults res : results) {
			ISequence seq = res.convertTo(ISequence.class);
			Preconditions.checkNotNull(seq);
			addLogicalStructure(seq);
		}
	}

}
