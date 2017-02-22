/**
 * 
 */
package tuwien.dbai.wpps.core.junit.test;

import org.junit.Assert;
import org.junit.Test;

import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.module.OntologyModule;
import tuwien.dbai.wpps.core.module.WPPSConfigModule;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;

import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 26, 2011 6:33:43 PM
 */
public class TestOntologyModule {

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.module.OntologyModule#configure()}.
	 */
	@Test
	public void testConfigure() {
		System.out.println( "=== testConfigure() ===");
		Stopwatch stopwatch = new Stopwatch().start();
		   
		Injector inj = Guice.createInjector(new OntologyModule(), new WPPSConfigModule());
		
		long millis = stopwatch.elapsedMillis();
		System.out.println("time elapsed:"+stopwatch);
		
		stopwatch.reset();stopwatch.start();
		
		WPOntSubModels wpOntModel = inj.getInstance(WPOntSubModels.class);
		Assert.assertNotNull(wpOntModel);
		
		millis = stopwatch.elapsedMillis();
		System.out.println("time elapsed:"+stopwatch);
		
		stopwatch.reset();stopwatch.start();
		
		if (wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL) != null) {
			System.out.println(wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL)
					.getNameSpace());
			System.out.println(wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL)
					.getRdfModel().hashCode());
		}
		
		stopwatch.reset();stopwatch.start();
		
		wpOntModel = inj.getInstance(WPOntSubModels.class);
		Assert.assertNotNull(wpOntModel);
		
		millis = stopwatch.elapsedMillis();
		System.out.println("time elapsed:"+stopwatch);
		
		stopwatch.reset();stopwatch.start();
		
		if (wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL) != null) {
			System.out.println(wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL)
					.getNameSpace());
			System.out.println(wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL)
					.getRdfModel().hashCode());
		}

		stopwatch.stop();
	    millis = stopwatch.elapsedMillis();
	    System.out.println("time elapsed:"+stopwatch);
	}

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.module.OntologyModule#provideBlockGeomObjectStructModel(tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels)}.
	 */
	@Test
	public void testProvideBlockGeomObjectStructModel() {
		System.out.println( "=== testProvideBlockGeomObjectStructModel() ===");
		
		Stopwatch stopwatch = new Stopwatch().start();
		   
		Injector inj = Guice.createInjector(new OntologyModule(), new WPPSConfigModule());
		
		long millis = stopwatch.elapsedMillis();
		System.out.println("time elapsed:"+stopwatch);
		
		stopwatch.reset();stopwatch.start();

		A a = inj.getInstance(A.class);
		Assert.assertNotNull(a);
		
		millis = stopwatch.elapsedMillis();
		System.out.println("time elapsed:"+stopwatch);
		
		stopwatch.reset();stopwatch.start();
		
		System.out.println(a.model.hashCode());

		stopwatch.reset();stopwatch.start();

		a = inj.getInstance(A.class);
		Assert.assertNotNull(a);
		
		millis = stopwatch.elapsedMillis();
		System.out.println("time elapsed:"+stopwatch);
		
		stopwatch.reset();stopwatch.start();
		
		System.out.println(a.model.hashCode());
		
		stopwatch.stop();
	    millis = stopwatch.elapsedMillis();
	    System.out.println("time elapsed:"+stopwatch);
	    
	}
	
	private static class A {
		public final Model model;
		@Inject
		public A(@AnnotStructBlockGeomModel Model model) {
			this.model = model;
		}
	}

}
