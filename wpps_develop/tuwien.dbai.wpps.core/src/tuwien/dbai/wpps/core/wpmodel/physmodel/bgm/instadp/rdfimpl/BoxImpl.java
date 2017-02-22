/**
 * File name: BlockContainmentFuncs.java
 * @created: May 6, 2011 9:44:27 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl;

import java.util.Collection;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.callback.IArrFunction;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.ICheckImplInitialization;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBoxType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfInnerBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfOuterBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfOutline;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib.CompositeBlockLib;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Resource;

// It has also name OuterInnerBlocksAF

/**
 * <p>To implement its methods static class {@link CompositeBlockLib} should be used.</p>
 *
  * <p>Functions related to the Composite and Basic blocks and their structural relations
  * such as containment.</p>
 * 
 * <P>Directly, functionality for this class is used in following classes:
 * {@link RdfBoundingBlock}, {@link RdfBox}, {@link RdfInnerBlock}, {@link RdfOuterBlock},
 * {@link RdfOutline}, {@link RdfViewPortBlock}, {@link RdfWebDocumentBlock}.</P>
 * 
 * <P>This additional functionality has similar interface with
 * {@link WebDocumentBlockImpl},
 * {@link ViewPortBlockImpl},
 * {@link OuterInnerBlocksAF}.</P>
 * 
 * @created: May 6, 2011 9:44:27 PM
 * @author Ruslan (ruslanrf@gmail.com)
 *
 */
@Singleton
public final class BoxImpl implements ICheckImplInitialization {
	private static final Logger log = Logger.getLogger(BoxImpl.class);
	
	public BoxImpl() {
if (log.isTraceEnabled()) log.trace(TSForLog.getTS(log)+"Constructing implementation.");
	}
	
//	/**
//	 * @param getContainingBoundingBlocks provides function with one argument and
//	 * which should be of type <code>{@linkplain Resource}</code>, and returns collection of objects
//	 * of the same type.
//	 */
//	public void setGetStructContainingBoundingBlocks(IArrFunction<Object, Object> getStructContainingBoundingBlocks) {
//		this.getContainingBoundingBlocks = getStructContainingBoundingBlocks;
//	}
//
//	/**
//	 * @param structContainsBlock provides function with tow argument
//	 * of type <code>{@linkplain Resource}</code>,
//	 * and returns <code>Boolean</code>.
//	 */
//	public void setStructContainsBlock(IArrFunction<Object, Object> structContainsBlock) {
//		this.structContainsBlock = structContainsBlock;
//	}
//
//	/**
//	 * @param getContainedBlocks provides function with one argument and
//	 * which should be of type <code>{@linkplain Resource}</code>, and returns collection of objects
//	 * of the same type.
//	 */
//	public void setGetStructContainedBlocks(IArrFunction<Object, Object> getStructContainedBlocks) {
//		this.getStructContainedBlocks = getStructContainedBlocks;
//	}
//
////	/**
////	 * @param addContainedBlock provides function with one argument and
////	 * which should be of type <code>{@linkplain Resource}</code>.
////	 */
////	public void setAddStructContainedBlock(IArrFunction<Object, Object> addStructContainedBlock) {
////		this.addStructContainedBlock = addStructContainedBlock;
////	}
//	
//	/**
//	 * @param getInnerBlock provides function with one argument and
//	 * which should be of type <code>{@linkplain Resource}</code>, and returns object
//	 * of the same type.
//	 */
//	public void setGetInnerBlock(final IArrFunction<Object, Object> getInnerBlock) {
//		this.getInnerBlock = getInnerBlock;
//	}
//
//	/**
//	 * @param addInnerBlock provides function with two arguments
//	 * of type <code>{@linkplain Resource}</code>.
//	 */
//	public void setAddInnerBlock(final IArrFunction<Object, Object> addInnerBlock) {
//		this.addInnerBlock = addInnerBlock;
//	}
//
//	/**
//	 * @param getOuterBlock provides function with one argument
//	 * of type <code>{@linkplain Resource}</code>, and returns object
//	 * of the same type.
//	 */
//	public void setGetOuterBlock(final IArrFunction<Object, Object> getOuterBlock) {
//		this.getOuterBlock = getOuterBlock;
//	}
//
//	/**
//	 * @param addOuterBlock provides function with two arguments
//	 * of type <code>{@linkplain Resource}</code>.
//	 */
//	public void setAddOuterBlock(final IArrFunction<Object, Object> addOuterBlock) {
//		this.addOuterBlock = addOuterBlock;
//	}
//
//	/**
//	 * @param getBoxForInnerBlock provides function with one argument
//	 * of type <code>{@linkplain Resource}</code>, and returns object
//	 * of the same type.
//	 */
//	public void setGetBoxForInnerBlock(final IArrFunction<Object, Object> getBoxForInnerBlock) {
//		this.getBoxForInnerBlock = getBoxForInnerBlock;
//	}
//
//	public void setGetBoxType(final IArrFunction<Object, Object> getBoxType) {
//		this.getBoxType = getBoxType;
//	}
//	
//	public void setAddBoxType(final IArrFunction<Object, Object> addBoxType) {
//		this.addBoxType = addBoxType;
//	}
//	
//	/**
//	 * @param getBoxForInnerBlock provides function with one argument
//	 * of type <code>{@linkplain Resource}</code>, and returns object
//	 * of the same type.
//	 */
//	public void setGetBoxForOuterBlock(final IArrFunction<Object, Object> getBoxForOuterBlock) {
//		this.getBoxForOuterBlock = getBoxForOuterBlock;
//	}
//	
//	public void setGetStructContainingWindowBlock(final IArrFunction<Object, Object> getStructContainingWindowBlock) {
//		this.getContainingPage = getStructContainingWindowBlock;
//	}
//	
//	public void setGetStructContainingViewPortBlock(final IArrFunction<Object, Object> getStructContainingViewPortBlock) {
//		this.getContainingViewPort = getStructContainingViewPortBlock;
//	}
//	
//	public void setGetClientRects(final IArrFunction<Object, Object> getParts) {
//		this.getClientRects = getParts;
//	}
//	
//	public void setAddClientRect(final IArrFunction<Object, Object> addPart) {
//		this.addClientRect = addPart;
//	}
	
	IArrFunction<Object, Object> getContainingBoundingBlocks = null;
//	private IArrFunction<Object, Object> structContainsBlock = null;
//	private IArrFunction<Object, Object> getStructContainedBlocks = null;
//	private IArrFunction<Object, Object> addStructContainedBlock = null;
	
	
	IArrFunction<Object, Object> getInnerBlock = null;
	IArrFunction<Object, Object> addInnerBlock = null;
	IArrFunction<Object, Object> getOuterBlock = null;
	IArrFunction<Object, Object> addOuterBlock = null;
	IArrFunction<Object, Object> getBoxForInnerBlock = null;
	IArrFunction<Object, Object> getBoxForOuterBlock = null;
	
	IArrFunction<Object, Object> getBoxType = null;
	IArrFunction<Object, Object> addBoxType = null;
	
	IArrFunction<Object, Object> getContainingPage = null;
//	IArrFunction<Object, Object> getContainingViewPort = null;
	
	IArrFunction<Object, Object> getClientRects = null;
	IArrFunction<Object, Object> addClientRect = null;
	IArrFunction<Object, Object> addClientRects = null;
	
//	/**
//	 * Check if block in the composite block.
//	 */
//	public boolean structContainsBlock(final Resource thisBlock, final Resource block, final Model model) {
//		return (Boolean)structContainsBlock.apply(thisBlock, block, model);
//	}

//	/**
//	 * @return collection of contained blocks. It never returns null.
//	 */
//	@SuppressWarnings("unchecked")
//	public Set<Resource> getStructContainedBlocks(final Resource thisBlock, final Model model) {
//		return (Set<Resource>)getStructContainedBlocks.apply(thisBlock, model);
//	}

	
//	public void addStructContainedBlock(Resource thisBlock, Resource block) {
//		addStructContainedBlock.apply(thisBlock, block);
//	}

	/**
	 * Getting composite block which contains specified block <code>thisBlock</code>.
	 * This function is used in bounding blocks ({@link IBoundingBlock}, {@link IBoundingBlock}),
	 * web page block (({@link IWebDocumentBlock}, {@link RdfWebDocumentBlock}))
	 * and viewport block(({@link IViewPortBlock}, {@link RdfViewPortBlock})).
	 * 
	 * Such object property as {@link StructBlockGeomModelOnt#inCompositeBlock} or
	 * its inverse ({@link StructBlockGeomModelOnt#containsBlock}),
	 * or {@link StructBlockGeomModelOnt#containsBlocks}. 
	 * 
	 * @param thisBoundungBlock
	 * @return never null.
	 */
	@SuppressWarnings("unchecked")
	public Collection<Resource> getContainingBoundingBlocks(final Resource thisBox) {
		return (Set<Resource>)getContainingBoundingBlocks.apply(thisBox);
	}

	/**
	 * Get inner block. If it does not exists, return null.
	 */
	public Resource getInnerBlock(final Resource box) {
		return (Resource)getInnerBlock.apply(box);
	}

	/**
	 * Is used only once for a box: during its creation. 
	 */
	public void addInnerBlock(final Resource box, final Resource inner) {
		addInnerBlock.apply(box, inner);
	}

	/**
	 * Get outer block. It must exist.
	 */
	public Resource getOuterBlock(final Resource box) {
		return (Resource) getOuterBlock.apply(box);
	}

	/**
	 * Is used only once for a box: during its creation.
	 */
	public void addOuterBlock(final Resource box, Resource outer) {
		addOuterBlock.apply(box, outer);
	}

	/**
	 * To get the Box for the inner block we analaze statements which contains
	 * property {@link StructBlockGeomModelOnt#isInnerBlockOf} or its inverse
	 * ({@link StructBlockGeomModelOnt#hasInnerBlock}), as well as
	 * {@link StructBlockGeomModelOnt#containsBlocks}. The use of property depends on the
	 * configuration of the ontology.
	 *  
	 * @param innerBlock
	 * @return Respective Box. It should not be <CODE>null</CODE> value, otherwise it means that
	 * we have inner block in the ontology which does not relate to any boxes.
	 */
	public Resource getBoxForInnerBlock(final Resource innerBlock) {
		return (Resource)getBoxForInnerBlock.apply(innerBlock);
	}

	/**
	 * To get the Box for the outer block we analaze statements which contains
	 * property {@link StructBlockGeomModelOnt#isOuterBlockOf} or its inverse
	 * ({@link StructBlockGeomModelOnt#hasOuterBlock}), as well as
	 * {@link StructBlockGeomModelOnt#containsBlocks}. The use of property depends on the
	 * configuration of the ontology.
	 * 
	 * @param outerBlock
	 * @return
	 */
	public Resource getBoxForOuterBlock(final Resource outerBlock) {
		return (Resource)getBoxForOuterBlock.apply(outerBlock);
//		return factory.createBoxAdp((Resource)getBoxForOuterBlock.exe(thisBlock));
	}

//	@Override
//	public boolean structContainsOuterInnerBlock(Resource box, Resource block) {
//		return (Boolean)structContainsOuterInnerBlock.exe(box, block);
//	}
	
	
	public Resource getContainingPage(final Resource thisBox) {
		return (Resource)getContainingPage.apply(thisBox);
	}
	
//	public Resource getContainingViewPort(final Resource thisBox) {
//		return (Resource)getContainingViewPort.apply(thisBox);
//	}
	
	public EBoxType getBoxType(final Resource box) {
		return (EBoxType)getBoxType.apply(box);
	}
	
	public void addBoxType(final Resource box, final EBoxType boxType) {
		addBoxType.apply(box, boxType);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Resource> getClientRects(final Resource thisBox) {
		return (Set<Resource>)getClientRects.apply(thisBox);
	}
	
	public void addClientRect(final Resource thisBox, final Resource outline) {
		addClientRect.apply(thisBox, outline);
	}
	
	public void addClientRects(final Resource thisBox, final Collection<Resource> outline) {
		addClientRects.apply(thisBox, outline);
	}
	
	@Override
	public boolean allFunctionsAreImplemented() {
		
		if ( (getContainingBoundingBlocks != null)
//				&& (structContainsBlock != null)
//				&& (getStructContainedBlocks != null)
//				&& (addStructContainedBlock != null)
				&& (getInnerBlock != null)
				&& (addInnerBlock != null)
				&& (getOuterBlock != null)
				&& (addOuterBlock != null)
				&& (getBoxForInnerBlock != null)
				&& (getBoxForOuterBlock != null)
				&& (getContainingPage != null)
//				&& (getContainingViewPort != null)
				&& (getBoxType != null)
				&& addBoxType!=null
				&& getClientRects!=null
				&& addClientRect!=null
				&& addClientRects != null
				)
			return true;
		else
			return false;
		
	}
	
	
}
