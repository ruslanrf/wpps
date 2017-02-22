/**
 * File name: DirWalker.java
 * @created: Feb 8, 2011 11:30:40 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.common;

import java.io.File;
import java.io.FilenameFilter;

import tuwien.dbai.wpps.common.callback.IProcedure;

/**
 * @created: Feb 8, 2011 11:30:40 AM
 * @author Ruslan Fayzrakhmanov (ruslanrf@gmail.com)
 *
 */
public class SimpleDirWalker {
	
	public SimpleDirWalker(File dir, IProcedure<File> pr) {
		setStartDir = dir;
		this.pr = pr;
	}
	
	public SimpleDirWalker(String dirStr, IProcedure<File> pr) {
		setStartDir = new File(dirStr);
		this.pr = pr;
	}
	
//	/**
//	 * @param dirStr
//	 * @param filter for files and sub/directories in the directory specified.
//	 * @param pr
//	 */
//	public SimpleDirWalker(String dirStr, FilenameFilter filter, IProcess pr) {
//		setStartDir = new File(dirStr);
//		this.filter = filter;
//		this.pr = pr;
//	}
	
	/**
	 * @param dirStr
	 * @param filter for files and sub/directories in the directory specified.
	 * @param pr
	 */
	public SimpleDirWalker(String dirStr, FilenameFilter filter, IProcedure<File> pr) {
		setStartDir = new File(dirStr);
		this.filter = filter;
		this.pr = pr;
	}
	
	private File setStartDir = null;
	private FilenameFilter filter = null;
	
//	public interface IProcess {
//		void process(File file);
//	}
	
	private IProcedure<File> pr = null;

	public void walk() {
		visitAllDirsAndFiles(setStartDir);
	}
	
	// Process all files and directories under dir
	private void visitAllDirsAndFiles(File dir) {
		pr.apply(dir);
	    if (dir.isDirectory()) {
	    	String[] children = null;
//	    	if (fileFilter != null)
    		children = dir.list(filter);
//	    	else
//	    		children = dir.list();
	        for (int i=0; i<children.length; i++) {
	            visitAllDirsAndFiles(new File(dir, children[i]));
	        }
	    }
	}
	

}
