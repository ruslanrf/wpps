/**
 * File name: BoxLib.java
 * @created: Apr 19, 2011 10:42:15 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.common.exceptions.UnknownValueFromPredefinedList;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpllib.InstAdpLib;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBoxType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfframe.RdfBox;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Preconditions;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Library of functions related to box.
 * 
 * @created: Apr 19, 2011 10:42:15 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * @see IBox
 * @see RdfBox
 */
final public class BoxLib {
	private static final Logger log = Logger.getLogger(BoxLib.class);
	
	// TODO: Move it to another plug-in which is respoinsible for generation of this model based on data gathered from the Mozilla.
//	/**
//	 * 
//	 * It detects either block or inline block based on the display CSS parameter.
//	 * For unknown values it returns Unknown type.
//	 *  TODO: add more cases to detect the type. Function should not return Unknown.
//	 * 
//	 * @param display
//	 * @return
//	 */
//	public static final EBoxType detectBoxTypeByCSSDisplay(final String display) {
//		if (display.equals(ECSSPropertyStringConstants.DISPLAY_BLOCK_VALUE)
//				|| display.equals(ECSSPropertyStringConstants.DISPLAY_LIST_ITEM_VALUE)
//				|| display.equals(ECSSPropertyStringConstants.DISPLAY_TABLE_VALUE)
//				|| display.equals(ECSSPropertyStringConstants.DISPLAY_TABLE_CAPTION_VALUE) // according css 2.1. it is block level element
//				) {
//			return EBoxType.BLOCK_LEVEL_ELEMENT;
//		}
//		else if (display.equals(ECSSPropertyStringConstants.DISPLAY_INLINE_VALUE)
//				|| display.equals(ECSSPropertyStringConstants.DISPLAY_INLINE_BLOCK_VALUE)
//				|| display.equals(ECSSPropertyStringConstants.DISPLAY_INLINE_TABLE_VALUE)) {
//			return EBoxType.INLINE_LEVEL_ELEMENT;
//		}
//		else if (display.equals(ECSSPropertyStringConstants.DISPLAY_TABLE_ROW_VALUE)
//				|| display.equals(ECSSPropertyStringConstants.DISPLAY_TABLE_ROW_GROUP_VALUE)
//				|| display.equals(ECSSPropertyStringConstants.DISPLAY_TABLE_HEADER_GROUP_VALUE)
//				|| display.equals(ECSSPropertyStringConstants.DISPLAY_TABLE_FOOTER_GROUP_VALUE)
//				|| display.equals(ECSSPropertyStringConstants.DISPLAY_TABLE_COLUMN_VALUE)
//				|| display.equals(ECSSPropertyStringConstants.DISPLAY_TABLE_COLUMN_GROUP_VALUE)
//				|| display.equals(ECSSPropertyStringConstants.DISPLAY_TABLE_CELL_VALUE) ) {
//			return EBoxType.TABLE_ELEMENT;
//		}
//		else
//			return EBoxType.UNKNOWN;
//	}
	
	/**
	 * Function which allows to not use model and call functions of box resource to get inner block,
	 * which is stored in property {@link StructBlockGeomModelOnt#hasInnerBlock}.
	 * 
	 * @param box
	 * @return
	 */
	@Deprecated
	public static final Resource getInnerBlock(final Resource box) {
		final StmtIterator iter = box.listProperties(StructBlockGeomModelOnt.hasInnerBlock);
		if (iter.hasNext()) {
			final Resource res = iter.next().getResource();
			if (iter.hasNext())
				log.error(TSForLog.getTS(log)+"Box "+box.getLocalName()+" has more than 1 inner block.");
			return res;
		}
		return null;
	}
	
	/**
	 * We get value of property {@link StructBlockGeomModelOnt#hasInnerBlock} for the box querying the model via Jena API.
	 * 
	 * @param box
	 * @param model
	 * @return
	 */
	public static final Resource getInnerBlock(final Resource box, final Model model) {
		return InstAdpLib.jenaTypeCastSoft(
				InstAdpLib.getValueAsRDFNodeSoft(box, StructBlockGeomModelOnt.hasInnerBlock, model)
				, Resource.class);
	}
	
	@Deprecated
	public static final Resource getInnerBlockFromSeq(final Resource box, final Model model) {
		final StmtIterator iter = model.listStatements(box, StructBlockGeomModelOnt.containsBlocks, (RDFNode)null);
		if (iter.hasNext()) {
			final Seq seq = iter.next().getSeq();
			// for a developer
			if (iter.hasNext())
				log.error(TSForLog.getTS(log)+"box "+box.getLocalName()+" has more than 1 sequence for contained blocks.");
			iter.close();
			final NodeIterator seqIter = seq.iterator();
			while(seqIter.hasNext()) {
				final Resource res = seqIter.nextNode().asResource();
				if (model.contains(res, RDF.type, StructBlockGeomModelOnt.InnerBlock))
					return res;
			}
		}
		return null;
	}
	
	/**
	 * Get inner block of the box from sequence which is stored in property {@link StructBlockGeomModelOnt#containsBlocks}.
	 * 
	 * @param box
	 * @return
	 */
	@Deprecated
	public static final Resource getInnerBlockFromSeq(final Resource box) {
		final StmtIterator iter = box.listProperties(StructBlockGeomModelOnt.containsBlocks);
		if (iter.hasNext()) {
			final Seq seq = iter.next().getSeq();
			// for a developer
			if (iter.hasNext())
				log.error(TSForLog.getTS(log)+"box "+box.getLocalName()+" has more than 1 sequence for contained blocks.");
			iter.close();
			final NodeIterator seqIter = seq.iterator();
			while(seqIter.hasNext()) {
				final Resource res = seqIter.nextNode().asResource();
				if (res.hasProperty(RDF.type, StructBlockGeomModelOnt.InnerBlock))
					return res;
			}
		}
		return null;
	}
	
	public static Set<Resource> getStructContainedBlocks(final Resource box, final Model model) {
		Set<Resource> rez = null;
		Resource res = getOuterBlock(box, model);
		if (res != null) {
			if (rez == null) rez = new HashSet<Resource>();
			rez.add(res);
		}
		res = getInnerBlock(box, model);
		if (res != null) {
			if (rez == null) rez = new HashSet<Resource>();
			rez.add(res);
		}
		if (rez == null) rez = Collections.emptySet();
		
		return rez;
	}
	
	/**
	 * Add inner block without checking on it existence.
	 * Add statement in the same model where box exists.
	 * 
	 * @param box
	 * @param innerBlock
	 */
	public static final void addInnerBlock(final Resource box, final Resource innerBlock) {
		box.addProperty(StructBlockGeomModelOnt.hasInnerBlock, innerBlock);
	}
	
	/**
	 * Add inner block without checking on it existence.
	 * We consider object property {@link StructBlockGeomModelOnt#hasInnerBlock hasInnerBlock} of the
	 * block-based geometric model.
	 * 
	 * @param box
	 * @param innerBlock
	 * @param model
	 */
	public static final void addInnerBlock(final Resource box, final Resource innerBlock, final Model model) {
		model.add(box, StructBlockGeomModelOnt.hasInnerBlock, innerBlock);
	}
	
	public static final Resource getOuterBlock(final Resource box, final Model model) {
		return InstAdpLib.jenaTypeCastSoft(
				InstAdpLib.getValueAsRDFNodeSoft(box, StructBlockGeomModelOnt.hasOuterBlock, model)
				, Resource.class);
	}
	
	@Deprecated
	public static final Resource getOuterBlock(final Resource box) {
		final StmtIterator iter = box.listProperties(StructBlockGeomModelOnt.hasOuterBlock);
		if (iter.hasNext()) {
			final Resource res = iter.next().getResource();
			if (iter.hasNext())
				log.error(TSForLog.getTS(log)+"Box "+box.getLocalName()+" has more than 1 outer block.");
			return res;
		}
		return null;
	}
	
	@Deprecated
	public static final Resource getOuterBlockFromSeq(final Resource box, final Model model) {
		final StmtIterator iter = model.listStatements(box, StructBlockGeomModelOnt.containsBlocks, (RDFNode)null);
		while (iter.hasNext()) {
			final Seq seq = iter.next().getSeq();
			// for a developer
			if (iter.hasNext())
				log.error(TSForLog.getTS(log)+"box "+box.getLocalName()+" has more than 1 sequence for contained blocks.");
			iter.close();
			final NodeIterator seqIter = seq.iterator();
			while(seqIter.hasNext()) {
				final Resource res = seqIter.nextNode().asResource();
				if (model.contains(res, RDF.type, StructBlockGeomModelOnt.OuterBlock))
					return res;
			}
		}
		return null;
	}
	
	/**
	 * Get outer block of the box from sequence which is stored in property {@link StructBlockGeomModelOnt#containsBlocks}
	 * of the block-based geometric model.
	 * 
	 * @param box
	 * @return
	 */
	@Deprecated
	public static final Resource getOuterBlockFromSeq(final Resource box) {
		final StmtIterator iter = box.listProperties(StructBlockGeomModelOnt.containsBlocks);
		while (iter.hasNext()) {
			final Seq seq = iter.next().getSeq();
			// for a developer
			if (iter.hasNext())
				log.error(TSForLog.getTS(log)+"box "+box.getLocalName()+" has more than 1 sequence for contained blocks.");
			iter.close();
			final NodeIterator seqIter = seq.iterator();
			while(seqIter.hasNext()) {
				final Resource res = seqIter.nextNode().asResource();
				if (res.hasProperty(RDF.type, StructBlockGeomModelOnt.OuterBlock))
					return res;
			}
			seqIter.close();
		}
		return null;
	}
	
	/**
	 * Add outer block without checking on it existence.
	 * Add statement in the same model where box exists.
	 * 
	 * @param box
	 * @param outerBlock
	 */
	public static final void addOuterBlock(final Resource box, final Resource outerBlock) {
		box.addProperty(StructBlockGeomModelOnt.hasOuterBlock, outerBlock);
	}
	
	/**
	 * Add outer block without checking on it existence.
	 * 
	 * @param box
	 * @param outerBlock
	 * @param model
	 */
	public static final void addOuterBlock(final Resource box, final Resource outerBlock, final Model model) {
		model.add(box, StructBlockGeomModelOnt.hasOuterBlock, outerBlock);
	}
	
	/**
	 * Add inner/outer block into the sequence corresponding to the property {@link StructBlockGeomModelOnt#containsBlocks}
	 * of the box without checking its existence.
	 * 
	 * Function just repeat a functionality of the method {@link CompositeBlockLib#addContainedBlockToSeq(Resource, Resource, Model)}
	 * 
	 * @param box
	 * @param innerOrOuterBlock
	 * 
	 * @see CompositeBlockLib#addContainedBlockToSeq(Resource, Resource, Model)
	 */
	public static final void addInnerOrOuterBlockToSeq(final Resource box, final Resource innerOrOuterBlock, final Model model) {
		CompositeBlockLib.addContainedBlockToSeq(box, innerOrOuterBlock, model);
	}
	
	/**
	 * Get box of the inner block <code>innerBlock</code> considering that model does not have a reasoner which deduce inverse relations.
	 * So, we get box querying for relation {@link StructBlockGeomModelOnt#hasInnerBlock}.
	 * 
	 * @param innerBlock
	 * @param model where statements are stored.
	 * @return box of the provided inner block.
	 */
	public static final Resource getBoxForInnerBlock(final Resource innerBlock, final Model model) {
		return InstAdpLib.getSubjectAsResourceSoft(innerBlock
				, StructBlockGeomModelOnt.hasInnerBlock, model);
	}
	
	/**
	 * Get box of the inner block <code>innerBlock</code> considering that model does have a reasoner which deduce inverse relations.
	 * In this case it is relations {@link StructBlockGeomModelOnt#hasInnerBlock} and {@link StructBlockGeomModelOnt#isInnerBlockOf}.
	 * 
	 * @param innerBlock
	 * @return box of the provided inner block.
	 */
	@Deprecated
	public static final Resource getBoxForInnerBlockWithClassifier(final Resource innerBlock) {
		final StmtIterator iter = innerBlock.listProperties(StructBlockGeomModelOnt.isInnerBlockOf);
		if (iter.hasNext()) {
			final Resource res = iter.next().getResource();
			if (iter.hasNext())
				log.error(TSForLog.getTS(log)+"Inner block "+innerBlock.getLocalName()+" has more than 1 box.");
			return res;
		}
		return null;
	}
	
	/**
	 * Get box of the outer block <code>outerBlock</code> considering that model does not have a reasoner which deduce inverse relations.
	 * So, we get box querying for relation {@link StructBlockGeomModelOnt#hasOuterBlock}.
	 * 
	 * @param outerBlock
	 * @param model where statements are stored.
	 * @return box of the provided outer block.
	 */
	public static final Resource getBoxForOuterBlock(final Resource outerBlock, final Model model) {
		return InstAdpLib.getSubjectAsResourceSoft(outerBlock
				, StructBlockGeomModelOnt.hasOuterBlock, model);
	}
	
	/**
	 * Get box of the outer block <code>outerBlock</code> considering that model does have a reasoner which deduce inverse relations.
	 * In this case it is relations {@link StructBlockGeomModelOnt#hasOuterBlock} and {@link StructBlockGeomModelOnt#isOuterBlockOf}.
	 * @param outerBlock
	 * @return box of the provided outer block.
	 */
	@Deprecated
	public static final Resource getBoxForOuterBlockWithClassifier(final Resource outerBlock) {
		final StmtIterator iter = outerBlock.listProperties(StructBlockGeomModelOnt.isOuterBlockOf);
		if (iter.hasNext()) {
			final Resource res = iter.next().getResource();
			if (iter.hasNext())
				log.error(TSForLog.getTS(log)+"Outer block "+outerBlock.getLocalName()+" has more than 1 box.");
			return res;
		}
		return null;
	}
	
	@Deprecated
	public static final Resource getBoxForInnerOrOuterBlockFromContainer(final Resource innerOrOuterBlock, final Model model) {
		Preconditions.checkNotNull(innerOrOuterBlock); Preconditions.checkNotNull(model);
		final Collection<Resource> res = CompositeBlockLib
			.getStructContainingBlocksFromContainer(innerOrOuterBlock, StructBlockGeomModelOnt.Box.getURI(), model);
		if (res.size() == 0) {
			log.warn(TSForLog.getTS(log)+"Inner or outer block "+innerOrOuterBlock.getLocalName()+" does not relate to a box.");
			return null;
		}
		if (res.size()>1)
			log.error(TSForLog.getTS(log)+"Inner or outer block "+innerOrOuterBlock.getLocalName()+" relates to more than one box.");
		return res.iterator().next();
	}
	
	/**
	 * Get box of a block which is contained in a container and correspond to the resource of type {@link StructBlockGeomModelOnt#Box Box}.
	 * 
	 * We get all resources which have sequences of the property {@link StructBlockGeomModelOnt#containsBlocks containsBlocks} with
	 * specified block <code>innerOrOuterBlock</code>. After that we get the first resource of type Box.
	 * 
	 * @param innerOrOuterBlock
	 * @return corresponding box or null if it does not exist.
	 * @see CompositeBlockLib#getStructContainingBlocksFromSeq(Resource, Model)
	 */
	@Deprecated
	public static final Resource getBoxForInnerOrOuterBlockFromContainer(final Resource innerOrOuterBlock) {
		final Collection<Resource> res = CompositeBlockLib
			.getStructContainingBlocksFromContainer(innerOrOuterBlock, StructBlockGeomModelOnt.Box.getURI(), innerOrOuterBlock.getModel());
		if (res.size() == 0) {
			log.warn(TSForLog.getTS(log)+"Inner or outer block "+innerOrOuterBlock.getLocalName()+" does not relate to a box.");
			return null;
		}
		if (res.size()>1)
			log.error(TSForLog.getTS(log)+"Inner or outer block "+innerOrOuterBlock.getLocalName()+" relates to more than one box.");
		return res.iterator().next();
	}
	
//	/**
//	 * @param box
//	 * @return CSS property of the box. <CODE>Null</CODE> if there is no CSS property.
//	 */
//	public static final CSSProperties getCSSProperties(final Resource box) {
//		final StmtIterator iter = box.listProperties(BlockGeomObjectOnt.hasComputedCssCode);
//		if(iter.hasNext()) {
//			final String cssStr = iter.next().getString();
//			if (iter.hasNext())
//				log.warn(TSForLogger.getTS(log)+"Box "+box+" has more than 1 css codes");
//			return CSSProperties.parseStringCode(cssStr);
//		}
//		log.warn(TSForLogger.getTS(log)+"Box "+box+" was asked for a css code which it does not have");
//		return null;
//	}
	
//	/**
//	 * Check is box contains specified block via property {@link StructBlockGeomModelOnt#containsBlock}.
//	 * 
//	 * @param box
//	 * @param block
//	 * @return
//	 */
//	public static final boolean checkBlockContainmentWithClassifier(final Resource box, final Resource block, final Model model) {
//		return CompositeBlockLib.checkBlockContainment(box, block, model);
//	}
	
	public static final boolean checkBlockContainmentWOClassifier(final Resource box, final Resource block, final Model model) {
		return model.contains(box, StructBlockGeomModelOnt.hasOuterBlock, block)
				|| model.contains(box, StructBlockGeomModelOnt.hasInnerBlock, block);
	}
	
	public static final Set<Resource> getClientRects(final Resource box, final Model model) {
		return InstAdpLib.fillCollectionOfValuesAsResources(box, StructBlockGeomModelOnt.hasClientRectangle
				, model, new LinkedHashSet<Resource>());
	}
	
	public static final List<Resource> getClientRectsFromContainer(final Resource box, final Model model) {
		return InstAdpLib.fillCollectionOfResourcesFromRdfSeqSoft(box
				, StructBlockGeomModelOnt.hasClientRectangles, model, new ArrayList<Resource>());
	}
	
	public static final void addClientRect(final Resource containing
			, final Resource contained, final Model model) {
		model.add(containing, StructBlockGeomModelOnt.hasClientRectangle, contained);
	}
	
	final static public void addClientRects(final Resource containing
			, final Collection<Resource> contained, final Model model) {
		Iterator<Resource> iter = contained.iterator();
		while (iter.hasNext()) {
			addClientRect(containing, iter.next(), model);
		}
	}
	
	final static public void addClientRectToSeq(final Resource containing
			, final Resource contained, final Model model) {
		InstAdpLib.appendResourceToRdfSeqOrCreate(containing
				, StructBlockGeomModelOnt.hasClientRectangles, contained, model);
	}
	
	final static public void addClientRectsToRdfSeq(final Resource containing
			, final Collection<Resource> contained, final Model model) {
		InstAdpLib.appendResourcesToRdfSeqOrCreate(containing
				, StructBlockGeomModelOnt.hasClientRectangles, contained, model);
	}
	
	/**
	 * This function allows to detect type of the box, based on the statements
	 * in the block-based geometric model.
	 * 
	 * @param box
	 * @return type of the box.
	 */
	public static final EBoxType getBoxType(final Resource box, final Model model) {
		if (model.contains(box, StructBlockGeomModelOnt.hasBoxType, StructBlockGeomModelOnt.BlockLevelElement))
			return EBoxType.BLOCK_LEVEL_ELEMENT;
		else if (model.contains(box, StructBlockGeomModelOnt.hasBoxType, StructBlockGeomModelOnt.InlineLevelElement))
			return EBoxType.INLINE_LEVEL_ELEMENT;
		else if (model.contains(box, StructBlockGeomModelOnt.hasBoxType, StructBlockGeomModelOnt.TableElement))
			return EBoxType.TABLE_ELEMENT;
		else if (model.contains(box, StructBlockGeomModelOnt.hasBoxType, StructBlockGeomModelOnt.UnknownBoxType))
			return EBoxType.UNKNOWN;
		else {
			throw new UnknownValueFromPredefinedList(log, "?");
		}
	}
	
	public static final void addBoxType(final Resource box, final EBoxType boxType, final Model model) {
			model.add(box, StructBlockGeomModelOnt.hasBoxType, boxType.getRdfResource());
	}
	
	final static public Resource getStructContainingPageFromContainer(final Resource block, final Model model) {
		final List<Resource> rl = CompositeBlockLib.getStructContainingBlocksFromContainer(StructBlockGeomModelOnt.Page
				, StructBlockGeomModelOnt.containsBoxes, block, model);
		return (rl.size() == 0)?null:rl.get(0);
	}
	
	final static public Resource getStructContainingPage(final Resource block, final Model model) {
		List<Resource> l = InstAdpLib.fillCollectionOfSubjectsAsResourcesByType(block, StructBlockGeomModelOnt.Page
				, StructBlockGeomModelOnt.containsBox, model, new ArrayList<Resource>(1));
		return (l.size() == 0)?null:l.get(0);
	}
	
}
