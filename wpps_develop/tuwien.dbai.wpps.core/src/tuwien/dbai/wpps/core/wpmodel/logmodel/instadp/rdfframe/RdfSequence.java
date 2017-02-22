/**
 * 
 */
package tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfframe;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.instadp.rdfimpl.TypeCastImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ILogicalObject;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequence;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.interf.ISequenceItem;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.LogicalObjectImpl;
import tuwien.dbai.wpps.core.wpmodel.logmodel.instadp.rdfimpl.SequenceImpl;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 3, 2012 1:51:01 PM
 */
public class RdfSequence extends RdfLogicalObject implements ISequence {
//	private static final Logger log = Logger.getLogger(RdfSequence.class);
	
	private final SequenceImpl sequenceImpl;
	
	@Inject
	public RdfSequence(
			@Assisted final Resource inst,
//			@AnnotLogicalModel final Model rdfModel,
			final WPOntSubModels tmp,
			final RdfInstAdpFactoryWrap rdfInstAdpFactoryWrap,
			final TypeCastImpl typeCastImpl
			, final LogicalObjectImpl logicalObjectImpl
			, final SequenceImpl sequenceImpl) {
		super(inst, rdfInstAdpFactoryWrap, typeCastImpl, logicalObjectImpl);
		this.sequenceImpl = sequenceImpl;
	}
	
	@Override
	public List<ISequenceItem> getItems() {
		final List<Resource> resList = sequenceImpl.getItems(rdfInst);
		return Lists.transform(resList,
				new Function<Resource, ISequenceItem>() {
					@Override public ISequenceItem apply(Resource arg0) {
						return rdfInstAdpFactoryWrap.createAdp(arg0, ISequenceItem.class);
				} } );
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ILogicalObject> getStructElements() {
		return (List)Collections.unmodifiableList(getItems());
	}
	
//	@Override
//	public List<ILogicalObject> getBasicElements() {
//		final List<IItem> itms = getItems();
//		final List<ILogicalObject> rez = new ArrayList<ILogicalObject>();
//		rez.addAll(itms);
//		return rez;
//	}

//	@Override
//	public List<ILogicalObject> getStructElementsAndThis() {
//		final List<ISequenceItem> itms = getItems();
//		final List<ILogicalObject> rez = new ArrayList<ILogicalObject>();
//		rez.add(this);
//		rez.addAll(itms);
//		return rez;
//	}
	
	@Override
	public void appendItems(final Collection<ISequenceItem> itemList) {
		sequenceImpl.appendItems(rdfInst, 
			Collections2.transform(itemList,
				new Function<ISequenceItem, Resource>() {
					@Override public Resource apply(ISequenceItem arg0) {
						return ((IRdfResourceAdp)arg0).getRdfResource();
		} } ) );
	}

	@Override
	public void appendItem(ISequenceItem item) {
		testForRdfResourceInterface(item);
		sequenceImpl.appendItem(rdfInst, ((IRdfResourceAdp)item).getRdfResource());
	}

}
