/**
 * 
 */
package tuwien.dbai.wpps.core.junit.test.physmodel.bgm.adp.instadp.rdfimpl;

import java.util.Collection;
import java.util.Iterator;

import org.junit.AfterClass;
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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 27, 2011 4:17:37 PM
 */
public class TestBoundingBlockStructContainmentImpl_1 {

	Stopwatch stopwatch = new Stopwatch();
	
	Injector inj = null;
	
	Resource thisBBRes = null;
	Resource containedBoxRes = null;
	Resource containedBBRes = null;
	Resource containingBBRes = null;
	
	Resource additionalBoxRes = null;
	
	BoundingBlockImpl bbimpl = null;
	
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
		thisBBRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"thisBBRes", StructBlockGeomModelOnt.BoundingBlock);
		containedBoxRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containedBoxRes", StructBlockGeomModelOnt.Box);
		containedBBRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containedBBRes", StructBlockGeomModelOnt.BoundingBlock);
		containingBBRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containingBBRes", StructBlockGeomModelOnt.BoundingBlock);
		additionalBoxRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"additionalBoxRes", StructBlockGeomModelOnt.Box);
		
		bbimpl = inj.getInstance(BoundingBlockImpl.class);
		bbimpl.addContainedBlock(containedBBRes, containedBoxRes);
		
		bbimpl.addContainedBlock(thisBBRes, containedBoxRes);
		bbimpl.addContainedBlock(thisBBRes, containedBBRes);
		
		bbimpl.addContainedBlock(containingBBRes, thisBBRes);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.adp.instadp.rdfimpl.BoundingBlockImpl#containsPage(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testStructContainsBlock() {
		System.out.println( "=== testStructContainsBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBoundingBlock bbBlock = factory.createAdp(thisBBRes, IBoundingBlock.class);
		Assert.assertTrue(bbBlock.containsBlock(factory.createAdp(containedBoxRes, IBox.class)));
		Assert.assertTrue(bbBlock.containsBlock(factory.createAdp(containedBBRes, IBoundingBlock.class)));
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.adp.instadp.rdfimpl.BoundingBlockImpl#getcontainedTopPage(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetStructContainedBlocks() {
		System.out.println( "=== testGetStructContainedBlocks() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBoundingBlock bbBlock = factory.createAdp(thisBBRes, IBoundingBlock.class);
		Collection<IBlock> setContainedBlocks=bbBlock.getContainedBlocks();
		Assert.assertEquals(setContainedBlocks.size(), 2);
		Iterator<IBlock> iter = setContainedBlocks.iterator();
		IBlock blocktmp = iter.next();
		Assert.assertTrue(containedBoxRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource())
				|| containedBBRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource()));
		blocktmp = iter.next();
		Assert.assertTrue(containedBoxRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource())
				|| containedBBRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource()));
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.adp.instadp.rdfimpl.BoundingBlockImpl#addStructContainedBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testAddStructContainedBlock() {
//		System.out.println( "=== testAddStructContainedBlock() ===");
//		
//		stopwatch.reset();stopwatch.start();
//		
//		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
//		IBoundingBlock bbBlock = factory.createAdp(thisBBRes, IBoundingBlock.class);
//		
//		IBox newBox = factory.createAdp(additionalBoxRes, IBox.class);
//		bbBlock.addStructContainedBlock(newBox);
//		
//		Assert.assertTrue(bbBlock.structContainsBlock(newBox));
//		
//		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.adp.instadp.rdfimpl.BoundingBlockImpl#getContainingBoundingBlocks(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetStructContainingBoundingBlocks() {
		System.out.println( "=== testGetStructContainingBoundingBlocks() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBoundingBlock thisBBBlock = factory.createAdp(thisBBRes, IBoundingBlock.class);
		Collection<IBoundingBlock> setContainingBBBlocks=thisBBBlock.getContainingBoundingBlocks();
		Assert.assertEquals(setContainingBBBlocks.size(), 1);
		Iterator<IBoundingBlock> iter = setContainingBBBlocks.iterator();
		IBoundingBlock blocktmp = iter.next();
		Assert.assertTrue( containingBBRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource()) );
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.adp.instadp.rdfimpl.BoundingBlockImpl#allFunctionsAreImplemented()}.
	 */
	@Test
	public void testAllFunctionsAreImplemented() {
		Assert.assertTrue(inj.getInstance(BoundingBlockImpl.class).allFunctionsAreImplemented());
	}

}
