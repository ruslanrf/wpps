/**
 * 
 */
package tuwien.dbai.wpps.objident;

import tuwien.dbai.wpps.objident.annot.ObjIdentEventBusAnnot;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Jun 1, 2012 5:25:05 PM
 */
public class SessionModule extends AbstractModule {

	final private EventBus eventBus;
	
	public SessionModule(EventBus eventBus) {
		super();
		this.eventBus = eventBus;
	}
	
	@Override
	protected void configure() {
		bind(EventBus.class).annotatedWith(ObjIdentEventBusAnnot.class).toInstance(eventBus);
	}

}
