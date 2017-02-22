/**
 * 
 */
package tuwien.dbai.wpps.methods.eval.basic;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import tuwien.dbai.wpps.core.ie.api.basis.IIEBasisAPI;
import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.WPUMethodState;
import tuwien.dbai.wpps.core.wpmodel.physmodel.bgm.instadp.interf.IBox;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Aug 6, 2013 1:55:54 AM
 */
public class StatLib {
	
//	private String wrapper;
	private long phMFillingTS;
	private long acquiringAreaTS;
	private long enrichingTS;
	private long queryingTS;
	private IIEBasisAPI api;
	
	public StatLib(IIEBasisAPI api) {
		this.api = api;
	}
	
	public long getPhMFillingTS() {
		return phMFillingTS;
	}
	public void setPhMFillingTS(long phMFillingTS) {
		this.phMFillingTS = phMFillingTS;
	}
	public void setPhMFillingTS(WPUMethodState state) {
		this.phMFillingTS = state.getStatistic().getPhMFillingTS();
	}
	
	private long acquiringAreaTS2 = 0;
	public void startAcquiringAreaTS() {
		acquiringAreaTS2 = System.currentTimeMillis();
	}
	public long stopAcquiringAreaTS() {
		acquiringAreaTS = System.currentTimeMillis() - acquiringAreaTS2;
		return acquiringAreaTS;
	}
	public long getAcquiringAreaTS() {
		return acquiringAreaTS;
	}
	public void setAcquiringAreaTS(long acquiringAreaTS) {
		this.acquiringAreaTS = acquiringAreaTS;
	}
	
	private long enrichingTS2 = 0;
	public void startEnriching() {
		enrichingTS2 = System.currentTimeMillis();
	}
	public long stopEnriching() {
		enrichingTS = System.currentTimeMillis() - enrichingTS2;
		return enrichingTS;
	}
	public long getEnrichingTS() {
		return enrichingTS;
	}
	public void setEnrichingTS2(long enrichingTS) {
		this.enrichingTS = enrichingTS;
	}
	
	private long queryingTS2 = 0;
	public void startQuerying() {
		queryingTS2 = System.currentTimeMillis();
	}
	public long stopQuerying() {
		queryingTS = System.currentTimeMillis() - queryingTS2;
		return queryingTS;
	}
	public long getQueryingTS() {
		return queryingTS;
	}
	public void setQueryingTS(long queryingTS) {
		this.queryingTS = queryingTS;
	}
	
//	public String getWrapper() {
//		return wrapper;
//	}
//	public void setWrapper(String wrapper) {
//		this.wrapper = wrapper;
//	}
	
	private int countBoxes() {
		IResults res = api.getObjectsByType(IBox.class);
		return res.size();
	}
	
	
	private static final int COLUMNS_QNT = 5;
	public void save(String filePath) {
		
		final CellProcessor[] processors = new CellProcessor[COLUMNS_QNT];
		for (CellProcessor p : processors) {
			p = new NotNull();
		}
		
		ICsvListWriter listWriter = null;
		try {
			listWriter = new CsvListWriter(new FileWriter(filePath, true),
			        CsvPreference.STANDARD_PREFERENCE);
			listWriter.write(
					new ArrayList<Object>(COLUMNS_QNT)
						{{add(phMFillingTS);
						add(acquiringAreaTS);
						add(enrichingTS);
						add(queryingTS);
						add(countBoxes());}}
			, processors);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( listWriter != null ) {
				try {
					listWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	

	
	
	
	
//	private int countNodes(nsIDOMDocument doc) {
//		NodeIterator iter = ((DocumentTraversal)doc).createNodeIterator(doc, NodeFilter.SHOW_ELEMENT, null, false);
//		int qnt = 0;
//		while (iter.nextNode() != null) {
//			qnt++;
//		}
//		iter = ((DocumentTraversal)doc).createNodeIterator(doc, NodeFilter.SHOW_TEXT, 
//				new NodeFilter() {
//					@Override public short acceptNode(Node n) {
//						if (n.getNodeValue().trim().length() == 0)
//						return NodeFilter.FILTER_SKIP;
//						else
//							return NodeFilter.FILTER_ACCEPT;
//				} }
//				, false);
//		while (iter.nextNode() != null) {
//			qnt++;
//		}
//		return qnt;
//	}
	
}
