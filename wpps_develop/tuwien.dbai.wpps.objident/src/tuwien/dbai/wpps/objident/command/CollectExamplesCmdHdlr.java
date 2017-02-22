package tuwien.dbai.wpps.objident.command;

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import tuwien.dbai.wpps.embrowser.EMBrowserUtil;
import tuwien.dbai.wpps.objident.lib.HtmlFilesProcessor;

import com.google.common.base.Preconditions;

@Deprecated
public class CollectExamplesCmdHdlr implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	// TODO implement!
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		File folder = null;
//				ObjIdentActivator.getDefSesCont().getCommonObjects().getMappedObjectAs(
//				EProperty.CURRENT_OPENED_FOLDER
//				, File.class);
		if (folder == null) return null;
		Preconditions.checkState(folder.isDirectory());
System.out.println("3 "+Thread.currentThread().getId());
		(new Thread(new HtmlFilesProcessor(folder, EMBrowserUtil.getActiveEMBrowserEditor()))).start();
		
System.out.println("That is it!");
		return null;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// TODO Auto-generated method stub

	}

}
