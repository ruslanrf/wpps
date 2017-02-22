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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBoxType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBoundingBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IInnerBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IOuterBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.ViewPortBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 27, 2011 11:44:05 PM
 */
public class TestBoxStructContainmentImpl_1 {

	Stopwatch stopwatch = new Stopwatch();
	
	Injector inj = null;
	
	Resource mainBoxRes = null;
	Resource innerBlockRes = null;
	Resource outerBlockRes = null;
	
	Resource boundingBlockRes = null;
	Resource windowBlockRes = null;
	Resource viewportBlockRes = null;
	
	Resource additionalInnerBlockRes = null;
	Resource additionalOuterBlockRes = null;
	
	BoxImpl boxImpl = null;
	WebPageBlockImpl windowImpl = null;
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
		mainBoxRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"mainBoxRes", StructBlockGeomModelOnt.Box);
		innerBlockRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"innerBlockRes", StructBlockGeomModelOnt.InnerBlock);
		outerBlockRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"outerBlockRes", StructBlockGeomModelOnt.OuterBlock);
		boundingBlockRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"boundingBlockRes", StructBlockGeomModelOnt.BoundingBlock);
		windowBlockRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"windowBlockRes", StructBlockGeomModelOnt.Page);
		viewportBlockRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"viewportBlockRes", StructBlockGeomModelOnt.ViewPort);
		additionalInnerBlockRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"additionalInnerBlockRes", StructBlockGeomModelOnt.InnerBlock);
		additionalOuterBlockRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"additionalOuterBlockRes", StructBlockGeomModelOnt.OuterBlock);
		
		boxImpl = inj.getInstance(BoxImpl.class);
		
		boxImpl.addBoxType(mainBoxRes, EBoxType.BLOCK_LEVEL_ELEMENT);
		
		boxImpl.addInnerBlock(mainBoxRes, innerBlockRes);
		boxImpl.addOuterBlock(mainBoxRes, outerBlockRes);
		
		windowImpl = inj.getInstance(WebPageBlockImpl.class);
		
		windowImpl.addContainedBox(windowBlockRes, mainBoxRes);
		
		viewportImpl = inj.getInstance(ViewPortBlockImpl.class);
//		viewportImpl.addContainedBox(viewportBlockRes, mainBoxRes);
		
		bbImpl = inj.getInstance(BoundingBlockImpl.class);
		
		bbImpl.addContainedBlock(boundingBlockRes, mainBoxRes);
		bbImpl.addContainedBlock(boundingBlockRes, innerBlockRes);
		bbImpl.addContainedBlock(boundingBlockRes, outerBlockRes);
		
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#structContainsBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testStructContainsBlock() {
		System.out.println( "=== testStructContainsBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox mainBox = factory.createAdp(mainBoxRes, IBox.class);
		
		Assert.assertTrue(mainBox.containsBlock(factory.createAdp(innerBlockRes, IBox.class)));
		Assert.assertTrue(mainBox.containsBlock(factory.createAdp(outerBlockRes, IBoundingBlock.class)));
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#getStructContainedBlocks(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetStructContainedBlocks() {
		System.out.println( "=== testGetStructContainedBlocks() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox mainBox = factory.createAdp(mainBoxRes, IBox.class);
		Collection<IBlock> setContainedBlocks=mainBox.getContainedBlocks();
		Assert.assertEquals(setContainedBlocks.size(), 2);
		Iterator<IBlock> iter = setContainedBlocks.iterator();
		IBlock blocktmp = iter.next();
		Assert.assertTrue(innerBlockRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource())
				|| outerBlockRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource()));
		blocktmp = iter.next();
		Assert.assertTrue(innerBlockRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource())
				|| outerBlockRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource()));
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#getContainingBoundingBlocks(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetStructContainingBoundingBlocks() {
		System.out.println( "=== testGetStructContainingBoundingBlocks() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox mainBox = factory.createAdp(mainBoxRes, IBox.class);
		
		Collection<IBoundingBlock> setContainingBBBlocks=mainBox.getContainingBoundingBlocks();
		Assert.assertEquals(setContainingBBBlocks.size(), 1);
		Iterator<IBoundingBlock> iter = setContainingBBBlocks.iterator();
		IBoundingBlock blocktmp = iter.next();
		Assert.assertTrue( boundingBlockRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource()) );
		
		IInnerBlock innerBlock = factory.createAdp(innerBlockRes, IInnerBlock.class);
		setContainingBBBlocks=innerBlock.getContainingBoundingBlocks();
		Assert.assertEquals(setContainingBBBlocks.size(), 1);
		iter = setContainingBBBlocks.iterator();
		blocktmp = iter.next();
		Assert.assertTrue( boundingBlockRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource()) );
		
		IOuterBlock outerBlock = factory.createAdp(outerBlockRes, IOuterBlock.class);
		setContainingBBBlocks=outerBlock.getContainingBoundingBlocks();
		Assert.assertEquals(setContainingBBBlocks.size(), 1);
		iter = setContainingBBBlocks.iterator();
		blocktmp = iter.next();
		Assert.assertTrue( boundingBlockRes.equals(((IRdfResourceAdp)blocktmp).getRdfResource()) );
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#getInnerBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetInnerBlock() {
		System.out.println( "=== testGetInnerBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox mainBox = factory.createAdp(mainBoxRes, IBox.class);
		IInnerBlock innerBlock=mainBox.getInnerBlock();
		Assert.assertTrue(innerBlockRes.equals(((IRdfResourceAdp)innerBlock).getRdfResource()) );
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Risky code! Will not insome cases!
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#addInnerBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource)}.
	 */
	@Test
	public void testAddInnerBlock() {
//System.out.println( "=== testAddInnerBlock() ===");
//		
//		stopwatch.reset();stopwatch.start();
//		
//		WPOntModel wpOntModel = inj.getInstance(WPOntModel.class);
//		OntModelAdp bbgmAdp = wpOntModel.getOntAdapter(EWPOntModel.BLOCK_GEOM_OBJECT_STRUCT_MODEL);
//		
//		BoxStructContainmentImpl impl = inj.getInstance(BoxStructContainmentImpl.class);
//		impl.addInnerBlock(mainBoxRes, additionalInnerBlockRes, bbgmAdp.getRdfModel());
//		
//		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
//		IBox mainBox = factory.createAdp(mainBoxRes, IBox.class);
//		Assert.assertTrue(mainBox.structContainsBlock(factory.createAdp(additionalInnerBlockRes, IInnerBlock.class)));
//		
//		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#getOuterBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetOuterBlock() {
		System.out.println( "=== testGetOuterBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox mainBox = factory.createAdp(mainBoxRes, IBox.class);
		IOuterBlock outerBlock=mainBox.getOuterBlock();
		Assert.assertTrue(outerBlockRes.equals(((IRdfResourceAdp)outerBlock).getRdfResource()) );
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Risky code! Will not insome cases!
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#addOuterBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource)}.
	 */
	@Test
	public void testAddOuterBlock() {
//System.out.println( "=== testAddOuterBlock() ===");
//		
//		stopwatch.reset();stopwatch.start();
//		
//		WPOntModel wpOntModel = inj.getInstance(WPOntModel.class);
//		OntModelAdp bbgmAdp = wpOntModel.getOntAdapter(EWPOntModel.BLOCK_GEOM_OBJECT_STRUCT_MODEL);
//		
//		BoxStructContainmentImpl impl = inj.getInstance(BoxStructContainmentImpl.class);
//		impl.addOuterBlock(mainBoxRes, additionalOuterBlockRes, bbgmAdp.getRdfModel());
//		
//		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
//		IBox mainBox = factory.createAdp(mainBoxRes, IBox.class);
//		Assert.assertTrue(mainBox.structContainsBlock(factory.createAdp(additionalOuterBlockRes, IInnerBlock.class)));
//		
//		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#getBoxForInnerBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetBoxForInnerBlock() {
		System.out.println( "=== testGetBoxForInnerBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IInnerBlock innerBlock = factory.createAdp(innerBlockRes, IInnerBlock.class);
		IBox mainBox = innerBlock.getBox();
		Assert.assertTrue(mainBoxRes.equals(((IRdfResourceAdp)mainBox).getRdfResource()) );
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#getBoxForOuterBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetBoxForOuterBlock() {
		System.out.println( "=== testGetBoxForOuterBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IOuterBlock outerBlock = factory.createAdp(outerBlockRes, IOuterBlock.class);
		IBox mainBox = outerBlock.getBox();
		Assert.assertTrue(mainBoxRes.equals(((IRdfResourceAdp)mainBox).getRdfResource()) );
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#getStructContainingWindowBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetStructContainingWindowBlock() {
		System.out.println( "=== testGetStructContainingWindowBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox mainBox = factory.createAdp(mainBoxRes, IBox.class);
		IWebPageBlock window = mainBox.getWebPage();
		Assert.assertTrue(windowBlockRes.equals(((IRdfResourceAdp)window).getRdfResource()) );
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#getStructContainingViewPortBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetStructContainingViewPortBlock() {
		System.out.println( "=== testGetStructContainingViewPortBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox mainBox = factory.createAdp(mainBoxRes, IBox.class);
//		IViewPortBlock viewPort = mainBox.getStructContainingViewPortBlock();
//		Assert.assertTrue(viewportBlockRes.equals(((IRdfResourceAdp)viewPort).getRdfResource()) );
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#getBoxType(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetBoxType() {
		System.out.println( "=== testGetBoxType() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox mainBox = factory.createAdp(mainBoxRes, IBox.class);
		
		Assert.assertEquals(mainBox.getBoxType(), EBoxType.BLOCK_LEVEL_ELEMENT);
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoxImpl#allFunctionsAreImplemented()}.
	 */
	@Test
	public void testAllFunctionsAreImplemented() {
		System.out.println( "=== testAllFunctionsAreImplemented() ===");
		
		stopwatch.reset();stopwatch.start();
		Assert.assertTrue(inj.getInstance(BoxImpl.class).allFunctionsAreImplemented());
		
		System.out.println("time elapsed:"+stopwatch);
	}

}
