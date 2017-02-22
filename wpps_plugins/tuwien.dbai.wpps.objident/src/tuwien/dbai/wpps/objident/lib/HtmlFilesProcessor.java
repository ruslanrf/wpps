/**
 * 
 */
package tuwien.dbai.wpps.objident.lib;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.swt.widgets.Display;

import tuwien.dbai.wpps.common.SimpleDirWalker;
import tuwien.dbai.wpps.common.callback.IProcedure;
import tuwien.dbai.wpps.embrowser.EMBrowserEditor;
import tuwien.dbai.wpps.embrowser.EMBrowserEvent;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Sep 25, 2012 7:21:36 PM
 */
public class HtmlFilesProcessor implements Runnable {
	private static final Logger log = Logger.getLogger(HtmlFilesProcessor.class);
	
	private final File mainDir;
	private final EMBrowserEditor browser;
	
	
	private boolean browserFree = true;
	private Object browserFreeLock = new Object();
	
	public HtmlFilesProcessor(File mainDir
			, EMBrowserEditor browser
//			, IFunction<File, Boolean> filter
//			, IProcedure<T>
			) {
		this.mainDir = mainDir;
		this.browser = browser;
		browser.getEventBus().register(HtmlFilesProcessor.this);
	}
	
	@Override
	public void run() {
		process(mainDir, browser);
	}
	
//	private static final int WAIT = 10;
	private final void process(final File mainDir
			,  final EMBrowserEditor browser) {
		final IProcedure<File> proc = new IProcedure<File>() {
			@Override public void apply(final File file) {
				if (file.isFile() && (file.getName().endsWith(".html") || file.getName().endsWith(".htm")) ) {
//System.out.println("1 "+Thread.currentThread().getId());
					synchronized (browserFreeLock) {
						while (!browserFree) {
							try {
								browserFreeLock.wait();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						
						// DO!!!
						
						browserFree = false;
					}
//System.out.println(file.getAbsolutePath());
					// --- run in GUI Thread ---
					Display display = Display.getDefault();
					if (display != null) {
						display.asyncExec(new Runnable() {
				            public void run() {
//System.out.println("4 "+Thread.currentThread().getId());
				            	browser.goToURL("file://"+file.getAbsolutePath());
//System.out.println("+4 "+Thread.currentThread().getId());
				            }
				          });
					}
				}
		} };
		
			final SimpleDirWalker dirWalker = new SimpleDirWalker(
					mainDir, proc);	
			dirWalker.walk();
			
	}
	
	@Subscribe
	public void _pageLoadedListener(EMBrowserEvent e) {
		// if this event from another web browser. Never shoud happens!
		Preconditions.checkArgument(browser.equals(e.getSource()));
		switch (e.getEventType()) {
		case LOADING_COMPLETE_2: {
//System.out.println("2 "+Thread.currentThread().getId());
if (log.isInfoEnabled()) log.info("Webpage has been loaded: "+(String)e.getDataArr()[0]);
			
			// 
			
			
			
			
			synchronized (browserFreeLock) {
				if (!browserFree) {
					browserFree = true;
					browserFreeLock.notifyAll();
				}
			}
			
		}
			break;
		default:
			break;
		}
	}

}
