package tuwien.dbai.wpps.common.process;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;


public class ProcessWrap {
	
	private String name;
	private String cmd;
	private boolean isOutput;
	private boolean isErrorOut;
	private OutputStream outStream;
	private OutputStream errStream;
	private boolean outputLabel;
	private boolean errorLabel;
	
//	private ProcessOutputStream outStreamProc = null;
//	private ProcessOutputStream errorStreamProc = null;
	
	private Process proc;
	public Process getProc() {
		return proc;
	}

	public ProcessWrap(
			String name
			, String cmd
			, boolean output, OutputStream outStream, boolean outputLabel
			, boolean error, OutputStream errStream, boolean errorLabel) {
		this.name = name;
		this.cmd = cmd;
		this.isOutput = output;
		this.isErrorOut = error;
		this.outStream = outStream;
		this.errStream = errStream;
		this.outputLabel = outputLabel;
		this.errorLabel = errorLabel;
	}
	
	private boolean exe1 = true;
	private boolean success = false;
	public boolean isSuccess() {
		return success;
	}

	public boolean exe() {
		if (!exe1) return false;
		exe1 = false;
		try {
			Runtime rt = Runtime.getRuntime();
			proc = rt.exec(cmd);
			
			if (isOutput && outStream != null) {
				ProcessOutputStream outStreamProc = new 
						ProcessOutputStream(name, proc.getInputStream()
								, (outputLabel)?"OUTPUT":null
								, outStream);
				new Thread(outStreamProc).start();
			}
			
			if (isErrorOut && errStream != null) {
				ProcessOutputStream errorStreamProc = new 
						ProcessOutputStream(name,proc.getErrorStream()
								, (errorLabel)?"ERROR":null
								, errStream);
				new Thread(errorStreamProc).start();
			}
			
//			int exitVal = proc.waitFor();
//			System.out.println("ExitValue: " + exitVal);
			success = true;
			return success;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
//	public ProcessWrap writeToProcess(String str) {
//		PrintWriter pw = new PrintWriter(proc.getOutputStream());
//		pw.println("(SayText \""+str+"\")");
//		pw.flush();
//		return this;
//	}
	
	public ProcessWrap writeToProcess(String str) {
		PrintWriter pw = new PrintWriter(proc.getOutputStream());
		pw.println(str);
		pw.flush();
		return this;
	}
	
	public void destroy() {
		proc.destroy();
	}
	
	public int waitFor() throws InterruptedException {
		int rez = proc.waitFor();
		return rez;
	}

}
