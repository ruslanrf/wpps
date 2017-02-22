/**
 * 
 */
package tuwien.dbai.wpps.core.junit.test;

import org.junit.Assert;
import org.junit.Test;

import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.module.WPPSConfigModule;

import com.google.common.base.Stopwatch;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 25, 2011 7:04:04 PM
 */
public class TestWPPSConfigModule {

	/**
	 * Test method for {@link tuwien.dbai.wpps.core.module.WPPSConfigModule#configure()}.
	 */
	@Test
	public void testConfigure() {
		
		Stopwatch stopwatch = new Stopwatch().start();
		   
		Injector inj = Guice.createInjector(new WPPSConfigModule());
		
		long millis = stopwatch.elapsedMillis();
		System.out.println("time elapsed:"+stopwatch);
		
		stopwatch.reset();stopwatch.start();
		
		WPPSConfig config = inj.getInstance(WPPSConfig.class);
		Assert.assertNotNull(config);
		
		millis = stopwatch.elapsedMillis();
		System.out.println("time elapsed:"+stopwatch);
		
		stopwatch.reset();stopwatch.start();
		
		System.out.println(config.getOntologyInstanceNSGenBase());
		System.out.println(config.getCreateOntology());
		System.out.println(config.getOntologyModels());
		System.out.println(config.getOntologyType());
		System.out.println(config.getOntologyReasonerType());
		System.out.println(config.getJenaRDFSReasoner());
		System.out.println(config.getJenaOWLReasoner());
		System.out.println(config.getLoadSchema());
		
		System.out.println(config.getAltSchemaUri());
		
		System.out.println(config.getCreateInOntology());
		System.out.println(config.getStructOneToManyRelationMap());
		

		stopwatch.stop();
	    millis = stopwatch.elapsedMillis();
	    System.out.println("time elapsed:"+stopwatch);
		   
	}

}
