/**
 * 
 */
package tuwien.dbai.wpps.core.junit.test.physmodel.bgm.adp.instadp.rdfimpl;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tuwien.dbai.wpps.core.module.InstanceAdaptersModule;
import tuwien.dbai.wpps.core.module.OntologyModule;
import tuwien.dbai.wpps.core.module.WPPSConfigModule;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IViewPortBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.ViewPortBlockImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 27, 2011 11:25:09 PM
 */
public class TestViewPortBlockStructContainmentImpl_1 {

	Stopwatch stopwatch = new Stopwatch();
	
	Injector inj = null;
	
	Resource thisViewPortRes= null;
	Resource containedBoxRes1 = null;
	Resource containedBoxRes2 = null;
	Resource containingBBRes = null;
	Resource additionalBoxRes = null;
	
	ViewPortBlockImpl viewportImpl = null;
	BoundingBlockImpl bbImpl = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
System.out.println( "=== setUp() ===");
		
		stopwatch.start();
		
		init1();
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Init for ontology with EOneToManyRelation.IN_COLLECTION and WO any classifiers/reasoners.
	 */
	private final void init1() {
		inj = Guice.createInjector(new InstanceAdaptersModule(), new OntologyModule(), new WPPSConfigModule());
		
		WPOntSubModels wpOntModel = inj.getInstance(WPOntSubModels.class);
		OntModelAdp bbgmAdp = wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
		Assert.assertNotNull(bbgmAdp);
		
		Model bbgmOnt = bbgmAdp.getRdfModel();
		thisViewPortRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"thisViewPortRes", StructBlockGeomModelOnt.ViewPort);
		containedBoxRes1 = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containedBoxRes1", StructBlockGeomModelOnt.Box);
		containedBoxRes2 = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containedBoxRes2", StructBlockGeomModelOnt.Box);
		containingBBRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containingBBRes", StructBlockGeomModelOnt.BoundingBlock);
		additionalBoxRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"additionalBoxRes", StructBlockGeomModelOnt.Box);
		
		viewportImpl = inj.getInstance(ViewPortBlockImpl.class);
//		viewportImpl.addStructContainedBlock(thisViewPortRes, containedBoxRes1, bbgmOnt);
//		viewportImpl.addStructContainedBlock(thisViewPortRes, containedBoxRes2, bbgmOnt);
		
		bbImpl = inj.getInstance(BoundingBlockImpl.class);
		bbImpl.addContainedBlock(containingBBRes, thisViewPortRes);
	}
	

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.ViewPortBlockImpl#getContainingBoundingBlocks(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetStructContainingBoundingBlocks() {
		System.out.println( "=== testGetStructContainingBoundingBlocks() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IViewPortBlock vp = factory.createAdp(thisViewPortRes, IViewPortBlock.class);
		Collection<IBoundingBlock> setContainingBBBlocks=vp.getContainingBoundingBlocks();
		Assert.assertEquals(setContainingBBBlocks.size(), 1);
		Iterator<IBoundingBlock> iter = setContainingBBBlocks.iterator();
		IBoundingBlock blocktmp = iter.next();
		Assert.assertTrue( containingBBRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource()) );
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.ViewPortBlockImpl#structContainsBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testStructContainsBlock() {
		System.out.println( "=== testStructContainsBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IViewPortBlock vp = factory.createAdp(thisViewPortRes, IViewPortBlock.class);
		Assert.assertTrue(vp.containsBlock(factory.createAdp(containedBoxRes1, IBox.class)));
		Assert.assertTrue(vp.containsBlock(factory.createAdp(containedBoxRes2, IBoundingBlock.class)));
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.ViewPortBlockImpl#getStructContainedBlocks(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetStructContainedBlocks() {
		System.out.println( "=== testGetStructContainedBlocks() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IViewPortBlock vp = factory.createAdp(thisViewPortRes, IViewPortBlock.class);
		Collection<IBlock> setContainedBlocks=vp.getContainedBlocks();
		Assert.assertEquals(setContainedBlocks.size(), 2);
		Iterator<IBlock> iter = setContainedBlocks.iterator();
		IBlock blocktmp = iter.next();
		Assert.assertTrue(containedBoxRes1.equals(((IRdfResourceAdp)blocktmp).getRdfResource())
				|| containedBoxRes2.equals(((IRdfResourceAdp)blocktmp).getRdfResource()));
		blocktmp = iter.next();
		Assert.assertTrue(containedBoxRes1.equals(((IRdfResourceAdp)blocktmp).getRdfResource())
				|| containedBoxRes2.equals(((IRdfResourceAdp)blocktmp).getRdfResource()));
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.ViewPortBlockImpl#addContainedBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource)}.
	 */
	@Test
	public void testAddStructContainedBlock() {
//		System.out.println( "=== testAddStructContainedBlock() ===");
//		
//		stopwatch.reset();stopwatch.start();
//		
//		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
//		IViewPortBlock vp = factory.createAdp(thisViewPortRes, IViewPortBlock.class);
//		
//		IBox newBox = factory.createAdp(additionalBoxRes, IBox.class);
//		newBox = factory.createAdp(additionalBoxRes, IBox.class);
//		
//		ViewPortBlockStructContainmentImpl impl = inj.getInstance(ViewPortBlockStructContainmentImpl.class);
//		WPOntModel wpOntModel = inj.getInstance(WPOntModel.class);
//		OntModelAdp bbgmAdp = wpOntModel.getOntAdapter(EWPOntModel.BLOCK_GEOM_OBJECT_STRUCT_MODEL);
//		impl.addStructContainedBlock(thisViewPortRes, additionalBoxRes, bbgmAdp.getRdfModel());
//		
//		Assert.assertTrue(vp.structContainsBlock(newBox));
//		
//		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.ViewPortBlockImpl#allFunctionsAreImplemented()}.
	 */
	@Test
	public void testAllFunctionsAreImplemented() {
		System.out.println( "=== testAllFunctionsAreImplemented() ===");
		
		stopwatch.reset();stopwatch.start();
		Assert.assertTrue(inj.getInstance(ViewPortBlockImpl.class).allFunctionsAreImplemented());
		
		System.out.println("time elapsed:"+stopwatch);
	}

}
