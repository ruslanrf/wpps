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
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.BoundingBlockImpl;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 27, 2011 5:37:10 PM
 */
public class TestWindowBlockStructContainmentImpl_1 {

	Stopwatch stopwatch = new Stopwatch();
	
	Injector inj = null;
	
	Resource thisWindowRes = null;
	Resource parentWindowRes = null;
	Resource childWindowRes1 = null;
	Resource childWindowRes2 = null;
	Resource containingBBRes1 = null;
	Resource containingBBRes2 = null;
	Resource containedBoxRes1 = null;
	Resource containedBoxRes2 = null;
	Resource viewportRes = null;
	
	Resource additionalBoxRes = null;
	Resource additionalChildWindowRes = null;
	Resource additionalViewportRes = null;
	
	WebPageBlockImpl windowImpl = null;
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
		thisWindowRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"thisWindowRes", StructBlockGeomModelOnt.Page);
		parentWindowRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"parentWindowRes", StructBlockGeomModelOnt.Page);
		childWindowRes1 = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"childWindowRes1", StructBlockGeomModelOnt.Page);
		childWindowRes2 = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"childWindowRes2", StructBlockGeomModelOnt.Page);
		containingBBRes1 = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containingBBRes1", StructBlockGeomModelOnt.BoundingBlock);
		containingBBRes2 = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containingBBRes2", StructBlockGeomModelOnt.BoundingBlock);
		containedBoxRes1 = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containedBoxRes1", StructBlockGeomModelOnt.Box);
		containedBoxRes2 = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containedBoxRes2", StructBlockGeomModelOnt.Box);
		additionalBoxRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"additionalBoxRes", StructBlockGeomModelOnt.Box);
		additionalChildWindowRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"additionalChildWindowRes", StructBlockGeomModelOnt.Page);
		viewportRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"viewportRes", StructBlockGeomModelOnt.ViewPort);
		additionalViewportRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"additionalViewportRes", StructBlockGeomModelOnt.ViewPort);
		
		windowImpl = inj.getInstance(WebPageBlockImpl.class);
		windowImpl.addChildPage(parentWindowRes, thisWindowRes);
		windowImpl.addChildPage(thisWindowRes, childWindowRes1);
		windowImpl.addChildPage(thisWindowRes, childWindowRes2);
		windowImpl.addViewPort(thisWindowRes, viewportRes);
		
		bbImpl = inj.getInstance(BoundingBlockImpl.class);
		bbImpl.addContainedBlock(containingBBRes1, thisWindowRes);
		bbImpl.addContainedBlock(containingBBRes2, thisWindowRes);
		
		windowImpl.addContainedBox(thisWindowRes, containedBoxRes1);
		windowImpl.addContainedBox(thisWindowRes, containedBoxRes2);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl#containsPage(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testStructContainsBlock() {
		System.out.println( "=== testStructContainsBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IWebPageBlock thisWindow = factory.createAdp(thisWindowRes, IWebPageBlock.class);
		Assert.assertTrue(thisWindow.containsBlock(factory.createAdp(containedBoxRes1, IBox.class)));
		Assert.assertTrue(thisWindow.containsBlock(factory.createAdp(containedBoxRes2, IBox.class)));
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl#getcontainedTopPage(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetStructContainedBlocks() {
		System.out.println( "=== testGetStructContainedBlocks() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IWebPageBlock thisWindow = factory.createAdp(thisWindowRes, IWebPageBlock.class);
		Collection<IBlock> setContainedBlocks=thisWindow.getContainedBlocks();
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
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl#addContainedBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource)}.
	 */
	@Test
	public void testAddStructContainedBlock() {
//		System.out.println( "=== testAddStructContainedBlock() ===");
//		
//		stopwatch.reset();stopwatch.start();
//		
//		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
//		IWindowBlock thisWindow = factory.createAdp(thisWindowRes, IWindowBlock.class);
//		
//		IBox newBox = factory.createAdp(additionalBoxRes, IBox.class);
//		newBox = factory.createAdp(additionalBoxRes, IBox.class);
//		
//		Assert.assertFalse(thisWindow.structContainsBlock(newBox));
//		
//		WindowBlockStructContainmentImpl impl = inj.getInstance(WindowBlockStructContainmentImpl.class);
//		WPOntModel wpOntModel = inj.getInstance(WPOntModel.class);
//		OntModelAdp bbgmAdp = wpOntModel.getOntAdapter(EWPOntModel.BLOCK_GEOM_OBJECT_STRUCT_MODEL);
//		impl.addStructContainedBlock(thisWindowRes, additionalBoxRes, bbgmAdp.getRdfModel());
//		
//		Assert.assertTrue(thisWindow.structContainsBlock(newBox));
//		
//		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl#getContainingBoundingBlocks(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetStructContainingBoundingBlocks() {
		System.out.println( "=== testGetStructContainingBoundingBlocks() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IWebPageBlock thisWindow = factory.createAdp(thisWindowRes, IWebPageBlock.class);
		
		Collection<IBoundingBlock> setContainingBBBlocks=thisWindow.getContainingBoundingBlocks();
		
		Assert.assertEquals(setContainingBBBlocks.size(), 2);
		Iterator<IBoundingBlock> iter = setContainingBBBlocks.iterator();
		IBoundingBlock blocktmp = iter.next();
		Assert.assertTrue(containingBBRes1.equals(((IRdfResourceAdp)blocktmp).getRdfResource())
				|| containingBBRes2.equals(((IRdfResourceAdp)blocktmp).getRdfResource()));
		blocktmp = iter.next();
		Assert.assertTrue(containingBBRes1.equals(((IRdfResourceAdp)blocktmp).getRdfResource())
				|| containingBBRes2.equals(((IRdfResourceAdp)blocktmp).getRdfResource()));
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl#getParentWindowBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetParentWindowBlock() {
		System.out.println( "=== testGetParentWindowBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IWebPageBlock thisWindow = factory.createAdp(thisWindowRes, IWebPageBlock.class);
		IWebPageBlock parentWindow =thisWindow.getParentWebPageBlock();
		Assert.assertNotNull(parentWindow);
		
		Assert.assertTrue(parentWindowRes.equals(((IRdfResourceAdp)parentWindow).getRdfResource()));
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl#getChildWindowBlocks(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetChildWindowBlocks() {
		System.out.println( "=== testGetChildWindowBlocks() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IWebPageBlock thisWindow = factory.createAdp(thisWindowRes, IWebPageBlock.class);
		
		Collection<IWebPageBlock> setChildWindows=thisWindow.getChildWebPageBlocks();
		
		Assert.assertEquals(setChildWindows.size(), 2);
		Iterator<IWebPageBlock> iter = setChildWindows.iterator();
		IWebPageBlock blocktmp = iter.next();
		Assert.assertTrue(childWindowRes1.equals(((IRdfResourceAdp)blocktmp).getRdfResource())
				|| childWindowRes2.equals(((IRdfResourceAdp)blocktmp).getRdfResource()));
		blocktmp = iter.next();
		Assert.assertTrue(childWindowRes1.equals(((IRdfResourceAdp)blocktmp).getRdfResource())
				|| childWindowRes2.equals(((IRdfResourceAdp)blocktmp).getRdfResource()));
		
		System.out.println("time elapsed:"+stopwatch);
	}
	
	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl#addChildWindowBlock(Resource, Resource, Model)}.
	 */
	@Test
	public void testAddChildWindowBlock() {
//		System.out.println( "=== testAddChildWindowBlock() ===");
//		
//		stopwatch.reset();stopwatch.start();
//		
//		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
//		IWindowBlock thisWindow = factory.createAdp(thisWindowRes, IWindowBlock.class);
//		
//		IWindowBlock newBox = factory.createAdp(additionalChildWindowRes, IWindowBlock.class);
//		newBox = factory.createAdp(additionalChildWindowRes, IWindowBlock.class);
//		
//		Assert.assertFalse(thisWindow.structContainsBlock(newBox));
//		
//		WindowBlockStructContainmentImpl impl = inj.getInstance(WindowBlockStructContainmentImpl.class);
//		WPOntModel wpOntModel = inj.getInstance(WPOntModel.class);
//		OntModelAdp bbgmAdp = wpOntModel.getOntAdapter(EWPOntModel.BLOCK_GEOM_OBJECT_STRUCT_MODEL);
//		impl.addChildWindowBlock(thisWindowRes, additionalChildWindowRes, bbgmAdp.getRdfModel());
//		
//		Assert.assertTrue(thisWindow.getChildWindowBlocks().contains(newBox));
//		
//		System.out.println("time elapsed:"+stopwatch);
	}
	
	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl#getViewPort(Resource, Model)}.
	 */
	@Test
	public void testGetViewPort() {
		System.out.println( "=== testGetViewPort() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IWebPageBlock thisWindow = factory.createAdp(thisWindowRes, IWebPageBlock.class);
		
		IViewPortBlock vp=thisWindow.getViewPort();
		
		Assert.assertNotNull(vp);
		Assert.assertTrue(viewportRes.equals(((IRdfResourceAdp)vp).getRdfResource()));
		
		System.out.println("time elapsed:"+stopwatch);
	}
	
	/**
	 * Risky code! Will not insome cases!
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl#addViewPort(Resource, Resource, Model)}.
	 */
	@Test
	public void testAddViewPort() {
//		System.out.println( "=== testAddChildWindowBlock() ===");
//		
//		stopwatch.reset();stopwatch.start();
//		
//		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
//		IWindowBlock thisWindow = factory.createAdp(thisWindowRes, IWindowBlock.class);
//		
//		IViewPortBlock newBox = factory.createAdp(additionalViewportRes, IViewPortBlock.class);
//		newBox = factory.createAdp(additionalViewportRes, IViewPortBlock.class);
//		
//		Assert.assertFalse(thisWindow.structContainsBlock(newBox));
//		
//		WindowBlockStructContainmentImpl impl = inj.getInstance(WindowBlockStructContainmentImpl.class);
//		WPOntModel wpOntModel = inj.getInstance(WPOntModel.class);
//		OntModelAdp bbgmAdp = wpOntModel.getOntAdapter(EWPOntModel.BLOCK_GEOM_OBJECT_STRUCT_MODEL);
//		impl.addViewPort(thisWindowRes, additionalViewportRes, bbgmAdp.getRdfModel());
//		
////		Assert.assertTrue(thisWindow.getViewPort().equals(newBox));
//		
//		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebPageBlockImpl#allFunctionsAreImplemented()}.
	 */
	@Test
	public void testAllFunctionsAreImplemented() {
		System.out.println( "=== testAllFunctionsAreImplemented() ===");
		
		stopwatch.reset();stopwatch.start();
		Assert.assertTrue(inj.getInstance(WebPageBlockImpl.class).allFunctionsAreImplemented());
		
		System.out.println("time elapsed:"+stopwatch);
	}

}
