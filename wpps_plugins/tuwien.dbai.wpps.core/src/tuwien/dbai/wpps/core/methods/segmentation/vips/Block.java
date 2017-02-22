/**
 * 
 */
package tuwien.dbai.wpps.core.methods.segmentation.vips;

import java.util.LinkedList;
import java.util.List;

import tuwien.dbai.wpps.common.Mapping2;
import tuwien.dbai.wpps.core.wpmodel.physmodel.dom.instadp.interf.IDOMTraversalNode;

/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Oct 27, 2012 2:12:41 PM
 */
public class Block {
	
	private final List<Block> childBlocksList;
	public List<Block> getChildBlocksList() {
		return childBlocksList;
	}
	
	private List<IDOMTraversalNode> rootsList;
	public List<IDOMTraversalNode> getRootsList() {
		return rootsList;
	}

	private final List<Separator> separators;
	public List<Separator> getSeparators() {
		return separators;
	}

	private final Mapping2 delta;
	public Mapping2 getDelta() {
		return delta;
	}
	
	private int doC;
	public int getDoC() {
		return doC;
	}
	public void setDoC(int doC) {
		this.doC = doC;
	}
	
	public Block() {
		childBlocksList = new LinkedList<Block>();
		separators = new LinkedList<Separator>();
		delta = new Mapping2();
		this.doC = 0;
		this.rootsList = new LinkedList<IDOMTraversalNode>();
	}

}
