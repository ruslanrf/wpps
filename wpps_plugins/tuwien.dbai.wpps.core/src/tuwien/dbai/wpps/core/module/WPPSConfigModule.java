/**
 * 
 */
package tuwien.dbai.wpps.core.module;

import java.io.File;

import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.annotation.AnnotWPPSConfig;
import tuwien.dbai.wpps.core.config.WPPSConfig;
import tuwien.dbai.wpps.core.config.WPPSConfigProvider;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 24, 2011 2:46:10 PM
 */
public class WPPSConfigModule extends AbstractModule {
	
	private final File wppsConfig;
	
	public WPPSConfigModule(File wppsConfig) {
		this.wppsConfig = wppsConfig;
	}

	@Override
	protected void configure() {
		bind(WPPSConfig.class).toProvider(WPPSConfigProvider.class).in(Singleton.class);
		bind(File.class).annotatedWith(AnnotWPPSConfig.class).toInstance(wppsConfig);
	}

}
