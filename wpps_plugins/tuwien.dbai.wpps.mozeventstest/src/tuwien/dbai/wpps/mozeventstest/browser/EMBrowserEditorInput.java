package tuwien.dbai.wpps.mozeventstest.browser;

import java.net.MalformedURLException;

import org.eclipse.ui.IFileEditorInput;

public class EMBrowserEditorInput
extends org.eclipse.atf.mozilla.ide.ui.browser.MozBrowserEditorInput {

	public EMBrowserEditorInput(String url) {
		super(url);
	}
	
	public EMBrowserEditorInput( IFileEditorInput input ) throws MalformedURLException{
		super(input);
	}

}
