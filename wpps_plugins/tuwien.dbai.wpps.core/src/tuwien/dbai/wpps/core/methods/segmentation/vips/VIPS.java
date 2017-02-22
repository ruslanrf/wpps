/**
 * 
 */
package tuwien.dbai.wpps.core.methods.segmentation.vips;

import java.util.List;

import tuwien.dbai.wpps.core.ie.instadp.interf.IResults;
import tuwien.dbai.wpps.core.methods.AWPUMethodDescription;
import tuwien.dbai.wpps.core.methods.AWPUWrapper;

/**
 * TODO: implement as a separate plug-in
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 27, 2012 1:06:30 PM
 */
public class VIPS extends AWPUWrapper {
	
	private int PDoc = 5;

	public VIPS(AWPUMethodDescription description) {
		super(description);
	}

	@Override
	protected List<IResults> _extractResults() {
		return null;
	}

	@Override
	protected void _dumpIntoLM(List<IResults> results) {
		
	}

}
