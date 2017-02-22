package tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpllib;
/**
 * File name: CompositeBlockLib.java
 * @created: May 8, 2011 9:39:19 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import tuwien.dbai.wpps.common.TSForLog;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.ICompositeBlock;
import tuwien.dbai.wpps.ontquerying.SPARQLCommonQueryExecutor;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Seq;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;

/**
 * Library of static functions which is dedicated to composite and bounding blocks.
 * 
 * @created: May 8, 2011 9:39:19 PM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 * @see CompositeBlock
 * @see ICompositeBlock 
 */
public final class CompositeBlockLib {
	private final static Logger log = Logger.getLogger(CompositeBlockLib.class);
	
	/**
	 * Get composite blocks of the type <CODE>containingBlockTypeUri</CODE>
	 * which contain specified block <code>block</code>. Composite block should contain
	 * blocks in a sequence (property {@link StructBlockGeomModelOnt#containsBlocks}).
	 * Query model via SPARQL.
	 * 
	 * @param block specified block. Do not use WebpageBlock, ViewPort, because result will be always null, and this function is too expensive for that.
	 * @param containingBlockTypeUri type of the containing block
	 *   (usually it is {@link StructBlockGeomModelOnt#BoundingBlock bounding block).
	 * @param model model where object is contained
	 * @return collection of resources
	 * @see SPARQLCommonQueryExecutor#getResourceWhichHasSpecifiedResourceInItsPropertyContainer(Model, String, String)
	 */
	final static public List<Resource> getStructContainingBlocksFromContainer(final Resource block
			, final String containingBlockTypeUri, final Model model) {
		return SPARQLCommonQueryExecutor.getResourceWhichHasSpecifiedResourceInItsPropertyContainer(
				model, containingBlockTypeUri, StructBlockGeomModelOnt.containsBlocks.getURI(), block.getURI());
	}
	
	final static public List<Resource> getStructContainingBlocksFromContainer(final Resource containingType
			, final Property prop, final Resource contained, final Model model) {
		return SPARQLCommonQueryExecutor.getResourceWhichHasSpecifiedResourceInItsPropertyContainer(
				model, containingType.getURI(), prop.getURI(), contained.getURI());
	}
	
	@Deprecated
	final static public Resource getStructContainingViewPortFromContainer(final Resource box, final Model model) {
		final List<Resource> resSet = getStructContainingBlocksFromContainer(box, StructBlockGeomModelOnt.ViewPort.getURI(), model);
		if (resSet == null || resSet.size() == 0)
			return null;
		else
			return resSet.iterator().next();
	}
	
	final static public Resource getStructContainingViewPort(final Resource block, final Model model) {
		final Set<Resource> resSet = getStructContainingCompositeBlocksWOClassifier(block, StructBlockGeomModelOnt.ViewPort, model);
		if (resSet.size()==0)
			return null;
		Iterator<Resource> iter = resSet.iterator();
		final Resource rez = iter.next();
		if (iter.hasNext())
			log.warn(TSForLog.getTS(log)+"Block "+block.getLocalName()+" corresponds to more than 1 view port.");
		return rez;
	}
	
//	/**
//	 * <P>
//	 * Get composite blocks of the type <CODE>containingBlockTypeUri</CODE>
//	 * which contain specified block <code>block</code>.
//	 * We bear in mind that there are classifier which compute property {@link BlockGeomObjectOnt#inCompositeBlock}
//	 * based on the property {@link BlockGeomObjectOnt#containsBlock}.
//	 * </P>
//	 * @param block specified block.
//	 * @param containingBlockType type of the containing block
//	 *   (usually it is {@link BlockGeomObjectOnt#BoundingBlock bounding block).
//	 * @param model where object is contained.
//	 * @return collection of resources containing <code>block</code>.
//	 */
//	final static public Collection<Resource> getStructContainingCompositeBlocksWithClassifier(final Resource block
//			, final Resource containingBlockType, final Model model) {
//		final Set<Resource> resSet = new HashSet<Resource>();
//		final StmtIterator iter = block.listProperties(BlockGeomObjectOnt.inCompositeBlock);
//		while (iter.hasNext()) {
//			final Resource nxt = iter.next().getResource();
//			if (model.contains(nxt, RDF.type, containingBlockType)) {
//				resSet.add(nxt);
//			}
//		}
//		return resSet;
//	}
	
	/**
	 * Get composite blocks of the type <CODE>containingBlockTypeUri</CODE>
	 * which contain specified block <code>block</code>.
	 * We bear in mind that there are no classifier which compute property {@link StructBlockGeomModelOnt#inCompositeBlock}
	 * based on the property {@link StructBlockGeomModelOnt#containsBlock}. Therefore, we analyse property 
	 * {@link StructBlockGeomModelOnt#containsBlock}
	 * 
	 * @param block specified block.
	 * @param containingBlockType type of the containing block
	 *   (usually it is {@link StructBlockGeomModelOnt#BoundingBlock bounding block).
	 * @param model where object is contained.
	 * @return array of resources containing <code>block</code>.
	 */
	final static public Set<Resource> getStructContainingCompositeBlocksWOClassifier(final Resource block
			, final Resource containingBlockType, final Model model) {
		final StmtIterator iter = model.listStatements(new SimpleSelector(null, StructBlockGeomModelOnt.containsBlock, block) {
			public boolean selects(final Statement s)
            {
				if (model.contains(s.getSubject(), RDF.type, containingBlockType)) {
					return true;
				}
				return false;
            }
		});
		final Set<Resource> resSet = new HashSet<Resource>(); 
		while (iter.hasNext()) {
			resSet.add(iter.next().getSubject());
		}
		return resSet;
	}
	
	/**
	 * Add block <code>containedBlock</code> as a contained block into the <code>compositeBlock</code>.
	 * The block is added into the sequence of contained blocks which is accessible via property {@link StructBlockGeomModelOnt#containsBlocks containsBlocks}
	 * of the block-based geometric model.
	 * If there is no a sequence, we create new one.
	 * 
	 * @param compositeBlock
	 * @param containedBlock
	 */
	@Deprecated
	final static public void addContainedBlockToSeq(final Resource compositeBlock
			, final Resource containedBlock, final Model model) {
		final StmtIterator iter = model.listStatements(compositeBlock, StructBlockGeomModelOnt.containsBlocks, (RDFNode)null);
		if (iter.hasNext()) {
			final Seq seq = iter.next().getSeq();
			// for a developer
			if (iter.hasNext())
				log.warn(TSForLog.getTS(log)+"Composite block "+compositeBlock.getLocalName()+" has more than 1 sequence for contained blocks.");
			seq.add(containedBlock);
		}
		else {
			final Seq seq = model.createSeq();
			seq.add(containedBlock);
			model.add(compositeBlock, StructBlockGeomModelOnt.containsBlocks, seq);
		}
		iter.close();
	}
	
//	public static final boolean checkBlockContainmentFromSeq(final Resource compositeBlock
//			, final Resource containedBlock) {
//		final StmtIterator iter = compositeBlock.listProperties(StructBlockGeomModelOnt.containsBlocks);
//		if (iter.hasNext()) {
//			final Seq seq = iter.next().getSeq();
//			if (iter.hasNext())
//				log.error(TSForLog.getTS(log)+"Composite block "+compositeBlock+" has more than 1 sequence of contained blocks.");
//			final NodeIterator seqIter = seq.iterator();
//			while(seqIter.hasNext()) {
//				if (containedBlock.equals(seqIter.nextNode().asResource()))
//					return true;
//			}
//		}
//		return false;
//	}
	
//	/**
//	 * Get all structurally contained blocks from sequence (property {@link StructBlockGeomModelOnt#containsBlocks}).
//	 */
//	public static final List<Resource> getStructContainedBlocksFromSeq(final Resource compositeBlock) {
//		final StmtIterator iter = compositeBlock.listProperties(StructBlockGeomModelOnt.containsBlocks);
//		final List<Resource> l = new LinkedList<Resource>();
//		if (iter.hasNext()) {
//			final Seq seq = iter.next().getSeq();
//			if (iter.hasNext())
//				log.error(TSForLog.getTS(log)+"Composite block "+compositeBlock+" has more than 1 sequence of contained blocks.");
//			final NodeIterator seqIter = seq.iterator();
//			while(seqIter.hasNext()) {
//				l.add(seqIter.nextNode().asResource());
//			}
//		}
//		return l;
//	}
	
	
//	public static final Collection<IAbstractBlock> getSpatiallyContainedBoxes(final IAbstractBlock compositeBlock, final ISpatialIndex si, final IFilter filter) {
//		return si.getContainments(compositeBlock, filter);
//	}
	
}
