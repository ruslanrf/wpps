/**
 * 
 */
package tuwien.dbai.wpps.core.junit.test.physmodel.bgm.adp.instadp.rdfimpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tuwien.dbai.wpps.core.module.InstanceAdaptersModule;
import tuwien.dbai.wpps.core.module.OntologyModule;
import tuwien.dbai.wpps.core.module.WPPSConfigModule;
import tuwien.dbai.wpps.core.wpmodel.instadp.factory.RdfInstAdpFactoryWrap;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IQntBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IWebPageBlock;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl;
import tuwien.dbai.wpps.ontschema.StructBlockGeomModelOnt;

import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Resource;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Dec 7, 2011 11:19:38 PM
 */
public class TestQntBlockImpl {

	Stopwatch stopwatch = new Stopwatch();
	
	Injector inj = null;
	
	Resource boxRes1 = null;
	Resource boxRes2 = null;
	Resource windowBlockRes = null;
	
	QntBlockImpl qntImpl = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println( "=== setUp() ===");
		stopwatch.start();
		
		inj = Guice.createInjector(new InstanceAdaptersModule(), new OntologyModule(), new WPPSConfigModule());
		
		WPOntSubModels wpOntModel = inj.getInstance(WPOntSubModels.class);
		OntModelAdp bbgmAdp = wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
		Assert.assertNotNull(bbgmAdp);
		
		OntModelAdp qntbmAdp = wpOntModel.getOntAdapter(EWPOntSubModel.QNT_BLOCK_MODEL);
		Assert.assertNotNull(qntbmAdp);
		
		Model bbgmOnt = bbgmAdp.getRdfModel();
		boxRes1 = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"boxRes1", StructBlockGeomModelOnt.Box);
		boxRes2= bbgmOnt.createResource(bbgmAdp.getNameSpace()+"boxRes2", StructBlockGeomModelOnt.Box);
		windowBlockRes = bbgmOnt.createResource(bbgmAdp.getNameSpace()+"windowBlockRes", StructBlockGeomModelOnt.Page);
		
		qntImpl = inj.getInstance(QntBlockImpl.class);
		
		qntImpl.addXMin(boxRes1, 20);
		qntImpl.addYMin(boxRes1, 30);
		qntImpl.addXMax(boxRes1, 40);
		qntImpl.addYMax(boxRes1, 50);
		
		qntImpl.addXMin(boxRes2, 60);
		qntImpl.addYMin(boxRes2, 70);
		qntImpl.addXMax(boxRes2, 80);
		qntImpl.addYMax(boxRes2, 90);
		
		qntImpl.addXMin(windowBlockRes, 20);
		qntImpl.addYMin(windowBlockRes, 30);
		qntImpl.addXMax(windowBlockRes, 80);
		qntImpl.addYMax(windowBlockRes, 90);
		
		System.out.println("time elapsed:"+stopwatch);
	}


	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl#getXMin(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetXMin() {
		System.out.println( "=== testGetXMin() ===");
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox box1 = factory.createAdp(boxRes1, IBox.class);
		System.out.println(box1.as(IQntBlock.class).getXMin());
		Assert.assertEquals(20, box1.as(IQntBlock.class).getXMin(), 0);

		IQntBlock qntBox2 = factory.createAdp(boxRes2, IQntBlock.class);
		Assert.assertEquals(60, qntBox2.getXMin(), 0);
		
		IWebPageBlock win = factory.createAdp(windowBlockRes, IWebPageBlock.class);
		Assert.assertEquals(20, win.as(IQntBlock.class).getXMin(), 0);
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl#getYMin(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetYMin() {
		System.out.println( "=== testGetYMin() ===");
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox box1 = factory.createAdp(boxRes1, IBox.class);
		Assert.assertEquals(30, box1.as(IQntBlock.class).getYMin(), 0);

		IQntBlock qntBox2 = factory.createAdp(boxRes2, IQntBlock.class);
		Assert.assertEquals(70, qntBox2.getYMin(), 0);
		
		IWebPageBlock win = factory.createAdp(windowBlockRes, IWebPageBlock.class);
		Assert.assertEquals(30, win.as(IQntBlock.class).getYMin(), 0);
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl#getXMax(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetXMax() {
		System.out.println( "=== testGetXMax() ===");
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox box1 = factory.createAdp(boxRes1, IBox.class);
		Assert.assertEquals(40, box1.as(IQntBlock.class).getXMax(), 0);

		IQntBlock qntBox2 = factory.createAdp(boxRes2, IQntBlock.class);
		Assert.assertEquals(80, qntBox2.getXMax(), 0);
		
		IWebPageBlock win = factory.createAdp(windowBlockRes, IWebPageBlock.class);
		Assert.assertEquals(80, win.as(IQntBlock.class).getXMax(), 0);
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl#getYMax(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetYMax() {
		System.out.println( "=== testGetYMax() ===");
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		IBox box1 = factory.createAdp(boxRes1, IBox.class);
		Assert.assertEquals(50, box1.as(IQntBlock.class).getYMax(), 0);

		IQntBlock qntBox2 = factory.createAdp(boxRes2, IQntBlock.class);
		Assert.assertEquals(90, qntBox2.getYMax(), 0);
		
		IWebPageBlock win = factory.createAdp(windowBlockRes, IWebPageBlock.class);
		Assert.assertEquals(90, win.as(IQntBlock.class).getYMax(), 0);
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl#getWidth(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetWidth() {
		System.out.println( "=== testGetWidth() ===");
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		
		IQntBlock qntBox1 = factory.createAdp(boxRes1, IQntBlock.class);
		Assert.assertEquals(20, qntBox1.getWidth(), 0);

		IQntBlock qntBox2 = factory.createAdp(boxRes2, IQntBlock.class);
		Assert.assertEquals(20, qntBox2.getWidth(), 0);
		
		IWebPageBlock win = factory.createAdp(windowBlockRes, IWebPageBlock.class);
		Assert.assertEquals(60, win.as(IQntBlock.class).getWidth(), 0);
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl#getHeight(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetHeight() {
		System.out.println( "=== testGetHeight() ===");
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		
		IQntBlock qntBox1 = factory.createAdp(boxRes1, IQntBlock.class);
		Assert.assertEquals(20, qntBox1.getHeight(), 0);

		IQntBlock qntBox2 = factory.createAdp(boxRes2, IQntBlock.class);
		Assert.assertEquals(20, qntBox2.getHeight(), 0);
		
		IWebPageBlock win = factory.createAdp(windowBlockRes, IWebPageBlock.class);
		Assert.assertEquals(60, win.as(IQntBlock.class).getHeight(), 0);
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl#getCentre(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetCentre() {
		System.out.println( "=== testGetCentre() ===");
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		
		IQntBlock qntBox1 = factory.createAdp(boxRes1, IQntBlock.class);
		Assert.assertEquals(30, qntBox1.getXCenter(), 0);
		Assert.assertEquals(40, qntBox1.getYCenter(), 0);

		IQntBlock qntBox2 = factory.createAdp(boxRes2, IQntBlock.class);
		Assert.assertEquals(70, qntBox2.getXCenter(), 0);
		Assert.assertEquals(80, qntBox2.getYCenter(), 0);
		
		IWebPageBlock win = factory.createAdp(windowBlockRes, IWebPageBlock.class);
		Assert.assertEquals(50, win.as(IQntBlock.class).getXCenter(), 0);
		Assert.assertEquals(60, win.as(IQntBlock.class).getYCenter(), 0);
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl#getDirection(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetDirection() {
		System.out.println( "=== testGetDirection() ===");
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		
		IQntBlock qntBox1 = factory.createAdp(boxRes1, IQntBlock.class);
		IQntBlock qntBox2 = factory.createAdp(boxRes2, IQntBlock.class);
		
		Assert.assertEquals(225
				, qntBox1.getRelationAsDouble(qntBox2, EBlockQntRelationType.QNT_DIRECTION)
				, 0.000000001);
		
		Assert.assertEquals(45
				, qntBox2.getRelationAsDouble(qntBox1, EBlockQntRelationType.QNT_DIRECTION)
				, 0.000000001);
		
		IQntBlock qntWin = factory.createAdp(windowBlockRes, IQntBlock.class);
		Assert.assertEquals(45
				, qntWin.getRelationAsDouble(qntBox1, EBlockQntRelationType.QNT_DIRECTION)
				, 0.000000001);

		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl#getDistance(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetDistance() {
		System.out.println( "=== testGetDistance() ===");
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		
		IQntBlock qntBox1 = factory.createAdp(boxRes1, IQntBlock.class);
		IQntBlock qntBox2 = factory.createAdp(boxRes2, IQntBlock.class);
		
		Assert.assertEquals(28.284271247
				, qntBox1.getRelationAsDouble(qntBox2, EBlockQntRelationType.QNT_DISTANCE)
				, 0.000000001);
		
		Assert.assertEquals(qntBox2.getRelationAsDouble(qntBox1, EBlockQntRelationType.QNT_DISTANCE)
				, qntBox1.getRelationAsDouble(qntBox2, EBlockQntRelationType.QNT_DISTANCE)
				, 0.000000001);
		
		
//		IQntBlock qntWin = factory.createAdp(windowBlockRes, IQntBlock.class);
//		Assert.assertEquals(45
//				, qntWin.getRelationAsDouble(qntBox1, EBlockQntRelationType.DIRECTION)
//				, 0.000000001);
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl#getBorderDistance(com.hp.hpl.jena.rdf.model.Resource, com.hp.hpl.jena.rdf.model.Resource, tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.EBlockQntRelationType, com.hp.hpl.jena.rdf.model.Model)}.
	 */
	@Test
	public void testGetBorderDistance() {
		System.out.println( "=== testGetDistance() ===");
		stopwatch.reset();stopwatch.start();
		
		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
		
		IQntBlock qntBox1 = factory.createAdp(boxRes1, IQntBlock.class);
		IQntBlock qntBox2 = factory.createAdp(boxRes2, IQntBlock.class);
		
		IQntBlock qntWin = factory.createAdp(windowBlockRes, IQntBlock.class);
		
		Assert.assertEquals(-40
				, qntBox1.getRelationAsDouble(qntBox2, EBlockQntRelationType.QNT_BORDER_DISTANCE_BB)
				, 0.000000001);
		
		Assert.assertEquals(40
				, qntBox2.getRelationAsDouble(qntBox1, EBlockQntRelationType.QNT_BORDER_DISTANCE_BB)
				, 0.000000001);
		
		Assert.assertEquals(0
				, qntBox1.getRelationAsDouble(qntBox1, EBlockQntRelationType.QNT_BORDER_DISTANCE_BB)
				, 0.000000001);
		
		Assert.assertEquals(-20
				, qntWin.getRelationAsDouble(qntBox1, EBlockQntRelationType.QNT_BORDER_DISTANCE_LR)
				, 0.000000001);
		
		System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.rdfimpl.QntBlockImpl#allFunctionsAreImplemented()}.
	 */
	@Test
	public void testAllFunctionsAreImplemented() {
System.out.println( "=== testAllFunctionsAreImplemented() ===");
		
		stopwatch.reset();stopwatch.start();
		Assert.assertTrue(inj.getInstance(QntBlockImpl.class).allFunctionsAreImplemented());
		
		System.out.println("time elapsed:"+stopwatch);
	}

}
