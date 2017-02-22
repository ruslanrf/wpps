/**
 * File name: EMBrowserEditorInput.java
 * @created: Oct 12, 2011 12:31:53 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.embrowser;

import java.net.MalformedURLException;

import org.eclipse.ui.IFileEditorInput;

/**
 * Input for the EMBrowserEditor
 * Class is a subclass of MozBrowserEditorInput from the
 * <a href="http://www.eclipse.org/atf/">ATF project</a>.
 * 
 * @created: Oct 12, 2011 12:31:53 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public class EMBrowserEditorInput
	extends org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditorInput {

	public EMBrowserEditorInput(String url) {
		super(url);
	}
	
	public EMBrowserEditorInput( IFileEditorInput input ) throws MalformedURLException {
		super(input);
	}
	
	public EMBrowserEditorInput(org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditorInput input) {
		super(input.getURL());
	}
	
}
