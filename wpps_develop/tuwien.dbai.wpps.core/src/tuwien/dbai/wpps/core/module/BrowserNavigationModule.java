/**
 * 
 */
package tuwien.dbai.wpps.core.module;

import tuwien.dbai.wpps.core.nav.BrowserNavigatorProvider;
import tuwien.dbai.wpps.core.nav.IBrowserNavigator;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Feb 10, 2012 4:15:08 PM
 */
public class BrowserNavigationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IBrowserNavigator.class).toProvider(BrowserNavigatorProvider.class).in(Singleton.class);
	}

}
