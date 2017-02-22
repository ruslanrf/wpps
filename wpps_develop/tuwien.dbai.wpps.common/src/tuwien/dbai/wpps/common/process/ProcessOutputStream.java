package tuwien.dbai.wpps.common.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author ruslan
 */
public class ProcessOutputStream implements Runnable {

	java.nio.ByteBuffer b;
	private String name;
	private InputStream is;
	private String type;
	private OutputStream os;
    
    ProcessOutputStream(String name, InputStream is)
    {
        this(name, is, null, null);
    }
    
    ProcessOutputStream(String name, InputStream is, String type)
    {
        this(name, is, type, null);
    }
    ProcessOutputStream(String name, InputStream is, String type, OutputStream redirect)
    {
    	this.name = name;
        this.is = is;
        this.type = type;
        this.os = redirect;
    }
    
    public void run()
    {
	        try
	        {
	            PrintWriter pw = null;
	            if (os != null)
	                pw = new PrintWriter(os);
	            else 
	            	pw = new PrintWriter(System.out);
	                
	            InputStreamReader isr = new InputStreamReader(is);
	            BufferedReader br = new BufferedReader(isr);
	            String line=null;
	            while ((line = br.readLine()) != null)
	            {
	            	line = br.readLine();
	                pw.println(name+":"+((type == null)?"":type + ">")+line);
	                pw.flush();
	            }
	            pw.flush();
	        } catch (IOException ioe)
	        {
	        }
    }
    
}
