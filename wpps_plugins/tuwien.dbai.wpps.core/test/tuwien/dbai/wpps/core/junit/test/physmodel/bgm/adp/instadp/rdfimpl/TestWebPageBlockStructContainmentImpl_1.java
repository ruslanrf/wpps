/**
 * 
 */
package tuwien.dbai.wpps.core.junit.test.physmodel.bgm.adp.instadp.rdfimpl;

import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.module.InstanceAdaptersModule;
import tuwien.dbai.wpps.core.module.OntologyModule;
import tuwien.dbai.wpps.core.module.WPPSConfigModule;
import tuwien.dbai.wpps.core.wpmodel.instadp.IRdfResourceAdp;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebDocumentBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebDocumentBlockImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 27, 2011 7:25:23 PM
 */
public class TestWebPageBlockStructContainmentImpl_1 {

	Stopwatch stopwatch = new Stopwatch();
	
	Injector inj = null;
	
	Resource thisWebpageRes= null;
	Resource containedBoxRes1 = null;
	Resource containedBoxRes2 = null;
	Resource containedTopWRes = null;
	
	Resource additionalBoxRes = null;
	Resource additionalTopWindowRes = null;
	
	WebDocumentBlockImpl webpageImpl = null;
	
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
		
		WPPSConfig conf = inj.getInstance(WPPSConfig.class);
		
		WPOntSubModels wpOntModel = inj.getInstance(WPOntSubModels.class);
		OntModelAdp bbgmAdp = wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
		Assert.assertNotNull(bbgmAdp);
		
		Model bbgmOnt = bbgmAdp.getRdfModel();
		thisWebpageRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"thisWebpageRes", StructBlockGeomModelOnt.Document);
		containedBoxRes1 = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containedBoxRes1", StructBlockGeomModelOnt.Box);
		containedBoxRes2 = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containedBoxRes2", StructBlockGeomModelOnt.Box);
		containedTopWRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"containedTopWRes", StructBlockGeomModelOnt.Page);
		additionalBoxRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"additionalBoxRes", StructBlockGeomModelOnt.Box);
		additionalTopWindowRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"additionalTopWindowRes", StructBlockGeomModelOnt.Page);
		
		webpageImpl = inj.getInstance(WebDocumentBlockImpl.class);
		webpageImpl.addTopPage(thisWebpageRes, containedTopWRes);
//		webpageImpl.addContainedBox(thisWebpageRes, containedBoxRes1);
//		webpageImpl.addContainedBox(thisWebpageRes, containedBoxRes2);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebDocumentBlockImpl#containsPage(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testStructContainsBlock() {
		System.out.println( "=== testStructContainsBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IWebDocumentBlock thisWebpage = factory.createAdp(thisWebpageRes, IWebDocumentBlock.class);
		Assert.assertTrue(thisWebpage.containsBlock(factory.createAdp(containedBoxRes1, IBox.class)));
		Assert.assertTrue(thisWebpage.containsBlock(factory.createAdp(containedBoxRes2, IBox.class)));
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebDocumentBlockImpl#getcontainedTopPage(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetStructContainedBlocks() {
		System.out.println( "=== testGetStructContainedBlocks() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IWebDocumentBlock thisWebpage = factory.createAdp(thisWebpageRes, IWebDocumentBlock.class);
		Collection<IBlock> setContainedBlocks=thisWebpage.getContainedBlocks();
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
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebDocumentBlockImpl#addContainedBlock(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource)}.
	 */
	@Test
	public void testAddStructContainedBlock() {
//		System.out.println( "=== testAddStructContainedBlock() ===");
//		
//		stopwatch.reset();stopwatch.start();
//		
//		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
//		IWebPageBlock thisWebpage = factory.createAdp(thisWebpageRes, IWebPageBlock.class);
//		
//		IBox newBox = factory.createAdp(additionalBoxRes, IBox.class);
//		newBox = factory.createAdp(additionalBoxRes, IBox.class);
//		newBox = factory.createAdp(additionalBoxRes, IBox.class);
//		
//		WebPageBlockStructContainmentImpl impl = inj.getInstance(WebPageBlockStructContainmentImpl.class);
//		WPOntModel wpOntModel = inj.getInstance(WPOntModel.class);
//		OntModelAdp bbgmAdp = wpOntModel.getOntAdapter(EWPOntModel.BLOCK_GEOM_OBJECT_STRUCT_MODEL);
//		impl.addStructContainedBlock(thisWebpageRes, additionalBoxRes, bbgmAdp.getRdfModel());
//		
//		Assert.assertTrue(thisWebpage.structContainsBlock(newBox));
//		
//		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebDocumentBlockImpl#getTopPage(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetTopWindowBlock() {
		System.out.println( "=== testGetTopWindowBlock() ===");
		
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IWebDocumentBlock thisWebpage = factory.createAdp(thisWebpageRes, IWebDocumentBlock.class);
		IWebPageBlock containedTopW=thisWebpage.getTopWebPage();
		Assert.assertNotNull(containedTopW);
		
		Assert.assertTrue(containedTopWRes.equals(((IRdfResourceAdp)containedTopW).getRdfResource()));
		
		System.out.println("time elapsed:"+stopwatch);
	}
	
	/**
	 * Risky code! Will not insome cases!
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebDocumentBlockImpl#addTopPage(Resource, Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testAddTopWindowBlock() {
//		System.out.println( "=== testAddTopWindowBlock() ===");
//		
//		stopwatch.reset();stopwatch.start();
//		
//		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
//		IWebPageBlock thisWebpage = factory.createAdp(thisWebpageRes, IWebPageBlock.class);
//		
//		IWindowBlock newWindow = factory.createAdp(additionalTopWindowRes, IWindowBlock.class);
//		
//		WebPageBlockStructContainmentImpl impl = inj.getInstance(WebPageBlockStructContainmentImpl.class);
//		WPOntModel wpOntModel = inj.getInstance(WPOntModel.class);
//		OntModelAdp bbgmAdp = wpOntModel.getOntAdapter(EWPOntModel.BLOCK_GEOM_OBJECT_STRUCT_MODEL);
//		impl.addTopWindowBlock(thisWebpageRes, additionalTopWindowRes, bbgmAdp.getRdfModel());
//		
////		Assert.assertTrue(thisWebpage.structContainsBlock(newWindow));
//		
//		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.WebDocumentBlockImpl#allFunctionsAreImplemented()}.
	 */
	@Test
	public void testAllFunctionsAreImplemented() {
		Assert.assertTrue(inj.getInstance(WebDocumentBlockImpl.class).allFunctionsAreImplemented());
	}

}
