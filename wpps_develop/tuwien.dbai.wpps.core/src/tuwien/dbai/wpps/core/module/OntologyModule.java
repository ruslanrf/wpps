/**
 * 
 */
package tuwien.dbai.wpps.core.module;

import tuwien.dbai.wpps.core.annotation.AnnotDOM;
import tuwien.dbai.wpps.core.annotation.AnnotInterfaceModel;
import tuwien.dbai.wpps.core.annotation.AnnotLogicalModel;
import tuwien.dbai.wpps.core.annotation.AnnotQltBlockModel;
import tuwien.dbai.wpps.core.annotation.AnnotQntBlockModel;
import tuwien.dbai.wpps.core.annotation.AnnotQntVisualModel;
import tuwien.dbai.wpps.core.annotation.AnnotStructBlockGeomModel;
import tuwien.dbai.wpps.core.annotation.AnnotVisualModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.EWPOntSubModel;
import tuwien.dbai.wpps.core.wpmodel.ontology.OntModelAdp;
import tuwien.dbai.wpps.core.wpmodel.ontology.WPOntSubModels;
import tuwien.dbai.wpps.core.wpmodel.ontology.factory.AWPModelFiller;
import tuwien.dbai.wpps.core.wpmodel.ontology.factory.EmptyWPOntModelProvider;
import tuwien.dbai.wpps.core.wpmodel.ontology.factory.WPModelFillerProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.hp.hpl.jena.rdf.model.Model;

/**
 * Depends on {@link WPPSConfigModule}.
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 24, 2011 1:51:30 AM
 */
public class OntologyModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(WPOntSubModels.class).toProvider(EmptyWPOntModelProvider.class).in(Singleton.class);
		bind(AWPModelFiller.class).toProvider(WPModelFillerProvider.class).in(Singleton.class);
	}
	
	@Provides @AnnotStructBlockGeomModel @Singleton
	OntModelAdp provideStructBlockGeomModelAdp(WPOntSubModels wpOntModel) {
		return wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
	}
	
//	@Provides @AnnotStructBlockGeomModel @Singleton
//	Model provideStructBlockGeomModel(WPOntSubModels wpOntModel) {
//		final OntModelAdp ontAdp = wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_BLOCK_GEOM_MODEL);
//		if (ontAdp == null)
//			return null;
//		else {
//			return ontAdp.getTopRdfModel();
//		}
//	}
	
	@Provides @AnnotQntBlockModel @Singleton
	OntModelAdp provideQntBlockModelAdp(WPOntSubModels wpOntModel) {
		return wpOntModel.getOntAdapter(EWPOntSubModel.QNT_BLOCK_MODEL);
	}
	
//	@Provides @AnnotQntBlockModel @Singleton
//	Model provideQntBlockModel(WPOntSubModels wpOntModel) {
//		final OntModelAdp ontAdp = wpOntModel.getOntAdapter(EWPOntSubModel.QNT_BLOCK_MODEL);
//		if (ontAdp == null)
//			return null;
//		else {
//			return ontAdp.getTopRdfModel();
//		}
//	}
	
	@Provides @AnnotQltBlockModel @Singleton
	OntModelAdp provideQltBlockModelAdp(WPOntSubModels wpOntModel) {
		return  wpOntModel.getOntAdapter(EWPOntSubModel.QLT_BLOCK_MODEL);
	}
	
//	@Provides @AnnotQltBlockModel @Singleton
//	Model provideQltBlockModel(WPOntSubModels wpOntModel) {
//		final OntModelAdp ontAdp = wpOntModel.getOntAdapter(EWPOntSubModel.QLT_BLOCK_MODEL);
//		if (ontAdp == null)
//			return null;
//		else {
//			return ontAdp.getTopRdfModel();
//		}
//	}
	
	@Provides @AnnotInterfaceModel @Singleton
	OntModelAdp provideInterfaceModelAdp(WPOntSubModels wpOntModel) {
		return wpOntModel.getOntAdapter(EWPOntSubModel.INTERFACE_MODEL);
	}
	
//	@Provides @AnnotInterfaceModel @Singleton
//	Model provideInterfaceModel(WPOntSubModels wpOntModel) {
//		final OntModelAdp ontAdp = wpOntModel.getOntAdapter(EWPOntSubModel.INTERFACE_MODEL);
//		if (ontAdp == null)
//			return null;
//		else {
//			return ontAdp.getTopRdfModel();
//		}
//	}
	
	@Provides @AnnotDOM @Singleton
	OntModelAdp provideDOMAdp(WPOntSubModels wpOntModel) {
		return wpOntModel.getOntAdapter(EWPOntSubModel.DOM);
	}
	
//	@Provides @AnnotDOM @Singleton
//	Model provideDOM(WPOntSubModels wpOntModel) {
//		final OntModelAdp ontAdp = wpOntModel.getOntAdapter(EWPOntSubModel.DOM);
//		if (ontAdp == null)
//			return null;
//		else {
//			return ontAdp.getTopRdfModel();
//		}
//	}
	
	@Provides @AnnotVisualModel @Singleton
	OntModelAdp provideStructVisualModelAdp(WPOntSubModels wpOntModel) {
		return wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_VISUAL_MODEL);
	}
	
//	@Provides @AnnotVisualModel @Singleton
//	Model provideStructVisualModel(WPOntSubModels wpOntModel) {
//		final OntModelAdp ontAdp = wpOntModel.getOntAdapter(EWPOntSubModel.STRUCT_VISUAL_MODEL);
//		if (ontAdp == null)
//			return null;
//		else {
//			return ontAdp.getTopRdfModel();
//		}
//	}
	
	@Provides @AnnotQntVisualModel @Singleton
	OntModelAdp provideQntVisualModelAdp(WPOntSubModels wpOntModel) {
		return wpOntModel.getOntAdapter(EWPOntSubModel.QNT_VISUAL_MODEL);
	}
	
//	@Provides @AnnotQntVisualModel @Singleton
//	Model provideQntVisualModel(WPOntSubModels wpOntModel) {
//		final OntModelAdp ontAdp = wpOntModel.getOntAdapter(EWPOntSubModel.QNT_VISUAL_MODEL);
//		if (ontAdp == null)
//			return null;
//		else {
//			return ontAdp.getTopRdfModel();
//		}
//	}
	
	@Provides @AnnotLogicalModel @Singleton
	OntModelAdp provideStructLogicalModelAdp(WPOntSubModels wpOntModel) {
		return wpOntModel.getOntAdapter(EWPOntSubModel.LOGICAL_MODEL);
	}
	
//	@Provides @AnnotLogicalModel @Singleton
//	Model provideStructLogicalModel(WPOntSubModels wpOntModel) {
//		final OntModelAdp ontAdp = wpOntModel.getOntAdapter(EWPOntSubModel.LOGICAL_MODEL);
//		if (ontAdp == null)
//			return null;
//		else {
//			return ontAdp.getTopRdfModel();
//		}
//	}
	
}
